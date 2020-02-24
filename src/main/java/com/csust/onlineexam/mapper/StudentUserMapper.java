package com.csust.onlineexam.mapper;

import com.csust.onlineexam.entity.StudentUser;
import com.csust.onlineexam.entity.StudentUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentUserMapper {
    long countByExample(StudentUserExample example);

    int deleteByExample(StudentUserExample example);

    int deleteByPrimaryKey(String studentNo);

    int insert(StudentUser record);

    int insertSelective(StudentUser record);

    List<StudentUser> selectByExampleWithRowbounds(StudentUserExample example, RowBounds rowBounds);

    List<StudentUser> selectByExample(StudentUserExample example);

    StudentUser selectByPrimaryKey(String studentNo);

    int updateByExampleSelective(@Param("record") StudentUser record, @Param("example") StudentUserExample example);

    int updateByExample(@Param("record") StudentUser record, @Param("example") StudentUserExample example);

    int updateByPrimaryKeySelective(StudentUser record);

    int updateByPrimaryKey(StudentUser record);
}