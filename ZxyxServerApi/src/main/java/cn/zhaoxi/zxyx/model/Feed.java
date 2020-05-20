package cn.zhaoxi.zxyx.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Feed {
    private Long feedId;
    /**
     * 用户信息
     */
    private User postUser;
    private String feedTitle;
    private String feedDescription;
    /**
     * 相册
     */
    private List<Photo> photos;
    private Long createTime;
    private Long updateTime;
    private Integer feedType;
}
