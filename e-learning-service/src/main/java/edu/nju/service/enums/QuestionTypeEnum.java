package edu.nju.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionTypeEnum {

    XZ(1, "选择题"),
    TK(2, "填空题"),
    YY(3, "应用题"),
    QT(4, "其他题型");

    private int code;
    private String message;


    public static QuestionTypeEnum of(int code) {
        for (QuestionTypeEnum item : QuestionTypeEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }
}
