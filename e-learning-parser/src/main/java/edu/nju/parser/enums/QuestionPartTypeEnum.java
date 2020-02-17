package edu.nju.parser.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QuestionPartTypeEnum {

    CONTENT(1, "题干"),
    APPEND(2, "附加"),
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
