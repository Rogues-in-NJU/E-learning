package edu.nju.parser.core;

import com.google.common.base.Preconditions;
import edu.nju.parser.core.plugin.Plugin;
import edu.nju.parser.core.plugin.PostConvertPlugin;
import edu.nju.parser.core.plugin.PreConvertPlugin;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DocxConverter {

    private DocxConverterConfig config;

    public DocxConverter(DocxConverterConfig config) {
        Preconditions.checkNotNull(config);
        this.config = config;
    }

    public Document convert2Html() {
        for (Plugin p : config.getPlugins()) {
            if (p instanceof PreConvertPlugin) {
                ((PreConvertPlugin) p).preConvert(config);
            }
        }
        try {
            docxFile2HtmlFile();
        } catch (Docx4JException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        for (Plugin p : config.getPlugins()) {
            if (p instanceof PostConvertPlugin) {
                ((PostConvertPlugin) p).postConvert(config);
            }
        }
        File htmlFile = new File(config.getHtmlFileOutputDirPath() + "/" + config.getHtmlFileName());
        if (!htmlFile.exists()) {
            return null;
        }
        try {
            return Jsoup.parse(htmlFile, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            return null;
        }
    }

    public void docxFile2HtmlFile() throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage wordMLPackage = Docx4J.load(new File(config.getDocxFilePath()));

        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
        htmlSettings.setImageDirPath(config.getImageDirPath());
        htmlSettings.setImageTargetUri(config.getImageTargetUri());
        htmlSettings.setWmlPackage(wordMLPackage);
        htmlSettings.setUserCSS(config.getUserCss());

        File htmlOutputDir = new File(config.getHtmlFileOutputDirPath());
        if (!htmlOutputDir.exists()) {
            htmlOutputDir.mkdir();
        }

        OutputStream os;
        os = new FileOutputStream(config.getHtmlFileOutputDirPath() + "/" + config.getHtmlFileName());

        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);
        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
    }

}
