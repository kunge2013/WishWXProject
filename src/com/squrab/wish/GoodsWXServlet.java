package com.squrab.wish;

import java.io.IOException;

import javax.annotation.Resource;

import org.redkale.net.http.HttpMapping;
import org.redkale.net.http.HttpRequest;
import org.redkale.net.http.HttpResponse;
import org.redkale.net.http.WebServlet;
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;
import org.redkale.util.Comment;

import com.squrab.wish.bean.BannerBean;
import com.squrab.wish.bean.CustomerBean;
import com.squrab.wish.bean.GiftBasketBean;
import com.squrab.wish.bean.GoodsBean;
import com.squrab.wish.bean.RecAddressBean;
import com.squrab.wish.bean.WishlistBean;
import com.squrab.wish.model.Customer;
import com.squrab.wish.model.GiftBasket;
import com.squrab.wish.model.Label;
import com.squrab.wish.model.RandomCode;
import com.squrab.wish.model.RecAddress;
import com.squrab.wish.model.UserInfo;
import com.squrab.wish.model.Wishlist;
import com.squrab.wish.service.BannerService;
import com.squrab.wish.service.GiftBasketService;
import com.squrab.wish.service.GoodsService;
import com.squrab.wish.service.LabelService;
import com.squrab.wish.service.RecAddressService;
import com.squrab.wish.service.WishlistService;

@WebServlet(value = { "/*" }, repair = false, comment = "商品访问服务")
public class GoodsWXServlet extends CustomerServlet {

	@Resource
	private GoodsService goodsService;

	@Resource
	private WishlistService wishlistService;

	@Resource
	private BannerService bannerService;

	@Resource
	private LabelService labelService;

	@Resource
	private RecAddressService recAddressService;

	@Resource
	private GiftBasketService giftBasketService;

	/*********************************** 商品 ************************************/
	@HttpMapping(url = "/goods/queryById", auth = false, comment = "商品详情")
	public void queryById(HttpRequest req, HttpResponse resp) {
		Customer customer = req.currentUser();
		int id = Integer.parseInt(req.getParameter("id"));
		resp.finish(goodsService.queryById(customer.getCustomerid(), id));
	}

	@Comment("伴手礼专区;热卖单品;猜你喜欢;首页查询；礼品查询")
	@HttpMapping(url = "/goods/query", auth = false, comment = "伴手礼专区;热卖单品;猜你喜欢;首页查询；礼品查询")
	public void queryForPage(HttpRequest req, HttpResponse resp) {
		Customer customer = req.currentUser();
		GoodsBean bean = req.getJsonParameter(GoodsBean.class, "bean");
		Flipper flipper = req.getJsonParameter(Flipper.class, "flipper");
		resp.finish(goodsService.queryForPage(customer, flipper, bean));
	}

	/*********************************** 心愿清单 ************************************/
	@HttpMapping(url = "/wishlist/create", auth = false, comment = "创建心愿清单")
	public void createWishlist(HttpRequest req, HttpResponse resp) {
		Customer customer = req.currentUser();
		Wishlist bean = req.getJsonParameter(Wishlist.class, "bean");
		resp.finish(wishlistService.create(customer, bean)); // 心愿清单已存在
	}

	@HttpMapping(url = "/wishlist/query", auth = false, comment = "查询轮播图可以根据ids 和其它条件")
	public void queryWishlist(HttpRequest req, HttpResponse resp) {
		WishlistBean bean = req.getJsonParameter(WishlistBean.class, "bean");
		Flipper flipper = req.getJsonParameter(Flipper.class, "flipper");
		resp.finish(wishlistService.queryForPage(flipper, bean)); // 心愿清单已存在
	}

	@HttpMapping(url = "/wishlist/delete", auth = false, comment = "根据Id删除")
	public void deletewishlist(HttpRequest req, HttpResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		resp.finish(wishlistService.delete(id));
	}

	/*********************************** 标签统计 ************************************/
	@HttpMapping(url = "/label/inLabelcreasebindgoodsnum", auth = false, comment = "标签商品个数统计")
	public void inLabelcreasebindgoodsnum(HttpRequest req, HttpResponse resp) {
		Customer customer = req.currentUser();
		Label bean = req.getJsonParameter(Label.class, "bean");
		resp.finish(goodsService.increasebindgoodsnum(customer, bean));
	}

