package edu.nju.parser.util;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

/**
 * Created by alfred on 2020/3/5.
 */
public class Doc2DocxSpireUtil {

    public static void convert(String path) {
        Document doc = new Document();
        doc.loadFromFile(path);
        doc.saveToFile(path.replace(".doc", ".docx"), FileFormat.Docx);
    }

}

