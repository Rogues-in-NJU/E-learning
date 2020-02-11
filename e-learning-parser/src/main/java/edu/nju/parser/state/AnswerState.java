package edu.nju.parser.state;

/**
 * Created by alfred on 2020/2/11.
 */
public class AnswerState implements RecognitionState {
    //拥有一个识别对象，用于更新识别当前状态
    Recognition recognition;

    public AnswerState(Recognition Recognition) {
        this.recognition = Recognition;
    }

    @Override
    public void stem() {
        //状态可以从答案变为题干
        System.out.println("From Answer To Stem!");
        recognition.setState(recognition.getStemState());
    }

    @Override
    public void options() {
        //状态不能从答案变为选项
        System.out.println("Cannot From Answer To Option!");
    }

    @Override
    public void answer() {
        System.out.println("Still Answer Now!");
    }

    @Override
    public void notes() {
        //状态可以从答案变为讲义
        System.out.println("From Answer To Notes!");
        recognition.setState(recognition.getNotesState());
    }
}