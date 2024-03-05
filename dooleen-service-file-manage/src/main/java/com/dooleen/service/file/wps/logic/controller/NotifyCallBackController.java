package com.dooleen.service.file.wps.logic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.service.file.wps.base.BaseController;
import com.dooleen.service.file.wps.base.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author czw
 * 消息回调接口
 */
@RestController
@RequestMapping("v1/3rd")
public class NotifyCallBackController extends BaseController {

    /**
     * 回调通知
     */
    @PostMapping("onnotify")
    public ResponseEntity<Object> onNotify(
            @RequestBody JSONObject obj
    ) {
        logger.info("回调通知param:{}", JSON.toJSONString(obj));
        // TODO
        // 返回数据暂不处理
        return Response.success();
    }

}
