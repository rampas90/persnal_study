public class HisDemo
{
	//�����ڸ� ����(����) �̶� �̸��� Ŭ������� �����ϰ� 
	// �� main()�޼ҵ� �ȿ��� �����ڸ� 'ȣ��'�Ҷ��� Ŭ�������� �ƴ϶� �����ڸ��� �����Ѵٰ��Ѵ�.

	public HisDemo()
	{
		System.out.println("������..");
	}

	// 1. Ŭ���� �޼ҵ� - ��ȯŸ���� �ִ� �޼ҵ�
	public static int a()
	{
		System.out.println("a()�޼ҵ�...");
		return 100;
	}

	//2. Ŭ���� �޼ҵ� - ��ȯŸ���� String�� �޼ҵ带 ����� �̸� main()���� ȣ��
	public static String b()
	{
		System.out.println("b()�޼ҵ�...");
		return "200";
	}
	
	//3. ����޼ҵ带 ����� ��ȯŸ���� int�� �޼ҵ带 �����ϰ�, main()�ȿ��� �̸� ȣ���غ���
	public int c()
	{
		System.out.println("c()");
		return 300;

	}


	public static void main(String[] args)
	{
		//Ŭ���� �޼ҵ� a()ȣ��
		int money=HisDemo.a();
		System.out.println(money);
		//Ŭ���� �޼ҵ� ba()ȣ��
		String money2=HisDemo.b();
		System.out.println(money2);
		//����޼ҵ� c()ȣ��
		HisDemo h = new HisDemo();
		int m = h.c();
		System.out.println(m);
	}
}