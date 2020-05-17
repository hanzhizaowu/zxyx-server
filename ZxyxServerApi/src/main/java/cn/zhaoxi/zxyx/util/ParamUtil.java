package cn.zhaoxi.zxyx.util;

import java.util.UUID;

/**
 * 参数相关工具类
 * @author czx
 */
public class ParamUtil {
    /**
     * 获取uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
