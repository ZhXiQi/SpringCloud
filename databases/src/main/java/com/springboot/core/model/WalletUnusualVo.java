package com.springboot.core.model;

import com.springboot.core.constant.WalletStatus;
import com.springboot.core.constant.WalletType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/31 13:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletUnusualVo extends BaseWalletVo {

    private long walletId;

    private String walletType;

    private String bankName;

    private String opName;

    private String remark;

    private int opType;

    private String opTypeStr;

    private List<String> materialUrl;

    public void setWalletType(int walletType) {
        this.walletType = WalletType.getMsgByCode(walletType);
    }

    public void setMaterialUrl(String materialUrl) {
        if (null == materialUrl){
            this.materialUrl = null;
        }else {
            String[] split = materialUrl.split(",");
            this.materialUrl = Arrays.asList(split);
        }
    }

    public void setOpType(int opType) {
        this.opType = opType;
        this.opTypeStr = WalletStatus.getMsgByCode(this.opType);
    }
}
