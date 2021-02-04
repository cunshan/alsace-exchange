package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.DetectionOrg;

import java.util.List;

public interface DetectionOrgService extends BaseService<DetectionOrg,Long> {

    /**
     * 导入被检测人员信息
     * @param param
     * @return
     */
    List<DetectionOrg> importOrg(List<Object> param, String orgCode,String orgName);
}
