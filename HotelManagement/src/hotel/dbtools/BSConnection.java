package hotel.dbtools;

import java.sql.Connection;

/**
 * �����ݿ����ӽ��а�װ���������������Լ��ַ�����������
 */
public class BSConnection {
	/**
	 * ���ݿ������
	 */
	private Connection connection = null;

	/**
	 * �ַ����ֶε�������
	 */
	private String stringType = null;

	/**
	 * �����ֶε�������
	 */
	private String numberType = null;

	/**
	 * �ַ���
	 */
	private String characterset = null;

	/**
	 * Ĭ�Ϲ���
	 */
	public BSConnection() {
		try {
			this.connection = ConnectionPool.getDBConnection();
			this.stringType = ConnectionPool.stringType;
			this.numberType = ConnectionPool.numberType;
			this.characterset = ConnectionPool.characterset;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ʹ�����ӣ��ַ����ֶ��������������ֶ��������Լ��ַ�������
	 */
	public BSConnection(Connection connection, String stringType,
			String numberType, String characterset) {
		this.connection = connection;
		this.stringType = stringType;
		this.numberType = numberType;
		this.characterset = characterset;
	}

	/**
	 * �õ�����
	 */
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * �õ��ַ����ֶ�������
	 */
	public String getStringType() {
		return this.stringType;
	}

	/**
	 * �õ������ֶ�������
	 */
	public String getNumberType() {
		return this.numberType;
	}

	/**
	 * �õ��ַ���
	 */
	public String getCharacterset() {
		return this.characterset;
	}
}