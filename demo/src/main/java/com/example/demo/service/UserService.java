package com.example.demo.service;

import com.example.demo.entity.Result;
import com.example.demo.entity.User;

public interface UserService {

    public Result Land(User user);

    public Result Register(User user);

    public Result GetUserInfo(String token);
}
