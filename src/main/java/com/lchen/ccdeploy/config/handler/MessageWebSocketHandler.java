package com.lchen.ccdeploy.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;


@Slf4j
@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    /**
     * 客户端与服务端建立连接后，将该连接与应用绑定
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        if (StringUtils.isNoneBlank(payload)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String context = mapper.readTree(payload).get("context").toString();
                session.getAttributes().put("context",context);
                //发送

            } catch (IOException e) {
                log.error("", e);
            }
        }
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