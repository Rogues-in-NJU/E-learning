package edu.nju.parser.ocr;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class Mathpix {
    private String src;
    private List<String> formats;
    private List<String> ocr;
    private Boolean skip_recrop;

    public Mathpix(String src) {
        this.src = src;
        this.formats = new ArrayList<String>();
        formats.add("latex_styled");
        this.ocr = new ArrayList<String>();
        ocr.add("math");
        ocr.add("text");
        this.skip_recrop = true;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public List<String> getFormats() {
        return formats;
    }

    public void setFormats(List<String> formats) {
        this.formats = formats;
    }

    public List<String> getOcr() {
        return ocr;
    }

    public void setOcr(List<String> ocr) {
        this.ocr = ocr;
    }

    public Boolean getSkip_recrop() {
        return skip_recrop;
    }

    public void setSkip_recrop(Boolean skip_recrop) {
        this.skip_recrop = skip_recrop;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
