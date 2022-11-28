package org.sacc.questionnaire.controller;

import lombok.extern.log4j.Log4j2;
import org.sacc.questionnaire.service.AnswerService;
import org.sacc.questionnaire.vo.AnswerVo;
import org.sacc.questionnaire.vo.RestResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/12/10 14:21
 */
@RestController
@Log4j2
public class AnswerController {

    @Resource
    private AnswerService answerService;

    @PostMapping("/uploadAnswer")
    public RestResult<Integer> uploadAnswer(@RequestBody List<AnswerVo> answers, @RequestHeader String token){
        return RestResult.success(answerService.uploadAnswer(answers,token));
    }

    @PostMapping("/clearAnswer")
    public RestResult<String> clearAnswer(@RequestHeader String token){
        return RestResult.success(answerService.clearAnswer(token));
    }

}
