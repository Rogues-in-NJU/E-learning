package edu.nju.parser.question;

import com.alibaba.fastjson.JSON;

import java.util.HashSet;
import java.util.Set;

public class Question {

    private Integer id;

    private Integer type;

    private String section = "";

    private String subSection = "";

    private String content;

    private String append;

    private String answer;

    private String note;

    private Set<String> labels = new HashSet<>();

    private Integer parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        if (this.section == null || this.section.isEmpty()) {
            this.section = section;
        }
    }

    public String getSubSection() {
        return subSection;
    }

    public void setSubSection(String subSection) {
        if (this.subSection == null || this.subSection.isEmpty()) {
            this.subSection = subSection;
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (this.content == null || this.content.isEmpty()) {
            this.content = content;
        }
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        if (this.append == null || this.append.isEmpty()) {
            this.append = append;
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        if (this.answer == null || this.answer.isEmpty()) {
            this.answer = answer;
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        if (this.note == null || this.note.isEmpty()) {
            this.note = note;
        }
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void addLabels(Set<String> labels){
        this.labels.addAll(labels);
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String questionToString() {
        String result = "Question ======================== \n" +
                "id :" + id + "\n" +
                "type :" + type + "\n" +
                "section :" + section + "\n" +
                "subSection :" + subSection + "\n" +
                "content :" + content + "\n" +
                "append :" + append + "\n" +
                "answer :" + answer + "\n" +
                "note :" + note + "\n" +
                "labels :" + JSON.toJSONString(labels) + "\n" +
                "parentId :" + parentId + "\n"
                + "================================";
        return result;
    }

    public boolean isEmpty(){
        return (content == null || content.isEmpty()) &&
                (append == null || append.isEmpty()) &&
                (answer == null || answer.isEmpty()) &&
                (note == null || note.isEmpty());
    }

    public void update(Question question){
        this.section = question.getSection();
        this.subSection = question.getSubSection();
        this.content = question.getContent();
        this.append = question.getAppend();
        this.answer = question.getAnswer();
            this.note = question.getNote();
        addLabels(question.getLabels());
    }
}
