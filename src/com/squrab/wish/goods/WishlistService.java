package com.squrab.wish.goods;

import org.redkale.net.http.RestMapping;
import org.redkale.net.http.RestService;
import org.redkale.service.RetResult;
import org.redkale.source.FilterNode;
import org.redkale.source.Flipper;
import org.redkale.util.Comment;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.WishlistBean;
import com.squrab.wish.constant.RetCodes;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.Services;
import com.squrab.wish.model.Wishlist;

@RestService(name = "wishlist", moduleid = Services.MODULE_USER, comment = "【OSS系统】心愿清单管理")
public class WishlistService extends CrudBaseService<Customer, Wishlist, WishlistBean> {

	@RestMapping(name = "create", auth = false, comment = "新增")
	public RetResult<Integer> create(Customer customer, Wishlist bean) {
		bean.setCustomerid(customer.getCustomerid());
		if (!(bean.getGoodsid() > 0)) bean.setCreatetime(System.currentTimeMillis());
		return RetCodes.retResult(RetCodes.RET_WISHLIST_EXISTENCE_ILLEGAL); // 心愿清单已存在
	}
	
	/**
	 * 	統一校驗
	 * @param bean
	 * @return
	 */
	protected int beforeSave(Wishlist bean) {
		Wishlist result = source.find(Wishlist.class, 
				FilterNode.create("customerid", bean.getCustomerid())
						  .create("goodsid", bean.getGoodsid()));
		// 校验是否重复添加
		if (result != null) return RetCodes.RET_WISHLIST_EXISTENCE_ILLEGAL;
		return RetCodes.RET_SUCCESS;
	}
	
	@Override
	@Comment("分页查询")
	@RestMapping(name = "query", auth = false, comment = "查询列表")
	protected RetResult<Sheet<Wishlist>> queryForPage(Customer customer, Flipper flipper, WishlistBean bean) {
		// TODO Auto-generated method stub
		return super.queryForPage(customer, flipper, bean);
	}
	
	@Override
	@RestMapping(name = "queryById", auth = false, comment = "根据ID加载数据")
	public RetResult<Wishlist> queryById(Customer customer, int id) {
		// TODO Auto-generated method stub
		return super.queryById(customer, id);
	}
	
	@Override
	@RestMapping(name = "delete", auth = false, comment = "删除")
	public RetResult<Integer> delete(Customer customer, int id) {
		// TODO Auto-generated method stub
		return super.delete(customer, id);
	}
	/**
	 *	判断当前对象是保存还是更新
	 * @param bean
	 * @return
	 */
	protected boolean isUpdate(Wishlist bean) {
		return bean.getWishlistid() > 0;
	}
}
