package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.CoinType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CoinTypeService extends IService<CoinType>{


    /**
     *
     * @param page
     * @param code
     * @return
     */
    Page<CoinType> findByPage(Page<CoinType> page, String code);

    /**
     *
     * @param status
     * @return
     */
    List<CoinType> listByStatus(Byte status);
}
