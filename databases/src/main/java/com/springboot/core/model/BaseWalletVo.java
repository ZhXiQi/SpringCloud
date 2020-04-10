package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/18 15:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseWalletVo {

    private long userId;

    private String walletNum;

    private BigDecimal balance;

    private String address;

    private LocalDateTime createTime;

    private String orgName;
}
