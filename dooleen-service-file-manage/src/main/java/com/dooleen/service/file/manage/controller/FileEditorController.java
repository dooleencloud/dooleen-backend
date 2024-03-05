
package com.dooleen.service.file.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dooleen.common.core.app.general.file.history.entity.GeneralFileHistoryEntity;
import com.dooleen.common.core.app.general.file.info.entity.GeneralFileInfoEntity;
import com.dooleen.common.core.app.general.file.info.service.GeneralFileInfoService;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.config.oauth2.properties.OAuth2Properties;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.aes.AESUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.file.manage.fastdfs.entity.FileModel;
import com.dooleen.service.file.manage.fastdfs.helpers.ConfigManager;
import com.dooleen.service.file.manage.fastdfs.util.CommonFileUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
@Controller
public class FileEditorController {
	@Autowired
	private CommonFileUtil fileUtil;

	@Autowired
	private GeneralFileInfoService seneralFileInfoService;

	@Autowired
	private OAuth2Properties oAuth2Properties;

	// 跳转上传页面
	@RequestMapping("/file/office/index")
	@SecretAnnotation(decode=false,encode=false)
	public String fileEditorIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info(">>>开始进入文档编辑页面");
		// 获取参数
		Map userInfo = null;
		String fileId = "";
		String token = "";
		String sign = "";
		String getParam = "";
		String mode = "view";
		String historyFilePath = "";
		if (request.getParameter("id") != null && request.getParameter("tenantId") != null
				&& request.getParameter("UID") != null && request.getParameter("token") != null) {
			fileId = request.getParameter("id");
			// 解密文件ID
			byte[] fileIdByte = Base64.decodeBase64(fileId);
			fileId = new String(fileIdByte);

			token = request.getParameter("token");
			sign = request.getParameter("sign");
			mode = request.getParameter("mode");
			if (request.getParameter("history") != null) {
				historyFilePath = request.getParameter("history");
				// 解密文件history
				byte[] historyByte = Base64.decodeBase64(historyFilePath);
				historyFilePath = new String(historyByte);
			}
			try {
				Claims claims = Jwts.parser().setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes("UTF-8"))
						.parseClaimsJws(token).getBody();
				userInfo = (Map) claims.get("userInfo");
			} catch (ExpiredJwtException e) {
				model.addAttribute("errorMsg", "访问令牌过期！");
				return "/file/office/error";
			} catch (Exception e) {
				model.addAttribute("errorMsg", "其他错误，请联系管理员！");
				return "/file/office/error";
			}
		} else {
			model.addAttribute("errorMsg", "参数输入有误，请联系管理员！");
			return "/file/office/error";
		}
		// 组织callback 参数
		Map<String, String[]> map = request.getParameterMap();
		int mapIndex = 0;
		for (Entry<String, String[]> entry : map.entrySet()) {
			String mapKey = entry.getKey();
			String[] mapValue = entry.getValue();
			if (mapIndex == 0) {
				getParam += mapKey + "=" + mapValue[0];
			} else {
				if (mapIndex < map.size() - 1) {
					getParam += "&" + mapKey + "=" + mapValue[0];
				}
			}
			mapIndex++;
		}
		log.info(" getParam = " + getParam);
		// 判断签名是否正确（防篡改）
		String tmpSign = DooleenMD5Util.md5(getParam, ConstantValue.md5Key);
		if (!tmpSign.equals(sign)) {
			model.addAttribute("errorMsg", "签名校验错误！");
			return "/file/office/error";
		}
		log.info(" token userinfo = " + userInfo.toString());
		String tokenUserId = (String) userInfo.get("userId");
		String tokenUserName = (String) userInfo.get("userName");
		String tokenTenantId = (String) userInfo.get("tenantId");
		String tokenRealName = (String) userInfo.get("realName");
		String tokenCompanyName = (String) userInfo.get("companyName");

		log.debug(">>>获取参数 fileId = " + fileId + "; userId=" + tokenUserId);
		log.debug(">>> 开始调用微服务获取文件信息");
		GeneralFileInfoEntity generalFileInfoEntity = new GeneralFileInfoEntity();
		generalFileInfoEntity.setTenantId(tokenTenantId);
		generalFileInfoEntity.setId(fileId);
		GeneralFileHistoryEntity generalFileHistoryEntity = new GeneralFileHistoryEntity();
		CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> fileCommonMsg = CreateCommonMsg
				.getCommonMsg(generalFileInfoEntity, generalFileHistoryEntity);

		List<GeneralFileHistoryEntity> generalFileHistoryList =new ArrayList();

		fileCommonMsg = seneralFileInfoService.queryGeneralFileInfoAndHistory(fileCommonMsg);
		generalFileInfoEntity = fileCommonMsg.getBody().getSingleBody();
		generalFileHistoryList = fileCommonMsg.getBody().getListBody();

		// 如果返回不成功，直接返回错误到页面
		log.info(">>>返回文件信息 {}", fileCommonMsg.toString());
		if (!fileCommonMsg.getHead().getRespCode().equals("S0000")) {
			model.addAttribute("errorMsg", fileCommonMsg.getHead().getRespMsg().toString());
			return "/file/office/error";
		}
		log.debug(">>> 准备进入主页面....");
		String fileName = generalFileInfoEntity.getFileName();
		FileModel file = new FileModel(fileName, tokenTenantId);
		log.debug(">>fileName = " + fileName);
		log.debug(">>documentType = " + file.documentType);
		log.debug(">>key = " + generalFileInfoEntity.getDataSign());
		log.debug(">>file.document.fileType = " + file.document.fileType);
		// 设置文件路径
		if (StringUtil.isEmpty(historyFilePath)) {
			historyFilePath = generalFileInfoEntity.getFilePath();
		} else {
			// 查看历史用随机key值
			Random random = new Random();
			generalFileInfoEntity.setFileSign(System.currentTimeMillis() + "" + random.nextInt(1000));
		}
		log.debug(">>path = " + ConfigManager.GetProperty(tokenTenantId + ".fdfs.resHost").trim() + historyFilePath);

		// model.addAttribute("files", file);
		model.addAttribute("documentId", generalFileInfoEntity.getId());
		model.addAttribute("documentTenantId", generalFileInfoEntity.getTenantId());
		model.addAttribute("documentKey", generalFileInfoEntity.getFileSign());
		model.addAttribute("documentType", file.documentType);
		model.addAttribute("documentTitle", fileName);
		model.addAttribute("documentFileType", file.document.fileType);
		model.addAttribute("documentUrl",
				ConfigManager.GetProperty(tokenTenantId + ".fdfs.resHost").trim() + historyFilePath);
		model.addAttribute("docserviceApiUrl",
				ConfigManager.GetProperty(tokenTenantId + ".files.docservice.url.api").trim());
		model.addAttribute("documentCallbackUrl",
				ConfigManager.GetProperty(tokenTenantId + ".files.docservice.url.callback").trim() + "?" + getParam);

		Map<String, String> userInfoMap = new HashMap();
		userInfoMap.put("id", tokenUserName);
		userInfoMap.put("name", tokenRealName);
		userInfoMap.put("userName", tokenUserName);
		userInfoMap.put("companyName", tokenCompanyName);
		model.addAttribute("userInfo", userInfoMap);

		// 组织历史记录
		List<JSONObject> list = new ArrayList<>();
		List<JSONObject> recentList = new ArrayList<>();
		List<JSONObject> historyDataList = new ArrayList<>();
		// 组装历史数据
		JSONObject historyDataJson = new JSONObject();
		for (int i = 0; i < generalFileHistoryList.size(); i++) {
			JSONObject historyJson = new JSONObject();
			historyJson.put("created", generalFileHistoryList.get(i).getCreateDatetime());
			historyJson.put("key", generalFileHistoryList.get(i).getFileSign());
			JSONObject userJson = new JSONObject();
			userJson.put("id", generalFileHistoryList.get(i).getCreateUserName());
			userJson.put("name", generalFileHistoryList.get(i).getCreateRealName());
			historyJson.put("user", userJson);
			historyJson.put("version", generalFileHistoryList.get(i).getFileVersionNo());
			list.add(historyJson);
			// 最近打开
			JSONObject recentJson = new JSONObject();
			recentJson.put("folder", "");
			recentJson.put("title", generalFileHistoryList.get(i).getFileName());
			recentJson.put("url", ConfigManager.GetProperty(tokenTenantId + ".fdfs.resHost").trim()
					+ generalFileHistoryList.get(i).getFilePath() + "?" + getParam);
			recentList.add(recentJson);

			if (i == 0) {
				historyDataJson.put("key", generalFileHistoryList.get(i).getFileSign());
				historyDataJson.put("url", ConfigManager.GetProperty(tokenTenantId + ".fdfs.resHost").trim()
						+ generalFileHistoryList.get(i).getFilePath() + "?" + getParam);
				historyDataJson.put("version", generalFileHistoryList.get(i).getFileVersionNo());
			}
		}
		model.addAttribute("currentVersion", generalFileInfoEntity.getLastVersionNo());
		model.addAttribute("history", list.toString());
		model.addAttribute("historyData", historyDataJson.toString());
		model.addAttribute("mode", mode);
		model.addAttribute("recent", recentList.toString());
		log.info("==== {}", list.toString());
		return "/file/office/editor";
	}


	// 跳转上传页面
	@RequestMapping("/file/office/callBack")
	@SecretAnnotation(decode=false,encode=false)
	public void callBack(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = new JSONObject();
		log.info(">>>开始进入文档编辑页面");

		// 获取参数
		Map userInfo = null;
		String fileId = "";
		String token = "";
		String sign = "";
		String historyFilePath = "";
		if (request.getParameter("id") != null && request.getParameter("tenantId") != null
				&& request.getParameter("UID") != null && request.getParameter("token") != null) {
			fileId = request.getParameter("id");
			token = request.getParameter("token");
			sign = request.getParameter("sign");
			if (request.getParameter("history") != null) {
				historyFilePath = request.getParameter("history");
				// 解密文件history
				byte[] historyByte = Base64.decodeBase64(historyFilePath);
				historyFilePath = new String(historyByte);
			}
			try {
				Claims claims = Jwts.parser().setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes("UTF-8"))
						.parseClaimsJws(token).getBody();
				userInfo = (Map) claims.get("userInfo");
			} catch (ExpiredJwtException e) {
				writer.write("{\"error\":\"访问令牌过期！\"}");
			} catch (Exception e) {
				writer.write("{\"error\":\"其他错误，请联系管理员！\"}");
			}
		} else {
			writer.write("{\"error\":\"参数输入有误，请联系管理员！\"}");
		}

		log.info(" token userinfo = " + userInfo.toString());
		String tokenUserId = (String) userInfo.get("userId");
		String tokenUserName = (String) userInfo.get("userName");
		String tokenTenantId = (String) userInfo.get("tenantId");
		String tokenRealName = (String) userInfo.get("realName");
		byte[] fileIdByte = Base64.decodeBase64(fileId);
		fileId = new String(fileIdByte);

		try {
			writer = response.getWriter();
			Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
			String body = scanner.hasNext() ? scanner.next() : "";
			log.info("body ==== {}", body);
			//jsonObj = (JSONObject) new JSONParser().parse(body);
			jsonObj = JSON.parseObject(body);
			/*
			 * 0 - no document with the key identifier could be found, 1 - document is being
			 * edited, 2 - document is ready for saving, 3 - document saving error has
			 * occurred, 4 - document is closed with no changes, 6 - document is being
			 * edited, but the current document state is saved, 7 - error has occurred while
			 * force saving the document.
			 */
			/*
			 * status =
			 * 1，我们给onlyoffice的服务返回{"error":"0"}的信息，这样onlyoffice会认为回调接口是没问题的，这样就可以在线编辑文档了，
			 * 否则的话会弹出窗口说明
			 */
			/*
			 * 当我们关闭编辑窗口后，十秒钟左右onlyoffice会将它存储的我们的编辑后的文件，，此时status =
			 * 2，通过request发给我们，我们需要做的就是接收到文件然后回写该文件。
			 */

			if (jsonObj.getInteger("status") == 2 || jsonObj.getInteger("status") == 6) {
				try {
					String downloadUri = (String) jsonObj.get("url");
					String fileSize = "";
					log.debug(">>>>" + jsonObj.get("status") + "文档编辑完成，临时保存地址" + downloadUri);
					FileModel file = new FileModel(downloadUri, tokenTenantId);
					URL url = new URL(downloadUri);
					java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
					InputStream stream = connection.getInputStream();
					fileSize = stream.available() + "";
					String path = fileUtil.uploadFileStream(stream, connection.getContentLength(),
							file.document.fileType);
					connection.disconnect();
					log.debug(">>>===文档上传成功，地址：" + path);

					log.debug(">>>获取参数 fileId = " + fileId + "; userId=" + tokenUserId + "; fize=" + fileSize
							+ ";historyFilePath=" + historyFilePath);
					log.debug(">>> 开始调用微服务保存文件信息");
					GeneralFileInfoEntity generalFileInfoEntity = new GeneralFileInfoEntity();
					generalFileInfoEntity.setTenantId(tokenTenantId);
					generalFileInfoEntity.setId(fileId);
					generalFileInfoEntity.setFilePath(path);
					generalFileInfoEntity.setFileSize(fileSize);
					generalFileInfoEntity.setFileName("0");
					generalFileInfoEntity.setCatalogId("0");
					generalFileInfoEntity.setFileType("0");
					generalFileInfoEntity.setFileSign(DooleenMD5Util.md5(path, ConstantValue.md5Key));

					GeneralFileHistoryEntity generalFileHistoryEntity = new GeneralFileHistoryEntity();
					CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> fileCommonMsg = CreateCommonMsg
							.getCommonMsg(generalFileInfoEntity, generalFileHistoryEntity);
					fileCommonMsg.getHead().setRealName(tokenRealName);
					fileCommonMsg.getHead().setTenantId(tokenTenantId);
					fileCommonMsg.getHead().setUserName(tokenUserName);
					fileCommonMsg.getHead().setTransCode("saveGeneralFileInfoAndHistory");
					fileCommonMsg = seneralFileInfoService.saveGeneralFileInfoAndHistory(fileCommonMsg);

					// 如果返回不成功，直接返回错误到页面
					log.debug(">>>返回文件信息" + fileCommonMsg.toString());
					if (!fileCommonMsg.getHead().getRespCode().equals("S0000")) {
						writer.write("{\"error\":" + fileCommonMsg.getHead().getRespMsg().toString() + "}");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				writer.write("{\"error\":0}");
			} else if (jsonObj.getInteger("status") == 1) {
				log.info(">>>" + jsonObj.getInteger("status") + "===文档以编辑模式打开====");
				writer.write("{\"error\":0}");
			} else if (jsonObj.getInteger("status") == 3) {
				log.info(">>>" + jsonObj.getInteger("status") + "===文档保存时发生错误====");
				writer.write("{\"error\":0}");
			} else {
				log.info(">>>===其他状态码：= " + jsonObj.getInteger("status"));
				writer.write("{\"error\":0}");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
