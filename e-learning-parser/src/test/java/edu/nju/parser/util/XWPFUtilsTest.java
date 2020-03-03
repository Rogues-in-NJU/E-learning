package edu.nju.parser.util;

import edu.nju.parser.core.DocxConverter;
import org.junit.jupiter.api.Test;

import java.io.File;

public class XWPFUtilsTest {

    @Test
    public void test() throws Exception {
        String baseDir = DocxConverter.class.getResource(File.separator).getPath();
        // Document document = converter.convert2Html();

        File dir = new File(baseDir);
        // WordprocessingMLPackage wordprocessingMLPackage = WordprocessingMLPackage.load(new File(dir.getAbsolutePath() + File.separator + "初三自主招生教学案07：整式.docx"));

        XWPFUtils.convertImages(dir.getAbsolutePath() + File.separator + "初三自主招生教学案07：整式.docx");
    }


}
