package com.huzhiyi.comm;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import com.huzhiyi.json.SimpleJsonArray;
import com.huzhiyi.json.SimpleJsonObject;
import com.huzhiyi.util.BeanUtils;

/**
 * 
 * @ClassName: AOPLogBean
 * @Description: TODO(������)
 *               <p>
 * @author willter
 * @date Jul 25, 2010
 * @version V1.0
 * 
 */
public class AOPLogBean {

	/**
	 * ��־����
	 */
	private static Logger log = Logger.getLogger(AOPLogBean.class);

	/**
	 * ��������
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	public Object intercept(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();

		Object returnValue = null;
		Class returnType = null;
		try {
			returnValue = joinPoint.proceed();
			if (null != returnValue) {
				returnType = returnValue.getClass();	
			}
		} catch (Throwable e) {
			log.error(e);
			throw e;
		}

		if (log.isDebugEnabled()) {
			Object target = joinPoint.getTarget();
			Object[] args = joinPoint.getArgs();
			StringBuilder sb = new StringBuilder("ִ�з���:");
			sb.append(target.getClass().getName());
			sb.append(".");
			sb.append(methodName);
			sb.append("(");
			SimpleJsonArray.toJsonString(args, sb);
			sb.append(")");
			sb.append("������ֵ:");
			if (null != returnType && BeanUtils.isCollection(returnType)) {
				sb.append(returnType.getName());
				sb.append(",size=");
				sb.append(returnType.getMethod("size").invoke(returnValue));
			} else {
				SimpleJsonObject.toJsonString(returnValue, sb);
			}

			sb.append("��");
			log.debug(sb);
		}

		return returnValue;
	}
}
