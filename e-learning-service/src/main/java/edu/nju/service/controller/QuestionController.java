package edu.nju.service.controller;


import com.alibaba.fastjson.JSONObject;
import edu.nju.service.common.ResponseResult;
import edu.nju.service.entity.ImageLatex;
import edu.nju.service.entity.Question;
import edu.nju.service.service.QuestionService;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    /**
     * 获取所有题目列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET, name = "获取所有题目")
    public String list() {
        List<Question> questionList = questionService.getQuestionList();
        return JSON.toJSONString(questionList);
    }

    /**
     * 获取题目详细信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, name = "获取一道题目")
    public String getOneQuestion(@PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);

        return JSON.toJSONString(question);
    }

    /**
     * 录入题目
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST, name = "录入题目")
    public void addQuestion(@Valid @RequestBody String questionStr) {
//        try {
        System.out.println("before decode:" + questionStr);
        String questionStrDecoded = "{}";
        try {
            questionStrDecoded = URLDecoder.decode(questionStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //todo 临时方案。百分号转码后有时出现一个=号。
            if('=' == (questionStrDecoded.charAt(questionStrDecoded.length() - 1))){
                questionStrDecoded = questionStrDecoded.substring(0, questionStrDecoded.length() - 1);
            }
            System.out.println("after  decode:" + questionStrDecoded);
            Question question = JSON.parseObject(questionStrDecoded,Question.class);
//            JSONObject jsonObject = JSON.parseObject(questionStrDecoded);
//            Question question = new Question();
//            question.setType((Integer)jsonObject.get("type"));
//            question.setContent((String)jsonObject.get("content"));
//            question.setAppend((String)jsonObject.get("append"));
//            question.setAnswer((String)jsonObject.get("answer"));
//            question.setNote((String)jsonObject.get("note"));
//            question.setLabels((String)jsonObject.get("labels"));
            questionService.saveQuestion(question);
//        } catch (Exception e) {
//            System.out.println(e);
//            return JSON.toJSONString(e);
//        }
    }

    /**
     * 录入题目图片的Latex文本
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/latex", method = RequestMethod.POST, name = "录入题目图片的Latex文本")
    public void addQuestionImageLatex(@Valid @RequestBody String imageStr) {
        System.out.println("before decode:" + imageStr);
        String imageStrDecoded = "{}";
        try {
            imageStrDecoded = URLDecoder.decode(imageStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //todo 临时方案。百分号转码后有时出现一个=号。
        if('=' == (imageStrDecoded.charAt(imageStrDecoded.length() - 1))){
            imageStrDecoded = imageStrDecoded.substring(0, imageStrDecoded.length() - 1);
        }
        System.out.println("after  decode:" + imageStrDecoded);
        ImageLatex imageLatex = JSON.parseObject(imageStrDecoded,ImageLatex.class);
        questionService.saveQuestionImageLatex(imageLatex);
    }

}
