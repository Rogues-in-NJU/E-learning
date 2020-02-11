package edu.nju.parser.ocr;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class MathPixResult {
    private Map<String, Double> detection_map;
    private List<String> detection_list;
    private Map<String, Double> position;
    private Double latex_confidence;
    private Double latex_confidence_rate;
    private String latex_styled;

    public Map<String, Double> getDetection_map() {
        return detection_map;
    }

    public void setDetection_map(Map<String, Double> detection_map) {
        this.detection_map = detection_map;
    }

    public List<String> getDetection_list() {
        return detection_list;
    }

    public void setDetection_list(List<String> detection_list) {
        this.detection_list = detection_list;
    }

    public Map<String, Double> getPosition() {
        return position;
    }

    public void setPosition(Map<String, Double> position) {
        this.position = position;
    }

    public Double getLatex_confidence() {
        return latex_confidence;
    }

    public void setLatex_confidence(Double latex_confidence) {
        this.latex_confidence = latex_confidence;
    }

    public Double getLatex_confidence_rate() {
        return latex_confidence_rate;
    }

    public void setLatex_confidence_rate(Double latex_confidence_rate) {
        this.latex_confidence_rate = latex_confidence_rate;
    }

    public String getLatex_styled() {
        return latex_styled;
    }

    public void setLatex_styled(String latex_styled) {
        this.latex_styled = latex_styled;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
