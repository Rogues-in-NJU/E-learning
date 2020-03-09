package edu.nju.parser;

import com.alibaba.fastjson.JSON;
import edu.nju.parser.ocr.FormulaOCRClient;
import edu.nju.parser.ocr.MathPixResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FormulaOCRClientTest {

    @Test
    public void test() throws IOException {
        String path = FormulaOCRClientTest.class.getResource("/").getPath();

        MathPixResult result = FormulaOCRClient.img2Latex(path + "/html/images/89532861-a3ec-407c-bef7-fb4d720ae9c5image8.png");
        System.out.println(JSON.toJSONString(result));
    }

}
