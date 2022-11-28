package org.sacc.questionnaire.service.impl;

import io.jsonwebtoken.Claims;
import org.sacc.questionnaire.dao.QuestionDao;
import org.sacc.questionnaire.dao.UserAnswerDao;
import org.sacc.questionnaire.enums.ResultEnum;
import org.sacc.questionnaire.exception.BusinessException;
import org.sacc.questionnaire.pojo.Question;
import org.sacc.questionnaire.pojo.UserAnswer;
import org.sacc.questionnaire.service.AnswerService;
import org.sacc.questionnaire.utils.JwtToken;
import org.sacc.questionnaire.vo.AnswerVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 林夕
 * Date 2021/12/10 14:33
 */

@Service
public class
AnswerServiceImpl implements AnswerService {

    @Resource
    private JwtToken jwtToken;

    @Resource
    private UserAnswerDao userAnswerDao;

    @Resource
    private QuestionDao questionDao;

    @Override
    public Integer uploadAnswer(List<AnswerVo> answers, String token) {
        final Claims claimByToken = jwtToken.getClaimByToken(token);
        String UUID = (String) claimByToken.get("UUID");
        final List<UserAnswer> select = userAnswerDao.select(UUID);
        if(select.size()>=3)
            throw new BusinessException(ResultEnum.IS_IN_LIMIT_TIMES);
        List<Question> questions = questionDao.selectAll();
        Map<Integer,String> questionMap = new HashMap<>();
        questions.forEach(question -> questionMap.put(question.getId(), question.getTrueAnswer()));
        int score = 0;
        for (AnswerVo answerVo : answers) {
            String trueAnswer = questionMap.get(answerVo.getQuestionId());
            if (trueAnswer.length() > 1 && trueAnswer.length() == answerVo.getAnswer().length()) {
                char[] a1 = new char[trueAnswer.length()];
                char[] a2 = new char[trueAnswer.length()];
                for (int i = 0; i < trueAnswer.length(); i++) {
                    a1[i] = trueAnswer.charAt(i);
                    a2[i] = answerVo.getAnswer().charAt(i);
                }
                Arrays.sort(a1);
                Arrays.sort(a2);
                if (Arrays.equals(a1, a2))
                    score+=5;
            } else if (trueAnswer.equals(answerVo.getAnswer())) {
                score+=5;
            }
        }
        String UUIDTimes = UUID + "-" + select.size();
        userAnswerDao.insert(UUID,answers.toString(),score,UUIDTimes);
        return score;
    }

    @Override
    public String clearAnswer(String token) {
        final Claims claimByToken = jwtToken.getClaimByToken(token);
        String UUID = (String) claimByToken.get("UUID");
        userAnswerDao.clearAnswer(UUID);
        return "清除成功";
    }
}
