package com.alsace.exchange.service.detection.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.base.CodeName;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.AlsaceBeanUtils;
import com.alsace.exchange.common.utils.IdUtils;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailResult;
import com.alsace.exchange.service.detection.emums.TaskDetailStatus;
import com.alsace.exchange.service.detection.excel.PersonTaskDetailVerifyService;
import com.alsace.exchange.service.detection.mapper.PersonTaskDetailMapper;
import com.alsace.exchange.service.detection.repositories.PersonTaskDetailRepository;
import com.alsace.exchange.service.detection.repositories.PersonTaskDetailResultRepository;
import com.alsace.exchange.service.detection.service.PdfService;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import com.alsace.exchange.service.sys.domain.UserData;
import com.alsace.exchange.service.sys.service.UserDataService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class PersonTaskDetailServiceImpl extends AbstractBaseServiceImpl<PersonTaskDetail> implements PersonTaskDetailService {

  @Resource
  private PersonTaskDetailRepository personTaskDetailRepository;
  @Resource
  private PersonTaskDetailVerifyService personTaskDetailVerifyService;
  @Resource
  private OrderNoGenerator orderNoGenerator;
  @Resource
  private PersonTaskDetailResultRepository personTaskDetailResultRepository;
  @Resource
  private PersonTaskDetailMapper personTaskDetailMapper;
  @Resource
  private UserDataService userDataService;

  @Override
  protected JpaRepository<PersonTaskDetail, Long> getJpaRepository() {
    return this.personTaskDetailRepository;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTaskDetail> getJpaSpecificationExecutor() {
    return this.personTaskDetailRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  @AutoFill(AutoFillType.UPDATE)
  public PersonTaskDetail update(PersonTaskDetail param) {
    Assert.notNull(param.getId(), Constants.ID_NOT_NULL_ERROR);
    PersonTaskDetail dbDetail = this.getOneById(param.getId(), false);
    Assert.notNull(dbDetail, "对应明细不存在！");
    Assert.state(TaskDetailStatus.INIT.status().equals(dbDetail.getDetailStatus()), "对应明细状态已提交，不能修改！");
    AlsaceBeanUtils.copyNotNullProperties(param, dbDetail);
    return personTaskDetailRepository.saveAndFlush(dbDetail);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean delete(List<Long> idList) {
    Assert.notEmpty(idList, "ID列表为空！");
    List<PersonTaskDetail> domainList = new ArrayList<>(idList.size());
    idList.forEach(id -> {
      PersonTaskDetail detail = this.getOneById(id);
      Assert.notNull(detail, "对应明细不存在！");
      Assert.state(TaskDetailStatus.INIT.status().equals(detail.getDetailStatus()), "对应明细状态已提交，不能修改！");
      detail.setDeleted(true);
      domainList.add(detail);
    });
    getJpaRepository().saveAll(domainList);
    return true;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<PersonTaskDetail> importDetails(List<Object> param, String taskCode) {
    InputStream is = new ByteArrayInputStream((byte[]) param.get(0));
    ImportParams params = new ImportParams();
    params.setTitleRows(1);
    // 开启Excel校验
    params.setNeedVerfiy(true);
    params.setVerifyHandler(personTaskDetailVerifyService);
    try {
      ExcelImportResult<PersonTaskDetailImport> importResult = ExcelImportUtil.importExcelMore(is, PersonTaskDetailImport.class, params);
      if (importResult.isVerfiyFail()) {
        StringBuffer sb = new StringBuffer();
        for (PersonTaskDetailImport entity : importResult.getFailList()) {
          sb.append(String.format("第%s行的错误是:%s", entity.getRowNum(), entity.getErrorMsg()));
        }
        throw new AlsaceException(sb.toString());
      }
      List<PersonTaskDetail> personTaskDetails = new ArrayList<>();
      importResult.getList().forEach(personTaskDetailImport -> {
        PersonTaskDetail personTaskDetail = new PersonTaskDetail();
        BeanUtils.copyProperties(personTaskDetailImport, personTaskDetail);
        personTaskDetail.setId(IdUtils.id());
        personTaskDetail.setTaskCode(taskCode);
        String detailCode = orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.PERSON_TASK_DETAIL_CODE);
        personTaskDetail.setDetailCode(detailCode);
        personTaskDetail.setGender("男".equals(personTaskDetailImport.getGender()) ? 0 : 1);
        personTaskDetail.setWorking("是".equals(personTaskDetailImport.getWorking()));
        personTaskDetail.setCreatedBy(loginInfoProvider.loginAccount());
        personTaskDetail.setCreatedDate(new Date());
        personTaskDetail.setDeleted(false);
        personTaskDetail.setDetailStatus(TaskDetailStatus.INIT.status());
        personTaskDetails.add(personTaskDetail);
      });
      return personTaskDetailRepository.saveAll(personTaskDetails);
    } catch (Exception e) {
      throw new AlsaceException("导入被检测人员数据异常！" + e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateResultBatch(List<String> taskCodeList, Boolean positive) {
    personTaskDetailResultRepository.updateResultByTaskCode(taskCodeList, positive, getLoginAccount());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateResultSingle(String taskCode, String testTubeNo, String detectionType, Boolean positive) {
    int count = personTaskDetailResultRepository.updateResultByTubeNo(taskCode, testTubeNo, detectionType, positive, getLoginAccount());
    if (count <= 0) {
      throw new AlsaceException("更新失败！");
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  @AutoFill(AutoFillType.CREATE)
  public void saveResult(List<PersonTaskDetailResult> resultList) {
    personTaskDetailResultRepository.saveAll(resultList);
  }

  @Override
  @Transactional(readOnly = true)
  public PageResult<PersonTaskDetail> findResultPage(PageParam<PersonTaskDetail> param) {
    PersonTaskDetail personTaskDetail = param.getParam();
    String loginAccount = getLoginAccount();
    personTaskDetail.setUserDataAccount(loginAccount);
    //查询出当前人的数据权限
    UserData queryUserData = new UserData();
    queryUserData.setLoginAccount(loginAccount).setDeleted(false);
    List<UserData> userDataList = userDataService.findAll(queryUserData);
    List<CodeName> codeNames = new ArrayList<>(userDataList.size());
    userDataList.forEach(userData->codeNames.add(new CodeName(userData.getDataCode(),userData.getDataLabel())));
    personTaskDetail.setUserDataList(codeNames);
    PageInfo<PersonTaskDetail> pageInfo =
            PageHelper.startPage(param.getPageNum(),param.getPageSize())
                    .doSelectPageInfo(()->personTaskDetailMapper.findResultPage(personTaskDetail));
    return new PageResult<>(pageInfo);
  }

  @Override
  public List<PersonTaskDetailImport> findResults(PersonTaskDetail param) {
    String loginAccount = getLoginAccount();
    param.setUserDataAccount(loginAccount);
    //查询出当前人的数据权限
    UserData queryUserData = new UserData();
    queryUserData.setLoginAccount(loginAccount).setDeleted(false);
    List<UserData> userDataList = userDataService.findAll(queryUserData);
    List<CodeName> codeNames = new ArrayList<>(userDataList.size());
    userDataList.forEach(userData->codeNames.add(new CodeName(userData.getDataCode(),userData.getDataLabel())));
    param.setUserDataList(codeNames);
    return personTaskDetailMapper.findResults(param);
  }

  @Override
  public ByteArrayOutputStream convertReceivePdf(String taskCode) throws IOException, DocumentException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    //设值字体样式
    BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
    Font fontBold = new Font(bf, 11, Font.BOLD);
    Font font2 = new Font(bf, 11, Font.NORMAL);
    // 页面大小
    Rectangle tRectangle = new Rectangle(PageSize.A4);
    // 定义文档
    Document doc = new Document(tRectangle, 20, 20, 20, 20);
    // 书写器
    PdfWriter writer = PdfWriter.getInstance(doc, out);
    //版本(默认1.4)
    writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
    PdfPTable table = new PdfPTable(5);
    table.setTotalWidth(new float[]{100, 100, 100, 100, 100});
    //添加表格内容
    //添加PDF标题内容
    this.addPdfTitle(table, fontBold);
    PersonTaskDetail personTaskDetail =new PersonTaskDetail();
    personTaskDetail.setTaskCode(taskCode);
    List<PersonTaskDetailImport> details = this.findResults(personTaskDetail);
    //添加导出信息
    addPdfTable(table, fontBold, font2, details);

    //打开文档
    doc.open();
    //添加img
    doc.add(table);
    //记得关闭document
    doc.close();
    return out;
  }
  private static void addPdfTable(PdfPTable table, Font fontBold, Font font2, List<PersonTaskDetailImport> details) {
    table.addCell(PdfService.getPDFCell("姓名", fontBold, true));
    table.addCell(PdfService.getPDFCell("身份证", fontBold, true));
    table.addCell(PdfService.getPDFCell("采样管码", fontBold, true));
    table.addCell(PdfService.getPDFCell("检测时间", fontBold, true));
    table.addCell(PdfService.getPDFCell("检测结果", fontBold, true));
    details.forEach(detail -> {
      table.addCell(PdfService.getPDFCell(detail.getPersonName(), font2, false));
      table.addCell(PdfService.getPDFCell(detail.getIdCardNo(), font2, false));
      table.addCell(PdfService.getPDFCell(detail.getPersonName(), font2, false));
      table.addCell(PdfService.getPDFCell(detail.getIdCardNo(), font2, false));
      table.addCell(PdfService.getPDFCell(detail.getPersonName(), font2, false));
//            table.addCell(PdfUtils.getPDFCell(String.valueOf(detail.getCnyNoTaxAmount()), font2, false));
//            //税额 = 金额 * 税率
//            table.addCell(PdfUtils.getPDFCell(String.valueOf(detail.getCnyTaxAmount()), font2, false));
    });
  }
  private void addPdfTitle(PdfPTable table, Font fontBold) {
    //中间部分填充
    PdfPCell cell = this.mergeCol("检测结果导出", fontBold, 5, false);
    cell.disableBorderSide(15);
    table.addCell(cell);
  }

  //合并列的静态函数
  public static PdfPCell mergeCol(String str, Font font, int i, Boolean isBgColor) {
    PdfPCell cell = new PdfPCell(new Paragraph(str, font));
    //设置背景颜色为浅灰
    if (isBgColor) {
      cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    }
    cell.setMinimumHeight(25);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //将该单元格所在行包括该单元格在内的i列单元格合并为一个单元格
    cell.setColspan(i);
    return cell;
  }
}
