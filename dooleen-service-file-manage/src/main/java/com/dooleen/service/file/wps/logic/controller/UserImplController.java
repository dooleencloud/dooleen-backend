package com.dooleen.service.file.wps.logic.controller;

import com.alibaba.fastjson.JSON;
import com.dooleen.service.file.wps.base.BaseController;
import com.dooleen.service.file.wps.base.Response;
import com.dooleen.service.file.wps.logic.dto.FileListDTO;
import com.dooleen.service.file.wps.logic.entity.UserEntity;
import com.dooleen.service.file.wps.logic.service.FileService;
import com.dooleen.service.file.wps.logic.service.UserService;
import com.dooleen.service.file.wps.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zm
 * 用户实现获取wps可预览URL
 */
@RestController
@RequestMapping("/file/wps/api")
public class UserImplController extends BaseController {

    @Autowired
    public UserImplController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    private final FileService fileService;
    private final UserService userService;

    /**
     * 通过fileId获取wpsUrl以及token
     *
     * @param fileId 文件id
     * @return token（包含url）
     */
    @GetMapping("getViewUrlDbPath")
    public ResponseEntity<Object> getViewUrlDbPath(String fileId, String userId) {
        logger.info("getViewUrlDbPath：fileId={},userId={}", fileId, userId);
        Token t = fileService.getViewUrl(fileId, userId, true);
        if (t != null) {
            return Response.success(t);
        } else {
            return Response.bad("文件不存在或其它异常！");
        }
    }

    /**
     * 转换文件
     *
     * @param srcUri     文件url
     * @param exportType 到处类型
     */
    @PutMapping("convert")
    public ResponseEntity<Object> convert(String taskId , String srcUri, String exportType) {
        fileService.convertFile(taskId, srcUri, exportType);
        return Response.success();
    }

    /**
     * 获取转换文件
     *
     * @param taskId 任务id，由convert接口生成
     */
    @GetMapping("getConvert")
    public ResponseEntity<Object> getConvert(String taskId) {
        Object res = fileService.getConvertQueryRes(taskId);
        return Response.success(res);
    }

}
