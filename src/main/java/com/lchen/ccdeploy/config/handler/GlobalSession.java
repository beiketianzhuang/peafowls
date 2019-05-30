package com.lchen.ccdeploy.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class GlobalSession {


    /**
     * 当客户端与服务端的连接关闭时，需要清除内存中管理的session
     *
     * @param userId
     */
    public static void removeSession(String userId, WebSocketSession webSocketSession) {
    }

    /**
     * 向客户端发送消息
     *
     * @param webSocketSession
     * @param message          (需要json格式的字符串，方便客户端处理)
     * @throws IOException
     */
    public static void sendMessage(WebSocketSession webSocketSession, String message) throws IOException {
        TextMessage textMessage = new TextMessage(message);
        if (webSocketSession.isOpen()) {
            webSocketSession.sendMessage(textMessage);
        }
    }

    public static <T> Optional<T> attributesFetch(WebSocketSession session, String key) {
        Map<String, Object> attributes = session.getAttributes();
        Object obj = attributes.get(key);
        if (obj == null) {
            return Optional.empty();
        }
        T t = (T) obj;
        return Optional.of(t);
    }
}
