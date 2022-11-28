package org.sacc.questionnaire.service;

import org.sacc.questionnaire.pojo.Question;
import org.sacc.questionnaire.vo.ExamVo;
import org.sacc.questionnaire.vo.QuestionVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/12/12 9:40
 */
public interface QuestionService {

    List<QuestionVo> getExamQuestions(String UUID);

    ExamVo getExam(String UUID);

    Boolean getScore(HttpServletResponse resp);
}
