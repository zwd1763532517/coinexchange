package com.bjsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.Sms;
import com.bjsxt.mapper.SmsMapper;
import com.bjsxt.service.SmsService;
@Service
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService{

}
