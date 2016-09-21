package com.itany.frame.spring.aop.proxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

/**
 * cglib对象创建工厂
 *
 * @author 15041997
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CglibFactory {
	
	Class<?> clazz;
	Callback callback;
	Callback[] callbacks;
	CallbackFilter callbackFilter;
	
	
	public CglibFactory(Class<?> clazz, Callback[] callbacks) {
		super();
		this.clazz = clazz;
		this.callbacks = callbacks;
	}


	public CglibFactory(Class<?> clazz, Callback[] callbacks,
			CallbackFilter callbackFilter) {
		super();
		this.clazz = clazz;
		this.callbacks = callbacks;
		this.callbackFilter = callbackFilter;
	}

	public CglibFactory(Class<?> clazz, Callback callback) {
		super();
		this.clazz = clazz;
		this.callback = callback;
	}


	@SuppressWarnings("unchecked")
    public <T> T createCglibInstance() {
		Enhancer en = new Enhancer();
		en.setSuperclass(clazz);
		en.setCallback(callback);
		return (T) en.create();
	}

	
	@SuppressWarnings("unchecked")
    public <T> T createCglibInstanceWithFilter() {
//		new CallbackFilter(){
//			
//			@Override
//			public int accept(Method arg0) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//			
//		};
//		Callback callback = NoOp.INSTANCE;
//		
		Enhancer en = new Enhancer();
		en.setSuperclass(clazz);
		en.setCallbacks(callbacks);
		en.setCallbackFilter(callbackFilter);
		return (T) en.create();
	}

}
