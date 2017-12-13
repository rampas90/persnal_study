public class Overloading
{
	public static void main(String[] args) 
	{	

		Superman s1 = new Superman();
		s1.showInfo();

		Superman s2 = new Superman("���۸�2");
		s2.showInfo();

		Superman s3 = new Superman("���۸�3",200);
		s3.showInfo();
	}
}

class Superman
{
	String name;	//null
	int height;		//0
	int power;		//0
	
	//�⺻ ������ 
	public Superman()
	{
		name="�ƹ���";  // ��������� �ʱ�ȭ ��� ( �������� �ֵ� ��� )
		height=170;
		power=200;
	}

	//���� ������
	public Superman(String name)
	{
		this.name=name;
	}

	public Superman(String name, int h)
	{
		this.name=name;
		height=h;
	}

	public Superman(int power, String name)
	{
		this.name=name;
		this.power=power;
	}

	public Superman(String name, int h, int p)
	{
		this.name=name;
		height=h;
		power=p;
	}

	public void showInfo()
	{
		System.out.println("===Superman Info===");
		System.out.println("�̸� : "+name);
		System.out.println("Ű : "+height);
		System.out.println("�� : "+power);
	}
}

s