package edu.nju.parser;

import edu.nju.parser.common.Paragraph;
import edu.nju.parser.core.DocxConverter;
import edu.nju.parser.core.DocxConverterConfig;
import edu.nju.parser.question.Question;
import edu.nju.parser.statemachine.StateMachine;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.util.FileUtil;
import edu.nju.parser.util.QuestionUtil;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by alfred on 2020/2/17.
 */
public class Main {
    public static void main(String[] args) {
        String baseDir = DocxConverter.class.getResource("/").getPath();
        // Document document = converter.convert2Html();

        File dir = new File(baseDir);
        List<File> files = FileUtil.getAllFile(dir, ".docx");

        for (File f: files) {
            StateMachineContext context = new StateMachineContext();
            StateMachine stateMachine = new StateMachine(context);
            try {
                DocxConverterConfig.DocxConverterConfigBuilder builder
                        = DocxConverterConfig.builder(f.getCanonicalPath(), baseDir + "/html");
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
            }
            Collection<Question> questions = context.getQuestions();
            for (Question q : questions) {
                System.out.println(q.questionToString());
            }
        }
    }
}
