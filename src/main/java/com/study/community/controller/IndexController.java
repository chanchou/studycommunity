package com.study.community.controller;

import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        //如果访问有cookies ，则判断数据库中是否有记录，如果有，则写入session
        Cookie[] cookies = request.getCookies();
        if(cookies.length!=0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findbyToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        System.out.println("以获取cookies");
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
