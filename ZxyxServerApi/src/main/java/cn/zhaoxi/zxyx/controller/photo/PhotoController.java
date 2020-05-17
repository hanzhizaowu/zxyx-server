package cn.zhaoxi.zxyx.controller.photo;

import cn.zhaoxi.zxyx.controller.BaseController;
import cn.zhaoxi.zxyx.service.RssService;
import cn.zhaoxi.zxyx.util.result.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : czx
 * time   : 2020/05/16
 * desc   : 资源服务相关
 * version: 1.0
 */
@Api(tags = "02-rss-upload", value = "RssApi", description = "资源上传相关接口")
@RestController
@RequestMapping("/rss/upload")
public class PhotoController extends BaseController {
    @Autowired
    private RssService mssService;

    /**
     * 用户图片上传
     */
    @ApiOperation(value = "用户图片上传", notes = "用户图片上传接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "userId", value = "用户id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType="form", name = "file", value = "图片文件 - 不可测试", required = true, dataType = "File", allowMultiple = true)
    })
    @RequestMapping(value = "/user/image", method = RequestMethod.POST)
    public Response uploadHotelContract(@RequestParam(name = "userId") Long userId,
                                        @RequestParam("file") MultipartFile file) {

        if ((file == null) || (userId == null)) {
            return Response.paramIsNull();
        }

        return mssService.uploadUserImage(file, userId);
    }

    /**
     * 动态图片上传
     */
    @ApiOperation(value = "动态图片上传", notes = "动态图片上传接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header", name = "X-App-Token", value = "token", required = true),
            @ApiImplicitParam(paramType="form", name = "file", value = "图片文件 - 不可测试", required = true, dataType = "File", allowMultiple = true)
    })
    @RequestMapping(value = "/feed/image", method = RequestMethod.POST)
    public Response uploadFeedbackImage(@RequestParam("file") MultipartFile[] files) {

        if (files == null) {
            return Response.paramIsNull();
        }

        logger.info("/feed/image param:" + files.length);

        return mssService.uploadFeedImage(files);
    }
}
