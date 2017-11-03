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
import java.time.LocalDateTime;
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

    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_IMAGE="image";
    public static final String MESSAGE_VOICE="video";
    public static final String MESSAGE_LINK="link";
    public static final String MESSAGE_LOCATION="location";
    public static final String MESSAGE_EVENT="event";
    public static final String MESSAGE_SUBSCRIBE="subscribe";
    public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
    public static final String MESSAGE_CLICK="CLICK";
    public static final String MESSAGE_VIEW="VIEW";










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



    public static String initText(String toUserName,String fromUserName,String content) {
        TextMessage text=new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MESSAGE_TEXT);
        text.setCreateTime(LocalDateTime.now().toString());
        text.setContent(content);
        return textMessageToXml(text);
    }





    /**
     * 主菜单
     * @return
     */
    public static String menuText() {
        StringBuffer sb=new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
        sb.append("1、课程介绍\n");
        sb.append("2、慕课网介绍\n\n");
        sb.append("回复?调出此菜单");
        return sb.toString();
    }

    public static String firstMenu() {
        StringBuffer sb=new StringBuffer();
        sb.append("本套课程介绍微信公众号开发，主要涉及公众号介绍、编辑模式介绍、开发模式介绍等");
        return sb.toString();
    }


    public static String secondMenu() {
        StringBuffer sb=new StringBuffer();
        sb.append("哈哈哈，懒得打字了…");
        return sb.toString();
    }



}
