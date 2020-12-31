package com.tangmiyi.future.core.utils.encryption;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RSAUtils {

    /**
     * 字符串编码
     */
    public static final String CHARSET = "UTF-8";
    /**
     * 加密算法RSA
     */
    public static final String RSA_ALGORITHM = "RSA";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";


    /**
     * 创建公钥私钥
     *
     * @param keySize 1024 2048
     * @return
     */
    public static Map<String, String> createKeys(int keySize) {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        // 初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());


        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKeyOfPKCS8", privateKeyStr);
        keyPairMap.put("privateKeyOfPKCS1", getPrivateKeyOfPKCS1(privateKeyStr));

        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥pkcs8
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }


    public static String getPrivateKeyOfPKCS1(String privateKey) {
        try {
            byte[] privBytes = Base64.decodeBase64(privateKey);

            PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privBytes);
            ASN1Encodable encodable = pkInfo.parsePrivateKey();
            ASN1Primitive primitive = encodable.toASN1Primitive();
            byte[] privateKeyPKCS1 = primitive.getEncoded();

            return pkcs1ToPem(privateKeyPKCS1, false);
        } catch (IOException e) {
            log.error("PKCS8ToPKCS1 error", e);
            return null;
        } catch (Exception e) {
            log.error("PKCS8ToPKCS1 error", e);
            return null;
        }
    }

    public static String pkcs1ToPem(byte[] pcks1KeyBytes, boolean isPublic) throws Exception {
        String type;
        if (isPublic) {
            type = "RSA PUBLIC KEY";
        } else {
            type = "RSA PRIVATE KEY";
        }

        PemObject pemObject = new PemObject(type, pcks1KeyBytes);
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        String pemString = stringWriter.toString();

        return pemString;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
                    publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
                    privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 分段处理
     *
     * @param cipher
     * @param opmode
     * @param datas
     * @param keySize
     * @return
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param encode     字符集编码
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));

            KeyFactory keyf = KeyFactory.getInstance(RSA_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

            signature.initSign(priKey);
            signature.update(content.getBytes(encode));

            byte[] signed = signature.sign();

            return Base64.encodeBase64URLSafeString(signed);
        } catch (Exception e) {
            throw new RuntimeException("签名发生异常", e);
        }
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @param encode    字符集编码
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String publicKey, String encode) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));

            boolean bverify = signature.verify(Base64.decodeBase64(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        Map<String, String> keyMap = createKeys(1024);
        String publicKey = keyMap.get("publicKey");
        String privateKeyOfPKCS8 = keyMap.get("privateKeyOfPKCS8");
        //String privateKeyOfPKCS1 = keyMap.get("privateKeyOfPKCS1");

        System.out.println("publicKey:\n" + publicKey);
        System.out.println("privateKeyOfPKCS8:\n" + privateKeyOfPKCS8);
        //System.out.println("privateKeyOfPKCS1:\n" + privateKeyOfPKCS1);

        String src = "app_id=35456878542&biz_data={\"order_no\":\"201811307810149579\",\"order_status\":100,\"update_time\":1543566391}&format=json&method=api.v2.order.orderfeedback&sign_type=RSA&timestamp=1543566391&version=1.0";

        String sign = sign(src, privateKeyOfPKCS8, CHARSET);
        System.out.println("sign:\n" + sign);

        boolean verify = verify(src, sign, publicKey, CHARSET);
        System.out.println("verify:" + verify);

        String privateEncrypt = privateEncrypt(src, getPrivateKey(privateKeyOfPKCS8));
        //System.out.println("privateEncrypt:\n" + privateEncrypt);

        String publicDecrypt = publicDecrypt(privateEncrypt, getPublicKey(publicKey));
        //System.out.println("publicDecrypt:\n" + publicDecrypt);

        String publicEncrypt = publicEncrypt(src, getPublicKey(publicKey));
        //System.out.println("publicEncrypt:\n" + publicEncrypt);

        String privateDecrypt = privateDecrypt(publicEncrypt, getPrivateKey(privateKeyOfPKCS8));
        //System.out.println("privateDecrypt:\n" + privateDecrypt);

    }
}
