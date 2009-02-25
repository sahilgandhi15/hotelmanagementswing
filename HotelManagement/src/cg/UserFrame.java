/*�û�����ģ�飺  	��ӡ��޸ĺ�ɾ���û���*/

package cg;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserFrame extends JFrame {

	private javax.swing.JLabel lab, lab1, lab2;
	private javax.swing.JTextField txtName, txaType;
	private javax.swing.JPasswordField pwd;
	private JTable tabShow;
	private javax.swing.JButton btnInsert, btnDelete, btnSelect, btnExit;
	private javax.swing.JComboBox cob;
	private DatabaseUser dbo;

	public UserFrame() {

		super("");
		dbo = new DatabaseUser();
		tabShow = new JTable();
		java.awt.Container me = this.getContentPane();

		me.setLayout(new BorderLayout());
		// me.add(new NorthPanel(),BorderLayout.NORTH);
		me.add(new JScrollPane(tabShow), BorderLayout.CENTER);
		me.add(new SouthPanel(), BorderLayout.SOUTH);

		this.addWindowListener(new WindowCloseEvent());

		this.setTitle("�û�����");
		this.setSize(600, 400);
		this.setLocationRelativeTo(this);
		this.setResizable(false);
		this.setVisible(true);
	}

	// private class NorthPanel extends JPanel{
	//		
	// public NorthPanel(){
	//
	// this.setLayout(new FlowLayout());
	//			
	// lab = new JLabel("�û���");
	// txtName = new JTextField(10);
	// lab1 = new JLabel("����");
	// pwd = new JPasswordField(10);
	// lab2 = new JLabel("����");
	// //txaType = new JTextField(10);
	// cob = new JComboBox();
	// cob.addItem("��ͨ�û�");
	// cob.addItem("�����û�");
	//			
	// this.add(lab);
	// this.add(txtName);
	// this.add(lab1);
	// this.add(pwd);
	// this.add(lab2);
	// this.add(cob);
	// }
	// }

	private class SouthPanel extends JPanel implements ActionListener {

		public SouthPanel() {
			super(new FlowLayout());
			btnInsert = new JButton("����");
			btnDelete = new JButton("ɾ��");
			btnSelect = new JButton("��ѯ");
			// btnExit = new JButton("�˳�");

			add(btnInsert);
			add(btnDelete);
			add(btnSelect);
			// add(btnExit);

			btnInsert.addActionListener(this);
			btnInsert.setActionCommand("inser");

			btnDelete.addActionListener(this);
			btnDelete.setActionCommand("dele");

			// btnExit.addActionListener(this);
			// btnExit.setActionCommand("exit");

			btnSelect.addActionListener(this);
			btnSelect.setActionCommand("sele");

		}

		public void actionPerformed(ActionEvent e) {
			String strcmd = e.getActionCommand();
			if (strcmd.equals("sele")) {
				DefaultTableModel use = new DefaultTableModel(dbo
						.getDataByTabname(), dbo.getColumnNamesByTabname());
				tabShow.setModel(use);
			}
			if (strcmd.equals("inser")) {
				new UseraddFrame();
			}
			if (strcmd.equals("dele")) {
				new UserdelFrame();
			}
			// if(strcmd.equals("exit")){
			//	         		
			// }
			//            	
		}

	}

	private class WindowCloseEvent extends WindowAdapter {

		public void windowClosing(WindowEvent e) {

		}
	}
}
