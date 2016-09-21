package com.cloudsea.sys.utils.expand;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cloudsea.sys.utils.expand.anotation.Instant;
import com.cloudsea.sys.utils.expand.anotation.Table;
import com.cloudsea.sys.utils.expand.anotation.TableColumn;
import com.cloudsea.sys.utils.expand.anotation.TableId;
import com.cloudsea.sys.utils.expand.constant.OrderType;
import com.cloudsea.sys.utils.expand.constant.Propertise;
import com.cloudsea.sys.utils.expand.constant.SQLOperatSymbol;
import com.cloudsea.sys.utils.expand.model.MappingClassInfo;
import com.cloudsea.sys.utils.expand.model.PrimaryKeyTypeEnum;
import com.cloudsea.sys.utils.expand.model.SQLOperatSymbolEnum;


/**
 * @author zhangxiaorong
 * 
 */
public class JdbcExpandUtil {

	public static final int DB_ISOLATION = Connection.TRANSACTION_READ_COMMITTED;

	
	public static <T> List<T> query(T object) {
		return query(object, null, null);
	}

	/**
	 * 
	 * @param <T>
	 * @param object
	 *            对象用来存放查询的条件的参数值
	 * @param operationMap
	 *            KEY存放对象字段名，VALUE存放操作条件
	 * @param orders
	 *            指定排序，key:对象字段名，value:排序方式desc或asc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> query(T object, Map<String, SQLOperatSymbolEnum> operationMap,
			LinkedHashMap<String, Integer> orders) {

		if (object == null)
			return null;

		// 读取实体类上配置的注解，把关系映射信息存入MappingClassInfo对象
		MappingClassInfo mappingInfo = mappingClassToTable(object.getClass());
		if (mappingInfo == null) {
			return null;
		}

		String sql = null;
		try {
			Map<String, SQLOperatSymbolEnum> opMap = new HashMap<String, SQLOperatSymbolEnum>();
			if (operationMap != null && operationMap.size() > 0) {
				for (Entry<String, SQLOperatSymbolEnum> entry: operationMap.entrySet()) {
					// 把属性名转成对应的列名
					opMap.put(mappingInfo.getNameCastMap().get(
							entry.getKey()), entry.getValue());
				}
			}

			// 组装出查询语句
			sql = querySqlCompose(object, mappingInfo, opMap, orders);
		} catch (Exception e) {

			return null;
		}

		return (List<T>) query(object.getClass(), sql, mappingInfo.getNameCastMap());
	}
	
	
	public static <T> List<T> query(Class<T> claz, String sql) {
		MappingClassInfo mappingInfo = mappingClassToTable(claz);
		if (mappingInfo == null)
			return null;
		return query(claz, sql, mappingInfo.getNameCastMap());
	}

	private static <T> List<T> query(Class<T> claz, String sql,
			Map<String, String> map) {
		if (claz == null)
			return null;

		// 读取实体类上配置的注解，把关系映射信息存入MappingClassInfo对象
		MappingClassInfo mappingInfo = mappingClassToTable(claz);
		if (mappingInfo == null) {
			return null;
		}

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		List<T> list = new ArrayList<T>();
		try {
			conn = DBConnection.getConnection();
			// conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				// 把查询出来的ResultSet，转换成List<T>
				list = populateBean(rs, claz, map);
			}
			// conn.commit();

		} catch (Exception e) {

		} finally {
			if (conn != null)
				DBConnection.freeConnection(conn, ps, rs);
		}
		return list;
	}


	public static boolean save(Object object) {
		if (object == null)
			return false;

		int sequenceID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		Savepoint txpoint = null;
		String sql = null;

		try {
			conn = DBConnection.getConnection();

			MappingClassInfo mappingInfo = mappingClassToTable(object
					.getClass());
			String sequenceName = mappingInfo.getSequenceName();

			if (sequenceName != null && !"".equals(sequenceName)) {
				sequenceID = SequenceUtil.getNextVal(sequenceName, conn);
			}

			// 指定事务隔离级别
			// conn.setTransactionIsolation(DB_ISOLATION);
			// conn.setAutoCommit(false);
			// 组装保存sql语句
			sql = saveSqlCompose(object, mappingInfo, sequenceID);
			ps = conn.prepareStatement(sql);
			try {
				// 设保存点，如果保存异常，可以回滚到保存点
				// 有的数据库引擎不支持Savepoint，如mysql的MyISAM引擎不支持，InnoDB引擎支持
				// txpoint = conn.setSavepoint();
			} catch (Exception e) {

				// 在设定保存点失败时，程序不会中断，可以继续执行
				// txpoint = null;
			}
			int count = ps.executeUpdate();

			// conn.commit();

			if (count < 0)
				return false;
			else
				return true;

		} catch (Exception e) {

			// try {
			// if (txpoint != null)
			// //如果异常，存在保存点，可以加滚到保存点
			// conn.rollback(txpoint);
			// else
			// conn.rollback();
			// } catch (SQLException e1) {
			// logger.errorT("JdbcExpandUtil.save Exception:" + e.getMessage());
			// }
			return false;
		} finally {
			try {
				conn.releaseSavepoint(txpoint);
				DBConnection.freeConnection(conn, ps);
			} catch (SQLException e) {

			}
		}
	}

	/**
	 * 对于主键为自定义内容加上序列号的情况，可以此方法
	 * 
	 * @param prefix
	 *            主键前缀
	 * @return
	 */
	public static boolean saveWithPrefixSeq(Object object, String prefix) {
		if (object == null)
			return false;

		int sequenceID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		Savepoint txpoint = null;
		String sql = null;
		String id = "";
		try {
			conn = DBConnection.getConnection();

			MappingClassInfo mappingInfo = mappingClassToTable(object
					.getClass());
			String sequenceName = mappingInfo.getSequenceName();

			if (sequenceName != null && !"".equals(sequenceName)) {
				sequenceID = SequenceUtil.getNextVal(sequenceName, conn);
			}
			id = prefix + sequenceID;
			// 指定事务隔离级别
			// conn.setTransactionIsolation(DB_ISOLATION);
			// conn.setAutoCommit(false);
			// 组装保存sql语句
			sql = saveSqlCompose(object, mappingInfo, id);
			ps = conn.prepareStatement(sql);
			try {
				// 设保存点，如果保存异常，可以回滚到保存点
				// 有的数据库引擎不支持Savepoint，如mysql的MyISAM引擎不支持，InnoDB引擎支持
				// txpoint = conn.setSavepoint();
			} catch (Exception e) {
				
			}
			int count = ps.executeUpdate();

			// conn.commit();

			if (count < 0)
				return false;
			else
				return true;

		} catch (Exception e) {
			
			// try {
			// if (txpoint != null)
			// //如果异常，存在保存点，可以加滚到保存点
			// conn.rollback(txpoint);
			// else
			// conn.rollback();
			// } catch (SQLException e1) {
			// logger.errorT("JdbcExpandUtil.save Exception:" + e.getMessage());
			// }
			return false;
		} finally {
			try {
				conn.releaseSavepoint(txpoint);
				DBConnection.freeConnection(conn, ps);
			} catch (SQLException e) {
				
			}
		}
	}

