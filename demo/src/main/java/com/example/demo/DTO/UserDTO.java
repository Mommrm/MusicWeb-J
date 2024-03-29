package com.example.demo.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends User {

    @TableField(exist = false)
    private String code;

    @Override
    public String toString() {
        return super.toString() + "  UserDTO{" +
                "code='" + code + '\'' +
                '}';
    }
}
