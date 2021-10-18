package com.bjsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.UserWallet;
import com.bjsxt.mapper.UserWalletMapper;
import com.bjsxt.service.UserWalletService;
@Service
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWallet> implements UserWalletService{

}