	/**
	 * 对于主键为自定义内容加上序列号的情况，可以此方法
	 * 
	 * @param prefix
	 *            主键前缀
	 * @return
	 */
	public static String saveWithPrefixSeqReturnId(Object object, String prefix) {
		if (object == null)
			return "";

		int sequenceID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		Savepoint txpoint = null;
		String sql = null;
		String id = "";
		try {
			conn = DBConnection.getConnection();

			MappingClassInfo mappingInfo = mappingClassToTable(object
					.getClass());
			String sequenceName = mappingInfo.getSequenceName();

			if (sequenceName != null && !"".equals(sequenceName)) {
				sequenceID = SequenceUtil.getNextVal(sequenceName, conn);
			}
			id = prefix + sequenceID;
			// 指定事务隔离级别
			// conn.setTransactionIsolation(DB_ISOLATION);
			// conn.setAutoCommit(false);
			// 组装保存sql语句
			sql = saveSqlCompose(object, mappingInfo, id);
			ps = conn.prepareStatement(sql);
			try {
				// 设保存点，如果保存异常，可以回滚到保存点
				// 有的数据库引擎不支持Savepoint，如mysql的MyISAM引擎不支持，InnoDB引擎支持
				// txpoint = conn.setSavepoint();
			} catch (Exception e) {
				
				// 在设定保存点失败时，程序不会中断，可以继续执行
				// txpoint = null;
			}
			int count = ps.executeUpdate();

			// conn.commit();
			if (count > 0) {
				return id;
			} else {
				return "";
			}

		} catch (Exception e) {
			
			return "";
		} finally {
			try {
				conn.releaseSavepoint(txpoint);
				DBConnection.freeConnection(conn, ps);
			} catch (SQLException e) {
				
			}
		}
	}

	public static int saveReturnId(Object object) {
		int sequenceID = -1;
		if (object == null)
			return sequenceID;

		Connection conn = null;
		PreparedStatement ps = null;
		Savepoint txpoint = null;
		String sql = null;

		try {
			conn = DBConnection.getConnection();

			MappingClassInfo mappingInfo = mappingClassToTable(object
					.getClass());
			String sequenceName = mappingInfo.getSequenceName();

			if (sequenceName != null && !"".equals(sequenceName)) {
				sequenceID = SequenceUtil.getNextVal(sequenceName, conn);
			}

			// 指定事务隔离级别
			// conn.setTransactionIsolation(DB_ISOLATION);
			// conn.setAutoCommit(false);
			// 组装保存sql语句
			sql = saveSqlCompose(object, mappingInfo, sequenceID);
			ps = conn.prepareStatement(sql);
			try {
				// 设保存点，如果保存异常，可以回滚到保存点
				// 有的数据库引擎不支持Savepoint，如mysql的MyISAM引擎不支持，InnoDB引擎支持
				// txpoint = conn.setSavepoint();
			} catch (Exception e) {
				
				// 在设定保存点失败时，程序不会中断，可以继续执行
				// txpoint = null;
			}
			int count = ps.executeUpdate();

			// conn.commit();
			if (count > 0) {
				return sequenceID;
			} else {
				return -1;
			}


		} catch (Exception e) {
			
			// try {
			// if (txpoint != null)
			// //如果异常，存在保存点，可以加滚到保存点
			// conn.rollback(txpoint);
			// else
			// conn.rollback();
			// } catch (SQLException e1) {
			// logger.errorT("JdbcExpandUtil.save Exception:" + e.getMessage());
			// }
		} finally {
			try {
				conn.releaseSavepoint(txpoint);
				DBConnection.freeConnection(conn, ps);
			} catch (SQLException e) {
				
			}
		}
		return sequenceID;
	}

