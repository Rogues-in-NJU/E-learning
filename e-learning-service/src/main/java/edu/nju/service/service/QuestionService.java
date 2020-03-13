package edu.nju.service.service;

import edu.nju.service.entity.ImageLatex;
import edu.nju.service.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getQuestionList();

    Question getQuestion(Integer id);

    void saveQuestion(Question question);

    void saveQuestionImageLatex(ImageLatex image);
}
