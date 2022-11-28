package org.sacc.questionnaire.controller;

import io.jsonwebtoken.Claims;
import org.sacc.questionnaire.pojo.Question;
import org.sacc.questionnaire.service.QuestionService;
import org.sacc.questionnaire.utils.JwtToken;
import org.sacc.questionnaire.vo.ExamVo;
import org.sacc.questionnaire.vo.QuestionVo;
import org.sacc.questionnaire.vo.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/12/10 14:04
 */
@RestController
public class ExamController {

    @Resource
    private QuestionService questionService;

    @Resource
    private JwtToken jwtToken;

    @PostMapping("/getExamQuestions")
    public RestResult<List<QuestionVo>> getExamQuestions(@RequestHeader String token){
        final Claims claimByToken = jwtToken.getClaimByToken(token);
        String UUID = (String) claimByToken.get("UUID");
        return RestResult.success(questionService.getExamQuestions(UUID));
    }

    @PostMapping("/getExam")
    public RestResult<ExamVo> getExam(@RequestHeader String token){
        final Claims claimByToken = jwtToken.getClaimByToken(token);
        String UUID = (String) claimByToken.get("UUID");
        return RestResult.success(questionService.getExam(UUID));
    }

    @GetMapping("/getScore")
    public void getScore(HttpServletResponse resp){
        questionService.getScore(resp);
    }
}
