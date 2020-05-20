package cn.zhaoxi.zxyx.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_user")
@Data
public class TUser {
    /**
     * 用户id
     */
    @Id
    @Column(name = "id")
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "userName")
    private String userName;

    /**
     * 用户密码
     */
    @Column(name = "password")
    private String userPassword;

    /**
     * 用户手机号
     */
    @Column(name = "phone")
    private String userMobile;

    /**
     * 性别，默认0，1女2男
     */
    @Column(name = "sex")
    private boolean userSex;

    /**
     * 用户头像
     */
    @Column(name = "avatar")
    private String userAvatar;

    /**
     * 用户签名
     */
    @Column(name = "signature")
    private String userSignature;

    /**
     * 用户token
     */
    @Column(name = "token")
    private String userToken;

    /**
     * 登录时间
     */
    @Column(name = "login_time")
    private Long userLoginTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long userCreateTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Long userUpdateTime;

    public TUser() {
    }

}
