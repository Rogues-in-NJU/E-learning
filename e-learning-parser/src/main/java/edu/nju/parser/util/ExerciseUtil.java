package edu.nju.parser.util;

import edu.nju.parser.common.Paragraph;
import edu.nju.parser.enums.QuestionPartTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ExerciseUtil {

    public static QuestionPartTypeEnum getParagraphType(Paragraph paragraph) {
        String line = paragraph.getInnerText();
        if (isContent(line)) return QuestionPartTypeEnum.CONTENT;
        if (isAppend(line)) return QuestionPartTypeEnum.APPEND;
        if (isAnswer(line)) return QuestionPartTypeEnum.ANSWER;
        if (isNote(line)) return QuestionPartTypeEnum.NOTE;
        return QuestionPartTypeEnum.OTHER;
    }

    /**
     * 是否为章节标题，其一般包含标签信息
     * @param paragraph
     * @return
     */
    public static boolean isChapterTitle(String paragraph) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("\\s*[一二三四五六七八九十]\\s*[．\\.、]+(.|\\s)*");
        String pattern = "^(" + StringUtils.join(patterns, "|") + ")$";

        return Pattern.matches(pattern, paragraph);
    }

    /**
     * 是否为答案标题，其后内容统一为答案
     * @param paragraph
     * @return
     */
    public static boolean isAnswerTitle(String paragraph) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("\\s*参考答案\\s*");
        String pattern = "^(" + StringUtils.join(patterns, "|") + ")$";

        return Pattern.matches(pattern, paragraph);
    }

    /**
     * 是否为题干，其应该属于一道新题目
     * @param paragraph
     * @return
     */
    public static boolean isContent(String paragraph) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("\\s*\\d+\\s*[．\\.、]+(.|\\s)*");
        patterns.add("\\s*附加题\\s*[．\\.、：:]*(.|\\s)*");
        patterns.add("\\s*[【\\[（\\(]\\s*附加题\\s*[】\\]）\\)](.|\\s)*");
        String pattern = "^(" + StringUtils.join(patterns, "|") + ")$";

        return Pattern.matches(pattern, paragraph);
    }

    /**
     * 是否为子题干
     * 1.如果之前已有题干出现，则其应该属于之前题目的一部分
     * 2.如果之前没有题干出现，则其应该属于一道新题目
     * @param paragraph
     * @return
     */
    public static boolean isSubContent(String paragraph) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("\\s*[【\\[（\\(]\\s*\\d+\\s*[】\\]）\\)](.|\\s)*");
        patterns.add("\\s*[【\\[（\\(]?\\s*[①②③④⑤⑥⑦⑧⑨⑩]+\\s*[】\\]）\\)]?[．\\.、]?(.|\\s)*");
        String pattern = "^(" + StringUtils.join(patterns, "|") + ")$";

        return Pattern.matches(pattern, paragraph);
    }

    /**
     * 是否为附加内容，其应该属于之前题目的一部分
     * @param paragraph
     * @return
     */
    public static boolean isAppend(String paragraph) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("\\s*[A-D]\\s*[．\\.、](.|\\s)*");
        patterns.add("\\s*[【\\[（\\(]\\s*[A-D]\\s*[】\\]）\\)](.|\\s)*");
        String pattern = "^(" + StringUtils.join(patterns, "|") + ")$";

        return Pattern.matches(pattern, paragraph);
    }

    /**
     * 是否为标准答案，其应该属于之前题目的一部分
     * @param paragraph
     * @return
     */
    public static boolean isAnswer(String paragraph) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("(\\s*[解答析证明]+\\s*[：:](.|\\s)*)");
        patterns.add("(\\s*[【\\[（\\(]\\s*[解答析证明]+\\s*[】\\]）\\)](.|\\s)*)");
        String pattern = "^(" + StringUtils.join(patterns, "|") + ")$";

        return Pattern.matches(pattern, paragraph);
    }

    /**
     * 是否为讲义，其应该属于之前题目的一部分
     * @param paragraph
     * @return
     */
    public static boolean isNote(String paragraph) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("(\\s*[【\\[（\\(]?\\s*((分析)|(点评))\\s*[】\\]）\\)]?(.|\\s)*)");
        String pattern = "^(" + StringUtils.join(patterns, "|") + ")$";

        return Pattern.matches(pattern, paragraph);
    }

    // public static boolean isMultiAnswer(String paragraph) {
    //     List<String> patterns = new ArrayList<>();
    //     patterns.add("([0-9]+[．、]{1}((?![0-9]+[．、]).)+){2,}");
    //     String pattern = "^(" + StringUtils.join(patterns, "|") + ")$";
    //
    //     return Pattern.matches(pattern, paragraph);
    // }

}
