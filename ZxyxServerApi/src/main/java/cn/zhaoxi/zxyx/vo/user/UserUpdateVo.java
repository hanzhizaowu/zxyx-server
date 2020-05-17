package cn.zhaoxi.zxyx.vo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserUpdateVo {

    private String userName;

    private String userPassword;

    private String userMobile;

    private Integer userSex;

    private String userSignature;

    private Long userId;
}