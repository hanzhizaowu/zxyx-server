package cn.zhaoxi.zxyx.service.rss;

import cn.zhaoxi.zxyx.entity.user.TUser;
import cn.zhaoxi.zxyx.mapper.TUserMapper;
import cn.zhaoxi.zxyx.util.constant.Constants;
import cn.zhaoxi.zxyx.util.ParamUtil;
import cn.zhaoxi.zxyx.util.config.RssConfig;
import cn.zhaoxi.zxyx.util.result.ExceptionMsg;
import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.feed.VideoVo;
import cn.zhaoxi.zxyx.vo.user.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author : czx
 * time   : 2020/05/16
 * desc   : 资源服务相关
 * version: 1.0
 */
@Service
public class RssServiceImpl implements RssService {

    private static Logger logger = LoggerFactory.getLogger(RssServiceImpl.class);

    @Resource
    private RssConfig rssConfig;

    @Resource
    private TUserMapper tUserMapper;

    /**
     * 用户图片上传
     */
    @Override
    public Response uploadUserImage(MultipartFile file, Long userId) {
        String[] types = new String[]{".jpg", ".png", ".jpeg"};
        return uploadUserFile(file, rssConfig.getUserPath(), types, userId);
    }

    /**
     * 动态图片上传
     */
    @Override
    public Response uploadFeedImage(MultipartFile[] files, String userSId) {
        String[] types = new String[]{".jpg", ".jpeg", ".webp", ".png", ".gif"};
        Long userId = Long.parseLong(userSId);
        return uploadFile(files, rssConfig.getFeedImagePath(), userId, types);
    }

    @Override
    public Response uploadFeedVideo(MultipartFile[] files, String userSId) {
        String[] types = new String[]{".jpg", ".mp4"};
        Long userId = Long.parseLong(userSId);
        return uploadFile(files, rssConfig.getFeedVideoPath(), userId, types);
    }

    /**
     * 上传用户文件到本地
     */
    private Response uploadUserFile(MultipartFile mfile, String typePath, String[] types, Long userId) {
        Response result = Response.success();

        // 上传目录
        String dirPath = rssConfig.getUploadPath() + typePath;
        logger.warn("upload path : " + dirPath);

        String fileName = mfile.getOriginalFilename();

        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());

        if (!verifyType(fileType, types)) {
            logger.warn("uploadFile : " + fileName);
            result.setCodeAndMsg(ExceptionMsg.ParamIsNull.getCode(), "格式不符合要求，只支持" + appendType(types));
            return result;
        }
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 重命名文件
        fileName = ParamUtil.getUUID() + fileType;
        file = new File(dirPath + fileName);

        try {
            // 保存文件
            mfile.transferTo(file);
        } catch (IOException e) {
            logger.warn("uploadFile : " + e.toString(), e);
            result.setCodeAndMsg(ExceptionMsg.ParamIsNull.getCode(), "上传失败，error：" + e.toString());
            return result;
        }

        // url
        String url = Constants.LOCALURL + typePath + fileName;

        TUser tUser = (TUser) tUserMapper.selectByPrimaryKey(userId);
        if (tUser == null) {
            result.setResponse(ExceptionMsg.LoginNameNotExists);
            return result;
        }
        tUser.setUserAvatar(url);
        tUserMapper.updateByPrimaryKeySelective(tUser);

        UserVo userVo = new UserVo();
        userVo.setUserId(userId);
        userVo.setUserAvatar(url);
        result.setData(userVo);
        return result;
    }

    /**
     * 上传文件到本地
     */
    private Response uploadFile(MultipartFile[] files, String typePath, Long userId, String... types) {
        Response result = Response.success();

        // 此处为返回给前端的访问链接
        // 如果通过nginx访问静态资源，可以给域名或ip的拼接路径，或者给相对路径
        // 如：http://localhost:7070/rss/image/20180516001.png，此为完整访问路径
        // 或：/rss/image/20180516001.png，此为相对路径
        // 我们采用相对路径即typePath

        // 上传目录
        String dirPath = rssConfig.getUploadPath() + typePath + userId + "/";
        logger.warn("upload path : " + dirPath);

        VideoVo videoVo = new VideoVo();
        for (MultipartFile multipartFile : files) {
            String fileName = multipartFile.getOriginalFilename();

            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());

            if (!verifyType(fileType, types)) {
                logger.warn("uploadFile : " + fileName);
                result.setCodeAndMsg(ExceptionMsg.ParamIsNull.getCode(), "格式不符合要求，只支持" + appendType(types));
                return result;
            }
            File file = new File(dirPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            // 重命名文件
            fileName = ParamUtil.getUUID() + fileType;
            file = new File(dirPath + fileName);
            Integer width = 0;
            Integer height = 0;

            try {
                // 保存文件
                multipartFile.transferTo(file);

                if(".jpg".equals(fileType)) {
                    BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream()); // 通过MultipartFile得到InputStream，从而得到BufferedImage
                    if (bufferedImage == null) {
                        // 证明上传的文件不是图片，获取图片流失败，不进行下面的操作
                        logger.warn("buffer image error");
                        result.setCodeAndMsg(ExceptionMsg.ParamIsNull.getCode(), "not picture");
                        return result;
                    }
                    width = bufferedImage.getWidth();
                    height = bufferedImage.getHeight();
                }
            } catch (IOException e) {
                logger.warn("uploadFile : " + e.toString(), e);
                result.setCodeAndMsg(ExceptionMsg.ParamIsNull.getCode(), "上传失败，error：" + e.toString());
                return result;
            }

            String fileUrl = Constants.LOCALURL + typePath + userId + "/" + fileName;
            if(".jpg".equals(fileType)) {
                videoVo.setCoverHeight(height);
                videoVo.setCoverWidth(width);
                videoVo.setVideoCover(fileUrl);
            } else {
                videoVo.setVideoUrl(fileUrl);
            }
        }

        result.setData(videoVo);
        return result;
    }

    /**
     * 验证文件类型
     */
    private boolean verifyType(String type, String... types) {
        for (String str : types) {
            if (str.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接类型字符
     */
    private String appendType(String... types) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = types.length; i < size; i++) {
            if (i == 0) {
                builder.append(types[i]);
            } else {
                builder.append("和").append(types[i]);
            }
        }
        return builder.toString();
    }
}
