package edu.nju.parser;

import edu.nju.parser.common.Paragraph;
import edu.nju.parser.core.DocxConverter;
import edu.nju.parser.core.DocxConverterConfig;
import edu.nju.parser.question.Question;
import edu.nju.parser.statemachine.StateMachine;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.util.FileUtil;
import edu.nju.parser.util.QuestionUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by alfred on 2020/2/17.
 */
public class Main {

    public static void main(String[] args) {
        String baseDir = args[0];
//        String baseDir = DocxConverter.class.getResource(File.separator).getPath();
        // Document document = converter.convert2Html();

        File dir = new File(baseDir);
        List<File> files = FileUtil.getAllFile(dir, ".docx");

        for (File f: files) {
            System.out.println(f + " 解析开始");

            StateMachineContext context = new StateMachineContext();
            StateMachine stateMachine = new StateMachine(context);
            try {
                DocxConverterConfig.DocxConverterConfigBuilder builder
                        = DocxConverterConfig.builder(f.getCanonicalPath(), baseDir + File.separator + "html");
                DocxConverter converter = new DocxConverter(builder.build());
                List<Paragraph> paragraphs = converter.convert2Paragraphs();

                for (Paragraph p : paragraphs) {
                    // 解析出一行后 调用 正则判断 出类别
                    // 然后用状态机辅助判断
//                System.out.println(p.getInnerText());
                    context.setLine(p);
                    stateMachine.execute(QuestionUtil.getParagraphType(p));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                stateMachine.close();
                System.out.println(f + " 解析结束");
            }

            Collection<Question> questions = context.getQuestions();
            try {
                saveHtmlFile(dir, f, questions);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void saveHtmlFile(File dir, File f, Collection<Question> questions) throws IOException {
        Document doc = Jsoup.parse("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "</body>\n" +
                "</html>");
        for (Question q : questions) {
            String c = "<div>题目: " + q.getContent() + "</div>";
            String ap = "";
            String an = "";
            String n = "";
            if (!StringUtils.isBlank(q.getAppend())) {
                ap = "<div>选项: " + q.getAppend() + "</div>";
            }
            if (!StringUtils.isBlank(q.getAnswer())) {
                an = "<div>答案: " + q.getAnswer() + "</div>";
            }
            if (!StringUtils.isBlank(q.getNote())) {
                n = "<div>讲义: " + q.getNote() + "</div>";
            }
            String labels = "<div>标签: " + StringUtils.join(q.getLabels(), " | ") + "</div>";
            doc.select("body")
                    .append("<div>" + c + ap + an + n + labels + "</div>")
                    .append("<hr>");
        }

        String docxFilePath = f.getCanonicalPath();
        String htmlFileName = docxFilePath.substring(docxFilePath.lastIndexOf(File.separator) + 1,
                docxFilePath.lastIndexOf(".") != -1 ? docxFilePath.lastIndexOf(".") : docxFilePath.length()) + ".html";
        File file = new File(dir.getAbsolutePath() + File.separator + "html" + File.separator + htmlFileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));

        bufferedWriter.write(doc.html());
        bufferedWriter.flush();
        bufferedWriter.close();

        System.out.println(file + " 生成成功");
    }
}
