package com.dooleen.service.system.license.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.config.license.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.system.license.entity.LicenseEntity;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.functors.ConstantFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 用于生成证书文件，不能放在给客户部署的代码里
 * @author liqh
 * @date 2021/4/26
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/platform/license")
public class LicenseCreatorController {

    /**
     * 证书生成路径
     */
    @Value("${license.licensePath}")
    private String licensePath;

    /**
     * 获取服务器硬件信息
     * @author liqh
     * @date 2021/4/26 13:13
     * @since 1.0.0
     * @param osName 操作系统类型，如果为空则自动判断
     * @return com.ccx.models.license.LicenseCheckModel
     */
    @RequestMapping(value = "/getServerInfos",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public LicenseCheckModel getServerInfos(@RequestParam(value = "osName",required = false) String osName) {
        //操作系统类型
        if(StringUtils.isBlank(osName)){
            osName = System.getProperty("os.name");
        }
        osName = osName.toLowerCase();

        AbstractServerInfos abstractServerInfos = null;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        }else{//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }

    /**
     * 生成证书
     * @author liqh
     * @date 2021/4/26 13:13
     * @since 1.0.0
     * @param commonMsg
     */
    @ApiOperation(value = "生成license", notes = "")
    @RequestMapping(value = "/generateLicense",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="GENERAL")
    public CommonMsg<LicenseCreatorParam, NullEntity> generateLicense(@RequestBody CommonMsg<LicenseCreatorParam,NullEntity> commonMsg) {

//    public Map<String,Object> generateLicense(@RequestBody(required = true) LicenseCreatorParam param) {
        LicenseCreatorParam param =  commonMsg.getBody().getSingleBody();
        Map<String,Object> resultMap = new HashMap<>(2);

        if(StringUtils.isBlank(param.getLicensePath())){
            param.setLicensePath(licensePath);
        }

        LicenseCreator licenseCreator = new LicenseCreator(param);
        boolean result = licenseCreator.generateLicense();

        if(!result){
            BizResponseEnum.LICENSE_GENERAL_ERROR.assertIsTrue(false,commonMsg);
        }
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @RequestMapping(value = "downloadLicenseFile", method = RequestMethod.GET)
    public void download(HttpServletResponse response){
        log.info("====开始下载证书！");
        //获取该文件的名称、后缀
        File file = new File(licensePath);
        //文件在服务器的路径
        InputStream inputStream = null;
        OutputStream out = null;
        try {
            //根据文件在服务器的路径读取该文件转化为流
            String result = "";
            inputStream = new FileInputStream(file);
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            String fileName = "license.lic";
            //设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            //设置文件头：最后一个参数是设置下载文件名(设置编码格式防止下载的文件名乱码)
            response.setHeader("Content-Disposition", "attachment;fileName="+new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
            out = response.getOutputStream();
            int b = 0;
            while (b != -1){
                b = inputStream.read(buffer);
                //写到输出流(out)中
                out.write(buffer,0,b);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                out.close();
                out.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
