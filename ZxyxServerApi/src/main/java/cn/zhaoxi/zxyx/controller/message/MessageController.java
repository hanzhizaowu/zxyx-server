package cn.zhaoxi.zxyx.controller.message;

import cn.zhaoxi.zxyx.service.message.LetterService;
import cn.zhaoxi.zxyx.util.result.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author czx
 * 消息相关
 */
@Api(tags = "04-message", value = "MessageApi", description = "消息相关接口")
@RestController
@RequestMapping("/message")
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LetterService letterService;

    /**
     * 私信分页
     */
    @ApiOperation(value = "分页私信", notes = "分页私信接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "letterId", dataType = "Long", value = "查询私信id", required = true),
            @ApiImplicitParam(paramType="form", name = "postUserId", dataType = "Long", value = "私信发布用户id", required = true),
            @ApiImplicitParam(paramType="form", name = "pageSize", dataType = "Integer", value = "页容量", defaultValue = "10"),
            @ApiImplicitParam(paramType="form", name = "receiverUserId", dataType = "Long", value = "私信接收用户id", required = true)
    })
    @RequestMapping(value = "/letter/page", method = RequestMethod.POST)
    public Response pageLetter(@RequestParam(name = "letterId") Long letterId,
                             @RequestParam(name = "postUserId") Long postUserId,
                             @RequestParam(name = "pageSize") Integer pageSize,
                             @RequestParam(name = "receiverUserId") Long receiverUserId) {

        if ((letterId == null) || (postUserId == null) || (receiverUserId == null)) {
            return Response.paramIsNull();
        }

        logger.info("param letterId is :" + letterId + " ,postUserId is:" + postUserId + " , pageSize is:" + pageSize +
                ",receiverUserId is:" + receiverUserId);

        return letterService.pageLetter(letterId, postUserId, receiverUserId, pageSize);
    }

    /**
     * 私信保存和发布
     */
    @ApiOperation(value = "发布私信", notes = "发布私信接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "postUserId", dataType = "Long", value = "私信发布用户id", required = true),
            @ApiImplicitParam(paramType="form", name = "receiverUserId", dataType = "Long", value = "私信接收用户id", required = true),
            @ApiImplicitParam(paramType = "form", name = "content", dataType = "VARCHAR", value = "内容", required = true)
    })
    @RequestMapping(value = "/letter/publish", method = RequestMethod.POST)
    public Response publishLetter(@RequestParam(name = "content") String content,
                                  @RequestParam(name = "postUserId") Long postUserId,
                                  @RequestParam(name = "receiverUserId") Long receiverUserId) {

        if (StringUtils.isEmpty(content) || Objects.isNull(postUserId) || Objects.isNull(receiverUserId)) {
            return Response.paramIsNull();
        }

        logger.info("param content is :" + content + " ,postUserId is:" + postUserId +
                ",receiverUserId is:" + receiverUserId);

        return letterService.publishLetter(postUserId, receiverUserId, content);
    }

    /**
     * 消息分页
     */
    @ApiOperation(value = "分页消息", notes = "分页消息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "userId", dataType = "Long", value = "查询用户id", required = true),
            @ApiImplicitParam(paramType="form", name = "letterUserTime", dataType = "Long", value = "消息用户分页参数", required = true),
            @ApiImplicitParam(paramType="form", name = "pageSize", dataType = "Integer", value = "页容量", defaultValue = "10")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Response pageMessage(@RequestParam(name = "userId") Long userId,
                               @RequestParam(name = "letterUserTime") Long letterUserTime,
                               @RequestParam(name = "pageSize") Integer pageSize) {

        if (userId == null) {
            return Response.paramIsNull();
        }

        logger.info("param userId is :" + userId + " ,letterUserTime is:" + letterUserTime + " , pageSize is:" + pageSize);

        return letterService.pageMessage(userId, letterUserTime, pageSize);
    }
}
