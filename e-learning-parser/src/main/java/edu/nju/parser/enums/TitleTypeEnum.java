package edu.nju.parser.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TitleTypeEnum {

    EXAM(1, "试卷标题"),
    CHAPTER(2, "章节标题");

    private int code;
    private String name;

    public static TitleTypeEnum of(int code) {
        for (TitleTypeEnum item : TitleTypeEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }
}
