package com.dooleen.service.file.wps.logic.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dooleen.common.core.app.general.file.history.entity.GeneralFileHistoryEntity;
import com.dooleen.common.core.app.general.file.history.mapper.GeneralFileHistoryMapper;
import com.dooleen.common.core.app.general.file.history.service.GeneralFileHistoryService;
import com.dooleen.common.core.app.general.file.info.entity.GeneralFileInfoEntity;
import com.dooleen.common.core.app.general.file.info.mapper.GeneralFileInfoMapper;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.file.manage.fastdfs.util.CommonFileUtil;
import com.dooleen.service.file.wps.base.BaseRepository;
import com.dooleen.service.file.wps.base.BaseService;
import com.dooleen.service.file.wps.config.Context;
import com.dooleen.service.file.wps.logic.dto.*;
import com.dooleen.service.file.wps.logic.entity.*;
import com.dooleen.service.file.wps.logic.repository.FileRepository;
import com.dooleen.service.file.wps.propertie.*;
import com.dooleen.service.file.wps.util.*;
import com.dooleen.service.file.wps.util.file.FileUtil;
import com.dooleen.service.file.wps.util.upload.ResFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class FileService extends BaseService<FileEntity, String> {
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    @Autowired
    private GeneralFileInfoMapper generalFileInfoMapper;

    @Autowired
    private GeneralFileHistoryMapper generalFileHistoryMapper;

    @Autowired
    private GeneralFileHistoryService generalFileHistoryService;


    @Autowired
    private CommonFileUtil fileUtil;
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    private final WpsUtil wpsUtil;
    private final WpsProperties wpsProperties;
    private final RedirectProperties redirect;

    @Autowired
    public FileService(WpsUtil wpsUtil, WpsProperties wpsProperties,
                       RedirectProperties redirect) {
        this.wpsUtil = wpsUtil;
        this.wpsProperties = wpsProperties;
        this.redirect = redirect;
    }

    @Autowired
    private ConvertProperties convertProperties;
    @Autowired
    private ServerProperties serverProperties;

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = FileRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public FileRepository getRepository() {
        return (FileRepository) this.baseRepository;
    }

    /**
     * 获取预览用URL
     *
     * @param fileId     文件id
     * @param userId     用户id
     * @param checkToken 是否校验token
     */
    public Token getViewUrl(String fileId, String userId, boolean checkToken) {
        // 获取文件信息
        GeneralFileInfoEntity generalFileInfoEntity = generalFileInfoMapper.selectById(fileId);
        if(StringUtil.isNull(generalFileInfoEntity)){
            logger.info("===>>{}文件不存在，请检查....",fileId);
        }
        CommonMsg<JSONObject, NullEntity> exceptionCommonMsg = CreateCommonMsg.getCommonMsg(new JSONObject(), new NullEntity());
        exceptionCommonMsg.getHead().setTenantId(generalFileInfoEntity.getTenantId());
        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,exceptionCommonMsg);
        //FileEntity fileEntity = this.findOne(fileId);
        if (generalFileInfoEntity != null) {
            Token t = new Token();
            String fileName = generalFileInfoEntity.getFileName();
            String fileType = FileUtil.getFileTypeByName(fileName);

            UUID randomUUID = UUID.randomUUID();
            String uuid = randomUUID.toString().replace("-", "");

            Map<String, String> values = new HashMap<String, String>() {
                {
                    put("_w_appid", wpsProperties.getAppid(generalFileInfoEntity.getTenantId()));
                    if (checkToken) {
                        put("_w_tokentype", "1");
                    }
                    put(redirect.getKey(), redirect.getValue());
                    put("_w_filepath", fileName);
                    put("_w_userid", userId);
                    put("_w_filetype", "db");
                }
            };

            String wpsUrl = wpsUtil.getWpsUrl(values, fileType, generalFileInfoEntity.getId(),generalFileInfoEntity.getTenantId());

            t.setToken(uuid);
            t.setExpires_in(600);
            t.setWpsUrl(wpsUrl);

            return t;
        }
        return null;
    }

    /**
     * 获取文件元数据
     *
     * @param userId 用户id
     */
    public Map<String, Object> getFileInfo(String userId) {
        String fileId = Context.getFileId();
        // 获取文件信息
        GeneralFileInfoEntity generalFileInfoEntity = generalFileInfoMapper.selectById(fileId);
        if(StringUtil.isNull(generalFileInfoEntity)){
            logger.info("===>>{}文件不存在，请检查....",fileId);
        }
        CommonMsg<JSONObject, NullEntity> exceptionCommonMsg = CreateCommonMsg.getCommonMsg(new JSONObject(), new NullEntity());
        exceptionCommonMsg.getHead().setTenantId(generalFileInfoEntity.getTenantId());
        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,exceptionCommonMsg);

        // 初始化文件读写权限为read
        String permission;
        UserAclBO userAcl = new UserAclBO();

        Object privilege = sysUserInfoMapper.getUserFilePrivilege(generalFileInfoEntity.tenantId,userId,generalFileInfoEntity.getCatalogId());
        if(!StringUtils.isEmpty(privilege) && privilege.equals("2") || generalFileInfoEntity.createUserName.equals(userId)) {
            userAcl.setHistory(1);
            userAcl.setRename(1);
            userAcl.setCopy(1);
            userAcl.setExport(1);
            userAcl.setPrint(1);
            permission = "write";
        }
        else
        {
            userAcl.setHistory(0);
            userAcl.setRename(0);
            userAcl.setCopy(0);
            userAcl.setExport(0);
            userAcl.setPrint(0);
            permission = "read";
        }

        // 增加水印
        WatermarkBO watermark = new WatermarkBO();

        //获取user
        QueryWrapper<SysUserInfoEntity> queryWrapper = new QueryWrapper<SysUserInfoEntity>();
        queryWrapper.lambda().eq(SysUserInfoEntity::getTenantId, generalFileInfoEntity.getTenantId());
        queryWrapper.lambda().eq(SysUserInfoEntity::getUserName, userId);
        SysUserInfoEntity sysUserInfoEntity = sysUserInfoMapper.selectOne(queryWrapper);
        UserDTO user = new UserDTO();
        if (sysUserInfoEntity != null) {
            user.setId(userId);
            user.setName(sysUserInfoEntity.getRealName());
            user.setAvatar_url(sysUserInfoEntity.getHeadImgUrl());
            user.setPermission(permission);
        }

        // 构建fileInfo
        FileDTO file = new FileDTO();
        file.setName(generalFileInfoEntity.getFileName());
        file.setId(generalFileInfoEntity.getId());
        file.setVersion(Integer.parseInt(generalFileInfoEntity.getLastVersionNo()));
        file.setSize(Integer.parseInt(generalFileInfoEntity.getFileSize()));
        file.setCreator(generalFileInfoEntity.getCreateRealName());
        file.setCreate_time(DateUtils.getDateByStr(generalFileInfoEntity.getCreateDatetime()).getTime());
        file.setModifier(generalFileInfoEntity.getUpdateRealName());
        file.setModify_time(DateUtils.getDateByStr(generalFileInfoEntity.getUpdateDatetime()).getTime());
        file.setDownload_url( serverProperties.getDomain(generalFileInfoEntity.getTenantId()) + generalFileInfoEntity.getFilePath());
        file.setUser_acl(userAcl);
        file.setWatermark(watermark);
        return new HashMap<String, Object>() {
            {
                put("file", file);
                put("user", user);
            }
        };
    }

    /**
     * 文件重命名
     *
     * @param fileName 文件名
     * @param userId   用户id
     */
    public void fileRename(String fileName, String userId) {
        String fileId = Context.getFileId();
        // 获取文件信息
        GeneralFileInfoEntity generalFileInfoEntity = generalFileInfoMapper.selectById(fileId);

        CommonMsg<JSONObject, NullEntity> exceptionCommonMsg = CreateCommonMsg.getCommonMsg(new JSONObject(), new NullEntity());
        exceptionCommonMsg.getHead().setTenantId(generalFileInfoEntity.getTenantId());
        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,exceptionCommonMsg);

        //获取user
        QueryWrapper<SysUserInfoEntity> queryWrapper = new QueryWrapper<SysUserInfoEntity>();
        queryWrapper.lambda().eq(SysUserInfoEntity::getTenantId, generalFileInfoEntity.getTenantId());
        queryWrapper.lambda().eq(SysUserInfoEntity::getUserName, userId);
        SysUserInfoEntity sysUserInfoEntity = sysUserInfoMapper.selectOne(queryWrapper);

        //修改文件名
        generalFileInfoEntity.setFileName(fileName);
        generalFileInfoEntity.setUpdateDatetime(DateUtils.getLongDateStr());
        generalFileInfoEntity.setUpdateUserName(sysUserInfoEntity.getUserName());
        generalFileInfoEntity.setUpdateRealName(sysUserInfoEntity.getRealName());
        generalFileInfoMapper.updateById(generalFileInfoEntity);
    }

    /**
     * 查询文件历史记录
     */
    public Map<String, Object> fileHistory(FileReqDTO req) {
        // 获取文件信息
        GeneralFileInfoEntity generalFileInfoEntity = generalFileInfoMapper.selectById(req.getId());
        if(StringUtil.isNull(generalFileInfoEntity)){
            logger.info("===>>{}文件不存在，请检查....",req.getId());
        }
        CommonMsg<JSONObject, NullEntity> exceptionCommonMsg = CreateCommonMsg.getCommonMsg(new JSONObject(), new NullEntity());
        exceptionCommonMsg.getHead().setTenantId(generalFileInfoEntity.getTenantId());
        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,exceptionCommonMsg);

        List<FileHisDTO> result = new ArrayList<>(1);
        if (req.getId() != null) {
            // 目前先实现获取所有的历史记录
            QueryWrapper<GeneralFileHistoryEntity> queryWrapper = new QueryWrapper<GeneralFileHistoryEntity>();
            queryWrapper.lambda().eq(GeneralFileHistoryEntity::getTenantId, generalFileInfoEntity.getTenantId());
            queryWrapper.lambda().eq(GeneralFileHistoryEntity::getFileId, generalFileInfoEntity.getId());
            List<GeneralFileHistoryEntity> historyVersionList = generalFileHistoryMapper.selectList(queryWrapper);

            if (historyVersionList != null && historyVersionList.size() > 0) {
                Set<String> userIdSet = new HashSet<>();
                for (GeneralFileHistoryEntity fileVersion : historyVersionList) {
                    userIdSet.add(fileVersion.getUpdateUserName());
                    userIdSet.add(fileVersion.getCreateUserName());
                }
                List<String> userIdList = new ArrayList<>(userIdSet);
                // 获取所有关联的人
                List<SendMsgUserInfoEntity> userList = sysUserInfoMapper.selectByUserNameList(generalFileInfoEntity.tenantId,userIdList);

                if (userList != null && userList.size() > 0) {
                    for (GeneralFileHistoryEntity fileVersion : historyVersionList) {
                        FileHisDTO fileHis = new FileHisDTO();
                        fileHis.setId(fileVersion.getId());
                        fileHis.setName(fileVersion.getFileName());
                        fileHis.setVersion(Integer.parseInt(fileVersion.getFileVersionNo()));
                        fileHis.setSize(Integer.parseInt(fileVersion.getFileSize()));
                        fileHis.setCreate_time(DateUtils.getDateByStr(fileVersion.getCreateDatetime()).getTime());
                        fileHis.setModify_time(DateUtils.getDateByStr(fileVersion.getUpdateDatetime()).getTime());
                        fileHis.setDownload_url(serverProperties.getDomain(fileVersion.getTenantId()) + fileVersion.getFilePath());
                        UserDTO creator = new UserDTO();
                        UserDTO modifier = new UserDTO();
                        for (SendMsgUserInfoEntity user : userList) {
                            if (user.getUserName().equals(fileVersion.getCreateUserName())) {
                                creator.setId(user.getUserName());
                                creator.setName(user.getRealName());
                                creator.setAvatar_url(user.getHeadImgUrl());
                            }
                            if (user.getUserName().equals(fileVersion.getUpdateUserName())) {
                                modifier.setId(user.getUserName());
                                modifier.setName(user.getRealName());
                                modifier.setAvatar_url(user.getHeadImgUrl());
                            }
                        }
                        fileHis.setModifier(modifier);
                        fileHis.setCreator(creator);
                        result.add(fileHis);
                    }
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("histories", result);
        return map;
    }

    /**
     * 保存文件
     *
     * @param mFile  文件
     * @param userId 用户id
     */
    public Map<String, Object> fileSave(MultipartFile mFile, String userId) {
        Date date = new Date();
        // 上传
        ResFileDTO resFileDTO =new ResFileDTO();
        String path="";
        try {
            path = fileUtil.uploadFile(mFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resFileDTO.setFileUrl(path);
        resFileDTO.setFileName(mFile.getName());
        resFileDTO.setCFileName(path.substring(path.lastIndexOf("/")+1,path.lastIndexOf(".")));
        int size =(int) mFile.getSize();
        resFileDTO.setFileSize(size);
        String ext = FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase();

        String fileId = Context.getFileId();
        // 获取文件信息
        GeneralFileInfoEntity generalFileInfoEntity = generalFileInfoMapper.selectById(fileId);
        CommonMsg<JSONObject, NullEntity> exceptionCommonMsg = CreateCommonMsg.getCommonMsg(new JSONObject(), new NullEntity());
        exceptionCommonMsg.getHead().setTenantId(generalFileInfoEntity.getTenantId());
        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,exceptionCommonMsg);
        //文件信息保存到历史
        GeneralFileHistoryEntity generalFileHistoryEntity = new GeneralFileHistoryEntity();
        CopyFieldBeanUtil.copyProperties(generalFileInfoEntity,generalFileHistoryEntity,true);
        generalFileHistoryEntity.setFileId(generalFileInfoEntity.getId());
        generalFileHistoryEntity.setFileVersionNo(generalFileInfoEntity.getLastVersionNo());
        generalFileHistoryEntity.setCreateDatetime(generalFileInfoEntity.getCreateDatetime());
        generalFileHistoryEntity.setCreateRealName(generalFileInfoEntity.getUpdateRealName());
        generalFileHistoryEntity.setCreateUserName(generalFileInfoEntity.getCreateUserName());
        generalFileHistoryEntity.setUpdateDatetime(generalFileInfoEntity.getUpdateDatetime());
        generalFileHistoryEntity.setUpdateRealName(generalFileInfoEntity.getUpdateRealName());
        generalFileHistoryEntity.setUpdateUserName(generalFileInfoEntity.getUpdateUserName());
        generalFileHistoryEntity.setDataSign(generalFileInfoEntity.getDataSign());
        generalFileHistoryEntity.setValidFlag(generalFileInfoEntity.getValidFlag());
        String id = GenerateNo.getId(generalFileInfoEntity.getTenantId(), "GeneralFileHistory", "FILH", redisTemplate);
        generalFileHistoryEntity.setId(id);
        generalFileHistoryMapper.insert(generalFileHistoryEntity);

        FileDTO fileInfo = new FileDTO();
        // 更新当前版本
        generalFileInfoEntity.setLastVersionNo(String.valueOf(Integer.parseInt(generalFileInfoEntity.getLastVersionNo())+1));
        generalFileInfoEntity.setFilePath(resFileDTO.getFileUrl());
        generalFileInfoEntity.setFileSize(String.valueOf(size));
        generalFileInfoEntity.setUpdateUserName(userId);
        generalFileInfoEntity.setUpdateDatetime(DateUtils.getLongDateStr());
        generalFileInfoEntity.setDataSign(DooleenMD5Util.md5(generalFileInfoEntity.toString(),  ConstantValue.md5Key));
        generalFileInfoMapper.updateById(generalFileInfoEntity);

        // 返回当前版本信息
        fileInfo.setId(generalFileInfoEntity.getId());
        fileInfo.setName(generalFileInfoEntity.getFileName());
        fileInfo.setVersion(Integer.parseInt(generalFileInfoEntity.getLastVersionNo()));
        fileInfo.setSize(Integer.parseInt(generalFileInfoEntity.getFileSize()));
        fileInfo.setDownload_url(serverProperties.getDomain(generalFileInfoEntity.getTenantId()) + generalFileInfoEntity.getFilePath());
        Map<String, Object> map = new HashMap<>();
        map.put("file", fileInfo);
        return map;
    }

    /**
     * 查询文件版本
     *
     * @param version 版本号
     */
    public Map<String, Object> fileVersion(int version) {
        FileDTO fileInfo = new FileDTO();
        String fileId = Context.getFileId();
        // 获取文件信息
        GeneralFileInfoEntity generalFileInfoEntity = generalFileInfoMapper.selectById(fileId);
        if(StringUtil.isNull(generalFileInfoEntity)){
            logger.info("===>>{}文件不存在，请检查....",fileId);
        }
        CommonMsg<JSONObject, NullEntity> exceptionCommonMsg = CreateCommonMsg.getCommonMsg(new JSONObject(), new NullEntity());
        exceptionCommonMsg.getHead().setTenantId(generalFileInfoEntity.getTenantId());
        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,exceptionCommonMsg);

        // 目前先实现获取所有的历史记录
        QueryWrapper<GeneralFileHistoryEntity> queryWrapper = new QueryWrapper<GeneralFileHistoryEntity>();
        queryWrapper.lambda().eq(GeneralFileHistoryEntity::getTenantId, generalFileInfoEntity.getTenantId());
        queryWrapper.lambda().eq(GeneralFileHistoryEntity::getFileId, generalFileInfoEntity.getId());
        queryWrapper.lambda().eq(GeneralFileHistoryEntity::getFileVersionNo, version+"");
        GeneralFileHistoryEntity fileVersion= generalFileHistoryMapper.selectOne(queryWrapper);

        if (fileVersion != null) {
            fileInfo.setId(fileVersion.getFileId());
            fileInfo.setName(fileVersion.getFileName());
            fileInfo.setVersion(Integer.parseInt(fileVersion.getFileVersionNo()));
            fileInfo.setSize(Integer.parseInt(fileVersion.getFileSize()));
            fileInfo.setDownload_url(serverProperties.getDomain(fileVersion.getTenantId()) + fileVersion.getFilePath());
            fileInfo.setCreate_time(DateUtils.getDateByStr(fileVersion.getCreateDatetime()).getTime());
            fileInfo.setCreator(fileVersion.createUserName);
            fileInfo.setModify_time(DateUtils.getDateByStr(fileVersion.getUpdateDatetime()).getTime());
            fileInfo.setModifier(fileVersion.createUserName);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("file", fileInfo);
        return map;
    }

    /**
     * 文件原格式	转换后格式
     * word			pdf、png
     * excel		pdf、png
     * ppt			pdf
     * pdf			word、ppt、excel
     *
     * @param srcUri     文件url
     * @param exportType 输出类型
     */
    public void convertFile(String taskId, String srcUri, String exportType) {
        if (StringUtils.isEmpty(taskId)) {
            taskId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        }
        System.out.println("--convertFile:taskId:-> " + taskId);
        String headerDate = Common.getGMTDate();
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("SrcUri", srcUri);
        param.put("FileName", FileUtil.getFileName(srcUri));
        param.put("ExportType", exportType);
        Integer port = null;
        if (serverProperties.getPort("") != 443 && serverProperties.getPort("") != 80) {
            port = serverProperties.getPort("");
        }
        param.put("CallBack", serverProperties.getDomain("") + (port == null ? "" : (":" + port)) + "/v1/3rd/file/convertCallback");//回调地址，文件转换后的通知地址，需保证可访问
        param.put("TaskId", taskId);
        //Content-MD5 表示请求内容数据的MD5值，对消息内容（不包括头部）计算MD5值获得128比特位数字，对该数字进行base64编码而得到，如”eB5eJF1ptWaXm4bijSPyxw==”，也可以为空；
        String contentMd5 = Common.getMD5(param);
        //签名url的参数不带请求参数
        String authorization = SignatureUtil.getAuthorization("POST", convertProperties.getConvert(), contentMd5, headerDate, convertProperties.getAppid(), convertProperties.getAppsecret()); //签名

        //header参数
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Common.CONTENTTYPE);
        headers.put(HttpHeaders.DATE, headerDate);
        headers.put(HttpHeaders.CONTENT_MD5, contentMd5);//文档上是 "Content-Md5"
        headers.put(HttpHeaders.AUTHORIZATION, authorization);

        // 请求
        String result = HttpUtil.post(convertProperties.getConvert(), headers, JSON.toJSONString(param));
        if (!StringUtils.isEmpty(result)) {
            JSONObject dataJson = JSON.parseObject(result);
            String code = dataJson.get("Code").toString();
            if (code.equals("OK")) {
                //成功，做其它业务处理
            } else {
                String errorMsg = "文件格式转换失败";
                if (dataJson.get("Message") != null) {
                    String message = dataJson.get("Message").toString();
                    errorMsg = errorMsg + message;
                }
                //失败
            }
        }
    }

    /**
     * 文件格式转换回调
     */
    public void convertCallBack(HttpServletRequest request) {
        try {
            BufferedReader buf = request.getReader();
            String str;
            StringBuilder data = new StringBuilder();
            while ((str = buf.readLine()) != null) {
                data.append(str);
            }
            logger.info("文件转换callBask取得data={}", data);
            if (data.length() > 0) {
                JSONObject dataJson = JSON.parseObject(data.toString());
                if (dataJson.get("Code") != null) {
                    String code = (String) dataJson.get("Code");
                    String taskId = (String) dataJson.get("TaskId");
                    String url = getConvertQueryRes(taskId);
                    if (!StringUtils.isEmpty(url) && code.equalsIgnoreCase(HttpStatus.OK.getReasonPhrase())) {
                        //
                        System.out.println(url);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件转换查询
     *
     * @param taskId 任务id，由convertFil接口生成
     */
    public String getConvertQueryRes(String taskId) {
        String headerDate = Common.getGMTDate();
        String downLoadUrl = "";
        try {
            //请求参数
            String contentMd5 = Common.getMD5(null); //请求内容数据的MD5值，用null作入参
            String url = convertProperties.getQuery() + "?TaskId=" + taskId + "&AppId=" + convertProperties.getAppid();
            String authorization = SignatureUtil.getAuthorization("GET", url, contentMd5, headerDate, convertProperties.getAppid(), convertProperties.getAppsecret()); //签名

            //header参数
            Map<String, String> headers = new LinkedHashMap<>();
            headers.put(HttpHeaders.CONTENT_TYPE, Common.CONTENTTYPE);
            headers.put(HttpHeaders.DATE, headerDate);
            headers.put(HttpHeaders.CONTENT_MD5, contentMd5);//文档上是 "Content-Md5"
            headers.put(HttpHeaders.AUTHORIZATION, authorization);

            //开始调用
            String result = HttpUtil.get(url, headers);
            if (!StringUtils.isEmpty(result)) {
                JSONObject dataJson = JSON.parseObject(result);
                String code = dataJson.get("Code").toString();
                if (code.equals("OK")) {
                    if (dataJson.get("Urls") != null) { //实际上返回这个参数
                        downLoadUrl = (dataJson.get("Urls")).toString();
                        // 源["xxx"]转换
                        JSONArray jsonArray = JSONArray.parseArray(downLoadUrl);
                        downLoadUrl = jsonArray.get(0).toString();
                    } else if (dataJson.get("Url") != null) {//文档是返回这个参数
                        downLoadUrl = dataJson.get("Url").toString();
                    }
                    //成功
                } else {
                    String errorMsg = "文件格式转换失败";
                    if (dataJson.get("Message") != null) {
                        String message = dataJson.get("Message").toString();
                        errorMsg = errorMsg + message;
                    }
                    //失败
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("recordWPSConvertResult处理出错，错误={}", e.getMessage(), e);
        }
        return downLoadUrl;
    }

}
