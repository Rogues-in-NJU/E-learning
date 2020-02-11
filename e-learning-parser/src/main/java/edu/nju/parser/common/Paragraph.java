package edu.nju.parser.common;

import lombok.Builder;
import lombok.Data;
import org.jsoup.nodes.Element;

@Builder
@Data
public class Paragraph {

    private String innerText;

    private Element originElement;

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

}
