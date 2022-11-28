package org.sacc.questionnaire.pojo;

import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/12/10 14:07
 */
@Data
public class Question {

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

    private String trueAnswer;
}
