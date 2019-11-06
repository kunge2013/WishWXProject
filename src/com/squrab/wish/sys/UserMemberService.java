/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squrab.wish.sys;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.redkale.net.http.HttpResult;
import org.redkale.net.http.RestMapping;
import org.redkale.net.http.RestService;
import org.redkale.net.http.RestSessionid;
import org.redkale.service.RetResult;
import org.redkale.source.CacheSource;
import org.redkale.util.Utility;

import com.squrab.wish.bean.LoginBean;
import com.squrab.wish.constant.RetCodes;
import com.squrab.wish.core.BaseService;
import com.squrab.wish.model.LoginResult;
import com.squrab.wish.model.OssRetCodes;
import com.squrab.wish.model.Services;
import com.squrab.wish.model.UserMember;

/**
 *
 * @author 86176
 */
@RestService(name = "usermember", moduleid = Services.MODULE_USER, comment = "【OSS系统】员工管理模块")
public class UserMemberService extends BaseService {
	protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
     private static final MessageDigest sha1;

    private static final MessageDigest md5;

    public static final String AES_KEY = "REDKALE_20200202";

    private static final Cipher aesEncrypter; //加密

    private static final Cipher aesDecrypter; //解密

    static {
        MessageDigest d = null;
        try {
            d = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        sha1 = d;
        try {
            d = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        md5 = d;

        Cipher cipher = null;
        final SecretKeySpec aesKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        } catch (Exception e) {
            throw new Error(e);
        }
        aesEncrypter = cipher;  //加密
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
        } catch (Exception e) {
            throw new Error(e);
        }
        aesDecrypter = cipher; //解密
    }

    private final int sessionExpireSeconds = 30 * 60;

    @Resource(name = "usersessions")
    protected CacheSource<Integer> sessions;

    
    @RestMapping(name = "login", auth = false, comment = "账号方式登录")
    public RetResult login(LoginBean bean) {
        if (bean == null || bean.emptySessionid() || bean.emptyAccount()) return OssRetCodes.retResult(OssRetCodes.RET_PARAMS_ILLEGAL);
        final LoginResult result = new LoginResult();
        final int optionid = Services.optionid(Services.MODULE_USER, Services.ACTION_LOGIN);
        bean.setPassword(UserMember.md5IfNeed(bean.getPassword()));
        UserMember detail = source.find(UserMember.class, "account", bean.getAccount());
        if (detail == null || Objects.equals(detail.getPassword(), bean.getPassword())/*|| (!bean.isWxlogin() && !Objects.equals(detail.getPassword(), bean.getPassword()))*/) {
            result.setRetcode(RetCodes.RET_USER_ACCOUNT_PWD_ILLEGAL);
//            super.log(null, optionid, "用户账号或密码错误，登录失败.");
            return OssRetCodes.retResult(OssRetCodes.RET_USER_ACCOUNT_PWD_ILLEGAL);
        }
        result.setRetcode(0);
        result.setSessionid(bean.getSessionid());
        result.setUser(detail);
//        MemberInfo user = detail.createMemberInfo();
//        if (user.isStatusFreeze()) {
//            result.setRetcode(RET_USER_FREEZED);
//            super.log(user, optionid, "用户被禁用，登录失败.");
//            return OssRetCodes.retResult(OssRetCodes.RET_USER_FREEZED);
//        }
//        if (!user.canAdmin()) user.setOptions(roleService.queryOptionidsByMemberid(user.getMemberid()));
//        result.setUser(user);
//        super.log(user, optionid, "用户登录成功.");
        System.out.println(detail.toString() +  optionid + "用户登录成功.");
        this.sessions.set(sessionExpireSeconds, bean.getSessionid(), result.getUser().getMemberid());
        return RetResult.success();
    }

    public UserMember current(String sessionid) {
    	return null;
    }
    
    @RestMapping(name = "logout", auth = true, comment = "用户退出登录")
    public HttpResult logout(@RestSessionid String sessionid) {
        sessions.remove(sessionid);
        return new HttpResult().header("Location", "/").status(302);
    }
     //AES加密
    public static String encryptAES(String value) {
        if (value == null || value.isEmpty()) return value;
        try {
            synchronized (aesEncrypter) {
                return Utility.binToHexString(aesEncrypter.doFinal(value.getBytes()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //AES解密
    public static String decryptAES(String value) {
        if (value == null || value.isEmpty()) return value;
        byte[] hex = Utility.hexToBin(value);
        try {
            synchronized (aesEncrypter) {
                return new String(aesDecrypter.doFinal(hex));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //第二次MD5
    public static String secondPasswordMD5(String passwordoncemd5) {
        byte[] bytes = ("REDKALE-" + passwordoncemd5.trim().toLowerCase()).getBytes();
        synchronized (md5) {
            bytes = md5.digest(bytes);
        }
        return new String(Utility.binToHex(bytes));
    }

    //第三次密码加密
    public static String digestPassword(String passwordtwicemd5) {
        if (passwordtwicemd5 == null || passwordtwicemd5.isEmpty()) return passwordtwicemd5;
        byte[] bytes = (passwordtwicemd5.trim().toLowerCase() + "-REDKALE").getBytes();
        synchronized (sha1) {
            bytes = sha1.digest(bytes);
        }
        return new String(Utility.binToHex(bytes));
    }

}
