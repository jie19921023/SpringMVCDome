package com.zj.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * 拦截器采用模板设计模式,在父类中过滤不需要登录的链接
 * Created by jie.zhao on 18-4-12.
 */
public abstract  class AbstractExcludeUrlInterceptor extends HandlerInterceptorAdapter {

    private PathMatcher pathMatcher = new AntPathMatcher();
    private List<String> excludeUrlPatterns;

    protected abstract boolean doPreHandle(HttpServletRequest request,HttpServletResponse response);
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(patternUrl(excludeUrlPatterns,getPath(request))){
            return true;
        }
        return doPreHandle(request,response);
    }


    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public List<String> getExcludeUrlPatterns() {
        return excludeUrlPatterns;
    }

    public void setExcludeUrlPatterns(List<String> excludeUrlPatterns) {
        this.excludeUrlPatterns = excludeUrlPatterns;
    }

    /**
     *获得请求链接的路径
     * @param request
     * @return
     */
    private final String getPath(HttpServletRequest request){
        String servletPath=request.getServletPath();
        if(servletPath == null || "/".equals(servletPath)){
            servletPath="";
        }
        return servletPath+request.getPathInfo();
    }

    private final boolean patternUrl(List<String> excludeUrlPatterns,String path){
        boolean flag=false;
        if(excludeUrlPatterns != null && excludeUrlPatterns.size()>0){
            Iterator<String> iterator=excludeUrlPatterns.iterator();
            while (iterator.hasNext()){
                String url=iterator.next();
                if(pathMatcher.match(url,path)){
                    flag=true;
                    break;
                }
            }
        }
        return flag;
    }
}
