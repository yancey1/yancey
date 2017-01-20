package com.yancey.manager.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信回调处理
 * 
 * @author yw
 */
@Controller
@RequestMapping("/weixin/callback/")
public class WeixinCallBackController {

	private static final Logger logger = LoggerFactory.getLogger(WeixinCallBackController.class);

	/*
	 * @Autowired private IWeixinPayService weixinPayService;
	 */

	@RequestMapping(value = "payNotify", method = RequestMethod.POST)
	@ResponseBody
	public String getSignature(HttpServletRequest req, HttpServletResponse response, String openId,
			String money) {
		logger.info("微信回调成功！！！");
		try {
			Map<String, String> map = getCallbackParams(req);
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                String orderId = map.get("out_trade_no");
                //这里写成功后的业务逻辑
            }
		} catch (Exception e) {
			logger.error("微信回调失败：" + e.getMessage());
		}
        return getPayCallback();
	}
	
    public Map<String, String> getCallbackParams(HttpServletRequest request)
            throws Exception {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        return XmlUtil.parseXml(result);
    }
    
    public String getPayCallback(){
        PayCallback callback = new PayCallback();
        XmlUtil.xstream().alias("xml", callback.getClass());
        String xml = XmlUtil.xstream().toXML(callback);
        return xml;
    }
}
