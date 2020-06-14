package cn.zhaoxi.zxyx.entity.comment;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_comment")
@Data
public class TComment {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "feedId")
    private Long feedId;

    @Column(name = "content")
    private String content;

    @Column(name = "createTime")
    private Long createTime;
}
