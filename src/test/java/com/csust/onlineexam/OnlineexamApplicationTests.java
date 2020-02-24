package com.csust.onlineexam;

import com.csust.onlineexam.entity.*;
import com.csust.onlineexam.mapper.ClassInfoMapper;
import com.csust.onlineexam.mapper.DepartmentMapper;
import com.csust.onlineexam.mapper.InstitutionMapper;
import com.csust.onlineexam.mapper.SchoolMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class OnlineexamApplicationTests {

    @Autowired
    private InstitutionMapper institutionMapper;

    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Test
    void contextLoads() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    //添加新院系
    @Test
    void test3(){
        School school = new School();
        school.setSchoolName("新院系");
        schoolMapper.insert(school);
    }
    @Test
    void test5(){

        Department department = new Department();
        department.setSchoolCode(57);
        department.setDepartmentName("测试3");
        departmentMapper.insert(department);
        System.out.println(department.getDepartmentId());
    }
    @Test
    void test4(){
        ClassInfo classInfo = new ClassInfo();
    }
    @Test
    void test2(){
        InstitutionInfo institutionInfo = institutionMapper.selectSchoolDepartmentInfoBySchoolCode(16);
        if(institutionInfo != null){
            System.out.println(institutionInfo.getSchool());
            List<DepartmentInfo> departmentInfoList = institutionInfo.getDepartmentInfoList();
            if( departmentInfoList != null){
                for(DepartmentInfo d : departmentInfoList){
                    System.out.println("专业");
                    System.out.println(d.getDepartment());
                    List<ClassInfo> classInfoList = d.getClassInfoList();
                    if(classInfoList != null){
                        System.out.println("班级：");
                        for (ClassInfo c : classInfoList){
                            System.out.println(c.getClassName());
                        }
                    }
                }
            }
        }
    }
}
