package edu.nju.parser.util;

import org.junit.jupiter.api.Test;

import java.util.List;

public class FileUtilsTest {

    @Test
    public void test() {
        List<String> files = FileUtil.getAllFileName("./");
        files.forEach(f -> {
            System.out.println(f);
            System.out.println(WordSplitUtil.extract(f).toString());
        });


    }

}
