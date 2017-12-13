public class Overloading3
{
	public static void main(String[] args) 
	{
		Superman s1 = new Superman(600,"this ������");
		s1.showInfo();
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
		name="�ƹ���";
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
		this(name,180,power); // �ٷ� �Ʒ��� �ִ� ������(�Ű������� 3���ִ�)�� �����ϰԵȴ�.
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
