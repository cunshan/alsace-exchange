package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.service.detection.domain.PersonTaskDetailResult;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PersonTaskDetailPageDetailVo implements Serializable {
  private static final long serialVersionUID = -446287740159515017L;

  private String personName;

  private List<PersonTaskDetailResult> testTubeList;
}
