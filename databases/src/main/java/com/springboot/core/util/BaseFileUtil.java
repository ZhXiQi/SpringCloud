package com.springboot.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * 计算文件hash
 */
public class BaseFileUtil {
    /**
     * 通过文件实际路径获取文件hash
     *
     * @param fileName 文件绝对路径
     * @param hashType hash类型，SHA1/SHA256/SHA512/MD5，默认为SHA256
     * @return 文件64位hash值
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileHashByRealPath(String fileName, String hashType) throws IOException, NoSuchAlgorithmException {
        if (null == fileName || fileName.trim().equals("")) {
            return null;
        }
        // hash类型不存在
        if (null == hashType || hashType.trim().equals("")) {
            hashType = "SHA-256";
        }
        MessageDigest md5;
        try(InputStream fis = new FileInputStream(fileName)) {
            byte buffer[] = new byte[1024];
            md5 = MessageDigest.getInstance(hashType);
            for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
                md5.update(buffer, 0, numRead);
            }
        }

        return toHexString(md5.digest());
    }

    public static String getFileHash(InputStream fis, String hashType) throws NoSuchAlgorithmException, IOException {
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }

    /**
     * 通过文件输入流获取文件hash
     *
     * @param fis      文件输入流
     * @param hashType hash类型，默认SHA256
     * @return 文件hash
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String getFileHashByInputStream(InputStream fis, String hashType) throws NoSuchAlgorithmException, IOException {

        if (null == fis) {
            return null;
        }

        if (null == hashType || hashType.trim().equals("")) {
            hashType = "SHA-256";
        }
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }

    /**
     * 获取字符串的hash
     *
     * @param strSrc   原始字符串
     * @param hashType hash类型，默认SHA256
     * @return 字符串hash
     * @throws NoSuchAlgorithmException
     */
    public static String getStringHash(String strSrc, String hashType) throws NoSuchAlgorithmException {
        if (null == strSrc || strSrc.trim().equals("")) {
            return null;
        }
        if (null == hashType || hashType.trim().equals("")) {
            hashType = "SHA-256";
        }
        MessageDigest md = MessageDigest.getInstance(hashType);
        md.update(strSrc.getBytes());
        return toHexString(md.digest());
    }

    /**
     * byte数组转16进制字符串
     *
     * @param src byte数组
     * @return 16进制字符串
     */
    protected static String toHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + "");
        }
        return stringBuilder.toString();
    }

    public static String getStringHash(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = toHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }
}
