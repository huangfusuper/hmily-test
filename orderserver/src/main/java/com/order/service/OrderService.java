package com.order.service;

import java.math.BigDecimal;

/**
 * @author huangfu
 */
public interface OrderService {
    /**
     * 创建订单并且进行扣除账户余额支付，并进行库存扣减操作.
     *
     * @param count  购买数量
     * @param amount 支付金额
     * @return string
     */
    String orderPay(Integer count, BigDecimal amount);
}
