package com.dooleen.service.websocket.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * WebSocketServer
 * @author liqh
 */
@ServerEndpoint("/webSocket/sendMsg/{tenantId}/{terminalType}/{msgType}/{userName}/{realName}/{avatar}")
@Component
@Slf4j
public class NoticeMsgSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static Map<String,Integer> onlineCountMap = new HashMap<>();
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String, NoticeMsgSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;

    /**发送用户*/
    private String userName="";

    /**发送用户姓名*/
    private String realName="";

    /**消息类型*/
    private String msgType="all";

    /**terminalType*/
    private String terminalType="";
    /**接收avatar*/
    private String avatar="";
    /**接收userName*/
    private String tenantId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("tenantId") String tenantId,@PathParam("terminalType") String terminalType, @PathParam("msgType") String msgType, @PathParam("userName") String userName, @PathParam("realName") String realName, @PathParam("avatar") String avatar) {
        this.session = session;
        this.userName = tenantId+"#"+terminalType+"#"+userName;
        this.realName = realName;
        this.tenantId = tenantId;
        this.terminalType = terminalType;
        this.msgType = msgType;
        this.avatar = avatar;
        //用户已登录则通知用户下线
        if(webSocketMap.containsKey(this.userName) ){
            webSocketMap.remove(this.userName);
            webSocketMap.put(this.userName, this);
        }
        else {
            webSocketMap.put(this.userName, this);
            //将用户列表推给所有用户
            //加入set中
            addOnlineCount(tenantId);
        }
        if(msgType.equals("userControlCommand")){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userCount",getOnlineCount(tenantId));
            //将用户列表推给管理用户
            sendAll(jsonObject.toJSONString());
            List<JSONObject> userList = new ArrayList<>();
            for (String key : webSocketMap.keySet()) {
                if (webSocketMap.get(key).tenantId.equals(tenantId) && !webSocketMap.get(key).userName.equals(this.userName)) {
                    JSONObject userInfoJson = new JSONObject();
                    userInfoJson.put("userName",webSocketMap.get(key).userName);
                    userInfoJson.put("realName",webSocketMap.get(key).realName);
                    userList.add(userInfoJson);
                }
            }
            jsonObject.put("userList",userList);
            try {
                webSocketMap.get(this.userName).sendMessage(jsonObject.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("==>>用户:"+this.userName+"已登录,当前租户"+tenantId+"在线人数为:" + getOnlineCount(tenantId));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userName)){
            webSocketMap.remove(userName);
            //从set中删除
            subOnlineCount(tenantId);
            log.info("==>>用户:"+userName+"已退出,当前租户"+tenantId+"在线人数为:" + getOnlineCount(tenantId));
        }
        else{
            log.info("==>>用户:"+userName+"已退出，无需重复退出！");
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("==>>开始接收消息..."+message);
        JSONObject msgObj = JSON.parseObject(message);
        if(msgType.equals("sendAllUserMsg")) {
            JSONObject msgJson = msgObj;
            msgJson.put("type","message");
            sendAll(msgJson.toJSONString());
        }
        else if(msgType.equals("sendOneUserMsg")) {
            JSONObject msgJson = msgObj;
            msgJson.put("type","message");
            sendInfo(tenantId+"#PC#"+msgObj.getString("userName"),msgJson.toJSONString());
            sendInfo(tenantId+"#APP#"+msgObj.getString("userName"),msgJson.toJSONString());
            sendInfo(tenantId+"#H5#"+msgObj.getString("userName"),msgJson.toJSONString());
            sendInfo(tenantId+"#MPWX#"+msgObj.getString("userName"),msgJson.toJSONString());
        }
        else if(msgType.equals("userControlCommand")){
            JSONObject msgJson = new JSONObject();
            msgJson.put("type","command");
            msgJson.put("commandName",msgObj.getString("command"));
            msgJson.put("message",msgObj.getString("message"));
            sendInfo(tenantId+"#"+terminalType+"#"+msgObj.getString("userName"),msgJson.toJSONString());
        }
    }

    /**
     * 给所有人发消息
     * @param message
     */
    public void sendAll(String message) {
        log.debug("==>>开始发消息 {}",message);
        //遍历HashMap
        for (String key : webSocketMap.keySet()) {
            log.debug("==>>当前的key"+webSocketMap.keySet());
            try {
                //判断接收用户是否是当前发消息的用户!userName.equals(key) &&
                if (webSocketMap.get(key).tenantId.equals(tenantId) && !userName.equals(key)) {
                    webSocketMap.get(key).sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userName+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public void sendInfo(String userName, String message) {
        log.info("发送消息到:"+userName+"，报文:"+message);
        if(StringUtils.isNotBlank(userName)&&webSocketMap.containsKey(userName)){
            try {
                webSocketMap.get(userName).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            log.error("==>>用户:"+userName+",不在线！");
        }
    }

    public static synchronized int getOnlineCount(String tenantId) {
        return onlineCountMap.get(tenantId);
    }

    public static synchronized void addOnlineCount(String tenantId) {
        NoticeMsgSocketServer.onlineCountMap.put(tenantId, NoticeMsgSocketServer.onlineCountMap.get(tenantId) == null? 1 : NoticeMsgSocketServer.onlineCountMap.get(tenantId)+ 1);
    }

    public static synchronized void subOnlineCount(String tenantId) {
        int cnt = NoticeMsgSocketServer.onlineCountMap.get(tenantId) - 1;
        //避免减到负数的情况
        if(cnt < 0){
            cnt = 0;
        }
        NoticeMsgSocketServer.onlineCountMap.put(tenantId, NoticeMsgSocketServer.onlineCountMap.get(tenantId) == null? 0 : cnt);
    }
}