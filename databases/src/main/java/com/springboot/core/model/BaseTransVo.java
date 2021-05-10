package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/9 20:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTransVo {

    private String fromAddress;

    private String toAddress;

    private double money;

    private String orgName;

    private String remark;
}
