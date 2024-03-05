package com.dooleen.service.file.wps.logic.repository;

import com.dooleen.service.file.wps.base.BaseRepository;
import com.dooleen.service.file.wps.logic.entity.UserAclEntity;

public interface UserAclRepository extends BaseRepository<UserAclEntity, Long> {

    UserAclEntity findFirstByFileIdAndUserId(String fileId, String userId);

}
