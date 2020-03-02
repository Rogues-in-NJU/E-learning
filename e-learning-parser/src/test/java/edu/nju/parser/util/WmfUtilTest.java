package edu.nju.parser.util;

import org.junit.jupiter.api.Test;

public class WmfUtilTest {

    @Test
    public void test() {
        //
        // String path = WmfUtilTest.class.getResource("/").getPath();
        //
        // WmfUtil.convert(path + "/html/images/c5f01dc4-e052-44fa-8309-3d5405f28941image2.wmf");


        String test = "xxx.wmf";

        System.out.println(test.replace(".wmf", ".png"));
    }

}
