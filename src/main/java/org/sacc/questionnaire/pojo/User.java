package org.sacc.questionnaire.pojo;

import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/12/9 14:48
 */
@Data
public class User {

    private String UUID;

    private String username;

    private String password;

    private String jobNumber;
}
