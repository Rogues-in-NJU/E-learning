package edu.nju.parser.statemachine;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.state.AnswerState;
import edu.nju.parser.statemachine.state.AppendState;
import edu.nju.parser.statemachine.state.ContentState;
import edu.nju.parser.statemachine.state.NoteState;

public class StateMachine {

    // previous - event - next
    static Table<StateObject, QuestionPartTypeEnum, StateObject> rules = HashBasedTable.create();

    static ContentState contentState    =   new ContentState();
    static AppendState  appendState     =   new AppendState();
    static AnswerState  answerState     =   new AnswerState();
    static NoteState    noteState       =   new NoteState();

    StateMachineContext context;

    {
        rules.put(contentState, QuestionPartTypeEnum.APPEND,   appendState);
        rules.put(contentState, QuestionPartTypeEnum.ANSWER,   answerState);
        rules.put(contentState, QuestionPartTypeEnum.NOTE,     noteState);
        rules.put(contentState, QuestionPartTypeEnum.CONTENT,  contentState);

        rules.put(appendState, QuestionPartTypeEnum.APPEND, appendState);
        rules.put(appendState, QuestionPartTypeEnum.ANSWER, answerState);
        rules.put(appendState, QuestionPartTypeEnum.NOTE,   noteState);
        rules.put(appendState, QuestionPartTypeEnum.CONTENT,contentState);

        rules.put(answerState, QuestionPartTypeEnum.CONTENT,contentState);
        rules.put(answerState, QuestionPartTypeEnum.NOTE,   noteState);
        rules.put(answerState, QuestionPartTypeEnum.ANSWER, answerState);

        rules.put(noteState, QuestionPartTypeEnum.NOTE,     noteState);
        rules.put(noteState, QuestionPartTypeEnum.CONTENT,  contentState);
    }

    public StateMachine(StateMachineContext context){
        this.context = context;
        this.context.setPreviousObj(contentState);
    }

    public void execute(QuestionPartTypeEnum questionPartTypeEnum){

        StateObject previousState = context.getPreviousObj();
        StateObject nextState = null;
        if(questionPartTypeEnum != null){
            nextState = rules.column(questionPartTypeEnum).get(previousState);
        }

        if(nextState == null){
            //不符合规则的特殊行，无法判断，暂时归于上一类
            nextState = previousState;
        }

        nextState.beforeAction(context);
        nextState.execute(context);
        nextState.afterAction(context);

    }
}
