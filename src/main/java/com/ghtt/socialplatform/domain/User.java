package com.ghtt.socialplatform.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User{
    @TableId(type = IdType.INPUT)
    private Long userId;
    private String nickName;
    private String realName;
    @TableField(value = "pwd",select = false)
    private String password;
    @TableField(select = false)
    private String phone;
    @TableLogic(delval = "1",value = "0")
    @TableField(select = false)
    private Integer deleted;
    private String sex;
    //private Integer height;
    //private Integer weight;
    private String country;
    private String province;
    private String city;
    private String district;
    private String school;
    private String campus;
    private String college;
    private String major;
    //private Integer isHomo;

    public User(String phone, String password,String nickName) {
        this.nickName = nickName;
        this.password = password;
        this.phone = phone;
    }

    public User(Long userId){
        this.userId=userId;
    }
}

