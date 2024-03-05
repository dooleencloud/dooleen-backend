package com.dooleen.service.file.wps.logic.service;

import com.dooleen.service.file.wps.base.BaseRepository;
import com.dooleen.service.file.wps.base.BaseService;
import com.dooleen.service.file.wps.logic.entity.WatermarkEntity;
import com.dooleen.service.file.wps.logic.repository.WatermarkRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WatermarkService extends BaseService<WatermarkEntity, Long> {

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = WatermarkRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public WatermarkRepository getRepository() {
        return (WatermarkRepository) this.baseRepository;
    }

    public void saveWatermark(String fileId) {
        this.save(new WatermarkEntity(fileId));
    }

}
