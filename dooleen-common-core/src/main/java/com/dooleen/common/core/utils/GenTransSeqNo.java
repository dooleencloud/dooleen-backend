package com.dooleen.common.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.dooleen.common.core.common.entity.CommonMsg;
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
public class GenTransSeqNo { 
	public static void SetTransSeqNo(CommonMsg repMsg)
	{
		Random random = new Random();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String transSeqNo = repMsg.getHead().getChainnelId() + repMsg.getHead().getTransCode() + df.format(new Date());//+random.nextInt(9)+random.nextInt(9);
		repMsg.getHead().setTransSeqNo(transSeqNo);
	}
}
