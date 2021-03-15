package com.mzl.ticketgrabbing.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   Message
 * @Description: 信息的封装
 * @Author: mzl
 * @CreateDate: 2021/1/11 20:38
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private Integer ticketId;
    private Integer userId;

}
