package edu.nju.parser.statemachine;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.nju.parser.common.Paragraph;
import edu.nju.parser.core.CommonTag;
import edu.nju.parser.core.MathTag;
import edu.nju.parser.core.Tags;
import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.enums.LabelTypeEnum;
import edu.nju.parser.question.Question;
import edu.nju.parser.util.QuestionUtil;
import org.jsoup.Jsoup;

import java.util.*;

public class StateMachineContext {

    private StateObject previousObj;

    //缓存标题以确定公共标签
    private Map<LabelTypeEnum, Set<String>> labels;

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

        labels = new HashMap<>();
        labels.put(LabelTypeEnum.EXAM, new HashSet<>());
        labels.put(LabelTypeEnum.CHAPTER, new HashSet<>());

        tags = new Tags();
        tags.addTagAnalyzer(new MathTag());
        tags.addTagAnalyzer(new CommonTag());
    }

    public void addLabels(LabelTypeEnum labelTypeEnum){
        Set<String> realTags = new HashSet<>(tags.getTags(line.getInnerText()));
        labels.get(labelTypeEnum).addAll(realTags);
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

        String plainTextContent = Jsoup.parse(content).text();
        String plainTextAppend = Jsoup.parse(append).text();
        String plainTextAnswer = Jsoup.parse(answer).text();
        String plainTextNote = Jsoup.parse(note).text();

        Set<String> realLabels = new HashSet<>();
        realLabels.addAll(tags.getTags(plainTextContent));
        realLabels.addAll(tags.getTags(plainTextAppend));
        realLabels.addAll(tags.getTags(plainTextAnswer));
        realLabels.addAll(tags.getTags(plainTextNote));

        // 编号
        String sections = QuestionUtil.findSections(plainTextContent);
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
        question.addLabels(labels.get(LabelTypeEnum.EXAM));
        question.addLabels(labels.get(LabelTypeEnum.CHAPTER));
        question.addLabels(realLabels);

        return question;
    }

    public void clearExamLabels(){
        labels.put(LabelTypeEnum.EXAM, new HashSet<>());
    }

    public void clearChapterLabels(){
        labels.put(LabelTypeEnum.CHAPTER, new HashSet<>());
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

    public Map<LabelTypeEnum, Set<String>> getLabels() {
        return labels;
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
