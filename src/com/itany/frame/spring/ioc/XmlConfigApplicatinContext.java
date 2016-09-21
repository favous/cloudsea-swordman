package com.itany.frame.spring.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javassist.Modifier;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import com.itany.frame.utils.JavaAssistantUtil;
import com.itany.frame.utils.StringUtil;

public class XmlConfigApplicatinContext extends GenericApplicationContext {
	
	public static XmlConfigApplicatinContext context;
    
	private XmlConfigApplicatinContext(URL url) throws Exception {
		List<BeanDefinition> beanDefineList = XmlConfigApplicatinContext.readBean(url);
		super.getBeanDefineList().addAll(beanDefineList);
		instanceBeans(super.getBeanDefineList(), super.getSingletonMap());
		injectProperties(super.getBeanDefineList(), super.getSingletonMap());
	}
    
    public static XmlConfigApplicatinContext init(URL url) throws Exception{
    	if (context == null){
    		synchronized (XmlConfigApplicatinContext.class) {
    	    	if (context == null){    	    		
    	    		context = new XmlConfigApplicatinContext(url);
    	    	}
			}
    	}
    	return context;
    }
    
    public static XmlConfigApplicatinContext getContext() throws Exception{
    	if (context == null){
    		throw new Exception("容器还没有被初始化");
    	}
    	return context;
    }
    
    public static Object getBean(String id) throws Exception{
    	return context.getSingletonMap().get(id);
    }


	private static List<BeanDefinition> readBean(URL url) throws DocumentException{  
    	
    	List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>();
        
        //读取文档的内容，得到一个document  
    	SAXReader saxReader = new SAXReader();  
    	Document document = saxReader.read(url);   
        
    	//指定命名空间
        Map<String,String> map = new HashMap<String,String>();  
        map.put("default", "http://www.springframework.org/schema/beans");  
        
		// 创建beans/bean查询路径 。其中default是默认前缀
		XPath xPath = document.createXPath("/default:beans/default:bean");

		// 设置命名空间
		xPath.setNamespaceURIs(map);
        
        //获取文档下所有bean节点  
        @SuppressWarnings("unchecked")  
        List<Element> elements = xPath.selectNodes(document); 
        
        for(Element element : elements){
        	
            //获取id属性值  
            String id = element.attributeValue("id");  
            
            //获取class全路径名
            String classPath = element.attributeValue("class"); 
            
            BeanDefinition beanDefinition = new BeanDefinition(id, classPath);  
            
            @SuppressWarnings("unchecked")  
            List<Element> propertys = element.elements("property");  
			if (propertys != null) {
                List<PropertyDefinition> list = new ArrayList<PropertyDefinition>();  
				for (Element property : propertys) {
					PropertyDefinition propertyDefinition = readProperty(property);
					list.add(propertyDefinition);
				}
				beanDefinition.setPropertyList(list);
            }  
            beanDefines.add(beanDefinition);  
            
            @SuppressWarnings("unchecked")  
            List<Element> constructorArgs = element.elements("constructor-arg");  
			if (constructorArgs != null) {
				List<PropertyDefinition> list = new ArrayList<PropertyDefinition>();  
				for (Element property : constructorArgs) {
					PropertyDefinition propertyDefinition = readProperty(property);
					list.add(propertyDefinition);
				}
				beanDefinition.setConstructorArgList(list);
			}
        }
		return beanDefines; 
    }

