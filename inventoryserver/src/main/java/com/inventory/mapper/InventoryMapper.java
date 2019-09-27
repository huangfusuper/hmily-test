package com.inventory.mapper;

import com.inventory.dto.InventoryDTO;
import com.inventory.entrty.InventoryDO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author xiaoyu
 */
@SuppressWarnings("all")
public interface InventoryMapper {


    /**
     * 开始下单  库存数量-下单数量
     *           锁定库存数量+下单数量
     * Decrease int.
     *
     * @param inventoryDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set total_inventory = total_inventory - #{count}," +
            " lock_inventory= lock_inventory + #{count} " +
            " where product_id =#{productId}  and  total_inventory >0  ")
    int decrease(InventoryDTO inventoryDTO);


    /**
     * 下单成功  用户取消锁定库存
     *
     * @param inventoryDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set " +
            " lock_inventory=  lock_inventory - #{count} " +
            " where product_id =#{productId}  and lock_inventory >0 ")
    int confirm(InventoryDTO inventoryDTO);


    /**
     * 清除锁定的值  添加商品库存
     * 用户下单失败  返回库存
     *
     * @param inventoryDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set total_inventory = total_inventory + #{count}," +
            " lock_inventory= lock_inventory - #{count} " +
            " where product_id =#{productId}  and lock_inventory >0 ")
    int cancel(InventoryDTO inventoryDTO);

    /**
     * 按产品ID查找库存。
     *
     * @param productId the product id
     * @return the inventory do
     */
    @Select("select id,product_id,total_inventory ,lock_inventory from inventory where product_id =#{productId}")
    InventoryDO findByProductId(String productId);

}
