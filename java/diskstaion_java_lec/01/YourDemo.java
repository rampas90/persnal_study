import java.lang.*;
// java.lang��Ű���� ����Ʈ ���̹Ƿ� �����ص� ��

public class YourDemo
{
	//1. main()�޼ҵ带 �ۼ�
	//2. �����ڸ� �����ϰ�
	//3. main()�޼ҵ� �ȿ��� �����ڸ� ȣ��
	
	//������ ����
	public YourDemo()
	{
		System.out.println("�̰��� ������");
	}

	//4. Ŭ���� �޼ҵ�(static�޼ҵ�)�� �����ϰ� �̸� main()�ȿ��� ȣ���غ���.
	static public void abc()
	{
		System.out.println("abc()");
	}

	public static void def(String str)
	{
		System.out.println(str);
	}


	//����޼ҵ� - static �����ڰ� ���� �ʴ� �޼ҵ�
	public void foo()
	{
		System.out.println("foo()...");
	}

	//���� �ΰ� static�� ���� �޼ҵ�� �ݵ�� Ŭ�������� �ٿ���� �Ѵ�.


	public static void main(String[] args)
	{
		System.out.println("--main()����--");

		//������Ÿ�� ������ = new ������();
		YourDemo your = new YourDemo();
		YourDemo.abc();
		YourDemo.def("Welcome");
		
		//Ŭ���� �޼ҵ� ȣ���� ���� Ŭ������.�޼ҵ��();
		
		//����޼ҵ带 ȣ���� ����
		//1) ���� ��ü�� �����ϰ�(�����ڸ� ȣ�����ְ�)
		//   �� ��ü�� �����ϰ� �ִ� ������.�޼ҵ��();  �� ������� ȣ���Ѵ�
		//   ��, ��ü��.�޼ҵ��();

		//��ü����
		YourDemo y = new YourDemo();
		y.foo();

	}
}




/*

comment

������ ���������� ���� �����ϰ� �Ѿ �� 


*/