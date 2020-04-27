package cn.zhaoxi.zxyx.util;

import org.springframework.util.DigestUtils;

/**
 * @author czx
 * @version 1.0.0
 * @time 2020/04/06
 * @description md5加密
 */
public class MD5Utils {
    public static String md5(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }
}
