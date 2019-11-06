/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squrab.wish.sys;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;

import org.redkale.convert.json.JsonConvert;
import org.redkale.convert.json.JsonFactory;
import org.redkale.net.http.HttpContext;
import org.redkale.net.http.HttpRequest;
import org.redkale.net.http.HttpResponse;
import org.redkale.net.http.HttpServlet;
import org.redkale.net.http.HttpUserType;
import org.redkale.net.http.WebServlet;
import org.redkale.service.RetResult;
import org.redkale.util.AnyValue;

import com.squrab.wish.bean.LoginBean;
import com.squrab.wish.constant.RetCodes;
import com.squrab.wish.model.UserInfo;
import com.squrab.wish.model.UserMember;

/**
 * 用户模块的Servlet
 *
 * @author zhangjx
 */
@WebServlet({"/userMember/*"})
@HttpUserType(UserMember.class)
public class UserMemberServlet extends HttpServlet {

    public static final String COOKIE_AUTOLOGIN = "UNF";

    @Resource
    private UserMemberService service;

    @Resource
    private JsonConvert userMemberConvert;

    protected static final boolean winos = System.getProperty("os.name").contains("Window");

    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected final boolean fine = logger.isLoggable(Level.FINE);

    protected final boolean finer = logger.isLoggable(Level.FINER);

    protected final boolean finest = logger.isLoggable(Level.FINEST);

    protected static final RetResult RET_UNLOGIN = RetCodes.retResult(RetCodes.RET_USER_UNLOGIN);

    protected static final RetResult RET_AUTHILLEGAL = RetCodes.retResult(RetCodes.RET_USER_AUTH_ILLEGAL);

    @Override
    public void init(HttpContext context, AnyValue config) {
        JsonFactory factory = JsonFactory.root().createChild();
        //当前用户查看自己的用户信息时允许输出隐私信息
        factory.register(UserMember.class, false, "memberid", "account", "membername", "password", "mobile");
        userMemberConvert = factory.getConvert();
        super.init(context, config);
    }
    
    /**
     * Servlet的入口判断，一般用于全局的基本校验和预处理
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     *
     * @throws IOException
     */
    @Override
    public void preExecute(final HttpRequest request, final HttpResponse response) throws IOException {
        if (finer) response.recycleListener((req, resp) -> {  //记录处理时间比较长的请求
                long e = System.currentTimeMillis() - ((HttpRequest) req).getCreatetime();
                if (e > 200) logger.finer("http-execute-cost-time: " + e + " ms. request = " + req);
            });
        request.setCurrentUser(currentUser(service, request));
        response.nextEvent();
    }

    /**
     * 获取当前用户对象，没有返回null, 提供static方法便于WebSocket进行用户态判断
     *
     * @param service UserService
     * @param req     HTTP请求对象
     *
     * @return
     * @throws IOException
     */
    public static final UserMember currentUser(UserMemberService service, HttpRequest req) throws IOException {
    	UserMember user = (UserMember) req.currentUser();
        if (user != null) return user;
        String sessionid = req.getSessionid(false);
        if (sessionid == null || sessionid.isEmpty()) sessionid = req.getParameter("token");
        if (sessionid != null && !sessionid.isEmpty()) user = service.current(sessionid);
        if (user != null) return user;
        String autologin = req.getCookie(COOKIE_AUTOLOGIN);
        if (autologin == null) return null;
        autologin = autologin.replace('"', ' ').trim();
        LoginBean bean = new LoginBean();
        bean.setCookieinfo(autologin);
        bean.setLoginagent(req.getHeader("User-Agent"));
        bean.setLoginip(req.getRemoteAddr());
        bean.setSessionid(req.changeSessionid());
        RetResult<UserMember> result = service.login(bean);
        user = result.getResult();
        return user;
    }
    /**
     * 校验用户的登录态
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     *
     * @throws IOException
     */
    @Override
    public final void authenticate(HttpRequest request, HttpResponse response) throws IOException {
        UserInfo info = request.currentUser();
        if (info == null) {
            response.finishJson(RET_UNLOGIN);
            return;
        } else if (!info.checkAuth(request.getModuleid(), request.getActionid())) {
            response.finishJson(RET_AUTHILLEGAL);
            return;
        }
        response.nextEvent();
    }
}
