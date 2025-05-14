package cn.edu.xmu.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * MD5工具类
 */
@Component
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    //加密所用盐
    private static final String salt = "liyejia0423";

    //输入密码到中间密码第一次MD5加密
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    //中间密码到数据库密码第二次MD5加密
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    //输入密码到数据库密码的加密（以上两种的融合）
    public static String inputPassToDBPass(String inputPass, String salt) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }


    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("123456", salt));
    }
}
