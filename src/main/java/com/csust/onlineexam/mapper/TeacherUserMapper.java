package com.csust.onlineexam.mapper;

import com.csust.onlineexam.entity.TeacherUser;
import com.csust.onlineexam.entity.TeacherUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TeacherUserMapper {
    long countByExample(TeacherUserExample example);

    int deleteByExample(TeacherUserExample example);

    int deleteByPrimaryKey(String teacherNo);

    int insert(TeacherUser record);

    int insertSelective(TeacherUser record);

    List<TeacherUser> selectByExampleWithRowbounds(TeacherUserExample example, RowBounds rowBounds);

    List<TeacherUser> selectByExample(TeacherUserExample example);

    TeacherUser selectByPrimaryKey(String teacherNo);

    int updateByExampleSelective(@Param("record") TeacherUser record, @Param("example") TeacherUserExample example);

    int updateByExample(@Param("record") TeacherUser record, @Param("example") TeacherUserExample example);

    int updateByPrimaryKeySelective(TeacherUser record);

    int updateByPrimaryKey(TeacherUser record);
}