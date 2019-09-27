package com.account.mapper;

import com.account.entrty.AccountDO;
import com.account.dto.AccountDTO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author huangfu
 */
@SuppressWarnings("all")
public interface AccountMapper {

    /**
     * 账户金额-付款金额
     * 锁定金额+付款金额
     * @param accountDTO the account dto
     * @return the int
     */
    @Update("update account set balance = balance - #{amount}," +
            " freeze_amount= freeze_amount + #{amount} ,update_time = now()" +
            " where user_id =#{userId}  and  balance > 0  ")
    int update(AccountDTO accountDTO);


    /**
     * Confirm int.
     *
     * @param accountDTO the account dto
     * @return the int
     */
    @Update("update account set " +
            " freeze_amount= freeze_amount - #{amount}" +
            " where user_id =#{userId}  and freeze_amount >0 ")
    int confirm(AccountDTO accountDTO);


    /**
     * Cancel int.
     *
     * @param accountDO the account do
     * @return the int
     */
    @Update("update account set balance = balance + #{amount}," +
            " freeze_amount= freeze_amount -  #{amount} " +
            " where user_id =#{userId}  and freeze_amount >0")
    int cancel(AccountDTO accountDTO);

    /**
     * Find by user id account do.
     *
     * @param userId the user id
     * @return the account do
     */
    @Select("select id,user_id,balance, freeze_amount from account where user_id =#{userId} limit 1")
    AccountDO findByUserId(String userId);
}
