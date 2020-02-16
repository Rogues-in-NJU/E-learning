package edu.nju.parser.util;

import org.junit.jupiter.api.Test;

import java.util.List;

public class FileUtilsTest {

    @Test
    public void test() {
        List<String> files = FileUtil.getAllFileName("/Users/hermc/Documents/University/Lab/Report/慕测Web应用验收评价系统");
        files.forEach(f -> {
            System.out.println(f);
            System.out.println(TagUtil.extract(f).toString());
        });


    }

}
