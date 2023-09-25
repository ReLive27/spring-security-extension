/***********************************************************************************************
 *  Copyright (C) 2023 ReLive27
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 ***********************************************************************************************/
package com.relive27.security.captcha;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

/**
 * @author: ReLive27
 * @date: 2023/9/21 17:15
 */
public class CaptchaAuthenticationConfigurer extends AbstractHttpConfigurer<CaptchaAuthenticationConfigurer, HttpSecurity> {
    private CaptchaAuthenticationFilter filter;

    protected ApplicationEventPublisher eventPublisher;

    public CaptchaAuthenticationConfigurer() {
        this.filter = new CaptchaAuthenticationFilter();
        this.loginProcessingUrl("/login");
    }

    public CaptchaAuthenticationConfigurer loginProcessingUrl(String loginProcessingUrl) {
        Assert.hasText(loginProcessingUrl, "loginProcessingUrl cannot be empty");
        this.filter.setRequestMatcher(new AntPathRequestMatcher(loginProcessingUrl));
        return this;
    }

    public CaptchaAuthenticationConfigurer captchaAuthorizationRequestResolver(CaptchaAuthorizationRequestResolver authorizationRequestResolver) {
        Assert.notNull(authorizationRequestResolver, "authorizationRequestResolver cannot be null");
        this.filter.setAuthorizationRequestResolver(authorizationRequestResolver);
        return this;
    }

    public CaptchaAuthenticationConfigurer captchaAuthorizationRequestRepository(CaptchaAuthorizationResponseRepository<CaptchaAuthorizationResponse> authorizationResponseRepository) {
        Assert.notNull(authorizationResponseRepository, "authorizationResponseRepository cannot be null");
        this.filter.setAuthorizationResponseRepository(authorizationResponseRepository);
        return this;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(this.postProcess(this.filter), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
}