    private static PropertyDefinition readProperty(Element element) {

    	String name = element.attributeValue("name");
    	
		String value = element.attributeValue("value");
		if (value != null){	
			return new PropertyDefinition(name, value, false);
		} 
		
		Element valueElement = element.element("value");
		if (valueElement != null){
			String text = valueElement.getText();
			return new PropertyDefinition(name, text, false);
		}
		
		String ref = element.attributeValue("ref");
		if (ref != null){
			return new PropertyDefinition(name, ref, true);
		}
		
		Element refElement = element.element("ref");
		if (refElement != null){
			String text = refElement.attributeValue("bean");
			return new PropertyDefinition(name, text, true);
		}
		
		@SuppressWarnings("unchecked")
		List<Element> listElements = element.elements("list");
		if (listElements != null){
			List<PropertyDefinition> list = new ArrayList<PropertyDefinition>();
			for (Element e : listElements){
				PropertyDefinition pd = readProperty(e);
				list.add(pd);
			}
			return new PropertyDefinition(name, list);
		}
		
		@SuppressWarnings("unchecked")
		List<Element> mapElements = element.elements("map");
		if (mapElements != null){
			Map<String, PropertyDefinition> map = new HashMap<String, PropertyDefinition>();
			for (Element e : mapElements){
				String key = element.attributeValue("key");
				PropertyDefinition pd = readProperty(e);
				map.put(key, pd);
			}
			return new PropertyDefinition(name, map);
		}

    	String textValue = element.getText();
		if (textValue != null){	
			return new PropertyDefinition(name, textValue, false);
		} 
		
		return null;
	}
    
    
    /** 
     * 初始化所有的bean,放入singletonMap中. 
     */  
    private void instanceBeans(List<BeanDefinition> beanDefineList, Map<String, Object> singletonMap){  
    	try {
    		for (BeanDefinition beanDefinition : beanDefineList) {
    			List<PropertyDefinition> constructorArgList = beanDefinition.getConstructorArgList();
    			String className = beanDefinition.getClassName();
    			String id = beanDefinition.getId();
    			
    			if (className == null || className.length() <= 0) {
    				continue;
    			}
    			if (constructorArgList.size() > 0){
    				continue;
    			}
    			if (id == null || id.length() == 0){
    				id = "classFullName:" + className;
    			}
    			
    			Class<?> clazz = Class.forName(className);
    			Object instance = clazz.newInstance();
    			singletonMap.put(id, instance);
    		}
    		
    		for (BeanDefinition beanDefinition : beanDefineList) {
    			List<PropertyDefinition> constructorArgList = beanDefinition.getConstructorArgList();
    			String className = beanDefinition.getClassName();
    			String id = beanDefinition.getId();
    			
    			if (className == null || className.length() <= 0) {
    				continue;
    			}
    			if (constructorArgList.size() <= 0){
    				continue;
    			}
    			if (id == null || id.length() == 0){
    				id = "classFullName:" + className;
    			}
    			
    			Class<?> clazz = Class.forName(className);
    			Constructor<?>[] constructons = clazz.getConstructors();
    			int argLength = constructorArgList.size();
    			Map<Constructor<?>, Object[]> matchConstructorMap = new HashMap<Constructor<?>, Object[]>();
    			boolean stepOut = false;
    			
    			for (Constructor<?> constructor : constructons){
    				Type[] types = constructor.getGenericParameterTypes();
    				if (types.length != argLength){
    					continue;
    				}
    				String[] names = JavaAssistantUtil.getConstructorParamNames(clazz, constructor);
    				Object[] parames = new Object[argLength];
    				for (int i = 0; i < constructorArgList.size(); i ++){
    					String configName = constructorArgList.get(i).getName();
    					if (configName != null && !"".equals(configName.trim())){
    						if (!configName.equals(names[i])){
    							stepOut = true;
    							break;
    						}
    					}
    					Type type = types[i];
                    	boolean parameterized = ParameterizedType.class.isAssignableFrom(type.getClass());
                    	parames[i] = buildBeanByDefinition(singletonMap, constructorArgList.get(i), type, parameterized);
    				}
    				if (stepOut){
    					continue;
    				}
    				matchConstructorMap.put(constructor, parames); 
    			}
    			
    			if (matchConstructorMap.size() == 0){
    				throw new Exception("从配置文件中没有找到匹配的构造方法");
    			}
    			
    			if (matchConstructorMap.size() > 1){
    				throw new Exception("从配置文件中找到" + matchConstructorMap.size() + "个匹配的构造方法");
    			}
    			
    			Entry<Constructor<?>, Object[]> entry = matchConstructorMap.entrySet().iterator().next();
    			Object instance = entry.getKey().newInstance(entry.getValue());
    			singletonMap.put(id, instance);
    		}
    			
    	} catch (Exception e) {
    		throw new RuntimeException("实例化容器bean时异常", e);
    	}
    } 
    
