package edu.nju.parser.util;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.pdf.PdfDocument;

import java.io.File;

public class ConvertDocxUtil {
    public static File convert(String filename) {
        try {
            if (filename.endsWith(".pdf")) {
                return convertPdf(filename);
            } else if (filename.endsWith(".doc")) {
                return convertDoc(filename);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static File convertPdf(String path) {
        PdfDocument pdf = new PdfDocument(path);
        path = path.replace(".pdf", ".docx");
        pdf.saveToFile(path, com.spire.pdf.FileFormat.DOCX);
        return new File(path);
    }

    private static File convertDoc(String path) {
        Document doc = new Document();
        doc.loadFromFile(path);
        path = path.replace(".doc", ".docx");
        doc.saveToFile(path, FileFormat.Docx);
        return new File(path);
    }
}
