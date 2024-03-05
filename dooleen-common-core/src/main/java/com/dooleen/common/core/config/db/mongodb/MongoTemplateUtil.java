package com.dooleen.common.core.config.db.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年5月9日 下午10:08:51
 * @Description : 生成代码工具类
 * @Author : apple
 * @Update: 2020年5月9日 下午10:08:51
 */
@Slf4j
@Service
public class MongoTemplateUtil {
	
	@Autowired
    @Qualifier(value = "DOOL1001MongoTemplate")
    private  MongoTemplate DOOL1001MongoTemplate;
    
    @Autowired
    @Qualifier(value = "DOOL1002MongoTemplate")
    private  MongoTemplate DOOL1002MongoTemplate;

    @Autowired
    @Qualifier(value = "DOOL1003MongoTemplate")
    private  MongoTemplate DOOL1003MongoTemplate;
    
    @Autowired
    @Qualifier(value = "DOOL1004MongoTemplate")
    private  MongoTemplate DOOL1004MongoTemplate;
    
    @Autowired
    @Qualifier(value = "DOOL1005MongoTemplate")
    private  MongoTemplate DOOL1005MongoTemplate;
    
    @Autowired
    @Qualifier(value = "DOOL1006MongoTemplate")
    private  MongoTemplate DOOL1006MongoTemplate;
    
    @Autowired
    @Qualifier(value = "DOOL1007MongoTemplate")
    private  MongoTemplate DOOL1007MongoTemplate;
    
    @Autowired
    @Qualifier(value = "DOOL1008MongoTemplate")
    private  MongoTemplate DOOL1008MongoTemplate;
    
    @Autowired
    @Qualifier(value = "DOOL1009MongoTemplate")
    private  MongoTemplate DOOL1009MongoTemplate;
    
    @Autowired
    @Qualifier(value = "DOOL1010MongoTemplate")
    private  MongoTemplate DOOL1010MongoTemplate;
    
    @Autowired
    @Qualifier(value = "DOOL1011MongoTemplate")
    private  MongoTemplate DOOL1011MongoTemplate;
    
    
	public  MongoTemplate getMongoTemplate(String tenantId) {
		switch(tenantId){
		case "DOOL1001":
			 return DOOL1001MongoTemplate;
			 
		case "DOOL1002":
			 return DOOL1002MongoTemplate;
			 
		case "DOOL1003":
			 return DOOL1003MongoTemplate;
			 
		case "DOOL1004":
			 return DOOL1004MongoTemplate;
			 
		case "DOOL1005":
			 return DOOL1005MongoTemplate;
			 
		case "DOOL1006":
			 return DOOL1006MongoTemplate;
			 
		case "DOOL1007":
			 return DOOL1007MongoTemplate;
			 
		case "DOOL1008":
			 return DOOL1008MongoTemplate;
			 
		case "DOOL1009":
			 return DOOL1009MongoTemplate;
			 
		case "DOOL1010":
			 return DOOL1003MongoTemplate;
			 
		case "DOOL1011":
			 return DOOL1011MongoTemplate;
			 
		default:
			return DOOL1001MongoTemplate;
		}
	}
}
