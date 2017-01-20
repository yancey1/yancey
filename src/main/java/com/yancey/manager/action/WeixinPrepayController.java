package com.yancey.manager.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yancey.manager.util.DateUtil;
import com.yancey.manager.util.NetWorkHelper;

/**
 * 微信预支付处理
 * 
 * @author yw
 */
@Controller
@RequestMapping("/weixin/prepay/")
public class WeixinPrepayController {

	private static final Logger logger = LoggerFactory.getLogger(WeixinPrepayController.class);

	private static final String APP_ID = "wx06c5ab0514dd2718";
	
	private static final String MER_ID = "1262944501";
	
	private static final String UNIFIED_ORDER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	private static final String CALL_BACK_URL="http://114.55.238.89:8088/joseph-cmp/weixin/callback/payNotify";   //微信回调地址
	
	private static final String SIGN_TYPE="MD5";
	
	private static final String MER_KEY="kenuocheshenghuoKeNuo20170101520";
	
	@RequestMapping(value = "getSignature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSignature(HttpServletRequest req, HttpServletResponse response, String openId,String money) {
		//解决跨域
		req.getHeader("x-forwarded-for"); 
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies.
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("openId="+openId+"---------money="+money+"--------id="+req.getRemoteAddr());
		try {
			String time = createTimestamp();
			String nonceStr = createNonceStr();
			String prepayId=unifiedOrder(nonceStr,openId, createTradeNo(), money,req.getRemoteAddr());
			if(StringUtils.isBlank(prepayId)){
				map.put("error","获取预支付ID异常");
				logger.info("获取预支付ID异常");
				return map;
			}
			String packageName = "prepay_id="+prepayId;
			map.put("nonceStr", nonceStr);
			map.put("timestamp", time);
			map.put("appId", APP_ID);
			map.put("signType", SIGN_TYPE);
			map.put("package", packageName);
			map.put("paySign", getPaySign(nonceStr, packageName,time));
		} catch (Exception e) {
			logger.error("微信权限验证异常：" + e.getMessage());
		}
		return map;
	}
	
	 /**
     * 统一下单
     * @Title: unifiedOrder 
     * @Description: TODO 
     * @param: @param openId 微信用户openId
     * @param: @param orderId 订单ID
     * @param: @param money 订单总价，单位：分
     * @param: @param callbackUrl 回调路径
     * @param: @return
     * @param: @throws Exception
     * @return: String
     */
    public String unifiedOrder(String nonceStr,String openId,String orderId,String money,String ip) throws Exception{
        UnifiedOrder unifiedOrder = new UnifiedOrder();
        unifiedOrder.setAppid(APP_ID);
        unifiedOrder.setBody("科诺微信支付");
        unifiedOrder.setMch_id(MER_ID);
        unifiedOrder.setNonce_str(nonceStr);
        unifiedOrder.setNotify_url(CALL_BACK_URL);
        unifiedOrder.setOpenid(openId);
        unifiedOrder.setOut_trade_no(orderId);
        unifiedOrder.setSpbill_create_ip(ip);
        unifiedOrder.setTotal_fee((int)(Float.parseFloat(money)*100));
        unifiedOrder.setTrade_type("JSAPI");
        String sign = createUnifiedOrderSign(unifiedOrder);
        unifiedOrder.setSign(sign);

        XmlUtil.getXstreamInclueUnderline().alias("xml", unifiedOrder.getClass());
        String xml = XmlUtil.getXstreamInclueUnderline().toXML(unifiedOrder);
        NetWorkHelper netHelper = new NetWorkHelper();
        String result = netHelper.getHttpsPost(UNIFIED_ORDER_URL, xml);
        Map<String, String> responseMap = XmlUtil.parseXml(result);
        return responseMap.get("prepay_id");
    }

    
    /**
     * 获取统一下单签名
     * @Title: createUnifiedOrderSign
     */
    public String createUnifiedOrderSign(UnifiedOrder unifiedOrder){
    	String[] paramArr = new String[] { "appid=" + unifiedOrder.getAppid(), "body=" + unifiedOrder.getBody(),
				"mch_id=" + unifiedOrder.getMch_id(), "nonce_str=" + unifiedOrder.getNonce_str()
				,"notify_url="+unifiedOrder.getNotify_url(),"openid="+unifiedOrder.getOpenid()
				,"out_trade_no="+unifiedOrder.getOut_trade_no(),"spbill_create_ip="+unifiedOrder.getSpbill_create_ip()
				,"total_fee="+unifiedOrder.getTotal_fee(),"trade_type="+unifiedOrder.getTrade_type()};
		Arrays.sort(paramArr);
		String content ="";
		for (String string : paramArr) {
			content+=string+"&";
		}
		content=content+"key="+MER_KEY;
        return DigestUtils.md5Hex(content.toString()).toUpperCase();
    }
    
    public String getPaySign(String nonceStr,String packageName,String time){
    	String[] paramArr = new String[] { "appId=" + APP_ID, "nonceStr=" + nonceStr,
				"package=" + packageName, "signType=" + SIGN_TYPE,"timeStamp="+time };
		Arrays.sort(paramArr);
		String content ="";
		for (String string : paramArr) {
			content+=string+"&";
		}
		content=content+"key="+MER_KEY;
		//content=content.substring(0,content.length()-1);
        String signature = DigestUtils.md5Hex(content.toString()).toUpperCase();
        logger.info("拼接字符串："+content);
        logger.info("签名为："+signature);
        return signature;
    }
    
    private static String createNonceStr() {
		return UUID.randomUUID().toString().substring(0, 10);
	}

	private static String createTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	private static String createTradeNo() {
		return DateUtil.getCurrentDateStr()+buildRandom(6);
	}
	
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	
	public static void main(String[] args) {
		String s="appid=wx06c5ab0514dd2718&body=科诺微信支付&mch_id=1262944501&nonce_str=f96f7c78-b&notify_url=http://114.55.238.89:8088/weixin/callback/payNotify&openid=12222222222222&out_trade_no=20170111208709&spbill_create_ip=0:0:0:0:0:0:0:1&total_fee=100&trade_type=JSAPI";
		System.out.println(DigestUtils.md5Hex(s.toString()).toUpperCase());
	}
}
