package com.csust.onlineexam.service;

import com.csust.onlineexam.entity.*;
import com.csust.onlineexam.mapper.AdminUserMapper;
import com.csust.onlineexam.mapper.StudentUserMapper;
import com.csust.onlineexam.mapper.TeacherUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Lenovo
 */
@Service
public class UserService implements UserDetailsService {

    private final AdminUserMapper adminUserMapper;
    private final StudentUserMapper studentUserMapper;
    private final TeacherUserMapper teacherUserMapper;
    public static Integer USER_TYPE = 0;
    @Autowired
    public UserService(AdminUserMapper adminUserMapper,StudentUserMapper studentUserMapper,TeacherUserMapper teacherUserMapper) {
        this.adminUserMapper = adminUserMapper;
        this.teacherUserMapper = teacherUserMapper;
        this.studentUserMapper = studentUserMapper;
    }

    public void createOrUpdate(AdminUser adminUser){
        AdminUserExample example = new AdminUserExample();
        example.createCriteria()
                .andAdminNoEqualTo(adminUser.getAdminNo());
        List<AdminUser> adminUsers = adminUserMapper.selectByExample(example);
        if(adminUsers.size()==0){
            //插入
            adminUserMapper.insert(adminUser);
        } else {
            //更新用户昵称或密码
            AdminUser dbAdmin = adminUsers.get(0);
            AdminUser updateAdmin = new AdminUser();
            updateAdmin.setAdminName(adminUser.getAdminName());
            updateAdmin.setAdminPassword(adminUser.getAdminPassword());
            AdminUserExample adminUserExample = new AdminUserExample();
            adminUserExample.createCriteria()
                    .andAdminNameEqualTo(dbAdmin.getAdminName());
            adminUserMapper.updateByExampleSelective(updateAdmin,adminUserExample);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //学生
        if(USER_TYPE==0){
            StudentUserExample example = new StudentUserExample();
            example.createCriteria()
                    .andStudentNoEqualTo(s);
            List<StudentUser> studentUsers = studentUserMapper.selectByExample(example);
            if(studentUsers.size()==0){
                throw new UsernameNotFoundException("用户账号不存在");
            }

            return new User(studentUsers.get(0).getName(),studentUsers.get(0).getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_STUDENT"));
        //老师
        } else if (USER_TYPE==1){
            TeacherUserExample example = new TeacherUserExample();
            example.createCriteria()
                    .andTeacherNoEqualTo(s);
            List<TeacherUser> teacherUsers = teacherUserMapper.selectByExample(example);
            if(teacherUsers.size()==0){
                throw new UsernameNotFoundException("账户不存在");
            }
            return new User(teacherUsers.get(0).getTeacherName(),teacherUsers.get(0).getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_TEACHER"));
            //管理员
        }else {
            System.out.println(USER_TYPE);
            AdminUserExample example = new AdminUserExample();
            example.createCriteria()
                    .andAdminNameEqualTo(s);
            List<AdminUser> admins =  adminUserMapper.selectByExample(example);
            if(admins.size()==0){
                throw new UsernameNotFoundException("账户不存在");
            }
            return new User(s, admins.get(0).getAdminPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        }

    }

}
