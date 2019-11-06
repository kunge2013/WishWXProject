/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squrab.wish.goods;

import org.redkale.net.http.RestMapping;
import org.redkale.net.http.RestService;
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.CategoryBean;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.model.Category;
import com.squrab.wish.model.Services;
import com.squrab.wish.model.Customer;

/**
 * @author 86176
 */

@RestService(name = "categoryInfo", moduleid = Services.MODULE_USER, comment = "【OSS系统】分类管理")
public class CategoryService extends CrudBaseService<Customer, Category, CategoryBean> {
	

	@Override
	@RestMapping(name = "create", auth = false, comment = "新增修改")
	public RetResult<Integer> create(Customer customer, Category bean) {
		return super.create(customer, bean);
	}
	
	@Override
	protected boolean isUpdate(Category bean) {
		// TODO Auto-generated method stub
		return bean.getCategoryid() > 0;
	}
	@Override
	@RestMapping(name = "query",auth = false,  comment = "查询类型列表")
	protected RetResult<Sheet<Category>> queryForPage(Customer customer, Flipper flipper, CategoryBean bean) {
		// TODO Auto-generated method stub
		return super.queryForPage(customer, flipper, bean);
	}
	
	@Override
	@RestMapping(name = "delete", auth = false,  comment = "删除类型信息关联")
	public RetResult<Integer> delete(Customer customer, int id) {
		// TODO Auto-generated method stub
		return super.delete(customer, id);
	}
	
	@Override
	@RestMapping(name = "queryById", auth = false, comment = "根据ID查询")
	public RetResult<Category> queryById(Customer customer, int id) {
		// TODO Auto-generated method stub
		return super.queryById(customer, id);
	}


}
