package edu.nju.learning;

import edu.nju.learning.entity.Question;
import edu.nju.learning.service.QuestionService;
import edu.nju.learning.service.impl.QuestionServiceImpl;

public class Demo {



    public static void main(String[] args) {
        QuestionService questionService = new QuestionServiceImpl();

        Question demoquestion = new Question();

        demoquestion.setType(1);
        demoquestion.setContent("test");
        questionService.saveQuestion(demoquestion);
    }

}
