package com.dooleen.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class CopyFieldBeanUtil extends BeanUtils {
 
    /**
     * 将source中与target中类型和名称相同的属性值赋值给对应的entity的属性，并返回target
     *
     * @param source          源对象
     * @param target          目标对象
     * @param ignoreNullField 是否忽略空值
     */
    public static void copyProperties(Object source, Object target, Boolean ignoreNullField) {
        List<Map<String, Object>> sourceFields = getFieldInfo(source);
        if (ObjectUtils.isEmpty(sourceFields)) {
            return;
        }
        for (Map sourceFieldMap : sourceFields) {
            try {
                Field f = target.getClass().getDeclaredField(sourceFieldMap.get("name").toString());
                //源对象属性值为空 或属性类型不一致 则返回继续下一条
                if (ignoreNullField && ObjectUtils.isEmpty(sourceFieldMap.get("value")) ||
                        !sourceFieldMap.get("type").equals(f.getType().toString())) {
                    continue;
                }
                f.setAccessible(true);
                f.set(target, sourceFieldMap.get("value"));
            } catch (Exception ex) {
                //查看其父类属性
                //log.error(ex.getMessage(), "CustomBeanUtil类属性复制错误 " + ex);
            }
        }
    }

    /**
     * 根据属性名获取属性值
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
 
    /**
     * 获取属性名数组
     */
    private static String[] getFieldName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }
 
    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */
    private static List<Map<String, Object>> getFieldInfo(Object o) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (ObjectUtils.isEmpty(o)) {
            return null;
        }
        List<Field> fields = new ArrayList<>(Arrays.asList(o.getClass().getDeclaredFields()));
        //如果存在父类，获取父类的属性值，类型，名称并添加到一起
        Class sc = o.getClass().getSuperclass();
        if (sc != null) {
            fields.addAll(Arrays.asList(sc.getDeclaredFields()));
        }
        for (Field field : fields) {
            if(!field.getName().equals("serialVersionUID")) {
                Map<String, Object> infoMap = new HashMap<>();
                infoMap.put("type", field.getType().toString());
                infoMap.put("name", field.getName());
                infoMap.put("value", getFieldValueByName(field.getName(), o));
                list.add(infoMap);
            }
        }
        return list;
    }
}