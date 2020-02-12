package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.StateObject;

public class StemState extends StateObject {

    public StemState(){
        super(QuestionPartTypeEnum.STEM);
    }
}
