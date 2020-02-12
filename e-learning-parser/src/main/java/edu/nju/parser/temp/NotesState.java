package edu.nju.parser.temp;

/**
 * Created by alfred on 2020/2/11.
 */
public class NotesState implements RecognitionState {
    //拥有一个识别对象，用于更新识别当前状态
    Recognition recognition;

    public NotesState(Recognition Recognition) {
        //通过构造函数引入电梯的实例化对象
        this.recognition = Recognition;
    }

    @Override
    public void stem() {
        //状态可以从讲义变为题干
        System.out.println("From Notes To Stem!");
        recognition.setState(recognition.getStemState());
    }

    @Override
    public void options() {
        //状态不能从讲义变为选项
        System.out.println("Cannot From Notes To Option!");
    }

    @Override
    public void answer() {
        //状态不能从讲义变为答案
        System.out.println("Cannot From Notes To Answer!");
    }

    @Override
    public void notes() {
        System.out.println("Still Notes Now!");
    }
}