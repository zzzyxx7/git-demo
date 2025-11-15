package org.example.demo1.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.demo1.utils.JwtUtils;

import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1. 获取请求路径
        String path = request.getServletPath();
        //2. 判断是否是登录请求,/login
        if (path.equals("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //3. 获取请求头中的token
        String token = request.getHeader("token");
        //4. 验证token
        if (token == null || token.isEmpty()){
            response.setStatus(401);
            return;
        }
        try{
            JwtUtils.parseJwt(token);
        }catch(Exception e){
            response.setStatus(401);
            return;
        }
        //5. 如果验证成功,放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
}
