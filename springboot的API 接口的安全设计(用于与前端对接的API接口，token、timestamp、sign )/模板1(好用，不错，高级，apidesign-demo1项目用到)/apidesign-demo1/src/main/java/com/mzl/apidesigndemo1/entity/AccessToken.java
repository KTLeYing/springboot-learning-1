package com.mzl.apidesigndemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.dnd.DropTarget;
import java.util.Date;

/**
 * @ClassName :   AccessToken
 * @Description: 可用有效的token实体类（主要用户设置token通过后的有效期，USER和API均可用）
 * @Author: mzl
 * @CreateDate: 2020/12/19 16:43
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {

    //用户token
    private String token;

    //token的有效时间
    private Date expire;
}
