package com.eric;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eric.model.BaseMsg;
import com.eric.model.msg.ChatMessage;
import com.eric.model.msg.ChatMsg;
import com.eric.repository.IChatDao;
import com.eric.repository.entity.ChatEntity;
import com.eric.core.domain.entity.UserEntity;
import com.eric.service.UserService;
import com.eric.utils.Util;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@Component
@Service
@ServerEndpoint("/websocket/{token}")
public class WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static final CopyOnWriteArraySet<WebSocketServer> sWebSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收token
    private String token = "";
    private Gson mGson = new Gson();
    private static ApplicationContext applicationContext;
    private static IChatDao chatDao;
    private static UserService userService;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
        chatDao = applicationContext.getBean(IChatDao.class);
        userService = applicationContext.getBean(UserService.class);

    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        this.session = session;
        sWebSocketSet.add(this);     //加入set中
        this.token = token;
        addOnlineCount();           //在线数加1
        try {
            UserEntity userEntity = userService.findByUserId(Long.parseLong(token));
            logger.info("++++" + token + ",当前在线人数为:" + userEntity.userName);

            applicationContext.getBean(UserService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BaseMsg<String> stringBaseMsg = new BaseMsg<>();
            stringBaseMsg.type = "conn";
            stringBaseMsg.timestamp = String.valueOf(System.currentTimeMillis());
            stringBaseMsg.data = "conn_success";
            String json = mGson.toJson(stringBaseMsg);
            sendMessage(json);

            connectNotify();
            logger.info("有新窗口开始监听:" + token + ",当前在线人数为:" + getOnlineCount());
        } catch (IOException e) {
            logger.error("websocket IO Exception");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        sWebSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        connectNotify();
        //断开连接情况下，更新主板占用情况为释放
        logger.info("释放的token为：" + token);
        //这里写你 释放的时候，要处理的业务
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("收到来自窗口" + token + "的信息:" + message);

        try {
            JSONObject object = JSONObject.parseObject(message);
            String type = object.getString("type");
            if (type.equals("chat")) {
                ChatMessage chatMessage = mGson.fromJson(object.getString("data"), ChatMessage.class);
                BaseMsg<ChatMessage> response = new BaseMsg<>();
                response.type = "chat";
                response.timestamp = String.valueOf(System.currentTimeMillis());
                response.data = chatMessage;
                sendMessage(mGson.toJson(response), chatMessage.getToUser());
            } else if (type.equals("singleChat")) {
                ChatMsg chatMessage = mGson.fromJson(object.getString("data"), ChatMsg.class);
                // 历史消息 保存
                chatDao.createSingleChat(chatMessage.getFromId() + "_chat");
                ChatEntity chatEntity = new ChatEntity();
                chatEntity.messageId = String.valueOf(Util.createMessageID());
                chatEntity.fromId = String.valueOf(Long.valueOf(chatMessage.getFromId()));
                chatEntity.toId = String.valueOf(Long.valueOf(chatMessage.getToId()));
                chatEntity.content = chatMessage.getContent();
                chatEntity.stamp = Long.parseLong(chatMessage.getStamp());
                chatEntity.msgType = chatMessage.getMsgType();
                chatDao.insertItem(chatMessage.getFromId() + "_chat", chatEntity);

                // 发送消息
                BaseMsg<ChatMsg> response = new BaseMsg<>();
                response.type = "singleChat";
                response.timestamp = String.valueOf(System.currentTimeMillis());
                response.data = chatMessage;
                sendMessage(mGson.toJson(response), chatMessage.getToId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @ Param session
     * @ Param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static void sendMessage(String message, String usrId) {
        logger.info("推送消息到窗口" + usrId + "，推送内容:" + message);
        boolean online = false;
        for (WebSocketServer item : sWebSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (item.token.equals(usrId)) {
                    item.sendMessage(message);
                    online = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!online) {
            // todo 对方没在线走推送
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public void connectNotify() {
        LinkedList<String> result = new LinkedList<>();
        for (WebSocketServer webSocketServer : getsWebSocketSet()) {
            result.add(webSocketServer.token);
        }
        BaseMsg<LinkedList<String>> response = new BaseMsg<>();
        response.type = "online";
        response.timestamp = String.valueOf(System.currentTimeMillis());
        response.data = result;
        String json = mGson.toJson(response);
        logger.info("当前在线：" + getOnlineCount() + "人，在线列表:" + json);
        for (WebSocketServer webSocketServer : getsWebSocketSet()) {
            try {
                webSocketServer.sendMessage(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static CopyOnWriteArraySet<WebSocketServer> getsWebSocketSet() {
        return sWebSocketSet;
    }
}
