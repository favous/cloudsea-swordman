package com.itany.frame.spring.ioc;

import java.io.File;
import java.io.IOException;
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
import javassist.NotFoundException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import com.itany.frame.spring.aop.AopContext;
import com.itany.frame.spring.beans.InitializingBean;
import com.itany.frame.spring.ioc.anotation.Autowired;
import com.itany.frame.spring.ioc.anotation.Component;
import com.itany.frame.spring.ioc.anotation.Controller;
import com.itany.frame.spring.ioc.anotation.Repository;
import com.itany.frame.spring.ioc.anotation.Resource;
import com.itany.frame.spring.ioc.anotation.Service;
import com.itany.frame.utils.FileUtil;
import com.itany.frame.utils.JavaAssistantUtil;
import com.itany.frame.utils.StringUtil;

public class AnotationConfigApplicatinContext extends GenericApplicationContext {
	
	public static AnotationConfigApplicatinContext context;
    
	private AnotationConfigApplicatinContext(URL[] urls, File[] classTypeFiles) throws Exception {
		for (File file : classTypeFiles){			
			List<BeanDefinition> beanDefineList = new ArrayList<BeanDefinition>();
			AnotationConfigApplicatinContext.searchByAnotation(beanDefineList, file);
			super.getBeanDefineList().addAll(beanDefineList);
		}
		for (URL url : urls){
			List<BeanDefinition> beanDefineList = AnotationConfigApplicatinContext.readXmlBean(url);			
			super.getBeanDefineList().addAll(beanDefineList);
		}
		
		instanceBeans(this);
		
		AopContext aopContext = new AopContext(super.getSingletonMap(), urls);
		aopContext.addAopContext(this);
		aopContext.instanceProxyBeans(this);
		
		injectPropertiesByClass(this);
		injectPropertiesById(this);
	}

	public static AnotationConfigApplicatinContext init(URL[] urls, File[] files) throws Exception{
    	if (context == null){
    		synchronized (XmlConfigApplicatinContext.class) {
    	    	if (context == null){    	    		
    	    		context = new AnotationConfigApplicatinContext(urls, files);
    	    	}
			}
    	}
    	return context;
    }
    
    public static AnotationConfigApplicatinContext getContext() throws Exception{
    	if (context == null){
    		throw new RuntimeException("容器还没有被初始化");
    	}
    	return context;
    }
    
    public static Object getBean(String id) throws Exception{
    	return context.getSingletonMap().get(id);
    }

	private static void searchByAnotation(List<BeanDefinition> beanDefineList, File file) 
			throws IOException, ClassNotFoundException, NotFoundException {

		List<BeanDefinition> anotationBeanDefineList = new ArrayList<BeanDefinition>();
		
		if (file.isDirectory()){			
			for (File f : file.listFiles()){				
				searchByAnotation(anotationBeanDefineList, f);
			}
			
		} else if (file.getName().endsWith(".class")) {
			String classPath = FileUtil.getFullClassNameByFile(file);

			Class<?> cls = Class.forName(classPath);
			String beanId = null;
			
			/**
			 * 在类名上，查找bean的加载定义的注解
			 */
			Component component = (Component) cls.getAnnotation(Component.class);
			if (beanId == null){				
				if (component != null){
					beanId = component.value();
					if (beanId == null || "".equals(beanId.trim())){
						beanId = lowerCaseFirst(cls.getSimpleName());
					}
				}
			}
			if (beanId == null){
				Controller controller = (Controller) cls.getAnnotation(Controller.class);
				if (controller != null){
					beanId = controller.value();
					if (beanId == null || "".equals(beanId.trim())){
						beanId = lowerCaseFirst(cls.getSimpleName());
					}
				}
			}
			if (beanId == null){
				Service service = (Service) cls.getAnnotation(Service.class);
				if (service != null){
					beanId = service.value();
					if (beanId == null || "".equals(beanId.trim())){
						beanId = lowerCaseFirst(cls.getSimpleName());
					}
				}
			}
			if (beanId == null){
				Repository repository = (Repository) cls.getAnnotation(Repository.class);
				if (repository != null){
					beanId = repository.value();
					if (beanId == null || "".equals(beanId.trim())){
						beanId = lowerCaseFirst(cls.getSimpleName());
					}
				}
			}
			
			if (beanId == null){
				return;
			}

			BeanDefinition beanDefinition = new BeanDefinition(beanId, classPath);

			/**
			 * 在类的全局变量上，查找全局变量的加载依赖注解
			 */
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields){
				Resource resource = (Resource) field.getAnnotation(Resource.class);
				if (resource != null){
					String fieldName = field.getName();
					String refName = resource.name();
					if (refName == null || "".equals(refName.trim())){
						refName = lowerCaseFirst(fieldName);
					}
					PropertyDefinition propertyDefinition = new PropertyDefinition(fieldName, refName, true);
					beanDefinition.getPropertyList().add(propertyDefinition);
					
				} else {
					Autowired autoWired = (Autowired) field.getAnnotation(Autowired.class);
					if (autoWired != null){
						String fieldName = field.getName();
						Class<?> refClass = field.getType();
						PropertyClassRefDefinition propertyDefinition = new PropertyClassRefDefinition(fieldName, refClass);
						beanDefinition.getPropertyClassList().add(propertyDefinition);
					}
				}
			}
			
			/**
			 * 在类的构造方法上，查找构造方法参数的加载依赖注解，一个类只会取一个构造方法
			 */
			Constructor<?>[] constructors = cls.getDeclaredConstructors();
			for (Constructor<?> constructor : constructors){
				Autowired autoWired = (Autowired) constructor.getAnnotation(Autowired.class);
				if (autoWired == null){
					break;
				}
				Class<?>[] types = constructor.getParameterTypes();
				if (types.length == 0){
					break;
				}
				String[] names = JavaAssistantUtil.getConstructorParamNames(cls, constructor);
				for (int i = 0; i < types.length; i++){
					Class<?> type = types[i];
					PropertyClassRefDefinition propertyDefinition = new PropertyClassRefDefinition(names[i], type);
					beanDefinition.getConstructorClassRefArgList().add(propertyDefinition);
				}
				break;//只取一个，所以不能在一个以上的构造方法加上Autowired
			}
			
			anotationBeanDefineList.add(beanDefinition);
		}
		
