package com.example.demo.dubbo.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.example.demo.dubbo.utils.TraceIDUtils;
import org.slf4j.MDC;

/**
 * 类名：TraceIDFilter
 * 功能：自定义日志拦截器
 * 描述：每次请求时生成唯一的TraceID（使用UUID生成的），
 * 创建人：typ
 * 创建时间：2018/11/13
 * 修改人：
 * 修改描述：
 * 修改时间：
 */
@Activate()
public class TraceIDFilter implements Filter {

	private static final String TRACE_ID = "TRACE_ID";

    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
		Result result = null;
    	try{
			if(inv.getAttachment(TRACE_ID)!=null){
				TraceIDUtils.setTraceId(inv.getAttachment(TRACE_ID));
				String mdcData = String.format("[TraceID:%s]", inv.getAttachment(TRACE_ID));
				MDC.put("mdcData", mdcData);
			}else if(TraceIDUtils.getTraceId()!=null){
				inv.getAttachments().put("TRACE_ID", TraceIDUtils.getTraceId());
			}
			result = invoker.invoke(inv);
		}catch (RpcException e) {
    		e.printStackTrace();
		}finally {
			inv.getAttachments().clear();
			TraceIDUtils.clear();
		}
        return result;
    }
}

