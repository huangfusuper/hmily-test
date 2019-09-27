package com.inventory.service.impl;

import com.inventory.dto.InventoryDTO;
import com.inventory.entrty.InventoryDO;
import com.inventory.mapper.InventoryMapper;
import com.inventory.service.InventoryService;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * InventoryServiceImpl.
 *
 * @author huangfu
 */
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {


    private final InventoryMapper inventoryMapper;

    @Autowired(required = false)
    public InventoryServiceImpl(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * t操作  执行扣减库存
     * @param inventoryDTO 库存DTO对象
     * @return
     */
    @Override
    @Hmily(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    @Transactional(rollbackFor = Exception.class)
    public Boolean decrease(InventoryDTO inventoryDTO) {
        //扣减库存
        inventoryMapper.decrease(inventoryDTO);

        return true;
        //throw new HmilyRuntimeException("dsaasdsa");
    }

    @Override
    public InventoryDO findByProductId(String productId) {
        //查询库存操作
        return inventoryMapper.findByProductId(productId);
    }


    /**
     * cc操作  成功   提交  将锁定锁定清空
     * @param inventoryDTO
     * @return
     */
    public Boolean confirmMethod(InventoryDTO inventoryDTO) {
        System.out.println("-------------------扣出成功了，将锁定库存清空------------------");
        final int rows = inventoryMapper.confirm(inventoryDTO);
        return true;
    }

    /**
     * c操作  失败  将锁定库存返还给库存
     * @param inventoryDTO
     * @return
     */
    public Boolean cancelMethod(InventoryDTO inventoryDTO) {
        System.out.println("-------------------扣出异常了，将锁定库存返还------------------");
        int rows = inventoryMapper.cancel(inventoryDTO);
        return true;
    }
}
