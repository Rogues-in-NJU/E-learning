package edu.nju.parser.util;

import com.spire.doc.*;
import com.spire.doc.documents.Paragraph;
/**
 * Created by alfred on 2020/3/5.
 */
public class Doc2HtmlSpireUtil {
    public static void main(String[] args) {
        //TODO test doc2image with spire
        //create a Document object
        Document doc = new Document();
        //load a Word file
        doc.loadFromFile("sample.docx");
        //save the first page to html
        doc.SaveToFile("sample.html", FileFormat.Html);
    }

}

