package com.huzhiyi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: RDEQueryMethod
 * @Description: TODO(√Ë ˆ¿‡)
 *               <p>
 * @author willter
 * @date Jul 25, 2010
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RDEQueryMethod {
	public String queryName() default "";

	public String findType() default "";

	public String paramters() default "";

	public String order() default "";
}
