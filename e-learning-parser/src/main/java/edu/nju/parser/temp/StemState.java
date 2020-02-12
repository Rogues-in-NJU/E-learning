package edu.nju.parser.temp;

/**
 * Created by alfred on 2020/2/11.
 */
public class StemState implements RecognitionState {
    //拥有一个识别对象，用于更新识别当前状态
    Recognition recognition;

    public StemState(Recognition Recognition) {
        this.recognition = Recognition;
    }

    @Override
    public void stem() {
        System.out.println("Still Stem Now!");
    }

    @Override
    public void options() {
        //状态可以从题干变为选项
        System.out.println("From Stem To Options!");
        recognition.setState(recognition.getOptionsState());
    }

    @Override
    public void answer() {
        //状态可以从题干变为答案
        System.out.println("From Stem To Answer!");
        recognition.setState(recognition.getAnswerState());
    }

    @Override
    public void notes() {
        //状态可以从题干变为讲义
        System.out.println("From Stem To Notes!");
        recognition.setState(recognition.getNotesState());
    }
}