package com.mzl.zfbpaydemo2.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @EnumName :   ResultEnum
 * @Description: 结果枚举类
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:46
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    OK(0, "正常"),
    PARAMS_ERROR(1, "参数错误"),
    MONEY_ERROR(2, "金额错误"),
    ALIPAY_QUERY_ERROR(20, "支付宝查询错误"),
    ALIPAY_REFUND_ERROR(21, "支付宝退款失败"),
    ALIPAY_CLOSE_ERROR(22, "支付宝交易关闭失败"),
    ORDER_NOT_EXIST(30, "订单不存在"),
    ORDER_STATUS_NOT_SUPPORT(31, "订单状态不支持");

    private Integer code;
    private String msg;

    public static String getMessage(int code){
        for (ResultEnum resultEnum : ResultEnum.values()){
            if(resultEnum.getCode() == code){
                return resultEnum.msg;
            }
        }
        return null;
    }


}
