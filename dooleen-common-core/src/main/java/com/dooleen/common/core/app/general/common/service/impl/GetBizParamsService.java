/**
 * 
 */
package com.dooleen.common.core.app.general.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dooleen.common.core.app.general.dict.entity.GeneralBizDictEntity;
import com.dooleen.common.core.app.general.dict.mapper.GeneralBizDictMapper;
import com.dooleen.common.core.app.general.param.entity.GeneralBizParamEntity;
import com.dooleen.common.core.app.general.param.mapper.GeneralBizParamMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.enums.BizResponseEnum;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年6月6日 下午8:13:50
 * @Description : 数据库字段表接口
 * @Author : apple
 * @Update: 2020年6月6日 下午8:13:50
 */
@Service
public class GetBizParamsService {
	@Autowired
	private  GeneralBizParamMapper generalBizParamMapper;
	
	@Autowired
	private  GeneralBizDictMapper generalBizDictMapper;
	/**
	 * 获取业务参数
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : commonMsg 
	 * @Param : key 
	 * @Return : String 
	 */
	public String getBizParam(CommonMsg commonMsg, String key){
		QueryWrapper<GeneralBizParamEntity> bizParamQueryWrapper = new QueryWrapper<GeneralBizParamEntity>();
		bizParamQueryWrapper.lambda().eq(GeneralBizParamEntity::getTenantId, commonMsg.getHead().getTenantId());
		bizParamQueryWrapper.lambda().eq(GeneralBizParamEntity::getParamKey, key);
		GeneralBizParamEntity paramEntity = generalBizParamMapper.selectOne(bizParamQueryWrapper);
		BizResponseEnum.BIZ_PARAM_NOT_FOUND.assertNotNull(paramEntity,commonMsg);
		return paramEntity.getParamValue();
	}
	
	/**
	 * 获取业务字典参数
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : commonMsg 
	 * @Param : key 
	 * @Return : List<JSONObject>
	 */
	public List<JSONObject> getBizDict(CommonMsg commonMsg, String dictNo){
		QueryWrapper<GeneralBizDictEntity> bizParamQueryWrapper = new QueryWrapper<GeneralBizDictEntity>();
		bizParamQueryWrapper.lambda().eq(GeneralBizDictEntity::getTenantId, commonMsg.getHead().getTenantId());
		bizParamQueryWrapper.lambda().eq(GeneralBizDictEntity::getDictNo, dictNo);
		GeneralBizDictEntity paramEntity = generalBizDictMapper.selectOne(bizParamQueryWrapper);
		BizResponseEnum.BIZ_DICT_NOT_FOUND.assertNotNull(paramEntity,commonMsg);
		return (List<JSONObject>) JSONArray.parse(paramEntity.getDictList());
	}
	
	/**
	 * 获取业务字典参数
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : commonMsg 
	 * @Param : key 
	 * @Return : List<JSONObject>
	 */
	public String getBizDictValue(List<JSONObject> dictList,String key){
		String value = "";
		for(int i=0; i < dictList.size(); i++) {
            if(key.equals(dictList.get(i).getString("label"))){
            	value = dictList.get(i).getString("value");
            }
        }
		return value;
	}
	
	
}
