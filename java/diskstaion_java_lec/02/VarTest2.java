/*
	������������ ���� Ŭ������ ������ ��� 
	public �Ӽ��� �� Ŭ������ ������ �Ѵ�.
	�ַ� main()�޼ҵ带 ������ Ŭ������ public �Ӽ��� ������, ������ ���� public �Ӽ��� ������
	Ŭ���� �̸����� ���ϸ��� �־�� �Ѵ�.
	�̶� �������� �ϸ� ���õ� Ŭ���������� ��� ����.
*/
class Demo
{
	static int x = 50; 
	       int y = 60;

	//������ �̸��� Ŭ�����̸��� �����ؾ��Ѵ�
	public Demo()
	{
		
	}

	public void foo()
	{
		System.out.println("foo()***");
	}

	public static String bar()
	{
		return "Demo�� public static string bar() �޼ҵ� �Դϴ�.";
	}
}



public class VarTest2
{

	//String Ÿ���� ���� str1, str2�� �����غ���
	// str1�� Ŭ���� ������, str2�� �ν��Ͻ� ������ ����
	// main() �޼ҵ� �ȿ��� str1�� str2�� ���� ����غ���

	static String str1 = "Ŭ��������";
          String str2 = "�ν��Ͻ�����";

	public VarTest2()
	{

	}	

	public static void main(String[] args)
	{
		//Ŭ���� ������ �Ʒ�ó�� Ŭ���� �̸����� �ٷ� ��밡��
		System.out.println("str1 : "+VarTest2.str1);

		//�ν��Ͻ� ������ �����ڸ� �ҷ���� ��밡�� (�Ʒ�ó�� ���Ұ�)
		//System.out.println("str1 : "+VarTest2.str1);
		VarTest2 v = new VarTest2();
		System.out.println("str2 : "+v.str2);


		//DemoŬ������ ���, Ŭ���� ������ x,y�� ���� ����غ���
		System.out.println("Demo�� x : " + Demo.x);

		Demo d = new Demo();
		System.out.println("Demo�� y : " + d.y);

		//DemoŬ������ foo()�� bar()�޼ҵ带 ȣ���غ���.

		Demo d2 = new Demo();
		d2.foo();

		String s = Demo.bar();
		System.out.println(s);
		System.out.println("--������---");
		System.out.println("d.y : "+ d.y);
		System.out.println("d2.y : "+ d2.y);

		d2.y = 80;

		System.out.println("--������---");
		System.out.println("d.y : "+ d.y);
		System.out.println("d2.y : "+ d2.y);

/*
		int xx; //��������
		System.out.println(xx);	//���������� �ʱ�ȭ ���� �ʾ����Ƿ� ������ ����.
*/
		int xx =0 ; //��������
		System.out.println(xx);	


	}
}

/*
	static �� ���� ������ �޼ҵ�� ������� �޸𸮿� �ö󰣴�. -> stack ����
	Ŭ������ �ҷ��ö� -> Class Loader
	�������� -> ��ü�������� ��밡���� ������ Ŭ���� �ε�Ÿ�ӿ� ���� Ŭ������ ����ɶ� �����
	�ν��Ͻ� ������ new VarTest(); ó�� ��ü������ �ؾ� �ö󰣴�
	heap ������ �ö�


	VarTest2 v = new VarTest2();

*/


