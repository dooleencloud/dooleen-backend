package com.dooleen.common.core.config.license;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于获取客户Linux服务器的基本信息
 *
 * @author liqh
 * @date 2021/4/23
 * @since 1.0.0
 */
@Slf4j
public class LinuxServerInfos extends AbstractServerInfos {

    @Override
    protected List<String> getIpAddress() throws Exception {
        List<String> result = null;

        //获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if(inetAddresses != null && inetAddresses.size() > 0){
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    protected List<String> getMacAddress() throws Exception {
        List<String> result = null;

        //1. 获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();
        log.info("===>>inetAddresses.size()="+inetAddresses.size());
        if(inetAddresses != null && inetAddresses.size() > 0){

            //2. 获取所有网络接口的Mac地址
            result = inetAddresses.stream().map(this::getMacByInetAddress).distinct().collect(Collectors.toList());
        }
        log.info("==>>当前Mac地址为：{}",result.toString());
        return result;
    }

    @Override
    protected String getCPUSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用dmidecode命令获取CPU序列号
        String[] shell = {"/bin/bash","-c","dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        if(reader.readLine() != null) {
            String line = reader.readLine().trim();
            if (StringUtils.isNotBlank(line)) {
                serialNumber = line;
            }
        }
        else{
            serialNumber = "ASCBFWER015406E1";
        }

        reader.close();
        log.info("==>>当前cpuSerial:{}",serialNumber);
        return serialNumber;
    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";
        //使用dmidecode命令获取主板序列号
        String[] shell = {"/bin/bash","-c","dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        if(reader.readLine() != null) {
            String line = reader.readLine().trim();
            if (StringUtils.isNotBlank(line)) {
                serialNumber = line;
            }
        }
        else{
            serialNumber = "S1DC65E10X9";
        }
        reader.close();
        return serialNumber;
    }
}
