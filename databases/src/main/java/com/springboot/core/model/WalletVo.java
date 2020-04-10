package com.springboot.core.model;

import com.springboot.core.constant.WalletStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/6 19:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletVo {

    private Long userId;

    private String orgName;

    private String bankName;

    private String cardNum;

    private Integer code;

    private int walletStatus;

    private String walletNum;

    private String walletStatusStr;

    public void setWalletStatus(int walletStatus) {
        this.walletStatus = walletStatus;
        this.walletStatusStr = WalletStatus.getMsgByCode(walletStatus);
    }
}
