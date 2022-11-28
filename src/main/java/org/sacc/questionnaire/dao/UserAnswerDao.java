package org.sacc.questionnaire.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.sacc.questionnaire.pojo.UserAnswer;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/12/10 14:45
 */
@Mapper
public interface UserAnswerDao {

    @Insert("insert into user_answer (UUID,answers,score,UUID_times) values (#{UUID},#{answers},#{score},#{UUIDTimes})")
    boolean insert(String UUID, String answers, Integer score,String UUIDTimes);

    @Select("select * from user_answer where UUID=#{UUID} order by id desc")
    List<UserAnswer> select(String UUID);

    @Delete("delete from user_answer where UUID = #{UUID}")
    void clearAnswer(String UUID);
}
