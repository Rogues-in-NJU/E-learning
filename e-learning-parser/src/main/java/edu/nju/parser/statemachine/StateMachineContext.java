package edu.nju.parser.statemachine;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.nju.parser.common.Paragraph;
import edu.nju.parser.core.CommonTag;
import edu.nju.parser.core.MathTag;
import edu.nju.parser.core.Tags;
import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.enums.TitleTypeEnum;
import edu.nju.parser.question.Question;
import edu.nju.parser.util.QuestionUtil;

import java.util.*;

public class StateMachineContext {

    private StateObject previousObj;

    //缓存标题以确定公共标签
    private Map<TitleTypeEnum, StringBuilder> titles;

    //缓存题目以解析的部分
    private Map<QuestionPartTypeEnum, StringBuilder> questionParts;

    //缓存已有题目
    private Table<String, String, Question> questions = HashBasedTable.create();

    //当前处理的一行
    private Paragraph line;

    private Tags tags;

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

        tags = new Tags();
        tags.addTagAnalyzer(new MathTag());
        tags.addTagAnalyzer(new CommonTag());
    }

    public void addTitle(TitleTypeEnum titleTypeEnum){
        titles.get(titleTypeEnum).append(line.getInnerText());
    }

    public void addLineToMap(QuestionPartTypeEnum questionPartTypeEnum){
        questionParts.get(questionPartTypeEnum).append(line.getInnerText());
    }

    //TODO 完整地存题目，目前只存了4个信息
    public Question getQuestion(){

        Question question = new Question();
        String content = questionParts.get(QuestionPartTypeEnum.CONTENT).toString();
        String append = questionParts.get(QuestionPartTypeEnum.APPEND).toString();
        String answer = questionParts.get(QuestionPartTypeEnum.ANSWER).toString();
        String note = questionParts.get(QuestionPartTypeEnum.NOTE).toString();

        Set<String> realTags = new HashSet<>();
        realTags.addAll(tags.getTags(content));
        realTags.addAll(tags.getTags(note));

        // 编号
        String sections = QuestionUtil.findSections(content);
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

    public void cacheQuestion(){
        Question question = getQuestion();
        if (question.isEmpty()) {
            return;
        }

        Question oldQuestion = questions.get(question.getSection(), question.getSubSection());
        if (oldQuestion != null) {
            oldQuestion.update(question);
            questions.put(oldQuestion.getSection(), oldQuestion.getSubSection(), oldQuestion);
        } else {
            questions.put(question.getSection(), question.getSubSection(), question);
        }
    }

    public Collection<Question> getQuestions(){
        return questions.values();
    }
}
