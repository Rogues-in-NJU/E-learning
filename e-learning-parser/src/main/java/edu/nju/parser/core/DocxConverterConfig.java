package edu.nju.parser.core;

import com.google.common.base.Preconditions;
import edu.nju.parser.core.plugin.Plugin;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DocxConverterConfig {

    public static class DocxConverterConfigBuilder {

        private DocxConverterConfig config;

        private DocxConverterConfigBuilder(String docxFilePath, String htmlFileOutputPath) {
            Preconditions.checkNotNull(docxFilePath);
            Preconditions.checkNotNull(htmlFileOutputPath);

            config = new DocxConverterConfig();

            if (htmlFileOutputPath.endsWith("/")) {
                htmlFileOutputPath = htmlFileOutputPath.substring(0, htmlFileOutputPath.length() - 1);
            }

            config.docxFilePath = docxFilePath;
            config.htmlFileName = docxFilePath.substring(docxFilePath.lastIndexOf("/") + 1,
                    docxFilePath.lastIndexOf(".") != -1 ? docxFilePath.lastIndexOf(".") : docxFilePath.length()) + ".html";
            config.htmlFileOutputDirPath = htmlFileOutputPath;
            config.imageDirPath = htmlFileOutputPath + "/images";
            config.imageTargetUri = "images";
            config.plugins = new LinkedList<>();
        }

        public DocxConverterConfigBuilder htmlFileName(String htmlFileName) {
            config.htmlFileName = htmlFileName;
            return this;
        }

        public DocxConverterConfigBuilder imageDirPath(String imageDirPath) {
            config.imageDirPath = imageDirPath;
            return this;
        }

        public DocxConverterConfigBuilder imageTargetUri(String imageTargetUri) {
            config.imageTargetUri = imageTargetUri;
            return this;
        }

        public DocxConverterConfigBuilder userCss(String userCss) {
            config.userCss = userCss;
            return this;
        }

        public DocxConverterConfigBuilder addPlugins(Plugin... plugins) {
            config.plugins.addAll(Arrays.asList(plugins));
            return this;
        }

        public DocxConverterConfig build() {
            return config;
        }

    }

    private DocxConverterConfig() {}

    /***
     * docx文件路径
     * */
    private String docxFilePath;

    /**
     * 生成的HTML文件输出文件夹路径
     * */
    private String htmlFileOutputDirPath;

    /**
     * 生成的HTML文件名
     * */
    private String htmlFileName;

    /**
     * 转为HTML时的css
     * */
    private String userCss
            = "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img,  ol, ul, li, table, caption, tbody, tfoot, thead, tr, th, td " +
            "{ margin: 0; padding: 0; border: 0;}" +
            "body {line-height: 1;} ";

    /**
     * 所有图片的保存路径
     * */
    private String imageDirPath;

    /**
     * HTML中图片超链接前缀
     * */
    private String imageTargetUri;

    private List<Plugin> plugins;

    public static DocxConverterConfigBuilder builder(String docxFilePath, String htmlFileOutputPath) {
        return new DocxConverterConfigBuilder(docxFilePath, htmlFileOutputPath);
    }

    public String getDocxFilePath() {
        return docxFilePath;
    }

    public String getHtmlFileName() {
        return htmlFileName;
    }

    public String getHtmlFileOutputDirPath() {
        return htmlFileOutputDirPath;
    }

    public String getUserCss() {
        return userCss;
    }

    public String getImageDirPath() {
        return imageDirPath;
    }

    public String getImageTargetUri() {
        return imageTargetUri;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }
}
