package com.imooc.servlet;

import com.imooc.po.TextMessage;
import com.imooc.util.CheckUtil;
import com.imooc.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author: Lighters_c
 * @Discrpition:
 * @Date: Created in 1:22 2017/11/2
 * @Modified_by:
 */
public class WeixinServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out=response.getWriter();
        try {
            Map<String,String> map=MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message=null;
            if(MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                if("1".equals(content)) {
                    message=MessageUtil.initText(toUserName,fromUserName,MessageUtil.firstMenu());
                } else if("2".equals(content)) {
                    message=MessageUtil.initText(toUserName,fromUserName,MessageUtil.secondMenu());
                } else if("?".equals(content)||"？".equals(content)) {
                    message=MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }
            } else if(MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                String eventType=map.get("Event");
                if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    message=MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }
            }
            System.out.println(message);
            out.print(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String signature=request.getParameter("signature");
        String timestamp=request.getParameter("timestamp");
        String nonce=request.getParameter("nonce");
        String echostr=request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if(CheckUtil.checkSignature(signature,timestamp,nonce)) {
            out.print(echostr);
        }
    }
}
