package cx;

//��Ϣ��װ
public class infoPack {
	private int mNum;
	private String mType;
	private String mPlace;
	private float mPrice;
	private String mInfo;

	public infoPack(int Num, String Type, String Place, float Price, String Info) {
		mNum = Num;
		mType = Type;
		mPlace = Place;
		mPrice = Price;
		mInfo = Info;
	}

	public int getNum() {
		return (mNum);
	}

	public String getType() {
		return (mType);
	}

	public String getPlace() {
		return (mPlace);
	}

	public float getPrice() {
		return (mPrice);
	}

	public String getInfo() {
		return (mInfo);
	}

	/*
	 * public String toString() { String str = "ѧ�ţ�" + mId + "\n"; str += "������" +
	 * mName + "\n"; str += "�Ա�" + mSex + "\n"; str += "���䣺" + mAge + "\n";
	 * return (str); }
	 */
}