package com.eric.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 摘要算法工具类
 */
public class DigestUtil {
    private static ThreadLocal<MessageDigest> messageDigestHolder = new ThreadLocal<MessageDigest>();

    static {
        try {
            MessageDigest message = MessageDigest.getInstance("MD5");
            messageDigestHolder.set(message);
        } catch (NoSuchAlgorithmException e) {
        }
    }
    public static String md5(String src){
        byte[] bytes = digest(src, "MD5");
        return bytes == null ? null : bytesToHex(bytes);
    }

    public static byte[] md5Bytes(String src){
        return digest(src, "MD5");
    }

    public static String sha1(String src){
        byte[] bytes = digest(src, "SHA-1");
        return bytes == null ? null : bytesToHex(bytes);
    }

    public static byte[] sha1Bytes(String src){
        return digest(src, "SHA-1");
    }

    public static String sha256(String src){
        byte[] bytes = digest(src, "SHA-256");
        return bytes == null ? null : bytesToHex(bytes);
    }

    public static byte[] sha256Bytes(String src){
        return digest(src, "SHA-256");
    }

    private static byte[] digest(String src, String algorithm) {
        MessageDigest crypt = null;
        byte[] bytes = null;
        try {
            crypt = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(crypt != null){
            crypt.reset();
            try {
                crypt.update(src.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            bytes = crypt.digest();
        }
        return bytes;
    }

    /**
     * 字节数组转换成16进制字符串
     * @author xuyf22
     * @param hash
     * @return
     */
    public static String bytesToHex(byte[] hash) {
        StringBuilder sb = new StringBuilder(hash.length*2);
        int tmp;
        for(byte b : hash){
            tmp = b & 0xff;
            if(tmp < 16) sb.append("0");
            sb.append(Integer.toHexString(tmp));
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转换成字节数组
     * @param src
     * @return
     */
    public static byte[] hexToBytes(String src){
        byte[] res = new byte[src.length()/2];
        char[] chs = src.toCharArray();
        for(int i=0,c=0; i<chs.length; i+=2,c++){
            res[c] = (byte) (Integer.parseInt(new String(chs,i,2), 16));
        }
        return res;
    }
    public static String getMD5Format(String[] data) {
        try {
            MessageDigest message = MessageDigest.getInstance("MD5");
            if (message == null) {
                message = MessageDigest.getInstance("MD5");
            }
            for (String str : data) {
                message.update(str.getBytes("UTF-8"));
            }
            byte[] b = message.digest();
            String digestHexStr = "";
            for (int i = 0; i < 16; i++) {
                digestHexStr += byteHEX(b[i]);
            }

            return digestHexStr;
        } catch (Exception e) {
            return null;
        }
    }

    // 用来将字节转换成 16 进制表示的字符
    static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /***
     *
     * @Title: byteHEX
     * @Description:
     * @author wujl
     * @param ib
     * @return String 返回类型
     */
    private static String byteHEX(byte ib) {
        char[] ob = new char[2];
        ob[0] = hexDigits[(ib >>> 4) & 0X0F];
        ob[1] = hexDigits[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    /***
     *
     * @Title: getMD5Format
     * @Description: 计算MD5并转换为32字节明文显示串
     * @author wujl
     * @param data
     * @return String 返回类型
     */
    public static String getMD5Format(String data) {
        try {
            MessageDigest message = messageDigestHolder.get();
            if (message == null) {
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }
            message.update(data.getBytes("UTF-8"));
            byte[] b = message.digest();

            String digestHexStr = "";
            for (int i = 0; i < 16; i++) {
                digestHexStr += byteHEX(b[i]);
            }

            return digestHexStr;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 签名生成算法
     *
     * @param params 请求参数集，所有参数必须已转换为字符串类型
     * @param  secret 签名密钥
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(Map<String, String> params, String secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(secret);
        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }
        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

}
