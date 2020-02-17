package edu.nju.parser.statemachine;

import edu.nju.parser.common.Paragraph;
import edu.nju.parser.enums.QuestionPartTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class StateMachineContext {

    private StateObject previousObj;

    //缓存题目以解析的部分
    private Map<QuestionPartTypeEnum, StringBuilder> questionParts;

    //当前处理的一行
    private Paragraph line;

    public StateMachineContext(){
        previousObj = null;
        questionParts = new HashMap<>();
        questionParts.put(QuestionPartTypeEnum.CONTENT, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.APPEND, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.ANSWER, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.NOTE, new StringBuilder());
    }

    public void addLineToMap(QuestionPartTypeEnum questionPartTypeEnum){
        questionParts.get(questionPartTypeEnum).append(line);
    }

    public String generateQuestionContent(){
        //todo 题目各部分以怎么样的形式存储进 content里
        String content = questionParts.get(QuestionPartTypeEnum.CONTENT).toString();
        String append = questionParts.get(QuestionPartTypeEnum.APPEND).toString();
        String answer = questionParts.get(QuestionPartTypeEnum.ANSWER).toString();
        String note = questionParts.get(QuestionPartTypeEnum.NOTE).toString();

        String result = "new question ========== \n" +
                "content :" + content + "\n" +
                "append :" + append + "\n" +
                "answer :" + answer + "\n" +
                "note :" + note + "\n"
                + "================================";
        return result;
    }

    public void clearQuestionMap(){
        questionParts.put(QuestionPartTypeEnum.CONTENT, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.APPEND, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.ANSWER, new StringBuilder());
        questionParts.put(QuestionPartTypeEnum.NOTE, new StringBuilder());
    }

    public Paragraph getLine() {
        return line;
    }

    public void setLine(Paragraph line) {
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
