package com.bjsxt.service;

import com.bjsxt.domain.CoinConfig;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CoinConfigService extends IService<CoinConfig>{


    /**
     *
     * @param coinId
     * @return
     */
    CoinConfig findByCoinId(Long coinId);

    /**
     *
     * @param coinConfig
     * @return
     */
    boolean updateOrSave(CoinConfig coinConfig);
}
