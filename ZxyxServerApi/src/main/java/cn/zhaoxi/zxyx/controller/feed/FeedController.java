package cn.zhaoxi.zxyx.controller.feed;

import cn.zhaoxi.zxyx.service.feed.FeedService;
import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.feed.FeedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author czx
 * 动态相关
 */
@Api(tags = "02-feed", value = "FeedApi", description = "动态相关接口")
@RestController
@RequestMapping("/feed")
public class FeedController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FeedService feedService;

    /**
     * 动态分页
     */
    @ApiOperation(value = "分页动态", notes = "分页动态接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "userId", dataType = "Long", value = "查询用户id", required = true),
            @ApiImplicitParam(paramType="form", name = "photoId", dataType = "Long", value = "帖子id阈值", defaultValue = "0"),
            @ApiImplicitParam(paramType="form", name = "pageSize", dataType = "Integer", value = "页容量", defaultValue = "10"),
            @ApiImplicitParam(paramType="form", name = "feedType", dataType = "Integer", value = "帖子类型", defaultValue = "0")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Response pageFeed(@RequestParam(name = "userId") Long userId,
                             @RequestParam(name = "photoId") Long photoId,
                             @RequestParam(name = "pageSize") Integer pageSize,
                             @RequestParam(name = "feedType") Integer feedType) {

        if (userId == null) {
            return Response.paramIsNull();
        }

        logger.info("param userId is :" + userId + " ,photoId is:" + photoId + " , pageSize is:" + pageSize +
                "feedType is:" + feedType);

        return feedService.pageFeed(userId, photoId, pageSize, feedType);
    }

    /**
     * 动态保存
     */
    @ApiOperation(value = "发布动态", notes = "发布动态接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "feedVo", value = "动态内容", required = true)
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response saveFeed(@ApiIgnore @RequestBody FeedVo feedVo) {

        if (feedVo == null) {
            return Response.paramIsNull();
        }

        logger.info("param is :" + feedVo.toString());

        return feedService.saveFeed(feedVo);
    }
}
