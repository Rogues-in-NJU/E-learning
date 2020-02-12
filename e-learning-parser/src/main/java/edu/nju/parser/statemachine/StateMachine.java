package edu.nju.parser.statemachine;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.state.AnswerState;
import edu.nju.parser.statemachine.state.NoteState;
import edu.nju.parser.statemachine.state.OptionState;
import edu.nju.parser.statemachine.state.StemState;

public class StateMachine {

    // previous - event - next
    static Table<StateObject, QuestionPartTypeEnum, StateObject> rules = HashBasedTable.create();

    static StemState    stemState       =     new StemState();
    static OptionState  optionState     =   new OptionState();
    static AnswerState  answerState     =   new AnswerState();
    static NoteState    noteState       =     new NoteState();

    StateMachineContext context;

    {
        rules.put(stemState, QuestionPartTypeEnum.OPTION,   optionState);
        rules.put(stemState, QuestionPartTypeEnum.ANSWER,   answerState);
        rules.put(stemState, QuestionPartTypeEnum.NOTE,     noteState);
        rules.put(stemState, QuestionPartTypeEnum.STEM,     stemState);

        rules.put(optionState, QuestionPartTypeEnum.OPTION, optionState);
        rules.put(optionState, QuestionPartTypeEnum.ANSWER, answerState);
        rules.put(optionState, QuestionPartTypeEnum.NOTE,   noteState);
        rules.put(optionState, QuestionPartTypeEnum.STEM,   stemState);

        rules.put(answerState, QuestionPartTypeEnum.STEM,   stemState);
        rules.put(answerState, QuestionPartTypeEnum.NOTE,   noteState);
        rules.put(answerState, QuestionPartTypeEnum.ANSWER, answerState);

        rules.put(noteState, QuestionPartTypeEnum.NOTE,     noteState);
        rules.put(noteState, QuestionPartTypeEnum.STEM,     stemState);
    }

    public StateMachine(StateMachineContext context){
        this.context = context;
    }

    public void execute(QuestionPartTypeEnum questionPartTypeEnum){

        StateObject previousState = context.getPreviousObj();
        StateObject nextState = rules.get(previousState, questionPartTypeEnum);

        if(nextState == null){
            //不符合规则的特殊行，无法判断，暂时归于上一类
            nextState = previousState;
        }

        nextState.beforeAction(context);
        nextState.execute(context);
        nextState.afterAction(context);

    }
}
