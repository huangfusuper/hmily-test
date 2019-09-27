package com.order.service.impl;

import com.order.entity.Order;
import com.order.enums.OrderStatusEnum;
import com.order.mapper.OrderMapper;
import com.order.service.OrderService;
import com.order.service.PaymentService;
import org.dromara.hmily.common.utils.IdWorkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huangfu
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    @Autowired(required = false)
    public OrderServiceImpl(OrderMapper orderMapper, PaymentService paymentService) {
        this.orderMapper = orderMapper;
        this.paymentService = paymentService;
    }
    /**
     * 创建订单并且进行扣除账户余额支付，并进行库存扣减操作.
     *
     * @param count  购买数量
     * @param amount 支付金额
     * @return string
     */
    @Override
    public String orderPay(Integer count, BigDecimal amount) {
        //构造商品对象
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setNumber(IdWorkerUtils.getInstance().buildPartNumber());
        //demo中的表里只有商品id为 1的数据
        order.setProductId("1");
        //订单状态未支付
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        //付款金额
        order.setTotalAmount(amount);
        //物品数量
        order.setCount(count);
        //demo中 表里面存的用户id为10000
        order.setUserId("10000");
        //保存订单，此时订单状态为未支付
        final int rows = orderMapper.save(order);
        //保存成功则进入支付流程
        if (rows > 0) {
            paymentService.makePayment(order);
        }
        return "success";
    }
}
