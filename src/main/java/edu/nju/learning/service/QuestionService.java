package edu.nju.learning.service;

import edu.nju.learning.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getQuestionList();

    Question getQuestion(Integer id);

    int saveQuestion(Question question);
}
