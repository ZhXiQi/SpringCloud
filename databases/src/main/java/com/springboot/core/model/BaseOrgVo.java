package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/1 13:04
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseOrgVo {

    private String orgName;

    private String address;

    private LocalDateTime createTime;
}
