package com.example.demo.entity;

import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userAvatar;
    private long userAttentionNum;
    private long userFanNum;
    private String userBrief;
    private DateTime userRegisterDate;
}
