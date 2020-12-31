package com.tangmiyi.future.core.utils.encryption;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public class AESEncryptUtils {
    private static final Logger logger = LoggerFactory.getLogger(AESEncryptUtils.class);
    private static final String UTF8 = "UTF-8";
    private static final String AES_ALG = "AES";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static final byte[] AES_IV = initIv(AES_CBC_PCK_ALG);
    static {
        fixKeyLength();
    }
    /**
     * 针对AES算法密钥长度限制进行hack
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void fixKeyLength() {
        String errorString = "Failed manually overriding key-length permissions.";
        int newMaxKeyLength;
        try {
            if ((newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES")) < 256) {
                Class c = Class.forName("javax.crypto.CryptoAllPermissionCollection");
                Constructor con = c.getDeclaredConstructor();
                con.setAccessible(true);
                Object allPermissionCollection = con.newInstance();
                Field f = c.getDeclaredField("all_allowed");
                f.setAccessible(true);
                f.setBoolean(allPermissionCollection, true);
                c = Class.forName("javax.crypto.CryptoPermissions");
                con = c.getDeclaredConstructor();
                con.setAccessible(true);
                Object allPermissions = con.newInstance();
                f = c.getDeclaredField("perms");
                f.setAccessible(true);
                ((Map) f.get(allPermissions)).put("*", allPermissionCollection);
                c = Class.forName("javax.crypto.JceSecurityManager");
                f = c.getDeclaredField("defaultPolicy");
                f.setAccessible(true);
                Field mf = Field.class.getDeclaredField("modifiers");
                mf.setAccessible(true);
                mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                f.set(null, allPermissions);
                newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES");
            }
        } catch (Exception e) {
            throw new RuntimeException(errorString, e);
        }
        if (newMaxKeyLength < 256)
            throw new RuntimeException(errorString); // hack failed
    }
    /**
     * AES加密
     *
     * @param content
     * @param appKey
     * @return
     */
    public static String aesEncrypt(String content, String appKey) {
        return aesEncrypt(content, appKey, UTF8);
    }
    /**
     * AES解密
     *
     * @param content
     * @param appKey
     * @return
     */
    public static String aesDecrypt(String content, String appKey) {
        return aesDecrypt(content, appKey, UTF8);
    }
    /**
     * AES加密
     *
     * @param content
     * @param appKey
     * @param charset
     * @return
     */
    public static String aesEncrypt(String content, String appKey, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(Base64.decodeBase64(appKey.getBytes()), AES_ALG), iv);
            byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));
            return new String(Base64.encodeBase64(encryptBytes));
        } catch (Exception e) {
            logger.error("AES加密失败：Aescontent = " + content + "; charset = " + charset, e);
            return null;
        }
    }
    /**
     * AES解密
     *
     * @param content
     * @param appKey
     * @param charset
     * @return
     */
    public static String aesDecrypt(String content, String appKey, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(appKey.getBytes()),AES_ALG), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.decodeBase64(content.getBytes()));
            return new String(cleanBytes, charset);
        } catch (Exception e) {
            logger.error("AES解密失败：Aescontent = " + content + "; charset = " + charset, e);
            return null;
        }
    }
    /**
     * 初始向量的方法, 全部为0. 针对AES算法的话,IV值一定是128位的(16字节).
     *
     * @param fullAlg
     * @return
     */
    private static byte[] initIv(String fullAlg) {
        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        } catch (Exception e) {
            int blockSize = 16;
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        }
    }
}
