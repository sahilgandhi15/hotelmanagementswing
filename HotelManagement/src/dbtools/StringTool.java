package dbtools;

import java.util.Collection;
import java.util.LinkedList;

/**
 * ����һЩ���õ��ַ���������
 */
public class StringTool {
	/**
	 * �������ı�ʶ�Ƿ�������Զ��ŷָ��ı�ʶ����
	 * 
	 * @param indicate
	 *            ��ʶ
	 * @param indicates
	 *            ��ʶ�� return �Ƿ����
	 */
	public static boolean contains(String indicate, String indicates) {
		// �ѱ�ʶ��ת�����б�
		Collection result = stringToLinkedList(indicates);
		// �����б��Ƿ������
		return result.contains(indicate);
	}

	/**
	 * ���ַ���תΪ����
	 */
	public static int stringToInt(String number) {
		try {
			int result = Integer.parseInt(number);
			return result;
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	/**
	 * ����תΪ�ַ���
	 */
	public static String intToString(int number) {
		String result = String.valueOf(number);
		return result;
	}

	/**
	 * ���Ը����ַ��ָ���ַ����ֽ���ַ�������
	 */
	public static LinkedList stringToLinkedList(String source, char ch) {
		LinkedList v = new LinkedList();
		if (source == null || source.equals("")) {
			return v;
		}
		int start = 0;
		int end = source.indexOf(ch);
		while (end != -1) {
			String str = source.substring(start, end);
			v.add(str);
			start = end + 1;
			end = source.indexOf(ch, start);
		}
		String str = source.substring(start);
		v.add(str);
		return v;
	}

	/**
	 * ����','�ָ���ַ����ֽ���ַ�������
	 */
	public static LinkedList stringToLinkedList(String source) {
		return stringToLinkedList(source, ',');
	}
}