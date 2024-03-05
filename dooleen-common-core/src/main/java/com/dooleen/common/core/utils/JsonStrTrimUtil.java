package com.dooleen.common.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Set;

public class JsonStrTrimUtil {
    public static JSONObject strTrim(String jsonStr){
        JSONObject reagobj = JSONObject.parseObject(jsonStr);
        Set<String> keySet = reagobj.keySet();
        Iterator<String> itt = keySet.iterator();
        while (itt.hasNext()) {
            String key = itt.next();
            Object obj = reagobj.get(key);
            if(obj instanceof JSONObject){
                Set<String> keySets = ((JSONObject) obj).keySet();
                Iterator<String> iterator = keySets.iterator();
                while (iterator.hasNext()) {
                    String key1 = iterator.next();
                    Object twoObj = ((JSONObject) obj).get(key1);
                    if(twoObj instanceof String){
                        if(twoObj == null){
                            continue ;
                        }else if("".equals(twoObj.toString().trim())){
                            continue ;
                        }else{
                            ((JSONObject) obj).put(key1, twoObj.toString().trim());
                        }
                    }
                    else if(twoObj instanceof JSONObject) {
                        Set<String> threeKeySets = ((JSONObject) twoObj).keySet();
                        Iterator<String> threeIterator = threeKeySets.iterator();
                        while (threeIterator.hasNext()) {
                            String threeKey = threeIterator.next();
                            Object threeOb = ((JSONObject) twoObj).get(threeKey);
                            if (threeOb instanceof String) {
                                if (threeOb == null) {
                                    continue;
                                } else if ("".equals(threeOb.toString().trim())) {
                                    continue;
                                } else {
                                    ((JSONObject) twoObj).put(threeKey, threeOb.toString().trim());
                                }
                            }
                        }
                    }
                }
                reagobj.put(key, obj);
            }else if(obj instanceof JSONArray){
                for(int i=0;i<((JSONArray) obj).size();i++){
                    Object o = ((JSONArray) obj).get(i);
                    if(o instanceof JSONObject){
                        Set<String> keySets = ((JSONObject) o).keySet();
                        Iterator<String> iterator = keySets.iterator();
                        while (iterator.hasNext()) {
                            String key1 = iterator.next();
                            Object o1 = ((JSONObject) o).get(key1);
                            if(o1 instanceof String){
                                if(o1 == null){
                                    continue ;
                                }else if("".equals(o1.toString().trim())){
                                    continue ;
                                }else{
                                    ((JSONObject) o).put(key1, o1.toString().trim());
                                }
                            }
                            else if(o1 instanceof JSONObject){
                                Set<String> keySets2 = ((JSONObject) o).keySet();
                                Iterator<String> iterator2 = keySets2.iterator();
                                while (iterator2.hasNext()) {
                                    String key2 = iterator2.next();
                                    Object o2 = ((JSONObject) o1).get(key2);
                                    if(o1 instanceof String){
                                        if(o2 == null){
                                            continue ;
                                        }else if("".equals(o2.toString().trim())){
                                            continue ;
                                        }else{
                                            ((JSONObject) o1).put(key2, o2.toString().trim());
                                        }
                                    }
                                }
                            }
                        }
                    }else if(o instanceof String){
                        if(o == null){
                            continue ;
                        }else if("".equals(o.toString().trim())){
                            continue ;
                        }else{
                            ((JSONArray) obj).set(i,((String) o).trim());
                        }
                    }
                }
                reagobj.put(key, obj);
            }else if(obj instanceof String){
                if(obj == null){
                    continue ;
                }else if("".equals(obj.toString().trim())){
                    continue ;
                }else{
                    reagobj.put(key, obj.toString().trim());
                }
            }
        }
        return reagobj;
    }}