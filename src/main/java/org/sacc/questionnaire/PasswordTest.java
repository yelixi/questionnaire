package org.sacc.questionnaire;

import org.sacc.questionnaire.utils.PasswordUtil;

/**
 * Created by 林夕
 * Date 2021/12/18 11:29
 */
public class PasswordTest {
    public static void main(String[] args) {
        final String bucketname = PasswordUtil.encode("20210032");
        System.out.println(bucketname);
    }
}
