package com.mzl.zfbpaydemo1.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.mzl.zfbpaydemo1.config.AlipayConfig;
import com.mzl.zfbpaydemo1.entity.Order;
import com.mzl.zfbpaydemo1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @ClassName :   OrderController
 * @Description: 订单控制器
 * @Author: mzl
 * @CreateDate: 2021/2/20 20:47
 * @Version: 1.0
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 跳转到购买页面（首页）
     * @return
     */
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }

    /**
     * 跳转到订单信息页面
     * @return
     */
    @RequestMapping("/toOrderInfo")
    public String toOrderInfo(Model model){
        //从数据库查询所有的订单
        List<Order> orders = orderService.findAllOrder();
        model.addAttribute("orders", orders);
        return "orderInfo";
    }

    /**
     * 支付
     */
    @RequestMapping("/pay")
    public void pay(Order order, HttpServletRequest request, HttpServletResponse response){
        orderService.pay(order, request, response);
    }

    /**
     * 支付宝同步通知（同步回调）
     */
    @RequestMapping("/returnNotify")
    public String returnNotify(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        System.out.println("执行支付宝同步通知...");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iterator = requestParams.keySet().iterator(); iterator.hasNext();){
            String name = (String) iterator.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++){
                valueStr = (i == values.length - 1)? valueStr + values[i] : valueStr + values[i] + ",";
            }
            System.out.println(valueStr);
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verifyResult = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
        if(verifyResult){  //验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //该页面可做页面美工编辑
            System.out.println("验证成功");
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            //处理标记付款成功
            Order order = orderService.findByOutTradeNo(outTradeNo);
            if (StringUtils.isEmpty(order.getTradeNo())){
                order.setStatus(1);
                order.setTradeNo(tradeNo);
                //保存到数据库(更新订单信息)
                orderService.updateOrder(order);
            }

            //跳转到订单信息页面
            return "redirect:/toOrderInfo";
        }else {
            //该页面可做页面美工编辑
            System.out.println("验证失败");
            return "toIndex";//跳转到首页
        }
    }

    /**
     * 支付宝异步通知（异步回调）
     */
    @RequestMapping("/synNotify")
    public String synNotify(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        System.out.println("执行支付宝异步通知...");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iterator = requestParams.keySet().iterator(); iterator.hasNext();){
            String name = (String) iterator.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++){
                valueStr = (i == values.length - 1)? valueStr + values[i] : valueStr + values[i] + ",";
            }
            System.out.println(valueStr);
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verifyResult = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
        if (verifyResult){  //验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //处理标记付款成功
            Order order = orderService.findByOutTradeNo(outTradeNo);
            if (StringUtils.isEmpty(order.getTradeNo())){
                order.setStatus(1);//设置为支付成功
                order.setTradeNo(tradeNo);//设置支付宝交易号
                orderService.updateOrder(order);//保存到数据库
                System.out.println("异步回调验证成功");
            }
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if(tradeStatus.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (tradeStatus.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            return "success";	//请不要修改或删除
        }else {  //验证失败
            return "fail";
        }
//        return "synNotify";
    }


}
