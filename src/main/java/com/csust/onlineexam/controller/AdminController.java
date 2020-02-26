package com.csust.onlineexam.controller;

import com.csust.onlineexam.entity.*;
import com.csust.onlineexam.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lenovo
 */
@Controller
@RequestMapping("admin")
public class AdminController {


    private final StudentUserExMapper studentUserExMapper;
    private final InstitutionMapper institutionMapper;

    private final SchoolMapper schoolMapper;
    private final DepartmentMapper departmentMapper;
    private final ClassInfoMapper classInfoMapper;
    @Autowired
    public AdminController(StudentUserExMapper studentUserExMapper,
                           InstitutionMapper institutionMapper,
                           SchoolMapper schoolMapper, DepartmentMapper departmentMapper, ClassInfoMapper classInfoMapper) {
        this.studentUserExMapper = studentUserExMapper;
        this.institutionMapper = institutionMapper;
        this.schoolMapper = schoolMapper;
        this.departmentMapper = departmentMapper;
        this.classInfoMapper = classInfoMapper;
    }

    @GetMapping("/index")
    public String admin(){
        return "admin";
    }

    @PostMapping("/addAdmin")
    public void addAdmin(){

    }
    @GetMapping("/welcome")
    public String welcome(){
        return "admin_welcome";
    }

    @GetMapping("/student_management")
    public String student(){
        return "student_management";
    }

    @GetMapping("/institution_management")
    public String institution(){
        return "institution_management";
    }
    /**
     * 获取所有学生信息
     * @param page 页码
     * @param limit 每页显示内容数
     * @return 所有学生信息
     */
    @GetMapping("/getAllStudent")
    @ResponseBody
    public Map<String,Object> getAllStudent(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "limit", defaultValue = "10") int limit){
        Map<String,Object> message = new HashMap<>(6);
        message.put("code",0);
        message.put("data",studentUserExMapper.getUserInfo());
        return message;
    }

    /**
     * 获取所有学院专业班级信息
     * @return json数据
     */
    @GetMapping("/getAllInstitution")
    @ResponseBody
    public Map<String,Object> getAllInstitution(){
        Map<String,Object> message = new HashMap<>(6);
        List<Integer> schoolList = institutionMapper.getAllSchoolCode();
        List<InstitutionInfo> institutionInfoList = new ArrayList<>();
        for(Integer schoolCode : schoolList){
            institutionInfoList.add(institutionMapper.selectSchoolDepartmentInfoBySchoolCode(schoolCode));
        }
        message.put("data",institutionInfoList);
        return message;
    }

    /**
     * 添加节点
     * @param schoolCode 学院代码
     * @param schoolName 学院名称
     * @return 添加结果
     */
    @RequestMapping(value = "/addSchool", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Boolean> addSchool(@RequestParam("schoolCode") String schoolCode,
                                        @RequestParam("schoolName") String schoolName){
        Map<String,Boolean> message = new HashMap<>(6);
        message.put("success",true);

        try {
            School school = new School();
            school.setSchoolName(schoolName);
            schoolMapper.insert(school);
        }catch (Exception e){
            message.put("success",false);
        }
        return message;
    }

    /**
     * 添加节点.
     * @param type 父节点类型. 0：根节点，1：学院，2：专业，3：班级
     * @param parentId 父节点id.
     * @param childName 子节点名称.
     * @param classNo 班级编号（如1603）.
     * @return 添加的节点生成的主键id
     */
    @RequestMapping(value = "/addNode",method = RequestMethod.POST)
    @ResponseBody
    public int addNode(@RequestParam("type") int type,
                       @RequestParam("id") int parentId,
                       @RequestParam("childName") String childName,
                      // @RequestParam("childId") int childId,
                       @RequestParam(value = "classNo", required = false) String classNo){
        int id = 0;
        //添加学院
        if(type==0){
            School school = new School();
            school.setSchoolName(childName);
            schoolMapper.insertSelective(school);
            id = school.getSchoolCode();
        //添加专业
        } else if(type==1){
            Department department = new Department();
            //department.setDepartmentId(childId);
            department.setDepartmentName(childName);
            department.setSchoolCode(parentId);
            departmentMapper.insert(department);
            id = department.getDepartmentId();
        } else if(type==2) {
            //添加班级
            ClassInfo classInfo = new ClassInfo();
            classInfo.setClassName(childName);
            classInfo.setDepartmentId(parentId);
            classInfo.setClassNo(classNo);
            classInfoMapper.insert(classInfo);
            id = classInfo.getClassId();
        }
        return id;
    }

    /**
     * 更新节点
     * @param id 更新Id
     * @param type 1：院系，2：专业，3：班级
     * @param name 新名称
     * @return 更新结果
     */
    @RequestMapping("/updateNode")
    @ResponseBody
    public int updateNode(@RequestParam("id") int id,
                          @RequestParam("type") int type,
                          @RequestParam("name") String name,
                          @RequestParam(value = "chassNo", required = false) String classNo){
        int code = 0;
        //更新学院名称
        if(type == 1){
            School school = new School();
            school.setSchoolName(name);
            school.setSchoolCode(id);
            code = schoolMapper.updateByPrimaryKeySelective(school);
        } else if(type==2){
            //更新专业名称
            Department department = new Department();
            department.setDepartmentName(name);
            department.setDepartmentId(id);
            code = departmentMapper.updateByPrimaryKeySelective(department);
        } else if(type==3) {
            ClassInfo classInfo = new ClassInfo();
            classInfo.setClassId(id);
            classInfo.setClassName(name);
            classInfo.setClassNo(classNo);
            classInfoMapper.updateByPrimaryKeySelective(classInfo);
        }
        return code;
    }

    @RequestMapping("/deleteNode")
    @ResponseBody
    public Map<String,String> deleteNode(@RequestParam("id") int id,
                          @RequestParam("type") int type){
        Map<String,String> message = new HashMap<>(1);
        int code = -1;
        if(type == 1){
            //删除学院节点
            code = schoolMapper.deleteByPrimaryKey(id);
        } else if(type==2){
            //删除专业节点
            code = departmentMapper.deleteByPrimaryKey(id);
        } else if(type==3) {
            //删除班级
            code = classInfoMapper.deleteByPrimaryKey(id);
        }
        if(code == 1){
           message.put("success","删除成功");
        } else {
            message.put("error","删除失败");
        }
        return message;
    }
}
