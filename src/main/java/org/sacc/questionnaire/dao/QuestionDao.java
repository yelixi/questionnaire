package org.sacc.questionnaire.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.sacc.questionnaire.pojo.Question;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/12/10 14:56
 */

@Mapper
public interface QuestionDao {

    @Select("select * from question")
    List<Question> selectAll();
}
