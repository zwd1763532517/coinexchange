package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.AdminBank;
import com.baomidou.mybatisplus.extension.service.IService;
public interface AdminBankService extends IService<AdminBank>{

    /**
     * 银行卡列表查询
     * @param page
     * @param bankCard
     * @return
     */
    Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard);
}
