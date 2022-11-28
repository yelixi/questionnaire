package org.sacc.questionnaire.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sacc.questionnaire.dao.UserDao;
import org.sacc.questionnaire.pojo.User;
import org.sacc.questionnaire.service.UserService;
import org.sacc.questionnaire.utils.JwtToken;

import org.sacc.questionnaire.utils.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * Created by 林夕
 * Date 2021/12/9 14:51
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private JwtToken jwtToken;

    @Override
    public String login(String username, String password) {
        String encodePassword = PasswordUtil.encode(password);
        User user = userDao.login(username,encodePassword);
        if(user==null)
            throw new RuntimeException("用户不存在");
        return jwtToken.generateToken(user);
    }

    @Override
    public Boolean register(String username, String password) {
        String encodePassword = PasswordUtil.encode(password);
        return userDao.register(username,encodePassword);
    }

    @Override
    public Boolean registerAll(MultipartFile multipartFile) throws IOException {
        InputStream file = multipartFile.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        for (int i=1;i<sheet.getLastRowNum();i++) {
            Row row = sheet.getRow(i);
            String username = convertCellValueToString(row.getCell(0));
            String jobNumber = convertCellValueToString(row.getCell(1));
            String encodePassword = PasswordUtil.encode(jobNumber);
            userDao.registerAll(username,encodePassword,jobNumber);
        }
        return true;
    }

    private static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }
}
