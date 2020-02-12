package edu.nju.parser.common;

import lombok.Builder;
import lombok.Data;
import org.jsoup.nodes.Element;

@Builder
@Data
public class Paragraph {

    /**
     * 去掉html标签的纯文本
     * */
    private String innerText;

    /**
     * 不包含段落最外层<p>标签
     * */
    private String html;

    /**
     * 包含段落最外层<p>标签
     * */
    private String outerHtml;

    /**
     * 原始html元素封装
     * */
    private Element originElement;

}
