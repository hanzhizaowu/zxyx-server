package cn.zhaoxi.zxyx.vo.feed;

import cn.zhaoxi.zxyx.util.constant.Constants;
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

    private Integer photoHeight;

    private Integer photoWidth;

    public PhotoVo() {}

    public void setAddUrl(String  url) {
        this.url = Constants.RSSURL + url;
    }

    public void setNoAddUrl(String url) {
        this.url = url;
    }
}
