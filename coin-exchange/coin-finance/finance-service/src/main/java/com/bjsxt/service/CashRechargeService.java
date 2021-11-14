package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.CashRecharge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjsxt.model.dto.CashParamDTO;
import com.bjsxt.model.vo.CashTradeVO;

public interface CashRechargeService extends IService<CashRecharge>{


    /**
     *
     * @param page
     * @param coinId
     * @param userId
     * @param userName
     * @param mobile
     * @param status
     * @param numMin
     * @param numMax
     * @param startTime
     * @param endTime
     * @return
     */
    Page<CashRecharge> findByPage(Page<CashRecharge> page, Long coinId, Long userId, String userName, String mobile, Byte status, String numMin, String numMax, String startTime, String endTime);

    /**
     *
     * @param page
     * @param userId
     * @param status
     * @return
     */
    Page<CashRecharge> findUserCashRecharge(Page<CashRecharge> page, Long userId, Byte status);

    /**
     * 进行一个GCN/充值/购买
     * @param userId
     * @param cashParam
     * @return
     */
    CashTradeVO buy(Long userId, CashParamDTO cashParam);
}
