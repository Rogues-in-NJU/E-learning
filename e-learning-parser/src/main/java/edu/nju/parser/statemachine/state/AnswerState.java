package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.statemachine.StateObject;

public class AnswerState extends StateObject {

    public AnswerState(){
        super(QuestionPartTypeEnum.ANSWER);
    }

    /**
     * 如果之前不是题干，现在出现题干。则认为是新题目了。需要将之前的题目存储起来
     * @param context
     */
    public void execute(StateMachineContext context){

        if (!context.getPreviousObj().equals(this)){
            //todo 存储题目
            String questionContent = context.generateQuestionContent();
            context.clearQuestionMap();
        }

        //可能是新的题干了
        super.execute(context);
    }
}