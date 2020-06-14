package cn.zhaoxi.zxyx.entity.feed;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_feed_video")
@Data
public class TFeedVideo {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "feedId")
    private Long feedId;

    @Column(name = "url")
    private String url;

    @Column(name = "videoTime")
    private Integer videoTime;
}
