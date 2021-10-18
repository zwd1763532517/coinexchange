package com.bjsxt.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.SysUserLog;
import com.bjsxt.mapper.SysUserLogMapper;
import com.bjsxt.service.SysUserLogService;
@Service
public class SysUserLogServiceImpl extends ServiceImpl<SysUserLogMapper, SysUserLog> implements SysUserLogService{

}
