package com.tangmiyi.future.core.controller;

import com.tangmiyi.future.core.utils.base.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Value("${spring.profiles.active}")
    private String env;

    protected String getJwtToken () {
        String jwtToken = jwtTokenUtils.getTokenFromHttpHead(httpServletRequest);
        return jwtToken;
    }
}
