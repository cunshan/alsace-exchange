package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.sys.domain.User;

import java.util.ArrayList;
import java.util.List;

public interface PersonTaskDetailService extends BaseService<PersonTaskDetail,Long> {
    /**
     * 导入被检测人员信息
     * @param param
     * @return
     */
    List<PersonTaskDetail> importDetails(List<Object> param,String taskCode);
}
