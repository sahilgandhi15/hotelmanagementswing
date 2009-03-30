package hotel.dbtools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * ���ݿ⹤���࣬�ṩ�˼򵥷������ݿ�ķ���
 */
public class DBTools {
	/**
	 * ��������ȸ���SQL����SQL��������
	 */
	private static SQLStatement sqlStatement = null;

	/**
	 * ��Ӧ������
	 */
	private BSConnection connection = null;

	static {
		try {
			// ���������ļ�����SQL��������
			String driver = PropertiesService.getProperty("Database",
					"SQLStatement");
			sqlStatement = (SQLStatement) Class.forName(driver).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SQLStatement getSQLStatement() {
		return sqlStatement;
	}

	/**
	 * �չ���
	 */
	public DBTools() {
		try {
			this.connection = new BSConnection(
					ConnectionPool.getDBConnection(),
					ConnectionPool.stringType, ConnectionPool.numberType,
					ConnectionPool.characterset);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ʹ�����ݿ����ӹ���
	 * 
	 * @param connection
	 *            ���ݿ�����
	 */
	public DBTools(BSConnection connection) {
		this.connection = connection;
	}

	/**
	 * �ر�
	 */
	public void close() {
		try {
			ConnectionPool.putDBConnection(this.connection.getConnection());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ���ݱ����õ������ֶε���������
	 * 
	 * @param tableName
	 *            ��������Сд������
	 * @return ��Ϣ�� �ֶ������ֶ����� ��������ֶμ������ͣ��ֶ���Ϊ��д
	 */
	public static Hashtable getDataTypes(String tableName) throws Exception {
		return sqlStatement.getDataTypes(tableName);
	}

	/**
	 * ִ��ɾ�����
	 * 
	 * @param tableName
	 *            ��������Сд������
	 * @param ID
	 *            Ҫɾ���Ķ����ID��
	 */
	public static void delete(String tableName, String ID) throws Exception {
		String sql = "delete from " + tableName + " where ID = " + ID;
		Connection connection = ConnectionPool.getDBConnection();
		// �������
		Statement state = connection.createStatement();
		// ִ�����
		try {
			sql = new String(sql.getBytes(), ConnectionPool.characterset);
			state.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("SQL����" + sql);
			throw e;
		} finally {
			state.close();
			ConnectionPool.putDBConnection(connection);
		}
	}

	/**
	 * ִ��SQL���
	 * 
	 * @param sql
	 *            SQL���
	 * @throws SQLException
	 */
	public static void excuteUpdate(String sql) throws Exception {
		Connection connection = ConnectionPool.getDBConnection();
		// �������
		Statement state = connection.createStatement();
		// ִ�����
		try {
			sql = new String(sql.getBytes(), ConnectionPool.characterset);
			state.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("SQL����" + sql);
			throw e;
		} finally {
			state.close();
			ConnectionPool.putDBConnection(connection);
		}
	}

	/**
	 * ִ��SQL��䣬ȡ�ö�ά�ַ��������ʾ�Ľ����
	 * 
	 * @param sql
	 *            SQL��䣬�������ֶ�����SQL��ʶӦ����С���Ի���
	 * @return ���������ͷ���ֶ���ΪСд���ֶ����ݴ�Сд����
	 */
	public static String[][] executeQueryWithTableHead(String sql)
			throws Exception {
		Connection conn = ConnectionPool.getDBConnection();
		// �������
		Statement state = conn.createStatement();
		// ִ�����
		ResultSet set = null;
		String[][] result = null;
		try {
			sql = new String(sql.getBytes(), ConnectionPool.characterset);
			// �õ������
			set = state.executeQuery(sql);
			// �ֽ���Ϊ����ͷ�Ķ�ά����
			result = parseResultWithTableHead(set);
		} catch (SQLException e) {
			System.out.println("SQL����:" + sql);
			throw e;
		} finally {
			try {
				set.close();
			} catch (Exception ex) {
			}
			try {
				state.close();
			} catch (Exception ex1) {
			}
			// �ͷ�����
			ConnectionPool.putDBConnection(conn);
		}
		return result;
	}

	/**
	 * ִ��SQL��䣬ȡ�ö�ά�ַ��������ʾ�Ľ����
	 * 
	 * @param sql
	 *            SQL��䣬�������ֶ�����SQL��ʶӦ����С���Ի���
	 * @return ���������ͷ���ֶ���ΪСд���ֶ����ݴ�Сд����
	 */
	public static String[][] executePageQueryWithTableHead(String sql,
			int start, int end) throws Exception {
		Connection conn = ConnectionPool.getDBConnection();
		// �������
		Statement state = conn.createStatement();
		// ִ�����
		ResultSet set = null;
		try {
			sql = new String(sql.getBytes(), ConnectionPool.characterset);
			long time = System.currentTimeMillis();
			// �õ������
			set = state.executeQuery(sql);
			System.out.println("query:" + (System.currentTimeMillis() - time));
		} catch (SQLException e) {
			System.out.println("SQL����:" + sql);
			throw e;
		}
		// �ֽ���Ϊ����ͷ�Ķ�ά����
		String[][] result = parsePageResultWithTableHead(set, start, end);
		set.close();
		state.close();
		// �ͷ�����
		ConnectionPool.putDBConnection(conn);
		return result;
	}

	/**
	 * ִ��SQL��䣬���ؽ����
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet querySet(String sql) {
		try {
			// �������
			Statement state = this.connection.getConnection().createStatement();
			// ִ�����
			sql = new String(sql.getBytes(), this.connection.getCharacterset());
			// �õ������
			return state.executeQuery(sql);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ִ��SQL��䣬�������ݵ���
	 * 
	 * @param sql
	 *            SQL��䣬�������ֶ�����SQL��ʶӦ����С���Ի���
	 * @return ���������ͷ���ֶ���ΪСд���ֶ����ݴ�Сд����
	 */
	public Iterator query(String sql) {
		try {
			// �������
			Statement state = this.connection.getConnection().createStatement();
			// ִ�����
			sql = new String(sql.getBytes(), this.connection.getCharacterset());
			// �õ������
			ResultSet set = state.executeQuery(sql);
			// �����ɽ�����������α�
			return new QueryIterator(set);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �ѽ����ת��Ϊ����ͷ�Ķ�ά�ַ�������
	 * 
	 * @param set
	 *            �����
	 * @return ����ͷ�Ķ�ά�ַ��������ʾ�Ľ��
	 */
	public static String[][] parseResultWithTableHead(ResultSet set)
			throws Exception {
		// �õ�����
		String[] names = getColNames(set);
		int colCount = names.length;
		// �ѽ����ת��������
		LinkedList v = new LinkedList();
		for (int i = 0; set.next(); i++) {
			v.add(getOneRecord(set, colCount));
		}
		// ������ת��������
		String result[][] = new String[v.size() + 1][colCount];
		Iterator iter = v.iterator();
		for (int i = 1; iter.hasNext(); i++) {
			String[] item = (String[]) iter.next();
			result[i] = item;
		}
		// ��������ӵ�������
		for (int i = 0; i < colCount; i++) {
			result[0][i] = names[i];
		}
		return result;
	}

	/**
	 * �ѽ����ת��Ϊ����ͷ�Ķ�ά�ַ�������
	 * 
	 * @param set
	 *            �����
	 * @return ����ͷ�Ķ�ά�ַ��������ʾ�Ľ��
	 */
	public static String[][] parsePageResultWithTableHead(ResultSet set,
			int start, int end) throws Exception {
		// �õ�����
		String[] names = getColNames(set);
		int colCount = names.length;
		// ��ȥ��ʼ�ļ�¼
		for (int i = 0; i < start; i++) {
			set.next();
		}
		// �ѽ����ת��������
		LinkedList v = new LinkedList();
		for (int i = start; set.next() && i < end; i++) {
			v.add(getOneRecord(set, colCount));
		}
		// ������ת��������
		String result[][] = new String[v.size() + 1][colCount];
		Iterator iter = v.iterator();
		for (int i = 1; iter.hasNext(); i++) {
			String[] item = (String[]) iter.next();
			result[i] = item;
		}
		// ��������ӵ�������
		for (int i = 0; i < colCount; i++) {
			result[0][i] = names[i];
		}
		return result;
	}

	/**
	 * �õ�����
	 * 
	 * @param set
	 *            �����
	 * @return ��������
	 */
	public static String[] getColNames(ResultSet set) {
		try {
			// ȡ������
			ResultSetMetaData metadata = set.getMetaData();
			int colCount = metadata.getColumnCount();
			// ȡ������
			String names[] = new String[colCount];
			for (int i = 0; i < colCount; i++) {
				names[i] = metadata.getColumnName(i + 1);
			}
			return names;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �õ�һ����¼
	 * 
	 * @param set
	 *            �����
	 * @param colCount
	 *            �ֶ���
	 * @return �������ʾ�ļ�¼
	 */
	public static String[] getOneRecord(ResultSet set, int colCount) {
		try {
			String object[] = new String[colCount];
			for (int i = 0; i < colCount; i++) {
				Object obj = set.getObject(i + 1);
				if (obj != null) {
					object[i] = new String(obj.toString().getBytes(
							ConnectionPool.characterset));
				} else {
					object[i] = "";
				}
			}
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ������ṹ ���ԭ������û�е�ǰ�ֶΣ�����ӣ����ԭ�ֶεĳ��Ȳ����������䳤��
	 * 
	 * @param tableName
	 *            ����
	 * @param newFields
	 *            ����ֶ���Ϣ
	 */
	public static void createTableStruct(String tableName, Hashtable newFields)
			throws Exception {
		sqlStatement.createTableStruct(tableName, newFields);
	}

	/**
	 * ִ�в������
	 * 
	 * @param tableName
	 *            ��������Сд����
	 * @param field
	 *            �����Ա�ʾ���ֶ�����ֵ���ֶ�����Сд����
	 * 
	 * �Ľ����ݣ� 1����������ڣ��򴴽��� 2����������ֶβ����ڣ��򴴽��ֶΣ����ַ��ʹ�����
	 * 3����������ֶγ��Ȳ��������Զ����䣬һ������1000���ֽڡ�
	 * 
	 * ������Դ�� 1��JDBC����ȡ�ñ��Լ��ֶ���Ϣ���ࡣ 2��DBTools����Щ��������ʹ�á�
	 */
	public static void insert(String tableName, Hashtable field)
			throws Exception {
		// ����ṹ,�Ƿ���Ҫ����ӵ��ֶ�
		createTableStruct(tableName, field);
		// ȡ�ֶ�����
		Hashtable fieldTypes = getDataTypes(tableName);
		// �������������Ĳ����¼
		insertNoCreateTable(tableName, field, fieldTypes);
	}

	public static void insertNoCreateTable(String tableName, Hashtable field,
			Hashtable fieldTypes) throws Exception {
		// Ҫ������ֶ�����
		StringBuffer fieldName = new StringBuffer();
		// Ҫ������ֶ�ֵ��
		StringBuffer fieldValue = new StringBuffer();
		// �õ������ֶ�����
		Enumeration ne = field.keys();
		while (ne.hasMoreElements()) {
			// �õ��ֶ������ֶ�ֵ
			Object key = ne.nextElement();
			String name = key.toString();
			if (fieldName.length() != 0) {
				fieldName.append(", ");
			}
			fieldName.append(name);
			// ȡ���ֶ����͸��ݴ�д
			String fieldType = (String) fieldTypes.get(sqlStatement
					.translateString(name.toUpperCase()));
			// ȡ���ֶ����͸���Сд
			if (fieldType == null) {
				fieldType = (String) fieldTypes.get(sqlStatement
						.translateString(name.toLowerCase()));
			}
			if (fieldType == null) {
				throw new Exception("�����ֶβ����ڣ�������" + tableName + "���ֶ�����" + name);
			}
			String value = field.get(key).toString();
			if (fieldValue.length() != 0) {
				fieldValue.append(", ");
			}
			// �ַ���
			if (StringTool.contains(fieldType, ConnectionPool.stringType)
					|| StringTool.contains(fieldType, ConnectionPool.stringType
							.toUpperCase())) {
				value = value.replaceAll("\\\\", "\\\\\\\\");
				value = value.replaceAll("'", "''");
				fieldValue.append("'").append(value.trim()).append("'");
			}
			// ������
			else if (fieldType.equals(ConnectionPool.numberType)
					|| fieldType
							.equals(ConnectionPool.numberType.toUpperCase())) {
				try {
					double dValue = Double.parseDouble(value);
					fieldValue.append(dValue);
				} catch (Exception e) {
					fieldValue.append(0);
				}
			}
		}
		// ƴдsql
		String sql = "insert into " + tableName + "(" + fieldName
				+ ") values (" + fieldValue + ")";
		Connection connection = ConnectionPool.getDBConnection();
		// �������
		Statement state = connection.createStatement();
		try {
			sql = new String(sql.getBytes(), ConnectionPool.characterset);
			// ִ�����
			state.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException("sql������:" + sql);
		} finally {
			state.close();
			ConnectionPool.putDBConnection(connection);
		}
	}

	/**
	 * �жϱ��Ƿ����
	 * 
	 * @parm tablename ����
	 * @return ���Ƿ����
	 * @throws SQLException
	 */
	public static boolean isTableExist(String tableName) throws Exception {
		return sqlStatement.isTableExist(tableName);
	}

	/**
	 * ɾ��ָ���ı�
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public static void dropTable(String tableName) throws Exception {
		DBTools.excuteUpdate("drop table " + tableName);
	}

	/**
	 * ���ݱ�ṹ�õ��ֶ���Ϣ���ֶ������ֶγ��ȣ�
	 * 
	 * @param String[][]
	 *            ��ṹ
	 * @return Hashtable �ԣ��ֶ���(Сд)���ֶγ��ȣ�Ϊ����Ĺ�ϣ��
	 * @throws Exception
	 */
	public static Hashtable getFieldLength(String[][] data) throws Exception {
		return sqlStatement.getFieldLength(data);
	}

	/**
	 * ���ı�������
	 * 
	 * @param tableName
	 *            ��������Сд����
	 * @param ID
	 *            Ҫ���µĶ����ID��
	 * @param field
	 *            �����Լ���ʽ��ʾ���ֶ�������ֵ���ֶ�����Сд����
	 * @throws java.sql.SQLException
	 */
	public static void update(String tableName, String ID, Hashtable field)
			throws Exception {
		// ����ṹ,�Ƿ���Ҫ����ӵ��ֶ�
		createTableStruct(tableName, field);
		// �õ������ֶ�����
		Hashtable fieldTypes = getDataTypes(tableName);
		// �Բ�������ṹ�ķ�ʽ����
		updateNoCreateTable(tableName, ID, field, fieldTypes);
	}

	/**
	 * ���ı������ݣ���������ṹ
	 * 
	 * @param tableName
	 *            ��������Сд����
	 * @param ID
	 *            Ҫ���µĶ����ID��
	 * @param field
	 *            �����Լ���ʽ��ʾ���ֶ�������ֵ���ֶ�����Сд����
	 * @throws java.sql.SQLException
	 */
	public static void updateNoCreateTable(String tableName, String ID,
			Hashtable field, Map fieldTypes) throws Exception {
		// set fieldName=fieldValue��
		String fieldValue = "";
		// �õ������ֶ�
		Enumeration ne = field.keys();
		while (ne.hasMoreElements()) {
			// �õ��ֶ����ƺ�ֵ
			Object key = ne.nextElement();
			String name = key.toString();
			String value = field.get(key).toString();
			// ���ǵ�һ����","��
			if (!fieldValue.equals("")) {
				fieldValue += ',';
			}
			// ȡ���ֶ����͸��ݴ�д
			String fieldType = (String) fieldTypes.get(sqlStatement
					.translateString(name.toUpperCase()));
			// ȡ���ֶ����͸���Сд
			if (fieldType == null) {
				fieldType = (String) fieldTypes.get(sqlStatement
						.translateString(name.toLowerCase()));
			}
			// ���û�еõ�
			if (fieldType == null) {
				// �׳��ֶβ������쳣
				throw new RuntimeException("�ֶ�" + name + "������");
			}
			// �ַ���
			if (StringTool.contains(fieldType, ConnectionPool.stringType)
					|| StringTool.contains(fieldType, ConnectionPool.stringType
							.toUpperCase())) {
				value = value.replaceAll("\\\\", "\\\\\\\\");
				value = value.replaceAll("'", "''");
				fieldValue += name + "='" + value + "'";
			}
			// ������
			else if (fieldType.equals(ConnectionPool.numberType)
					|| fieldType
							.equals(ConnectionPool.numberType.toUpperCase())) {
				if (!value.equals("")) {
					value = value.replaceAll("\\\\", "\\\\\\\\");
					value = value.replaceAll("'", "''");
					fieldValue += name + "=" + value;
				} else {
					fieldValue += name + "=" + "-1";
				}
			}
		}
		String idType = (String) fieldTypes.get("ID");
		if (idType == null) {
			idType = (String) fieldTypes.get("id");
		}
		// ���û�еõ�
		if (idType == null) {
			// �׳��ֶβ������쳣
			throw new RuntimeException("�ֶ�ID������");
		}
		// �ַ���
		if (StringTool.contains(idType, ConnectionPool.stringType)
				|| StringTool.contains(idType, ConnectionPool.stringType
						.toUpperCase())) {
			ID = "'" + ID + "'";
		}
		// ƴдsql
		String sql = "update " + tableName + " set " + fieldValue
				+ " where id=" + ID;
		Connection connection = ConnectionPool.getDBConnection();
		// �������
		Statement state = connection.createStatement();
		try {
			sql = new String(sql.getBytes(), ConnectionPool.characterset);
			// ִ�����
			state.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("sql������:" + sql);
			throw e;
		} finally {
			state.close();
			ConnectionPool.putDBConnection(connection);
		}
	}

	/**
	 * ���������ӱ�ͷ�еõ�������
	 * 
	 * @param head
	 *            ��ͷ����ͷ�е��ֶ�����Сд����
	 * @param name
	 *            ��������Сд����
	 * @return ��������û���ҵ�������-1
	 * @throws java.lang.Exception
	 */
	public static int indexOf(String[] head, String name) throws Exception {
		for (int i = 0; i < head.length; i++) {
			if (head[i].toLowerCase().equals(name.toLowerCase())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * ��һ�м�¼ת�����ֶ���=�ֶ�ֵ��ʾ��Hash��
	 * 
	 * @param data[][]
	 *            Ҫת���Ĵ���ͷ�Ľ��������ͷ��Сд����
	 * @param row
	 *            Ҫת������
	 * @return �����ֶ���ȫ��ΪСд
	 */
	public static Hashtable getAttributes(String data[][], int row)
			throws Exception {
		Hashtable ht = new Hashtable();
		for (int j = 0; j < data[0].length; j++) {
			String name = data[0][j];
			String value = data[row][j];
			ht.put(name.toLowerCase(), value);
		}
		return ht;
	}

	/**
	 * ������
	 * 
	 * @param tableName
	 *            ����
	 * @param fields
	 *            �ֶ����� �ֶ������ֶ�ֵ
	 */
	public static void createTable(String tableName, Hashtable fields)
			throws Exception {
		// �õ�SQL���
		String sql = sqlStatement.createTable(tableName, fields);
		// ִ��sql���
		Connection connection = ConnectionPool.getDBConnection();
		Statement state = connection.createStatement();
		try {
			sql = new String(sql.getBytes(), ConnectionPool.characterset);
			state.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("SQL����" + sql);
			throw e;
		} finally {
			state.close();
			ConnectionPool.putDBConnection(connection);
		}
	}

	/**
	 * �����ֶ�
	 * 
	 * @param tableName
	 *            ����
	 * @param fields
	 *            ��Ϣ�� �ֶ���:�ֶ�ֵ
	 * @throws TableMakerException
	 */
	public static void addFields(String tableName, Hashtable fields)
			throws Exception {
		// �õ�SQL���
		String sql = sqlStatement.addFields(tableName, fields);
		// ִ��sql���
		Connection connection = ConnectionPool.getDBConnection();
		Statement state = connection.createStatement();
		try {
			sql = new String(sql.getBytes(), ConnectionPool.characterset);
			state.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("SQL����" + sql);
			throw e;
		} finally {
			state.close();
			ConnectionPool.putDBConnection(connection);
		}
	}

	/**
	 * �����ֶγ���
	 * 
	 * @param fields
	 *            ��Ϣ�� �ֶ������ֶ�ֵ
	 * @parm tableName ����
	 */
	public static void extendFieldsLength(String tableName, Hashtable fields)
			throws Exception {
		// �õ�SQL���
		String sql = sqlStatement.extendFieldsLength(tableName, fields);
		// ִ��sql���
		Connection connection = ConnectionPool.getDBConnection();
		Statement state = connection.createStatement();
		try {
			sql = new String(sql.getBytes(), ConnectionPool.characterset);
			state.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("SQL����" + sql);
			throw e;
		} finally {
			state.close();
			ConnectionPool.putDBConnection(connection);
		}
	}

	/**
	 * ȡ�����ݿ������еı��� ����mySql,sybase,SQLServer��
	 */
	public static String[] getTableNames() throws Exception {
		return sqlStatement.getTableNames();
	}

	/**
	 * ��Hashtable��ʾ������ת����sql��������������������� >�� >=�ȱȽ�����������û�����Ĵ���, ���򣬰��൱����
	 * 
	 * @param condition
	 *            hashtable��ʾ������
	 * @param isLike
	 *            �Ƿ�ģ����ѯ
	 * @return SQL����ʾ������
	 */
	public static String hashtableToSQLCondition(Map condition, boolean isLike) {
		return hashtableToSQLCondition(condition, isLike, "and");
	}

	/**
	 * ��Hashtable��ʾ������ת����sql��������������������� >�� >=�ȱȽ�����������û�����Ĵ���, ���򣬰��൱����
	 * 
	 * @param condition
	 *            hashtable��ʾ������
	 * @param isLike
	 *            �Ƿ�ģ����ѯ
	 * @param logic
	 *            ʹ��and����or��������
	 * @return SQL����ʾ������
	 */
	public static String hashtableToSQLCondition(Map condition, boolean isLike,
			String logic) {
		String result = "";
		// ����ÿһ������
		Iterator iter = condition.keySet().iterator();
		while (iter.hasNext()) {
			// �õ����ֺ�ֵ
			String name = (String) iter.next();
			String value = (String) condition.get(name);
			String con = name + "='" + value + "'";
			// �����߼������
			value = processLogic(name, value, "and");
			value = processLogic(name, value, "or");
			// ����������������
			if (value.indexOf('>') != -1 || value.indexOf(">=") != -1
					|| value.indexOf('<') != -1 || value.indexOf("<=") != -1) {
				con = name + value;
			}
			// �����ģ����ѯ
			else if (isLike) {
				con = name + " like '" + value + "'";
			}
			// ���һ������
			result = DBTools.connectCondition(result, con, logic);
		}
		return result;
	}

	/**
	 * �����û����������е��߼��������ֱ�����߼��������������ֶ���
	 * 
	 * @param name
	 *            �ֶ���
	 * @param value
	 *            �ֶ�ֵ�����п��ܺ����߼������
	 * @param logic
	 *            Ҫ������߼������
	 * @return ������
	 */
	private static String processLogic(String name, String value, String logic) {
		StringBuffer result = new StringBuffer(value);
		// ����ÿһ���߼������
		int pos = result.indexOf(logic);
		while (pos != -1) {
			result.insert(pos + logic.length() + 1, name);
			pos = result.indexOf(logic, pos + logic.length() + 1);
		}
		return result.toString();
	}

	/**
	 * ��Hashtable��ʾ������ת����sql��������,�ַ�������ģ����ѯ
	 * 
	 * @param condition
	 *            hashtable��ʾ������
	 * @return SQL����ʾ������
	 */
	public static String hashtableToSQLLikeCondition(Map condition) {
		return hashtableToSQLCondition(condition, true);
	}

	/**
	 * ��������sql����
	 * 
	 * @param sql1
	 *            ����1
	 * @param sql2
	 *            ����2
	 * @return ���������ӽ��
	 */
	public static String connectCondition(String sql1, String sql2) {
		return connectCondition(sql1, sql2, "and");
	}

	/**
	 * ��������sql����
	 * 
	 * @param sql1
	 *            ����1
	 * @param sql2
	 *            ����2
	 * @param logic
	 *            ���ӷ���
	 * @return ���������ӽ��
	 */
	public static String connectCondition(String sql1, String sql2, String logic) {
		// �������1Ϊ��
		if (sql1 == null || sql1.equals("")) {
			// ����sql2
			return sql2;
		}
		// �����������2Ϊ��
		else if (sql2 == null || sql2.equals("")) {
			// ����sq11
			return sql1;
		}
		// ���򣬶���Ϊ��
		else {
			return sql1 + " " + logic + " " + sql2;
		}
	}

	/**
	 * ��ѯ�����ʹ�õĵ�����
	 */
	private class QueryIterator implements Iterator {
		/**
		 * ��ѯ�Ľ����
		 */
		private ResultSet set = null;

		/**
		 * ��������
		 */
		private String[] colNames = null;

		/**
		 * ʹ�ý��������
		 * 
		 * @param set
		 *            �����
		 */
		public QueryIterator(ResultSet set) {
			this.set = set;
			// �õ�����
			this.colNames = DBTools.getColNames(this.set);
		}

		/**
		 * ����û����һ��
		 */
		public boolean hasNext() {
			try {
				return this.set.next();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * ȡ��һ��
		 */
		public Object next() {
			try {
				// �ӽ������ǰ�α��еõ�����
				String[] datas = DBTools.getOneRecord(this.set,
						this.colNames.length);
				return this.datasToMap(datas);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * �����������ֶ���ת����map
		 * 
		 * @param datas
		 *            ����
		 * @return ��map��ʽ���ֵ������Լ�ֵ
		 */
		private Map datasToMap(String[] datas) {
			Map result = new HashMap();
			// ����ÿһ���ֶ�
			for (int i = 0; i < this.colNames.length; i++) {
				// ����������п�ֵ
				if (datas[i] == null) {
					datas[i] = "";
				}
				// ���������Լ�ֵ�ŵ������
				result.put(this.colNames[i].toLowerCase(), datas[i]);
			}
			return result;
		}

		/**
		 * ����һ������
		 * 
		 * @param Object
		 *            ����
		 */
		public void remove() {
		}
	}

	/**
	 * ���ݱ���ȡ������
	 * 
	 * @param tableName
	 *            ����
	 * @return ����
	 */
	public static Object[] getColumnNames(String tableName) {
		try {
			// ���ݱ����õ��ֶ�����
			Map map = getDataTypes(tableName);
			// ����ÿһ���ֶ�����
			return map.keySet().toArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {

		Hashtable ht = new Hashtable();
		ht.put("f1", "a");
		ht.put("f2", "b");
		ht.put("f3", "����");
		ht.put("f4", "����");
		try {
			DBTools.insert("test_table", ht);
			String[][] result = DBTools
					.executeQueryWithTableHead("select * from test_table");
			for (int i = 1; i < result.length; i++) {
				Hashtable row = DBTools.getAttributes(result, i);
				System.out.println(row);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
