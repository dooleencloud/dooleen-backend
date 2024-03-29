package com.dooleen.common.core.utils.aes;

/**
 * AES 128bit 加密解密工具类
 * @author liqh
 */

import com.dooleen.common.core.enums.BizResponseEnum;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AESUtil {

    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同，也可以不同!
    private static String KEY;
    private static String IV;
    private static boolean ENCODE;
    private static boolean PROTECTFLAG;
    private static int PROTECTTIME;
    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/NoPadding";

    /**
     * 因为静态变量不能直接从配置文件通过@value获取，所以采用此方法
     * @param key
     */
    @Value("${AES.KEY}")
    public void AESkey(String key){
        KEY = key;
    }
    @Value("${AES.IV}")
    public void AESIv(String iv){
        IV = iv;
    }

    @Value("${AES.ENCODE}")
    public void AESEncode(boolean encode){
        ENCODE = encode;
    }

    @Value("${PROTECT.FLAG}")
    public void PROTECTFlag(boolean protectFlag){
        PROTECTFLAG = protectFlag;
    }

    @Value("${PROTECT.TIME}")
    public void PROTECTTime(int protectTime){
        PROTECTTIME = protectTime;
    }

    /**
     * 加密方法 返回base64加密字符串
     * 和前端保持一致
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);//"算法/模式/补码方式"NoPadding PKCS5Padding
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            BizResponseEnum.ENCODE_EXCEPTION.assertIsTrue(false,null);
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv) throws Exception {
        try {
//        byte[] encrypted1 = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(new Base64().decode(data));
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            BizResponseEnum.DECODE_EXCEPTION.assertIsTrue(false,null);
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception {
        return decrypt(data, KEY, IV);
    }

    //返回是否加密
    public static boolean encode(){
        return ENCODE;
    }

    //返回是否请求保护
    public static boolean protectFlag(){
        return PROTECTFLAG;
    }

    //返回是否请求保护
    public static int protectTime(){
        return PROTECTTIME;
    }

    /**
     * 测试
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String args[]) throws Exception {
        Map map = new HashMap<>();
        map.put("code", "0");
        map.put("data", "3232dsfs");
        String test1 = JSON.toJSONString(map);
        String test =new String(test1.getBytes(),"UTF-8");
        String data = null;
        data = encrypt(test);
        System.out.println("数据："+test);
        System.out.println("加密："+data);
        String jiemi =decrypt(data).trim();
        System.out.println("解密："+jiemi);

    }
}
