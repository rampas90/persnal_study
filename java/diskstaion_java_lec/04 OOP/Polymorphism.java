public class Polymorphism
{
	public static void main(String[] args) 
	{
		Human h1 = new Human();
		h1.name = "�ΰ�";
		h1.showInfo();


		Superman s1 = new Superman("���۸�1",1000);
		s1.showInfo();
		s1.showInfo("@@@@���۸�Ÿ��Ʋ@@@@");

		// (�θ�Ÿ��) (����) = (new) (�ڽ��� ��ü����)�� �����ϴ�
		// �θ�� �ڽ��� ��Ӱ����� �� ����
		Human hs = new Superman("�̵���",300);

		System.out.println("hs.name = "+hs.name);
		//System.out.println("hs.power = "+hs.power);// �̱����� ������ �������� ������???
		System.out.println("=================");
		hs.showInfo();											// ���ܱ���
		//hs.showInfo("############");						// �̹��� ������ ����
		/*
			�θ�Ÿ������ ���� ������ �ϰ� �ڽ��� ��ü�� �������� ���
			�� ���������� ������ �� �ִ� ����
			1. �θ�κ��� �������� ����, �޼ҵ�[o]
			2. �ڽ��� ������ ������ ���� �޼ҵ�[x]
			3. �׷��� �ڽ��� �������̵��� �޼ҵ尡 ���� ��� , �� �޼ҵ带 �켱ȣ���Ѵ�.
		*/
	}
}

class Human
{
	String name;

	public void showInfo()
	{
		System.out.println("�̸�:"+name);
	}
}

class Superman extends Human
{
	int power;

	public Superman(String name, int power)
	{
		this.name=name;
		this.power=power;
	}
	
	//�������̵�
	public void showInfo()
	{
		super.showInfo();
		System.out.println("�Ŀ�: "+power);
	}

	//�����ε�
	public void showInfo(String title)
	{
		
		System.out.println(title);
		this.showInfo();
	}
}

class Aquaman extends Human
{
	int speed;
}	