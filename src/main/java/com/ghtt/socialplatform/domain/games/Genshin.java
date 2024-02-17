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
//@TableName("sp.genshin left join (select userId,sex,major,school,campus,college from sp.user) as b on genshin.userId=b.userId")
@Component("genshinEntity")
@TableName("genshin")
public class Genshin extends Game{
    @TableId(type= IdType.INPUT)
    private Long userId;
    private String sex;
    private String school;
    private String campus;
    private String college;
    private String major;
    private String zone;
    private String characters;
    private Integer worldLevel;
    private Integer adventureLevel;
    private Integer resourceSharable;
    private String des;

}
