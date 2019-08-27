package com.plato.mp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class User {
    @TableField(value = "userId")
    private Long userId;
    @TableField(value = "userName")
    private String userName;

    private String password;

    private String phone;
    //数据库中无该字段，使用transient排除
    private transient String mark1;
    //数据库中无该字段，使用static排除
    private static String mark2;
    //数据库中无该字段，使用exist排除
    @TableField(exist = false)
    private String mark3;
}
