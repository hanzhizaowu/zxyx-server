package cn.zhaoxi.zxyx.vo.user;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RegisterVo {
    private String userName;
    private String userMobile;
    private String userPassword;
}
