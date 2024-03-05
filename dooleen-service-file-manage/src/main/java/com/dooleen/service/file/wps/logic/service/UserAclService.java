package com.dooleen.service.file.wps.logic.service;

import com.dooleen.service.file.wps.base.BaseRepository;
import com.dooleen.service.file.wps.base.BaseService;
import com.dooleen.service.file.wps.logic.entity.UserAclEntity;
import com.dooleen.service.file.wps.logic.repository.UserAclRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserAclService extends BaseService<UserAclEntity, Long> {

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = UserAclRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public UserAclRepository getRepository() {
        return (UserAclRepository) this.baseRepository;
    }

    public void saveUserFileAcl(String userId, String fileId) {
        this.save(new UserAclEntity(userId, fileId));
    }

}
