package com.fastcam.spserver.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTCheckFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        System.out.println("check uri.........." + path);

        if (path.startsWith("/member/loginLocal")) {
            return true;
        }

        if(path.startsWith("/images/"))
            return true;

        if(path.startsWith("/product/getBestProduct"))
            return true;

        if(path.startsWith("/product/getNewProduct"))
            return true;

        if(path.startsWith("/product_images/"))
            return true;

        if(path.startsWith("/member/idcheck"))
            return true;

        if(path.startsWith("/member/join"))
            return true;

        if(path.startsWith("/member/kakaostart"))
            return true;

        if(path.startsWith("/member/kakaoLogin"))
            return true;

        if(path.startsWith("/product/getCategoryList"))
            return true;

        if(path.startsWith("/product/getProduct"))
            return true;

        if(path.startsWith("/member/refresh"))
            return true;

        if(path.startsWith("/favicon.ico"))
            return true;

        return false;
    }
}
