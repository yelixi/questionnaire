package org.sacc.questionnaire.service.impl;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sacc.questionnaire.dao.QuestionDao;
import org.sacc.questionnaire.dao.UserAnswerDao;
import org.sacc.questionnaire.dao.UserDao;
import org.sacc.questionnaire.pojo.Question;
import org.sacc.questionnaire.pojo.User;
import org.sacc.questionnaire.pojo.UserAnswer;
import org.sacc.questionnaire.service.QuestionService;
import org.sacc.questionnaire.vo.ExamVo;
import org.sacc.questionnaire.vo.QuestionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/12/12 9:40
 */

@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionDao questionDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserAnswerDao userAnswerDao;

    @Override
    public List<QuestionVo> getExamQuestions(String UUID) {
        String username = userDao.getUsernameByUUID(UUID);
        if(username==null)
            return null;
        else {
            final List<Question> questions = questionDao.selectAll();
            List<QuestionVo> questionVos = new LinkedList<>();
            questions.forEach(question -> {
                QuestionVo questionVo = new QuestionVo();
                BeanUtils.copyProperties(question,questionVo);
                questionVos.add(questionVo);
            });
            return questionVos;
        }
    }

    @Override
    public ExamVo getExam(String UUID) {
        final List<UserAnswer> userAnswers = userAnswerDao.select(UUID);
        ExamVo examVo = new ExamVo();
        examVo.setTimes(userAnswers.size());
        List<Integer> scores = new LinkedList<>();
        userAnswers.forEach(userAnswer -> scores.add(userAnswer.getScore()));
        examVo.setScores(scores);
        return examVo;
    }

    @Override
    public Boolean getScore(HttpServletResponse resp) {
        List<User> userList = userDao.getAllUser();
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = workbook.createSheet("sheet");
        for (int i = 0; i < userList.size()+1; i++) {
            XSSFRow sheetRow = sheet.createRow(i);
            if(i==0){
                sheetRow.createCell(0).setCellValue("姓名");
                sheetRow.createCell(1).setCellValue("工号");
                sheetRow.createCell(2).setCellValue("最高分");
            }
            else {
                sheetRow.createCell(0).setCellValue(userList.get(i - 1).getUsername());
                sheetRow.createCell(1).setCellValue(userList.get(i - 1).getJobNumber());
                List<UserAnswer> userAnswerList = userAnswerDao.select(userList.get(i - 1).getUUID());
                if (userAnswerList.size() == 0)
                    sheetRow.createCell(2).setCellValue("未作答");
                else {
                    int max = 0;
                    for (UserAnswer userAnswer : userAnswerList) {
                        if (userAnswer.getScore() > max)
                            max = userAnswer.getScore();
                    }
                    sheetRow.createCell(2).setCellValue(max);
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
            byte[] bytes = os.toByteArray();
            resp.reset();
            //Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
            // Content-disposition其实可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，
            // 文件直接在浏览器上显示或者在访问时弹出文件下载对话框。
            resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("成绩"+".xlsx", StandardCharsets.UTF_8));
            resp.setContentType("application/x-msdownload");
            resp.setCharacterEncoding("utf-8");
            resp.getOutputStream().write(bytes);
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
}
