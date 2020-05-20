package cn.zhaoxi.zxyx.dao;

import cn.zhaoxi.zxyx.model.Feed;

import java.util.List;
import java.util.Map;

public interface FeedDao {
    List<Feed> pageFeed(Map<String, Object> map);
}
