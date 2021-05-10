package com.springboot.core.model;

import com.springboot.core.constant.TransType;
import com.springboot.core.constant.TxStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/30 16:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxRecordVo extends BaseTxRecordVo {

    private String txType;

    private String fromOrgName;

    private String fromWalletNum;

    private String toOrgName;

    private String toWalletNum;

    private List<String> txHashList;

    private String materialUrl;


    @Override
    public void setStatus(String status) {
        int intStatus = Integer.parseInt(status);
        super.setStatus(TxStatus.getMsgByCode(intStatus));
    }

    public void setTxType(int txType) {
        this.txType = TransType.getMsgByCode(txType);
    }

    public void setTxHashList(String txHashList) {
        String[] txHash = txHashList.split(",");

        ArrayList<String> mulTxHash = new ArrayList<>(8);
        if (txHash.length < 1){
            this.txHashList = mulTxHash;
        }else {
            Stream.of(txHash).forEach(res -> {
                String[] split = res.split(";");
                int intType = Integer.parseInt(split[0]);
                String result = MessageFormat.format("{0} ({1})",split[1],TransType.getMsgByCode(intType));
                mulTxHash.add(result);
            });
            this.txHashList = mulTxHash;
        }
    }

}
