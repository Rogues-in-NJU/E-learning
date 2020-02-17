package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.StateObject;

public class OtherState extends StateObject {
    public OtherState(){
        super(QuestionPartTypeEnum.OTHER);
    }
}
