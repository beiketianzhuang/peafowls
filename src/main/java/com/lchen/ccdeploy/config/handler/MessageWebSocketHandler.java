package com.lchen.ccdeploy.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    /**
     * 客户端与服务端建立连接后，将该连接与用户绑定
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
    }

    /**
     * session关闭后清除内存中的相关session
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }


}