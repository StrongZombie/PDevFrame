package hbb.example.test.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class MD5Utils {
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
            }
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


    public static String getMD5(String str) {
        //MD5是加密方式
        MessageDigest messageDigest = null;
        try {
            /**
             * 获取MD5加密方式对象
             */
            messageDigest = MessageDigest.getInstance("MD5");
            /**
             * 重新设置摘要以供进一步使用
             */
            messageDigest.reset();
            /**
             * 使用指定的字节组更新摘要
             */
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**
         * 将二进制的byte数组进行加密
         * 0xFF & byteArray[i]  进行加密 & int值的 255   000...00010000001
         *
         * toHexString   将int值转换为16进制
         *
         * String hexString = Integer.toHexString(result) + 1 ;//这里加1可以进行2次加密
         */
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        //16位加密，从第9位到25位
//        return md5StrBuff.substring(8, 24).toString().toUpperCase();
        return md5StrBuff.substring(0, 32).toString().toLowerCase();
    }
}
