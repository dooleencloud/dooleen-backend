package com.dooleen.service.file.wps.logic.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.general.file.info.entity.GeneralFileInfoEntity;
import com.dooleen.common.core.app.general.file.info.mapper.GeneralFileInfoMapper;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.service.file.wps.base.BaseRepository;
import com.dooleen.service.file.wps.base.BaseService;
import com.dooleen.service.file.wps.config.Context;
import com.dooleen.service.file.wps.logic.dto.FileDTO;
import com.dooleen.service.file.wps.logic.entity.UserEntity;
import com.dooleen.service.file.wps.logic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<UserEntity, String> {
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    @Autowired
    private GeneralFileInfoMapper generalFileInfoMapper;

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = UserRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public UserRepository getRepository() {
        return (UserRepository) this.baseRepository;
    }

    public Map<String, Object> userInfo(JSONObject reqObj) {
        String fileId = Context.getFileId();
        // 获取文件信息
        GeneralFileInfoEntity generalFileInfoEntity = generalFileInfoMapper.selectById(fileId);
        if(StringUtil.isNull(generalFileInfoEntity)){
            logger.info("===>>{}文件不存在，请检查....",fileId);
        }
        CommonMsg<JSONObject, NullEntity> exceptionCommonMsg = CreateCommonMsg.getCommonMsg(new JSONObject(), new NullEntity());
        exceptionCommonMsg.getHead().setTenantId(generalFileInfoEntity.getTenantId());
        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,exceptionCommonMsg);

        List<String> ids = null;

        if (reqObj != null) {
            if (reqObj.containsKey("ids")) {
                ids = JSONArray.parseArray(reqObj.getString("ids"), String.class);
            }
        }

        Map<String, Object> map = new HashMap<>();
        List<UserEntity> users = new ArrayList<>();
        if (ids != null && !ids.isEmpty()) {
            // 获取所有关联的人
            List<SendMsgUserInfoEntity> userList = sysUserInfoMapper.selectByUserNameList(generalFileInfoEntity.tenantId,ids);
            for(SendMsgUserInfoEntity userInfoEntityList: userList){
                UserEntity userEntity =  new UserEntity();
                userEntity.setId(userInfoEntityList.getUserName());
                userEntity.setName(userInfoEntityList.getRealName());
                userEntity.setAvatar_url(userInfoEntityList.getHeadImgUrl());
            }
        }
        map.put("users", users);
        return map;
    }

    public List<UserEntity> getUserList(){
        return  (List<UserEntity>) this.findAll();
    }

}