	/***********************************
	 * Banner图
	 ************************************/
	@HttpMapping(url = "/banner/query", auth = false, comment = "查询轮播图可以根据ids 和其它条件")
	public void queryBanner(HttpRequest req, HttpResponse resp) {
		Flipper flipper = req.getJsonParameter(Flipper.class, "flipper");
		BannerBean bean = req.getJsonParameter(BannerBean.class, "bean");
		resp.finish(bannerService.queryForPage(flipper, bean));
	}

	/*********************************** 礼品篮 ************************************/
	@HttpMapping(url = "/giftbasket/addGiftBasket", auth = false, comment = "添加删除礼品蓝")
	public void addGiftBasket(HttpRequest req, HttpResponse resp) {
		Customer customer = req.currentUser();
		GiftBasket bean = req.getJsonParameter(GiftBasket.class, "bean");
		resp.finish(giftBasketService.create(customer, bean));
	}

	@HttpMapping(url = "/giftbasket/query", auth = true, comment = "添加删除礼品蓝")
	public void queryGiftBasket(HttpRequest req, HttpResponse resp) {
		Customer customer = req.currentUser();
		Flipper flipper = req.getJsonParameter(Flipper.class, "flipper");
		GiftBasketBean bean = req.getJsonParameter(GiftBasketBean.class, "bean");
		bean.setCustomerid(customer.getCustomerid());
		resp.finish(giftBasketService.queryForPage(flipper, bean));
	}

	@HttpMapping(url = "/giftbasket/delete", auth = false, comment = "清除礼物篮")
	public void deleteGiftBasket(HttpRequest req, HttpResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		resp.finish(giftBasketService.delete(id));
	}

	@HttpMapping(url = "/customer/fetchOpenid", auth = false, comment = "获取微信openId")
	public void fetchOpenid(HttpRequest req, HttpResponse resp) {
		String code = req.getRequstURIPath("code:", req.getParameter("code"));
		resp.finishJson(service.fetchOpenid(code));
	}

	@Comment("绑定电话号码")
	@HttpMapping(url = "/customer/bindMobile", auth = true, comment = "绑定电话号码")
	public void bindMobile(HttpRequest req, HttpResponse resp) {
		CustomerBean bean = req.getJsonParameter(CustomerBean.class, "bean");
		resp.finishJson(service.bindMobile(bean));
	}

	@Comment("添加标签到用户表字段以分号分割")
	@HttpMapping(url = "/customer/addLabels", auth = true, comment = "添加标签到用户表字段以分号分割")
	public void addLabels(HttpRequest req, HttpResponse resp) {
		Customer bean = req.getJsonParameter(CustomerBean.class, "bean");
		resp.finishJson(service.create(bean));
	}

	/**********************************
	 * 用户收货地址
	 ************************************************/
	@Comment("用户收获地址绑定")
	@HttpMapping(url = "/recAddress/bindRecAddress", auth = true, comment = "用户收获地址绑定")
	public void bindRecAddress(HttpRequest req, HttpResponse resp) {
		Customer customer = req.currentUser();
		RecAddress bean = req.getJsonParameter(RecAddress.class, "bean");
		;
		resp.finishJson(recAddressService.create(customer, bean));
	}

	@Comment("分页查询")
	@HttpMapping(url = "/recAddress/queryRecAddress", auth = false, comment = "查询收货地址列表")
	protected void queryRecAddress(HttpRequest req, HttpResponse resp) {
		Flipper flipper = req.getJsonParameter(Flipper.class, "flipper");
		RecAddressBean bean = req.getJsonParameter(RecAddressBean.class, "bean");
		;
		resp.finishJson(recAddressService.queryForPage(flipper, bean));
	}

	@HttpMapping(url = "/goods/login", auth = false, comment = "测试接口")
	public void test(HttpRequest req, HttpResponse resp) {
		System.out.println("AAA");
	}

	/**
	 * 手机号注册绑定
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@HttpMapping(auth = false, url = "/customer/smsregcode", comment = "短信验证码")
	public void smsreg(HttpRequest req, HttpResponse resp) throws IOException {
		smsvercode(RandomCode.TYPE_SMSREG, req, resp);
	}

	private void smsvercode(final short type, HttpRequest req, HttpResponse resp) throws IOException {
		String mobile = req.getRequstURIPath("mobile:", req.getParameter("mobile"));
		if (type == RandomCode.TYPE_SMSODM) { // 给原手机号码发送验证短信
			UserInfo user = req.currentUser();
			if (user != null)
				mobile = user.getMobile();
		}
		RetResult rr = service.smscode(type, mobile);
		if (finest)
			logger.finest(req.getRequestURI() + ", mobile = " + mobile + "---->" + rr);
		resp.finishJson(rr);
	}
}
