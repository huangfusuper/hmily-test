package com.order.client;

import com.order.dto.InventoryDTO;
import org.springframework.stereotype.Component;

/**
 * @author xiaoyu(Myth)
 */
@Component
public class InventoryHystrix implements InventoryClient {

    @Override
    public Boolean decrease(InventoryDTO inventoryDTO) {
        return false;
    }

    @Override
    public Integer findByProductId(String productId) {
        return 0;
    }
}
