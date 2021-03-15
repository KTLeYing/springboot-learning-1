package com.mzl.shirodemo2.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @ClassName :   ResultMap
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/22 0:12
 * @Version: 1.0
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMap<T> {

    private String result;
    private String message;
    private T data;

    public ResultMap(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResultMap<T> success(){
        this.result = "success";
        return this;
    }

    public ResultMap<T> fail(){
        this.result = "fail";
        return this;
    }

    public ResultMap<T> message(String message){
        this.message = message;
        return this;
    }

}
