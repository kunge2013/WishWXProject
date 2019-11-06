package com.squrab.wish.banner;

import org.redkale.net.http.RestMapping;
import org.redkale.net.http.RestService;
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;
import org.redkale.util.Comment;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.BannerBean;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.model.Banner;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.Services;

@RestService(name = "banner", moduleid = Services.MODULE_USER, comment = "【OSS系统】轮播图管理")
public class BannerService extends CrudBaseService<Customer, Banner, BannerBean> {

	@Override
	protected boolean isUpdate(Banner bean) {
		// TODO Auto-generated method stub
		return bean.getBannerid() > 0;
	}
	
	@RestMapping(name = "create", auth = false, comment = "新增")
	@Override
	public RetResult<Integer> create(Customer member, Banner bean) {
		// TODO Auto-generated method stub
		return super.create(member, bean);
	}
	
	@Override
	@RestMapping(name = "query", auth = false, comment = "查询列表")
	public RetResult<Sheet<Banner>> queryForPage(@Comment("当前用") Customer member, Flipper flipper, BannerBean bean) {
		return super.queryForPage(member, flipper, bean);
	}
	
	@Override
	@RestMapping(name = "delete", auth = false, comment = "删除")
	public RetResult<Integer> delete(@Comment("当前用户") Customer member, int id) {
		return super.delete(member, id);
	}

	@Override
	@RestMapping(name = "queryById", auth = false, comment = "根据ID查询")
	public RetResult<Banner> queryById(@Comment("当前用户") Customer member, int id) {
		// TODO Auto-generated method stub
		return super.queryById(member, id);
	}
}
