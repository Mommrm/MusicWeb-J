package com.example.demo.service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
    Result SentCode(String str);

    Result Land(UserDTO user);

    Result GetUserInfo(String token);

    Result UpdateUserInfo(User user);
}
