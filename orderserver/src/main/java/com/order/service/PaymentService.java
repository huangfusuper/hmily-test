package com.order.service;

import com.order.entity.Order;

/**
 * @author huangfu
 */
public interface PaymentService {
    /**
     * 订单支付.
     *
     * @param order 订单实体
     */
    void makePayment(Order order);
}
