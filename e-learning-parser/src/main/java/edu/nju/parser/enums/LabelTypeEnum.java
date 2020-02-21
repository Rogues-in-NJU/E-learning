package edu.nju.parser.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LabelTypeEnum {

    PATH(0, "文件路径"),
    EXAM(1, "试卷标题"),
    CHAPTER(2, "章节标题");

    private int code;
    private String name;

    public static LabelTypeEnum of(int code) {
        for (LabelTypeEnum item : LabelTypeEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }
}
