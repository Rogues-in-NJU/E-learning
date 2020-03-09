package edu.nju.parser.core;

import com.alibaba.fastjson.JSON;
import edu.nju.parser.common.Paragraph;
import edu.nju.parser.question.Question;
import edu.nju.parser.statemachine.StateMachine;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.util.FileUtil;
import edu.nju.parser.util.QuestionUtil;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

public class DocxConverterTest {

    @Test
    public void testConvert() throws IOException, Docx4JException {
        String baseDir = DocxConverter.class.getResource(File.separator).getPath();
        // Document document = converter.convert2Html();

        File dir = new File(baseDir);

        DocxConverterConfig.DocxConverterConfigBuilder builder
                = DocxConverterConfig.builder(dir.getAbsolutePath() + File.separator + "a.docx", baseDir + File.separator + "html");
        DocxConverter converter = new DocxConverter(builder.build());
        List<Paragraph> paragraphs = converter.convert2Paragraphs();
        // for (Paragraph p : paragraphs) {
        //     System.out.println(p.getHtml());
        // }

        StateMachineContext context = new StateMachineContext();
        StateMachine stateMachine = new StateMachine(context);

        for (Paragraph p : paragraphs) {
            // 解析出一行后 调用 正则判断 出类别
            // 然后用状态机辅助判断
            System.out.println(JSON.toJSONString(p.getImageLatexes()));
            context.setLine(p);
            stateMachine.execute(QuestionUtil.getParagraphType(p));
        }

        Collection<Question> questions = context.getQuestions();
        stateMachine.close();

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

        File file = new File(dir.getAbsolutePath() + File.separator + "html" + File.separator + "a2.html");
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));

        String s = doc.html();
        System.out.println(s);

        bufferedWriter.write(doc.html());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    @Test
    public void test() {
        String baseDir = DocxConverter.class.getResource(File.separator).getPath();
        // Document document = converter.convert2Html();

        File dir = new File(baseDir);
        List<File> files = FileUtil.getAllFile(dir, ".docx");
        Collection<Question> questions = null;

        for (File f: files) {
            StateMachineContext context = new StateMachineContext();
            StateMachine stateMachine = new StateMachine(context);
            try {
                context.addPathLabels(f.getCanonicalPath());

                DocxConverterConfig.DocxConverterConfigBuilder builder
                        = DocxConverterConfig.builder(f.getCanonicalPath(), "");
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
                questions = context.getQuestions();
                stateMachine.close();
            }

            for (Question q : questions) {
                System.out.println(q.questionToString());
            }
        }
    }

    @Test
    public void testBlank() {
        System.out.println(Jsoup.parse("<img href='test.png'/>").text());
    }

}
