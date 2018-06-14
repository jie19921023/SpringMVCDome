package com.zj.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jie.zhao on 18-4-12.
 */
public class LoginCheckInterceptor extends AbstractExcludeUrlInterceptor {


    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response) {
        //校验用户是否登录
        return false;
    }
}
