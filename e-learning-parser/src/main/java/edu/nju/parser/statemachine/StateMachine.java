package edu.nju.parser.statemachine;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.state.*;

public class StateMachine {

    // previous - event - next
    static Table<StateObject, QuestionPartTypeEnum, StateObject> rules = HashBasedTable.create();

    static ContentState contentState    =   new ContentState();
    static AppendState  appendState     =   new AppendState();
    static AnswerState  answerState     =   new AnswerState();
    static NoteState    noteState       =   new NoteState();
    static TitleState   titleState      =   new TitleState();
    static OtherState   otherState      =   new OtherState();

    StateMachineContext context;

    {
        rules.put(contentState, QuestionPartTypeEnum.CONTENT,  contentState);
        rules.put(contentState, QuestionPartTypeEnum.APPEND,   appendState);
        rules.put(contentState, QuestionPartTypeEnum.ANSWER,   answerState);
        rules.put(contentState, QuestionPartTypeEnum.NOTE,     noteState);
        rules.put(contentState, QuestionPartTypeEnum.TITLE,    titleState);
        rules.put(contentState, QuestionPartTypeEnum.OTHER,    otherState);

        rules.put(appendState, QuestionPartTypeEnum.CONTENT,contentState);
        rules.put(appendState, QuestionPartTypeEnum.APPEND, appendState);
        rules.put(appendState, QuestionPartTypeEnum.ANSWER, answerState);
        rules.put(appendState, QuestionPartTypeEnum.NOTE,   noteState);
        rules.put(appendState, QuestionPartTypeEnum.TITLE,  titleState);
        rules.put(appendState, QuestionPartTypeEnum.OTHER,  otherState);

        rules.put(answerState, QuestionPartTypeEnum.CONTENT,contentState);
        rules.put(answerState, QuestionPartTypeEnum.ANSWER, answerState);
        rules.put(answerState, QuestionPartTypeEnum.NOTE,   noteState);
        rules.put(answerState, QuestionPartTypeEnum.TITLE,  titleState);
        rules.put(answerState, QuestionPartTypeEnum.OTHER,  otherState);

        rules.put(noteState, QuestionPartTypeEnum.CONTENT,  contentState);
        rules.put(noteState, QuestionPartTypeEnum.ANSWER,   answerState);
        rules.put(noteState, QuestionPartTypeEnum.NOTE,     noteState);
        rules.put(noteState, QuestionPartTypeEnum.TITLE,    titleState);
        rules.put(noteState, QuestionPartTypeEnum.OTHER,    otherState);

        rules.put(titleState, QuestionPartTypeEnum.CONTENT,  contentState);
        rules.put(titleState, QuestionPartTypeEnum.APPEND,   appendState);
        rules.put(titleState, QuestionPartTypeEnum.ANSWER,   answerState);
        rules.put(titleState, QuestionPartTypeEnum.NOTE,     noteState);
        rules.put(titleState, QuestionPartTypeEnum.TITLE,    titleState);
        rules.put(titleState, QuestionPartTypeEnum.OTHER,    otherState);

        //other
        rules.put(otherState, QuestionPartTypeEnum.CONTENT,  contentState);
        rules.put(otherState, QuestionPartTypeEnum.APPEND,   appendState);
        rules.put(otherState, QuestionPartTypeEnum.ANSWER,   answerState);
        rules.put(otherState, QuestionPartTypeEnum.NOTE,     noteState);
        rules.put(otherState, QuestionPartTypeEnum.TITLE,    titleState);
        rules.put(otherState, QuestionPartTypeEnum.OTHER,    otherState);
    }

    public StateMachine(StateMachineContext context){
        this.context = context;
        this.context.setPreviousObj(titleState);
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

    public void close(){
        context.clearExamTitle();
        context.clearChapterTitle();
        context.cacheQuestion();
        context.clearQuestionMap();
    }
}
