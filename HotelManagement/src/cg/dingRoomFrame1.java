package cg;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dbtools.ConnectionPool;
import dbtools.DBTools;

class dingRoomFrame1 extends JFrame implements ActionListener, ItemListener {
	public static String h2omane;
	public odbc dbo = new odbc();
	String[] str = { "1", "0" };

	JPanel contentPane;
	JLabel jLabel1 = new JLabel();
	JComboBox jComboBox1 = new JComboBox(dbo.getAllTabelName0());
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();
	JLabel jLabel5 = new JLabel();
	JLabel jLabel6 = new JLabel();
	JLabel jLabel7 = new JLabel();
	JLabel jLabel8 = new JLabel();
	JLabel jLabel10 = new JLabel();
	JLabel jLabel12 = new JLabel();
	JLabel jLabel14 = new JLabel();
	JLabel jLabel15 = new JLabel();
	JComboBox jComboBox2 = new JComboBox(str);
	JLabel jLabel16 = new JLabel();
	JTextArea jTextArea1 = new JTextArea();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();
	JLabel jLabel17 = new JLabel();
	JTextField jTextField1 = new JTextField();
	JTextField jTextField2 = new JTextField();
	JTextField jTextField3 = new JTextField();
	JLabel jLabel9 = new JLabel();
	JTextField jTextField4 = new JTextField();

