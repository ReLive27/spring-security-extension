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

import lombok.Data;
import org.springframework.util.Assert;

/**
 * @author: ReLive27
 * @date: 2023/9/21 16:45
 */
@Data
public class CaptchaAuthorizationRequest {

    private String captcha;

    private String host;

    private String username;

    public static Builder host(String host) {
        return new Builder().host(host);
    }

    public static Builder captcha(String captcha) {
        return new Builder().captcha(captcha);
    }


    public static final class Builder {

        private String captcha;

        private String host;

        private String username;

        public Builder captcha(String captcha) {
            this.captcha = captcha;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public CaptchaAuthorizationRequest build() {
            Assert.hasText(this.captcha, "captcha cannot be empty");
            Assert.hasText(this.host, "host cannot be empty");
            CaptchaAuthorizationRequest authorizationRequest = new CaptchaAuthorizationRequest();
            authorizationRequest.captcha = this.captcha;
            authorizationRequest.host = this.host;
            authorizationRequest.username = this.username;
            return authorizationRequest;
        }
    }
}
