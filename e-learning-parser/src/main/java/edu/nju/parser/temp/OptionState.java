package edu.nju.parser.temp;

/**
 * Created by alfred on 2020/2/11.
 */
public class OptionState implements RecognitionState {
    //拥有一个识别对象，用于更新识别当前状态
    Recognition recognition;

    public OptionState(Recognition Recognition) {
        this.recognition = Recognition;
    }

    @Override
    public void stem() {
        //状态可以从选项变为题干
        System.out.println("From Options To Stem!");
        recognition.setState(recognition.getStemState());
    }

    @Override
    public void options() {
        System.out.println("Still Options Now!");
    }

    @Override
    public void answer() {
        //状态可以从选项变为答案
        System.out.println("From Options To Answer!");
        recognition.setState(recognition.getAnswerState());
    }

    @Override
    public void notes() {
        //状态可以从选项变为讲义
        System.out.println("From Options To Notes!");
        recognition.setState(recognition.getNotesState());
    }
}