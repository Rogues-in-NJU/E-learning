package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.StateObject;

public class AppendState extends StateObject {

    public AppendState(){
        super(QuestionPartTypeEnum.APPEND);
    }
}
