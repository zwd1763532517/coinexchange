package com.bjsxt.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.config.IdAutoConfiguration;
import com.bjsxt.domain.Sms;
import com.bjsxt.domain.UserAuthAuditRecord;
import com.bjsxt.dto.UserDto;
import com.bjsxt.geetest.GeetestLib;
import com.bjsxt.mappers.UserDtoMapper;
import com.bjsxt.model.dto.RegisterParamDTO;
import com.bjsxt.model.dto.UpdatePhoneParamDTO;
import com.bjsxt.model.dto.UserAuthFormDTO;
import com.bjsxt.service.SmsService;
import com.bjsxt.service.UserAuthAuditRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.User;
import com.bjsxt.mapper.UserMapper;
import com.bjsxt.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GeetestLib geetestLib;
    @Autowired
    private UserAuthAuditRecordService userAuthAuditRecordService ;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SmsService smsService;


    @Override
    public Page<User> findByPage(Page<User> page, String mobile, Long userId, String userName, String realName, Integer status,Integer reviewsStatus) {
        return this.page(page,new LambdaQueryWrapper<User>()
                .like(StringUtils.isNotEmpty(mobile),User::getMobile,mobile)
                .like(StringUtils.isNotEmpty(userName),User::getUsername,userName)
                .like(StringUtils.isNotEmpty(realName),User::getRealName,realName)
                .eq(userId != null,User::getId,userId)
                .eq(status != null,User::getStatus,status)
                .eq(reviewsStatus != null,User::getReviewsStatus,reviewsStatus)
        );
    }

    @Override
    public Page<User> findDirPage(Page<User> page, Long userId) {
        return page(page,new LambdaQueryWrapper<User>().eq(User::getDirectInviteid,userId));
    }

    @Override
    @Transactional
    public void updateUserAuthStatus(Long id, Byte authStatus, Long authCode, String remark) {
        log.info("开始修改用户的审核状态,当前用户{},用户的审核状态{},图片的唯一code{}",id,authStatus,authCode);
        User user = this.getById(id);
        if(user!=null){
//            user.setAuthStatus(authStatus); // 认证的状态
            user.setReviewsStatus(authStatus.intValue()); // 审核的状态
            updateById(user) ; // 修改用户的状态
        }
        UserAuthAuditRecord userAuthAuditRecord = new UserAuthAuditRecord();
        userAuthAuditRecord.setUserId(id);
        userAuthAuditRecord.setStatus(authStatus);
        userAuthAuditRecord.setAuthCode(authCode);
        String usrStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString() ;
        userAuthAuditRecord.setAuditUserId(Long.valueOf(usrStr)); // 审核人的ID
        //审核人的名称需要远程调用admin-service,没有事务
        userAuthAuditRecord.setAuditUserName("---------------------------");
        userAuthAuditRecord.setRemark(remark);
        userAuthAuditRecordService.save(userAuthAuditRecord) ;
    }

    @Override
    public User getById(Serializable id) {
        User user = super.getById(id);
        if (user == null) {
            throw new IllegalArgumentException("请输入正确的用户Id");
        }
        Byte seniorAuthStatus = null; // 用户的高级认证状态
        String seniorAuthDesc = "";// 用户的高级认证未通过,原因
        Integer reviewsStatus = user.getReviewsStatus(); // 用户被审核的状态 1通过,2拒绝,0,待审核"
        if (reviewsStatus == null) { //为null 时,代表用户的资料没有上传
            seniorAuthStatus = 3;
            seniorAuthDesc = "资料未填写";
        } else {
            switch (reviewsStatus) {
                case 1:
                    seniorAuthStatus = 1;
                    seniorAuthDesc = "审核通过";
                    break;
                case 2:
                    seniorAuthStatus = 2;
                    // 查询被拒绝的原因--->审核记录里面的
                    // 时间排序,
                    List<UserAuthAuditRecord> userAuthAuditRecordList = userAuthAuditRecordService.getUserAuthAuditRecordList(user.getId());
                    if (!CollectionUtils.isEmpty(userAuthAuditRecordList)) {
                        UserAuthAuditRecord userAuthAuditRecord = userAuthAuditRecordList.get(0);
                        seniorAuthDesc = userAuthAuditRecord.getRemark();
                    }
                    seniorAuthDesc = "原因未知";
                    break;
                case 0:
                    seniorAuthStatus = 0;
                    seniorAuthDesc = "等待审核";
                    break;

            }
        }
        user.setSeniorAuthStatus(seniorAuthStatus);
        user.setSeniorAuthDesc(seniorAuthDesc);
        return user;
    }




    @Override
    public Map<Long,UserDto> getBasicUsers(List<Long> ids, String userName, String mobile) {

        if (CollectionUtils.isEmpty(ids) && StringUtils.isEmpty(userName) && StringUtils.isEmpty(mobile)) {
            return Collections.emptyMap();
        }

        List<User> list = list(new LambdaQueryWrapper<User>()
                .in(!CollectionUtils.isEmpty(ids), User::getId, ids)
                .like(!StringUtils.isEmpty(userName), User::getUsername, userName)
                .like(!StringUtils.isEmpty(mobile), User::getMobile, mobile));
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        // 将user->userDto
        List<UserDto> userDtos = UserDtoMapper.INSTANCE.convert2Dto(list);
        Map<Long, UserDto> userDtoMaps = userDtos.stream().collect(Collectors.toMap(UserDto::getId, userDto -> userDto));
        return userDtoMaps;
    }

    @Override
    public boolean identifyVerify(Long id, UserAuthFormDTO userAuthForm) {
        User user = this.getById(id);
        Assert.notNull(user, "认证的用户不存在");
        Byte authStatus = user.getAuthStatus();
        if (!authStatus.equals((byte) 0)) {
            throw new IllegalArgumentException("该用户已经认证成功了");
        }
        // 执行认证
        checkForm(userAuthForm); // 极验
        // 实名认证
        boolean check = IdAutoConfiguration.check(userAuthForm.getRealName(), userAuthForm.getIdCard());
        if (!check) {
            throw new IllegalArgumentException("该用户信息错误,请检查");
        }

        // 设置用户的认证属性
        user.setAuthtime(new Date());
        user.setAuthStatus((byte) 1);
        user.setRealName(userAuthForm.getRealName());
        user.setIdCard(userAuthForm.getIdCard());
        user.setIdCardType(userAuthForm.getIdCardType());

        return updateById(user);
    }

    @Override
    public boolean updatePhone(Long userId, UpdatePhoneParamDTO updatePhoneParam) {
        // 1 使用 userId 查询用户
        User user = getById(userId);

        // 2 验证旧手机
        String oldMobile = user.getMobile(); // 旧的手机号 --- > 验证旧手机的验证码
        String oldMobileCode = stringRedisTemplate.opsForValue().get("SMS:VERIFY_OLD_PHONE:" + oldMobile);
        if(!updatePhoneParam.getOldValidateCode().equals(oldMobileCode)){
            throw new IllegalArgumentException("旧手机的验证码错误") ;
        }

        // 3 验证新手机
        String newPhoneCode = stringRedisTemplate.opsForValue().get("SMS:CHANGE_PHONE_VERIFY:" + updatePhoneParam.getNewMobilePhone());
        if(!updatePhoneParam.getValidateCode().equals(newPhoneCode)){
            throw new IllegalArgumentException("新手机的验证码错误") ;
        }
        // 4 修改手机号
        user.setMobile(updatePhoneParam.getNewMobilePhone());

        return updateById(user);
    }

    @Override
    public boolean checkNewPhone(String mobile, String countryCode) {
        int count = count(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile).eq(User::getCountryCode,countryCode));
        if(count>0){ // 有用户占用这个手机号
            throw new IllegalArgumentException("该手机号已经被占用") ;
        }
        // 2 向新的手机发送短信
        Sms sms = new Sms();
        sms.setMobile(mobile);
        sms.setCountryCode(countryCode);
        sms.setTemplateCode("CHANGE_PHONE_VERIFY"); // 模板代码  -- > 校验手机号
        return smsService.sendMsg(sms);
    }

    @Override
    public boolean register(RegisterParamDTO registerParam) {
        log.info("用户开始注册{}", JSON.toJSONString(registerParam, true));

        String mobile = registerParam.getMobile();
        String email = registerParam.getEmail();

        // 1 简单的校验
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)) {
            throw new IllegalArgumentException("手机号或邮箱不能同时为空");
        }

        // 2 查询校验
        int count = count(new LambdaQueryWrapper<User>()
                .eq(!StringUtils.isEmpty(email), User::getEmail, email)
                .eq(!StringUtils.isEmpty(mobile), User::getMobile, mobile)
        );

        if(count>0){
            throw new IllegalArgumentException("手机号或邮箱已经被注册");
        }

        registerParam.check(geetestLib, redisTemplate); // 进行极验的校验
        User user = getUser(registerParam); // 构建一个新的用户
        return save(user);
    }

    private User getUser(RegisterParamDTO registerParam) {
        User user = new User();
        user.setCountryCode(registerParam.getCountryCode());
        user.setEmail(registerParam.getEmail());
        user.setMobile(registerParam.getMobile());
        String encodePwd = new BCryptPasswordEncoder().encode(registerParam.getPassword());
        user.setPassword(encodePwd);
        user.setPaypassSetting(false);
        user.setStatus((byte) 1);
        user.setType((byte) 1);
        user.setAuthStatus((byte) 0);
        user.setLogins(0);
        user.setInviteCode(RandomUtil.randomString(6)); // 用户的邀请码
        if (!StringUtils.isEmpty(registerParam.getInvitionCode())) {
            User userPre = getOne(new LambdaQueryWrapper<User>().eq(User::getInviteCode, registerParam.getInvitionCode()));

            if (userPre != null) {
                user.setDirectInviteid(String.valueOf(userPre.getId())); // 邀请人的id , 需要查询
                user.setInviteRelation(String.valueOf(userPre.getId())); // 邀请人的id , 需要查询
            }
        }
        return user;
    }


    private void checkForm(UserAuthFormDTO userAuthForm) {
        userAuthForm.check(geetestLib, redisTemplate);
    }
}
