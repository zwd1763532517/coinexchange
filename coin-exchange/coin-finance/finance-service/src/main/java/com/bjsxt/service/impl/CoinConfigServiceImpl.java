package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjsxt.domain.Coin;
import com.bjsxt.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.mapper.CoinConfigMapper;
import com.bjsxt.domain.CoinConfig;
import com.bjsxt.service.CoinConfigService;
@Service
public class CoinConfigServiceImpl extends ServiceImpl<CoinConfigMapper, CoinConfig> implements CoinConfigService{

    @Autowired
    private CoinService coinService;

    @Override
    public CoinConfig findByCoinId(Long coinId) {
        // coinConfig的id 和Coin的id 值是相同的
        return getOne(new LambdaQueryWrapper<CoinConfig>().eq(CoinConfig::getId, coinId));
    }

    @Override
    public boolean updateOrSave(CoinConfig coinConfig) {
        Coin coin = coinService.getById(coinConfig.getId());
        if(coin==null){
            throw new IllegalArgumentException("coin-Id不存在") ;
        }
        coinConfig.setCoinType(coin.getType());
        coinConfig.setName(coin.getName());
        // 如何是新增/修改呢?
        CoinConfig config = getById(coinConfig.getId());
        if (config == null) { // 新增操作
            return save(coinConfig);
        } else { // 修改操作
            return updateById(coinConfig);
        }
    }
}
