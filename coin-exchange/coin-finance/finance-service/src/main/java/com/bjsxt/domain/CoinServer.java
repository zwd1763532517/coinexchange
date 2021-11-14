package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.CoinServer")
@Data
@TableName(value = "coin_server")
public class CoinServer {
    /**
     * 自增id
     */
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="自增id")
    private Long id;

    /**
     * 钱包服务器ip
     */
    @TableField(value = "rpc_ip")
    @ApiModelProperty(value="钱包服务器ip")
    private String rpcIp;

    /**
     * 钱包服务器ip
     */
    @TableField(value = "rpc_port")
    @ApiModelProperty(value="钱包服务器ip")
    private String rpcPort;

    /**
     * 服务是否运行 0:正常,1:停止
     */
    @TableField(value = "running")
    @ApiModelProperty(value="服务是否运行 0:正常,1:停止")
    private Integer running;

    /**
     * 钱包服务器区块高度
     */
    @TableField(value = "wallet_number")
    @ApiModelProperty(value="钱包服务器区块高度")
    private Long walletNumber;

    @TableField(value = "coin_name")
    @ApiModelProperty(value="null")
    private String coinName;

    /**
     * 备注信息
     */
    @TableField(value = "mark")
    @ApiModelProperty(value="备注信息")
    private String mark;

    /**
     * 真实区块高度
     */
    @TableField(value = "real_number")
    @ApiModelProperty(value="真实区块高度")
    private Long realNumber;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time")
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;

    public static final String COL_RPC_IP = "rpc_ip";

    public static final String COL_RPC_PORT = "rpc_port";

    public static final String COL_RUNNING = "running";

    public static final String COL_WALLET_NUMBER = "wallet_number";

    public static final String COL_COIN_NAME = "coin_name";

    public static final String COL_MARK = "mark";

    public static final String COL_REAL_NUMBER = "real_number";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";

    public static final String COL_CREATED = "created";
}