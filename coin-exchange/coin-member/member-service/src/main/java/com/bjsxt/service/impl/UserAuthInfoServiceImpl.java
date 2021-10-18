package com.bjsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.UserAuthInfo;
import com.bjsxt.mapper.UserAuthInfoMapper;
import com.bjsxt.service.UserAuthInfoService;
@Service
public class UserAuthInfoServiceImpl extends ServiceImpl<UserAuthInfoMapper, UserAuthInfo> implements UserAuthInfoService{

}
