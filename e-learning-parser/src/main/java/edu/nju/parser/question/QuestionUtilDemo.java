package edu.nju.parser.question;

import java.util.List;

public class QuestionUtilDemo {
    public static void main(String[] args){
        Question  question = new Question();
        question.setType(1);
        question.setContent("题目测试");
        question.setLabels("test");
        question.setAppend("{\"content\":\"json测试\",\"labels\":\"test\",\"type\":1}");

        System.out.println(QuestionUtil.saveQuestion(question));

        List<Question> questions = QuestionUtil.getAllQuestions();

        System.out.println(questions.size());
    }
}
