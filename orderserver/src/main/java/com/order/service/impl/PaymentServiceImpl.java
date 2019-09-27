package com.order.service.impl;

import com.order.client.AccountClient;
import com.order.client.InventoryClient;
import com.order.dto.AccountDTO;
import com.order.dto.InventoryDTO;
import com.order.entity.Order;
import com.order.enums.OrderStatusEnum;
import com.order.mapper.OrderMapper;
import com.order.service.PaymentService;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author huangfu
 */
@Service
@SuppressWarnings("all")
public class PaymentServiceImpl implements PaymentService {

    private final OrderMapper orderMapper;
    private final AccountClient accountClient;
    private final InventoryClient inventoryClient;

    @Autowired(required = false)
    public PaymentServiceImpl(OrderMapper orderMapper,
                              AccountClient accountClient,
                              InventoryClient inventoryClient) {
        this.orderMapper = orderMapper;
        this.accountClient = accountClient;
        this.inventoryClient = inventoryClient;
    }

    /**
     * 成功则将订单状态修改为已支付   否则修改为支付失败
     * @param order 订单实体
     */
    @Override
    @Hmily(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void makePayment(Order order) {
        //将状态修改为支付中
        order.setStatus(OrderStatusEnum.PAYING.getCode());
        //修改订单状态
        orderMapper.update(order);
        //检查数据  用户
        final BigDecimal accountInfo = accountClient.findByUserId(order.getUserId());
        //检查数据    商品
        final Integer inventoryInfo = inventoryClient.findByProductId(order.getProductId());

        if (accountInfo.compareTo(order.getTotalAmount()) < 0) {
            throw new HmilyRuntimeException("余额不足！");
        }

        if (inventoryInfo < order.getCount()) {
            throw new HmilyRuntimeException("库存不足！");
        }
        //进入扣减库存操作
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setCount(order.getCount());
        inventoryDTO.setProductId(order.getProductId());
        inventoryClient.decrease(inventoryDTO);
        //扣除用户余额
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(order.getTotalAmount());
        accountDTO.setUserId(order.getUserId());
        accountClient.payment(accountDTO);
    }


    /**
     * 提交操作 证明以上数据全部正常
     * @param order
     */
    public void confirmOrderStatus(Order order) {
        System.out.println("--------------------订单操作成功---------------------");
        //将订单状态修改为支付成功
        order.setStatus(OrderStatusEnum.PAY_SUCCESS.getCode());
        //修改订单状态
        orderMapper.update(order);
    }

    /**
     * 中间步骤出现异常
     * @param order
     */
    public void cancelOrderStatus(Order order) {
        System.out.println("--------------------订单操作失败---------------------");
        //修改订单状态为支付失败
        order.setStatus(OrderStatusEnum.PAY_FAIL.getCode());
        //修改订单状态
        orderMapper.update(order);
    }
}
