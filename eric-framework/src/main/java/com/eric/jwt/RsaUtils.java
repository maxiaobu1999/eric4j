package com.eric.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Rsa 密码工具
 */
public class RsaUtils {
    public static final String signAlgorithm = "SHA256withRSA";
    private static final Logger log = LoggerFactory.getLogger(RsaUtils.class);

    public static Map<String, String> createKey() {
        Map<String, String> keyPairMap = new HashMap<>();
        KeyPairGenerator rsa = null;
        try {
            rsa = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
        if (rsa != null) {
            rsa.initialize(1024, new SecureRandom());
            KeyPair keyPair = rsa.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥

            //获取公、私钥值
            String publicKeyValue = byte2Base64String(publicKey.getEncoded());
            String privateKeyValue = byte2Base64String(privateKey.getEncoded());

            keyPairMap.put("public", publicKeyValue);
            keyPairMap.put("private", privateKeyValue);
        }

        return keyPairMap;
    }

    public static RSAPrivateKey getPrivateKey(String privateKeyBase64Str) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(base64String2Byte(privateKeyBase64Str));
        KeyFactory keyFactory;
        keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    public static RSAPublicKey getPublicKey(String publicKeyBase64Str) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(base64String2Byte(publicKeyBase64Str));
        KeyFactory keyFactory;
        keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 由证书获得公钥
     *
     * @param cerPath 证书路径
     * @return 16进制封装的公钥
     * @throws CertificateException
     * @throws IOException
     */

    public static PublicKey getPublicKeyByCer(String cerPath) throws CertificateException, IOException {
        //实例化证书工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(cerPath);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        PublicKey publicKey = certificate.getPublicKey();
        return publicKey;
    }

    /**
     * base64字符串转字节数组
     *
     * @param base64Str
     * @return
     */
    public static byte[] base64String2Byte(String base64Str) {
        return Base64.getDecoder().decode(base64Str);
    }

    /**
     * 字节数组转base64字符串
     *
     * @param bytes
     * @return
     */
    public static String encryptBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * base64字符串转字节数组
     *
     * @param str
     * @return
     */
    public static byte[] decryptBase64(String str) {
        return Base64.getDecoder().decode(str);
    }

    /**
     * 字节数组转base64字符串
     *
     * @param bytes
     * @return
     */
    public static String byte2Base64String(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }

    /**
     * 3DES 加密
     *
     * @param plainText
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String plainText, String key) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(stringToBytes(key));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(spec);
        IvParameterSpec ips = new IvParameterSpec("01234567".getBytes());
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ips);
        return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 使用私钥对数据进行数字签名
     *
     * @param str        待加签字符串
     * @param privateKey 私钥
     * @return java.lang.String
     * @date: 2024/03/26
     */
    public static String sign(String str, String privateKey) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] data = stringToBytes(str);
        messageDigest.update(data);
        byte[] outputDigest_sign = messageDigest.digest();
        PrivateKey priKey = getPrivateKey(privateKey);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(priKey);
        signature.update(outputDigest_sign);
        byte[] bytes = signature.sign();
        return encryptBase64(bytes);
    }

    /**
     * 使用公钥对数据验证签名
     *
     * @param str       待验签字符串(即要加签的内容)
     * @param publicKey 公钥
     * @param sign      数据签名
     * @return boolean
     * @date: 2024/03/26
     */
    public static boolean verify(String str, String publicKey, String sign) throws Exception {
        byte[] data = stringToBytes(str);
        byte[] signBytes = decryptBase64(sign);
        PublicKey pubKey = getPublicKey(publicKey);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(signBytes);
    }

    /**
     * 3DES解密
     *
     * @param encryptedBytes
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(byte[] encryptedBytes, String key) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(stringToBytes(key));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(spec);
        IvParameterSpec ips = new IvParameterSpec("01234567".getBytes());
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ips);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return bytesToString(decryptedBytes);
    }

    /**
     * md5 加密
     * @param str
     * @return
     */
    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            return str;
        } catch (UnsupportedEncodingException e) {
            return str;
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] stringToBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

}
