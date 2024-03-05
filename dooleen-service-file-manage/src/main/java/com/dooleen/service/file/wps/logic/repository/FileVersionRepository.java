package com.dooleen.service.file.wps.logic.repository;

import com.dooleen.service.file.wps.base.BaseRepository;
import com.dooleen.service.file.wps.logic.entity.FileVersionEntity;

import java.util.List;

public interface FileVersionRepository extends BaseRepository<FileVersionEntity, Long> {

    List<FileVersionEntity> findByFileIdOrderByVersionDesc(String fileId);

    FileVersionEntity findByFileIdAndVersion(String fileId, int version);

}
