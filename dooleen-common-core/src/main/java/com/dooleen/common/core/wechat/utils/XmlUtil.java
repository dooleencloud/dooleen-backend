package com.dooleen.common.core.wechat.utils;

import com.dooleen.common.core.wechat.vo.PaymentEntity;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtil {
    public static Map<String,Object> parseXML(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,Object> map=new HashMap<String,Object>();
        /* 通过IO获得Document */
        SAXReader reader = new SAXReader();
        Document doc = reader.read(request.getInputStream());
        //得到xml的根节点
        Element root=doc.getRootElement();
        recursiveParseXML(root,map);
        return map;
    }
    private static void recursiveParseXML(Element root,Map<String,Object> map){
        //得到根节点的子节点列表
        List<Element> elementList=root.elements();
        //判断有没有子元素列表
        if(elementList.size()==0){
            map.put(root.getName(), root.getTextTrim());
        }
        else{
            //遍历
            for(Element e:elementList){
                recursiveParseXML(e,map);
            }
        }
    }
 
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点都增加CDATA标记
                boolean cdata = true;
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write(text);
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
    public static String messageToXML(PaymentEntity paymentPo){
        xstream.alias("xml",PaymentEntity.class);
        return xstream.toXML(paymentPo);
    }
}