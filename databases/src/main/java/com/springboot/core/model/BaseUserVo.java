package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/6 16:46
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserVo {

    private Long userId;

    private String email;

    private String orgName;
}
