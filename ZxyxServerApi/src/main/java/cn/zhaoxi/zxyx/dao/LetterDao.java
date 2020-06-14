package cn.zhaoxi.zxyx.dao;

import cn.zhaoxi.zxyx.model.Letter;

import java.util.List;
import java.util.Map;

public interface LetterDao {
    Letter getSingleLetter(Map<String, Object> map);

    Integer countLetter(Map<String, Object> map);

    List<Letter> pageLetter(Map<String, Object> map);
}
