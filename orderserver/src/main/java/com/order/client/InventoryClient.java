

package com.order.client;

import com.order.dto.InventoryDTO;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface Inventory client.
 *
 * @author xiaoyu
 */
@FeignClient(value = "inventory-service")
public interface InventoryClient {

    /**
     * 库存扣减.
     *
     * @param inventoryDTO 实体对象
     * @return true 成功
     */
    @RequestMapping("/inventory-service/inventory/decrease")
    @Hmily
    Boolean decrease(@RequestBody InventoryDTO inventoryDTO);


    /**
     * 获取商品库存.
     *
     * @param productId 商品id
     * @return InventoryDO integer
     */
    @RequestMapping("/inventory-service/inventory/findByProductId")
    Integer findByProductId(@RequestParam("productId") String productId);

}
