package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.Coin;
import com.bjsxt.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.mapper.AdminAddressMapper;
import com.bjsxt.domain.AdminAddress;
import com.bjsxt.service.AdminAddressService;
@Service
public class AdminAddressServiceImpl extends ServiceImpl<AdminAddressMapper, AdminAddress> implements AdminAddressService{


    @Autowired
    private CoinService coinService ;


    @Override
    public Page<AdminAddress> findByPage(Page<AdminAddress> page, Long coinId) {
        return page(page,new LambdaQueryWrapper<AdminAddress>().eq(coinId!=null ,AdminAddress::getCoinId,coinId));
    }

    /**
     * 重写save ,为了让我们的归集地址里面包含coinType
     * @param entity
     * @return
     */
    @Override
    public boolean save(AdminAddress entity) {
        Long coinId = entity.getCoinId();
        Coin coin = coinService.getById(coinId);
        if(coin==null){
            throw new IllegalArgumentException("输入的币种id错误") ;
        }
        String type = coin.getType();
        entity.setCoinType(type);
        return super.save(entity) ;
    }
}
