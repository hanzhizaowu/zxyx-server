package cn.zhaoxi.zxyx.dao;

import cn.zhaoxi.zxyx.model.LetterUser;

import java.util.List;
import java.util.Map;

public interface LetterUserDao {
    List<LetterUser> pageLetterUser(Map<String, Object> map);

    LetterUser getSingleLetterUser(Long postUserId, Long receiverUserId);

    void deleteSingleLetterUser(Long postUserId, Long receiverUserId);
}
