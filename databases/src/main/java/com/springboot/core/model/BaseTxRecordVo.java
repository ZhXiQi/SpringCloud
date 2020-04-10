package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/30 16:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTxRecordVo {

    private String serialNum;

    private BigDecimal money;

    private LocalDateTime createTime;

    private String fromAddr;

    private String toAddr;

    private String status;

    private String remark;
}
