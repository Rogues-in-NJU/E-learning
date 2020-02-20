package edu.nju.parser.statemachine.state;

import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.enums.TitleTypeEnum;
import edu.nju.parser.statemachine.StateMachineContext;
import edu.nju.parser.statemachine.StateObject;
import edu.nju.parser.util.QuestionUtil;

import java.util.Map;

public class TitleState extends StateObject {

    private TitleTypeEnum titleType;

    public TitleState(){
        super(QuestionPartTypeEnum.TITLE);
        this.titleType = TitleTypeEnum.EXAM;
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
        Map<TitleTypeEnum, StringBuilder> titles = context.getTitles();
        System.out.println();
        System.out.println("exam:" + titles.get(TitleTypeEnum.EXAM));
        System.out.println("chapter:" + titles.get(TitleTypeEnum.CHAPTER));
        System.out.println();
        context.clearChapterTitle();

        context.addTitle(QuestionUtil.getTitleType(context.getLine()));
    }

    public TitleTypeEnum getTitleType() {
        return titleType;
    }
}
