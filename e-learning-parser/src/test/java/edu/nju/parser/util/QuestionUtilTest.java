package edu.nju.parser.util;

import org.junit.jupiter.api.Test;

public class QuestionUtilTest {

    @Test
    public void test() {
        String t = QuestionUtil.findSubSection("一、1. 测试");
        String t2 = QuestionUtil.findSubSection("一、测试");
        String t3 = QuestionUtil.findSubSection("1. 测试x");
        String t4 = QuestionUtil.findSubSection("1、 测试xxx");
        String t5 = QuestionUtil.findSubSection("一、1、 测试");
        System.out.println(t);
        System.out.println(t2);
        System.out.println(t3);
        System.out.println(t4);
        System.out.println(t5);

        System.out.println(t5.trim().replaceAll("[．.、]", " "));
    }

}
