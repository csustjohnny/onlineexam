package com.csust.onlineexam.mapper;

import com.csust.onlineexam.entity.School;
import com.csust.onlineexam.entity.SchoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SchoolMapper {
    long countByExample(SchoolExample example);

    int deleteByExample(SchoolExample example);

    int deleteByPrimaryKey(Integer schoolCode);

    int insert(School record);

    int insertSelective(School record);

    List<School> selectByExampleWithRowbounds(SchoolExample example, RowBounds rowBounds);

    List<School> selectByExample(SchoolExample example);

    School selectByPrimaryKey(Integer schoolCode);

    int updateByExampleSelective(@Param("record") School record, @Param("example") SchoolExample example);

    int updateByExample(@Param("record") School record, @Param("example") SchoolExample example);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);
}