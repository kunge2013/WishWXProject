package com.squrab.wish.customer;

import static com.squrab.wish.constant.RetCodes.RET_USER_MOBILE_ILLEGAL;
import static com.squrab.wish.constant.RetCodes.RET_USER_MOBILE_SMSFREQUENT;
import static com.squrab.wish.constant.RetCodes.retResult;
import static org.redkale.source.FilterExpress.EQUAL;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.annotation.Resource;

import org.redkale.net.http.RestMapping;
import org.redkale.net.http.RestService;
import org.redkale.service.RetResult;
import org.redkale.source.FilterExpress;
import org.redkale.source.FilterNode;
import org.redkale.source.Flipper;
import org.redkale.util.Comment;
import org.redkale.util.Sheet;
import org.redkalex.weixin.WeiXinMPService;

import com.squrab.wish.bean.CustomerBean;
import com.squrab.wish.bean.CustomerLoginBean;
import com.squrab.wish.constant.RetCodes;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.core.RandomService;
import com.squrab.wish.core.SmsService;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.RandomCode;
import com.squrab.wish.model.Services;
import com.squrab.wish.third.SmallProcessService;

/**
 *
 * @author 86176
 */
@RestService(name = "customer", moduleid = Services.MODULE_USER, comment = "【OSS系统】轮播图管理")
public class CustomerService extends CrudBaseService<Customer, Customer, CustomerBean> {

	@Resource
	SmallProcessService wxservice;

	@Resource
	private WeiXinMPService weiXinMPService;

	@Resource
	private RandomService randomCodeService;

	@Resource
	private SmsService smsService;

	@Override
	protected boolean isUpdate(Customer bean) {
		return bean.getCustomerid() > 0;
	}

	@Override
	@RestMapping(name = "create", auth = false, comment = "新增")
	public RetResult<Integer> create(Customer customer, Customer bean) {
		// TODO Auto-generated method stub
		return super.create(customer, bean);
	}

	@Override
	@RestMapping(name = "queryById", auth = false, comment = "根据ID加载数据")
	public RetResult<Customer> queryById(Customer customer, int id) {
		// TODO Auto-generated method stub
		return super.queryById(customer, id);
	}

	@Override
	@RestMapping(name = "query", auth = true, comment = "查询列表")
	protected RetResult<Sheet<Customer>> queryForPage(Customer customer, Flipper flipper, CustomerBean bean) {
		// TODO Auto-generated method stub
		return super.queryForPage(customer, flipper, bean);
	}

	@Override
	@RestMapping(name = "delete", auth = false, comment = "删除")
	public RetResult<Integer> delete(Customer customer, int id) {
		// TODO Auto-generated method stub
		return super.delete(customer, id);
	}

	@RestMapping(name = "findCustomerByopenid", auth = false, comment = "根据openid查询用户信息")
	public RetResult<Customer> findCustomerByopenid(String wxopenid) {
		return RetResult
				.success(source.find(Customer.class, FilterNode.create("wxopenid", FilterExpress.EQUAL, wxopenid)));
	}

	@Comment("根据手机号码查找用户")
	@RestMapping(name = "findCustomerByMobile", auth = true, comment = "根据手机号码查找用户")
	public RetResult<Customer> findCustomerByMobile(Customer customer, String mobile) {
		RetResult<Customer> result = new RetResult<Customer>();
		if (mobile == null || mobile.isEmpty()) {
			result.setRetcode(RetCodes.RET_CUSTOMERINFO_MOBILE_IS_NULL_ILLEGAL);
			return result;
		}
		return RetResult.success(source.find(Customer.class, FilterNode.create("customertel", EQUAL, mobile)));
	}

