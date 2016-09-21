package spring;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import com.itany.frame.spring.aop.definition.AopConfigDefinitionReader;
import com.itany.frame.spring.aop.definition.AopConfigDefinition;

public class TestAopConfig {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException, DocumentException {
        URL url = TestIoc.class.getClassLoader().getResource("spring-common.xml"); 
		List<AopConfigDefinition> list = new ArrayList<AopConfigDefinition>();
		AopConfigDefinitionReader.readXmlBean(url, list);
		System.out.println(list);
	}

}
