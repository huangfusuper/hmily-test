package com.inventory.entrty;

import lombok.Data;

import java.io.Serializable;

/**
 * InventoryDO.
 *
 * @author xiaoyu
 */
@Data
public class InventoryDO implements Serializable {

    private static final long serialVersionUID = 6957734749389133832L;

    private Integer id;

    /**
     * 商品id.
     */
    private String productId;

    /**
     * 总库存.
     */
    private Integer totalInventory;

    /**
     * 锁定库存.
     */
    private Integer lockInventory;

}
