package edu.nju.parser.temp;

/**
 * Created by alfred on 2020/2/11.
 * 定义题目识别的状态：题干、选项、答案、讲义
 */
public interface RecognitionState {
    void stem();

    void options();

    void answer();

    void notes();
}
