package edu.nju.parser.core;

import edu.nju.parser.common.Paragraph;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DocxConverterTest {

    @Test
    public void test() {
        String baseDir = DocxConverter.class.getResource("/").getPath();
        System.out.println(baseDir);
        DocxConverterConfig.DocxConverterConfigBuilder builder
                = DocxConverterConfig.builder(baseDir + "/demo.docx", baseDir + "/html");
        DocxConverter converter = new DocxConverter(builder.build());
        // Document document = converter.convert2Html();
        List<Paragraph> paragraphs = converter.convert2Paragraphs();
        for (Paragraph p: paragraphs) {
            System.out.println(p.getInnerText());
        }
    }

}