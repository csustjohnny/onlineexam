package com.csust.onlineexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lenovo
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @GetMapping("/index")
    public String welcome(){
        return "teacher";
    }

    @PostMapping("/addStudent")
    public String addStudent(){
        return "";
    }
}
