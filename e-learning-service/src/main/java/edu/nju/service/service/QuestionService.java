package edu.nju.service.service;

import edu.nju.service.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getQuestionList();

    Question getQuestion(Integer id);

    int saveQuestion(Question question);
}
