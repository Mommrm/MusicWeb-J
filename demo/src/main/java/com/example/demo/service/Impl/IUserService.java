package com.example.demo.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.PlayListMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.util.RedisConstants;
import com.example.demo.util.RegexUtils;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Service
public class IUserService implements UserService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlayListMapper playListMapper;
    String cacheCode = "";

    @Override
    public Result SentCode(String str) {
        // 生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 保存验证码
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_USER_CODE + str,code,RedisConstants.LOGIN_CODE_TTL,TimeUnit.SECONDS);
        //发送验证码
        if (!RegexUtils.isPhoneInvalid(str)) {
            //发往手机
            System.out.println("手机验证码为: { " + code + " }");
        }
        if(!RegexUtils.isEmailInvalid(str)){
            //发往邮箱
            System.out.println("邮箱验证码为: { " + code + " }" + str);
        }
        return Result.ok();
    }

    @Override
    public Result Land(UserDTO user) {
        String isPhoneOrEmail = "Phone";
        // 验证用户手机号 或 邮箱
        String email = user.getUserEmail();
        String phone = user.getUserPhone();
        UserDTO userDTO = new UserDTO();
        System.out.println("email: " + email + " phone: " + phone);
        if (RegexUtils.isPhoneInvalid(phone) && email == null) {
            return Result.fail("输入了错误的手机号");
        }
        if(RegexUtils.isPhoneInvalid(email) && phone == null){
            return Result.fail("输入了错误的邮箱号");
        }
        // 验证码校验
        if (email == null) {
            userDTO.setUserPhone(phone);
            isPhoneOrEmail = "Phone";
            cacheCode = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_USER_CODE + phone);
        }

        if(phone == null){
            userDTO.setUserEmail(email);
            isPhoneOrEmail = "Email";
            cacheCode = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_USER_CODE + email);
        }
        String code = user.getCode();
        if (!cacheCode.equals(code)) {
            return Result.fail("错误的验证码");
        }
        User phoneUser = userMapper.selectByPhone(phone);
        User emailUser = userMapper.selectByEmail(email);
        System.out.println("phoneUser: " + phoneUser + " emailUser: " + emailUser);
        // 是否有此用户 输入的是手机号但是没有查询到该手机的用户
        if (isPhoneOrEmail.equals("Phone") && phoneUser == null) {
            // 没有，创建用户
            System.out.println("没有该手机用户,创建用户!");
            userDTO = addUser(userDTO);
        }
        // 输入的是邮箱但是没有查询到该邮箱的用户
        if (isPhoneOrEmail.equals("Email") && emailUser == null) {
            // 没有，创建用户
            System.out.println("没有该邮箱用户,创建用户!");
            userDTO = addUser(userDTO);
        }
        System.out.println(userDTO);
        // 保存用户到Redis
        // 1. 生成token
        String token = UUID.randomUUID().toString();
        // 2. 保存用户
        Map<String,Object> userMap = BeanUtil.beanToMap(userDTO, String.valueOf(new HashMap<>()));
        stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_USER_KEY + token,userMap);
        // 3. 设置token过期时间
        stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token,RedisConstants.LOGIN_USER_TTL ,TimeUnit.MINUTES);
        return Result.ok();
    }

    @Override
    public Result GetUserInfo(String userId) {
        // TODO 获取用户信息
        User user = userMapper.selectByUserId(userId);
        System.out.println(user);
        if (user != null) {
            return Result.ok(user);
        }
        return Result.fail("获取用户信息失败");
    }

    @Override
    public Result UpdateUserInfo(User user) {
        boolean flag = userMapper.updateUserInfo(user);
        if (flag) {
            Result.ok();
        }
        return Result.fail("更新失败");
    }


    /**
     * 创建用户并创建对应歌单
     * @param user
     * @return
     */
    private UserDTO addUser(UserDTO user){
        UserDTO returnUserDTO = new UserDTO();
        try {
            DateTime now = DateTime.now();
            returnUserDTO.setUserRegisterDate(now);
            returnUserDTO.setUserPhone(user.getUserPhone());
            returnUserDTO.setUserEmail(user.getUserEmail());
            returnUserDTO.setUserId("u-" + UUID.randomUUID());
            returnUserDTO.setUserName("User-" + RandomUtil.randomString(10));

            String playlistId = "p-" + UUID.randomUUID().toString();
            //添加用户
            userMapper.createUser(returnUserDTO);
            //创建默认歌单 默认的歌单号为用户Id
            playListMapper.createPlaylist(playlistId, "我的歌单", now);
            playListMapper.createUPlaylist(returnUserDTO.getUserId(),playlistId,"我的歌单");
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        return returnUserDTO;
    }
}
