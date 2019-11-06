package com.squrab.wish.third;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.redkale.convert.json.JsonConvert;
import org.redkale.service.Local;
import org.redkale.util.AutoLoad;
import org.redkale.util.Utility;

import com.squrab.wish.core.BaseService;

@AutoLoad
@Local
public class SmallProcessService extends BaseService {
	
	/**
	 * 微信认证url 路径
	 */
	private static final String WX_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
	
	@Resource
	public JsonConvert convert;
	
	@Resource(name = "weixin.sp.appid")
	private String appId = "wx8288e06d6753207c33";
	
	@Resource(name = "weixin.sp.appsecret")
	private String appSecret = "6f3e3db163f0627b9853d53c7fe88f3c";
	
	/**
	 * 	小程序认证
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 */
	 public  HashMap<String, Object> authWeChat(String code) {
	        String url = String.format(WX_AUTH_URL ,appId, appSecret, code);
	        try {
				return convert.convertFrom(JsonConvert.TYPE_MAP_STRING_STRING, Utility.getHttpContent(url));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("authChat error : " + e.getMessage());
			}
     } 
}