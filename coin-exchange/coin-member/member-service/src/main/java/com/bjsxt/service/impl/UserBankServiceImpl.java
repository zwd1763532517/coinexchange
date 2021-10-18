package com.bjsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.UserBank;
import com.bjsxt.mapper.UserBankMapper;
import com.bjsxt.service.UserBankService;
@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements UserBankService{

}
