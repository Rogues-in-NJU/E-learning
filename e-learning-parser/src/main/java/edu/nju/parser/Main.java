package edu.nju.parser;

import edu.nju.parser.common.Paragraph;
import edu.nju.parser.core.DocxConverter;
import edu.nju.parser.core.DocxConverterConfig;
import edu.nju.parser.statemachine.StateMachine;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.util.ExerciseUtil;

import java.util.List;

/**
 * Created by alfred on 2020/2/17.
 */
public class Main {
    public static void main(String[] args) {
        StateMachineContext context = new StateMachineContext();
        StateMachine stateMachine = new StateMachine(context);

        String baseDir = DocxConverter.class.getResource("/").getPath();
//        System.out.println(baseDir);
        // Document document = converter.convert2Html();

        try {
            DocxConverterConfig.DocxConverterConfigBuilder builder
                    = DocxConverterConfig.builder(baseDir + "/demo.docx", baseDir + "/html");
            DocxConverter converter = new DocxConverter(builder.build());
            List<Paragraph> paragraphs = converter.convert2Paragraphs();

            for (Paragraph p : paragraphs) {
                // 解析出一行后 调用 正则判断 出类别
                // 然后用状态机辅助判断
//                System.out.println(p.getInnerText());
                context.setLine(p);
                stateMachine.execute(ExerciseUtil.getParagraphType(p));
            }
            stateMachine.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
