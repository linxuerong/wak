package com.ruoyi.common.utils;

import com.ruoyi.common.utils.sign.Base64;
import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GoogleAuthenticator {

    public static final String ADMINKEY = "NIB3G463V7T27Q5R";
    public static final String ADMINUSER = "payadmin";

    public static void main(String[] args) {

        //String name = "testuser"; // 用户名

        String secret = GoogleAuthenticator.generateSecretKey(); // 创建谷歌验证key
        //String secret = "";
        //String url = GoogleAuthenticator.getQRBarcodeURL(name, "testhost", secret); // 获取谷歌验证key二维码

        System.out.println("key："+secret);
        //System.out.println("qrcode："+url);

        //System.out.println(authcode("722197", "NIB3G463V7T27Q5R")); // 根据验证码+key 验证是否正确
    }



    public static final int SECRET_SIZE = 10;

    public static final String SEED = "g8GjEvTbW5oVSV7avLBdwIHqGlUYNzKFI7izOF8GwLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";

    public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

    int window_size = 0; // default 3 - max 17 (from google docs) 最多可偏移的时间

    public void setWindowSize(int s) {
        if (s >= 1 && s <= 17)
            window_size = s;
    }

    public static Boolean authcode(String codes, String savedSecret) {
        long code = Long.parseLong(codes);
        long t = System.currentTimeMillis();
        GoogleAuthenticator ga = new GoogleAuthenticator();
        boolean r = ga.check_code(savedSecret, code, t);
        return r;
    }

    public static String generateSecretKey() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
            sr.setSeed(Base64.decode(SEED));
            byte[] buffer = sr.generateSeed(SECRET_SIZE);
            Base32 codec = new Base32();
            byte[] bEncodedKey = codec.encode(buffer);
            String encodedKey = new String(bEncodedKey);
            return encodedKey;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public static String getQRBarcodeURL(String user, String host, String secret) {
        String format = "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
        return String.format(format, user, host, secret);
    }


    public boolean check_code(String secret, long code, long timeMsec) {
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);
        long t = (timeMsec / 1000L) / 30L;
        for (int i = -window_size; i <= window_size; ++i) {
            long hash;
            try {
                hash = verify_code(decodedKey, t + i);
            }catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            if (hash == code) {
                return true;
            }
        }
        return false;
    }

    private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }


    /**
     * 生成二维码内容
     *
     * @param secretKey 密钥
     * @param account   账户名
     * @param issuer    网站地址（可不写）
     * @return
     */
    public static String getQrCodeText(String secretKey, String account, String issuer) {
        String normalizedBase32Key = secretKey.replace(" ", "").toUpperCase();
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode((!StringUtils.isEmpty(issuer) ? (issuer + ":") : "") + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(normalizedBase32Key, "UTF-8").replace("+", "%20")
                    + (!StringUtils.isEmpty(issuer) ? ("&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20")) : "");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
