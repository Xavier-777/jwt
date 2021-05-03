package com.mei.jwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    //密钥（盐值）
    private static final String SING = "asd@!#987@#!#";

    /**
     * 生成token，默认超时时间为7天
     *
     * @param map
     * @return
     */
    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);

        JWTCreator.Builder JWTBuilder = JWT.create();

        //payload
        map.forEach((k, v) -> {
            JWTBuilder.withClaim(k, v);
        });

        //超时时间
        JWTBuilder.withExpiresAt(instance.getTime());

        //sing
        String token = JWTBuilder.sign(Algorithm.HMAC256(SING));

        return token;
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SING)).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        return verify;
    }
}
