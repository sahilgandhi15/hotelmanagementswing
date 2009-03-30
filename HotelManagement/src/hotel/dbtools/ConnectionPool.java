package hotel.dbtools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import org.w3c.dom.Element;

/**
 * �����ӳص����ݿ����ӹ�����
 */
public class ConnectionPool {
	/**
	 * ���ӳأ��������
	 */
	private static LinkedList list = new LinkedList();

	/**
	 * ���������
	 */
	private static int maxCount = 0;

	/**
	 * �������ݿ�����ʱ���û���
	 */
	private static String user = "";

	/**
	 * �������ݿ�����ʱ���û�����
	 */
	private static String pw = "";

	/**
	 * �������ݿ�����ʱ�����������URL
	 */
	private static String dbURL = "";

	/**
	 * �ִ�����
	 */
	public static String stringType = "";

	/**
	 * ��������
	 */
	public static String numberType = "";

	/**
	 * �ַ���
	 */
	public static String characterset = "";

	private static boolean init = false;

	/**
	 * ��ʹ�ú�����ӷŵ����ӻ�����У����������������ر�����
	 * 
	 * @param conn
	 *            ����
	 */
	public static synchronized void putDBConnection(Connection conn)
			throws SQLException {
		// �����û���������
		if (list.size() < maxCount) {
			list.add(conn);
			// ���򣬹ر�
		} else {
			conn.close();
		}
	}

	/**
	 * �����ӳ�ȡ�����ݿ����ӣ�������ӳ��ѿգ�����������
	 * 
	 * @return ���ݿ�����
	 * @throws Exception
	 */
	public static synchronized Connection getDBConnection() throws Exception {
		while (list.size() != 0) {
			// ȡ�õ�һ������
			Connection conn = (Connection) list.remove(0);
			// û�йرգ�����
			if (!conn.isClosed()) {
				return conn;
			}
		}
		if (!init) {
			load();
			init = true;
		}
		// �����Ϊ�գ�����������, ����ӵ����ӳ���
		return DriverManager.getConnection(dbURL, user, pw);
	}

	/**
	 * ���������ļ�������װ������
	 */
	public static void load() throws Exception {
		// ���������
		maxCount = Integer.parseInt(PropertiesService.getProperty("Database",
				"maxCount"));
		// URL
		dbURL = PropertiesService.getProperty("Database", "URL");
		// �û���
		user = PropertiesService.getProperty("Database", "user");
		// ����
		pw = PropertiesService.getProperty("Database", "password");
		// �ִ�����
		stringType = PropertiesService.getProperty("Database", "stringtype");
		// ��������
		numberType = PropertiesService.getProperty("Database", "numbertype");
		// �ַ���
		characterset = PropertiesService.getProperty("characterset", "type");
		// �õ���������
		String jdbcDriver = PropertiesService.getProperty("Database", "driver");
		Class.forName(jdbcDriver);
		// ������
		int connCount = Integer.parseInt(PropertiesService.getProperty(
				"Database", "connCount"));
		// ����Ĭ�ϵ�����
		for (int i = 0; i < connCount; i++) {
			System.out.println("url:" + dbURL);
			Connection conn = DriverManager.getConnection(dbURL, user, pw);
			putDBConnection(conn);
		}
	}

	/**
	 * ������ʱ���ӣ�ʹ�����������Լ����йر�
	 */
	public static Connection createTempConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(dbURL, user, pw);
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ���ݸ�����Ԫ�������õ�һ������
	 * 
	 * @param element
	 *            �����������Ե�Ԫ��
	 * @return һ�����ݿ�����
	 */
	public static BSConnection getDBConnection(Element element) {
		try {
			// URL
			String dbURL = element.getAttribute("URL");
			// �û���
			String user = element.getAttribute("user");
			// ����
			String pw = element.getAttribute("password");
			// �õ���������
			String jdbcDriver = element.getAttribute("driver");
			Class.forName(jdbcDriver);
			Connection connection = DriverManager
					.getConnection(dbURL, user, pw);
			// �ִ�����
			String stringType = element.getAttribute("stringtype");
			// ��������
			String numberType = element.getAttribute("numbertype");
			// �ַ���
			String characterset = element.getAttribute("characterset");
			return new BSConnection(connection, stringType, numberType,
					characterset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}