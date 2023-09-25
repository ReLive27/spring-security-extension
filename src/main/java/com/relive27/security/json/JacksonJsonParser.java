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
package com.relive27.security.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author: ReLive27
 * @date: 2023/9/24 23:03
 */
public class JacksonJsonParser implements JsonParser {
    private static final JacksonJsonParser.MapTypeReference MAP_TYPE = new JacksonJsonParser.MapTypeReference();
    private static final JacksonJsonParser.ListTypeReference LIST_TYPE = new JacksonJsonParser.ListTypeReference();
    private ObjectMapper objectMapper;

    public JacksonJsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JacksonJsonParser() {
    }

    public Map<String, Object> parseMap(String json) {
        return (Map) this.tryParse(() ->
                        (Map) this.getObjectMapper().readValue(json, MAP_TYPE)
                , Exception.class);
    }

    @Override
    public String formatMap(Map<String, ?> map) {
        return this.tryParse(() -> this.getObjectMapper().writeValueAsString(map), Exception.class);
    }

    public List<Object> parseList(String json) {
        return (List) this.tryParse(() ->
                        (List) this.getObjectMapper().readValue(json, LIST_TYPE)
                , Exception.class);
    }

    @Override
    public JsonNode getFirstNode(JsonNode node, String path) throws JsonParseException {
        JsonNode resultNode = null;
        if (path != null) {
            resultNode = getElement(node, path);
        }
        return resultNode;
    }

    @Override
    public JsonNode getElement(JsonNode json, String name) throws JsonParseException {
        if (json != null && name != null) {
            JsonNode node = json;
            for (String nodeName : name.split("\\.")) {
                if (node != null) {
                    if (nodeName.matches("\\d+")) {
                        node = node.get(Integer.parseInt(nodeName));
                    } else {
                        node = node.get(nodeName);
                    }
                }
            }
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    private ObjectMapper getObjectMapper() {
        if (this.objectMapper == null) {
            this.objectMapper = new ObjectMapper();
        }

        return this.objectMapper;
    }

    protected final <T> T tryParse(Callable<T> parser, Class<? extends Exception> check) {
        try {
            return parser.call();
        } catch (Exception e) {
            if (check.isAssignableFrom(e.getClass())) {
                throw new JsonParseException(e);
            } else {
                ReflectionUtils.rethrowRuntimeException(e);
                throw new IllegalStateException(e);
            }
        }
    }

    private static class ListTypeReference extends TypeReference<List<Object>> {
        private ListTypeReference() {
        }
    }

    private static class MapTypeReference extends TypeReference<Map<String, Object>> {
        private MapTypeReference() {
        }
    }
}

