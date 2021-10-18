package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.AdminBank;
import com.bjsxt.mapper.AdminBankMapper;
import com.bjsxt.service.AdminBankService;
@Service
public class AdminBankServiceImpl extends ServiceImpl<AdminBankMapper, AdminBank> implements AdminBankService{

    @Override
    public Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard) {
        return this.page(page,new LambdaQueryWrapper<AdminBank>()
                .like(StringUtils.isNotEmpty(bankCard),AdminBank::getBankCard,bankCard)
        );
    }
}
