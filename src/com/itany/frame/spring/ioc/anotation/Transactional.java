/**
 * 
 */
package com.itany.frame.spring.ioc.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.itany.frame.spring.tx.enums.Isolation;
import com.itany.frame.spring.tx.enums.Propagation;


/**
 * @author zhangxiaorong
 * 2014-3-26
 */



@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface Transactional {

	  public String value() default "";
	  
	  public Propagation propagation() default Propagation.REQUIRED;
	  
	  public Isolation isolation() default Isolation.DEFAULT;
	  
	  public abstract int timeout() default (int) -1;
	  
	  public abstract boolean readOnly() default false;
	  
	  public abstract Class<?>[] rollbackFor() default {};
	  
	  public abstract java.lang.String[] rollbackForClassName() default {};
	  
	  public abstract java.lang.Class<?>[] noRollbackFor() default {};
	  
	  public abstract java.lang.String[] noRollbackForClassName() default {};

}
