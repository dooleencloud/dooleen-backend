package com.dooleen.service.file.manage.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.dooleen.common.core.utils.aes.SecretAnnotation;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.config.oauth2.properties.OAuth2Properties;
import com.dooleen.service.file.manage.fastdfs.helpers.ConfigManager;
import com.dooleen.service.file.manage.fastdfs.util.CommonFileUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: FileUploadController
 * @Author: xz
 * @CreateDate: 2019/3/25 17:31
 * @Version: 1.0
 * 上传如果超过10m的文件会报错，最好修改tomcat的，如果使用js对图片要求不是太高可以进行图片压缩
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private CommonFileUtil fileUtil;

	@Autowired
	private OAuth2Properties oAuth2Properties;
    
    /**
     * 上传单文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    @SecretAnnotation(decode=false,encode=false)
    public JSONObject upload(@RequestParam("file") MultipartFile file) {
        //判断非空
    	JSONObject returnBody = new JSONObject();
    	JSONObject returnObj = new JSONObject();
        if (file.isEmpty()) {
            returnObj.put("respCode","E0001");
            returnObj.put("respMsg","上传文件列表为空！");
            returnBody.put("head", returnObj);
            return returnBody;
        }
        try {
        	List<String> ExtsDocument = Arrays.asList("cvs","html","htm","txt","zip", "wps", "pdf", "png", "jpg", "jpeg", "bmp", "ai","doc", "docx","xls", "xlsx", "xlsm", "pps", "ppsx", "ppsm","ppt", "pptx", "pptm");
        	int size = Integer.parseInt(ConfigManager.GetProperty("filesize-max").trim());
        	if(file.getSize() > size) {
        	    log.error("===>文件大小超过"+(size/(1024*1024))+"M");
        		returnObj.put("respCode","E0002");
                returnObj.put("respMsg","文件超过限制大小"+(size/(1024*1024))+"M");
                returnBody.put("head", returnObj);
                return returnBody;
        	}
        	String ext = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        	if(ext.equals("")) {
                log.error("===>文件扩展名不能为空");
                returnObj.put("respCode","E0003");
                returnObj.put("respMsg","文件扩展名不能为空！");
                returnBody.put("head", returnObj);
                return returnBody;
        	}
        	if(!ExtsDocument.contains(ext)) {
                log.error("===>不支持当前文件类型");
        		returnObj.put("respCode","E0004");
                returnObj.put("respMsg","不支持当前文件类型");
                returnBody.put("head", returnObj);
                return returnBody;
        	}
            // 测试MultipartFile接口的各个方法
            log.info("[文件类型ContentType] - [{}]",file.getContentType());
            log.info("[文件组件名称Name] - [{}]",file.getName());
            log.info("[文件原名称OriginalFileName] - [{}]",file.getOriginalFilename());
            log.info("[文件大小] - [{}]",file.getSize());

            String path = fileUtil.uploadFile(file);
            returnObj.put("respCode","S0000");
            returnBody.put("fileType",ext);
            returnBody.put("fileName",file.getOriginalFilename());
            returnBody.put("fileSize",file.getSize());
            returnBody.put("filePath",path);
            log.info("[文件路径path] - [{}]",path);
            returnBody.put("head", returnObj);
            return returnBody;
        } catch (Exception e) {
            e.printStackTrace();
            returnObj.put("respCode","E0005");
            returnObj.put("respMsg","文件上传错误！");
            returnBody.put("head", returnObj);
            return returnBody;
        }
    }

    /**
     * 使用字节流形式进行写文件
     * @param file
     */
    public void writeFile(MultipartFile file) {
        try {
        	String path = "";
            //获取输出流
            OutputStream os = new FileOutputStream(path + file.getOriginalFilename());
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is = file.getInputStream();
            byte[] buffer = new byte[1024];
            //判断输入流中的数据是否已经读完的标识
            int length = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while((length = is.read(buffer))!=-1){
                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                os.write(buffer, 0, length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 多文件上传
     * @param files
     * @return
     */
    @PostMapping("/uploadBatch")
    @ResponseBody
    public String uploadBatch(@RequestParam("files") MultipartFile[] files) {
        log.info("文件名称："+ files.length );
        if(files!=null&&files.length>0){
            String filePath = "/root/dooleen";
            for (MultipartFile mf : files) {
                // 获取文件名称
                String fileName = mf.getOriginalFilename();
                // 获取文件后缀
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                // 重新生成文件名
                fileName = UUID.randomUUID()+suffixName;

                if (mf.isEmpty()) {
                    return "文件名称："+ fileName +"上传失败，原因是文件为空!";
                }
                File dir = new File(filePath + fileName);
                try {
                    // 写入文件
                    mf.transferTo(dir);
                    log.info("文件名称："+ fileName +"上传成功");
                } catch (IOException e) {
                    log.error(e.toString(), e);
                    return "文件名称："+ fileName +"上传失败";
                }
            }
            return "多文件上传成功";
        }
        return "上传文件不能为空";
    }
}
