package com.example.demo.dubbo.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Properties;

/**
 * 路径：com.example.demo.dubbo.mybatis
 * 类名：SqlCostInterceptor.java
 * 功能：Sql执行时间记录拦截器
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/13
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
public class SqlCostInterceptor implements Interceptor {

    /**
     * 方法名：intercept
     * 功能：计算SQL执行时间
     * 描述：
     * 创建人：typ
     * 创建时间：2018/11/13
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long sqlCost = endTime - startTime;
            log.info("==> 执行SQL耗时：" + sqlCost + "ms");
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}