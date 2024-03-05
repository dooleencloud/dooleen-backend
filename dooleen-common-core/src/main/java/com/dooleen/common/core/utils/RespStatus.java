package com.dooleen.common.core.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2019-07-11 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :
 * @Maintainer:liqiuhong
 * @Update:
 */
public class RespStatus {

	/**
	 * 错误返回码
	 */
	public static final String errorCode = "E0001";
	
	/**
	 * 正确返回码
	 */
	public static final String succCode = "S0000";
	public static final String jsonStr = "{"
			+ "'S0000':'处理成功',"
			+ "'E0001':'数据校验未通过'," 
			+ "'E0002':'未找到记录'," 
			+ "'E0003':'保存记录失败'," 
			+ "'E0004':'删除记录失败'," 
			+ "'E0005':'输入数据为空'," 	
			+ "'E0006':'删除记录失败',"	
			+ "'E0007':'数据已被篡改',"
			+ "'E0008':'输入扩展数据为空'," 
			+ "'E0009':'用户ID为空'," 
			+ "'E0010':'记录已存在'," 
			+ "'E1001':'用户信息不存在'," 
			+ "'E1002':'用户密码输入错误',"
			+ "'E1003':'用户已注销！',"
			+ "'E1004':'用户已存在！',"
			+ "'E1005':'用户会议时间冲突！',"
			+ "'E4000':'数据签名错误！',"
			+ "'E4004':'没有接口访问权限！',"
			+ "'E4005':'登录令牌过期，请重新登录再试！',"
			+ "'E4006':'没有接口访问权限！',"
			+ "'E4007':'非令牌持有者，非法请求！',"
			+ "'E8001':'流程记录不存在！',"
			+ "'E8002':'流程节点记录不存在！',"
			+ "'E8003':'流程已处理过，无需重复提交！',"
			+ "'E8004':'流程节点处理记录不存在！',"
			+ "'E8005':'该节点不支持撤回！',"
			+ "'E8006':'会签不支持撤回！',"
			+ "'E8007':'下一节点已开始处理，不能撤回！',"
			+ "'E8008':'处理意见不能为空！',"
			+ "'E9001':'调微服务失败，进入断路器',"
			+ "'E9002':'亲，点击太快，请慢慢点！',"
			+ "'E9998':'处理异常',"
			+ "'E9999':'其他错误'" 
			+ "}";
	public static final JSONObject json = JSONObject.parseObject(jsonStr);
}
