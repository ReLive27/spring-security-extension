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

import org.springframework.security.core.AuthenticationException;

/**
 * @author: ReLive27
 * @date: 2023/9/21 17:13
 */
public class BadCaptchaException extends AuthenticationException {

    private CaptchaAuthenticationExchange authenticationExchange;

    public BadCaptchaException(CaptchaAuthenticationExchange authenticationExchange) {
        this(authenticationExchange.getAuthorizationRequest() == null ? "验证码不能为空" :
                authenticationExchange.getAuthorizationResponse() == null ? "验证码已过期" :
                        "验证码错误");
        this.authenticationExchange = authenticationExchange;
    }

    public BadCaptchaException(String msg) {
        super(msg);
    }

    public BadCaptchaException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CaptchaAuthenticationExchange getAuthenticationExchange() {
        return authenticationExchange;
    }
}
