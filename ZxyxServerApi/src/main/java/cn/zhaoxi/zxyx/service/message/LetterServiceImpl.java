package cn.zhaoxi.zxyx.service.message;

import cn.zhaoxi.zxyx.dao.LetterDao;
import cn.zhaoxi.zxyx.dao.LetterUserDao;
import cn.zhaoxi.zxyx.entity.message.TLetter;
import cn.zhaoxi.zxyx.entity.message.TLetterUser;
import cn.zhaoxi.zxyx.entity.user.TUser;
import cn.zhaoxi.zxyx.mapper.TLetterMapper;
import cn.zhaoxi.zxyx.mapper.TLetterUserMapper;
import cn.zhaoxi.zxyx.mapper.TUserMapper;
import cn.zhaoxi.zxyx.model.Letter;
import cn.zhaoxi.zxyx.model.LetterUser;
import cn.zhaoxi.zxyx.util.client.MyMqttClient;
import cn.zhaoxi.zxyx.util.result.Response;
import cn.zhaoxi.zxyx.vo.feed.ConversationVo;
import cn.zhaoxi.zxyx.vo.feed.LetterVo;
import cn.zhaoxi.zxyx.vo.user.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class LetterServiceImpl implements LetterService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TLetterMapper tLetterMapper;

    @Resource
    private TLetterUserMapper tLetterUserMapper;

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private LetterDao letterDao;

    @Resource
    private LetterUserDao letterUserDao;

    @Resource
    private MyMqttClient myMqttClient;

    @Override
    public Response pageLetter(Long letterId, Long postUserId, Long receiverUserId, Integer pageSize) {
        Response result = Response.success();

        Map<String, Object> map = new HashMap<>();
        map.put("letterId", letterId);
        map.put("postUserId", postUserId);
        map.put("receiverUserId", receiverUserId);
        map.put("pageSize", pageSize);
        List<Letter> receiveletterList = letterDao.pageLetter(map);

        map.put("postUserId", receiverUserId);
        map.put("receiverUserId", postUserId);
        List<Letter> postletterList = letterDao.pageLetter(map);

        List<Letter> letterList = new ArrayList<Letter>();
        letterList.addAll(postletterList);
        letterList.addAll(receiveletterList);

        List<LetterVo> letterVos = new ArrayList<>();

        if ((!Objects.isNull(letterList)) && (letterList.size() > 0)) {
            Collections.sort(letterList, new Comparator<Letter>() {
                @Override
                public int compare(Letter o1, Letter o2) {
                    if(o1.getCreateTime() > o2.getCreateTime()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });

            if((pageSize != null) && (pageSize > 0) && (letterList.size() > pageSize)) {
                letterList.subList(0, pageSize - 1);
            } else if(((pageSize == null) || (pageSize.intValue() == 0)) && (letterList.size() > 10)) {
                letterList.subList(0,9);
            }

            TUser postUser = (TUser) tUserMapper.selectByPrimaryKey(postUserId);
            UserVo postUserVo = new UserVo();
            postUserVo.setUserId(postUser.getUserId());
            postUserVo.setUserName(postUser.getUserName());
            postUserVo.setUserAvatar(postUser.getUserAvatar());

            TUser receiverUser = (TUser) tUserMapper.selectByPrimaryKey(receiverUserId);
            UserVo receiverUserVo = new UserVo();
            receiverUserVo.setUserId(receiverUser.getUserId());
            receiverUserVo.setUserName(receiverUser.getUserName());
            receiverUserVo.setUserAvatar(receiverUser.getUserAvatar());

            letterUserDao.deleteSingleLetterUser(postUserId, receiverUserId);

            letterList.forEach(item -> {
                LetterVo data = new LetterVo(item);
                data.setPostUser(postUserVo);
                data.setReceiverUser(receiverUserVo);
                letterVos.add(data);
            });
        }

        result.setData(letterVos);
        return result;
    }

    @Override
    public Response publishLetter(Long postUserId, Long receiverUserId, String content) {
        Response result = Response.success();

        TLetter tLetter = new TLetter();
        tLetter.setPostUserId(postUserId);
        tLetter.setReceiverUserId(receiverUserId);
        tLetter.setContent(content);
        Long time = System.currentTimeMillis();
        tLetter.setCreateTime(time);
        tLetter.setState(0);

        tLetterMapper.insertSelective(tLetter);

        LetterUser letterUser = letterUserDao.getSingleLetterUser(postUserId,
                receiverUserId);
        TLetterUser tLetterUser = new TLetterUser();
        tLetterUser.setPostUserId(postUserId);
        tLetterUser.setReceiverUserId(receiverUserId);
        tLetterUser.setUpdateTime(time);

        if(Objects.isNull(letterUser)) {
            tLetterUserMapper.insertSelective(tLetterUser);
        } else {
            tLetterUser.setId(letterUser.getId());
            tLetterUserMapper.updateByPrimaryKeySelective(tLetterUser);
        }

        LetterVo letterVo = new LetterVo(tLetter);
        TUser tUser = new TUser();
        tUser.setUserId(postUserId);
        tUser = tUserMapper.selectByPrimaryKey(tUser);
        UserVo postUserVo = new UserVo(tUser);
        postUserVo.setUserAvatar(postUserVo.getUserAvatar());
        letterVo.setPostUser(postUserVo);

        tUser.setUserId(receiverUserId);
        tUser = tUserMapper.selectByPrimaryKey(tUser);
        UserVo receiverUserVo = new UserVo(tUser);
        receiverUserVo.setUserAvatar(receiverUserVo.getUserAvatar());
        letterVo.setReceiverUser(receiverUserVo);

        myMqttClient.publish(Long.toString(receiverUserId), letterVo.toString());

        result.setData(letterVo);
        return result;
    }

    @Override
    public Response pageMessage(Long userId, Long letterUserTime, Integer pageSize) {
        Response result = Response.success();

        Map<String, Object> map = new HashMap<>();
        map.put("receiverUserId", userId);
        map.put("letterUserTime", letterUserTime);
        map.put("pageSize", pageSize);
        List<LetterUser> list = letterUserDao.pageLetterUser(map);

        List<ConversationVo> conversationVos = chatUser2Msg(list, userId);

        result.setData(conversationVos);
        return result;
    }

    private List<ConversationVo> chatUser2Msg(List<LetterUser> userList, Long userId) {
        List<ConversationVo> result = new ArrayList<ConversationVo>();
        if (userList != null && userList.size() > 0) {
            Map<String, Object> map = new HashMap<>();

            userList.forEach(item -> {
                map.put("receiverUserId", item.getReceiverUserId());
                map.put("postUserId", item.getPostUserId());
                map.put("createTime", item.getUpdateTime());
                Letter letter = letterDao.getSingleLetter(map);
                ConversationVo conversationVo = new ConversationVo();
                LetterVo letterVo = new LetterVo(letter);

                TUser tUser = new TUser();
                tUser.setUserId(item.getPostUserId());
                tUser = tUserMapper.selectByPrimaryKey(tUser);
                UserVo postUserVo = new UserVo(tUser);
                postUserVo.setUserAvatar(postUserVo.getUserAvatar());
                letterVo.setPostUser(postUserVo);

                tUser.setUserId(item.getReceiverUserId());
                tUser = tUserMapper.selectByPrimaryKey(tUser);
                UserVo receiverUserVo = new UserVo(tUser);
                receiverUserVo.setUserAvatar(receiverUserVo.getUserAvatar());
                letterVo.setReceiverUser(receiverUserVo);

                conversationVo.setLatestMessage(letterVo);

                Integer unReadMsg = letterDao.countLetter(map);
                conversationVo.setUnReadMsgCnt(unReadMsg);
                result.add(conversationVo);
            });
        }
        return result;
    }
}
