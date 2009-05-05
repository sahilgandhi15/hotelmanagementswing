package hotel.form.context;

import hotel.form.main.MainFrame;
import hotel.hibernate.HibernateProxyUtil;
import hotel.model.dingroom.DingRoom;
import hotel.model.user.Guest;
import hotel.model.user.User;
import hotel.model.user.VIPUser;
import hotel.service.CommandService;
import hotel.service.dingroom.DingRoomServiceCommand;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class FootInfo extends BasePanel implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] strtf = { "0" };
	public JButton jButton1, jButton2;
	public JTextField jTextField1 = new JTextField();
	public JComboBox waitForFoot = new JComboBox();
	public JComboBox jComboBox2 = new JComboBox(strtf);

	public JLabel jLabel3 = new JLabel();
	public JLabel jLabel5 = new JLabel();
	public JLabel jLabel7 = new JLabel();
	public JLabel jLabel9 = new JLabel();
	public JLabel jLabel11 = new JLabel();
	public JLabel jLabel13 = new JLabel();
	public JComboBox jTextArea1 = new JComboBox(); 
	public JLabel jLabel14 = new JLabel();

	public static float jg, zk, rztime, tftime;
	public static float addname;
	public static String strbox1, strtxt1;
	public static String str; // ��̬��ˮ��
	
	private hotel.model.dingroom.DingRoom selectedDingRoom;

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
		waitForFoot.setBounds(new Rectangle(75, 35, 80, 26));
		this.initWaitForFoot(waitForFoot);
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
		jTextField1.setEditable(false);
		jLabel15.setText("�Ƿ���ס");
		jLabel15.setBounds(new Rectangle(165, 212, 60, 23));
		jComboBox2.setBounds(new Rectangle(225, 212, 71, 26));
		jLabel16.setText("�ʵ���");
		jLabel16.setBounds(new Rectangle(306, 212, 60, 23));
		jButton1.setBounds(new Rectangle(18, 212, 60, 23));
		jButton1.setText("����");
		jButton2.setBounds(new Rectangle(89, 212, 60, 23));
		jButton2.setText("ȡ��");
		this.add(waitForFoot);
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
		//this.add(jLabel16);
		//this.add(jComboBox2);
		//this.add(jButton2);
		//this.add(jLabel15);
		this.add(jButton1);

		jButton1.addActionListener(this);
		jButton2.addActionListener(this);

		waitForFoot.addItemListener(this);

		jButton1.setActionCommand("true");
		jButton2.setActionCommand("false");

		this.parent.setTitle("���ʴ���");
		//this.setVisible(true);
	}

	private void initWaitForFoot(JComboBox waitForFoot) {
		waitForFoot.removeAllItems();
		List<?> unFootDingRoom = (List<?>) CommandService.getInstance().execute(new DingRoomServiceCommand(DingRoomServiceCommand.getEndedAndUnfootDingRoomCommand()), false);
		for (Iterator iter = unFootDingRoom.iterator(); iter.hasNext(); ) {
			hotel.model.dingroom.DingRoom dingRoom = (hotel.model.dingroom.DingRoom) iter.next();
			waitForFoot.addItem(dingRoom.getRoom().getRoomNum());
		}
		CommandService.getInstance().close();
		waitForFoot.addItem("ѡ�񷿺�");
		waitForFoot.setSelectedItem("ѡ�񷿺�");
	}

	public void itemStateChanged(ItemEvent ie) {
		//waitForFoot.removeItem("ѡ�񷿺�");
		String str = (String) waitForFoot.getSelectedItem();
		try {
			if (str != null && !str.equals("ѡ�񷿺�")) {
				Map condition = new HashMap();
				condition.put("roomNum", str);
				selectedDingRoom = (DingRoom) ((List)CommandService.getInstance().execute(new DingRoomServiceCommand(DingRoomServiceCommand.getUnfootDingRoomByRoomNumCommand(), condition), false)).get(0);
				jLabel3.setText(selectedDingRoom.getRoom().getType()); // ������
				jLabel5.setText(selectedDingRoom.getRoom().getPrise() + ""); // ������
				jLabel13.setText(selectedDingRoom.getDiscount() + ""); // �ۿ�
				jLabel7.setText(selectedDingRoom.getUser().getName()); // ����
				User user = selectedDingRoom.getUser();
				user = (User) HibernateProxyUtil.getImplementation(user);
				String identifier = null;
				if (user instanceof Guest) {
					identifier = ((Guest)user).getIdentifier();
				} else if (user instanceof VIPUser) {
					identifier = ((VIPUser)user).getIdentifier();
				}
				jLabel9.setText(identifier); // ���֤
				jLabel11.setText(selectedDingRoom.getStart() + ""); // ��סʱ��
				jTextField1.setText(selectedDingRoom.getEnd() + "");
				jTextField1.setEditable(false);
				CommandService.getInstance().close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean foot(DingRoom selectedDingRoom) {
		hotel.model.footinfo.FootInfo footInfo = selectedDingRoom.createFootInfo();
		CashierFrame cashierFrame = new CashierFrame(footInfo);
		boolean cashed = cashierFrame.isCashed();
		if (cashed) {
			selectedDingRoom.setFootState("�ѽ���");
			CommandService.getInstance().assignId(footInfo);
			Map condition = new HashMap();
			condition.put("entity", selectedDingRoom);
			CommandService.getInstance().execute(new DingRoomServiceCommand(DingRoomServiceCommand.getSaveOrUpdateEntryCommand(), condition));
			waitForFoot.removeItem(selectedDingRoom.getRoom().getRoomNum());
		}
		return cashed;
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		boolean cashed = false;
		if (command.equals("true")) {
			if (selectedDingRoom != null) {
				cashed = foot(selectedDingRoom);
				selectedDingRoom = null;
			} else {
				String str = (String) waitForFoot.getSelectedItem();
				if (str != null && !str.equals("ѡ�񷿺�")) {
					Map condition = new HashMap();
					condition.put("roomNum", str);
					selectedDingRoom = (DingRoom) ((List)CommandService.getInstance().execute(new DingRoomServiceCommand(DingRoomServiceCommand.getUnfootDingRoomByRoomNumCommand(), condition), false)).get(0);
					cashed = foot(selectedDingRoom);
					selectedDingRoom = null;
				}
			}
			if (cashed) {
				JOptionPane.showMessageDialog(FootInfo.this, "�ѽ���", "��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);				
			}
		}
	}

	public void access(MainFrame vistor) {
		vistor.visit(this);
	}

	@Override
	public void refresh() {
	}
}
