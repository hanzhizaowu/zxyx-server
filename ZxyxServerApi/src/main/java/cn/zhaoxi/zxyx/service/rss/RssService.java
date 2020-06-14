package cn.zhaoxi.zxyx.service.rss;

import cn.zhaoxi.zxyx.util.result.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : czx
 * time   : 2020/05/16
 * desc   : 资源服务相关
 * version: 1.0
 */
public interface RssService {

    Response uploadUserImage(MultipartFile file, Long userId);

    Response uploadFeedImage(MultipartFile[] files, String userId);

    Response uploadFeedVideo(MultipartFile[] files, String userId);
}
