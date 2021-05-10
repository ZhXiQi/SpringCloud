package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/1/2 11:19
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSignVo {

    private long serialNum;

    private String needHashString;

    private String sign;

    private long id;
}
