package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
public interface NoticeService extends IService<Notice>{


    /**
     * 获取公告列表
     * @param page
     * @param title
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    Page<Notice> findByPage(Page<Notice> page, String title, String startTime, String endTime, Integer status);

}
