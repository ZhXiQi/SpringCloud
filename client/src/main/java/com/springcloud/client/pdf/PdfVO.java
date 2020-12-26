package com.springcloud.client.pdf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
@Builder
@AllArgsConstructor
public class PdfVO {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String id;
    private String blockHeight;
    private String hash;
    private String txHash;
    private String origin;
    private String name;
    private String opTime;
    private String chainTime;
    private String chainName;
    private String qrCodePath;
    private String type;

    public PdfVO() {

    }



    public void setOpTime(Long time) {
        if (dateFormat == null) {
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        opTime = dateFormat.format(time);
    }

    public void setChainTime(Long time) {
        if (dateFormat == null) {
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        chainTime = dateFormat.format(time);
    }

    public void setChainTime(String s) {
        chainTime = s;
    }

}
