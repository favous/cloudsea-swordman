package spring;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

public class Test {

	public static void main(String[] args) throws DocumentException {
		
//    	//不需要命名空间时
//		SAXReader saxReader = new SAXReader(); 
//		URL url = Test.class.getResource("/book.xml");
//		Document document = saxReader.read(url); 
//		String xpathstr = "//title/@lang";  
//    	List<Element> list = document.selectNodes(xpathstr);

		
		//如果用无参saxReader构造多个对象，则他们的DocumentFactory属性是同一个
		SAXReader saxReader = new SAXReader(new DocumentFactory());  
    	Map map = new HashMap();
    	map.put("default", "http://www.eclipse.org/birt/2005/design"); // 加入命名空间
    	saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
    	URL url = Test.class.getResource("/book.xml");
    	Document document = saxReader.read(url);  
    	
    	String xpathstr = "//default:title/@lang";  
    	List<Element> list = document.selectNodes(xpathstr);
  	
    	
    	for (Object o : list){
    		if (Element.class.isAssignableFrom(o.getClass())){    			
    			Element e = (Element) o;
    			System.out.println(e.getName());
    			System.out.println(e.getPath());
    			System.out.println(e.getQualifiedName());
    			System.out.println(e.getText());
    			Namespace nspace = e.getNamespace();
    			Object data = e.getData();
    			QName qname = e.getQName();
    			System.out.println("==========");
    		} else if (Attribute.class.isAssignableFrom(o.getClass())){
    			Attribute a = (Attribute) o;
    			System.out.println(a.getName());
    			System.out.println(a.getPath());
    			System.out.println(a.getQualifiedName());
    			System.out.println(a.getText());
    			Object data = a.getData();
    			QName qname = a.getQName();
    			System.out.println("==========");
	   		} else{
				System.out.println("==========");
    		}
    	}
    	
    	System.out.println(document.asXML());
    	
    	
//    	//命名空间 声明语法: <document xmlns='URL'>  <document xmlns:yourname='URL'>
    	
    	
//    	如果用xpath解释带命名空间的xml
//    	 XPath XPath = document.createXPath("//default:title/@lang");
//         XPath.setNamespaceURIs(map);
//         List nodelist = XPath.selectNodes(document);
    	
    	
//    	也可以按规范把命名空间和前缀写到xml位置地址中，然后按没有命名空间方式查

    	
//    	bookstore 选择所有bookstore子节点 
//    	/bookstore 选择根节点bookstore 
//    	bookstore/book 在bookstore的子节点中选择所有名为book的节点 
//    	//book          选择xml文档中所有名为book的节点 
//    	bookstore//book 选择节点bookstore下的所有名为book为节点 
//    	//@lang          选择所有名为lang的属性 
//    	断言 
//    	在方括号中[],用来更进一步定位选择的元素 
//    	表达式                      描述 
//    	/bookstore/book[1]          选择根元素bookstore的book子元素中的第一个 
//    	注意: IE5以上浏览器中第一个元素是0 
//    	/bookstore/book[last()] 选择根元素bookstore的book子元素中的最后一个 
//    	/bookstore/book[last()-1] 选择根元素bookstore的book子元素中的最后第二个 
//    	/bookstore/book[position()<3] 选择根元素bookstore的book子元素中的前两个 
//    	//title[@lang]          选择所有拥有属性lang的titile元素 
//    	//title[@lang='eng'] 选择所有属性值lang为eng的title元素 
//    	/bookstore/book[price>35.00] 选择根元素bookstore的book子元素中那些拥有price子元素且值大于35的 
//    	/bookstore/book[price>35.00]/title 选择根元素bookstore的book子元素中那些拥有price子元素且值大于35的title子元素 
//    	选择位置的节点 
//    	通配符               描述 
//    	*             匹配所有元素 
//    	@*             匹配所有属性节点 
//    	node()             匹配任何类型的节点 
//    	示例 
//    	表达式               描述 
//    	/bookstore/*    选择根元素bookstore的下的所有子元素 
//    	//*             选择文档中所有元素 
//    	//title[@*]    选择所有拥有属性的title元素
//
//    	使用操作符“|”组合选择符合多个path的表达式
    	
	}
}
