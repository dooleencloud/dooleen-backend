package com.dooleen.common.core.mq.msg.email;

import java.io.Serializable;

import lombok.Data;

@Data
public class Attachment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 文件fastDFS的位置
	 */
	private String fastDFSPath;
	
	/**
	 * 文件中文名称
	 */
	private String fileChName;
}
