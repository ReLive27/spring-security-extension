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

import lombok.Data;
import org.springframework.util.Assert;

/**
 * @author: ReLive27
 * @date: 2023/9/21 17:06
 */
@Data
public class CaptchaAuthorizationResponse {
    private String captcha;

    private String host;

    public static CaptchaAuthorizationResponse.Builder host(String host) {
        return new CaptchaAuthorizationResponse.Builder().host(host);
    }

    public static CaptchaAuthorizationResponse.Builder captcha(String captcha) {
        return new CaptchaAuthorizationResponse.Builder().captcha(captcha);
    }

    public static final class Builder {

        private String captcha;

        private String host;

        public CaptchaAuthorizationResponse.Builder captcha(String captcha) {
            this.captcha = captcha;
            return this;
        }

        public CaptchaAuthorizationResponse.Builder host(String host) {
            this.host = host;
            return this;
        }


        public CaptchaAuthorizationResponse build() {
            Assert.hasText(this.captcha, "captcha cannot be empty");
            Assert.hasText(this.host, "host cannot be empty");
            CaptchaAuthorizationResponse authorizationResponse = new CaptchaAuthorizationResponse();
            authorizationResponse.captcha = this.captcha;
            authorizationResponse.host = this.host;
            return authorizationResponse;
        }
    }
}
