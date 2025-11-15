package org.example.demo1.filter;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
//@WebFilter(urlPatterns = "/*")// 拦截所有请求
public class DemoFilter implements Filter {

    // 初始化，web服务器启动时执行，仅执行一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    // 拦截到请求后执行，执行多次

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("DemoFilter doFilter");
        // 放行，将请求传递给下一个过滤器或Servlet
        filterChain.doFilter(servletRequest, servletResponse);
    }
    // 销毁，web服务器停止时执行，仅执行一次

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