	/**
	 * 授权
	 * 
	 * @param code
	 * @return
	 */
	@RestMapping(name = "fetchOpenid", auth = false, comment = "获取微信openId")
	public RetResult<Map<String, Object>> fetchOpenid(String code) {
		Map<String, Object> map = wxservice.authWeChat(code);
		RetResult<Map<String, Object>> result = new RetResult();
		result.setResult(map);
		if (map.containsKey("openid")) {
			Customer entity = new Customer();
			String openId = String.valueOf(map.get("openid"));
			Customer customer = source.find(Customer.class, FilterNode.create("wxopenid", openId));// 如果当前账户之前没有注册那么就进行注册
			if (customer == null) {
				String sessionKey = String.valueOf(map.get("session_key"));
				entity.setWxopenid(openId);
				entity.setWxsessionkey(sessionKey);
				entity.setCreatetime(System.currentTimeMillis());
				source.insert(entity);
			}
			return RetResult.success(map);
		}
		result.setRetcode(RetCodes.RET_CUSTOMERINFO_FETCHOPENID_ILLEGAL);
		return result;
	}

	/**
	 * 当前用户信息
	 * @param openId
	 * @return
	 */
	public Customer current(String openId) {
		Customer customer = null;
		if (openId != null && !openId.isEmpty()) {
			customer = source.find(Customer.class, FilterNode.create("wxopenid", FilterExpress.EQUAL, openId));
		}
		return customer;
	}


	@Comment("绑定电话号码")
	@RestMapping(name = "bindMobile", auth = true, comment = "绑定电话号码")
	public RetResult<Customer> bindMobile(CustomerBean bean) {
		RetResult<Customer> result = new RetResult();
		Customer customer = null;
		String openid = bean.getWxopenid();
		String tel = null;// 小程序注册编码
		if (bean == null || (tel = bean.getCustomertel()) == null || (bean.getWxopenid()) == null
				|| bean.getWxopenid().isEmpty()) {
			result.setRetcode(RetCodes.RET_USER_SIGNUP_ILLEGAL);
			return result;
		}
		RetResult<Customer> r = findCustomerByopenid(openid);// 如果已经绑定了
		if (r.isSuccess() && (customer = r.getResult()) != null) {
			result.setRetcode(RetCodes.RET_CUSTOMER_REGISTERED_ILLEGAL);
			return result;
		}
		// 查询验证码
		RandomCode randomCode = randomCodeService.findRandomCode(tel);
		if (randomCode == null) {
			result.setRetcode(RetCodes.RET_CUSTOMER_VERIFYCODE_ILLEGAL);
			return result;
		}
		customer.setCustomertel(tel);
		source.updateColumn(customer, "customertel");
		result.setResult(customer);
		return result;
	}

	@Comment("发送短信验证码")
	public RetResult smscode(final short type, String mobile) {
		if (mobile == null)
			return new RetResult(RET_USER_MOBILE_ILLEGAL, type + " mobile is null"); // 手机号码无效
		if (mobile.indexOf('+') == 0)
			mobile = mobile.substring(1);
		List<RandomCode> codes = randomCodeService.queryRandomCodeByMobile(mobile);
		if (!codes.isEmpty()) {
			RandomCode last = codes.get(codes.size() - 1);
			if (last.getCreatetime() + 60 * 1000 > System.currentTimeMillis())
				return RetCodes.retResult(RET_USER_MOBILE_SMSFREQUENT);
		}
		final int smscode = RandomCode.randomSmsCode();
		try {
			if (!smsService.sendRandomSmsCode(type, mobile, smscode))
				return retResult(RET_USER_MOBILE_SMSFREQUENT);
		} catch (Exception e) {
			logger.log(Level.WARNING, "mobile(" + mobile + ", type=" + type + ") send smscode " + smscode + " error",
					e);
			return retResult(RET_USER_MOBILE_SMSFREQUENT);
		}
		RandomCode code = new RandomCode();
		code.setCreatetime(System.currentTimeMillis());
		code.setRandomcode(mobile + "-" + smscode);
		code.setType(type);
		randomCodeService.createRandomCode(code);
		return RetResult.success();
	}
}
