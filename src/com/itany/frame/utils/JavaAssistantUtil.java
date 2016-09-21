package com.itany.frame.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;


public class JavaAssistantUtil {

	public static String[] getMethodParamNames(Class<?> clazz, String methodName) throws NotFoundException {
		
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = pool.get(clazz.getName());
		CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
		
		MethodInfo methodInfo = ctMethod.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute localVariableAttr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		
		if (localVariableAttr == null) {
			throw new NotFoundException("");
		}
		
		String[] paramNames = new String[ctMethod.getParameterTypes().length];
		int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
		
		for (int i = 0; i < paramNames.length; i++)
			paramNames[i] = localVariableAttr.variableName(i + pos);
		
		return paramNames;
	}
	
	public static String[] getConstructorParamNames(Class<?> clazz, Constructor<?> constructor) throws NotFoundException {
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		CtClass[] ctClasses = new CtClass[parameterTypes.length];
		ClassPool pool = ClassPool.getDefault();
		
		for (int i = 0; i < parameterTypes.length; i++){
			ctClasses[i] = pool.get(parameterTypes[i].getName());
		}
		
		CtClass ctClass = pool.get(clazz.getName());
		CtConstructor ctConstructor = ctClass.getDeclaredConstructor(ctClasses);
		MethodInfo methodInfo = ctConstructor.getMethodInfo2();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute localVariableAttr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		
		if (localVariableAttr == null) {
			throw new NotFoundException("");
		}
		
		String[] paramNames = new String[ctConstructor.getParameterTypes().length];
		int pos = Modifier.isStatic(ctConstructor.getModifiers()) ? 0 : 1;
		
		for (int i = 0; i < paramNames.length; i++)
			paramNames[i] = localVariableAttr.variableName(i + pos);
		
		return paramNames;
	}

	
	public static List<String[]> getConstructorNames(Class<?> clazz, String methodName) throws NotFoundException {
		
		List<String[]> list = new ArrayList<String[]>();
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = pool.get(clazz.getName());
		CtConstructor[] ctConstructors = ctClass.getDeclaredConstructors();
		
		for (CtConstructor ctConstructor : ctConstructors){
			MethodInfo methodInfo = ctConstructor.getMethodInfo2();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute localVariableAttr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
			
			if (localVariableAttr == null) {
				throw new NotFoundException("");
			}
			
			String[] paramNames = new String[ctConstructor.getParameterTypes().length];
			int pos = Modifier.isStatic(ctConstructor.getModifiers()) ? 0 : 1;
			
			for (int i = 0; i < paramNames.length; i++)
				paramNames[i] = localVariableAttr.variableName(i + pos);
			
			list.add(paramNames);
		}
		
		return list;
	}
	
	

}
