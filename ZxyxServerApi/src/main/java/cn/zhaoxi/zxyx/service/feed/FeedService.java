package cn.zhaoxi.zxyx.service.feed;

import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.feed.FeedVo;
import org.springframework.stereotype.Service;

public interface FeedService {
    Response pageFeed(Long userId, Long photoId, Integer pageSize);

    Response saveFeed(FeedVo feedVo);
}
