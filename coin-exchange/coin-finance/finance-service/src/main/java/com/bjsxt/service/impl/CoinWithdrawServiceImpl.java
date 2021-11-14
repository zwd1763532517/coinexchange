package com.bjsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.CoinWithdraw;
import com.bjsxt.mapper.CoinWithdrawMapper;
import com.bjsxt.service.CoinWithdrawService;
@Service
public class CoinWithdrawServiceImpl extends ServiceImpl<CoinWithdrawMapper, CoinWithdraw> implements CoinWithdrawService{

}
