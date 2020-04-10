package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/19 10:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseBlockVo {
    private String blockNum;

    private String hash;

    private String time;

    private Integer txNum;
}
