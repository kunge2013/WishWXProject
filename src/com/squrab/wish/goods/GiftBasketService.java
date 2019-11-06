package com.squrab.wish.goods;

import org.redkale.net.http.RestMapping;
import org.redkale.net.http.RestService;
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.GiftBasketBean;
import com.squrab.wish.constant.RetCodes;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.GiftBasket;
import com.squrab.wish.model.Services;

@RestService(name = "giftBasket", moduleid = Services.MODULE_GOODS, comment = "【OSS系统】商品管理模块")
public class GiftBasketService extends CrudBaseService<Customer, GiftBasket, GiftBasketBean> {
	
	@Override
	@RestMapping(name = "create", auth = false, comment = "新增")
	public RetResult<Integer> create(Customer customer, GiftBasket bean) {
		int customerId = 0, goodsId = 0, rows = 0;
		if (customer == null || bean == null ||
					(customerId = customer.getCustomerid()) == 0
					|| (goodsId = bean.getGoodsid()) == 0) RetCodes.retInfo(RetCodes.RET_PARAMS_ILLEGAL);//参数无效
			
		GiftBasketBean gciftbasketBean = new GiftBasketBean();
		gciftbasketBean.setGoodsid(goodsId);
		gciftbasketBean.setCustomerid(customerId);
		GiftBasket giftbasket = source.find(GiftBasket.class, gciftbasketBean);
		if (giftbasket == null) {
			giftbasket = new GiftBasket();
			giftbasket.setCreatetime(System.currentTimeMillis());
			giftbasket.setCurrentprice(bean.getCurrentprice());
			giftbasket.setCustomerid(customerId);
			giftbasket.setGoodsid(goodsId);
			giftbasket.setPurchasenum(bean.getPurchasenum());
			if(!((rows = source.insert(giftbasket)) > 0)) RetCodes.retInfo(RetCodes.RET_GIFTBASKET_SAVE_ILLEGAL);
		} else {
			giftbasket.increPurchasenum(bean.getPurchasenum());// 增加、减少数量
			giftbasket.setModifytime(System.currentTimeMillis());
			// if(!((rows = source.update(giftbasket)) > 0)) RetCodes.retInfo(RetCodes.RET_GIFTBASKET_UPDATE_ILLEGAL);
			rows = source.update(giftbasket);
		}
		return RetResult.success(rows);
	}
	
	@RestMapping(name = "query", auth = false, comment = "查询礼物篮")
	protected RetResult<Sheet<GiftBasket>> queryForPage(Customer userMember, Flipper flipper, GiftBasketBean bean) {
		//		bean.setCustomerid(userMember.getCustomerid());
		return super.queryForPage(userMember, flipper, bean);
	}
	
	
	@RestMapping(name = "delete", auth = false, comment = "清除礼物篮")
	public RetResult<Integer> delete(Customer customer, int id) {
		GiftBasket basket = source.find(GiftBasket.class, id);
		int rows = 0;
		if (basket != null) rows = source.delete(basket);
		return RetResult.success(rows);
	}
	
}
