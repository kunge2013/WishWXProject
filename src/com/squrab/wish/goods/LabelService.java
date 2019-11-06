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
import org.redkale.util.Comment;
import org.redkale.util.Sheet;

import com.squrab.wish.bean.LabelBean;
import com.squrab.wish.core.CrudBaseService;
import com.squrab.wish.model.Label;
import com.squrab.wish.model.Services;
import com.squrab.wish.model.UserMember;

/**
 *
 * @author 86176
 */
@RestService(name = "labelInfo", moduleid = Services.MODULE_USER, comment = "【OSS系统】标签管理分类管理")
public class LabelService extends CrudBaseService<UserMember, Label, LabelBean> {

	@RestMapping(name = "create", auth = false, comment = "新增标签")
	public RetResult<Integer> create(UserMember userMember, Label bean) {
		return super.create(userMember, bean);
	}

	@Override
	protected boolean isUpdate(Label bean) {
		// TODO Auto-generated method stub
		return bean.getLabelid() > 0;
	}
	
	@Comment("分页查询")
	@RestMapping(name = "query",  auth = false, actionid = Services.ACTION_QUERY, comment = "查询角色列表")
	protected RetResult<Sheet<Label>> queryForPage(UserMember userMember, Flipper flipper, LabelBean bean) {
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
	@RestMapping(name = "queryById", auth = false, comment = "根据ID查询")
	public RetResult<Label> queryById(UserMember userMember, int id) {
		// TODO Auto-generated method stub
		return super.queryById(userMember, id);
	}
	
}