	public static boolean batchSave(List<? extends Object> entityList) {
		if (entityList == null || entityList.size() == 0)
			return false;

		MappingClassInfo mappingInfo = mappingClassToTable(entityList.get(0)
				.getClass());
		Connection conn = null;
		PreparedStatement ps = null;
		Savepoint txpoint = null;
		String sql = null;
		boolean result = true;

		try {
			conn = DBConnection.getConnection();
			conn.setTransactionIsolation(DB_ISOLATION);
			// conn.setAutoCommit(false);
			List<String> fieldList = new ArrayList<String>();
			sql = saveSqlCreate(entityList.get(0), mappingInfo, fieldList);
			
			try {
				txpoint = conn.setSavepoint();
			} catch (SQLException e1) {
				
			}
			ps = conn.prepareStatement(sql);
			for (int i = 0, size = 1; i < entityList.size(); i++, size++) {
				Object obj = entityList.get(i);
				// 给一条记录的问号传参数
				setParamater(ps, obj, fieldList);
				// 指定默认条件下每100条做一次批处理
				if (size % 100 != 0) {

					ps.addBatch();
				} else {
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			ps.executeBatch();
			// conn.commit();

		} catch (Exception e) {
			
			result = false;
			try {
				if (txpoint != null)
					conn.rollback(txpoint);
				else
					conn.rollback();
			} catch (SQLException e1) {
				
			}

		} finally {
			DBConnection.freeConnection(conn, ps);
		}
		return result;
	}

	public static boolean update(Object condition, Object value) {
		MappingClassInfo mappingInfo = mappingClassToTable(condition.getClass());
		// fieldNameList用来存放生成的sql语句中的依次的每个问号对应的类的属性名
		List<String> fieldNameList = new ArrayList<String>();
		String sql = updateSqlCreate(condition, value, mappingInfo,
				fieldNameList, false);
		
		boolean result = saveOrUpdateOrDelete(value, fieldNameList, sql);
		return result;
	}

	public static boolean updateIgnoreNull(Object condition, Object value) {
		MappingClassInfo mappingInfo = mappingClassToTable(condition.getClass());
		// fieldNameList用来存放生成的sql语句中的依次的每个问号对应的类的属性名
		List<String> fieldNameList = new ArrayList<String>();
		String sql = updateSqlCreate(condition, value, mappingInfo,
				fieldNameList, true);
		
		boolean result = saveOrUpdateOrDelete(value, fieldNameList, sql);
		return result;
	}

	public static boolean delete(Object condition) {
		MappingClassInfo mappingInfo = mappingClassToTable(condition.getClass());
		String sql = deleteSqlCreate(condition, mappingInfo);
		
		boolean result = saveOrUpdateOrDelete(condition, null, sql);
		return result;
	}

	public static boolean saveOrUpdateOrDelete(Object object,
			List<String> fieldNameList, String sql) {
		if (object == null)
			return false;

		Connection conn = null;
		PreparedStatement ps = null;
		Savepoint txpoint = null;

		try {
			conn = DBConnection.getConnection();
			conn.setTransactionIsolation(DB_ISOLATION);
			// conn.setAutoCommit(false);
			try {
				txpoint = conn.setSavepoint();
			} catch (SQLException e1) {
				
			}
			ps = conn.prepareStatement(sql);
			setParamater(ps, object, fieldNameList);
			int size = ps.executeUpdate();
			// conn.commit();
			if (size > 0)
				return true;
			else
				return false;

		} catch (Exception e) {
			
			try {
				if (txpoint != null)
					conn.rollback(txpoint);
				else
					conn.commit();
			} catch (SQLException e1) {
				
			}
			return false;

		} finally {
			DBConnection.freeConnection(conn, ps);
		}
	}

	public static MappingClassInfo mappingClassToTable(Class<?> claz) {

		// 用来存放表的所有的列与对应的类属性名,id也一样保存
		// key:fieldName value:columnName
		Map<String, String> nameCastMap = new HashMap<String, String>();
		Table table = claz.getAnnotation(Table.class);

		if (table == null)
			return null;

		String tableName = table.tableName();
		String className = claz.getSimpleName();
		PrimaryKeyTypeEnum pkType = null;
		String idName = null;
		String sequenceName = null;

		if (tableName == null || "".equals(tableName.trim()))
			tableName = className;

		List<Field> list = new ArrayList<Field>();
		getAllField(claz, list);

		for (Field field : list) {
			// Instant注解表示是瞬间的，不可以持久化
			if (field.getAnnotation(Instant.class) != null)
				continue;

			// 如果是静态属性，则他的字段值就不可以持久化
			int modifiers = field.getModifiers();
			if (Modifier.isStatic(modifiers))
				continue;

			TableId tableId = field.getAnnotation(TableId.class);
			TableColumn column = field.getAnnotation(TableColumn.class);
			String fieldName = field.getName();
			String columnName;

			// 注解配置TableId比TableColumn优先，如果配置中没有指定表字段名，就以类的属性名为准
			if (tableId != null) {
				columnName = tableId.idName();
				sequenceName = tableId.sequenceName();
				if (columnName == null || "".equals(columnName.trim()))
					columnName = fieldName;
				idName = columnName;
				pkType = tableId.pkType();
			} else if (column != null) {
				columnName = column.columnName();
				if (columnName == null || "".equals(columnName.trim()))
					columnName = fieldName;
				if (column.oracleKeyword()) // 如果列名是关键字，必须加双引号且是大写字母
					columnName = ("\"" + columnName + "\"").toUpperCase();
			} else {
				continue;
			}

			nameCastMap.put(fieldName, columnName);
		}

		if (nameCastMap.size() == 0)
			return null;

		MappingClassInfo mappingInfo = new MappingClassInfo();
		mappingInfo.setTableName(tableName);
		mappingInfo.setIdName(idName);
		mappingInfo.setSequenceName(sequenceName);
		mappingInfo.setPkType(pkType);
		mappingInfo.setNameCastMap(nameCastMap);
		return mappingInfo;
	}

	private static String querySqlCompose(Object object,
			MappingClassInfo mappingInfo, Map<String, SQLOperatSymbolEnum> operationMap,
			LinkedHashMap<String, Integer> orders)
			throws IllegalArgumentException, IllegalAccessException {

		StringBuffer sql = new StringBuffer();
		Map<String, String> nameCastMap = mappingInfo.getNameCastMap();
		String tableName = mappingInfo.getTableName();
		StringBuffer queryOrderyBy = new StringBuffer(" ORDER BY ");
		// sql语句中的条件数据 key:等号左边 value:等号右边
		Map<String, Object> conditionMap = new HashMap<String, Object>();

		try {
			for (String oldFieldName : nameCastMap.keySet()) {
				Field field = object.getClass().getDeclaredField(oldFieldName);
				field.setAccessible(true);
				Object value = field.get(object);

				conditionMap.put(nameCastMap.get(oldFieldName), value);
				
			}
		} catch (Exception e) {
			
		}

		sql.append("select * from ");
		if (Propertise.DATA_SCHEMA != null
				&& !"".equalsIgnoreCase(Propertise.DATA_SCHEMA.trim()))
			sql.append(Propertise.DATA_SCHEMA + ".");
		sql.append(tableName);

		String res = sqlConditionCompose(conditionMap, operationMap);
		sql.append(res);

		// 如果排序有指定，就组装出排序语句
		if (orders != null && orders.size() > 0) {
			for (String fieldName : orders.keySet()) {
				Integer orderVal = orders.get(fieldName);
				if (orderVal != null
						&& orderVal.intValue() == OrderType.ASC.intValue()) {
					queryOrderyBy.append(", ");
					queryOrderyBy.append(nameCastMap.get(fieldName));
					queryOrderyBy.append(" ASC ");
				} else if (orderVal != null
						&& orderVal.intValue() == OrderType.DESC.intValue()) {
					queryOrderyBy.append(", ");
					queryOrderyBy.append(nameCastMap.get(fieldName));
					queryOrderyBy.append(" DESC ");
				}
			}

			// 如果组装的语句至少有一个排序字段，就把组装排序语句加到sql中
			if (!queryOrderyBy.toString().trim().equalsIgnoreCase("ORDER BY")) {
				Pattern pattern = Pattern.compile("ORDER BY[\u0020]*,");
				Matcher matcher = pattern.matcher(queryOrderyBy.toString()
						.toUpperCase());
				String str = matcher.replaceAll("ORDER BY");
				sql.append(str);
			}
		}

		System.out.println(sql);
		return sql.toString().trim();
	}

	/**
	 * 
	 * @param object
	 *            保存对象
	 * @param mappingInfo
	 *            注解映射信息
	 * @param fieldNameList
	 *            每个问号对应的类的属性名
	 * @return
	 */
	private static String saveSqlCreate(Object object,
			MappingClassInfo mappingInfo, List<String> fieldNameList) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlval = new StringBuffer();
		Map<String, String> fieldCastMap = mappingInfo.getNameCastMap();
		String tableName = mappingInfo.getTableName();

		sql.append("insert into ");
		if (Propertise.DATA_SCHEMA != null
				&& !"".equalsIgnoreCase(Propertise.DATA_SCHEMA.trim()))
			sql.append(Propertise.DATA_SCHEMA + ".");
		sql.append(tableName);
		sql.append(" ( ");

		sqlval.append("values(");
		String s = "";

		try {

			for (String oldFieldName : fieldCastMap.keySet()) {
				Field field = object.getClass().getDeclaredField(oldFieldName);
				field.setAccessible(true);
				String newFieldName = fieldCastMap.get(oldFieldName)
						.toUpperCase();

				// 这里默认了主健策略是数据库自己生成的，不需要传参数
				// 除了主键，所有的列都是要上问号，做为传参的标记的
				if (!newFieldName.equals(mappingInfo.getIdName())) {
					sql.append(newFieldName + ", ");
					sqlval.append("?, ");
				}
				// sql中每加了一个问号，就要同步存入这个问号对应的列对应的类的属性名
				fieldNameList.add(oldFieldName);

			}
			sql.append(")");
			sqlval.append(")");

			// Pattern pattern = Pattern.compile("\\Q,)\\E");
			Pattern pattern = Pattern.compile(",[\u0020]*\\)");
			Matcher matcher = pattern.matcher(sql.toString()
					+ sqlval.toString());
			s = matcher.replaceAll(")");
			return s;

		} catch (Exception e) {
			
			return null;
		}
	}

	/**
	 * 
	 * @param condition
	 *            封装的是更新条件的数据
	 * @param value
	 *            封装的是更新结果要求的数据
	 * @param mappingInfo
	 *            注解的映射信息
	 * @param fieldNameList
	 *            每个问号对应的类的属性名
	 * @param ignoreNull
	 *            在sql语句中，set值时，是否忽略空值，如果为true表示空值就不用set，如果false，表示空值也一样要set
	 * @return
	 */
	private static String updateSqlCreate(Object condition, Object value,
			MappingClassInfo mappingInfo, List<String> fieldNameList,
			boolean ignoreNull) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		Map<String, String> fieldCastMap = mappingInfo.getNameCastMap();
		String tableName = mappingInfo.getTableName();

		sql.append("UPDATE ");
		if (Propertise.DATA_SCHEMA != null
				&& !"".equalsIgnoreCase(Propertise.DATA_SCHEMA.trim()))
			sql.append(Propertise.DATA_SCHEMA + ".");
		sql.append(tableName);
		sql.append(" SET ");

		sql2.append(" WHERE ");
		String s = "";

		try {

			for (String oldFieldName : fieldCastMap.keySet()) {
				Field field = value.getClass().getDeclaredField(oldFieldName);
				field.setAccessible(true);
				Object fieldValue = field.get(condition);
				String columnName = fieldCastMap.get(oldFieldName)
						.toUpperCase();

				// 字段如果不是主键都是更新目标
				if (!columnName.equals(mappingInfo.getIdName())) {
					if (!ignoreNull) {
						sql.append(columnName);
						sql.append(" = ?, ");
						fieldNameList.add(oldFieldName);
					} else {
						Object fieldVal = field.get(value);
						if (fieldVal != null) {
							sql.append(columnName);
							sql.append(" = ?, ");
							fieldNameList.add(oldFieldName);
						}
					}
				}

				// 非空的值做为更新的条件
				if (null != fieldValue && !isNullValue(fieldValue)) {
					String colunmVal = fieldValue instanceof String ? "'" + getColunmVal(fieldValue) + "'" : getColunmVal(fieldValue);
					Object rule = " = " + colunmVal + ", ";
					sql2.append(columnName);
					sql2.append(rule);
					sql2.append(" AND ");
				}
			}
			sql2.append(")");

			// Pattern pattern = Pattern.compile("\\Q,)\\E");
			Pattern pattern1 = Pattern.compile(",[\u0020]*WHERE");
			Matcher matcher1 = pattern1.matcher(sql.toString()
					+ sql2.toString());
			s = matcher1.replaceAll(" WHERE ");

			Pattern pattern2 = Pattern.compile(",[\u0020]*AND");
			Matcher matcher2 = pattern2.matcher(s);
			s = matcher2.replaceAll(" AND ");

			Pattern pattern3 = Pattern.compile("AND[\u0020]*\\)");
			Matcher matcher3 = pattern3.matcher(s);
			s = matcher3.replaceAll("");

			return s;

		} catch (Exception e) {
			
			return null;
		}
	}

