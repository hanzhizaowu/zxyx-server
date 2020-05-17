package cn.zhaoxi.zxyx.service;

import cn.zhaoxi.zxyx.entity.user.TUser;
import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.user.LoginVo;
import cn.zhaoxi.zxyx.vo.user.RegisterVo;
import cn.zhaoxi.zxyx.vo.user.UserUpdateVo;
import cn.zhaoxi.zxyx.vo.user.UserVo;

/**
 * @author czx
 * @version 1.0.0
 * @time 2020/04/26
 * @description 用户相关的service接口
 */
public interface UserService {
    Response login(LoginVo loginVo);

    Response isLogin(LoginVo loginVo);

    Response register(RegisterVo registerVo);

    Response resetPassword(RegisterVo registerVo);

    Response getUser(Long userId);

    TUser userInfo(Long userId);

    Response updateUser(UserUpdateVo userUpdateVo);
}
