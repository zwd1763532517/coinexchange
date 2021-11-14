package com.bjsxt.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.User;
import com.bjsxt.domain.UserAuthAuditRecord;
import com.bjsxt.domain.UserAuthInfo;
import com.bjsxt.dto.UserDto;
import com.bjsxt.feign.UserServiceFeign;
import com.bjsxt.model.dto.RegisterParamDTO;
import com.bjsxt.model.dto.UpdatePhoneParamDTO;
import com.bjsxt.model.dto.UserAuthFormDTO;
import com.bjsxt.response.R;
import com.bjsxt.service.UserAuthAuditRecordService;
import com.bjsxt.service.UserAuthInfoService;
import com.bjsxt.service.UserService;
import com.bjsxt.model.vo.UserAuthInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户模块")
@RequestMapping("/users")
public class UserController implements UserServiceFeign {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthAuditRecordService userAuthAuditRecordService;

    @Autowired
    private UserAuthInfoService userAuthInfoService;

    @GetMapping
    @ApiOperation(value = "会员查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "大小"),
            @ApiImplicitParam(name = "mobile",value = "手机号"),
            @ApiImplicitParam(name = "userId",value = "用户id"),
            @ApiImplicitParam(name = "userName",value = "用户名称"),
            @ApiImplicitParam(name = "realName",value = "真实姓名"),
            @ApiImplicitParam(name = "status",value = "状态"),
    })
    @PreAuthorize("hasAuthority('user_query')")
    public R<Page<User>> findByPage(@ApiIgnore Page<User> page,
                                   String mobile,Long userId,
                                    String userName,String realName,
                                    Integer status){
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<User> userPage = userService.findByPage(page,mobile,userId,userName,realName,status,null);
        return R.ok(userPage);
    }

    @PostMapping("/status")
    @ApiOperation(value = "修改用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "会员id"),
            @ApiImplicitParam(name = "status",value = "会员的状态")
    })
    @PreAuthorize("hasAuthority('user_update')")
    public R updateStatus(Long id,Byte status){
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        boolean b = userService.updateById(user);
        if(b){
            return R.ok();
        }
        return R.fail();
    }

    @PatchMapping
    @ApiOperation(value = "修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "会员实体")
    })
    @PreAuthorize("hasAuthority('user_update')")
    public R update(@RequestBody User user){
        boolean b = userService.updateById(user);
        if(b){
            return R.ok();
        }
        return R.fail();
    }

    @GetMapping("/info")
    @ApiOperation(value = "查询会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "会员的id")
    })
    public R<User> userInfo(Long id){
        User user = userService.getById(id);
        return R.ok(user);
    }


    @GetMapping("/directInvites")
    @ApiOperation(value = "查询该用户邀请的用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "大小"),
    })
    public R<Page<User>> getDir(@ApiIgnore Page<User> page,Long userId){
        Page<User> userPage = userService.findDirPage(page,userId);
        return R.ok(userPage);
    }

    @GetMapping("/auths")
    @ApiOperation(value = "会员查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "大小"),
            @ApiImplicitParam(name = "mobile",value = "手机号"),
            @ApiImplicitParam(name = "userId",value = "用户id"),
            @ApiImplicitParam(name = "userName",value = "用户名称"),
            @ApiImplicitParam(name = "realName",value = "真实姓名"),
            @ApiImplicitParam(name = "reviewsStatus",value = "审核状态"),
    })
    public R<Page<User>> findUserAuthsByPage(
            @ApiIgnore Page<User> page,
            String mobile,Long userId,
            String userName,String realName,
            Integer reviewsStatus
    ){
        Page<User> userPage = userService.findByPage(page,mobile,userId,userName,realName,null,reviewsStatus);
        return R.ok(userPage);
    }

    @GetMapping("/auth/info")
    @ApiOperation(value = "会员查询认证详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id的值")
    })
    public R<UserAuthInfoVO> getUserAuthInfo(
            Long id
    ){
        User user = userService.getById(id);
        List<UserAuthAuditRecord> userAuthAuditRecords = null;
        List<UserAuthInfo> userAuthInfoList = null;
        if(user != null){
            Integer reviewsStatus = user.getReviewsStatus();
            if(reviewsStatus ==null || reviewsStatus==0){
                userAuthAuditRecords = Collections.emptyList();
                userAuthInfoList = userAuthInfoService.getUserAuthInfoByUserId(id);
            }else{
                userAuthAuditRecords = userAuthAuditRecordService.getUserAuthAuditRecordList(id);
                UserAuthAuditRecord userAuthAuditRecord = userAuthAuditRecords.get(0);
                Long authCode = userAuthAuditRecord.getAuthCode();
                userAuthInfoList = userAuthInfoService.getUserAuthInfoByCode(authCode);
            }
        }
        return R.ok(new UserAuthInfoVO(user,userAuthInfoList,userAuthAuditRecords));
    }


    @PostMapping("/auths/status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户的ID"),
            @ApiImplicitParam(name = "authStatus", value = "用户的审核状态"),
            @ApiImplicitParam(name = "authCode", value = "一组图片的唯一标识"),
            @ApiImplicitParam(name = "remark", value = "审核拒绝的原因"),
    })
    public R updateUserAuthStatus(@RequestParam(required = true) Long id, @RequestParam(required = true) Byte authStatus, @RequestParam(required = true) Long authCode, String remark) {
        userService.updateUserAuthStatus(id,authStatus,authCode,remark);
        return R.ok();
    }

    @GetMapping("/current/info")
    @ApiOperation(value = "获取当前登录用户的详情")
    public R<User> currentUserInfo(){
        // 获取用户的Id
        String idStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.getById(Long.valueOf(idStr));
        user.setPassword("****");
        user.setPaypassword("*****");
        return R.ok(user) ;
    }

    @PostMapping("/authAccount")
    @ApiOperation(value = "用户的实名认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "" ,value = "")
    })
    public R identifyCheck(@RequestBody UserAuthFormDTO userAuthForm){
        String idStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        boolean isOk = userService.identifyVerify(Long.valueOf(idStr),userAuthForm) ;
        if(isOk){
            return R.ok() ;
        }
        return R.fail("认证失败") ;
    }


    @PostMapping("/updatePhone")
    @ApiOperation(value = "修改手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updatePhoneParam",value = "updatePhoneParam 的json数据")
    })
    public R updatePhone(@RequestBody @Validated UpdatePhoneParamDTO updatePhoneParam) {
        String idStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        boolean isOk = userService.updatePhone(Long.valueOf(idStr), updatePhoneParam);

        if (isOk) {
            return R.ok();
        }
        return R.fail("修改失败");
    }

    @GetMapping("/checkTel")
    @ApiOperation(value = "检查新的手机号是否可用,如可用,则给该新手机发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile" ,value = "新的手机号"),
            @ApiImplicitParam(name = "countryCode" ,value = "手机号的区域")
    })
    public R checkNewPhone(@RequestParam(required = true) String mobile,@RequestParam(required = true) String countryCode){
        boolean isOk =   userService.checkNewPhone(mobile,countryCode) ;
        return isOk ? R.ok():R.fail("新的手机号校验失败") ;
    }


    @PostMapping("/register")
    @ApiOperation(value = "用户的注册")
    public R register(@RequestBody RegisterParamDTO registerParam) {
        boolean isOk = userService.register(registerParam);
        if (isOk) {
            return R.ok();
        }
        return R.fail("注册失败");
    }



    /**
     *
     * @param ids
     * @return
     */
    @Override
    public Map<Long,UserDto> getBasicUsers(List<Long> ids, String userName, String mobile) {
        Map<Long,UserDto> userDtos = userService.getBasicUsers(ids,userName,mobile);
        return userDtos;
    }
}
