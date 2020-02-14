package edu.nju.parser.util;

import de.innosystec.unrar.exception.RarException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UnCompressUtilTest {

    @Test
    public void test() throws IOException, RarException {
        UnCompressUtil.unrar("/Users/hermc/Downloads/Demo.rar",
                UnCompressUtil.class.getResource("/").getPath() + "testrar");
    }

}
