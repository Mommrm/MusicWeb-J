package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


public class TokenGeneration {
    @Autowired
    private UserMapper userMapper;
    //7天后过期
    private static final long expire = 604800;
    //32位密钥
    private static String secret = "abcdfghiabcdfghiabcdfghiabcdfghi";

    public String GetToken(String userId){
        return generateToken(userId);
    }

    public User GetUserInfo(String token){
        try{
            return decrypt(token);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    //token加密算法
    private String generateToken(String userId) {
        System.out.println("userId: ----------------------------------" + userId);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * expire);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    //解密用户信息
    private User decrypt(String token){
        String userId = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).
                getBody().getSubject();
        System.out.println("userId: " + userId);
        return userMapper.findUser(userId);
    }
}
