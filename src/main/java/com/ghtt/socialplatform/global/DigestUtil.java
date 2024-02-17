package com.ghtt.socialplatform.global;

import com.ghtt.socialplatform.controller.Code;
import com.ghtt.socialplatform.controller.exceptions.SystemException;

import java.security.MessageDigest;

public class DigestUtil {
    /**
     * MD5 加密长度：32字节数量
     */
    private final static String MD5 = "MD5";
    /**
     * SHA1 加密长度：40字节数量
     */
    private final static String SHA1 = "SHA-1";
    /**
     * SHA256 加密长度：64字节数量
     */
    private final static String SHA256 = "SHA-256";
    /**
     * SHA512 加密长度：128字节数量
     */
    private final static String SHA512 = "SHA-512";


    /**
     * @param input 消息类型
     * @param algorithm 算法类型：MD5、SHA-1、SHA-256、SHA-512
     * @return
     * 加盐加密后的密码
     */
    public static String encrypt(String input, String algorithm, String salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] digest = messageDigest.digest((input+salt).getBytes());

            return toHex(digest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(Code.INSERT_ERR,"安全加密出错，请稍后再试");
        }
    }

    private static String toHex(byte[] digest) {
        // 创建对象用来拼接
        StringBuilder sb = new StringBuilder();

        for (byte b : digest) {
            // 转成 16进制
            String s = Integer.toHexString(b & 0xff);
            if (s.length() == 1) {
                // 如果生成的字符只有一个，前面补0
                s = "0" + s;
            }
            sb.append(s);
        }
        return sb.toString();
    }
}

