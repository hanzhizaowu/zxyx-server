package cn.zhaoxi.zxyx.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Photo {
    private Long photoId;

    private String url;

    private Integer photoType;
}
