package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.StateObject;

public class NoteState extends StateObject {

    public NoteState(){
        super(QuestionPartTypeEnum.NOTE);
    }
}
