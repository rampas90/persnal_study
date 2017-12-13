public class Inheritance
{
	public static void main(String[] args) 
	{	
		/*
			�Ʒ� �ҽ��� �θ�Ŭ������ getInfo()�޼ҵ忡 power�� ���⶧���� 
			�̸��� Ű�� ����ְ� power�� ������ش�
		*/
		Superman s1 = new Superman();
		s1.name="���۸�1";
		s1.height=190;
		s1.power=2000;
		String info = s1.getInfo();
		System.out.println(info);
		
		String info2 = s1.getInfo("----���۸� ����-----");
		System.out.println(info2);

		System.out.println("=================");

		Human h1 = new Human();
		h1.name="���1";
		h1.height=188;
		String str2 = h1.getInfo();
		System.out.println(str2);

		System.out.println("=================");

		Aquaman a1 = new Aquaman();
		a1.name="����Ƹ�1";
		a1.height=190;
		a1.speed=1200;
		String infoa = a1.getInfo();
		System.out.println(infoa);

		a1.getInfo("-----����Ƹ� ����-----",50);


		System.out.println("=================");

	}
}

// �θ� Ŭ���� - Super Class
class Human
{
	String name;
	int height;

	public String getInfo()
	{
		String str="�̸�: "+name+"\nŰ: "+height;
		return str;
	}
}

class Superman extends Human
{
	int power;

	// �޼ҵ� Overriding(������) �θ�
	// 1. �θ��� �Ͱ� ������ �޼ҵ��
	// 2. �Ű������� �����ϰ�
	// 3. ��ȯŸ�Ե� �����ϰ�
	// 4. ���������ڴ� �θ��� �Ͱ� �����ϰų� �� ���� ������ �����ڸ� ���
	// 5. Exception�� ��� �θ� Ŭ������ �޼ҵ�� �����ϰų� 
	//    �� ��ü���� Exception�� �߻����Ѿ��Ѵ�.

	//�������̵�
	public String getInfo()
	{
		//String str="�̸�: "+name+"\nŰ: "+height+"\n��: "+power;
		String str = super.getInfo()+"\n�Ŀ�: "+power;
		return str;
	}

	//�����ε�
	public String getInfo(String title)
	{
		String str = title+"\n"+this.getInfo();
		return str;
	}
}

class Aquaman extends Human
{
	int speed;

	public String getInfo()
	{
		//String str="�̸�: "+name+"\nŰ: "+height+"\n�ӵ�: "+speed;
		String str=super.getInfo()+"\n�ӵ�: "+speed;
		return str;
	}

	//�����ε�
	public void getInfo(String title, int speed)
	{
		System.out.println(title);
		System.out.println(this.getInfo());
		System.out.println("--- ���ǵ� ---");
		this.speed += speed;
		System.out.println("�ӵ�: "+this.speed);
	}
}