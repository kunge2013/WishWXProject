package com.squrab.wish.customer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.redkale.convert.json.JsonConvert;
import org.redkale.net.http.HttpContext;
import org.redkale.net.http.HttpMapping;
import org.redkale.net.http.HttpRequest;
import org.redkale.net.http.HttpResponse;
import org.redkale.net.http.HttpServlet;
import org.redkale.net.http.HttpUserType;
import org.redkale.net.http.WebServlet;
import org.redkale.service.RetResult;
import org.redkale.util.AnyValue;

import com.squrab.wish.constant.RetCodes;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.RandomCode;
import com.squrab.wish.model.UserInfo;

@HttpUserType(Customer.class)
@WebServlet(value = {"/customer/*"}, repair = false, comment = "商品访问服务")
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
	private CustomerService service;

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
	
    /**
     * 手机号注册绑定
     * @param req
     * @param resp
     * @throws IOException
     */
    @HttpMapping(auth = true, url = "/smsregcode")
    public void smsreg(HttpRequest req, HttpResponse resp) throws IOException {
        smsvercode(RandomCode.TYPE_SMSREG, req, resp);
    }
    
    private void smsvercode(final short type, HttpRequest req, HttpResponse resp) throws IOException {
        String mobile = req.getRequstURIPath("mobile:", req.getParameter("mobile"));
        if (type == RandomCode.TYPE_SMSODM) { //给原手机号码发送验证短信
            UserInfo user = req.currentUser();
            if (user != null) mobile = user.getMobile();
        }
        RetResult rr = service.smscode(type, mobile);
        if (finest) logger.finest(req.getRequestURI() + ", mobile = " + mobile + "---->" + rr);
        resp.finishJson(rr);
    }
}
