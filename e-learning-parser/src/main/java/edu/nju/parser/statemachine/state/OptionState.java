package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.StateObject;

public class OptionState extends StateObject {

    public OptionState(){
        super(QuestionPartTypeEnum.OPTION);
    }
}
