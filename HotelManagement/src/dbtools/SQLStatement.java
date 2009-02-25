package dbtools;

import java.util.Hashtable;
import java.util.Map;

/**
 * ���ڸ����ݿ⽨���SQL��䲻ͬ�����Խ����˽ӿ���������Բ�ͬ���ݿ� ����ͬ�����ݿ����������ͬSQL��������
 */
public interface SQLStatement {
	/**
	 * �����������ݿ�����䡣
	 * 
	 * @param tableName
	 *            ����
	 * @param fields
	 *            Ҫ�����ı���ֶ��б������ֶ����Լ��ֶ�ֵ
	 * @return �������ݿ���SQL���
	 */
	public String createTable(String tableName, Map fields);

	/**
	 * ���������ݿ�����ֶε���䡣
	 * 
	 * @param tableName
	 *            ����
	 * @param fields
	 *            Ҫ���ӵ��ֶ��б������ֶ����Լ��ֶ�ֵ
	 * @return �����ݿ�����ֶε����
	 */
	public String addFields(String tableName, Map fields);

	/**
	 * ������չ���ݿ��ֶγ��ȵ���䡣
	 * 
	 * @param tableName
	 *            ����
	 * @param fields
	 *            Ҫ��չ���ֶ��б������ֶ����Լ��ֶ�ֵ
	 * @return ��չ���ݿ��ֶγ��ȵ����
	 */
	public String extendFieldsLength(String tableName, Map fields);

	/**
	 * �Ѹ����ַ��������ݿ�Ĵ�Сд�������ת��
	 */
	public String translateString(String value);

	/**
	 * ����ṹ
	 * 
	 */
	public void createTableStruct(String tableName, Hashtable newFields)
			throws Exception;

	/**
	 * ���ݱ����õ������ֶε���������
	 */
	public Hashtable getDataTypes(String tableName) throws Exception;

	/**
	 * ���ݱ�ṹ�õ��ֶ���Ϣ�������ֶ������ֶγ��ȣ�
	 */
	public Hashtable getFieldLength(String[][] data) throws Exception;

	/**
	 * �õ����ݿ������б���
	 */
	public String[] getTableNames() throws Exception;

	/**
	 * �жϱ��Ƿ����
	 */
	public boolean isTableExist(String tableName) throws Exception;
}