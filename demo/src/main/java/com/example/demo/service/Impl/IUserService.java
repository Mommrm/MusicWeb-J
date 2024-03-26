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


    @Override
    public Result SentCode(String str) {
        // 生成验证码
        String code = RandomUtil.randomNumbers(6);
        System.out.println("验证码为: { " + code + " }");
        // 保存验证码
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_USER_CODE + str,code);
        //是手机
        if (RegexUtils.isPhoneInvalid(str)) {
            // 发送验证码
            System.out.println("验证码为: { " + code + " }");
        }
        return Result.ok();
    }

    @Override
    public Result Land(UserDTO user) {
        // 验证用户手机号 或 邮箱
        String email = user.getUserEmail();
        String phone = user.getUserPhone();
        UserDTO userDTO = new UserDTO();
        if (RegexUtils.isPhoneInvalid(phone)|| RegexUtils.isEmailInvalid(email)) {
            return Result.fail("输入了错误的手机号或邮箱");
        }
        // 验证码校验
        String cachePhoneCode = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_USER_CODE + phone);
        String cacheEmailCode = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_USER_CODE + email);
        String code = user.getCode();
        if (!cachePhoneCode.equals(code) && !cacheEmailCode.equals(code)) {
            return Result.fail("错误的验证码");
        }
        // 是否有此用户
        userDTO.setUserPhone(userMapper.selectByPhone(phone));
        userDTO.setUserEmail(userMapper.selectByEmail(email));
        if (userDTO == null) {
            // 没有，创建用户
            addUser(userDTO);
        }
        // 保存用户到Redis
        // 1. 生成token
        String token = UUID.randomUUID().toString();
        // 2. 保存用户
        Map<String,Object> userMap = BeanUtil.beanToMap(userDTO,new HashMap<>(), CopyOptions.create()
                .setIgnoreNullValue(true)
                .setFieldValueEditor((fieldName,fieldValue) -> fieldValue.toString()));
        stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_USER_KEY + token,userMap);
        // 3. 设置token过期时间
        stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token,RedisConstants.LOGIN_USER_TTL ,TimeUnit.MINUTES);
        return Result.ok();
    }

    @Override
    public Result GetUserInfo(String userId) {
        // TODO 获取用户信息
        User user = userMapper.selectByUserId(userId);
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
    private boolean addUser(UserDTO user){
        try {
            String phone = user.getUserPhone();
            String email = user.getUserEmail();
            String userId = "u-" + UUID.randomUUID().toString();
            String playlistId = "p-" + UUID.randomUUID().toString();

            String userName = "User-" + RandomUtil.randomString(10);
            DateTime dateTime = DateTime.now();
            //添加用户
            userMapper.createUser(userId,userName,phone,email,dateTime);
            //创建默认歌单 默认的歌单号为用户Id
            playListMapper.createPlaylist(playlistId, "我的歌单", dateTime);
            playListMapper.createUPlaylist(userId,playlistId,"我的歌单");
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
}
