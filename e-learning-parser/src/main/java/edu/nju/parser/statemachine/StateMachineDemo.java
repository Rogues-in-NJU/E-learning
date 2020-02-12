package edu.nju.parser.statemachine;

import edu.nju.parser.enums.QuestionPartTypeEnum;

import java.edu.nju.parser.util.ParagraphType;

public class StateMachineDemo {

    public static void main(String[] args){
        StateMachineContext context = new StateMachineContext();
        StateMachine stateMachine = new StateMachine(context);

        // 解析出一行后 调用 正则判断 出类别
        // 然后用状态机辅助判断
        String line = "demo";
        java.edu.nju.parser.util.ParagraphType paragraphType = ParagraphType.AnswerTitle;

        context.setLine(line);
        stateMachine.execute(paragraphType);

        //循环进行
        //每到一个新题目。状态机会将上一道题存储进数据库
    }
}
