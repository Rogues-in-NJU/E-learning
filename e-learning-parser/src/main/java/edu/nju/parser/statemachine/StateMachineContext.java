package edu.nju.parser.statemachine;

import edu.nju.parser.common.Paragraph;
import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.enums.TitleTypeEnum;
import edu.nju.parser.question.Question;
import edu.nju.parser.util.ExerciseUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class StateMachineContext {

    private StateObject previousObj;

    //缓存标题以确定公共标签
    private Map<TitleTypeEnum, StringBuilder> titles;

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

        titles = new HashMap<>();
        titles.put(TitleTypeEnum.EXAM, new StringBuilder());
        titles.put(TitleTypeEnum.CHAPTER, new StringBuilder());
    }

    public void addTitle(TitleTypeEnum titleTypeEnum){
        titles.get(titleTypeEnum).append(line.getInnerText());
    }

    public void addLineToMap(QuestionPartTypeEnum questionPartTypeEnum){
        questionParts.get(questionPartTypeEnum).append(line.getInnerText());
    }

//    public String generateQuestionContent(){
//        //todo 题目各部分以怎么样的形式存储进 content里
//        String content = questionParts.get(QuestionPartTypeEnum.CONTENT).toString();
//        String append = questionParts.get(QuestionPartTypeEnum.APPEND).toString();
//        String answer = questionParts.get(QuestionPartTypeEnum.ANSWER).toString();
//        String note = questionParts.get(QuestionPartTypeEnum.NOTE).toString();
//
//        // 之后生成对象时可以有专门的方法判断是否为空
//        if (content.isEmpty() && append.isEmpty() && answer.isEmpty() && note.isEmpty()) {
//            return null;
//        }
//
//        String result = "new question ========== \n" +
//                "content :" + content + "\n" +
//                "append :" + append + "\n" +
//                "answer :" + answer + "\n" +
//                "note :" + note + "\n"
//                + "================================";
//        return result;
//    }

    //TODO 完整地存题目，目前只存了4个信息
    public Question getQuestion(){

        Question question = new Question();
        String content = questionParts.get(QuestionPartTypeEnum.CONTENT).toString();
        String append = questionParts.get(QuestionPartTypeEnum.APPEND).toString();
        String answer = questionParts.get(QuestionPartTypeEnum.ANSWER).toString();
        String note = questionParts.get(QuestionPartTypeEnum.NOTE).toString();

        // 编号
        String sections = ExerciseUtil.findSections(content);
        if (Objects.nonNull(sections)) {
            sections = sections.trim().replaceAll("[．.、]", " ");
            String[] splits = sections.split(" ");
            if (splits.length >= 2) {
                question.setSection(splits[0].trim());
                question.setSubSection(splits[1].trim());
            } else {
                question.setSubSection(splits[0]);
            }
        }

        question.setContent(content);
        question.setAppend(append);
        question.setAnswer(answer);
        question.setNote(note);

        return question;
    }

    public void clearExamTitle(){
        titles.put(TitleTypeEnum.EXAM, new StringBuilder());
    }

    public void clearChapterTitle(){
        titles.put(TitleTypeEnum.CHAPTER, new StringBuilder());
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

    public Map<TitleTypeEnum, StringBuilder> getTitles() {
        return titles;
    }
}
