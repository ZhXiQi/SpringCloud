package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/19 10:30
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseNodeInfoVo {

    private int id;

    private String hostname;

    private String ip;

    private int status;
}
