package cn.zhaoxi.zxyx.vo.user;

import cn.zhaoxi.zxyx.entity.user.TUser;
import cn.zhaoxi.zxyx.model.User;
import cn.zhaoxi.zxyx.util.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserVo implements Serializable {
    private static final long serialVersionUID = 6140370993590882012L;
    private String userName;
    private Long userId;
    private String userToken;
    private String userAvatar;
    private String userMobile;
    private String userEmail;
    private String userPassword;
    private String userNewPassword;
    private Integer userSex;
    private String userSignature;

    public UserVo() {}

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = Constants.RSSURL + userAvatar;
    }
}
