package com.ghtt.socialplatform.domain.multitable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_hold_blacklist")
public class UserHoldBlacklist {
    private Long holderId;
    private Long memberId;
}
