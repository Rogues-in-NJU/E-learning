package edu.nju.parser.enums;

public enum QuestionPartTypeEnum {

    STEM(1, "题干"),
    OPTION(2, "选项"),
    ANSWER(3, "答案"),
    NOTE(4, "讲义");

    private int code;
    private String name;


    public static QuestionPartTypeEnum of(int code) {
        for (QuestionPartTypeEnum item : QuestionPartTypeEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }
}
