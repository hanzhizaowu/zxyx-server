package cn.zhaoxi.zxyx.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {
    private Long userId;
    private String userName;
    private String userAvatar;
    private String userToken;
}
