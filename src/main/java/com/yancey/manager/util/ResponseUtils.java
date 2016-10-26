package com.yancey.manager.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * HttpServletResponse帮助类
 * 
 * @author liufang
 * 
 */
public final class ResponseUtils {
  public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

  /**
   * 发送文本。使用UTF-8编码。
   * 
   * @param response
   *          HttpServletResponse
   * @param text
   *          发送的字符串
   */
  public static void renderText(HttpServletResponse response, String text) {
    render(response, "text/plain;charset=UTF-8", text);
  }

  /**
   * 发送json。使用UTF-8编码。
   * 
   * @param response
   *          HttpServletResponse
   * @param text
   *          发送的字符串
   */
  public static void renderJson(HttpServletResponse response, String text) {
    render(response, "application/json;charset=UTF-8", text);
  }

  /**
   * 发送xml。使用UTF-8编码。
   * 
   * @param response
   *          HttpServletResponse
   * @param text
   *          发送的字符串
   */
  public static void renderXml(HttpServletResponse response, String text) {
    render(response, "text/xml;charset=UTF-8", text);
  }

  /**
   * 发送内容。使用UTF-8编码。
   * 
   * @param response
   * @param contentType
   * @param text
   */
  public static void render(HttpServletResponse response, String contentType, String text) {
    String str = !edu.hziee.common.lang.StringUtil.isBlank(text) ? text : "无";
    response.setContentType(contentType);
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    try {
      response.getWriter().write(str);
      response.getWriter().flush();
      response.getWriter().close();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  public static void returnJson(HttpServletResponse response, String msg, boolean flag) {
    JSONObject json = new JSONObject();
    json.put("success", flag);
    json.put("msg", msg);
    render(response, "text/plain;charset=UTF-8", json.toString());
  }
  /**
   * LIST对象转化为JSON字符串
   * 
   * @param list
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static String jsonFormat(List list) {
    JSONArray jsonArray = JSONArray.fromObject(list);
    return jsonArray.toString();
  }

  public static void responseInfoExists(HttpServletResponse response) {
    JSONObject resultJson = new JSONObject();
    resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.INFO_EXISTS.getCode());
    resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.INFO_EXISTS.getMsg());
    ResponseUtils.renderJson(response, resultJson.toString());
  }
  
  public static void responseInfoAppExists(HttpServletResponse response) {
	    JSONObject resultJson = new JSONObject();
	    resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.APPNAME_EXISTS.getCode());
	    resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.APPNAME_EXISTS.getMsg());
	    ResponseUtils.renderJson(response, resultJson.toString());
	  }

  public static void responseSuccess(HttpServletResponse response) {
    JSONObject resultJson = new JSONObject();
    resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.SUCCESS.getCode());
    resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.SUCCESS.getMsg());
    ResponseUtils.renderJson(response, resultJson.toString());
  }

  public static void responseFailure(HttpServletResponse response) {
    JSONObject resultJson = new JSONObject();
    resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
    resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.FAILURE.getMsg());
    ResponseUtils.renderJson(response, resultJson.toString());
  }
  public static void responseUploadType(HttpServletResponse response) {
    JSONObject resultJson = new JSONObject();
    resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.UPLOAD_TYPE.getCode());
    resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.UPLOAD_TYPE.getMsg());
    ResponseUtils.renderJson(response, resultJson.toString());
  }
  public static void responseBeenApplied(HttpServletResponse response) {
    JSONObject resultJson = new JSONObject();
    resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.BEEN_APPLIED.getCode());
    resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.BEEN_APPLIED.getMsg());
    ResponseUtils.renderJson(response, resultJson.toString());
  }
  public static void responseAuthority(HttpServletResponse response) {
    JSONObject resultJson = new JSONObject();
    resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.AUTHORITY.getCode());
    resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.AUTHORITY.getMsg());
    ResponseUtils.renderJson(response, resultJson.toString());
  }

  public static void responseApkOnline(HttpServletResponse response) {
    JSONObject resultJson = new JSONObject();
    resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.APK_ON_LINE.getCode());
    resultJson.put(ReturnJsonCode.RETURN_MSG, ReturnJsonCode.MsgCodeEnum.APK_ON_LINE.getMsg());
    ResponseUtils.renderJson(response, resultJson.toString());
  }

  public static void responseImageError(HttpServletResponse response, String msg) {
    JSONObject resultJson = new JSONObject();
    resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.IMAGEERROR.getCode());
    resultJson.put(ReturnJsonCode.RETURN_MSG, msg);
    ResponseUtils.renderJson(response, resultJson.toString());
  }
}
