package edu.nju.parser.statemachine;

import edu.nju.parser.enums.QuestionPartTypeEnum;

public class StateMachineDemo {

    public static void main(String[] args){
        StateMachineContext context = new StateMachineContext();
        StateMachine stateMachine = new StateMachine(context);

        // 解析出一行后 调用 正则判断 出类别
        // 然后用状态机辅助判断
        String line1 = "stemmmmmm";
        context.setLine(line1);
        stateMachine.execute(QuestionPartTypeEnum.CONTENT);

        String line2 = "answerrrrr";
        context.setLine(line2);
        stateMachine.execute(QuestionPartTypeEnum.ANSWER);

        String line3 = "stemmmmm";
        context.setLine(line3);
        stateMachine.execute(QuestionPartTypeEnum.CONTENT);

        //循环进行
        //每到一个新题目。状态机会将上一道题存储进数据库
    }
}
