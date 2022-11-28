package org.sacc.questionnaire.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by 林夕
 * Date 2021/12/9 14:50
 */
public interface UserService {

    String login(String username,String password);

    Boolean register(String username,String password);

    Boolean registerAll(MultipartFile multipartFile) throws IOException, InvalidFormatException;
}
