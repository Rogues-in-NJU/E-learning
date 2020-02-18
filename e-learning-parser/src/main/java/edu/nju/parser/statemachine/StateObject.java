package edu.nju.parser.statemachine;

import edu.nju.parser.enums.QuestionPartTypeEnum;

public class StateObject {

    private QuestionPartTypeEnum questionPart;

    protected StateObject(QuestionPartTypeEnum questionPartTypeEnum){
        questionPart = questionPartTypeEnum;
    }

    public String getName(){
        return questionPart.name();
    };

    public void beforeAction(StateMachineContext context){
//        StateObject previousObj = context.getPreviousObj();
//        System.out.println("previous is ：" + previousObj.getName() +
//                " ; now is : " + getName());
    }

    public void execute(StateMachineContext context){
        //将当前行内容存储进对应题目部分里
        context.addLineToMap(questionPart);
    }

    public void afterAction(StateMachineContext context){
        context.setPreviousObj(this);
    }

    public QuestionPartTypeEnum getQuestionPart() {
        return questionPart;
    }
}
