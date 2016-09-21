///**
// * 
// */
//package com.cloudsea.sys.module.bpm;
//
//import com.cloudsea.sys.module.bpm.domain.Article;
//import com.cloudsea.sys.module.bpm.domain.AuditState;
//import com.cloudsea.sys.module.bpm.domain.User;
//import com.cloudsea.sys.module.bpm.exception.EventSourceExcuteException;
//import com.cloudsea.sys.module.bpm.exception.IncorrectStatusParamException;
//import com.cloudsea.sys.module.bpm.exception.NoneEventSourceException;
//
///**
// * @author zhangxiaorong
// * 2014-5-16
// */
//public class StartMain {
//
//	/**
//	 * @param args
//	 * @throws Exception 
//	 */
//	public static void main(String[] args) throws Exception {
//		
//		AuditState auditState = new AuditState();
//		auditState.setValue(1);
//		
//		Article article = new Article();
//		article.setAuthor("安倍");
//		article.setContent("苍井空，呀马跌，太精彩！");
//		article.setName("挺黄执行书");
//		article.setAuditState(auditState);
//		
//		User user = new User();
//		user.setUserName("小泽马利亚");
//		
//		EntityInvocation beanEntity = new EntityInvocation();
//		beanEntity.setArticle(article);
//		beanEntity.setUser(user);
//		
//		Process process = ProcessContext.getProcess("扫黄行动", beanEntity);
//		try {
//			System.out.println("\r\t\r\t==========当前状态为" + process.getCurrentStatus().getClass().getSimpleName());
//			System.out.println("执行write");
//			process.write("OfficeStatus");
//		} catch (EventSourceExcuteException e) {
//			e.printStackTrace();
//		} catch (NoneEventSourceException e) {
//			e.printStackTrace();
//		} catch (IncorrectStatusParamException e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("\r\t\r\t==========当前状态为" + process.getCurrentStatus().getClass().getSimpleName());
//			System.out.println("执行auditPass");
//			process.auditPass("DeptleaderStatus");
//		} catch (EventSourceExcuteException e) {
//			e.printStackTrace();
//		} catch (NoneEventSourceException e) {
//			e.printStackTrace();
//		} catch (IncorrectStatusParamException e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("\r\t\r\t==========当前状态为" + process.getCurrentStatus().getClass().getSimpleName());
//			System.out.println("执行auditBack");
//			article.getAuditState().setReturnResean("对剧情应有更高要求，不精彩鬼才看，");
//			User auditleader = beanEntity.getUser();
//			auditleader.setUserName("挺黄办老黄");
//			article.getAuditState().setReturnUser(auditleader);
//			process.auditBack("writerRoomStatus");
//		} catch (EventSourceExcuteException e) {
//			e.printStackTrace();
//		} catch (NoneEventSourceException e) {
//			e.printStackTrace();
//		} catch (IncorrectStatusParamException e) {
//			e.printStackTrace();
//		}
//		
//		process = ProcessContext.getProcess("扫黄行动", beanEntity);
//		try {
//			System.out.println("\r\t\r\t==========当前状态为" + process.getCurrentStatus().getClass().getSimpleName());
//			System.out.println("执行write");
//			process.write("DeptleaderStatus");
//		} catch (EventSourceExcuteException e) {
//			e.printStackTrace();
//		} catch (NoneEventSourceException e) {
//			e.printStackTrace();
//		} catch (IncorrectStatusParamException e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("\r\t\r\t==========当前状态为" + process.getCurrentStatus().getClass().getSimpleName());
//			System.out.println("执行auditPass");
//			process.execute("auditPass", "OfficeStatus");
//		} catch (EventSourceExcuteException e) {
//			e.printStackTrace();
//		} catch (NoneEventSourceException e) {
//			e.printStackTrace();
//		} catch (ReflectiveOperationException e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("\r\t\r\t==========当前状态为" + process.getCurrentStatus().getClass().getSimpleName());
//			System.out.println("执行publish");
//			process.execute("publish", null);
//		} catch (EventSourceExcuteException e) {
//			e.printStackTrace();
//		} catch (NoneEventSourceException e) {
//			e.printStackTrace();
//		} catch (ReflectiveOperationException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
