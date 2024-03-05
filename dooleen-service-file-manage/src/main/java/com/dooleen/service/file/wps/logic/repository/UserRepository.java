package com.dooleen.service.file.wps.logic.repository;

import com.dooleen.service.file.wps.base.BaseRepository;
import com.dooleen.service.file.wps.logic.entity.UserEntity;

import java.util.List;

public interface UserRepository extends BaseRepository<UserEntity, String> {

    List<UserEntity> findByIdIn(List<String> id);

}
