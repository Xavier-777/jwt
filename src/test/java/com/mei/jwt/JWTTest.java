package com.mei.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

public class JWTTest {
    private String sing = "asd@!#987@#!#";          //盐值

    /**
     * 生成JWT
     */
    @Test
    public void contextLoads() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        String token = JWT.create()
                .withClaim("u_id", 123)     //payload
                .withClaim("username", "Xavier")
                .withExpiresAt(instance.getTime())      //超时时间
                .sign(Algorithm.HMAC256(sing));         //签名（加密）
        System.out.println(token);
    }

    /**
     * 验证JWT，并获取其信息
     */
    @Test
    public void verify(){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(sing)).build();
        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1X2lkIjoxMjMsImV4cCI6MTYyMDYzMDA2NCwidXNlcm5hbWUiOiJYYXZpZXIifQ.zTIeb7yJb1cKk1o7qCRiozcPafOdE7oln7cuJQZHcMA");
        Integer u_id = verify.getClaim("u_id").asInt();
        String username = verify.getClaim("username").asString();
        System.out.println(u_id);
        System.out.println(username);
    }
}
