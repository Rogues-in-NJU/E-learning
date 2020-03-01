package edu.nju.parser.core;

import com.qianxinyao.analysis.jieba.keyword.TFIDFAnalyzer;
//import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//@Slf4j
public class MathTag implements Tag {

    private Set<String> originMathTags;
    private TFIDFAnalyzer analyzer = new TFIDFAnalyzer();
    private int topN = 10;

    public MathTag() {
        originMathTags = new HashSet<>();
        File math = new File(MathTag.class.getResource("/math.csv").getPath());
        try {
            BufferedReader br = new BufferedReader(new FileReader(math));
            String line;
            while ((line = br.readLine()) != null) {
                originMathTags.add(line.trim());
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
        for (String t : originMathTags) {
            if (content.contains(t)) {
                tags.add(t);
            }
        }
        return tags;
    }

}
