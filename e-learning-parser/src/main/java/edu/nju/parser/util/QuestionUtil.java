package edu.nju.parser.util;

import edu.nju.parser.common.Paragraph;
import edu.nju.parser.enums.QuestionPartTypeEnum;
import edu.nju.parser.enums.TitleTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionUtil {

    public static void main(String[] args) {
        System.out.println(QuestionUtil.isAnswerTitle("【答案】"));
    }

    public static QuestionPartTypeEnum getParagraphType(Paragraph paragraph) {
        String line = paragraph.getInnerText();
        if (isContent(line)) return QuestionPartTypeEnum.CONTENT;
        if (isAppend(line)) return QuestionPartTypeEnum.APPEND;
        if (isAnswer(line)) return QuestionPartTypeEnum.ANSWER;
        if (isNote(line)) return QuestionPartTypeEnum.NOTE;
        if (isChapterTitle(line)) return QuestionPartTypeEnum.TITLE;
        if (isAnswerTitle(line)) return QuestionPartTypeEnum.TITLE;
        return QuestionPartTypeEnum.OTHER;
    }

    public static TitleTypeEnum getTitleType(Paragraph paragraph) {
        String line = paragraph.getInnerText();
        if (isChapterTitle(line)) return TitleTypeEnum.CHAPTER;
        if (isAnswerTitle(line)) return TitleTypeEnum.CHAPTER;
        return TitleTypeEnum.EXAM;
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
     * 将answer放在answerTitle之前，可以排除一些情况
     * @param paragraph
     * @return
     */
    public static boolean isAnswerTitle(String paragraph) {
        List<String> patterns = new ArrayList<String>();
        patterns.add(".*参\\s*考\\s*答\\s*案.*");
        patterns.add(".*试\\s*题\\s*解\\s*析.*");
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
        patterns.add("\\s*附\\s*加\\s*题\\s*[．\\.、：:]*(.|\\s)*");
        patterns.add("\\s*[【\\[（\\(]\\s*附\\s*加\\s*题\\s*[】\\]）\\)](.|\\s)*");
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
        patterns.add("(\\s*[【\\[（\\(]?\\s*((分\\s*析)|(点\\s*评)|(评\\s*价))\\s*[】\\]）\\)]?(.|\\s)*)");
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

    public static String findSections(String content) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("([一二三四五六七八九十]+[．\\.、]{1}){0,1}[0-9]+[．\\.、]{1}");
        String pattern = "^(" + StringUtils.join(patterns, "|") + ")";

        Matcher matcher = Pattern.compile(pattern).matcher(content);
        if (!matcher.find()) {
            return null;
        } else {
            return matcher.group();
        }
    }

}
