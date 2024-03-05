package com.dooleen.service.file.manage.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dooleen.common.core.app.general.file.history.entity.GeneralFileHistoryEntity;
import com.dooleen.common.core.app.general.file.info.entity.GeneralFileInfoEntity;
import com.dooleen.common.core.app.general.file.info.service.GeneralFileInfoService;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.config.oauth2.properties.OAuth2Properties;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.service.file.manage.fastdfs.helpers.ConfigManager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: FileUploadController
 * @Author: xz
 * @CreateDate: 2019/3/25 17:31
 * @Version: 1.0 文件下载
 */
@Slf4j
@Controller
public class FileDownloadController {

	@Autowired
	private OAuth2Properties oAuth2Properties;

	@Autowired
	private GeneralFileInfoService seneralFileInfoService;

	@GetMapping("/file/office/downloadfile")
	@SecretAnnotation(decode=false,encode=false)
	public String downloadFile(Model model, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String fileName = "";// 文件名
		Map userInfo = null;
		String fileId = "";
		String token = "";
		String sign = "";
		String getParam = "";
		String mode = "view";
		if (request.getParameter("id") != null && request.getParameter("tenantId") != null
				&& request.getParameter("UID") != null && request.getParameter("token") != null) {
			fileId = request.getParameter("id");
			token = request.getParameter("token");
			sign = request.getParameter("sign");
			mode = request.getParameter("mode");
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
		} 
		String tokenTenantId = (String) userInfo.get("tenantId"); 
		// 解密文件ID
		byte[] fileIdByte = Base64.decodeBase64(fileId);
		fileId = new String(fileIdByte);
		log.debug(">>> 开始调用微服务获取文件信息");
		GeneralFileInfoEntity generalFileInfoEntity = new GeneralFileInfoEntity();
		generalFileInfoEntity.setTenantId(tokenTenantId);
		generalFileInfoEntity.setId(fileId);
		GeneralFileHistoryEntity generalFileHistoryEntity = new GeneralFileHistoryEntity();
		CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> fileCommonMsg = CreateCommonMsg
				.getCommonMsg(generalFileInfoEntity, generalFileHistoryEntity);
		fileCommonMsg = seneralFileInfoService.queryGeneralFileInfoAndHistory(fileCommonMsg);
		// 如果返回不成功，直接返回错误到页面
		log.info(">>>返回文件信息 {}", fileCommonMsg.toString());
		if (!fileCommonMsg.getHead().getRespCode().equals("S0000")) {
			model.addAttribute("errorMsg", fileCommonMsg.getHead().getRespMsg().toString());
			return "/file/office/error";
		}
		generalFileInfoEntity = fileCommonMsg.getBody().getSingleBody();
		if (generalFileInfoEntity != null) {
			try {
				fileName = generalFileInfoEntity.getFileName();
				String downloadUri = ConfigManager.GetProperty(tokenTenantId + ".fdfs.resHost").trim() + generalFileInfoEntity.getFilePath();
				URL url = new URL(downloadUri);
				java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
				InputStream stream = connection.getInputStream();
				
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition", "attachment; fileName=" + fileName + ";filename*=utf-8''"
						+ URLEncoder.encode(fileName, "UTF-8"));
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					bis = new BufferedInputStream(stream);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					log.info("" + i);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					return "下载成功";
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return "文件不存在";
	}
}
