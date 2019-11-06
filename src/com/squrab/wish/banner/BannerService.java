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
import com.squrab.wish.model.Services;
import com.squrab.wish.model.UserMember;

@RestService(name = "banner", moduleid = Services.MODULE_USER, comment = "【OSS系统】轮播图管理")
public class BannerService extends CrudBaseService<UserMember, Banner, BannerBean> {

	@Override
	protected boolean isUpdate(Banner bean) {
		// TODO Auto-generated method stub
		return bean.getBannerid() > 0;
	}
	
	@RestMapping(name = "create", auth = false, comment = "新增")
	@Override
	public RetResult<Integer> create(UserMember userMember, Banner bean) {
		// TODO Auto-generated method stub
		return super.create(userMember, bean);
	}
	
	@Override
	@RestMapping(name = "query", auth = false, comment = "查询列表")
	public RetResult<Sheet<Banner>> queryForPage(@Comment("当前用户") UserMember userMember, Flipper flipper, BannerBean bean) {
		return super.queryForPage(userMember, flipper, bean);
	}
	
	@Override
	@RestMapping(name = "delete", auth = false, comment = "删除")
	public RetResult<Integer> delete(@Comment("当前用户") UserMember userMember, int id) {
		return super.delete(userMember, id);
	}

	@Override
	@RestMapping(name = "queryById", auth = false, comment = "根据ID查询")
	public RetResult<Banner> queryById(@Comment("当前用户") UserMember userMember, int id) {
		// TODO Auto-generated method stub
		return super.queryById(userMember, id);
	}
}
