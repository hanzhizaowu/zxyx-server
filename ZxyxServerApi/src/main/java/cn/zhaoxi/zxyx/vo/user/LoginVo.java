package cn.zhaoxi.zxyx.vo.user;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 4275087053214489123L;
    private String userName;
    private String userPassword;
}
