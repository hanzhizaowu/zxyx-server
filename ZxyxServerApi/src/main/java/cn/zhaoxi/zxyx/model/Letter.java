package cn.zhaoxi.zxyx.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Letter {
    private Long letterId;
    private Long postUserId;
    private Long receiverUserId;
    private String content;
    private Long createTime;
    private Integer state;
}
