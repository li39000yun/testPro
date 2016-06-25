package com.lyq.test;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

/**
 * Created by lyq on 2016/4/18.
 */
public class TestCodec {

    //AES加密
    public static String AESEncrypt(String data,String base64Key) throws Exception
    {
        try
        {
            byte[] key = Base64.decodeBase64(base64Key);
            //iv箱量采用key的前16位字节
            byte[] iv = Arrays.copyOf(key, 16);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0)
            {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(AESEncrypt("123","hIfjSnR41PrUl4Ko12yv6WK/kqliat2UstTGQrsTz68="));
    }
}
