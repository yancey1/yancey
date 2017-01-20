package com.yancey.manager.action;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yancey.manager.common.CacheKey;
import com.yancey.manager.redis.RedisClient;
import com.yancey.manager.util.NetWorkHelper;

import net.sf.json.JSONObject;

/**
 * 微信JS授权验证
 * 
 * @author yw
 */
@Controller
@RequestMapping("/weixin/validate/")
public class WeixinPermissionVaildate {

	private static final Logger logger = LoggerFactory.getLogger(WeixinPermissionVaildate.class);

	@Autowired
	private RedisClient<String, Object> redisClient;

	private static final String APP_ID = "wx06c5ab0514dd2718";

	private static final String APP_SECRET = "aa8f1951574cd840252bc28982321221";

	 @RequestMapping(value = "/list", method = RequestMethod.GET)
	  public String list(Model model) {
	    return "user/weixin";
	  }
	
	@RequestMapping(value = "getSignature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSignature(HttpServletRequest req, HttpServletResponse response, String jsUrl) {
		//解决跨域
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies.
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String accessToken = getAccessToken();
			String ticket = geticket(accessToken);
			String time = createTimestamp();
			String nonceStr = createNonceStr();
			String sign = getSignature(ticket, time, nonceStr, jsUrl);
			map.put("signature", sign);
			map.put("nonceStr", nonceStr);
			map.put("timestamp", time);
			map.put("appId", APP_ID);
		} catch (Exception e) {
			logger.error("微信权限验证异常：" + e.getMessage());
		}
		return map;
	}

	public String getAccessToken() {
		String accessToken = "";
		NetWorkHelper netHelper = new NetWorkHelper();
		Object obj = redisClient.get(CacheKey.getCacheKeyWxAccessToken());
		if (obj == null) {
			String url = String.format(
					"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", APP_ID,
					APP_SECRET);
			String result = netHelper.getHttpsResponse(url, "GET");
			JSONObject jsonObj = JSONObject.fromObject(result);
			accessToken = jsonObj.getString("access_token");
			redisClient.put(CacheKey.getCacheKeyWxAccessToken(), accessToken, 7100); // 因为accessToken有效期为2个小时，设置为7100秒
		} else {
			accessToken = (String) obj;
		}
		return accessToken;
	}

	public String geticket(String accessToken) {
		String ticket = "";
		NetWorkHelper netHelper = new NetWorkHelper();
		Object obj = redisClient.get(CacheKey.getCacheKeyWxTicket());
		if (obj == null) {
			String url = String.format(
					"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi");
			String result = netHelper.getHttpsResponse(url, "GET");
			JSONObject jsonObj = JSONObject.fromObject(result);
			ticket = jsonObj.getString("ticket");
			redisClient.put(CacheKey.getCacheKeyWxTicket(), ticket, 6900); // 防止获取ticket时，accessToken已过期，设置为6900秒
		} else {
			ticket = (String) obj;
		}
		return ticket;
	}

	public static String getSignature(String jsapi_ticket, String timestamp, String nonce, String jsurl)
			throws IOException {
		/****
		 * 对 jsapi_ticket、 timestamp 和 nonce 按字典排序 对所有待签名参数按照字段名的 ASCII
		 * 码从小到大排序（字典序）后，使用 URL 键值对的格式（即key1=value1&key2=value2…）拼接成字符串
		 * string1。这里需要注意的是所有参数名均为小写字符。 接下来对 string1 作 sha1 加密，字段名和字段值都采用原始值，不进行
		 * URL 转义。即 signature=sha1(string1)。
		 * **如果没有按照生成的key1=value&key2=value拼接的话会报错
		 */
		String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket, "timestamp=" + timestamp,
				"noncestr=" + nonce, "url=" + jsurl };
		Arrays.sort(paramArr);
		String content = paramArr[0].concat("&" + paramArr[1]).concat("&" + paramArr[2]).concat("&" + paramArr[3]);
		String gensignature = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			gensignature = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (gensignature != null) {
			return gensignature;
		} else {
			return "false";
		}
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 *
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	private static String createNonceStr() {
		return UUID.randomUUID().toString();
	}

	private static String createTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	
	public static void main(String[] args) {
		System.out.println(Long.toString(System.currentTimeMillis() / 1000));
		System.out.println(UUID.randomUUID().toString());
	}
}
