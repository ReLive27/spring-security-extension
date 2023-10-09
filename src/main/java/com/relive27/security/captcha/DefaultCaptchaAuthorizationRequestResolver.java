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

import com.relive27.security.json.JsonParser;
import com.relive27.security.json.JsonParserFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * @author: ReLive27
 * @date: 2023/9/21 17:19
 */
@Slf4j
public class DefaultCaptchaAuthorizationRequestResolver implements CaptchaAuthorizationRequestResolver {
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;

    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

    private final JsonParser jsonParser = JsonParserFactory.getJsonParser();

    @Override
    public CaptchaAuthorizationRequest resolve(HttpServletRequest request) throws IOException {
        CaptchaAuthorizationRequest.Builder builder = CaptchaAuthorizationRequest.host(getIpAddr(request));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (HttpMethod.POST.equals(HttpMethod.resolve(request.getMethod()))) {
            try (BufferedReader reader = request.getReader()) {
                String tmp;
                StringBuilder body = new StringBuilder();
                while ((tmp = reader.readLine()) != null) {
                    body.append(tmp);
                }

                Map<String, Object> bodyMap = jsonParser.parseMap(body.toString());
                String captcha = (String) bodyMap.get(captchaParameter);
                builder.captcha(captcha);
                if (authentication == null) {
                    String username = (String) bodyMap.get(usernameParameter);
                    builder.username(username);
                } else {
                    builder.username(authentication.getName());
                }
            } catch (IOException e) {
                log.error("Failed to parse verification code request body", e);
                return null;
            }
        } else {
            builder.captcha(this.obtainCaptcha(request));
            if (authentication == null) {
                builder.username(this.obtainUsername(request));
            } else {
                builder.username(authentication.getName());
            }
        }

        return builder.build();
    }

    @Nullable
    protected String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(this.captchaParameter);
    }

    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }


    public static String getIpAddr(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        } else {
            return xfHeader.split(",")[0];
        }
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public void setCaptchaParameter(String captchaParameter) {
        this.captchaParameter = captchaParameter;
    }
}
