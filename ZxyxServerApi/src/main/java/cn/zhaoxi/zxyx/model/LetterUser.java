package cn.zhaoxi.zxyx.model;

import lombok.Data;

@Data
public class LetterUser {
    private Long id;
    private Long receiverUserId;
    private Long postUserId;
    private Long updateTime;
}
