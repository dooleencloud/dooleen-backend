package com.dooleen.common.core.utils;

import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

public class BeanUtils {

    /**
     * 将对象转换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map转换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }
    
    /**
     * 把一个字符串转换成bean对象
     * @param str
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
       return JSON.toJavaObject(JSON.parseObject(str), clazz);
 
    }

}