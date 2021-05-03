package com.mei.jwt.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mei.jwt.entity.User;
import com.mei.jwt.service.UserService;
import com.mei.jwt.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public Map<String, Object> login(User user) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            User loginDB = userService.login(user);
            HashMap<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(loginDB.getId()));
            payload.put("username", loginDB.getUsername());
            //生成token
            String token = JWTUtils.getToken(payload);

            map.put("state", true);
            map.put("msg", "verify successful");
            map.put("token", token);//响应token
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 受保护的接口
     *
     * @return
     */
    @PostMapping("/user/test")
    public Map<String, Object> test(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        //处理业务逻辑
        String token = request.getHeader("token");  //获取token内的信息
        DecodedJWT verify = JWTUtils.verify(token);
        System.out.println(verify.getClaim("id"));
        System.out.println(verify.getClaim("username"));

        map.put("state", true);
        map.put("msg", "request successful");
        return map;
    }
}