    private void injectProperties(List<BeanDefinition> beanDefineList, Map<String, Object> singletonMap) throws Exception{
    	
    	for(BeanDefinition beanDefinition : beanDefineList){  
            Object bean = singletonMap.get(beanDefinition.getId());  
            if(bean != null){  
            	Field[] fields = bean.getClass().getDeclaredFields();
                for (PropertyDefinition property : beanDefinition.getPropertyList()){ 
                    for(Field field : fields){  
                        if(property.getName().equals(field.getName())){ 
                        	Type type = null;
                        	boolean parameterized = true;
                        	try {
								type = (ParameterizedType)field.getGenericType();
							} catch (Exception e) {
								parameterized = false;
								type = field.getType();
							}
                        	Object obj = buildBeanByDefinition(singletonMap, property, type, parameterized);
                        	field.setAccessible(true);
                        	field.set(bean, obj);  
                        }  
                    }  
                }  
            }  
        }  
    }
    
    /**
     * 
     * @param singletonMap	存放容器加载的bean对象
     * @param property		对象属性的描述
     * @param type			对象属性的类型			field.getGenericType()或field.getGenericType()
     * @param parameterized	对象属性是否带泛型	
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	private Object buildBeanByDefinition(Map<String, Object> singletonMap, PropertyDefinition property, 
    		Type type, boolean parameterized) throws Exception{
    	
    	Object obj = null;
    	Class<?> clazz = null;
    	Type[] actualTypes = null;
    	
    	if (parameterized){    		
    		ParameterizedType parameterizedType = (ParameterizedType)type;
    		clazz = (Class<?>) parameterizedType.getRawType();
    		actualTypes = parameterizedType.getActualTypeArguments();  
    	} else if (type != null){
        	clazz = (Class<?>) type;
    	} 

		String value = property.getValue();
    	String ref = property.getRef();
    	List<PropertyDefinition> list = property.getList();
    	Map<String, PropertyDefinition> map = property.getMap();
    	
    	if (value != null && !"".equals(value.trim())){
    		obj = StringUtil.parseStringByType(value, clazz);
    		
    	} else if (ref != null && !"".equals(ref.trim())){
    		obj = singletonMap.get(property.getRef());  
    		
    	} else if (clazz != null && list != null && !list.isEmpty()){
    		List<Object> l = null;
    		if (clazz == List.class || clazz == AbstractList.class){
    			l = new ArrayList<Object>();
    		} else if (clazz == AbstractSequentialList.class){
    			l = new LinkedList<Object>();
    		} else if (List.class.isAssignableFrom(clazz)){
    			l = (List<Object>) clazz.getConstructor().newInstance();
    		} else {
    			throw new Exception("不能创建全路径名为" + clazz.getName() + "的集合对象");
    		}
    		for (PropertyDefinition definition : list){
    			Object object = null;
    			try {
    				object = buildBeanByDefinition(singletonMap, definition, (ParameterizedType) actualTypes[0], true);
				} catch (Exception e) {
					object = buildBeanByDefinition(singletonMap, definition, actualTypes[0], false);
				}
    			l.add(object);
    		}
    		obj = l;
    		
    	} else if (clazz != null && map != null && !map.isEmpty()){
    		Map<String, Object> m = null;
    		if (clazz == Map.class || clazz == AbstractMap.class){
    			m = new HashMap<String, Object>();
    		} else if (!clazz.isInterface() && List.class.isAssignableFrom(clazz) && !Modifier.isAbstract(AbstractList.class.getModifiers())){
    			m = (Map<String, Object>) clazz.getConstructor().newInstance();
    		} else {
    			throw new Exception("不能创建全路径名为" + clazz.getName() + "的集合对象");
    		}
			for (Entry<String, PropertyDefinition> entry : map.entrySet()){
				Object object = null;
				try {
					object = buildBeanByDefinition(singletonMap, entry.getValue(), (ParameterizedType) actualTypes[1], true);
				} catch (Exception e) {
					object = buildBeanByDefinition(singletonMap, entry.getValue(), actualTypes[0], false);
				}
				m.put(entry.getKey(), object);
			}
			obj = m;
    	}
		return obj;
    }



}
