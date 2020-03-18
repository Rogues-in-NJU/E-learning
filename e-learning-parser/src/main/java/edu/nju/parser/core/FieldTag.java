package edu.nju.parser.core;

import com.qianxinyao.analysis.jieba.keyword.TFIDFAnalyzer;
//import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

//@Slf4j
public class FieldTag implements Tag {

    private Set<String> originTags;
    private TFIDFAnalyzer analyzer = new TFIDFAnalyzer();
    private int topN = 10;

    public FieldTag(File f) {
        originTags = new HashSet<>();

        try {
            InputStream mathInputStream = new FileInputStream(f);
            InputStreamReader inputStreamReader = new InputStreamReader(mathInputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String line;
            while ((line = br.readLine()) != null) {
                originTags.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getTags(String content) {
        Set<String> tags = new HashSet<>();
        // 分词好像还不如全部比较一遍
        // List<Keyword> keywords = analyzer.analyze(content, 10);
        // keywords.forEach(w -> {
        //     if (originMathTags.contains(w.getName())) {
        //         tags.add(w.getName());
        //     }
        // });
        for (String t : originTags) {
            if (content.contains(t)) {
                tags.add(t);
            }
        }
        return tags;
    }

    public static void main(String[] args) {

    }
}