	private static String deleteSqlCreate(Object condition,
			MappingClassInfo mappingInfo) {

		StringBuffer sql = new StringBuffer();
		String className = condition.getClass().getSimpleName();
		Map<String, String> fieldCastMap = mappingInfo.getNameCastMap();
		String tableName = mappingInfo.getTableName();

		if (null != fieldCastMap) {
			for (String key : fieldCastMap.keySet()) {
				if (key != null && !"".equals(key.trim())
						&& key.trim().equalsIgnoreCase(className))
					className = fieldCastMap.get(key);
			}
		}

		sql.append("DELETE FROM ");
		if (Propertise.DATA_SCHEMA != null
				&& !"".equalsIgnoreCase(Propertise.DATA_SCHEMA.trim()))
			sql.append(Propertise.DATA_SCHEMA + ".");
		sql.append(tableName);

		sql.append(" WHERE ");
		String s = "";

		try {

			for (String oldFieldName : fieldCastMap.keySet()) {
				Field field = condition.getClass().getDeclaredField(
						oldFieldName);
				field.setAccessible(true);
				String columnName = fieldCastMap.get(oldFieldName)
						.toUpperCase();
				Object fieldValue = field.get(condition);

				// 非空的值做为删除的条件
				if (null != fieldValue && !isNullValue(fieldValue)) {
					String colunmVal = fieldValue instanceof String ? "'" + getColunmVal(fieldValue) + "'" : getColunmVal(fieldValue);
					Object rule = " = " + colunmVal + ", ";
					sql.append(columnName);
					sql.append(rule);
					sql.append(" AND ");
				}
			}
			sql.append(")");

			Pattern pattern2 = Pattern.compile(",[\u0020]*AND");
			Matcher matcher2 = pattern2.matcher(sql.toString());
			s = matcher2.replaceAll(" AND ");

			Pattern pattern3 = Pattern.compile("AND[\u0020]*\\)");
			Matcher matcher3 = pattern3.matcher(s);
			s = matcher3.replaceAll("");

			return s;

		} catch (Exception e) {
			
			return null;
		}
	}

