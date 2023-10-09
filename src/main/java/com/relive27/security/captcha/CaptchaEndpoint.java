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

import com.relive27.security.core.endpoint.FrameworkEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: ReLive27
 * @date: 2023/9/14 15:27
 */
@FrameworkEndpoint
public class CaptchaEndpoint {

    @RequestMapping("/captcha")
    public ResponseEntity test(){
        return ResponseEntity.status(200)
                .body("ssss");
    }
}