		beanDefineList.addAll(anotationBeanDefineList);		
	}

	private static String lowerCaseFirst(String name) {
		String first = String.valueOf(Character.toLowerCase(name.charAt(0)));
		return first + name.substring(1);
	}

    @SuppressWarnings("unchecked")  
	private static List<BeanDefinition> readXmlBean(URL url) throws DocumentException{  
    	
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
        List<Element> elements = xPath.selectNodes(document); 
        
        for(Element element : elements){
        	
            //获取id属性值  
            String id = element.attributeValue("id");  
            
            //获取class全路径名
            String classPath = element.attributeValue("class"); 
            
            BeanDefinition beanDefinition = new BeanDefinition(id, classPath);  
            
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

    @SuppressWarnings("unchecked")  
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
		
		String ref = element.attributeValue("value-ref");
		if (ref != null){
			return new PropertyDefinition(name, ref, true);
		}
		
		ref = element.attributeValue("ref");
		if (ref != null){
			return new PropertyDefinition(name, ref, true);
		}
		
		String bean = element.attributeValue("bean");
		if (bean != null){
			return new PropertyDefinition(name, bean, true);
		}
		
		Element refElement = element.element("ref");
		if (refElement != null){
			String text = refElement.attributeValue("bean");
			return new PropertyDefinition(name, text, true);
		}
		
		List<Element> listElements = element.elements("list");
		if (listElements != null && !listElements.isEmpty()){
			List<PropertyDefinition> list = new ArrayList<PropertyDefinition>();
			List<Element> rowElements = listElements.get(0).elements();
			for (Element e : rowElements){
				PropertyDefinition pd = readProperty(e);
				list.add(pd);
			}
			return new PropertyDefinition(name, list);
		}
		
		List<Element> mapElements = element.elements("map");
		if (mapElements != null && !mapElements.isEmpty()){
			Map<String, PropertyDefinition> map = new HashMap<String, PropertyDefinition>();
			List<Element> entryElements = mapElements.get(0).elements("entry");
			for (Element e : entryElements){
				String key = e.attributeValue("key");
				PropertyDefinition pd = readProperty(e);
				pd.setName(key);
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
    private void instanceBeans(GenericApplicationContext genericApplicationContext){ 
    	try {
    		for (BeanDefinition beanDefinition : genericApplicationContext.getBeanDefineList()) {
    			instanceBean(beanDefinition, genericApplicationContext);
    		}
    	} catch (Exception e) {
    		throw new RuntimeException("实例化容器bean时异常", e);
    	}
    } 

    
    private void instanceBean(BeanDefinition beanDefinition, GenericApplicationContext context) throws Exception{ 
    	
    	Map<String, Object> singletonMap = context.getSingletonMap();
    	Map<Class<?>, Object> singletonClassMap = context.getSingletonClassMap();
    	

		/** 如果已经被读过（加载）*/
		if (beanDefinition.isRead()){
			return;
		} else {    				
			beanDefinition.setRead(true);
		}
		
		String className = beanDefinition.getClassName();
		String id = beanDefinition.getId();
		
		if (className == null || className.length() <= 0) {
			return;
		}
		
		if (id == null || id.length() == 0){
			int index = className.lastIndexOf(".");
			id = String.valueOf(Character.toUpperCase(className.charAt(index + 1)))
					+ className.substring(index + 1);
		}
		
		/**  重复定义bean id异常*/
		if (singletonMap.containsKey(id)){
			throw new RepeatDefineBeanIdException("重复定义bean,\r\n ,id:" + id + 
					"class:" + className + ", " + singletonMap.get(id.getClass().getName()));
		}

		Class<?> clazz = Class.forName(className);
		
		/**
		 * 以ID依赖有参构造参数
		 */
    	List<PropertyDefinition> constructorArgList = beanDefinition.getConstructorArgList();
		if (constructorArgList.size() > 0) {
			
			Constructor<?>[] constructons = clazz.getConstructors();
			int argLength = constructorArgList.size();
			Map<Constructor<?>, Object[]> matchConstructorMap = new HashMap<Constructor<?>, Object[]>();
			boolean stepOut = false;	//跳出本次循环标记
			
			/** 
			 * 判断Class中哪些构造方法匹配用户定义的构造方法，
			 * 有且只能匹配到一个
			 * 判断方式：参数个数，参数名称
			 */
			for (Constructor<?> constructor : constructons){
				Type[] types = constructor.getGenericParameterTypes();
				if (types.length != argLength){
					continue;	//参数个数不一致
				}
				String[] names = JavaAssistantUtil.getConstructorParamNames(clazz, constructor);
				Object[] parames = new Object[argLength];
				for (int i = 0; i < constructorArgList.size(); i ++){
					String configName = constructorArgList.get(i).getName();
					if (configName != null && !"".equals(configName.trim())){
						if (!configName.equals(names[i])){
							stepOut = true;
							break;	//参数名称不一致
						}
					}
					Type type = types[i];
	            	boolean parameterized = ParameterizedType.class.isAssignableFrom(type.getClass());
	            	parames[i] = getBeanByIdDefinition(context, constructorArgList.get(i), type, parameterized);
				}
				if (stepOut){
					continue;
				}
				matchConstructorMap.put(constructor, parames); 
			}
			
			if (matchConstructorMap.size() == 0){
				throw new RuntimeException("从用户定义中没有找到匹配的构造方法");
			}
			if (matchConstructorMap.size() > 1){
				throw new RuntimeException("从用户定义中找到" + matchConstructorMap.size() + "个匹配的构造方法");
			}
			
			Entry<Constructor<?>, Object[]> entry = matchConstructorMap.entrySet().iterator().next();
			Object instance = entry.getKey().newInstance(entry.getValue());
			
			pubInstance(id, clazz, instance, singletonMap, singletonClassMap);
			return;
		} 
		
		/**
		 * 以class依赖的有参构造参数
		 */
    	List<PropertyClassRefDefinition> constructorClassRefList = beanDefinition.getConstructorClassRefArgList();
		if (constructorClassRefList.size() > 0){
			
			Map<Constructor<?>, Object[]> matchConstructorMap = new HashMap<Constructor<?>, Object[]>();
			Constructor<?>[] constructons = clazz.getConstructors();
			int argLength = constructorClassRefList.size();
			
			/**
			 * 非id注入的情况下，有Autowired注解
			 */
			for (Constructor<?> constructor : constructons){
				if (constructor.getAnnotation(Autowired.class) != null){    					
					Object[] parames = new Object[argLength];
					for (int i = 0; i < constructorClassRefList.size(); i ++){
						parames[i] = getBeanByClassDefinition(context, constructorClassRefList.get(i));
					}
					matchConstructorMap.put(constructor, parames);
				}
			}
			
			if (matchConstructorMap.size() == 0){
				throw new RuntimeException("从用户定义中没有找到匹配的构造方法");
			}
			if (matchConstructorMap.size() > 1){
				throw new RuntimeException("从用户定义中找到" + matchConstructorMap.size() + "个匹配的构造方法");
			}
			
			Entry<Constructor<?>, Object[]> entry = matchConstructorMap.entrySet().iterator().next();
			Object instance = entry.getKey().newInstance(entry.getValue());
			
			pubInstance(id, clazz, instance, singletonMap, singletonClassMap);
			return;
		}
		
		/**
		 * 无参构造
		 */
		Object instance = clazz.newInstance();
		pubInstance(id, clazz, instance, singletonMap, singletonClassMap);
    }
    

	private void pubInstance(String id, Class<?> clazz, Object instance, Map<String, Object> singletonMap,
			Map<Class<?>, Object> singletonClassMap) throws Exception {
		
		//执行InitializingBean的afterPropertiesSet初始化方法
		if (InitializingBean.class.isAssignableFrom(clazz)){
			((InitializingBean)instance).afterPropertiesSet();
		}
		
		//如果是FactoryBean的实现类，id前加&对应instance，id对应instance.getObject()返回的对象,CLASS就取getObject()的CLASS
		if (FactoryBean.class.isAssignableFrom(clazz)){
			singletonMap.put("&" + id, instance);
			singletonClassMap.put(clazz, instance);
			clazz = clazz.getMethod("getObject").getReturnType();
			instance = ((FactoryBean<?>)instance).getObject();
		} 
		
		singletonMap.put(id, instance);
		singletonClassMap.put(clazz, instance);
	}

	private void injectPropertiesById(GenericApplicationContext context) throws Exception{
    	
		List<BeanDefinition> beanDefineList = context.getBeanDefineList(); 
		Map<String, Object> singletonMap = context.getSingletonMap();
		
    	for(BeanDefinition beanDefinition : beanDefineList){  
            Object bean = singletonMap.get(beanDefinition.getId());  
            if(bean == null){  
            	continue;
            }
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
            			Object obj = getBeanByIdDefinition(context, property, type, parameterized);
            			field.setAccessible(true);
            			field.set(bean, obj);  
            		}  
            	}  
            }  
        }  
    }

	private void injectPropertiesByClass(AnotationConfigApplicatinContext context) 
			throws Exception {
		
		Map<Class<?>, Object> singletonCassMap = context.getSingletonClassMap();
		List<BeanDefinition> beanDefineList = context.getBeanDefineList(); 
		
		for(BeanDefinition beanDefinition : beanDefineList){
			Class<?> clazz = Class.forName(beanDefinition.getClassName());
            Object bean = singletonCassMap.get(clazz);  
            if(bean == null){  
            	continue;
            }  
            Field[] fields = clazz.getDeclaredFields();
            for (PropertyClassRefDefinition property : beanDefinition.getPropertyClassList()){ 
            	for(Field field : fields){  
            		if(property.getName().equals(field.getName())){ 
            			
            			Object obj = getClassBeanFromProxy(property.getClassRef(), context.getProxyClassMap());
            			if (obj == null){
            				obj = getBeanByClassDefinition(context, property);
            			}
            			
            			field.setAccessible(true);
            			field.set(bean, obj);  
            		}  
            	}    
            }  
        }  
	}
    
    private Object getClassBeanFromProxy(Class<?> classRef,
			Map<Class<?>, Object> proxyClassMap) {
		
    	Object obj = proxyClassMap.get(classRef);  

		if (obj != null){
    		return obj;
    		
    	} else {    		
    		List<Class<?>> likeClasses = new ArrayList<Class<?>>();
    		for (Class<?> keyClass : proxyClassMap.keySet()){
    			if (classRef.isAssignableFrom(keyClass)){
    				likeClasses.add(keyClass);
    			}
    		}
    		if (likeClasses.size() == 0)
    			return null;
    		else if (likeClasses.size() == 1)
    			return proxyClassMap.get(likeClasses.get(0));
    		else
    			throw new RuntimeException(classRef.getName() + "子类或者接口实现类相符合的，找到不止一个对象");
    	}
	}

	private Object getBeanByClassDefinition(GenericApplicationContext context, 
    		PropertyClassRefDefinition property) throws Exception {
    	
    	Map<Class<?>, Object> singletonClassMap = context.getSingletonClassMap();
    	Class<?> refClass = property.getClassRef();
    	boolean contain = false;
    	
    	//查检singletonClassMap是否有key等于refClass或者是其子类或者实现类
    	for (Class<?> cls : singletonClassMap.keySet()){
    		if (refClass.isAssignableFrom(cls)){
    			contain = true;
    			break;
    		}
    	}
    	
		if (!contain){
			for (BeanDefinition define : context.getBeanDefineList()){
				Class<?> cls = Class.forName(define.getClassName());
				//如果是FactoryBean，就取其getObject方法的回返值
				if (FactoryBean.class.isAssignableFrom(cls)){
					cls = cls.getMethod("getObject").getReturnType();
				}
				//如果从BeanDefinition集合中找到对应refClass的，就用此BeanDefinition实例化
				if (refClass.isAssignableFrom(cls)){
					instanceBean(define, context);
					break;
				}
			}
		} 
		
		Object obj = singletonClassMap.get(refClass);  

		if (obj != null){
    		return obj;
    		
    	} else {    		
    		List<Class<?>> likeClasses = new ArrayList<Class<?>>();
    		for (Class<?> keyClass : singletonClassMap.keySet()){
    			if (refClass.isAssignableFrom(keyClass)){
    				likeClasses.add(keyClass);
    			}
    		}
    		if (likeClasses.size() == 0)
    			return null;
    		else if (likeClasses.size() == 1)
    			return singletonClassMap.get(likeClasses.get(0));
    		else
    			throw new RuntimeException(refClass.getName() + "子类或者接口实现类相符合的，找到不止一个对象");
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
	private Object getBeanByIdDefinition(GenericApplicationContext context, PropertyDefinition property, 
    		Type type, boolean parameterized) throws Exception{
    	
    	Object obj = null;
    	Class<?> clazz = null;
    	Type[] actualTypes = null;
    	Map<String, Object> singletonMap = context.getSingletonMap();
    	
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
    	
    	//如果是基本数据类型
    	if (value != null && !"".equals(value.trim())){
    		obj = StringUtil.parseStringByType(value, clazz);
    		
    	//如果是id引用类型
    	} else if (ref != null && !"".equals(ref.trim())){
    		String refKey = property.getRef();
    		if (!singletonMap.containsKey(refKey)){
    			for (BeanDefinition define : context.getBeanDefineList()){
    				if (ref.equals(define.getId())){
    					instanceBean(define, context);
    					break;
    				}
    			}
    		} 
    		
			obj = context.getProxyIdMap().get(property.getRef());
			if (obj == null){
				obj = singletonMap.get(property.getRef());  
			}
    		
//    		如果是list类型
    	} else if (clazz != null && list != null && !list.isEmpty()){
    		List<Object> l = null;
    		if (clazz == List.class || clazz == AbstractList.class){
    			l = new ArrayList<Object>();
    		} else if (clazz == AbstractSequentialList.class){
    			l = new LinkedList<Object>();
    		} else if (List.class.isAssignableFrom(clazz)){
    			l = (List<Object>) clazz.getConstructor().newInstance();
    		} else {
    			throw new RuntimeException("不能创建全路径名为" + clazz.getName() + "的集合对象");
    		}
    		for (PropertyDefinition definition : list){
    			Object object = null;
    			try {
    				object = getBeanByIdDefinition(context, definition, (ParameterizedType) actualTypes[0], true);
				} catch (Exception e) {
					object = getBeanByIdDefinition(context, definition, actualTypes[0], false);
				}
    			l.add(object);
    		}
    		obj = l;
    		
//    		如果是map引用类型
    	} else if (clazz != null && map != null && !map.isEmpty()){
    		Map<String, Object> m = null;
    		if (clazz == Map.class || clazz == AbstractMap.class){
    			m = new HashMap<String, Object>();
    		} else if (!clazz.isInterface() && List.class.isAssignableFrom(clazz) && !Modifier.isAbstract(AbstractList.class.getModifiers())){
    			m = (Map<String, Object>) clazz.getConstructor().newInstance();
    		} else {
    			throw new RuntimeException("不能创建全路径名为" + clazz.getName() + "的集合对象");
    		}
			for (Entry<String, PropertyDefinition> entry : map.entrySet()){
				Object object = null;
				try {
					object = getBeanByIdDefinition(context, entry.getValue(), (ParameterizedType) actualTypes[1], true);
				} catch (Exception e) {
					object = getBeanByIdDefinition(context, entry.getValue(), actualTypes[0], false);
				}
				m.put(entry.getKey(), object);
			}
			obj = m;
    	}
		return obj;
    }

}
