package com.example.Genius.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtils {
    private static final Logger logger = LoggerFactory.getLogger(GeneralUtils.class);

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for(int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }
    public static boolean isEmailAddress(String address){
        // 邮箱验证规则
        String regEx = "[a-zA-Z0-9]+@[a-zA-z0-9]*\\.com";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(address);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }
//    public static void main(String[] args){
//        System.out.println(isEmailAddress("791427340@qq.com"));
//        System.out.println(isEmailAddress("791427340@qq+.com"));
//        System.out.println(isEmailAddress("@qq.com"));
//        System.out.println(isEmailAddress("79142sdfa7340@qq.com"));
//        System.out.println(isEmailAddress("ada791427340@qq.com"));
//        System.out.println(isEmailAddress("791427340@125qq.com"));
//        System.out.println(isEmailAddress("791427340qq.com"));
//        System.out.println(isEmailAddress("791427340@qqcom"));
//    }
//    public static Date parseFromString(String time){  // FIXME:这里返回值有问题，研究一下
//        SimpleDateFormat formatter = new SimpleDateFormat();
//        ParsePosition pos = new ParsePosition(0);
//        Date newTime = formatter.parse(time,pos); //不带new的话实际上只是指向这个方法返回的对象的内存地址
//        Date newTime2 = new Date();
//        return newTime2;
//    }
}
