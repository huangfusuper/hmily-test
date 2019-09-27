/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.account.service.impl;

import com.account.dto.AccountDTO;
import com.account.entrty.AccountDO;
import com.account.mapper.AccountMapper;
import com.account.service.AccountService;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.annotation.PatternEnum;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Account service.
 *
 * @author xiaoyu
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    /**
     * Instantiates a new Account service.
     *
     * @param accountMapper the account mapper
     */
    @Autowired(required = false)
    public AccountServiceImpl(final AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    /**
     * 扣减操作
     * @param accountDTO 参数dto
     * @return
     */
    @Override
    @Hmily(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    @Transactional(rollbackFor = Exception.class)
    public boolean payment(final AccountDTO accountDTO) {
        accountMapper.update(accountDTO);
        throw new RuntimeException("asdsadsa");
        //return true;
    }

    /**
     * 查询用户信息
     * @param userId 用户id
     * @return
     */
    @Override
    public AccountDO findByUserId(final String userId) {
        return accountMapper.findByUserId(userId);
    }

    /**
     * cc操作    冻结金额清空
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmMethod(final AccountDTO accountDTO) {
        final int rows = accountMapper.confirm(accountDTO);
        System.out.println("----------------------支付成功啦----------------------------");
        return Boolean.TRUE;
    }


    /**
     * cc操作  取消扣钱
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelMethod(final AccountDTO accountDTO) {
        System.out.println("----------------------------扣钱失败鸟-------------------------------------");
        final int rows = accountMapper.cancel(accountDTO);
        if (rows != 1) {
            throw new HmilyRuntimeException("取消扣减账户异常！");
        }

        System.out.println("----------------------------扣钱失败鸟-------------------------------------");
        return Boolean.TRUE;
    }
}
