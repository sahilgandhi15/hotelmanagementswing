package dbtools;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Mysql���ݿ���������SQL���
 */
public class MysqlStatement implements SQLStatement {
	/**
	 * �����������ݿ�����䡣
	 * 
	 * @param tableName
	 *            ����
	 * @param fields
	 *            Ҫ�����ı���ֶ��б������ֶ����Լ��ֶ�ֵ
	 * @return �������ݿ���SQL���
	 */
	public String createTable(String tableName, Map fields) {
		// ׼��sql���
		String sql = "create table " + tableName + "(";
		Iterator en = fields.keySet().iterator();
		// �������ֶ�ȡ�ֶ�
		while (en.hasNext()) {
			String fieldName = this.translateString((String) en.next());
			String stringType = ConnectionPool.stringType;
			sql += fieldName + " " + stringType + ",";
			// ���ؼ����ֶ�sql���׼��
			if (fieldName.equals("id")) {
				sql = sql.substring(0, sql.length() - 1);
				sql += " not null,";
			}
		}
		return sql.substring(0, sql.length() - 1) + ")";
	}

	/**
	 * ���������ݿ�����ֶε���䡣
	 * 
	 * @param tableName
	 *            ����
	 * @param fields
	 *            Ҫ���ӵ��ֶ��б������ֶ����Լ��ֶ�ֵ
	 * @return �����ݿ�����ֶε����
	 */
	public String addFields(String tableName, Map fields) {
		// ׼��sql���
		String sql = "ALTER TABLE " + tableName + " ADD(";
		Iterator en = fields.keySet().iterator();
		// �������ֶ�ȡ�ֶ�
		int i = 1;
		while (en.hasNext()) {
			String fieldName = (String) en.next();
			// ������ǵ�һ�����ö��ŷָ�
			if (i != 1) {
				sql = sql + ",";
			}
			String stringType = ConnectionPool.stringType;
			sql = sql + fieldName + " " + stringType;
			i++;
		}
		return sql + ")";
	}

	/**
	 * ������չ���ݿ��ֶγ��ȵ���䡣
	 * 
	 * @param tableName
	 *            ����
	 * @param fields
	 *            Ҫ��չ���ֶ��б������ֶ����Լ��ֶ�ֵ
	 * @return ��չ���ݿ��ֶγ��ȵ����
	 */
	public String extendFieldsLength(String tableName, Map fields) {
		// ׼��sql���
		String sql = "ALTER TABLE " + tableName + " MODIFY(";
		Iterator en = fields.keySet().iterator();
		// �������ֶ�ȡ�ֶ���Ϣ
		int i = 1;
		while (en.hasNext()) {
			String fieldName = (String) en.next();
			// ������ǵ�һ�����ö��ŷָ�
			if (i != 1) {
				sql = sql + ",";
			}
			String stringType = ConnectionPool.stringType;
			sql = sql + fieldName + " " + stringType;
			i++;
		}
		return sql + ")";
	}

	/**
	 * �Ѹ����ַ��������ݿ�Ĵ�Сд�������ת��
	 */
	public String translateString(String value) {
		return value;
	}

	/**
	 * ����ṹ
	 * 
	 */
	public void createTableStruct(String tableName, Hashtable newFields)
			throws Exception {
		// ����������򽨱�ṹ
		if (!(isTableExist(tableName))) {
			DBTools.createTable(tableName, newFields);
		}
		// �����ԭ�����ά��
		else {
			Hashtable addFieldsHt = new Hashtable();
			Hashtable modifyFieldsHt = new Hashtable();
			Connection connection = ConnectionPool.getDBConnection();
			// ȡԭ������
			DatabaseMetaData dmd = connection.getMetaData();
			String user = PropertiesService.getProperty("Database", "user");
			ResultSet rs = dmd.getColumns(null, translateString(user),
					translateString(tableName), "%");
			String[][] data = DBTools.parseResultWithTableHead(rs);
			Hashtable oldFields = getFieldLength(data);
			// �����±��ԭ�����ά��
			Enumeration newEnum = newFields.keys();
			// ���ȡ�±��ֶ���ɱ��ֶζԱ�
			while (newEnum.hasMoreElements()) {
				String newField = (String) newEnum.nextElement();
				newField = newField.toLowerCase();
				String newFieldValue = newFields.get(newField).toString();
				// ����ֶ���������ԭ����
				if (oldFields.containsKey(newField)) {
					// ׼��Ҫ������ֶι�ϣ��
					String oldFieldValue = (String) oldFields.get(newField);
					if (newFieldValue.length() > Integer
							.parseInt(oldFieldValue)) {
						modifyFieldsHt.put(newField, newFieldValue);
					}
				}
				// �����ֶ�����������ԭ����
				else {
					{
						// ׼��Ҫ���ӵ��ֶι�ϣ��
						addFieldsHt.put(newField, newFieldValue);
					}
				}
			}
			// �����ֶ�
			if (addFieldsHt.size() > 0) {
				DBTools.addFields(tableName, addFieldsHt);
			}
			// �����ֶγ���
			if (modifyFieldsHt.size() > 0) {
				DBTools.extendFieldsLength(tableName, modifyFieldsHt);
			}
			ConnectionPool.putDBConnection(connection);
		}
	}

