package cn.zhaoxi.zxyx.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author : czx
 * time   : 2020/05/16
 * desc   : 资源服务相关
 * version: 1.0
 */
@Component
public class RssConfig {
    /**
     * linux文件上传目录
     */
    @Value("${rss-path.linuxPath}")
    private String linuxPath;

    /**
     * 获取文件上传目录
     */
    public String getUploadPath() {
        return linuxPath;
    }

    /**
     * 用户图片目录
     */
    public String getUserPath() {
        return "user/";
    }

    /**
     * 动态图片目录
     */
    public String getFeedPath() {
        return "feed/";
    }
}
