package cn.zhaoxi.zxyx.vo.feed;

import cn.zhaoxi.zxyx.model.Feed;
import cn.zhaoxi.zxyx.model.Photo;
import cn.zhaoxi.zxyx.vo.user.UserVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * time   : 2020/05/18
 * desc   : 动态保存参数
 * version: 1.0
 */

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FeedVo implements Serializable {
    private static final long serialVersionUID = 7400826848435916662L;

    private String feedTitle;

    private List<PhotoVo> photos;

    private String feedDescription;

    private Long feedId;

    private UserVo postUser;

    private Long createTime;

    private Long updateTime;

    private Integer feedType;

    public FeedVo(Feed feed) {
        this.feedId = feed.getFeedId();
        this.feedTitle = feed.getFeedTitle();
        this.feedDescription = feed.getFeedDescription();
        this.createTime = feed.getCreateTime();
        this.updateTime = feed.getUpdateTime();
        this.feedType = feed.getFeedType();

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(feed.getPostUser(), userVo);

        List<Photo> orgPhotos = feed.getPhotos();
        List<PhotoVo> destPhotoVos = new ArrayList<>();
        if(!Objects.isNull(orgPhotos)) {
            orgPhotos.forEach(item ->{
                PhotoVo photoVo = new PhotoVo();
                BeanUtils.copyProperties(item, photoVo);
                destPhotoVos.add(photoVo);
            });
            this.photos = destPhotoVos;
        }
    }
}
