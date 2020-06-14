package cn.zhaoxi.zxyx.service.feed;

import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.feed.FeedVo;

public interface FeedService {
    Response pageFeed(Long userId, Long photoId, Integer pageSize, Integer feedType);

    Response saveFeed(FeedVo feedVo);
}
