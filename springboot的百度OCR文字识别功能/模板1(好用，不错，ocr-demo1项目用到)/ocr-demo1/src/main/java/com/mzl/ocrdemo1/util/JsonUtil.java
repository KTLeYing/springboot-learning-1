package com.mzl.ocrdemo1.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @ClassName :   JsonUtil
 * @Description: Json工具类
 * @Author: mzl
 * @CreateDate: 2021/3/9 10:41
 * @Version: 1.0
 */
public class JsonUtil {

    /**
     * json字符串转换为map
     */
    public static <T> Map<String, Object> json2map(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonString, Map.class);
    }

}
