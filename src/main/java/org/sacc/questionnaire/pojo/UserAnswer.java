package org.sacc.questionnaire.pojo;

import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/12/10 14:10
 */

@Data
public class UserAnswer {

    private Integer id;

    private String UUID;

    private String answers;

    private Integer score;
}
