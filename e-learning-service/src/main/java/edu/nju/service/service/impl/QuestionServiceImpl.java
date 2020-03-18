package edu.nju.service.service.impl;

import edu.nju.service.entity.ImageLatex;
import edu.nju.service.entity.Question;
import edu.nju.service.repo.ImageRepository;
import edu.nju.service.repo.QuestionRepository;
import edu.nju.service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ImageRepository imageRepository;

    @Override
    public List<Question> getQuestionList()
    {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestion(Integer id) {
        return questionRepository.getOne(id);
    }

    @Override
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void saveQuestionImageLatex(ImageLatex image) {
        imageRepository.save(image);
    }
}
