package com.itany.frame.spring.aop.definition;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import com.itany.frame.spring.aop.definition.aspect.AopAfter;
import com.itany.frame.spring.aop.definition.aspect.AopAfterReturn;
import com.itany.frame.spring.aop.definition.aspect.AopAfterThrow;
import com.itany.frame.spring.aop.definition.aspect.AopAround;
import com.itany.frame.spring.aop.definition.aspect.AopBefore;
import com.itany.frame.spring.aop.definition.aspect.AopByLoc;
import com.itany.frame.spring.aop.definition.aspect.AopDeclareParents;


public class AopConfigDefinitionReader {
	
	
	@SuppressWarnings("unchecked")
	public static void readXmlBean(URL url, List<AopConfigDefinition> configDefinitionList) throws DocumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
				
		SAXReader saxReader = new SAXReader();  
    	Document document = saxReader.read(url);   
        
    	//指定命名空间
        Map<String,String> map = new HashMap<String,String>();  
        map.put("default", "http://www.springframework.org/schema/beans");  
        map.put("aop", "http://www.springframework.org/schema/aop");  
        
		// 创建beans/aop:config查询路径 。
		XPath xPath = document.createXPath("/default:beans/aop:config");

		// 设置命名空间
		xPath.setNamespaceURIs(map);
        
        //获取文档下所有aop:config节点  
        List<Element> elements = xPath.selectNodes(document); 
        
        for(Element element : elements){
            
        	AopConfigDefinition configDefinition = new AopConfigDefinition();  
        	List<AopPointcut> pointcutList = configDefinition.getPointcutList();
        	List<AopAdvisor> advisorList = configDefinition.getAdvisorList();
        	List<AopAspect> aspectList = configDefinition.getAspectList();
        	
            List<Element> pointcuts = element.elements("pointcut");  
			if (pointcuts != null) {
				for (Element property : pointcuts) {
					AopPointcut aopPointcut = readPointcut(property);
					pointcutList.add(aopPointcut);
				}
            }  
        	
            List<Element> advisors = element.elements("advisor");  
			if (advisors != null) {
				for (Element property : advisors) {
					AopAdvisor advisor = readAdvisors(property);
					advisorList.add(advisor);
				}
            }  
        	
            List<Element> aspects = element.elements("aspect");  
			if (aspects != null) {
				for (Element property : aspects) {
					AopAspect aspect = readAspects(property);
					aspectList.add(aspect);
				}
            }  
			
			configDefinitionList.add(configDefinition);
        }
	}

	private static AopPointcut readPointcut(Element element) {
    	String id = element.attributeValue("id");
    	String expression = element.attributeValue("expression");
    	AopPointcut aopPointcut = new AopPointcut();
    	aopPointcut.setId(id);
    	aopPointcut.setExpression(expression);
		return aopPointcut;
	}
	
	private static AopAdvisor readAdvisors(Element element) {
		AopAdvisor advisor = new AopAdvisor();
    	String id = element.attributeValue("id");
    	String adviceRef = element.attributeValue("advice-ref");
    	String order = element.attributeValue("order");
    	String pointcut = element.attributeValue("pointcut");
    	String pointcutRef = element.attributeValue("pointcut-ref");
    	advisor.setAdviceRef(adviceRef);
    	advisor.setId(id);
    	advisor.setOrder(order);
    	advisor.setPointcut(pointcut);
    	advisor.setPointcutRef(pointcutRef);
		return advisor;
	}
	
	@SuppressWarnings("unchecked")
	private static AopAspect readAspects(Element element) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		
		AopAspect aspect = new AopAspect();
    	String id = element.attributeValue("id");
    	String order = element.attributeValue("order");
    	String ref = element.attributeValue("ref");
    	
        List<Element> afterReturns = element.elements("aop:after-returning");  
    	List<AopAfterReturn> afterReturnList = new ArrayList<AopAfterReturn>();
		if (afterReturns != null) {
			for (Element e : afterReturns) {
				AopAfterReturn aopByLoc = readAopByLoc(e, AopAfterReturn.class);				
				afterReturnList.add(aopByLoc);
			}
        }  
    	
        List<Element> afters = element.elements("after");  
    	List<AopAfter> afterList = new ArrayList<AopAfter>();
		if (afterList != null) {
			for (Element e : afters) {
				AopAfter aopByLoc = readAopByLoc(e, AopAfter.class);				
				afterList.add(aopByLoc);
			}
        }  
    	
        List<Element> afterThrows = element.elements("after-throwing");  
    	List<AopAfterThrow> afterThrowList = new ArrayList<AopAfterThrow>();
		if (afterThrows != null) {
			for (Element e : afterThrows) {
				AopAfterThrow aopByLoc = readAopByLoc(e, AopAfterThrow.class);				
				afterThrowList.add(aopByLoc);
			}
        }  
    	
        List<Element> arounds = element.elements("around");  
    	List<AopAround> aroundList = new ArrayList<AopAround>();
		if (arounds != null) {
			for (Element e : arounds) {
				AopAround aopByLoc = readAopByLoc(e, AopAround.class);				
				aroundList.add(aopByLoc);
			}
        }  
    	
        List<Element> befores = element.elements("before");  
    	List<AopBefore> beforeList = new ArrayList<AopBefore>();
		if (befores != null) {
			for (Element e : befores) {
				AopBefore aopByLoc = readAopByLoc(e, AopBefore.class);				
				beforeList.add(aopByLoc);
			}
        }  
    	
        List<Element> declareParentss = element.elements("declare-parents");  
    	List<AopDeclareParents> declareParentsList = new ArrayList<AopDeclareParents>();
		if (declareParentss != null) {
			for (Element e : declareParentss) {
				AopDeclareParents declareParents = new AopDeclareParents();
				declareParents.setImplementInterface(e.attributeValue("implement-interface"));
				declareParents.setTypesMatching(e.attributeValue("types-matching"));				
				declareParentsList.add(declareParents);
			}
		}
		
        List<Element> pointcuts = element.elements("pointcut");  
    	List<AopPointcut> pointcutList = new ArrayList<AopPointcut>();
		if (pointcuts != null) {
			for (Element e : pointcuts) {
				AopPointcut pointcut = readPointcut(e);				
				pointcutList.add(pointcut);
			}
		}
    	
    	aspect.setId(id);
    	aspect.setOrder(order);
    	aspect.setRef(ref);
		aspect.setAfterReturnList(afterReturnList);
		aspect.setAfterThrowList(afterThrowList);
		aspect.setAfterList(afterList);
		aspect.setAroundList(aroundList);
		aspect.setBeforeList(beforeList);
		aspect.setDeclareParentsList(declareParentsList);
		aspect.setPointcutList(pointcutList);
		
	return aspect;
	}

	private static <T> T readAopByLoc(Element e, Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		T t = clazz.newInstance();
		AopByLoc aopByLoc = new AopByLoc();
		aopByLoc.setArgNames(e.attributeValue("arg-names"));
		aopByLoc.setMethod(e.attributeValue("method"));
		aopByLoc.setPointcut(e.attributeValue("pointcut"));
		aopByLoc.setPointcutRef(e.attributeValue("pointcut-ref"));
		BeanUtils.copyProperties(t, aopByLoc);
		return t;
	}

}
