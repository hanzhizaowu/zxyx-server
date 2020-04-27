package cn.zhaoxi.zxyx.controller.user;

import cn.zhaoxi.zxyx.controller.BaseController;
import cn.zhaoxi.zxyx.service.UserService;
import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.user.LoginVo;
import cn.zhaoxi.zxyx.vo.user.RegisterVo;
import cn.zhaoxi.zxyx.vo.user.UserUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "01-user", value = "UserApi", description = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户api测试", notes = "用户api测试接口", httpMethod = "POST", hidden = true)
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public Response hello() {
        logger.info("hello");
        Response result = new Response();
        result.setCode(0);
        result.setMsg("hello");
        return result;
    }

    @ApiOperation(value = "账号注册", notes = "账号注册接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "userMobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType="form", name = "userName", value = "用户名", required = true),
            @ApiImplicitParam(paramType="form", name = "userPassword", value = "密码", required = true)
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(RegisterVo registerVo) {
        if (registerVo == null) {
            return Response.paramIsNull();
        }

        logger.info("param is :" + registerVo.toString());

        return userService.register(registerVo);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "userName", value = "用户名", required = true),
            @ApiImplicitParam(paramType="form", name = "userPassword", value = "密码", required = true)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(LoginVo loginVo) {
        if (loginVo == null) {
            return Response.paramIsNull();
        }

        logger.info("param is :" + loginVo.toString());

        return userService.login(loginVo);
    }

    @ApiOperation(value = "密码重置", notes = "密码重置接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "userMobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType="form", name = "userName", value = "用户名", required = true),
            @ApiImplicitParam(paramType="form", name = "userPassword", value = "密码", required = true)
    })
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public Response resetPassword(RegisterVo registerVo) {
        if (registerVo == null) {
            return Response.paramIsNull();
        }

        logger.info("param is :" + registerVo.toString());

        return userService.resetPassword(registerVo);
    }

    @ApiOperation(value = "用户资料更改", notes = "用户资料更改接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header", name = "userToken", value = "token", required = true),
            @ApiImplicitParam(paramType="form", name = "userMobile", value = "手机号"),
            @ApiImplicitParam(paramType="form", name = "userName", value = "用户名"),
            @ApiImplicitParam(paramType="form", name = "userPassword", value = "密码"),
            @ApiImplicitParam(paramType="form", name = "userSex", value = "性别，0未知，1女2男"),
            @ApiImplicitParam(paramType="form", name = "userAvatar", value = "头像路径"),
            @ApiImplicitParam(paramType="form", name = "userSignature", value = "个性签名")
    })
    @RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
    public Response updateUser(UserUpdateVo userUpdateVo, @ApiIgnore @RequestAttribute(name = "userId") Long userId) {
        if (userUpdateVo == null) {
            return Response.paramIsNull();
        }

        logger.info("userId is {}, param is {}", userId, userUpdateVo.toString());

        return userService.updateUser(userUpdateVo, userId);
    }

    @ApiOperation(value = "用户信息查询", notes = "用户信息查询接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header", name = "userToken", value = "token", required = true),
            @ApiImplicitParam(paramType="form", name = "userId", value = "用户id")
    })
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public Response getUser(@ApiIgnore @RequestAttribute(name = "userId") Long userId) {
        if (userId != null) {
            logger.info("param is :" + userId);

            return userService.getUser(userId);
        } else {
            return Response.paramIsNull();
        }
    }
}
