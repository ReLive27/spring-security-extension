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
package com.relive27.security.core.endpoint;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: ReLive27
 * @date: 2023/9/14 15:28
 */
public class FrameworkEndpointHandlerMapping extends RequestMappingHandlerMapping {
    private static final String REDIRECT = UrlBasedViewResolver.REDIRECT_URL_PREFIX;

    private static final String FORWARD = UrlBasedViewResolver.FORWARD_URL_PREFIX;

    private Map<String, String> mappings = new HashMap<>();

    private String approvalParameter = "user_extension_approval";

    private Set<String> paths = new HashSet<>();

    private String prefix;

    public FrameworkEndpointHandlerMapping() {
        setOrder(Ordered.LOWEST_PRECEDENCE - 2);
    }

    public void setPrefix(String prefix) {
        if (!StringUtils.hasText(prefix)) {
            prefix = "";
        } else
            while (prefix.endsWith("/")) {
                prefix = prefix.substring(0, prefix.lastIndexOf("/"));
            }
        this.prefix = prefix;
    }

    public void setMappings(Map<String, String> patternMap) {
        this.mappings = new HashMap<>(patternMap);
        for (String key : mappings.keySet()) {
            String result = mappings.get(key);
            if (result.startsWith(FORWARD)) {
                result = result.substring(FORWARD.length());
            }
            if (result.startsWith(REDIRECT)) {
                result = result.substring(REDIRECT.length());
            }
            mappings.put(key, result);
        }
    }

    public String getServletPath(String defaultPath) {
        return (prefix == null ? "" : prefix) + getPath(defaultPath);
    }


    public String getPath(String defaultPath) {
        String result = defaultPath;
        if (mappings.containsKey(defaultPath)) {
            result = mappings.get(defaultPath);
        }
        return result;
    }

    public Set<String> getPaths() {
        return paths;
    }

    public void setApprovalParameter(String approvalParameter) {
        this.approvalParameter = approvalParameter;
    }

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotationUtils.findAnnotation(beanType, FrameworkEndpoint.class) != null;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {

        RequestMappingInfo defaultMapping = super.getMappingForMethod(method, handlerType);
        if (defaultMapping == null) {
            return null;
        }

        Set<String> defaultPatterns = defaultMapping.getPatternsCondition().getPatterns();
        String[] patterns = new String[defaultPatterns.size()];

        int i = 0;
        for (String pattern : defaultPatterns) {
            patterns[i] = getPath(pattern);
            paths.add(pattern);
            i++;
        }
        PatternsRequestCondition patternsInfo = new PatternsRequestCondition(patterns, useTrailingSlashMatch(), getPathMatcher());

        ParamsRequestCondition paramsInfo = defaultMapping.getParamsCondition();
        if (!approvalParameter.equals("user_extension_approval")) {
            String[] params = new String[paramsInfo.getExpressions().size()];
            Set<NameValueExpression<String>> expressions = paramsInfo.getExpressions();
            i = 0;
            for (NameValueExpression<String> expression : expressions) {
                String param = expression.toString();
                if ("user_extension_approval".equals(param)) {
                    params[i] = approvalParameter;
                } else {
                    params[i] = param;
                }
                i++;
            }
            paramsInfo = new ParamsRequestCondition(params);
        }

        RequestMappingInfo mapping = new RequestMappingInfo(patternsInfo, defaultMapping.getMethodsCondition(),
                paramsInfo, defaultMapping.getHeadersCondition(), defaultMapping.getConsumesCondition(),
                defaultMapping.getProducesCondition(), defaultMapping.getCustomCondition());
        return mapping;

    }
}
