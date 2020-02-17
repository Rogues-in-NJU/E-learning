package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.statemachine.StateObject;

public class OtherState extends StateObject {

    StateObject previousObject = this;

    public OtherState(){
        super(QuestionPartTypeEnum.OTHER);
    }

    public void execute(StateMachineContext context){
        //将当前行内容存储进对应题目部分里
        previousObject = context.getPreviousObj();
        if( previousObject instanceof OtherState){
            previousObject = ((OtherState) previousObject).getPreviousObject();
        }
        context.addLineToMap(previousObject.getQuestionPart());
    }

    public String getName(){
        return QuestionPartTypeEnum.OTHER.name();
    };

    public StateObject getPreviousObject() {
        return previousObject;
    }

    public void setPreviousObject(StateObject previousObject) {
        this.previousObject = previousObject;
    }
}
