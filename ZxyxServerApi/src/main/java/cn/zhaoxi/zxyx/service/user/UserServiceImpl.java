package cn.zhaoxi.zxyx.service.user;

import cn.zhaoxi.zxyx.entity.user.TUser;
import cn.zhaoxi.zxyx.mapper.TUserMapper;
import cn.zhaoxi.zxyx.util.Crypt.Crypt;
import cn.zhaoxi.zxyx.util.DateUtils;
import cn.zhaoxi.zxyx.util.MD5Utils;
import cn.zhaoxi.zxyx.util.result.ExceptionMsg;
import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.user.LoginVo;
import cn.zhaoxi.zxyx.vo.user.RegisterVo;
import cn.zhaoxi.zxyx.vo.user.UserUpdateVo;
import cn.zhaoxi.zxyx.vo.user.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TUserMapper tUserMapper;

    @Override
    public Response login(LoginVo loginVo) {
        Response result = Response.success();

        String username = loginVo.getUserName();
        String password = loginVo.getUserPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            result.setResponse(ExceptionMsg.ParamIsNull);
            return result;
        }

        // 用户名登录
        TUser param = new TUser();
        param.setUserName(username);
        String decodePsd = Crypt.Decrypt(password);
        param.setUserPassword(MD5Utils.md5(decodePsd));
        TUser tUser = (TUser) tUserMapper.selectOne(param);

        // 支持手机号登录
        if (tUser == null) {
            param.setUserName(null);
            param.setUserMobile(username);
            tUser = (TUser) tUserMapper.selectOne(param);
        }
        if (tUser == null) {
            result.setResponse(ExceptionMsg.LoginNameOrPassWordError);
            return result;
        }

        // 更新登录时间
        tUser.setUserLoginTime(new Date().getTime());
        tUserMapper.updateByPrimaryKeySelective(tUser);

        UserVo userResult = new UserVo();
        userResult.setUserId(tUser.getUserId());
        userResult.setUserToken(tUser.getUserToken());
        userResult.setUserName(tUser.getUserName());
        result.setData(userResult);

        return result;
    }

    @Override
    public Response isLogin(LoginVo loginVo) {
        Response result = Response.success();

        String userToken = loginVo.getUserToken();
        if (StringUtils.isEmpty(userToken)) {
            result.setResponse(ExceptionMsg.ParamIsNull);
            return result;
        }

        TUser param = new TUser();
        param.setUserToken(userToken);
        TUser tUser = (TUser) tUserMapper.selectOne(param);
        if (tUser == null) {
            result.setResponse(ExceptionMsg.UnLogin);
            return result;
        }

        return result;
    }

    @Override
    public Response register(RegisterVo registerVo) {
        Response result = Response.success();

        String username = registerVo.getUserName();
        String phone = registerVo.getUserMobile();
        String password = registerVo.getUserPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            result.setResponse(ExceptionMsg.ParamIsNull);
            return result;
        }

        // 检测手机号
        TUser param = new TUser();
        param.setUserMobile(phone);
        TUser tUser = (TUser) tUserMapper.selectOne(param);
        if (tUser != null) {
            result.setResponse(ExceptionMsg.MobileUsed);
            return result;
        }
        // 检用户名
        param.setUserMobile(null);
        param.setUserName(username);
        tUser = (TUser) tUserMapper.selectOne(param);
        if (tUser != null) {
            result.setResponse(ExceptionMsg.UserNameUsed);
            return result;
        }

        tUser = new TUser();
        tUser.setUserName(username);

        String decodePsd = Crypt.Decrypt(password);
        tUser.setUserPassword(MD5Utils.md5(decodePsd));
        tUser.setUserCreateTime(DateUtils.getCurrentTime());
        tUser.setUserUpdateTime(DateUtils.getCurrentTime());
        tUser.setUserAvatar("img/avater.png");
        tUser.setUserMobile(phone);
        tUserMapper.insertSelective(tUser);

        TUser tUserToken = (TUser) tUserMapper.selectOne(param);
        String token = MD5Utils.md5(tUserToken.getUserUpdateTime()
                + tUserToken.getUserId().toString()
                + tUserToken.getUserPassword()).toLowerCase();
        tUserToken.setUserToken(token);
        tUserMapper.updateByPrimaryKeySelective(tUserToken);
        UserVo userVo = new UserVo();
        userVo.setUserId(tUserToken.getUserId());
        userVo.setUserToken(tUserToken.getUserToken());
        userVo.setUserName(tUserToken.getUserName());
        result.setData(userVo);

        return result;
    }

    @Override
    public Response resetPassword(RegisterVo registerVo) {
        Response result = Response.success();

        String username = registerVo.getUserName();
        String phone = registerVo.getUserMobile();
        String password = registerVo.getUserPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            result.setResponse(ExceptionMsg.ParamIsNull);
            return result;
        }

        // 检查用户存不存在
        TUser param = new TUser();
        param.setUserName(username);
        param.setUserMobile(phone);
        TUser tUser = (TUser) tUserMapper.selectOne(param);
        if (tUser == null) {
            result.setResponse(ExceptionMsg.LoginNameNotExists);
            return result;
        }

        String decodePsd = Crypt.Decrypt(password);
        tUser.setUserPassword(MD5Utils.md5(decodePsd));
        tUser.setUserUpdateTime(DateUtils.getCurrentTime());
        String token = MD5Utils.md5(tUser.getUserUpdateTime()
                + tUser.getUserId().toString()
                + tUser.getUserPassword()).toLowerCase();

        tUserMapper.updateByPrimaryKeySelective(tUser);

        return result;
    }

    @Override
    public Response getUser(Long userId) {
        Response result = Response.success();

        if (userId == null) {
            result.setResponse(ExceptionMsg.ParamIsNull);
            return  result;
        }

        TUser tUser = (TUser) tUserMapper.selectByPrimaryKey(userId);
        if (tUser == null) {
            result.setResponse(ExceptionMsg.LoginNameNotExists);
            return result;
        }

        UserVo userVo = new UserVo();
        userVo.setUserId(tUser.getUserId());
        userVo.setUserName(tUser.getUserName());
        userVo.setUserAvatar(tUser.getUserAvatar());
        userVo.setUserSignature(tUser.getUserSignature());
        result.setData(userVo);
        return result;
    }

    @Override
    public TUser userInfo(Long userId) {
        return (TUser) tUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public Response updateUser(UserUpdateVo userUpdateVo) {
        Response result = Response.success();

        Long userId = userUpdateVo.getUserId();
        if (userId == null) {
            result.setResponse(ExceptionMsg.ParamIsNull);
            return result;
        }

        TUser param = new TUser();

        // 切换数据
        BeanUtils.copyProperties(userUpdateVo, param);
        tUserMapper.updateByPrimaryKeySelective(param);

        return result;
    }
}
