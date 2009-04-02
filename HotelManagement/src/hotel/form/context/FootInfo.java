package hotel.form.context;

import hotel.form.main.MainFrame;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class FootInfo extends BasePanel implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] strtf = { "0" };
	// String[] strfj={"101","102","103","201","202","203","301","302","303"};
	public JButton jButton1, jButton2;
	public JTextField jTextField1 = new JTextField();
	public JComboBox jComboBox1 = new JComboBox(/*dbo.getAllTabelName2()*/);
	public JComboBox jComboBox2 = new JComboBox(strtf);

	public JLabel jLabel3 = new JLabel();
	public JLabel jLabel5 = new JLabel();
	public JLabel jLabel7 = new JLabel();
	public JLabel jLabel9 = new JLabel();
	public JLabel jLabel11 = new JLabel();
	public JLabel jLabel13 = new JLabel();
	public JComboBox jTextArea1 = new JComboBox(/*dbo.getAllTabelName3()*/); // ��Ϊ�ʵ���
	public JLabel jLabel14 = new JLabel();

	public static float jg, zk, rztime, tftime;
	public static float addname;
	public static String strbox1, strtxt1;
	public static String str; // ��̬��ˮ��

	public FootInfo(MainFrame parent) {
		super(parent);
		JLabel jLabel1 = new JLabel();
		JLabel jLabel2 = new JLabel();
		JLabel jLabel4 = new JLabel();
		JLabel jLabel6 = new JLabel();
		JLabel jLabel8 = new JLabel();
		JLabel jLabel10 = new JLabel();
		JLabel jLabel12 = new JLabel();
		// JLabel jLabel14 = new JLabel();
		// JTextField jTextField1 = new JTextField();
		JLabel jLabel15 = new JLabel();
		// JComboBox jComboBox2 = new JComboBox(strtf);
		jComboBox2.enable(false);
		JLabel jLabel16 = new JLabel();
		// JTextArea jTextArea1 = new JTextArea();
		JButton jButton1 = new JButton();
		JButton jButton2 = new JButton();

		this.setLayout(null);
		setSize(new Dimension(471, 300));
		jLabel1.setText("�ͷ����");
		jLabel1.setBounds(new Rectangle(18, 35, 57, 25));
		jComboBox1.setBounds(new Rectangle(75, 35, 80, 26));
		jComboBox1.addItem("ѡ�񷿺�");
		jComboBox1.setSelectedItem("ѡ�񷿺�");
		jLabel2.setText("�ͷ�����");
		jLabel2.setBounds(new Rectangle(165, 35, 60, 23));
		jLabel3.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel3.setBounds(new Rectangle(225, 35, 71, 26));
		jLabel4.setText("�ͷ�����");
		jLabel4.setBounds(new Rectangle(306, 35, 60, 23));
		jLabel5.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel5.setBounds(new Rectangle(367, 35, 79, 26));
		jLabel6.setText("�˿�����");
		jLabel6.setBounds(new Rectangle(18, 91, 57, 25));
		jLabel7.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel7.setBounds(new Rectangle(75, 91, 80, 26));
		jLabel8.setText("�˿����֤����");
		jLabel8.setBounds(new Rectangle(165, 90, 94, 25));
		jLabel9.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel9.setBounds(new Rectangle(270, 90, 176, 26));
		jLabel10.setText("��סʱ��");
		jLabel10.setBounds(new Rectangle(18, 147, 57, 25));
		jLabel11.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel11.setBounds(new Rectangle(75, 147, 80, 26));
		jLabel12.setDisplayedMnemonic('0');
		jLabel12.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel12.setText("�ۿ�");
		jLabel12.setBounds(new Rectangle(165, 147, 60, 23));
		jLabel13.setBorder(BorderFactory.createLoweredBevelBorder());
		jLabel13.setBounds(new Rectangle(225, 147, 71, 26));
		jLabel14.setText("�˷�ʱ��");
		jLabel14.setBounds(new Rectangle(306, 147, 60, 23));
		jTextField1.setBounds(new Rectangle(367, 147, 79, 26));
		jLabel15.setText("�Ƿ���ס");
		jLabel15.setBounds(new Rectangle(165, 212, 60, 23));
		jComboBox2.setBounds(new Rectangle(225, 212, 71, 26));
		jLabel16.setText("�ʵ���");
		jLabel16.setBounds(new Rectangle(306, 212, 60, 23));
		jTextArea1.setBounds(new Rectangle(367, 212, 70, 26));
		jTextArea1.addItem("ѡ���˵�");
		jTextArea1.setSelectedItem("ѡ���˵�");
		jButton1.setBounds(new Rectangle(18, 212, 60, 23));
		jButton1.setText("����");
		jButton2.setBounds(new Rectangle(89, 212, 60, 23));
		jButton2.setText("ȡ��");
		this.add(jComboBox1);
		this.add(jLabel7);
		this.add(jLabel12);
		this.add(jLabel13);
		this.add(jLabel11);
		this.add(jTextField1);
		this.add(jLabel1);
		this.add(jLabel3);
		this.add(jLabel2);
		this.add(jLabel5);
		this.add(jLabel6);
		this.add(jLabel10);
		this.add(jLabel8);
		this.add(jLabel9);
		this.add(jLabel4);
		this.add(jLabel14);
		this.add(jLabel16);
		this.add(jComboBox2);
		this.add(jButton2);
		this.add(jLabel15);
		this.add(jButton1);
		this.add(jTextArea1);

		jButton1.addActionListener(this);
		jButton2.addActionListener(this);

		jComboBox1.addItemListener(this);
		jTextArea1.addItemListener(new add());

		jButton1.setActionCommand("true");
		jButton2.setActionCommand("false");

		this.parent.setTitle("���ʴ���");
		this.setVisible(true);
	}

	public void itemStateChanged(ItemEvent ie) {
		jComboBox1.removeItem("ѡ�񷿺�");
		String str = (String) ((Vector) jComboBox1.getSelectedItem()).get(0);
		try {
			String strsql = "select * from footinfo where �ͷ���� =";
			String[][] result = null;//DBTools.executeQueryWithTableHead(strsql + str);
			System.out.println(strsql + str);

			jLabel3.setText(result[1][2]); // ������
			jLabel5.setText(result[1][4]); // ������
			jLabel13.setText(result[1][5]); // �ۿ�

		} catch (Exception sql) {
			sql.printStackTrace();
		}
	}

	public void add() {
		jg = java.lang.Float.parseFloat(jLabel5.getText());
		rztime = java.lang.Float.parseFloat(jLabel11.getText());
		zk = java.lang.Float.parseFloat(jLabel13.getText());
		tftime = java.lang.Float.parseFloat(jTextField1.getText());

		addname = (tftime - rztime) * jg * zk;

		strbox1 = (String) jComboBox2.getSelectedItem();
		strtxt1 = jTextField1.getText();

		System.out.println(jg);
		System.out.println(rztime);
		System.out.println(zk);
		System.out.println(tftime);
	}

	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals("false")) {
			this.setVisible(false);
		}
		if (str.equals("true")) {
			String a = jTextField1.getText();
			if (a.equals("")) {
				JOptionPane.showMessageDialog(this, "������д�˷�ʱ�䣡", "��ʾ",
						JOptionPane.ERROR_MESSAGE);

			} else {

				jComboBox2.enable(true);
				add();
				//new jiesuan();
			}
		}
	}

	class add implements ItemListener {

		public void itemStateChanged(ItemEvent ie) {
			jTextArea1.removeItem("ѡ���˵�");
			try {
				str = (String) ((Vector) jTextArea1.getSelectedItem()).get(0);

				System.out.println("�ڵ���Ԫ���ǣ�" + str);

				String strsql = "select * from dingfangxinxi where �˵���ˮ�� =";
				String[][] result = null;//DBTools.executeQueryWithTableHead(strsql + str + " and ʹ��״̬ = '����'");

				jLabel7.setText(result[1][6]); // ����
				jLabel9.setText(result[1][7]); // ���֤
				jLabel11.setText(result[1][8]); // ��סʱ��

			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("������¼������ˣ�");
			}
		}
	}
	
	public void access(MainFrame vistor) {
		vistor.visit(this);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
