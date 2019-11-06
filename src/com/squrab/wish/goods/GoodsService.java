/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squrab.wish.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.redkale.boot.Application;
import org.redkale.convert.json.JsonConvert;
import org.redkale.net.http.RestMapping;
import org.redkale.net.http.RestService;
import org.redkale.service.RetResult;
import org.redkale.source.FilterNode;
import org.redkale.source.Flipper;
import org.redkale.util.Comment;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.GoodsBean;
import com.squrab.wish.constant.RetCodes;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.Goods;
import com.squrab.wish.model.Services;
import com.squrab.wish.model.Wishlist;

/**
 * 	商品管理服务类
 * @author 86176
 */

@RestService(name = "goodsInfo", moduleid = Services.MODULE_GOODS, comment = "【OSS系统】商品管理模块")
public class GoodsService extends CrudBaseService<Customer, Goods, GoodsBean> {

	@Comment("序列化工具")
	@Resource
	protected JsonConvert convert;

	@Comment("心愿清单服务")
	@Resource
	private WishlistService wishlistService;
	
	@Override
	@RestMapping(name = "create", auth = false, comment = "新增")
	public RetResult<Integer> create(Customer customer, Goods bean) {
		// TODO Auto-generated method stub
		return super.create(customer, bean);
	}
	
	@Override
	protected int beforeSave(Goods bean) {
		// TODO Auto-generated method stub
		if (bean.getGoodsid() > 0) {
			return super.beforeSave(bean);
		}
		List<Goods> results = source
					.queryColumnList("goodsname", Goods.class, "goodsname", bean.getGoodsname());
		if (results != null && results.size() > 0) return RetCodes.RET_GOODSINFO_NAME_REPEAT_ILLEGAL; //名称重复
		return super.beforeSave(bean);
	}
	
	@Comment("查询")
	@RestMapping(name = "query", auth = true, /* actionid = Services.ACTION_QUERY, */ comment = "查询角色列表")
	public RetResult<Sheet<Goods>> queryForPage(Customer customer, Flipper flipper, GoodsBean bean) {
		// TODO Auto-generated method stub
		return super.queryForPage(customer, flipper, bean);
	}
	
	@Override
	@RestMapping(name = "delete", auth = false, comment = "删除")
	public RetResult<Integer> delete(Customer customer, int id) {
		// TODO Auto-generated method stub
		return super.delete(customer, id);
	}
	
	@Override
	@RestMapping(name = "queryById", auth = false, comment = "根据ID加载数据")
	public RetResult<Goods> queryById(Customer customer, int id) {
		// TODO Auto-generated method stub
		Goods goodInfo = source.find(Goods.class, id); // 查询当前对象是否被收藏过
		if(goodInfo != null ) {
			Wishlist wishlist = source.find(Wishlist.class, 
						FilterNode
							.create("customerid", customer.getCustomerid())
							.create("goodsid", id));
			if (null != wishlist) {
				goodInfo.setWishlistid(wishlist.getWishlistid());
			}
		}
		return RetResult.success(goodInfo);
	}
	
	
	
	/**
	 *	 若当前对象存在Id就进行更新
	 */
	@Override
	protected boolean isUpdate(Goods bean) {
		// TODO Auto-generated method stub
		return bean.getGoodsid() > 0;
	}
	
    public static void main(String[] args) throws Throwable {
    	Object obj = Goods.class;
    	System.out.println(Goods.class);
        GoodsService service = Application.singleton(GoodsService.class);
        Goods bean =  new Goods();
        bean.setBrand("AAA阿迪222222A");
        bean.setBusinessname("acc鞋子");
        bean.setCoverimage("ae.jpg");
        bean.setGoodsname("AAsasasAA");
        bean.setIntroduceimage("ae.jpg;ac.jpg;");
        bean.setMarkprice(10001l);
        Map<String ,String> mapData = new HashMap<String, String>();
        mapData.put("color", "红色");
        bean.setSpecifications(mapData);
        bean.setGoodsspecificdetails(mapData);
        bean.setGoodstype(10);
        bean.setDeliveryarea("湖北省;四川省;廣東省;");
        bean.setDeliveryplace("发货地址");
        bean.setDeliverycycleday(1);
        bean.setDeliverycyclehour(10);
        bean.setBusinessname("商家a");
        bean.setDeliveryarea("湖北武汉");
        bean.setReturnpolicy("不能退款!!!");
        bean.setMemberId(10000);
        bean.setCreatetime(System.currentTimeMillis());
        bean.setGoodsstatus(10);
        bean.setTrafficvolume(1001);
        bean.setTotalselesvolume(2001);
        bean.setGoodsdetails("商品详情。。。。");
        bean.setGoodslabels("A;B;C;");
        // 新建
        
//        Object res = service.create(null, bean);
//        System.out.println(res);
        GoodsBean qbean =  new GoodsBean();
        qbean.setGoodsname("阿迪");
        Object obj2 = service.queryForPage(null, null, qbean);
        System.out.println(obj2);
    }

}
