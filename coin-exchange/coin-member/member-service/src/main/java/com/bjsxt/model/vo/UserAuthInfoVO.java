package com.bjsxt.model.vo;

import com.bjsxt.domain.User;
import com.bjsxt.domain.UserAuthAuditRecord;
import com.bjsxt.domain.UserAuthInfo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "对于VO对象，其实不需要实现序列化，因为RestController会自动序列化")
public class UserAuthInfoVO {

    private User user;

    private List<UserAuthInfo> userAuthInfoList;

    private List<UserAuthAuditRecord> authAuditRecordList;
}
