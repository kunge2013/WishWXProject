package com.squrab.wish;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.redkale.boot.Application;
import org.redkale.convert.json.JsonConvert;
import org.redkale.net.http.HttpContext;
import org.redkale.net.http.HttpMapping;
import org.redkale.net.http.HttpRequest;
import org.redkale.net.http.HttpResponse;
import org.redkale.net.http.HttpServlet;
import org.redkale.net.http.HttpUserType;
import org.redkale.net.http.RestMapping;
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;
import org.redkale.util.AnyValue;
import org.redkale.util.Comment;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.CustomerBean;
import com.squrab.wish.bean.RecAddressBean;
import com.squrab.wish.constant.RetCodes;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.RandomCode;
import com.squrab.wish.model.RecAddress;
import com.squrab.wish.model.UserInfo;
import com.squrab.wish.service.CustomerService;
import com.squrab.wish.service.LabelService;
import com.squrab.wish.service.RecAddressService;

@HttpUserType(Customer.class)
public class CustomerServlet extends HttpServlet {
	
	public static final String COOKIE_AUTOLOGIN = "UNF";
	 
	protected static final boolean winos = System.getProperty("os.name").contains("Window");

	protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	protected final boolean fine = logger.isLoggable(Level.FINE);

	protected final boolean finer = logger.isLoggable(Level.FINER);

	protected final boolean finest = logger.isLoggable(Level.FINEST);

	protected static final RetResult RET_UNLOGIN = RetCodes.retResult(RetCodes.RET_USER_UNLOGIN);

	protected static final RetResult RET_AUTHILLEGAL = RetCodes.retResult(RetCodes.RET_USER_AUTH_ILLEGAL);

	@Resource
	protected JsonConvert convert;

	@Resource
	protected CustomerService service;
	
	
	@Override
	public void init(HttpContext context, AnyValue config) {
		super.init(context, config);
	}

	@Override
	public void preExecute(final HttpRequest request, final HttpResponse response) throws IOException {
		if (finer)
			response.recycleListener((req, resp) -> { // 记录处理时间比较长的请求
				long e = System.currentTimeMillis() - ((HttpRequest) req).getCreatetime();
				if (e > 200) logger.finer("http-execute-cost-time: " + e + " ms. request = " + req);
			});
		String openId = request.getHeader("openId");
		if (null != openId && !openId.isEmpty()) 
		request.setCurrentUser(service.current(openId));
		response.nextEvent();
	}

	/**
	 * 	查看当前校验是否成功！！！
	 */
	protected void authenticate(HttpRequest request, HttpResponse response) throws IOException {
		Customer info = request.currentUser();
        if (info == null) {
            response.finishJson(RET_UNLOGIN);
            return;
        } 
		response.nextEvent();
	}
	

    
}
