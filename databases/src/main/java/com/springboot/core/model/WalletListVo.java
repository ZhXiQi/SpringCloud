package com.springboot.core.model;

import com.springboot.core.constant.WalletStatus;
import com.springboot.core.constant.WalletType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/9 16:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletListVo extends BaseWalletVo {

    private String cardNum;

    private long id;

    private String status;

    private String walletType;

    public void setStatus(int status) {
        this.status = WalletStatus.getMsgByCode(status);
    }

    public void setWalletType(int walletType) {
        this.walletType = WalletType.getMsgByCode(walletType);
    }
}
