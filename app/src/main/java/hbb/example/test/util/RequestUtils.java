package hbb.example.test.util;

import android.content.Context;
import android.util.Base64;

import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.android.gms.vision.clearcut.LogUtils;


import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hbb.example.test.R;

/**
 *
 * @author Administrator
 * @date 2018/4/8
 */

public class RequestUtils {

    public static final String SUCCESS="0000";

    public static InnerParam newParams(){
        return new InnerParam();
    }




    /**
     * 网络请求加签函数
     * 加签逻辑为base64(md5(key1=urlEncoder(value1)&key2=urlEncoder(value2)...SIGNATURE_KEY))
     * @param param 加签函数字典
     * @return 加签后的结果
     */
    public static String encryptParam(Map<String, String> param) {
        String encrypt = "";
        try {
            Set<String> set = param.keySet();
            String[] keys = new String[set.size()];
            set.toArray(keys);
            Arrays.sort(keys);
            StringBuilder builder = new StringBuilder();
            for (int numIndex = 0; numIndex < keys.length; numIndex++) {
                String key = keys[numIndex];
                builder.append(key + "=" + URLEncoder.encode(param.get(key), "UTF-8")  + "&");
            }
            builder.deleteCharAt(builder.length() - 1);

            String transUrlEncode = URLEncoder.encode(builder.toString(), "UTF-8");
            //添加尾部key
            String appendUrlEncode = transUrlEncode + "YgAqeZq1eM#6#xTWkjtEGO%Ol4oxzBIlYI#k75HJml4bCr!F8YTqySDueKRY%1GB";

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(appendUrlEncode.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            encrypt = Base64.encodeToString(strBuf.toString().getBytes(), Base64.DEFAULT);

            if(encrypt.indexOf("\n")!=-1){
                encrypt =  encrypt.replace("\n","");
            }


        }catch (Exception e){e.printStackTrace();}

        return encrypt;
    }


    public static class InnerParam{

        Map<String,String> mMap;

        public InnerParam() {
            this(false);
        }


        public InnerParam(boolean isUser) {
            mMap=new HashMap<>();
            if(!isUser){
                mMap.put("user_code","1");
                mMap.put("key","2");
            }
        }



        public InnerParam addParams(String key,String value){
            mMap.put(key,value);
            return this;
        }

        public InnerParam addParams(String key,int value){
            mMap.put(key,value+"");
            return this;
        }

        public InnerParam addParams(String key,long value){
            mMap.put(key,value+"");
            return this;
        }

        public Map<String,String> create(){
            String signature=encryptParam(mMap);
            mMap.put("signature",signature);
//            LogUtils.e(mMap);
            return mMap;
        }

    }


}
