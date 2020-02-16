package edu.nju.parser.util;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于提取文字中的关键词
 *
 * 字符串分词
 * */
public class TagUtil {

    /**
     * 获取段落文字分词
     *
     * @param sequence 文本
     * @return 分词列表
     * */
    public static List<String> extract(String sequence) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> tokens = segmenter.process(sequence, JiebaSegmenter.SegMode.INDEX);
        return tokens.stream()
                .map(t -> t.word).collect(Collectors.toList());
    }

}
