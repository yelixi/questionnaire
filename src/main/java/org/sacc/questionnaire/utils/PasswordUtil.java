package org.sacc.questionnaire.utils;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class PasswordUtil{
    public static String encode(String rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.getBytes(StandardCharsets.UTF_8)));
    }

}
