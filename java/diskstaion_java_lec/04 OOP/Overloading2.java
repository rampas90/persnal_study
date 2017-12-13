public class Overloading2
{
	public static void main(String[] args) 
	{

		Aquaman a1 = new Aquaman();
		a1.showInfo();

		Aquaman a2 = new Aquaman("����Ƹ�2");
		a2.showInfo();

		Aquaman a3 = new Aquaman("����Ƹ�3",189,490);
		a3.showInfo();

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

class Aquaman
{
	String name;
	int height;
	int speed;

	public Aquaman()
	{
		name="�⺻��";
		height=100;
		speed=200;
	}

	public Aquaman(String name, int h, int s)
	{
		this.name=name;
		height=h;
		speed=s;
	}

	public Aquaman(String name)
	{
		this.name=name;
	}

	public void showInfo()
	{
		System.out.println("===Aquaman Info===");
		System.out.println("�̸� : "+name);
		System.out.println("Ű : "+height);
		System.out.println("�ӵ� : "+speed);
	}
}
