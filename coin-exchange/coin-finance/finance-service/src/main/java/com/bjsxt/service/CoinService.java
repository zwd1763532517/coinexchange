package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.Coin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CoinService extends IService<Coin>{


    /**
     *
     * @param name
     * @param type
     * @param status
     * @param title
     * @param walletType
     * @param page
     * @return
     */
    Page<Coin> findByPage(String name, String type, Byte status, String title, String walletType, Page<Coin> page);

    /**
     *
     * @param status
     * @return
     */
    List<Coin> getCoinsByStatus(Byte status);

    /**
     *
     * @param coinName
     * @return
     */
    Coin getCoinByCoinName(String coinName);
}
