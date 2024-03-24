package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/land")
    public Result Land(@RequestBody User user) throws Exception{
        return userService.Land(user);
    }

    @PostMapping("/Register")
    public Result RegisterUser(@RequestBody User user){
        return userService.Register(user);
    }

    @PostMapping("/getUserInfo")
    public Result BytokenGetInfo(@RequestParam("token") String token){
        return userService.GetUserInfo(token);
    }
}
