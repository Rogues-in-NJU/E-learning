package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.enums.LabelTypeEnum;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.statemachine.StateObject;
import edu.nju.parser.util.QuestionUtil;

import java.util.Map;
import java.util.Set;

public class TitleState extends StateObject {

    private LabelTypeEnum titleType;

    public TitleState(){
        super(QuestionPartTypeEnum.TITLE);
        this.titleType = LabelTypeEnum.EXAM;
    }

    @Override
    public void beforeAction(StateMachineContext context) {
        this.titleType = QuestionUtil.getTitleType(context.getLine());
    }

    @Override
    public void execute(StateMachineContext context){

        //todo 存储题目
        context.cacheQuestion();
        context.clearQuestionMap();

        //todo 根据标题提取公共标签
        context.clearChapterLabels();

        context.setSection();
        context.addLabels(QuestionUtil.getTitleType(context.getLine()));
    }

    public LabelTypeEnum getTitleType() {
        return titleType;
    }
}
