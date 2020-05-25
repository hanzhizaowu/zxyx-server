package cn.zhaoxi.zxyx.entity.feed;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_feed")
@Data
public class TFeed {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "feedTitle")
    private String feedTitle;

    @Column(name = "feedDescription")
    private String feedDescription;

    @Column(name = "createTime")
    private Long createTime;

    @Column(name = "updateTime")
    private Long updateTime;

    @Column(name = "feedType")
    private Integer feedType;

    @Column(name = "feedCover")
    private String feedCover;

    @Column(name = "coverHeight")
    private Integer coverHeight;

    @Column(name = "coverWidth")
    private Integer coverWidth;
}
