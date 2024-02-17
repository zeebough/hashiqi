package com.ghtt.socialplatform.domain.games;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component("mcEntity")
@TableName("mc")
public class MC extends Game{
    @TableId(type = IdType.INPUT)
    private Long userId;
    private String sex;
    private String school;
    private String campus;
    private String college;
    private String major;
    private String tags;
    private String des;
}
