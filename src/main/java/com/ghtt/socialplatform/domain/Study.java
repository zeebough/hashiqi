package com.ghtt.socialplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("study")
public class Study {
    @TableId(type = IdType.ASSIGN_ID)
    private Long recordId;
    private Long userId;
    private LocalDateTime issueTime;
    private String place;
    private LocalDateTime expectTime;
    @TableField(exist = false)
    private String expectTimeStr;
    @TableField(exist = false)
    private String sex;
    @TableField(exist = false)
    private String major;
    @TableField(exist = false)
    private String school;
    @TableField(exist = false)
    private String campus;
    @TableField(exist = false)
    private String college;
    private String subject;

    public Study(Long userId){
        this.userId=userId;
    }
}