	/**
	 * ���ݱ����õ������ֶε���������
	 */
	public Hashtable getDataTypes(String tableName) throws Exception {
		Hashtable ht = new Hashtable();
		Connection connection = ConnectionPool.getDBConnection();
		DatabaseMetaData dmd = connection.getMetaData();
		// �õ�����Ϣ
		String user = PropertiesService.getProperty("Database", "user");
		ResultSet rs = dmd.getColumns(null, translateString(user),
				translateString(tableName), "%");
		// �õ��ֶ������ֶ�����
		while (rs.next()) {
			String fieldName = rs.getString("COLUMN_NAME");
			String fieldType = rs.getString("TYPE_NAME");
			ht.put(fieldName.toUpperCase(), fieldType);
		}
		ConnectionPool.putDBConnection(connection);
		return ht;
	}

	/**
	 * ���ݱ�ṹ�õ��ֶ���Ϣ�������ֶ������ֶγ��ȣ�
	 */
	public Hashtable getFieldLength(String[][] data) throws Exception {
		// ����ֵΪ ht
		Hashtable ht = new Hashtable();
		// �õ��ֶνṹ
		// ׼���к�
		String[] head = new String[data[0].length];
		for (int i = 0; i < data[0].length; i++) {
			head[i] = data[0][i];
		}
		int fieldname_column = DBTools.indexOf(head, "COLUMN_NAME");
		int fieldSize_column = DBTools.indexOf(head, "COLUMN_SIZE");
		for (int i = 1; i < data.length; i++) {
			String fieldname = data[i][fieldname_column].toLowerCase();
			String fieldSize = data[i][fieldSize_column];
			ht.put(fieldname, fieldSize);
		}
		return ht;
	}

	/**
	 * �õ����ݿ������б���
	 */
	public String[] getTableNames() throws Exception {
		Connection connection = ConnectionPool.getDBConnection();
		// ȡ�����еı���
		DatabaseMetaData dmd = connection.getMetaData();
		String name = "%";
		String[] types = { "TABLE" };
		String user = PropertiesService.getProperty("Database", "user");
		ResultSet rs = dmd.getTables(null, translateString(user),
				translateString(name), types);
		String[][] ret = DBTools.parseResultWithTableHead(rs);
		String[] tableNames = new String[ret.length - 1];
		for (int i = 1; i < ret.length; i++) {
			tableNames[i - 1] = ret[i][2];
		}
		ConnectionPool.putDBConnection(connection);
		return tableNames;
	}

	/**
	 * �жϱ��Ƿ����
	 */
	public boolean isTableExist(String tableName) throws Exception {
		Connection connection = ConnectionPool.getDBConnection();
		// ȡ�����еı���
		DatabaseMetaData dmd = connection.getMetaData();
		String[] types = { "TABLE" };
		String user = PropertiesService.getProperty("Database", "user");
		ResultSet rs = dmd.getTables(null, translateString(user),
				translateString(tableName), types);
		// ����ñ��������򷵻�ֵΪ��
		if (rs.next()) {
			ConnectionPool.putDBConnection(connection);
			return true;
		}
		// ���򷵻�ֵΪ��
		else {
			ConnectionPool.putDBConnection(connection);
			return false;
		}
	}
}
