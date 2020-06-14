package cn.zhaoxi.zxyx.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Comment {
    private Long commentId;
    private User user;
    private String content;
    private Long createTime;
}
