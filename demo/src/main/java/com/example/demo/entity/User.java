package com.example.demo.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "music_user_info")
public class User {
    @TableId(value = "music_user_id",type = IdType.ASSIGN_UUID)
    private String userId;
    @TableField("music_user_name")
    private String userName;
    @TableField("music_user_phone")
    private String userPhone;
    @TableField("music_user_email")
    private String userEmail;
    @TableField("music_user_avatar")
    private String userAvatar;
    @TableField("music_user_attnum")
    private long userAttentionNum;
    @TableField("music_user_fannum")
    private long userFanNum;
    @TableField("music_user_sex")
    private String userSex;
    @TableField("music_user_brief")
    private String userBrief;
    @TableField("music_user_regisdate")
    private DateTime userRegisterDate;
}
