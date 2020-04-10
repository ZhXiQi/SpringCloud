/*
package com.springboot.core.util;

import com.springboot.core.common.response.Code;
import com.springboot.sdk.rpc.Transaction.Transaction;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

*/
/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/1/2 13:50
 *//*

public class BusinessUtil {

    */
/**
     * 离线签名，设置签名
     * @param serialNum
     * @param sign
     * @return
     * @throws Exception
     *//*

    public static Transaction setSignature(long serialNum, String sign) throws Exception {
        String trans = (String) RedisUtil.get(String.valueOf(serialNum));
        if (StringUtils.isEmpty(trans)){
            throw new Exception(Code.TRANS_NOT_EXIST.getMsg());
        }
        Transaction transaction = Transaction.deSerialize(trans);
        Field signature = transaction.getClass().getDeclaredField("signature");
        signature.setAccessible(true);
        signature.set(transaction,sign);
        return transaction;
    }

    public static int getIntNumFromHex(String hexStr){
        hexStr = hexStr.substring(2);
        int result = Integer.parseInt(hexStr, 16);
        return result;
    }
}
*/
