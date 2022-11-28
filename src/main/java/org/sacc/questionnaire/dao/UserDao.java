package org.sacc.questionnaire.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.sacc.questionnaire.pojo.User;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/12/9 15:02
 */
@Mapper
public interface UserDao {

    @Select("select * from user where username = #{username} and password = #{password}")
    User login(String username,String password);

    @Insert("insert into user (username,password) values (#{username},#{password})")
    boolean register(String username,String password);

    @Select("select username from user where UUID = #{UUID} limit 1")
    String getUsernameByUUID(String UUID);

    @Select("select * from user")
    List<User> getAllUser();

    @Insert("insert into user (username,password,job_number) values (#{username},#{password},#{jobNumber})")
    boolean registerAll(String username,String password,String jobNumber);
}
