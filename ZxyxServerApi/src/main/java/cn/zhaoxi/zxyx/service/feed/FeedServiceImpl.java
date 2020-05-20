package cn.zhaoxi.zxyx.service.feed;

import cn.zhaoxi.zxyx.dao.FeedDao;
import cn.zhaoxi.zxyx.entity.feed.TFeed;
import cn.zhaoxi.zxyx.entity.feed.TFeedPhoto;
import cn.zhaoxi.zxyx.mapper.TFeedMapper;
import cn.zhaoxi.zxyx.mapper.TFeedPhotoMapper;
import cn.zhaoxi.zxyx.model.Feed;
import cn.zhaoxi.zxyx.model.User;
import cn.zhaoxi.zxyx.util.Constants;
import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.feed.FeedVo;
import cn.zhaoxi.zxyx.vo.feed.PhotoVo;
import cn.zhaoxi.zxyx.vo.user.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class FeedServiceImpl implements FeedService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TFeedMapper tFeedMapper;

    @Resource
    private TFeedPhotoMapper tFeedPhotoMapper;

    @Resource
    private FeedDao feedDao;

    @Override
    public Response pageFeed(Long userId, Long photoId, Integer pageSize) {
        Response result = Response.success();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("photoId", photoId);
        map.put("pageSize", pageSize);
        List<Feed> feedList = feedDao.pageFeed(map);
        List<FeedVo> feedVos = new ArrayList<>();

        if (!Objects.isNull(feedList)) {
            feedList.forEach(item -> {
                FeedVo data = new FeedVo(item);
                feedVos.add(data);
            });
        }

        result.setData(feedVos);
        return result;
    }

    @Transactional
    @Override
    public Response saveFeed(FeedVo feedVo) {
        Response result = Response.success();

        String feedInfo = feedVo.getFeedTitle();
        logger.warn("feedtitle is: " + feedInfo + " , userid is: " + feedVo.getPostUser().getUserId() );
        if ((StringUtils.isEmpty(feedInfo)) || (feedVo.getPostUser().getUserId() == null))  {
            logger.warn("param warn : 参数为空");
            result = Response.paramIsNull();
            return result;
        }

        // 保存动态
        TFeed tFeed = new TFeed();
        tFeed.setUserId(feedVo.getPostUser().getUserId());
        tFeed.setFeedTitle(feedInfo);
        tFeed.setFeedDescription(feedVo.getFeedDescription());
        Integer feedType = feedVo.getFeedType();
        if(feedType == null) {
            feedType = 0;
        }
        tFeed.setFeedType(feedType);
        Long time = System.currentTimeMillis();
        tFeed.setCreateTime(time);
        tFeed.setUpdateTime(time);
        tFeedMapper.insertSelective(tFeed);

        // 保存图片
        List<PhotoVo> photoList = feedVo.getPhotos();
        if (photoList != null && photoList.size() != 0) {
            for (PhotoVo photoVo : photoList) {
                TFeedPhoto tFeedPhoto = new TFeedPhoto();
                tFeedPhoto.setFeedId(tFeed.getId());
                tFeedPhoto.setUrl(photoVo.getUrl());
                tFeedPhoto.setState(Constants.PHOTOUNDEAL);
                tFeedPhoto.setCreateTime(time);
                tFeedPhoto.setUpdateTime(time);
                tFeedPhoto.setPhotoType(photoVo.getPhotoType());
                tFeedPhotoMapper.insertSelective(tFeedPhoto);
                logger.info("photo {}", photoVo.getUrl());
            }
        }

        return result;
    }
}
