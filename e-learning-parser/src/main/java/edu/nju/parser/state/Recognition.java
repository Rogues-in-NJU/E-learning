package edu.nju.parser.state;

/**
 * Created by alfred on 2020/2/11.
 */
public class Recognition {
    //定义出识别过程的所有状态
    private RecognitionState stemState;
    private RecognitionState optionsState;
    private RecognitionState answerState;
    private RecognitionState notesState;

    public Recognition() {
        stemState = new StemState(this);
        optionsState = new OptionState(this);
        answerState = new AnswerState(this);
        notesState = new NotesState(this);

    }

    //定义识别状态
    RecognitionState state;

    public void stem() {
        state.stem();
    }

    public void options() {
        state.options();
    }

    public void answer() {
        state.answer();
    }

    public void notes() {
        state.notes();
    }

    public void setState(RecognitionState state) {
        this.state = state;
    }

    public RecognitionState getStemState() {
        return stemState;
    }

    public RecognitionState getOptionsState() {
        return optionsState;
    }

    public RecognitionState getAnswerState() {
        return answerState;
    }

    public RecognitionState getNotesState() {
        return notesState;
    }

}
