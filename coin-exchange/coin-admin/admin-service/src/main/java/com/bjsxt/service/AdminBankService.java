package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.AdminBank;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjsxt.dto.AdminBankDto;

import java.util.List;

public interface AdminBankService extends IService<AdminBank>{

    /**
     * 银行卡列表查询
     * @param page
     * @param bankCard
     * @return
     */
    Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard);

    /**
     *
     * @return
     */
    List<AdminBankDto> getAllAdminBanks();
}
