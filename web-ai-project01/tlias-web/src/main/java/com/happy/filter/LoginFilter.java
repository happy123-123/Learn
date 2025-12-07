package com.happy.filter;

import com.happy.utils.CurrentHolder;
import com.happy.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        HttpServletResponse servletResponse1 = (HttpServletResponse) servletResponse;

        if (servletRequest1.getRequestURI().contains("/login")) {
            log.info("登录操作,放行");
            filterChain.doFilter(servletRequest1, servletResponse1);
            return;
        }
        String token = servletRequest1.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("令牌不存在");
            servletResponse1.setStatus(401);
            return;
        }
        try {
            Claims claims = JwtUtils.parseJWT(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
            log.info("{}", empId);
        } catch (Exception e) {
            log.info("令牌非法");
            servletResponse1.setStatus(401);
            return;
        }
        filterChain.doFilter(servletRequest1, servletResponse1);
        log.info("令牌合法,放行");
        CurrentHolder.remove();
    }
}
