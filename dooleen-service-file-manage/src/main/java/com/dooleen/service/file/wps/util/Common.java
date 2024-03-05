package com.dooleen.service.file.wps.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Common {
    public static final String CONTENTTYPE  = "application/json";

    public static String getGMTDate() {
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        return sdf.format(cd.getTime());
    }

    public static String getMD5(Map<String, Object> paramMap) {
        try {
            String req = "";
            if (paramMap != null) {
                req = JSON.toJSONString(paramMap);
            }
            return DigestUtils.md5Hex(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
