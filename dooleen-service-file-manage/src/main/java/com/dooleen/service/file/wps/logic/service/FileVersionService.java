package com.dooleen.service.file.wps.logic.service;

import com.dooleen.service.file.wps.base.BaseRepository;
import com.dooleen.service.file.wps.base.BaseService;
import com.dooleen.service.file.wps.logic.entity.FileVersionEntity;
import com.dooleen.service.file.wps.logic.repository.FileVersionRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FileVersionService extends BaseService<FileVersionEntity, Long> {

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = FileVersionRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public FileVersionRepository getRepository() {
        return (FileVersionRepository) this.baseRepository;
    }

}
