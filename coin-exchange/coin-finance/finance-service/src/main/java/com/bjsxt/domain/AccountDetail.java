package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@ApiModel(value = "com.bjsxt.domain.AccountDetail")
@Data
@TableName(value = "account_detail")
public class AccountDetail {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "null")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 币种id
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value = "币种id")
    private Long coinId;

    /**
     * 账户id
     */
    @TableField(value = "account_id")
    @ApiModelProperty(value = "账户id")
    private Long accountId;

    /**
     * 该笔流水资金关联方的账户id
     */
    @TableField(value = "ref_account_id")
    @ApiModelProperty(value = "该笔流水资金关联方的账户id")
    private Long refAccountId;

    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    /**
     * 入账为1，出账为2
     */
    @TableField(value = "direction")
    @ApiModelProperty(value = "入账为1，出账为2")
    private Boolean direction;

    /**
     * 业务类型:
     充值(recharge_into)
     提现审核通过(withdrawals_out)
     下单(order_create)
     成交(order_turnover)
     成交手续费(order_turnover_poundage)
     撤单(order_cancel)
     注册奖励(bonus_register)
     提币冻结解冻(withdrawals)
     充人民币(recharge)
     提币手续费(withdrawals_poundage)
     兑换(cny_btcx_exchange)
     奖励充值(bonus_into)
     奖励冻结(bonus_freeze)
     */
    @TableField(value = "business_type")
    @ApiModelProperty(value = "业务类型: 充值(recharge_into) 提现审核通过(withdrawals_out) 下单(order_create) 成交(order_turnover) 成交手续费(order_turnover_poundage) 撤单(order_cancel) 注册奖励(bonus_register) 提币冻结解冻(withdrawals) 充人民币(recharge) 提币手续费(withdrawals_poundage) 兑换(cny_btcx_exchange) 奖励充值(bonus_into) 奖励冻结(bonus_freeze) ")
            private String businessType;

            /**
             * 资产数量
            */
            @TableField(value = "amount")
            @ApiModelProperty(value = "资产数量")
            private BigDecimal amount;

            /**
             * 手续费
            */
            @TableField(value = "fee")
            @ApiModelProperty(value = "手续费")
            private BigDecimal fee;

            /**
             * 流水状态：
            充值
            提现
            冻结
            解冻
            转出
            转入
            */
            @TableField(value = "remark")
            @ApiModelProperty(value = "流水状态： 充值 提现 冻结 解冻 转出 转入")
            private String remark;

            /**
             * 日期
            */
            @TableField(value = "created")
            @ApiModelProperty(value = "日期")
            private Date created;

            public static final String COL_USER_ID="user_id";

            public static final String COL_COIN_ID="coin_id";

            public static final String COL_ACCOUNT_ID="account_id";

            public static final String COL_REF_ACCOUNT_ID="ref_account_id";

            public static final String COL_ORDER_ID="order_id";

            public static final String COL_DIRECTION="direction";

            public static final String COL_BUSINESS_TYPE="business_type";

            public static final String COL_AMOUNT="amount";

            public static final String COL_FEE="fee";

            public static final String COL_REMARK="remark";

            public static final String COL_CREATED="created";

    public AccountDetail(Object o, Long userId, Long coinId, Long id, Long id1, Long orderId, byte b, String type, BigDecimal mum, BigDecimal fee, String 用户提现, Object o1, Object o2, Object o3) {
    }
}