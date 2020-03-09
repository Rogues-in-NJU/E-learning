package edu.nju.parser.common;

import lombok.Builder;
import lombok.Data;
import org.jsoup.nodes.Element;

import java.util.List;

@Builder
@Data
public class Paragraph {

    /**
     * 去掉html标签的纯文本
     * */
    private String innerText;

    /**
     * 包含段落最外层<p>标签
     * */
    private String html;

    /**
     * 包含父标签
     * */
    private String outerHtml;

    /**
     * 原始html元素封装
     * */
    private Element originElement;

    /**
     * 存储图片src和对应图片分析出的latex
     * */
    private List<ImageLatex> imageLatexes;

}
