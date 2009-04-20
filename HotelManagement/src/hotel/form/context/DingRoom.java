package hotel.form.context;

import hotel.form.main.MainFrame;
import hotel.service.CommandService;
import hotel.service.dingroom.DingRoomServiceCommand;
import hotel.util.DateUtil;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DingRoom extends BasePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton btnInsert, btnDelete, btnUpdate, allDingRoomInfo, inUse, btnSelect2;
	public JComboBox jComboBox1, jComboBox2, jComboBox3, jComboBox4,
			jComboBox5, jComboBox6;
	public JTextField jTextField1, jTextField2, jTextField3;
	public JTextArea jTextArea1;
	public JTable tabShow;

	public DingRoom(MainFrame parent) {
		super(parent);

		tabShow = new JTable();
		refresh();

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(tabShow), BorderLayout.CENTER);
		this.add(new SouthPanel(), BorderLayout.SOUTH);

		this.parent.setTitle("������Ϣ������");
		this.setVisible(true);

	}

	private class SouthPanel extends JPanel implements ActionListener {
		public SouthPanel() {
			super(new FlowLayout());
			btnInsert = new JButton("����");
			btnDelete = new JButton("�˷�");
			allDingRoomInfo = new JButton("��ѯ���ж�����Ϣ");
			inUse = new JButton("ʹ�÷���");
			btnSelect2 = new JButton("���˷���");

			this.add(btnInsert);
			this.add(btnDelete);
			this.add(allDingRoomInfo);
			this.add(inUse);
			this.add(btnSelect2);

			allDingRoomInfo.addActionListener(this);
			btnInsert.addActionListener(this);
			inUse.addActionListener(this);
			btnSelect2.addActionListener(this);
			btnDelete.addActionListener(this);

			allDingRoomInfo.setActionCommand("select");
			btnInsert.setActionCommand("insert");
			inUse.setActionCommand("select1");
			btnSelect2.setActionCommand("select2");
			btnDelete.setActionCommand("delete");
		}

		public void actionPerformed(ActionEvent e) {
			String str = e.getActionCommand();
			if (str.equals("select")) {
				refresh();
			}
			if (str.equals("insert")) {
				new dingRoomFrame1(DingRoom.this);
			}
			if (str.equals("select1")) {
				//inUse,δ�˷���
				DefaultTableModel dingRoom = new DefaultTableModel((Object[][]) CommandService.getInstance().execute(new DingRoomServiceCommand(DingRoomServiceCommand.getUnCheckOutDingRoomAsArrayCommand())), hotel.model.dingroom.DingRoom.getFieldMapLabel().values().toArray());
				tabShow.setModel(dingRoom);
			}
			if (str.equals("select2")) {
				//���˷���
				DefaultTableModel dingRoom = new DefaultTableModel((Object[][]) CommandService.getInstance().execute(new DingRoomServiceCommand(DingRoomServiceCommand.getCheckOutDingRoomAsArrayCommand())), hotel.model.dingroom.DingRoom.getFieldMapLabel().values().toArray());
				tabShow.setModel(dingRoom);
			}
			if (str.equals("delete")) {
				int selectedRowIndex = tabShow.getSelectedRow();
				if (selectedRowIndex != -1) {
					//���ȸ���id�õ�DingRoomʵ��
					String id = (String) tabShow.getModel().getValueAt(selectedRowIndex, 0);
					Map condition = new HashMap();
					condition.put("id", id);
					hotel.model.dingroom.DingRoom dingRoom = (hotel.model.dingroom.DingRoom) CommandService.getInstance().execute(new DingRoomServiceCommand(DingRoomServiceCommand.getByIdCommand(), condition));
					if (dingRoom.getEnd() != null) {
						JOptionPane.showMessageDialog(DingRoom.this, "��ѡ�񷿼�����ʹ�õķ���", "������ʾ", JOptionPane.ERROR_MESSAGE);
					} else {
						String roomNum = (String) tabShow.getModel().getValueAt(selectedRowIndex, 2);
						if (JOptionPane.showConfirmDialog(DingRoom.this, "ȷ��Ҫ�˷����Ϊ��" + roomNum + "�ķ�����") == JOptionPane.OK_OPTION) {
							dingRoom.setEnd(DateUtil.getNow());
							dingRoom.setFootState("δ����");
							condition.put("entity", dingRoom);
							CommandService.getInstance().execute(new DingRoomServiceCommand(DingRoomServiceCommand.getSaveOrUpdateEntryCommand(), condition));
							refresh();
						}
					}
				} else {
					JOptionPane.showMessageDialog(DingRoom.this, "��ѡ�񷿼�", "������ʾ", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public void access(MainFrame vistor) {
		vistor.visit(this);
	}

	@Override
	public void refresh() {
		DefaultTableModel dingRoom = new DefaultTableModel((Object[][]) CommandService.getInstance().execute(new DingRoomServiceCommand(DingRoomServiceCommand.getAllAsArrayCommand())), hotel.model.dingroom.DingRoom.getFieldMapLabel().values().toArray());
		tabShow.setModel(dingRoom);
	}

}