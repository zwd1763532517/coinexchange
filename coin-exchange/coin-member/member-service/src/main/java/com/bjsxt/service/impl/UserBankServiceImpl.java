package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.UserBank;
import com.bjsxt.mapper.UserBankMapper;
import com.bjsxt.service.UserBankService;
@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements UserBankService{

    @Override
    public Page<UserBank> findByPage(Page<UserBank> page, Long userId) {
        return this.page(page,new LambdaQueryWrapper<UserBank>()
                .eq(userId != null,UserBank::getUserId,userId)

        );
    }

    @Override
    public UserBank getUserBankByUserId(Long userId) {
        UserBank userBank = getOne(
                new LambdaQueryWrapper<UserBank>()
                        .eq(UserBank::getUserId, userId)
                        .eq(UserBank::getStatus, 1));
        return userBank;
    }

    @Override
    public boolean bindBank(Long userId, UserBank userBank) {
        userBank.setUserId(userId);
        return save(userBank);
    }
}
