package com.ghtt.socialplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("game_info")
public class GameInfo {
    @TableId(type = IdType.ASSIGN_ID)
    private Long gameId;
    private String gameName;

}
