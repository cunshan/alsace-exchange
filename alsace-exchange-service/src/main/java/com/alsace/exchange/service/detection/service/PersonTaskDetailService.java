package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.sys.domain.User;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public interface PersonTaskDetailService extends BaseService<PersonTaskDetail,Long> {
    /**
     * 导入被检测人员信息
     * @param param
     * @return
     */
    List<PersonTaskDetail> importDetails(List<Object> param,String taskCode);


    /**
     * 批量更新任务对应明细的检测结果
     * @param taskCodeList 任务编码列表
     * @param positive 是否阳性
     */
    void updateResultBatch(List<String> taskCodeList, Boolean positive);

    /**
     * 根据试管编码更新
     * @param taskCode 任务编码
     * @param testTubeNo 试管编码
     * @param detectionMethod 检测项目  1：核酸检测 2：抗体检测
     * @param positive 是否阳性
     */
    void updateResultSingle(String taskCode, String testTubeNo, Integer detectionMethod, Boolean positive);
}
