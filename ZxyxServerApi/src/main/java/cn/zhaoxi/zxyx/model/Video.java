package cn.zhaoxi.zxyx.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Video {
    private Long videoId;

    private String videoUrl;

    private Integer videoTime;
}
