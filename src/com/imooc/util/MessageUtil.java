package com.imooc.util;

import com.imooc.po.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 11:49 2017/11/2
 * @Modified_by:
 */
public class MessageUtil {
    /**
     * xml转map集合
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,String> map=new HashMap<>();
        SAXReader reader=new SAXReader();

        InputStream in=request.getInputStream();
        Document doc=reader.read(in);
        Element root=doc.getRootElement();

        List<Element> list=root.elements();

        for(Element e:list) {
            map.put(e.getName(),e.getText());
        }

        in.close();
        return map;

    }

    /**
     * 将文本消息转换为xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml",TextMessage.class);
        return xStream.toXML(textMessage);
    }
}
