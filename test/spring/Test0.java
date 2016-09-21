package spring;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;

import com.itany.frame.spring.ioc.PropertyDefinition;
import com.itany.frame.spring.ioc.XmlConfigApplicatinContext;

public class Test0 {

	public static void main(String[] args) throws Exception {
		
		System.out.println(ParameterizedType.class);
		
		Constructor<?>[] cons = PropertyDefinition.class.getConstructors();
		for (Constructor<?> constructor : cons){
			Type[] types = constructor.getGenericParameterTypes();
			for (Type type : types){
				System.out.println(ParameterizedType.class.isAssignableFrom(type.getClass()));
				System.out.println(type);
			}
		}
	}
}
