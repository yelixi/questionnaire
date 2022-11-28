package org.sacc.questionnaire.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.sacc.questionnaire.service.UserService;
import org.sacc.questionnaire.vo.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by 林夕
 * Date 2021/12/9 14:50
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public RestResult<String> login(String username, String password){
        return RestResult.success(userService.login(username,password));
    }

    @PostMapping("/register")
    public RestResult<Boolean> register(String username,String password){
        return RestResult.success(userService.register(username,password));
    }

    @PostMapping("/registerAll")
    public RestResult<Boolean> registerAll(MultipartFile multipartFile) throws IOException, InvalidFormatException {
        return RestResult.success(userService.registerAll(multipartFile));
    }
}
