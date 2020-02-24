package com.csust.onlineexam.mapper;

import com.csust.onlineexam.entity.AdminUser;
import com.csust.onlineexam.entity.AdminUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AdminUserMapper {
    long countByExample(AdminUserExample example);

    int deleteByExample(AdminUserExample example);

    int deleteByPrimaryKey(Integer adminNo);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    List<AdminUser> selectByExampleWithRowbounds(AdminUserExample example, RowBounds rowBounds);

    List<AdminUser> selectByExample(AdminUserExample example);

    AdminUser selectByPrimaryKey(Integer adminNo);

    int updateByExampleSelective(@Param("record") AdminUser record, @Param("example") AdminUserExample example);

    int updateByExample(@Param("record") AdminUser record, @Param("example") AdminUserExample example);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
}