	private static String saveSqlCompose(Object object,
			MappingClassInfo mappingInfo, Object sequenceID) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlval = new StringBuffer();
		Map<String, String> fieldCastMap = mappingInfo.getNameCastMap();

		String tableName = mappingInfo.getTableName();

		sql.append("insert into ");
		if (Propertise.DATA_SCHEMA != null
				&& !"".equalsIgnoreCase(Propertise.DATA_SCHEMA.trim()))
			sql.append(Propertise.DATA_SCHEMA + ".");
		sql.append(tableName);
		sql.append(" ( ");

		sqlval.append("values(");
		String s = "";

		try {

			String idName = mappingInfo.getIdName();
			for (String oldFieldName : fieldCastMap.keySet()) {
				Field field = object.getClass().getDeclaredField(oldFieldName);
				field.setAccessible(true);
				Object value = field.get(object);
				String columnName = fieldCastMap.get(oldFieldName)
						.toUpperCase();

				String colunmVal = value instanceof String ? "'" + getColunmVal(value) + "'" : getColunmVal(value);
				Object rule = " = " + colunmVal + ", ";;
				if (columnName.equalsIgnoreCase(idName)) {
					sql.append(idName + ", ");
					if(idName instanceof String){
						sqlval.append("'"+String.valueOf(sequenceID)+"'");
					}else{
						sqlval.append(String.valueOf(sequenceID));
					}
				
					sqlval.append(", ");
				} else if (null != value && !isNullValue(value)) {
					sql.append(columnName + ", ");
					sqlval.append(rule);
				}
			}
			sql.append(")");
			sqlval.append(")");

			// Pattern pattern = Pattern.compile("\\Q,)\\E");
			Pattern pattern = Pattern.compile(",[\u0020]*\\)");
			Matcher matcher = pattern.matcher(sql.toString()
					+ sqlval.toString());
			s = matcher.replaceAll(")");
			s = Pattern.compile("\\Q=\\E").matcher(s).replaceAll("");
			return s;

		} catch (Exception e) {
			
			return null;
		}
	}

	private static void setParamater(PreparedStatement ps, Object obj,
			List<String> paramFieldNameList) {

		if (ps == null || obj == null || paramFieldNameList == null
				|| paramFieldNameList.size() == 0)
			return;

		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < paramFieldNameList.size(); i++) {
			String paramFieldName = paramFieldNameList.get(i);
			for (Field field : fields) {
				String fieldName = field.getName();
				if (fieldName.equals(paramFieldName)) {
					field.setAccessible(true);

					try {
						Class<? extends Object> className = field.getType();
						Object value = field.get(obj);

						if (value == null) {
							ps.setObject(i + 1, value);
							// ps.setNull(i+1, Types.);
							break;
						}

						if (className.isPrimitive() || className == Byte.class
								|| className == Short.class
								|| className == Character.class
								|| className == Integer.class
								|| className == Long.class
								|| className == Float.class
								|| className == Double.class
								|| className == Boolean.class
								|| className == String.class
								|| className == StringBuffer.class
								|| className == StringBuilder.class)
							ps.setObject(i + 1, value);
						else if (className == BigInteger.class)
							ps.setString(i + 1, value.toString());
						else if (className == BigDecimal.class)
							ps.setBigDecimal(i + 1, (BigDecimal) value);
						else if (className == Time.class)
							ps.setTime(i + 1, (Time) value);
						else if (className == java.util.Date.class)
							ps.setDate(i + 1, new java.sql.Date(
									((java.util.Date) value).getTime()));
						else if (className == java.sql.Date.class)
							ps.setDate(i + 1, (java.sql.Date) value);
						else if (className == Reader.class)
							ps.setClob(i + 1, (Reader) value);
						else if (className == InputStream.class)
							ps.setBlob(i + 1, (InputStream) value);
						else
							try {
								// 如果都不是以上类型，就以OBJECT保存，加了异常处理，如果传参失败，程序也可以继续
								ps.setObject(i + 1, value);
							} catch (Exception e) {
								
							}

					} catch (IllegalArgumentException e) {
						
					} catch (IllegalAccessException e) {
						
					} catch (SQLException e) {
						
					}
				}
			}
		}

	}

	private static void getAllField(Class claz, List<Field> list) {
		Field[] fields = claz.getDeclaredFields();
		for (Field f : fields)
			list.add(f);

		claz = claz.getSuperclass();
		if (claz != null)
			getAllField(claz, list);

	}

	private static <T> List<T> populateBean(ResultSet rs, Class<T> clazz,
			Map<String, String> nameCastMap) throws SQLException,
			InstantiationException, IllegalAccessException {
		// 结果集的元素对象
		ResultSetMetaData rsmd = rs.getMetaData();
		// 获取结果集的元素个数
		int colCount = rsmd.getColumnCount();
		// 返回结果的列表集合
		List<T> list = new ArrayList<T>();
		// 业务对象的属性数组
		Field[] fields = clazz.getDeclaredFields();

		while (rs.next()) {// 对每一条记录进行操作
			T obj = clazz.newInstance();// 构造业务对象实体
			// 将每一个字段取出进行赋值

			for (int i = 1; i <= colCount; i++) {
				String colunmName = rsmd.getColumnName(i);

				try {
					// 寻找该列对应的对象属性
					for (int j = 0; j < fields.length; j++) {
						Field f = fields[j];
						String fieldName = f.getName();
						String mappingName = nameCastMap.get(fieldName);

						// 如果匹配进行赋值
						if (mappingName != null
								&& !"".equals(mappingName.trim())) {

							// 如果列名是oracle关键字，解释的映射会加双引号，而这里为了比较要去掉双引号
							mappingName = mappingName.replace("\"", "");

							if (mappingName.equalsIgnoreCase(colunmName)) {
								Object value = getValFromResultSet(rs, i, f);

								if (value == null)
									continue;
								try {

									boolean flag = f.isAccessible();
									f.setAccessible(true);
									f.set(obj, value);
									f.setAccessible(flag);
									break;
								} catch (Exception e) {
									
								}
							}
						}
					}
				} catch (Exception e) {
					
				}
			}
			list.add(obj);
		}
		return list;
	}

	//如果是对象空值，或者是基本类型的0值，就为true
	private static boolean isNullValue(Object value) {
		
		if (value == null)
			return true;
		
		if (value.getClass() == byte.class && Integer.parseInt(String.valueOf(value)) == 0) {
			return true;
		}
		if (value.getClass() == short.class && Integer.parseInt(String.valueOf(value)) == 0) {
			return true;
		}
		if (value.getClass() == int.class && Integer.parseInt(String.valueOf(value)) == 0) {
			return true;
		}
		if (value.getClass() == long.class && Long.parseLong(String.valueOf(value)) == 0) {
			return true;
		}
		if (value.getClass() == float.class && Float.parseFloat(String.valueOf(value)) == 0) {
			return true;
		}
		if (value.getClass() == double.class && Double.parseDouble(String.valueOf(value)) == 0) {
			return true;
		} else
			return false;
	}

	private static Object getValFromResultSet(ResultSet rs, int i, Field f) {
		Object object = null;
		try {
			if (f.getType() == java.util.Date.class) {
				java.sql.Date date = rs.getDate(i);
				if (date != null) {
					object = new java.util.Date(date.getTime());
				}
			} else if (f.getType() == byte.class || f.getType() == Byte.class)
				object = rs.getByte(i);
			else if (f.getType() == short.class || f.getType() == Short.class)
				object = rs.getShort(i);
			else if (f.getType() == int.class || f.getType() == Integer.class)
				object = rs.getInt(i);
			else if (f.getType() == long.class || f.getType() == Long.class)
				object = rs.getLong(i);
			else if (f.getType() == float.class || f.getType() == Float.class)
				object = rs.getFloat(i);
			else if (f.getType() == double.class || f.getType() == Double.class)
				object = rs.getDouble(i);
			else if (f.getType() == boolean.class
					|| f.getType() == Boolean.class)
				object = rs.getBoolean(i);
			else if (f.getType() == BigInteger.class)
				object = (rs.getString(i) == null || rs.getString(i).trim()
						.equals("")) ? null : new BigInteger(rs.getString(i));
			else if (f.getType() == BigDecimal.class)
				object = rs.getBigDecimal(i);
			else if (f.getType() == Time.class)
				object = rs.getTime(i);
			else if (f.getType() == java.sql.Date.class)
				object = rs.getDate(i);
			else if (f.getType() == java.util.Date.class)
				object = new java.util.Date(rs.getDate(i).getTime());
			else if (f.getType() == java.sql.Timestamp.class)
				object = rs.getTimestamp(i);
			else if (f.getType() == Reader.class)
				object = rs.getClob(i);
			else if (f.getType() == InputStream.class)
				object = rs.getBlob(i);
			else
				object = rs.getObject(i);

			if (rs.wasNull()) {
				object = null;
			}

		} catch (SQLException e) {
			
		}
		return object;
	}

	public static String getColunmVal(Object o) {
		String colunmVal = null;
		if (null != o) {
			if (o instanceof java.lang.String){				
				colunmVal = String.valueOf(o);
			} else if (o instanceof java.sql.Date) {
				java.sql.Date dateValue = (java.sql.Date) o;
				colunmVal = "to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd").format(dateValue)
						+ "','YYYY-MM-DD')";
			} else if (o instanceof java.sql.Timestamp) {
				java.sql.Date dateValue = new java.sql.Date(((java.sql.Timestamp) o).getTime());
				colunmVal = "to_date('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateValue)
						+ "','yyyy-mm-dd hh24:mi:ss')";
			} else if (o instanceof java.util.Date) {
				java.util.Date utilDate = (java.util.Date) o;
				java.sql.Date dateValue = new java.sql.Date(utilDate.getTime());
				colunmVal = "to_date('" + new SimpleDateFormat("yyyy-MM-dd").format(dateValue)
						+ "','YYYY-MM-DD')";
			} else if (o instanceof java.lang.Double) {
				colunmVal = (java.lang.Double) o + "";
			} else if (o instanceof java.lang.Integer) {
				int intValue = (java.lang.Integer) o;
				colunmVal = intValue + "";
			} else if (o instanceof java.lang.Float) {
				colunmVal = (java.lang.Float) o + "";
			} else if (o instanceof java.lang.Boolean) {
				boolean b = (java.lang.Boolean) o;
				colunmVal = b ? 1 + "," : 0 + "";
			} else if (o instanceof java.math.BigDecimal) {
				BigDecimal b = (java.math.BigDecimal) o;
				colunmVal = b.toString() + "";
			} else if (o instanceof java.lang.Long) {
				Long b = (Long) o;
				colunmVal = b.toString() + "";
			} else if (o instanceof java.sql.Time) {
				java.sql.Time b = (java.sql.Time) o;
				colunmVal = "'" + b.toString() + "'";
			} else {
				colunmVal = "'" + o.toString() + "'";
			}
		} else {
			colunmVal = " is null";
		}
		return colunmVal;
	}



