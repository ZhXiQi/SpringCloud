package com.springboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 解析xxxAPI返回的交易信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransInfoVO {


    private String blockNumber;

    private String hash;

    private String blockHash;

    private String txIndex;

    private String from;

    private String to;

    private String amount;

    private String timestamp;

    private String nonce;

    private String extra;

    private String executeTime;

    private String payload;

    private String signature;

}
