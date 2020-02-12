package edu.nju.parser.statemachine;

import edu.nju.parser.enums.QuestionPartTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class StateMachineContext {

    private StateObject previousObj;

    //缓存题目以解析的部分
    private Map<QuestionPartTypeEnum, StringBuilder> questionParts;

    //当前处理的一行
    private String line;

    public StateMachineContext(){
        previousObj = null;
        questionParts = new HashMap<>();
        questionParts.put(QuestionPartTypeEnum.STEM, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.OPTION, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.ANSWER, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.NOTE, new StringBuilder());
        line = "";
    }

    public void addLineToMap(QuestionPartTypeEnum questionPartTypeEnum){
        questionParts.get(questionPartTypeEnum).append(line);
        line = "";
    }

    public String generateQuestionContent(){
        //todo 题目各部分以怎么样的形式存储进 content里
        return "todo";
    }

    public void clearQuestionMap(){
        questionParts.put(QuestionPartTypeEnum.STEM, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.OPTION, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.ANSWER, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.NOTE, new StringBuilder());
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public StateObject getPreviousObj() {
        return previousObj;
    }

    public void setPreviousObj(StateObject previousObj) {
        this.previousObj = previousObj;
    }

    public Map<QuestionPartTypeEnum, StringBuilder> getQuestionParts() {
        return questionParts;
    }

    public void setQuestionParts(Map<QuestionPartTypeEnum, StringBuilder> questionParts) {
        this.questionParts = questionParts;
    }
}
