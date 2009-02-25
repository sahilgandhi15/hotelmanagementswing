package cg;

import java.sql.SQLException;
import java.util.Vector;

import dbtools.DBTools;

public class odbc {

	public odbc() {
	}

	public Vector<String> getColumnNamesByTabname() {
		Vector<String> cols = new Vector<String>();
		try {
			String strSql = "SELECT * FROM dingfangxinxi";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			for (int i = 0; i < result[0].length; i++) {
				cols.add(result[0][i]);
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return (cols);
	}

	public Vector<String> getColumnNamesByTabname1() {
		try {
			String strSql = "SELECT �ͷ����,�ͷ�����,�ͷ�λ��,�Ƿ���ס FROM footinfo WHERE �Ƿ���ס=1";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			Vector colHead = new Vector();
			for (int i = 0; i < result[0].length; i++) {
				colHead.add(result[0][i]);
			}
			return colHead;
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public Vector<String> getColumnNamesByTabname2() {
		try {
			String strSql = "SELECT �ͷ����,�ͷ�����,�ͷ�λ��,�Ƿ���ס FROM footinfo WHERE �Ƿ���ס=0";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			Vector colHead = new Vector();
			for (int i = 0; i < result[0].length; i++) {
				colHead.add(result[0][i]);
			}
			return colHead;
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Vector<Object> getDataByTabname() {
		Vector<Object> data = new Vector<Object>();
		try {
			String strSql = "SELECT * FROM dingfangxinxi";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			return DatabaseOperate.getVector(result);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Vector<Object> getDataByTabname1() {
		try {
			String strSql = "SELECT �ͷ����,�ͷ�����,�ͷ�λ��,�Ƿ���ס FROM footinfo WHERE �Ƿ���ס=1";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			return DatabaseOperate.getVector(result);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Vector<Object> getDataByTabname2() {
		try {
			String strSql = "SELECT �ͷ����,�ͷ�����,�ͷ�λ��,�Ƿ���ס FROM footinfo WHERE �Ƿ���ס=0";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			return DatabaseOperate.getVector(result);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Vector<String> getAllTabelName() {
		/*
		 * try { String strSql = "SELECT [name] AS tab_name FROM sysobjects
		 * WHERE xtype='U' ORDER BY [name]"; String[][] result =
		 * DBTools.executeQueryWithTableHead(strSql); return
		 * DatabaseOperate.getVector(result); while (rs.next()) {
		 * vec.add(rs.getString("tab_name")); } } catch (SQLException sqle) {
		 * sqle.printStackTrace(); }
		 */
		return null;
	}
	
	public Vector<String> getAllTabelName0() {
		try {
			String strSql = "select �ͷ���� from footinfo where �Ƿ���ס=0";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			return DatabaseOperate.getVector(result);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Vector<String> getAllTabelName1() {
		try {
			String strSql = "select �ͷ���� from footinfo";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			return DatabaseOperate.getVector(result);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Vector<String> getAllTabelName2() {
		try {
			String strSql = "select �ͷ���� from footinfo where �Ƿ���ס=1";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			return DatabaseOperate.getVector(result);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	// �ʵ���ˮ��
	public Vector<String> getAllTabelName3() {
		try {
			String strSql = "select �˵���ˮ�� from dingfangxinxi where ʹ��״̬ = '����'";
			String[][] result = DBTools.executeQueryWithTableHead(strSql);
			return DatabaseOperate.getVector(result);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void closeConnection() {
	}
}