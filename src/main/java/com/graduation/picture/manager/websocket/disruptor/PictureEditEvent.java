package com.graduation.picture.manager.websocket.disruptor;

import com.graduation.picture.manager.websocket.model.PictureEditRequestMessage;
import com.graduation.picture.model.entity.User;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Classname PictureEditEvent
 * @Date 2026/1/1 20:18
 * @Author by Eddie
 * @Description 图片编辑事件
 */
@Data
public class PictureEditEvent {

    /**
     * 消息
     */
    private PictureEditRequestMessage pictureEditRequestMessage;

    /**
     * 当前用户的 session
     */
    private WebSocketSession session;

    /**
     * 当前用户
     */
    private User user;

    /**
     * 图片 id
     */
    private Long pictureId;

}
