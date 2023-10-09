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

/**
 * @author: ReLive27
 * @date: 2023/9/21 17:11
 */
public class CaptchaAuthenticationExchange {
    private final CaptchaAuthorizationRequest authorizationRequest;
    private final CaptchaAuthorizationResponse authorizationResponse;

    public CaptchaAuthenticationExchange(CaptchaAuthorizationRequest authorizationRequest,
                                         CaptchaAuthorizationResponse authorizationResponse) {
        this.authorizationRequest = authorizationRequest;
        this.authorizationResponse = authorizationResponse;
    }

    public CaptchaAuthorizationRequest getAuthorizationRequest() {
        return authorizationRequest;
    }

    public CaptchaAuthorizationResponse getAuthorizationResponse() {
        return authorizationResponse;
    }
}

