package com.example.demo.controller;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.service.Impl.IUserService;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private IUserService userService;
    @GetMapping("/phone/code")
    public Result SentCodeByPhone(@RequestParam("phone") String phone) throws Exception{
        return userService.SentCode(phone);
    }

    @GetMapping("/email/code")
    public Result SentCodeByEmail(@RequestParam("email") String email) throws Exception{
        return userService.SentCode(email);
    }

    @GetMapping("/land")
    public Result Land(@RequestBody UserDTO user) throws Exception{
        return userService.Land(user);
    }

    @PostMapping("/getUserInfo")
    public Result ByUserIdGetInfo(@RequestParam String userId){
        return userService.GetUserInfo(userId);
    }
    @PostMapping("/updataInfo")
    public Result updataUserInfo(@RequestBody User user){
        return userService.UpdateUserInfo(user);
    }
}
