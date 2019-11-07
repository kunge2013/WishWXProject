package com.squrab.wish.customer;

import java.util.List;

import org.redkale.net.http.RestMapping;
import org.redkale.net.http.RestService;
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;
import org.redkale.util.Comment;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.RecAddressBean;
import com.squrab.wish.constant.RecAddressDefault;
import com.squrab.wish.constant.RetCodes;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.RecAddress;
import com.squrab.wish.model.Services;
@RestService(name = "recaddress", moduleid = Services.MODULE_USER, comment = "【微信后台系统】收货地址管理")
public class RecAddressService extends CrudBaseService<Customer, RecAddress, RecAddressBean> {

	@Override
	@RestMapping(name = "create", auth = true, comment = "新增")
	public RetResult<Integer> create(Customer customer, RecAddress bean) {
		bean.setCustomerid(customer.getCustomerid());
		RecAddressBean addressBean = new RecAddressBean();
		addressBean.setCustomerid(customer.getCustomerid());
		List<RecAddress> list = source.queryList(RecAddress.class, addressBean);
		if (list == null || list.size() == 0) bean.setDefaultaddress(RecAddressDefault.DEFAULT.getCode());
		return super.create(customer, bean);
	}
	
	@Override
	protected int beforeSave(RecAddress bean) {
		// TODO Auto-generated method stub
		String recUserName = bean.getRecusername();
		String recAddress = bean.getRecaddress();
		String recPhone = bean.getRecaddress();
		if (recUserName == null || recUserName.isBlank()) return RetCodes.RET_RECUSERNAME_EXISTENCE_ILLEGAL;
		if (recAddress == null || recAddress.isBlank()) return RetCodes.RET_RECADDRESS_EXISTENCE_ILLEGAL;
		if (recPhone == null || recPhone.isBlank()) return RetCodes.RET_RECPHONE_EXISTENCE_ILLEGAL;
		// 校验 修改默认地址
		if (isUpdate(bean)) {
			// 判断
			int customerid = bean.getCustomerid();
			RecAddressBean addressBean = new RecAddressBean();
			addressBean.setCustomerid(customerid);
			List<RecAddress> list = source.queryList(RecAddress.class, addressBean);
			int defaultadd = bean.getDefaultaddress();	
			//将之前的所有地址改为非默认地址
			if (list != null &&
					list.size() > 0 && 
					defaultadd == RecAddressDefault.DEFAULT.getCode()) {
				list.forEach(obj -> {
					obj.setDefaultaddress(RecAddressDefault.NODEFAULT.getCode());// 将字段设置为不是默认地址
				});
				source.update(list);
			}
		}
		return super.beforeSave(bean);
	}
	@Override
	protected boolean isUpdate(RecAddress bean) {
		// TODO Auto-generated method stub
		boolean isUpdate =  bean.getRecaddressid() > 0;
		if (!isUpdate) bean.setCreatetime(System.currentTimeMillis()); // 创建时间为当前时间
		return isUpdate;
	}
	
    @Comment("分页查询")
	@RestMapping(name = "query", auth = false, comment = "查询列表")
	protected RetResult<Sheet<RecAddress>> queryForPage(Customer userMember, Flipper flipper, RecAddressBean bean) {
		// TODO Auto-generated method stub
		return super.queryForPage(userMember, flipper, bean);
	}
	
}
