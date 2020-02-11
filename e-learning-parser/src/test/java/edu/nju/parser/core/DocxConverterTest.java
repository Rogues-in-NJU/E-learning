package edu.nju.parser.core;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

public class DocxConverterTest {

    @Test
    public void test() {
        String baseDir = DocxConverter.class.getResource("/").getPath();
        System.out.println(baseDir);
        DocxConverterConfig.DocxConverterConfigBuilder builder
                = DocxConverterConfig.builder(baseDir + "/demo.docx", baseDir + "/html");
        DocxConverter converter = new DocxConverter(builder.build());
        Document document = converter.convert2Html();
        System.out.println(document.toString());
    }

}
