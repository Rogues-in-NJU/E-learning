package edu.nju.parser.question;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestionUtilDemo {
    public static void main(String[] args){
        Question  question = new Question();
        question.setType(1);
        question.setContent("题目测试");
        Set<String> labels = new HashSet<>();
        labels.add("test");
        question.addLabels(labels);
        question.setAppend("{\"content\":\"json测试\",\"labels\":\"test\",\"type\":1}");

        System.out.println(QuestionUtil.saveQuestion(question));

        List<Question> questions = QuestionUtil.getAllQuestions();

        System.out.println(questions.size());
    }
}
