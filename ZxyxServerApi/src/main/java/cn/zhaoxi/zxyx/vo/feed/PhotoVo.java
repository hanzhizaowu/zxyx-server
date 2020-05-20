package cn.zhaoxi.zxyx.vo.feed;

import cn.zhaoxi.zxyx.util.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PhotoVo implements Serializable {
    private static final long serialVersionUID = 5455161420596945963L;

    private Long photoId;

    private String url;

    private Integer photoType;

    public void setUrl(String  url) {
        this.url = Constants.RSSURL + url;
    }
}
