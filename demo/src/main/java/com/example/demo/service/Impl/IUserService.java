package com.example.demo.service.Impl;

import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.PlayListMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.config.TokenGeneration;
import com.example.demo.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class IUserService implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlayListMapper playListMapper;
    private static String secret = "abcdfghiabcdfghiabcdfghiabcdfghi";


    @Override
    public Result Land(User user) {
        TokenGeneration tokenGeneration = new TokenGeneration();  //有关登录逻辑的实现
        User userInfo = new User();

        try{
            User i = userMapper.Land(user);  //验证用户
            if(i != null){
                user.setUserId(userMapper.findLandId(user.getAccount(),user.getPassword()));

                String token = tokenGeneration.GetToken(user.getUserId());
                userInfo.setUserId(userMapper.Land(user).getUserId());
                userInfo.setUserName(userMapper.Land(user).getUserName());
                userInfo.setAccount(user.getAccount());
                userInfo.setPassword(user.getPassword());
                userInfo.setToken(token);

                System.out.println(userInfo);
                return Result.ok(userInfo);
            }else{
                return Result.fail("登录失败");
            }
        }catch(Exception e){
            System.out.println(e);
            return Result.fail("登录异常");
        }
    }

    @Override
    public Result Register(User user) {
        String[] tempIDs = userMapper.findAllId();
        user.setUserId(String.valueOf(tempIDs[tempIDs.length-1] + 1)); //获取当前最后一位用户ID 往后退一位赋予给新注册用户
        //判断是否已经存在用户名和账号都相同的账户
        if(!userMapper.isExistEqualUser(user.getUserName() , user.getAccount()).isEmpty()){
            System.out.println("有用户");
            return Result.fail("已经拥有重复用户");
        }

        //满足密码要求
        if(Password_Check(user.getPassword())){
            try{
                if(addUser(user)){
                    return Result.ok();
                }
                else {
                    return Result.fail("注册失败");
                }
            }catch (Exception e){
                return Result.fail("注册异常");
            }
        }
        return Result.fail("注册失败");
    }

    @Override
    public Result GetUserInfo(String token) {
        User userInfo = decrypt(token);
        if (userInfo != null) {
            return Result.ok(userInfo);
        }
        return Result.fail("获取用户信息失败");
    }

    //解密用户信息
    private User decrypt(String token){
        return userMapper.findUser(Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).
                getBody().getSubject());
    }
    /**
     * 校验密码
     * @param password
     * @return
     */
    private boolean Password_Check(String password){
        int isEnglish = 0;//记录英文字符出现次数
        if(password.length() < 8) //密码小于8位
            return false;
        for(int i = 0;i<password.length();i++){
            char temp = password.charAt(i);
            if((temp >= 65 && temp <= 90) || (temp >= 97 && temp <= 122)) {
                isEnglish++;
            }
        }
        if(isEnglish < 2){ //英文字符少于两位
            return false;
        }
        //其余情况均为符合密码要求
        return true;
    }

    /**
     * 创建用户并创建对应歌单
     * @param user
     * @return
     */
    @Transactional
    private boolean addUser(User user){
        try {
            //添加用户
            userMapper.Register(user);
            //创建默认歌单 默认的歌单号为用户Id
            playListMapper.createPlaylist(user.getUserId(), user.getUserName() + "的歌单", user.getUserId() );
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
}
