package com.dooleen.service.websocket.server;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.utils.DateUtils;
import com.dooleen.service.websocket.server.util.WebsocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * WebSocketServer
 * @author liqh
 */
@ServerEndpoint("/webSocket/loginUser/{tenantId}/{terminalType}/{userName}/{realName}/{avatar}/{sessionId}")
@Component
@Slf4j
public class LoginUserServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static Map<String,Integer> onlineCountMap = new HashMap<>();
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String, LoginUserServer> webSocketMap = new ConcurrentHashMap<>();

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;

    /**接收tenantId*/
    private String userName="";

    /**接收realName*/
    private String realName="";

    /**接收avatar*/
    private String avatar="";
    /**终端类型*/
    private String terminalType="";

    /**接收sessionId*/
    private String sessionId="";

    /**接收userName*/
    private String tenantId="";

    /**接收用户IP*/
    private String address="";

    private JSONObject userInfo;
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("tenantId") String tenantId,@PathParam("terminalType") String terminalType, @PathParam("userName") String userName, @PathParam("realName") String realName, @PathParam("avatar") String avatar,  @PathParam("sessionId") String sessionId) {
        this.session = session;
        this.userName=tenantId+"#"+terminalType+"#"+userName;
        this.tenantId = tenantId;
        this.realName = realName;
        this.avatar = avatar;
        this.terminalType = terminalType;
        this.sessionId = session.getId();
        //获取IP
        System.out.println("session id"+sessionId);
        InetSocketAddress remoteAddress = WebsocketUtil.getRemoteAddress(session);
        JSONObject jsonObject = new JSONObject();
        JSONObject userInfoJson = new JSONObject();
        userInfoJson.put("userName", userName);
        userInfoJson.put("address", remoteAddress.toString().replace("/","").split(":")[0]);
        userInfoJson.put("loginTime", DateUtils.getLongDateStr());
        userInfoJson.put("realName",realName);
        userInfoJson.put("avatar",avatar);
        this.userInfo = userInfoJson;
        this.address = remoteAddress.toString().replace("/","").split(":")[0];
        //用户已登录则通知用户下线
        if(webSocketMap.containsKey(this.userName) ){
            jsonObject.put("type","login");
            jsonObject.put("userInfo",userInfoJson);
            try {
                webSocketMap.get(this.userName).sendMessage(jsonObject.toJSONString());
                System.out.println("客户端请求IP："+remoteAddress);
                //String ip = resolveRemoteIp(session.getRequestURI().);
            } catch (IOException e) {
                log.error("用户:"+this.userName+",网络异常!!!!!!");
            }
            webSocketMap.remove(this.userName);
            webSocketMap.put(this.userName, this);
        }
        else {
            webSocketMap.put(this.userName, this);
            //将用户列表推给所有用户
            List<JSONObject> userList = new ArrayList<>();
            for (String key : webSocketMap.keySet()) {
                if (webSocketMap.get(key).tenantId.equals(tenantId) && !webSocketMap.get(key).userName.equals(this.userName)) {
                    userList.add(webSocketMap.get(key).userInfo);
                }
            }
            jsonObject.put("type","userInfoList");
            jsonObject.put("userList",userList);
            sendAll(jsonObject.toJSONString());

            //加入set中
            addOnlineCount(tenantId+"#"+terminalType);

            //在线数加1
        }
        log.info("用户:"+this.userName+"已登录,当前租户"+tenantId+"在线人数为:" + getOnlineCount(tenantId+"#"+terminalType));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userName) && address.equals(webSocketMap.get(userName).address) && terminalType.equals(webSocketMap.get(userName).terminalType) && sessionId.equals(webSocketMap.get(userName).sessionId)){
            webSocketMap.remove(userName);
            //从set中删除
            subOnlineCount(tenantId+"#"+terminalType);
            log.info("用户退出:"+userName+",当前租户"+tenantId+"在线人数为:" + getOnlineCount(tenantId+"#"+terminalType));
        }
        else{
            log.info("用户:"+userName+"已退出，无需重复退出！");
        }
    }

    /**
     * 给所有人发消息
     * @param message
     */

    public void sendAll(String message) {
        String sendMessage = message.split("[|]")[0];
        //遍历HashMap
        for (String key : webSocketMap.keySet()) {
            try {
                //判断接收用户是否是当前发消息的用户!userName.equals(key) &&
                if (webSocketMap.get(key).tenantId.equals(tenantId)) {
                    webSocketMap.get(key).sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {

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
    public static void sendInfo(String message,@PathParam("userName") String userName) throws IOException {
        log.info("发送消息到:"+userName+"，报文:"+message);
        if(StringUtils.isNotBlank(userName)&&webSocketMap.containsKey(userName)){
            webSocketMap.get(userName).sendMessage(message);
        }else{
            log.error("用户"+userName+",不在线！");
        }
    }

    public static synchronized int getOnlineCount(String tenantId) {
        return onlineCountMap.get(tenantId);
    }

    public static synchronized void addOnlineCount(String tenantId) {
        LoginUserServer.onlineCountMap.put(tenantId,LoginUserServer.onlineCountMap.get(tenantId) == null? 1 : LoginUserServer.onlineCountMap.get(tenantId)+ 1);
    }

    public static synchronized void subOnlineCount(String tenantId) {
        LoginUserServer.onlineCountMap.put(tenantId, LoginUserServer.onlineCountMap.get(tenantId) == null? 0 : LoginUserServer.onlineCountMap.get(tenantId) - 1);
    }
}