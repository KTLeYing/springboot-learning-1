package com.mzl.zfbpaydemo1.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.mzl.zfbpaydemo1.config.AlipayConfig;
import com.mzl.zfbpaydemo1.dao.OrderDao;
import com.mzl.zfbpaydemo1.entity.Order;
import com.mzl.zfbpaydemo1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName :   OrderServiceImpl
 * @Description: 订单业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/2/20 20:51
 * @Version: 1.0
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     *  从数据库查询所有的订单
     * @return
     */
    @Override
    public List<Order> findAllOrder() {
        return orderDao.findAllOrder();
    }

    /**
     * 通过商家订单号查询订单
     * @param outTradeNo
     * @return
     */
    @Override
    public Order findByOutTradeNo(String outTradeNo) {
        return orderDao.findByOutTradeNo(outTradeNo);
    }

    /**
     * 保存到数据库(更新订单信息)
     * @param order
     */
    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    /**
     * 支付
     * @param order
     * @param request
     * @param response
     */
    @Override
    public void pay(Order order, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(order);
        Float money = Float.valueOf(order.getCommodity().equals("商品1")?10 : order.getCommodity().equals("商品2")?20 : 30);

        String nowTime = String.valueOf(System.currentTimeMillis());
        System.out.println("SellerNo:" + nowTime);
        //将订单信息保存到数据库，等待支付成功回调再进行处理
        order.setMoney(money);
        order.setOutTradeNo(nowTime);
        orderDao.addOrder(order); //保存到数据库

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = nowTime;//采用时间戳当做订单号
        // 订单名称，必填
        String subject = new String(order.getCommodity());
        //付款金额，必填
        String total_mount = new String(String.valueOf(money));
        // 销售产品码（固定） 必填
        String product_code = "QUICK_WAP_WAY";
        //商品描述，可空
        String body = new String("商品测试");
        // 超时时间 可空
        String timeout_express = "2m";

        System.out.println("ddd");

        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.sign_type);
        AlipayTradeWapPayRequest alipayRequest=new AlipayTradeWapPayRequest();
        System.out.println("hhh");
        // 封装请求支付信息
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_mount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipayRequest.setBizModel(model);

        System.out.println("kkk");
        // 设置异步通知地址
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        System.out.println("ggg");
        // 设置同步地址
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        System.out.println("fff");
        // form表单生产
        String form = "";
        try{
            // 调用SDK生成表单
            form = client.pageExecute(alipayRequest).getBody();
            System.out.println("ttt");
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            System.out.println("ppp");
            //直接将完整的表单html输出到页面
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
