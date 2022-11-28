package org.sacc.questionnaire.vo;

import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/12/18 11:49
 */

@Data
public class QuestionVo {
    private Integer id;

    private String stem;

    /**
     * 0代表单选，1代表多选
     */
    private Integer type;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
}