	public dingRoomFrame1() {
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(null);
		setSize(new Dimension(515, 252));
		setTitle("Frame Title");
		jLabel1.setText("�ͷ����");
		jLabel1.setBounds(new Rectangle(17, 14, 53, 25));
		jComboBox1.setBounds(new Rectangle(74, 20, 70, 22));
		jComboBox1.addItem("ѡ�񷿼�");
		jComboBox1.setSelectedItem("ѡ�񷿼�");
		jLabel2.setText("�ͷ�����");
		jLabel2.setBounds(new Rectangle(166, 14, 53, 25));
		jLabel3.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel3.setBounds(new Rectangle(225, 20, 70, 22));
		jLabel4.setText("�ͷ�λ��");
		jLabel4.setBounds(new Rectangle(329, 14, 53, 25));
		jLabel5.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel5.setBounds(new Rectangle(393, 20, 82, 22));
		jLabel6.setText("�ͷ�����");
		jLabel6.setBounds(new Rectangle(16, 69, 53, 25));
		jLabel7.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel7.setBounds(new Rectangle(75, 69, 70, 22));
		jLabel8.setText("�˿�����");
		jLabel8.setBounds(new Rectangle(165, 69, 53, 25));
		jLabel10.setText("�˿����֤����");
		jLabel10.setBounds(new Rectangle(307, 69, 94, 25));
		jLabel12.setDisplayedMnemonic('0');
		jLabel12.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel12.setText("��ס����");
		jLabel12.setBounds(new Rectangle(15, 122, 53, 25));
		jLabel14.setBorder(null);
		jLabel14.setText("�ۿ�");
		jLabel14.setBounds(new Rectangle(151, 122, 34, 22));
		jLabel15.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel15.setBounds(new Rectangle(191, 122, 51, 21));
		jComboBox2.setBounds(new Rectangle(447, 122, 53, 22));
		jLabel16.setText("��ע��Ϣ");
		jLabel16.setBounds(new Rectangle(251, 122, 55, 25));
		jTextArea1.setBounds(new Rectangle(303, 123, 65, 59));
		jButton1.setBounds(new Rectangle(15, 181, 60, 23));
		jButton1.setText("�Ǽ�");
		jButton2.setBounds(new Rectangle(91, 181, 60, 23));
		jButton2.setText("ȡ��");
		jLabel17.setText("�Ƿ���ס");
		jLabel17.setBounds(new Rectangle(387, 122, 53, 25));
		jTextField1.setBounds(new Rectangle(221, 69, 70, 22));
		jTextField2.setBounds(new Rectangle(404, 69, 94, 22));
		jTextField3.setBounds(new Rectangle(75, 124, 70, 22));
		// jTextField3.addActionListener(new
		// Frame_jTextField3_actionAdapter(this));
		jLabel9.setText("�˵���");
		jLabel9.setBounds(new Rectangle(380, 156, 41, 25));
		jTextField4.setBounds(new Rectangle(429, 158, 70, 22));
		{
			//������ˮ��
			String[][] result = null;
			try {
				result = DBTools.executeQueryWithTableHead("select * from dingfangxinxi");
				jTextField4.setText(result.length + "");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		contentPane.add(jLabel7);
		contentPane.add(jLabel3);
		contentPane.add(jLabel8);
		contentPane.add(jLabel10);
		contentPane.add(jLabel14);
		contentPane.add(jLabel15);
		contentPane.add(jLabel16);
		contentPane.add(jLabel5);
		contentPane.add(jLabel4);
		contentPane.add(jLabel1);
		contentPane.add(jComboBox1);
		contentPane.add(jLabel2);
		contentPane.add(jLabel6);
		contentPane.add(jLabel12);
		contentPane.add(jTextField1);
		contentPane.add(jTextField3);
		contentPane.add(jTextField2);
		contentPane.add(jComboBox2);
		contentPane.add(jLabel17);
		contentPane.add(jTextField4);
		contentPane.add(jButton1);
		contentPane.add(jButton2);
		contentPane.add(jTextArea1);
		contentPane.add(jLabel9);

		jButton1.addActionListener(this);
		jButton2.addActionListener(this);

		jComboBox1.addItemListener(this);

		jButton1.setActionCommand("true");
		jButton2.setActionCommand("false");

		this.setTitle("��������");
		this.setSize(520, 245);
		this.setResizable(false);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(this);
		this.setVisible(true);
	}

	public void itemStateChanged(ItemEvent i) {
		int in = i.getStateChange();
		if (in == ItemEvent.SELECTED) {
			try {
				this.jComboBox1.removeItem("ѡ�񷿼�");
				int a = jComboBox1.getSelectedIndex() + 1; // �õ�ѡ�����
				String strno = (String) ((Vector) jComboBox1.getSelectedItem())
						.get(0); // �õ�����Ԫ��
				String strSql = "select * from footinfo where �ͷ����=";
				String[][] result = DBTools.executeQueryWithTableHead(strSql
						+ strno);

				if (result.length > 1) {
					jLabel3.setText(result[1][2]);
					jLabel5.setText(result[1][3]);
					jLabel7.setText(result[1][4]);
					jLabel15.setText(String.valueOf(result[1][5]));
				}

			} catch (Exception sqle) {
				sqle.printStackTrace();
				JOptionPane.showMessageDialog(this, "д������ʧ�ܣ�", "ʧ��",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("��ѽ�����ˣ�");
			}

		}
	}

	public void actionPerformed(ActionEvent e) {

		String str = e.getActionCommand();
		if (str.equals("true")) {
			Object sele = jComboBox1.getSelectedItem();
			String box1 = (String) (sele instanceof String ? sele : ((Vector)sele).get(0));
			if (sele instanceof String) {
				JOptionPane.showMessageDialog(this, "��ѡ�񷿼䣡", "ʧ��",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int in = Integer.parseInt(box1); // 1
			System.out.println(box1);
			String box2 = (String) jLabel3.getText(); // 2
			System.out.println(box2);
			String box3 = (String) jLabel5.getText(); // 3
			System.out.println(box3);
			String box4 = (String) jLabel7.getText();
			float flo = Float.parseFloat(box4); // 4
			System.out.println(flo);
			String txt1 = jTextField1.getText(); // 5
			System.out.println(txt1);
			String txt2 = jTextField2.getText(); // 6
			System.out.println(txt2);
			String txt3 = jTextField3.getText(); // 7
			System.out.println(txt3);
			String box5 = (String) jLabel15.getText();
			float fl = Float.parseFloat(box5); // 8
			System.out.println(fl);
			String tax1 = jTextArea1.getText(); // 9
			System.out.println(tax1);
			String box6 = (String) jComboBox2.getSelectedItem(); // 10
			int f = Integer.parseInt(box6);

			String txt4 = jTextField4.getText();
			int o = Integer.parseInt(txt4); // ��ˮ��
			String h2omane = jTextField4.getText(); // �õ���ˮ��String

			System.out.println("h2omane��һ�κ�" + h2omane);
			Connection con = null;
			try {
				con = ConnectionPool.getDBConnection();
				String strSql = "insert into dingfangxinxi(id,�ͷ����,�ͷ�����,�ͷ�λ��,�ͷ�����,�˿�����,�˿����֤����,��ס����,�ۿ�,��ע��Ϣ,�˵���ˮ��) values (?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(strSql);

				ps.setInt(1, Types.NULL);
				ps.setInt(2, in);
				ps.setString(3, box2);
				ps.setString(4, box3);
				ps.setFloat(5, flo);
				ps.setString(6, txt1);
				ps.setString(7, txt2);
				ps.setString(8, txt3);
				ps.setFloat(9, fl);
				ps.setString(10, tax1);
				// ps.setFloat(10,f); //�Ƿ���ס
				ps.setInt(11, o); // ������ˮ��
				ps.executeUpdate();

				String strno = (String) ((Vector) jComboBox1.getSelectedItem()).get(0); // �õ��ͱ�
				String strSql1 = "update footinfo set �Ƿ���ס=?";
				String strno1 = " where �ͷ����=";

				System.out.println("�Ƿ���ס" + f);
				System.out.println(strSql1 + strno1 + strno);

				PreparedStatement pss = con.prepareStatement(strSql1 + strno1
						+ strno);
				pss.setInt(1, f);
				pss.executeUpdate();

				System.out.println("1��0�����");

				JOptionPane.showMessageDialog(this, "�Ǽǳɹ���", "�ɹ�",
						JOptionPane.INFORMATION_MESSAGE);
				// sta.close();
			} catch (Exception sqle) {
				sqle.printStackTrace();
				JOptionPane.showMessageDialog(this, "д������ʧ�ܣ�", "ʧ��",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("����aaa");
			} finally {
				try {
					ConnectionPool.putDBConnection(con);
				} catch (SQLException e1) {
					throw new RuntimeException(e1);
				}
			}

		}
		if (str.equals("false")) {
			this.setVisible(false);
			;
		}
	}

}