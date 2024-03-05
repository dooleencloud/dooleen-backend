package com.dooleen.service.file.wps.logic.repository;

import com.dooleen.service.file.wps.base.BaseRepository;
import com.dooleen.service.file.wps.logic.entity.WatermarkEntity;

public interface WatermarkRepository extends BaseRepository<WatermarkEntity, Long> {

    WatermarkEntity findFirstByFileId(String fileId);

}
