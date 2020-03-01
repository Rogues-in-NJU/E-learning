package edu.nju.parser.question;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class QuestionStorageUtil {


    static String URL = "http://123.57.9.228:8080/api/question";

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
        String params = "{}";
        try {
            params = URLEncoder.encode(JSON.toJSONString(question), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        System.out.println("save question json: " + params);
        String res = MyHttpClient.sendPost(URL , params);
        return res;
    }
}
