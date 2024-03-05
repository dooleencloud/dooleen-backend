/*
 * Copyright 2015-2102 RonCoo(http://) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dooleen.common.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
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
public final class StringUtil {

	private static final Log LOG = LogFactory.getLog(StringUtil.class);

	/**
	 * 私有构造方法,将该工具类设为单例模式.
	 */
	private StringUtil() {
	}

	/**
	 * 函数功能说明 ： 判断字符串是否为空 . 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param str @参数： @return @return boolean @throws
	 */
	public static boolean isEmpty(String str) {
		return null == str || "".equals(str);
	}
	
	/**
	 * 函数功能说明 ： 判断字符串是否为空 . 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param str @参数： @return @return boolean @throws
	 */
	public static boolean isNull(Object str) {
		return null == str || "null".equals(str);
	}
	/**
	 * 函数功能说明 ： 判断字符串是否为空 . 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param str @参数： @return @return boolean @throws
	 */
	public static boolean isEmptyAndZero(String str) {
		return null == str || "null".equals(str) || "".equals(str) || "0".equals(str);
	}
	/**
	 * 函数功能说明 ： 判断对象数组是否为空. 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param obj @参数： @return @return boolean @throws
	 */
	public static boolean isEmpty(Object[] obj) {
		return null == obj || 0 == obj.length;
	}

	/**
	 * 函数功能说明 ： 判断对象是否为空. 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param obj @参数： @return @return boolean @throws
	 */
	public static boolean isEmpty(Object obj) {
		if (null == obj) {
			return true;
		}
		if (obj instanceof String) {
			return ((String) obj).trim().isEmpty();
		}
		return !(obj instanceof Number) ? false : false;
	}

	/**
	 * 函数功能说明 ： 判断集合是否为空. 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param obj @参数： @return @return boolean @throws
	 */
	public static boolean isEmpty(List<?> obj) {
		return null == obj || obj.isEmpty();
	}

	/**
	 * 函数功能说明 ： 判断Map集合是否为空. 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param obj @参数： @return @return boolean @throws
	 */
	public static boolean isEmpty(Map<?, ?> obj) {
		return null == obj || obj.isEmpty();
	}

	/**
	 * 函数功能说明 ： 获得文件名的后缀名. 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param fileName @参数： @return @return String @throws
	 */
	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 获取去掉横线的长度为32的UUID串.
	 * 
	 * @author zb.
	 * @return uuid.
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取带横线的长度为36的UUID串.
	 * 
	 * @author zb.
	 * @return uuid.
	 */
	public static String get36UUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 验证一个字符串是否完全由纯数字组成的字符串，当字符串为空时也返回false.
	 * 
	 * @author zb .
	 * @param str 要判断的字符串 .
	 * @return true or false .
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		} else {
			boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
			boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
			return isInt || isDouble;
		}
	}

	/**
	 * 计算采用utf-8编码方式时字符串所占字节数
	 * 
	 * @param content
	 * @return
	 */
	public static int getByteSize(String content) {
		int size = 0;
		if (null != content) {
			try {
				// 汉字采用utf-8编码时占3个字节
				size = content.getBytes("utf-8").length;
			} catch (UnsupportedEncodingException e) {
				LOG.error(e);
			}
		}
		return size;
	}

	/**
	 * 函数功能说明 ： 截取字符串拼接in查询参数. 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param ids @参数： @return @return String @throws
	 */
	public static List<String> getInParam(String param) {
		boolean flag = param.contains(",");
		List<String> list = new ArrayList<String>();
		if (flag) {
			list = Arrays.asList(param.split(","));
		} else {
			list.add(param);
		}
		return list;
	}

	/**
	 * 下划线转驼峰 user_name ----> userName house.user_name ----> userName userName --->
	 * userName
	 * 
	 * @param underlineName 带有下划线的名字
	 * @return 驼峰字符串
	 */
	public static String underlineToHump(String underlineName) {
		// 截取下划线分成数组
		char[] charArray = underlineName.toCharArray();
		// 判断上次循环的字符是否是"_"
		boolean underlineBefore = false;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0, l = charArray.length; i < l; i++) {
			// 判断当前字符是否是"_",如果跳出本次循环
			if (charArray[i] == 95) {
				underlineBefore = true;
			} else if (underlineBefore) {
				// 如果为true，代表上次的字符是"_",当前字符需要转成大写
				buffer.append(charArray[i] -= 32);
				underlineBefore = false;
			} else { // 不是"_"后的字符就直接追加
				buffer.append(charArray[i]);
			}
		}
		return buffer.toString();
	}

	/**
	 * 驼峰转 下划线 userName ----> user_name user_name ----> user_name
	 * 
	 * @param humpName 驼峰命名
	 * @return 带下滑线的String
	 */
	public static String humpToUnderline(String humpName) {
		// 截取下划线分成数组，
		char[] charArray = humpName.toCharArray();
		StringBuffer buffer = new StringBuffer();
		// 处理字符串
		for (int i = 0, l = charArray.length; i < l; i++) {
			if (charArray[i] >= 65 && charArray[i] <= 90) {
				buffer.append("_").append(charArray[i] += 32);
			} else {
				buffer.append(charArray[i]);
			}
		}
		return buffer.toString();
	}

	/**
	 * 
	 * 判断字符串是否json串
	 * 
	 * @Author : apple
	 */
	public static boolean isJson(String content) {
		if (content != null) {
			try {
				JSONObject.parseObject(content);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 判断字符串是否Array串
	 * 
	 * @Author : apple
	 */
	public static boolean isJSONArray(String content) {
		if (content != null) {
			try {
				JSON.parseArray(content);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
