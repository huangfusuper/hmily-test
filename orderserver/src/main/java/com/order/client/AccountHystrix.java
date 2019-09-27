
package com.order.client;

import com.order.dto.AccountDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * The type Account hystrix.
 *
 * @author xiaoyu(Myth)
 */
@Component
public class AccountHystrix implements AccountClient {

    @Override
    public Boolean payment(AccountDTO accountDO) {
        System.out.println("执行断路器。。" + accountDO.toString());
        return false;
    }

    @Override
    public BigDecimal findByUserId(String userId) {
        System.out.println("执行断路器。。");
        return BigDecimal.ZERO;
    }
}
