package edu.nju.parser.statemachine;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.nju.parser.common.Paragraph;
import edu.nju.parser.core.CommonTag;
import edu.nju.parser.core.FieldTag;
import edu.nju.parser.core.Tags;
import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.enums.LabelTypeEnum;
import edu.nju.parser.question.Question;
import edu.nju.parser.question.QuestionStorageUtil;
import edu.nju.parser.util.FileUtil;
import edu.nju.parser.util.QuestionUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

import java.io.*;
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

    // 当前所处章节
    private String section = "";

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
        tags.addTagAnalyzer("common", new CommonTag());

        File dir = new File(StateMachineContext.class.getResource("/tags").getPath());
        List<File> tagFiles = FileUtil.getAllFileWithoutConvert(dir, ".csv");
        for(File f : tagFiles){
            String keyword = f.getName().replace(".csv", "");
            tags.addTagAnalyzer(keyword, new FieldTag(f));
        }
    }

    public void addLabels(LabelTypeEnum labelTypeEnum){
        Set<String> realTags = new HashSet<>(tags.getTags("common", line.getInnerText()));
        labels.get(labelTypeEnum).addAll(realTags);
    }

    public void addPathLabels(String filePath) {
        if (Objects.nonNull(filePath)) {
            labels.put(LabelTypeEnum.PATH, tags.getTags("common", filePath));
        }
    }

    public void addLineToMap(QuestionPartTypeEnum questionPartTypeEnum){
        questionParts.get(questionPartTypeEnum).append(line.getHtml());
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
        String[] keywords = getKeywords();
        realLabels.addAll(tags.getTags(StringUtils.join(keywords, ""), plainTextContent));
        realLabels.addAll(tags.getTags(StringUtils.join(keywords, ""), plainTextAppend));
        realLabels.addAll(tags.getTags(StringUtils.join(keywords, ""), plainTextAnswer));
        realLabels.addAll(tags.getTags(StringUtils.join(keywords, ""), plainTextNote));

        // 编号
        String subSection = QuestionUtil.findSubSection(plainTextContent);
        if (Objects.nonNull(subSection)) {
            String section = QuestionUtil.findSection(subSection);
            if (Objects.nonNull(section)) {
                section = section.trim().replaceAll("[．.、【\\[（\\(】\\]）\\)\\s]", "");
                this.section = section;
                subSection = subSection.trim().replaceAll("[一二三四五六七八九十]", "");
            }

            subSection = subSection.trim().replaceAll("[．.、【\\[（\\(】\\]）\\)\\s]", "");
            question.setSubSection(subSection);
        }

        question.setContent(content);
        question.setAppend(append);
        question.setAnswer(answer);
        question.setNote(note);
        question.setSection(this.section);
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

    public void storageAllQuestions() {
        for (Question question : getQuestions()) {
            String result = QuestionStorageUtil.saveQuestion(question);
            System.out.println(result);
        }
    }

    public void setSection() {
        String line = Jsoup.parse(this.line.getInnerText()).text();
        String section = QuestionUtil.findSection(line);
        if (Objects.nonNull(section)) {
            section = section.trim().replaceAll("[．.、【\\[（\\(】\\]）\\)\\s]", "");
            this.section = section;
        }
    }

    private String[] getKeywords(){
        String[] keywords = {"", ""};
        Set<String> grades = new HashSet<>();
        grades.add("小学");
        grades.add("初中");
        grades.add("高中");

        //目前只支持年级、科目两类标签
        //CHAPTER的label会覆盖EXAM的label
        for(String label: labels.get(LabelTypeEnum.EXAM)){
            if (grades.contains(label)) {
                keywords[0] = label;
            } else {
                keywords[1] = label;
            }
        }
        for(String label: labels.get(LabelTypeEnum.CHAPTER)){
            if (grades.contains(label)) {
                keywords[0] = label;
            } else {
                keywords[1] = label;
            }
        }

        return keywords;
    }
}
