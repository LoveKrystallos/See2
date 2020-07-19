package com.example.see2.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

public class Parameters {
    public static HashMap<String, String> parametersMap(){
        HashMap<String, String> pramrmap = null;
        try {
            //创建Map集合，用来存放请求时所要用到的参数
            pramrmap = new HashMap<>();

            //系统当前的Unix时间戳
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            //随机数
            String nonce = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            //秘钥
            String key="K;9)Bq|ScMF1h=Vp5uA-G87d(_fi[aP,.w^{vQ:W";

            //创建数组，存放数据
            String[] arrayOfstring=new String[3];
            arrayOfstring[0]=key;
            arrayOfstring[1]=timestamp;
            arrayOfstring[2]=nonce;
            //排序
            Arrays.sort(arrayOfstring);
            //利用StringBuffer进行拼接
            StringBuffer sb = new StringBuffer();
            for (String s : arrayOfstring) {
                sb.append(s);
            }
            String keystr = sb.toString();

            //获取SHA-1
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(keystr.getBytes());
            byte[] bytes = digest.digest();
            StringBuffer sf = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String s = Integer.toHexString(bytes[i] & 0xff);
                if (s.length()<2){
                    sf.append(0);
                }
                sf.append(s);
            }
            String singnature = sf.toString();

            //将全部参数放入集合
            pramrmap.put("signature",singnature);
            pramrmap.put("timestamp",timestamp);
            pramrmap.put("nonce",nonce);
            pramrmap.put("from","android");
            pramrmap.put("lang","zh");
        return pramrmap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pramrmap;
    }
}
