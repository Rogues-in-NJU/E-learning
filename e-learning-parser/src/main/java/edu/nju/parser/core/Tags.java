package edu.nju.parser.core;

import java.util.*;

public class Tags {

    private Map<String, Tag> tagAnalyzers;

    public Tags() {
        tagAnalyzers = new HashMap<>();
    }

    public void addTagAnalyzer(String keyword, Tag tagAnalyzer) {
        this.tagAnalyzers.put(keyword, tagAnalyzer);
    }

    public Set<String> getTags(String keyword, String content) {
        Set<String> tags = new HashSet<>();
        Tag tag = tagAnalyzers.get(keyword);
        if (tag != null) {
            tags.addAll(tag.getTags(content));
        }
        return tags;
    }
}
