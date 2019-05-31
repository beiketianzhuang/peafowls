package com.lchen.ccdeploy.config.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class GlobalSession {

    /**
     * 为每个应用维护一份数据
     */
    private static Map<String, List<WebSocketSession>> contextSessions = Maps.newConcurrentMap();


    public static void putContextSession(String context, WebSocketSession session) {
        List<WebSocketSession> webSocketSessions = contextSessions.computeIfAbsent(context, a -> Lists.newLinkedList());
        webSocketSessions.add(session);
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

    public static Map<String, List<WebSocketSession>> getContextSessions() {
        return Collections.unmodifiableMap(contextSessions);
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
