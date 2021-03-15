package com.mzl.seckilldemo1.rabbitmq;

import com.mzl.seckilldemo1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   SeckillMessage
 * @Description: 秒杀的消息体（消息队列的消息体）
 * @Author: mzl
 * @CreateDate: 2021/3/6 9:13
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {

    private User user;
    private long goodsId;
}
