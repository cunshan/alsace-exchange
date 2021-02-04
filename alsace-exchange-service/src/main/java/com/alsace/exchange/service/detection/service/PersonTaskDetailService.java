package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailResult;
import com.alsace.exchange.service.sys.domain.User;
import com.itextpdf.text.DocumentException;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface PersonTaskDetailService extends BaseService<PersonTaskDetail,Long> {
    /**
     * 导入被检测人员信息
     * @param taskCode 任务编码
     * @param param 导入内容
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
     * @param positive 是否阳性
     */
    void updateResultSingle(String taskCode, String testTubeNo, String detectionType, Boolean positive);

    /**
     * 批量保存检测结果
     */
    void saveResult(List<PersonTaskDetailResult> resultList);

  /**
   * 人员检测结果分页查询
   * @param param 分页查询条件
   * @Author: wayne
   * @Date: 2021/1/28
   */
    PageResult<PersonTaskDetail> findResultPage(PageParam<PersonTaskDetail> param);

    /**
     * 人员检测结果条件查询
     * @param param 分页查询条件
     * @Author: wayne
     * @Date: 2021/1/30
     */
    List<PersonTaskDetailImport> findResults(PersonTaskDetail param);

    /**
     * 导出PDF
     * @param param 查询条件
     * @Author: wayne
     * @Date: 2021/2/1
     */
    ByteArrayOutputStream convertReceivePdf(PersonTaskDetail param) throws IOException, DocumentException;

    /**
     * 查询人员表单信息分页
     * @param detailPage 查询条件
     * @Author: wayne
     * @Date: 2021/2/1
     */
    PageResult<PersonTaskDetail> findFromPage(PageParam<PersonTaskDetail> detailPage);
}
