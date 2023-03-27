package com.memoire.kital.raph.openFeign.interceptorTocken;

import com.memoire.kital.raph.security.SecurityUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = SecurityUtils.getJwtToken();
        if(null != token && !token.trim().isEmpty()) {
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }
}
