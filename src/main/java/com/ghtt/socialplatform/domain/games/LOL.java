package com.ghtt.socialplatform.domain.games;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
//@TableName("lol")
@Component("lolEntity")
@TableName("lol")
public class LOL extends Game {
    @TableId(type=IdType.INPUT)
    private Long userId;
    private String sex;
    private String school;
    private String campus;
    private String college;
    private String major;
    private String zone;
    private String gameId;
    private String ranking;
    @TableField(value = "psn")
    private String positions;
    private String des;
}
