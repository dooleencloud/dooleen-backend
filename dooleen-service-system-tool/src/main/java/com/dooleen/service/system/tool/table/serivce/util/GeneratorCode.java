package com.dooleen.service.system.tool.table.serivce.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
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
public class GeneratorCode {

	/**
	 * 输出到文件
	 * @param infoMap  传入的map
	 * @param ftlPath  ftl所在路径
	 * @param ftlName  ftl文件名
	 * @param filePath 输出前的文件全部路径
	 * @param outFile  输出后的文件
	 * @throws Exception
	 * @Return 
	 */
	public static void genCode(Map<String, Object> infoMap,String ftlPath, String ftlName, String outPath, String outFile) {
		log.info("====开始生成代码 == "+outPath+"/"+outFile);
		try {
			File file = new File(outPath+"/"+outFile);
			// 判断有没有父路径，就是判断文件整个路径是否存在
			if (!file.getParentFile().exists()) { 
				// 不存在就全部创建
				file.getParentFile().mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			Template template = getTemplate(ftlName, ftlPath);
			template.process(infoMap, out);
			out.flush();
			out.close();
		} catch (Exception te) {
			te.printStackTrace();
		}
		log.info("====生成代码结束 == ");
	}

	/**
	 * 通过文件名加载模板
	 * 
	 * @param ftlName ftl文件名
	 * @param ftlPath ftl文件路径
	 * @return
	 * @throws Exception
	 */
	public static Template getTemplate(String ftlName, String ftlPath) throws Exception {
		try {
			Configuration cfg = new Configuration();
			cfg.setDefaultEncoding("UTF-8");
			cfg.setDirectoryForTemplateLoading(new File(ftlPath));
			// 在模板文件目录中找到名称为name的文件
			Template template = cfg.getTemplate(ftlName);
			return template;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}  
}
