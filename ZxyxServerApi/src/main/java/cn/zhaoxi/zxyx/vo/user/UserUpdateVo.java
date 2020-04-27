package cn.zhaoxi.zxyx.vo.user;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserUpdateVo {

    private String userName;

    private String userPassword;

    private String userMobile;

    private Integer userSex;

    private String userAvatar;

    private String userSignature;
}