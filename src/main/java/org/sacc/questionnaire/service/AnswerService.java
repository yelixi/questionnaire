package org.sacc.questionnaire.service;

import org.sacc.questionnaire.vo.AnswerVo;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/12/10 14:31
 */
public interface AnswerService {

    Integer uploadAnswer(List<AnswerVo> answers, String token);

    String clearAnswer(String token);
}
