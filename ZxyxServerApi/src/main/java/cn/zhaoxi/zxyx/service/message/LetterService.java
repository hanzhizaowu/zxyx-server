package cn.zhaoxi.zxyx.service.message;

import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.feed.LetterVo;

public interface LetterService {
    Response pageLetter(Long letterId, Long postUserId, Long receiverUserId, Integer pageSize);

    Response publishLetter(Long postUserId, Long receiverUserId, String content);

    Response pageMessage(Long userId, Long letterUserTime, Integer pageSize);
}
