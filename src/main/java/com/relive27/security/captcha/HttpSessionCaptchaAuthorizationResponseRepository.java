/*
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
 */
package com.relive27.security.captcha;

import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: ReLive27
 * @date: 2023/9/21 17:20
 */
public final class HttpSessionCaptchaAuthorizationResponseRepository implements CaptchaAuthorizationResponseRepository<CaptchaAuthorizationResponse> {
    private static final String DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME = HttpSessionCaptchaAuthorizationResponseRepository.class
            .getName() + ".AUTHORIZATION_REQUEST";

    private final String sessionAttributeName = DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME;


    @Override
    public CaptchaAuthorizationResponse loadCaptchaAuthorizationResponse(HttpServletRequest request) {
        Assert.notNull(request, "request cannot be null");
        CaptchaAuthorizationResponse authorizationResponse = this.getAuthorizationResponses(request);
        return authorizationResponse;
    }

    @Override
    public void saveAuthorizationResponse(CaptchaAuthorizationResponse authorizationResponse, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");
        if (authorizationResponse == null) {
            this.removeAuthorizationResponse(request, response);
            return;
        }
        request.getSession().setAttribute(this.sessionAttributeName, authorizationResponse);

    }

    @Override
    public CaptchaAuthorizationResponse removeAuthorizationResponse(HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");

        CaptchaAuthorizationResponse authorizationResponse = this.getAuthorizationResponses(request);
        if (authorizationResponse != null) {
            request.getSession().removeAttribute(this.sessionAttributeName);
        }
        return authorizationResponse;
    }

    private CaptchaAuthorizationResponse getAuthorizationResponses(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (CaptchaAuthorizationResponse) session.getAttribute(this.sessionAttributeName) : null;
    }
}
