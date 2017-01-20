package com.yancey.manager.common;


/**
 * 缓存KEY常量管理类
 * 
 * @author john.xu
 * */
public class CacheKey {

  public static final String CACHE_KEY_WXACCESSTOKEN          = "CACHE_KEY_WXACCESSTOKEN   _";
  public static final String CACHE_KEY_WXTICKET          = "CACHE_KEY_WXTICKET   _";
  public static String getCacheKeyWxAccessToken() {
	    return CACHE_KEY_WXACCESSTOKEN;
  }

  public static String getCacheKeyWxTicket() {
	    return CACHE_KEY_WXTICKET;
  }
}
