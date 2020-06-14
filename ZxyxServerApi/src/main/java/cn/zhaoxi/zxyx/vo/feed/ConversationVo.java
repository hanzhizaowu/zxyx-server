package cn.zhaoxi.zxyx.vo.feed;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ConversationVo implements Serializable {
    private static final long serialVersionUID = 5473492518793050263L;

    private Integer unReadMsgCnt;
    private LetterVo latestMessage;
}
