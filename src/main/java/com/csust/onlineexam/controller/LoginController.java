package com.csust.onlineexam.controller;

import com.csust.onlineexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author Lenovo
 */
@Controller
public class LoginController {
    private static Random r = new Random(System.currentTimeMillis());
    private static char[] chs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int NUMBER_OF_CHS = 4;
    private static final int IMG_WIDTH = 85;
    private static final int IMG_HEIGHT = 25;
    private static final int OFF_X = 15;
    private static final int OFF_Y = 18;
    private static final int MAX_ANGLE = 60;
    private static final int ROLE_STUDENT = 0;
    private static final int ROLE_TEACHER = 1;
    public static final int ENCODE_LENGTH = 16;
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login/check",method = RequestMethod.POST)
    public ModelAndView checkUser(@RequestParam( value = "userNo") String userNo,
                                  @RequestParam(value = "password") String password,
                                  @RequestParam(value = "userType") int userType,
                                  @RequestParam(value = "validateCode") String validateCode,
                                  HttpServletRequest request,
                                  Model model){
        System.out.println("进入登录验证界面");
        ModelAndView mav;
        Object iniCodeObject = request.getSession().getAttribute("checkCode");
        if(iniCodeObject==null) {
            mav = new ModelAndView("/login");
            return mav;
        }
        String iniCode = iniCodeObject.toString();
        if(iniCode.equals(validateCode)){
            User dbUser;
            //验证用户名
            if(userType== ROLE_STUDENT){
                //验证进入学生界面
                mav = new ModelAndView("/student/index");
                return mav;
            } else if (userType== ROLE_TEACHER){
                //验证进入教师界面
                mav = new ModelAndView("/teacher/main");
                return mav;
            } else {
                //进入管理员界面
                dbUser =  (User) userService.loadUserByUsername(userNo);
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(ENCODE_LENGTH);
                System.out.println(dbUser.getPassword());
                if(encoder.matches(password,dbUser.getPassword())){

                    mav = new ModelAndView("/admin/index");
                    mav.addObject("user",dbUser.getUsername());
                }
                else {
                    mav = new ModelAndView("/login");
                    mav.addObject("errorCode","用户名或密码不正确");
                }
                return mav;

            }
        } else{
            mav = new ModelAndView("/login");
            mav.addObject("errorCode","验证码不正确");
            return mav;
        }
    }
    @RequestMapping("login/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) {
        /*实例化BufferedImage*/
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        /*设置背景颜色*/
        Color backgroundColor = new Color(200, 200, 255);
        g.setColor(backgroundColor);
        /*图片边框*/
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);

        StringBuilder checkCode = new StringBuilder();
        int index;
        for (int i = 0; i < NUMBER_OF_CHS; i++) {
            index = r.nextInt(chs.length);
            /*随机一个颜色*/
            g.setColor(new Color(r.nextInt(88), r.nextInt(200), r.nextInt(150)));
            g.translate(OFF_X, OFF_Y);
            int randomangle = r.nextInt(MAX_ANGLE);
            g.rotate(Math.toRadians(randomangle));
            /*画出字符*/
            g.drawString(chs[index] + "", 0, 0);
            g.rotate(Math.toRadians(-randomangle));
            g.translate(0,-OFF_Y);
            /*验证码字符串*/
            checkCode.append(chs[index]);
        }
        g.translate(-OFF_X * NUMBER_OF_CHS,0);
        request.getSession().setAttribute("checkCode",checkCode.toString());
        try {
            ImageIO.write(image,"jpg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
