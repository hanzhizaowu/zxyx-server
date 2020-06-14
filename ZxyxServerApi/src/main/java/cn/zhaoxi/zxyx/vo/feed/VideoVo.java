package cn.zhaoxi.zxyx.vo.feed;

import cn.zhaoxi.zxyx.util.constant.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VideoVo implements Serializable {
    private static final long serialVersionUID = 621144889672103076L;

    private Long videoId;

    private String videoUrl;

    private Integer videoTime;

    private String videoCover;

    private Integer coverWidth;

    private Integer coverHeight;

    public VideoVo() {

    }

    public void setAddUrl(String url) {
        this.videoUrl = Constants.RSSURL + url;
    }
}
