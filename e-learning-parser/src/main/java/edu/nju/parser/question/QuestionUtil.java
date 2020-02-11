package edu.nju.parser.question;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class QuestionUtil {


    static String URL = "http://localhost:8080/api/question";

    public static List<Question> getAllQuestions(){
        String res = MyHttpClient.sendGet(URL + "/list", "");
        List<Question> questions = JSON.parseArray(res, Question.class);
        return questions;
    }

    public static Question getQuestion(Integer id){
        String res = MyHttpClient.sendGet(URL + "/list", "?id="+id);
        Question question = JSON.parseObject(res, Question.class);
        return question;
    }

    public static String saveQuestion(Question question){
        String params = JSON.toJSONString(question);

        System.out.println("save question json: " + params);
        String res = MyHttpClient.sendPost(URL , params);
        return res;
    }
}
