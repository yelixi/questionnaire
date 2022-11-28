package org.sacc.questionnaire.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/12/12 9:59
 */

@Data
public class ExamVo {

    private Integer times;

    private List<Integer> scores;
}
