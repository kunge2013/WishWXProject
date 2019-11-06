package com.squrab.wish.sys;

import org.redkale.net.http.RestMapping;
import org.redkale.net.http.RestService;
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.RegionBean;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.model.Region;
import com.squrab.wish.model.Services;
import com.squrab.wish.model.UserMember;

@RestService(name = "regionInfo", moduleid = Services.MODULE_GOODS, comment = "【OSS系统】區域管理模块")
public class RegionService extends CrudBaseService<UserMember, Region, RegionBean> {
	
	@Override
	protected boolean isUpdate(Region bean) {
		// TODO Auto-generated method stub
		return bean.getRegionid() > 0;
	}
	
	@Override
	@RestMapping(name = "queryById", auth = false, comment = "根据ID加载数据")
	public RetResult<Region> queryById(UserMember userMember, int id) {
		// TODO Auto-generated method stub
		return super.queryById(userMember, id);
	}
	
	@Override
	@RestMapping(name = "query", auth = false, comment = "查询列表")
	protected RetResult<Sheet<Region>> queryForPage(UserMember userMember, Flipper flipper, RegionBean bean) {
		// TODO Auto-generated method stub
		return super.queryForPage(userMember, flipper, bean);
	}
	
	@Override
	@RestMapping(name = "delete", auth = false, comment = "删除")
	public RetResult<Integer> delete(UserMember userMember, int id) {
		// TODO Auto-generated method stub
		return super.delete(userMember, id);
	}

	@Override
	@RestMapping(name = "create", auth = false, comment = "新增")
	public RetResult<Integer> create(UserMember userMember, Region bean) {
		// TODO Auto-generated method stub
		return super.create(userMember, bean);
	}
}
