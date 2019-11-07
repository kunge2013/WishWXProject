package com.squrab.wish.goods;

import javax.annotation.Resource;

import org.redkale.net.http.RestMapping;
import org.redkale.net.http.WebServlet;
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;
import org.redkale.util.Comment;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.BannerBean;
import com.squrab.wish.bean.GiftBasketBean;
import com.squrab.wish.bean.GoodsBean;
import com.squrab.wish.bean.WishlistBean;
import com.squrab.wish.customer.CustomerServlet;
import com.squrab.wish.model.Banner;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.GiftBasket;
import com.squrab.wish.model.Goods;
import com.squrab.wish.model.Label;
import com.squrab.wish.model.Wishlist;
import com.squrab.wish.service.BannerService;
import com.squrab.wish.service.GiftBasketService;
import com.squrab.wish.service.GoodsService;
import com.squrab.wish.service.WishlistService;

@WebServlet(value = {"/goods/*"}, repair = false, comment = "商品访问服务")
public class GoodsWXServlet extends CustomerServlet {

	@Resource
	private GoodsService goodsService;
	
	@Resource
	private WishlistService wishlistService;
	
	@Resource
	private BannerService bannerService;
	
	@Resource
	private GiftBasketService giftBasketService;
	/***********************************商品************************************/
	@RestMapping(name = "queryById", auth = false, comment = "商品详情")
	public RetResult<Goods> queryById(Customer customer, int id) {
		return goodsService.queryById(customer.getCustomerid(), id);
	}
	@Comment("伴手礼专区;热卖单品;猜你喜欢;首页查询；礼品查询")
	@RestMapping(name = "query", auth = true,  comment = "伴手礼专区;热卖单品;猜你喜欢;首页查询；礼品查询")
	public RetResult<Sheet<Goods>> queryForPage(Customer customer, Flipper flipper, GoodsBean bean) {
		return goodsService.queryForPage(customer, flipper, bean);
	}
	
	/***********************************心愿清单************************************/
	@RestMapping(name = "createWishlist", auth = false, comment = "创建心愿清单")
	public RetResult<Integer> createWishlist(Customer customer, Wishlist bean) {
		return wishlistService.create(customer, bean); // 心愿清单已存在
	} 
	
	@RestMapping(name = "queryWishlist", auth = false, comment = "查询轮播图可以根据ids 和其它条件")
	public RetResult<Sheet<Wishlist>> queryWishlist(Flipper flipper, WishlistBean bean){
		return wishlistService.queryForPage(flipper, bean);
	}
	
	@RestMapping(name = "deleteWishlist", auth = false, comment = "根据Id删除")
	public RetResult<Integer> delete(int id) {
		return wishlistService.delete(id);
	}
	/***********************************标签统计************************************/
	@RestMapping(name = "inLabelcreasebindgoodsnum", auth = false, comment = "标签商品个数统计")
	public RetResult<Integer> inLabelcreasebindgoodsnum(Customer customer, Label bean){
		return goodsService.increasebindgoodsnum(customer, bean);
	}

	/***********************************Banner图************************************/
	@RestMapping(name = "queryBanner", auth = false, comment = "查询轮播图可以根据ids 和其它条件")
	public RetResult<Sheet<Banner>> queryBanner(Flipper flipper, BannerBean bean){
		return bannerService.queryForPage(flipper, bean);
	}
	/***********************************礼品篮************************************/
	@RestMapping(name = "addGiftBasket", auth = false, comment = "添加删除礼品蓝")
	public RetResult<Integer> addGiftBasket(Customer customer, GiftBasket bean) {
		return giftBasketService.create(customer, bean);
	}
	
	@RestMapping(name = "queryGiftBasket", auth = true, comment = "添加删除礼品蓝")
	public RetResult<Sheet<GiftBasket>> queryGiftBasket(Customer customer, Flipper flipper, GiftBasketBean bean) {
		bean.setCustomerid(customer.getCustomerid());
		return giftBasketService.queryForPage(flipper, bean);
	}
	
	@RestMapping(name = "deleteGiftBasket", auth = false, comment = "清除礼物篮")
	public RetResult<Integer> delete(Customer customer, int id) {
		return giftBasketService.delete(id);
	}
	
}
