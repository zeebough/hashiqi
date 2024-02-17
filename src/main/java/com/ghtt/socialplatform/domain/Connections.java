package com.ghtt.socialplatform.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("connections")
public class Connections {
    private Long userId;
    private String conType;
    private String value;

    public Connections(Long userId){
        this.userId=userId;
    }

    public Connections(Long userId,String conType){
        this.userId=userId;
        this.conType=conType;
    }
}
