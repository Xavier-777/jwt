package com.mei.jwt.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mei.jwt.utils.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头的令牌
        String token = request.getHeader("token");
        HashMap<String, Object> map = new HashMap<>();
        try {
            JWTUtils.verify(token);      //验证令牌
            return true;                //放行
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        }
        map.put("state", false);      //设置状态
        String json = new ObjectMapper().writeValueAsString(map);   //将map转化为json
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
