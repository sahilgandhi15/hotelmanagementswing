package cx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

//������ݿ⣬�������ݲ���
public class OnData {

	private Connection con;
	public infoPack get;

	public OnData() {
		// ���췽��
	}

	public boolean OnData(String sAdd) {

		String mAdd = sAdd;
		try {
			// �������ݿ�
			con = ConnectionDatabase.getConnection();
			System.out.println("�Ѿ��������ݿ����ӡ�����ӿͷ�������Ϣ����");
			String strSql = "INSERT INTO guestroomBase VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(strSql);

			// ����"���������ݽ��з�װ" �ķ���
			this.usePack();
			ps.setInt(1, Types.NULL);
			ps.setInt(2, this.get.getNum());
			ps.setString(3, get.getType());
			ps.setString(4, get.getPlace());
			ps.setFloat(5, get.getPrice());
			ps.setString(6, get.getInfo());

			// ResultSet rs = ps.executeQuery(strSql);
			// ArrayList al = new ArrayList();
			// while (rs.next())
			// {
			// int num = rs.getInt("�ͷ����");
			// String type = rs.getString("�ͷ�����");
			// String place = rs.getString("�ͷ�λ��");
			// float price = rs.getFloat("�ͷ�����");
			// String info = rs.getString("������Ϣ");
			// al.add(new infoPack(num, type, place, price, info));

			// }
			// rs.close();

			int count = ps.executeUpdate();
			ps.close();

			// ����ִ�н������true/false
			return (count > 0 ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
			return (false);
		} finally {
			try {
				ConnectionDatabase.close(con);
				System.out.println("��ӿͷ�������Ϣʱ�ɹ��ر����ݿ������");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("���ݿ��������δ�رգ�");
			}
		}
	}

	public void usePack() {
		try {
			String n = BaseInfo.bAdd.txtNum.getText().trim();// ʹ��ԭ�������
			int num = Integer.parseInt(n);
			float price = Float.parseFloat(BaseInfo.bAdd.txtPrice.getText()
					.trim());
			String type = BaseInfo.bAdd.txtType.getText().trim();
			String place = BaseInfo.bAdd.txtPlace.getText().trim();
			String info = BaseInfo.bAdd.txtArea.getText().trim();

			if (info.equals("")) {
				info = null;
			}
			// ����������ݽ��з�װ
			get = new infoPack(num, type, place, price, info);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

}