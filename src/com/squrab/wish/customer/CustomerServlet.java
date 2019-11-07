package com.squrab.wish.customer;

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
	private CustomerService service;
	
	@Resource
	private LabelService labelService;

	@Resource
	private RecAddressService recAddressService;
	
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
    
    @RestMapping(name = "fetchOpenid", auth = true, comment = "获取微信openId")
	public RetResult<Map<String, Object>> fetchOpenid(String code) {
		return service.fetchOpenid(code);
	}
    
    @Comment("绑定电话号码")
	@RestMapping(name = "bindMobile", auth = true, comment = "绑定电话号码")
	public RetResult<Customer> bindMobile(CustomerBean bean) {
		return service.bindMobile(bean);
	}
   
    @Comment("添加标签到用户表字段以分号分割")
  	@RestMapping(name = "addLabels", auth = true, comment = "添加标签到用户表字段以分号分割")
  	public RetResult<Integer> addLabels(Customer bean) {
  		return service.create(bean);
  	}
    
    /**********************************用户收货地址************************************************/
    @Comment("用户收获地址绑定")
  	@RestMapping(name = "bindRecAddress", auth = true, comment = "用户收获地址绑定")
    public RetResult<Integer> bindRecAddress(Customer customer, RecAddress bean) {
		return recAddressService.create(customer, bean);
	}
    
    @Comment("分页查询")
   	@RestMapping(name = "queryRecAddress", auth = false, comment = "查询收货地址列表")
   	protected RetResult<Sheet<RecAddress>> queryRecAddress(Flipper flipper, RecAddressBean bean) {
   		// TODO Auto-generated method stub
   		return recAddressService.queryForPage(flipper, bean);
   	}
    public static void main(String[] args) throws Exception {
		Application.main(null);
	}
}
