package cn.zhaoxi.zxyx.entity.feed;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_feed_photo")
@Data
public class TFeedPhoto {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "feedId")
    private Long feedId;

    @Column(name = "url")
    private String url;

    @Column(name = "state")
    private Integer state;

    @Column(name = "createTime")
    private Long createTime;

    @Column(name = "updateTime")
    private Long updateTime;

    @Column(name = "photoHeight")
    private Integer photoHeight;

    @Column(name = "photoWidth")
    private Integer photoWidth;
}
