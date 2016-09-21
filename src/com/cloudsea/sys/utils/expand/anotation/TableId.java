package com.cloudsea.sys.utils.expand.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;	
import java.lang.annotation.RetentionPolicy;

import com.cloudsea.sys.utils.expand.model.PrimaryKeyTypeEnum;



/**
 * @author zhangxiaorong
 * 2014-3-26
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface TableId {
	public String idName() default "";
	public String sequenceName();
	public PrimaryKeyTypeEnum pkType() default PrimaryKeyTypeEnum.AUTO;
}
