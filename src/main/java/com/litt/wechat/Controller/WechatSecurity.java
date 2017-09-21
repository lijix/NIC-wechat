package com.litt.wechat.Controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.litt.wechat.Dispatcher.EventDispatcher;
import com.litt.wechat.Dispatcher.MsgDispatcher;
import com.litt.wechat.Util.MessageUtil;
import com.litt.wechat.Util.SignUtil;

@Controller
@RequestMapping("/wechat")
public class WechatSecurity{
	private static Logger logger = Logger.getLogger(WechatSecurity.class);
	public static String openid;

	
	/**
	 * 
	 * @Description: 用于接收get参数，返回验证参数
	 * @param @param request
	 * @param @param response
	 * @param @param signature
	 * @param @param timestamp
	 * @param @param nonce
	 * @param @param echostr
	 * @date 2016年3月4日 下午6:20:00
	 */
	@RequestMapping(value = "security", method = RequestMethod.GET)
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr) {
		try {
			logger.info("这里是DoGet方法");
			System.out.println("这里是DoGet方法");
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				PrintWriter out = response.getWriter();
				out.print(echostr);
				out.close();
			} else {
				logger.info("这里存在非法请求！");
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	
	@RequestMapping(value = "security", method = RequestMethod.POST)
    public void DoPost(HttpServletRequest request,HttpServletResponse response) {
    	
        try{
        	// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
            request.setCharacterEncoding("UTF-8");  
            response.setCharacterEncoding("UTF-8");  
            
            Map<String, String> map=MessageUtil.parseXml(request);
            openid=map.get("FromUserName");
            System.out.println("wechatde openid="+openid);
            String msgtype=map.get("MsgType");
            if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
            	String msg = EventDispatcher.processEvent(map); //进入事件处理
             // 响应消息  
                PrintWriter out = response.getWriter();  
                out.print(msg);  
                out.close(); 
            }else{
                String msg = MsgDispatcher.processMessage(map); //进入消息处理
                
                
                // 响应消息  
                   PrintWriter out = response.getWriter();  
                   out.print(msg);  
                   out.close(); 
            }
        }catch(Exception e){
            logger.error(e,e);
        }
     
    }


		
}
