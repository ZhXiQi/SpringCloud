package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/19 13:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseLineChartVo {

    private LocalDate date;

    private BigDecimal value;
}
