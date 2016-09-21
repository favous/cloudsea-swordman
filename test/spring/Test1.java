package spring;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SignatureAttribute;

import com.itany.frame.spring.ioc.AnotationConfigApplicatinContext;
import com.itany.frame.spring.ioc.GenericApplicationContext;
import com.itany.frame.spring.ioc.XmlConfigApplicatinContext;

public class Test1 {

	List<Class> l = new ArrayList();
    private Map<String, Map<String, Object>> aaaa = new HashMap<String, Map<String, Object>>();
	public static void main(String[] args) throws Exception {
		Type type = Test1.class.getDeclaredField("l").getGenericType();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type[] types = parameterizedType.getActualTypeArguments();  
		ParameterizedType parameterizedType2 = (ParameterizedType) types[1];
	    Type[] types2 = parameterizedType2.getActualTypeArguments();
		System.out.println(types2[0]);;
		
		Type getRawType = parameterizedType.getRawType();
		String gbname = ((Class)getRawType).getName();

        
		Class clazz = XmlConfigApplicatinContext.class;
		Class clazz2 = AnotationConfigApplicatinContext.class;

		try {
			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.get(clazz.getName());

			CtMethod cm = cc.getDeclaredMethod("readProperty");

			MethodInfo methodInfo = cm.getMethodInfo();

			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
					.getAttribute(LocalVariableAttribute.tag);
			if (attr == null) {
				// exception
			}
			String[] paramNames = new String[cm.getParameterTypes().length];
			int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
			for (int i = 0; i < paramNames.length; i++)
				paramNames[i] = attr.variableName(i + pos);

			for (int i = 0; i < paramNames.length; i++) {
				System.out.println(paramNames[i]);
			}
			
			String getSignature = cm.getSignature();
			CtClass getReturnType = cm.getReturnType();
			CtClass[] getParameterTypes = cm.getParameterTypes();
			Object[][] getParameterAnnotations = cm.getParameterAnnotations();
			String getName = cm.getName();
			String getLongName = cm.getLongName();
			CtClass[] getExceptionTypes = cm.getExceptionTypes();
			Object[] getAnnotations = cm.getAnnotations();
			Object[] getAvailableAnnotations = cm.getAvailableAnnotations();
			Class<? extends CtMethod> getClass = cm.getClass();
			CtClass getDeclaringClass = cm.getDeclaringClass();
			System.out.println("=====");
			
			CtField[] ctfeilds = pool.get(clazz.getName()).getDeclaredFields();
			CtField ctfeild =  pool.get(GenericApplicationContext.class.getName()).getDeclaredField("singletonMap");
			Object getConstantValue = ctfeild.getConstantValue();
			Class<? extends CtField> getClass2 = ctfeild.getClass();
			CtClass getDeclaringClass2 = ctfeild.getDeclaringClass();
			CtClass getType = ctfeild.getType();
			
			FieldInfo getFieldInfo = ctfeild.getFieldInfo();
			AttributeInfo getAttribute = getFieldInfo.getAttribute("");
			List getAttributes = getFieldInfo.getAttributes();
			for (Object o  :  getAttributes){
				SignatureAttribute SignatureAttribute = (javassist.bytecode.SignatureAttribute) o;
				System.out.println(SignatureAttribute.getSignature());
				System.out.println(SignatureAttribute.getName());
				LocalVariableAttribute ab = (LocalVariableAttribute) o;
				int i = Modifier.isStatic(ctfeild.getModifiers()) ? 0 : 1;
				String variableName = ab.variableName(i);
				int aaaa = 0;
			}
			
			FieldInfo getFieldInfo2 = ctfeild.getFieldInfo2();

		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.get(clazz2.getName());
			CtConstructor cm = cc.getDeclaredConstructors()[0];
			MethodInfo methodInfo = cm.getMethodInfo2();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
					.getAttribute(LocalVariableAttribute.tag);
			if (attr == null) {
				// exception
			}
			String[] paramNames = new String[cm.getParameterTypes().length];
			int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
			for (int i = 0; i < paramNames.length; i++)
				paramNames[i] = attr.variableName(i + pos);

			for (int i = 0; i < paramNames.length; i++) {
				System.out.println(paramNames[i]);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
