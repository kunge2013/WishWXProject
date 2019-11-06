package com.squrab.wish.merchant;

import org.redkale.net.http.RestService;
import org.redkale.source.FilterNode;

import com.squrab.wish.bean.MerchantBean;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.model.Merchant;
import com.squrab.wish.model.Services;
import com.squrab.wish.model.Customer;

@RestService(name = "merchant", moduleid = Services.MODULE_USER, comment = "【微信系统】商户登录管理")
public class MerchantService extends CrudBaseService<Customer, Merchant, MerchantBean> {
	
//	@Override
//	protected boolean isUpdate(Merchant bean) {
//		// TODO Auto-generated method stub
//		return bean.getMerchantid() > 0;
//	}
//	
//	@Override
//	@RestMapping(name = "create", auth = false, comment = "新增")
//	public RetResult<Integer> create(Customer userMember, Merchant bean) {
//		// TODO Auto-generated method stub
//		return super.create(userMember, bean);
//	}
//	
//	@Override
//	@RestMapping(name = "queryById", auth = false, comment = "根据ID加载数据")
//	public RetResult<Merchant> queryById(Customer userMember, int id) {
//		// TODO Auto-generated method stub
//		return super.queryById(userMember, id);
//	}
//
//	@Override
//	@RestMapping(name = "query", auth = false, comment = "查询列表")
//	protected RetResult<Sheet<Merchant>> queryForPage(Customer userMember, Flipper flipper, MerchantBean bean) {
//		// TODO Auto-generated method stub
//		return super.queryForPage(userMember, flipper, bean);
//	}
//	
//	@Override
//	@RestMapping(name = "delete", auth = false, comment = "删除")
//	public RetResult<Integer> delete(Customer userMember, int id) {
//		// TODO Auto-generated method stub
//		return super.delete(userMember, id);
//	}
	
	/**
	 * 	当前用户信息
	 * @param openId
	 * @return
	 */
	public Merchant current(String merchanttel, String password) {
		Merchant merchant = null;
		if( merchanttel != null && !merchanttel.isEmpty() && password != null && !password.isEmpty()) {
			merchant = source.find(Merchant.class, FilterNode
						.create("merchanttel", merchanttel)
						.create("password", password));
		}
		return merchant;
	}
}
