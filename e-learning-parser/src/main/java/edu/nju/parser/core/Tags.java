package edu.nju.parser.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tags {

    private List<Tag> tagAnalyzers;

    public Tags() {
        tagAnalyzers = new ArrayList<>();
    }

    public void addTagAnalyzer(Tag tagAnalyzer) {
        this.tagAnalyzers.add(tagAnalyzer);
    }

    public Set<String> getTags(String content) {
        Set<String> tags = new HashSet<>();
        tagAnalyzers.forEach(a -> tags.addAll(a.getTags(content)));
        return tags;
    }

}
