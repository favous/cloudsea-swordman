package com.cloudsea.sys.utils.expand.anotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;	
import java.lang.annotation.RetentionPolicy;

/**
 * @author zhangxiaorong
 * 2014-3-26
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.FIELD})
public @interface Instant {
	
}
