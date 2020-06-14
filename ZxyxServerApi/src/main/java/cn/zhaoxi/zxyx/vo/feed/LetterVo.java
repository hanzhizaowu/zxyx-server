package cn.zhaoxi.zxyx.vo.feed;

import cn.zhaoxi.zxyx.entity.message.TLetter;
import cn.zhaoxi.zxyx.model.Letter;
import cn.zhaoxi.zxyx.model.User;
import cn.zhaoxi.zxyx.vo.user.UserVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LetterVo implements Serializable {
    private static final long serialVersionUID = 6755389857962186066L;

    private Long letterId;
    private UserVo postUser;
    private UserVo receiverUser;
    private String content;
    private Long createTime;
    private Integer letterState; //0:未读;1:已读

    public LetterVo(Letter letter) {
        this.letterId = letter.getLetterId();
        this.content = letter.getContent();
        this.createTime = letter.getCreateTime();
    }

    public LetterVo(TLetter tLetter) {
        this.letterId = tLetter.getId();
        this.createTime = tLetter.getCreateTime();
        this.content = tLetter.getContent();
    }
}
