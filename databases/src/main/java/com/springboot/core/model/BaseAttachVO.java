package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基本附件信息
 *
 *
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseAttachVO {

    private Integer type;

    private String url;

    private String name;

    private String hash;

    public BaseAttachVO(Integer type, String url, String name) {
        this.type = type;
        this.url = url;
        this.name = name;
    }

    public BaseAttachVO(String url, String name, String hash) {
        this.url = url;
        this.name = name;
        this.hash = hash;
    }
}
