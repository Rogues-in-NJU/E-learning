package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.statemachine.StateObject;

public class AnswerState extends StateObject {

    public AnswerState(){
        super(QuestionPartTypeEnum.ANSWER);
    }

}
