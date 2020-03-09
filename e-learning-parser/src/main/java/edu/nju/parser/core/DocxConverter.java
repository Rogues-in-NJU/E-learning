package edu.nju.parser.core;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import edu.nju.parser.common.ImageLatex;
import edu.nju.parser.common.Paragraph;
import edu.nju.parser.core.plugin.Plugin;
import edu.nju.parser.core.plugin.PostConvertPlugin;
import edu.nju.parser.core.plugin.PreConvertPlugin;
import edu.nju.parser.ocr.FormulaOCRClient;
import edu.nju.parser.ocr.MathPixResult;
import edu.nju.parser.util.ImgUtil;
import edu.nju.parser.util.XWPFUtils;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//import lombok.extern.slf4j.Slf4j;


//@Slf4j
public class DocxConverter {

    private DocxConverterConfig config;

    public DocxConverter(DocxConverterConfig config) {
        Preconditions.checkNotNull(config);
        this.config = config;
    }

    public Document convert2Html() throws Docx4JException, IOException {
        for (Plugin p : config.getPlugins()) {
            if (p instanceof PreConvertPlugin) {
                ((PreConvertPlugin) p).preConvert(config);
            }
        }

        docxFile2HtmlFile();

        for (Plugin p : config.getPlugins()) {
            if (p instanceof PostConvertPlugin) {
                ((PostConvertPlugin) p).postConvert(config);
            }
        }
        File htmlFile = new File(config.getHtmlFileOutputDirPath() + File.separator + config.getHtmlFileName());
        if (!htmlFile.exists()) {
            throw new FileNotFoundException(String.format("HTML文件 [%s] 未找到!", htmlFile.getCanonicalPath()));
        }
        return Jsoup.parse(htmlFile, StandardCharsets.UTF_8.name());
    }

    public List<Paragraph> convert2Paragraphs() throws Docx4JException, IOException {
        Document document = convert2Html();
        if (Objects.isNull(document)) {
            return null;
        }

        convertImages(document);

        Elements elements = document.select("p");
        List<Paragraph> paragraphs = elements.stream().map(e -> {
            List<ImageLatex> imageLatexes = convertLatex(e);
            Paragraph p = Paragraph.builder()
                    .innerText(Jsoup.parse(e.html()).text())
                    .html(e.html())
                    .html(e.outerHtml())
                    .originElement(e)
                    .imageLatexes(imageLatexes)
                    .build();
            return p;
        }).collect(Collectors.toList());
        return paragraphs;
    }

    public void docxFile2HtmlFile() throws Docx4JException, FileNotFoundException {
        System.out.println("Start convert docx file " + config.getDocxFilePath() +
                " to html file" + config.getHtmlFileOutputDirPath());
        try {
            XWPFUtils.convertImages(config.getDocxFilePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        os = new FileOutputStream(config.getHtmlFileOutputDirPath() + File.separator + config.getHtmlFileName());

        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);
        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
    }

    public void convertImages(Document document) {
        Elements elements = document.select("img");
        elements.forEach(e -> {
            try {
                String imageUrl = e.attr("src");
                if (imageUrl.endsWith(".wmf")) {
                    String imageName = imageUrl.substring(config.getImageTargetUri().length());
                    if (imageName.startsWith("/")) {
                        imageName = imageName.substring(1);
                    }
                    String imagePath = config.getImageDirPath() + "/" + imageName;
                    ImgUtil.wmf2Png(imagePath);
                    imageUrl = imageUrl.replace(".wmf", ".png");
                    e.attr("src", imageUrl);
                } else if (imageUrl.endsWith(".emf")) {
                    String imageName = imageUrl.substring(config.getImageTargetUri().length());
                    if (imageName.startsWith("/")) {
                        imageName = imageName.substring(1);
                    }
                    String imagePath = config.getImageDirPath() + "/" + imageName;
                    ImgUtil.emf2Png(imagePath);
                    imageUrl = imageUrl.replace(".emf", ".png");
                    e.attr("src", imageUrl);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public List<ImageLatex> convertLatex(Element element) {
        Elements elements = element.select("img");
        List<ImageLatex> imageLatexes = new LinkedList<>();
        elements.forEach(e -> {
            try {
                String imageUrl = e.attr("src");
                if (imageUrl.endsWith(".png") || imageUrl.endsWith(".jpg")) {
                    String imageName = imageUrl.substring(config.getImageTargetUri().length());
                    if (imageName.startsWith("/")) {
                        imageName = imageName.substring(1);
                    }
                    String imagePath = config.getImageDirPath() + "/" + imageName;
                    MathPixResult result = FormulaOCRClient.img2Latex(imagePath);
                    ImageLatex imageLatex = ImageLatex.builder()
                            .imageUrl(imageUrl)
                            .imageLatex(result.getLatex_styled()).build();
                    imageLatexes.add(imageLatex);

                    // System.out.println(JSON.toJSONString(imageLatex));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return imageLatexes;
    }

}
