package edu.nju.parser.state;

/**
 * Created by alfred on 2020/2/11.
 */
public class Test {
    public static void main(String[] args) {
        Recognition recognition = new Recognition();
        recognition.setState(new StemState(recognition));
        recognition.stem();
        recognition.options();
        recognition.answer();
        recognition.notes();
    }
}
