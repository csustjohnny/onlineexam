package com.csust.onlineexam.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        Map<String,String> map = new HashMap<>();
        if(e instanceof UsernameNotFoundException){
            map.put("error","不存在此用户，请确认后重试");
        } else if(e instanceof AuthenticationServiceException){
            map.put("error","验证码不正确");
        } else if(e instanceof BadCredentialsException){
            map.put("error","用户名或密码不正确，请确认后重试");
        }
        httpServletResponse.setContentType("text/json; charset=utf-8");
        String data = JSON.toJSONString(map);
        httpServletResponse.getWriter().write(data);
    }
}
