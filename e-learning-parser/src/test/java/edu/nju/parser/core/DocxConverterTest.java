package edu.nju.parser.core;

import edu.nju.parser.common.Paragraph;
import edu.nju.parser.statemachine.StateMachine;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.util.ExerciseUtil;
import edu.nju.parser.util.Split;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DocxConverterTest {

    @Test
    public void test() {
        StateMachineContext context = new StateMachineContext();
        StateMachine stateMachine = new StateMachine(context);

        String baseDir = DocxConverter.class.getResource("/").getPath();
        System.out.println(baseDir);
        // Document document = converter.convert2Html();

        try {
            DocxConverterConfig.DocxConverterConfigBuilder builder
                    = DocxConverterConfig.builder(baseDir + "/demo.docx", baseDir + "/html");
            DocxConverter converter = new DocxConverter(builder.build());
            List<Paragraph> paragraphs = converter.convert2Paragraphs();

            for (Paragraph p: paragraphs) {
                // 解析出一行后 调用 正则判断 出类别
                // 然后用状态机辅助判断
                String line = p.getInnerText();
                System.out.println(line);
                context.setLine(line);
                stateMachine.execute(ExerciseUtil.getParagraphType(line));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
