package edu.nju.parser.question;

public class Question {

    private Integer id;

    private Integer type;

    private String section;

    private String subSection;

    private String content;

    private String append;

    private String answer;

    private String note;

    private String labels;

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
        this.section = section;
    }

    public String getSubSection() {
        return subSection;
    }

    public void setSubSection(String subSection) {
        this.subSection = subSection;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
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
                "content :" + content + "\n" +
                "append :" + append + "\n" +
                "answer :" + answer + "\n" +
                "note :" + note + "\n" +
                "labels :" + labels + "\n" +
                "parentId :" + parentId + "\n"
                + "================================";
        return result;
    }
}
