package cx;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import cg.DatabaseOperate;
import dbtools.ConnectionPool;
import dbtools.DBTools;

public class ConnectionDatabase {

	ConnectionDatabase() {
	}

	// �������ݿ�
	public static Connection getConnection() {
		try {
			return ConnectionPool.getDBConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void close(Connection conn) {
		try {
			ConnectionPool.putDBConnection(conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void close()// �κ�"�ر�"����д����
	{

	}

	public Vector<String> getColumnNamesByTabname() {
		try {
			String strSql = "SELECT * FROM guestroomBase WHERE";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			return DatabaseOperate.getVector(result);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * public Vector<Object> getDataByTabname() { Vector<Object> data = new
	 * Vector<Object>(); try { Statement sta =
	 * con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_READ_ONLY); String strSql = "SELECT * FROM
	 * guestroomBase"; ResultSet rs = sta.executeQuery(strSql); int cols =
	 * rs.getMetaData().getColumnCount(); while (rs.next()) { Vector<Object>
	 * vec = new Vector<Object>(); for (int i = 1; i <= cols; i++) {
	 * vec.add(rs.getObject(i)); } data.add(vec); } rs.close(); sta.close(); }
	 * catch (SQLException sqle) { sqle.printStackTrace(); }
	 * 
	 * return (data); }
	 * 
	 * public Vector<String> getAllTabelName() { Vector<String> vec = new
	 * Vector<String>(); try { Statement sta = con.createStatement(); String
	 * strSql = "select �ͷ���� from guestroomBase"; ResultSet rs =
	 * sta.executeQuery(strSql); while (rs.next()) {
	 * vec.add(rs.getString("�ͷ����")); } rs.close(); sta.close(); } catch
	 * (SQLException sqle) { sqle.printStackTrace(); }
	 * 
	 * return (vec); }
	 */
}