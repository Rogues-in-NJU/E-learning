package edu.nju.service.controller;


import edu.nju.service.common.ResponseResult;
import edu.nju.service.entity.Question;
import edu.nju.service.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.List;

@Slf4j
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
    public String addQuestion(@Valid @RequestBody String questionStr) {
        try {
            System.out.println("before decode:" + questionStr);
            String questionStrDecoded = URLDecoder.decode(questionStr, "utf-8");
            questionStrDecoded = questionStrDecoded.substring(0, questionStrDecoded.length() - 1);
            System.out.println("after  decode:" + questionStrDecoded);
            Question question = JSON.parseObject(questionStrDecoded, Question.class);
            int result = questionService.saveQuestion(question);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            return JSON.toJSONString(e);
        }
    }

}
