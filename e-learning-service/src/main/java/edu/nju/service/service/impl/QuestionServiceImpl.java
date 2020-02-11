package edu.nju.service.service.impl;

import edu.nju.learning.entity.Question;
import edu.nju.learning.repo.QuestionRepository;
import edu.nju.service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestionList() {
        return null;
    }

    @Override
    public Question getQuestion(Integer id) {
        return null;
    }

    @Override
    public int saveQuestion(Question question) {
        questionRepository.save(question);
        return 0;
    }
}
