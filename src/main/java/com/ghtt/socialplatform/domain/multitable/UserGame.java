package com.ghtt.socialplatform.domain.multitable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_game")
public class UserGame {
    private Long userId;
    private Long gameId;
    private Integer isPrivate; //default 0

    public UserGame(Long userId, Long gameId) {
        this.userId=userId;
        this.gameId=gameId;
    }
}
