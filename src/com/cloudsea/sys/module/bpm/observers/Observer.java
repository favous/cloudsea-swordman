/**
 * 
 */
package com.cloudsea.sys.module.bpm.observers;

import com.cloudsea.sys.module.bpm.EntityInvocation;


/**
 * @author zhangxiaorong
 * 2014-5-16
 */
public interface Observer {
	
	void action(EntityInvocation entityInvocation);


}
