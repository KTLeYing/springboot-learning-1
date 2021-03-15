package com.mzl.websocketdemo2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Message
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/10 21:33
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String userId;

    private String toUserId;

    private String message;
}