//	public static String matcherStringVal(Object o) {
//		if (null != o) {
//			String str = String.valueOf(o);
//			if (o instanceof java.lang.String) {
//				str = Pattern.compile("\\Q'\\E").matcher(str).replaceAll("''");
//			}
//			return str;
//		} else
//			return "";
//	}

	public static String sqlConditionCompose(Map<String, Object> sqlMap,
			Map<String, SQLOperatSymbolEnum> operationMap) {
		
		StringBuffer sql = new StringBuffer();
		boolean and = false;
		
		if (null != sqlMap && !sqlMap.isEmpty()) {
			for (String colunmName : sqlMap.keySet()) {
				Object val = sqlMap.get(colunmName);
				String colunmVal = getColunmVal(val);
				String conditionVal = colunmVal;
				String ruleVal = "";
				
				if (val instanceof String)
					conditionVal = "'" + colunmVal + "'";	
				
				//默认情况下，非空的值才能做为查询条件
				//默认情况下，查询条件用等于
				if (val != null && !isNullValue(val)) {
					ruleVal = " = " + conditionVal;					
				}
				
				if (null != operationMap && operationMap.size() > 0) {
					for (String colunmName2 : operationMap.keySet()) {
						if (colunmName.equals(colunmName2)) {
							Integer operation = operationMap.get(colunmName2).getKey();
							if (SQLOperatSymbolEnum.EQUAL.getKey() == operation)
								ruleVal = " =" + conditionVal;
							if (SQLOperatSymbolEnum.NOT_EQUAL.getKey() == operation)
								ruleVal = " !=" + conditionVal;
							if (SQLOperatSymbolEnum.GREATER.getKey() == operation)
								ruleVal = " >" + conditionVal;
							if (SQLOperatSymbolEnum.GREATER_EQUAL.getKey() == operation)
								ruleVal = " >=" + conditionVal;
							if (SQLOperatSymbolEnum.LESS.getKey() == operation)
								ruleVal = " <" + conditionVal;
							if (SQLOperatSymbolEnum.LESS_EQUAL.getKey() == operation)
								ruleVal = " <=" + conditionVal;
							if (SQLOperatSymbolEnum.NULL.getKey() == operation)
								ruleVal = " IS NULL";
							if (SQLOperatSymbolEnum.NOT_NULL.getKey() == operation)
								ruleVal = " IS NOT NULL";
							if (SQLOperatSymbolEnum.LIKE.getKey() == operation)
								ruleVal = " like '%" + colunmVal + "%'";
							if (SQLOperatSymbolEnum.NOT_LIKE.getKey() == operation)
								ruleVal = " not like '%" + colunmVal + "%'";
							if (SQLOperatSymbolEnum.IN.getKey() == operation)
								;
							if (SQLOperatSymbolEnum.NOT_IN.getKey() == operation)
								;
							if (SQLOperatSymbolEnum.BETWEEN.getKey() == operation)
								;
							if (SQLOperatSymbolEnum.NOT_BETWEEN.getKey() == operation)
								;
							
						}
					}
				}
				
				if (ruleVal != null && !"".equals(ruleVal.trim())){					
					String conditionSql = null;
					if (and) {
						conditionSql = " and " + colunmName.toUpperCase() + ruleVal ;
					} else {
						conditionSql = " where " + colunmName.toUpperCase() + ruleVal;
						and = true;
					}
					
					sql.append(conditionSql);
				}
			}
		}
		return sql.toString();
	}

}