package com.example.demo.dubbo.utils;


/**
 * 类名：
 * 功能：自定义日志拦截器
 * 描述：
 * 创建人：typ
 * 创建时间：2018/11/13
 * 修改人：
 * 修改描述：
 * 修改时间：
 */
public class TraceIDUtils {

	private static final ThreadLocal<String> traceid = new ThreadLocal<String>();

	public static String getTraceId() {
		return traceid.get();
	}

	public static void setTraceId(String traceId) {
		traceid.set(traceId);
	}

	public static void clear(){
		traceid.remove();
	}
}
