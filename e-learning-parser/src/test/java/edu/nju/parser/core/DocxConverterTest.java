package edu.nju.parser.core;

import edu.nju.parser.common.Paragraph;
import edu.nju.parser.question.Question;
import edu.nju.parser.statemachine.StateMachine;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.util.FileUtil;
import edu.nju.parser.util.QuestionUtil;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class DocxConverterTest {

    @Test
    public void testConvert() throws IOException, Docx4JException {
        String baseDir = DocxConverter.class.getResource("/").getPath();
        // Document document = converter.convert2Html();

        File dir = new File(baseDir);

        DocxConverterConfig.DocxConverterConfigBuilder builder
                = DocxConverterConfig.builder("/Users/hermc/Downloads/aa.docx", baseDir + "/html");
        DocxConverter converter = new DocxConverter(builder.build());
        List<Paragraph> paragraphs = converter.convert2Paragraphs();
        for (Paragraph p : paragraphs) {
            System.out.println(p.getOuterHtml());
        }
    }

    @Test
    public void test() {
        String baseDir = DocxConverter.class.getResource("/").getPath();
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
