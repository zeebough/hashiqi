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
@TableName("wang_zhe")
@Component("wang_zheEntity")
public class WangZhe extends Game{
    @TableId(type= IdType.INPUT)
    private Long userId;
    //@TableField(exist = false)
    private String sex;
    //@TableField(exist = false)
    private String school;
    //@TableField(exist = false)
    private String campus;
    //@TableField(exist = false)
    private String college;
    //@TableField(exist = false)
    private String major;
    private String zone;
    private String gameId;
    private String ranking;
    @TableField(value = "psn")
    private String positions;
    private String des;
}
