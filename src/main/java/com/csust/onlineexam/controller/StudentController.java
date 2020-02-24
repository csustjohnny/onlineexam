package com.csust.onlineexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lenovo
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/index")
    public String student(){
        return "student";
    }

}
