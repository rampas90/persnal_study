//Console Application
//import java.lang.System;
import java.lang.*;
public class MyDemo
{
	/*
		1. ������ : ��ü�� ������ �� ȣ���Ѵ�.
		            ���Ѿ��� ��Ģ
					- ������ �̸��� Ŭ�������� �����ؾ� �Ѵ�.
					- ��ȯŸ���� ������� �ʴ´�.
					   -> static void || static int .....
					
					�ϴ���
					- ��������� �ʱ�ȭ

		2. �Ӽ�(�������)
		3. �޼ҵ�
	*/

	// 1. ������ (�Ʒ�ó�� �ۼ��ϸ� �����ڸ� '����'�ߴٰ� ��) 
	public MyDemo()
	{
		System.out.println("MyDemo() ������ �Դϴ�.");
	}

	// ����� ���� �޼ҵ�1
	public static void myfunc()
	{
		// static�� ���� �޼ҵ�� => Ŭ���� �޼ҵ�, static �޼ҵ�
		System.out.println("���� ���� �޼ҵ� myfunc().");
	}

	// ����� ���� �޼ҵ�2
	public static void a()
	{
		System.out.println("a()");
	}

	// ����� ���� �޼ҵ�3
	public static void b(String str)
	{
		// str�� StringŸ���� �Ű�����(��������),�μ�,�Ķ����
		System.out.println(str);
	}



	public static void main(String [] args)
	{
		System.out.println("--���α׷��� �����Դϴ�.--");

		//������ ȣ�� (�Ʒ�ó�� �ۼ��ϸ� �����ڸ� 'ȣ��' �ߴٰ� ��) 
		MyDemo my = new MyDemo();
		MyDemo my2 = new MyDemo();

		//�޼ҵ� ȣ��
		// 1. Ŭ���� �޼ҵ���....Ŭ�����̸�.�޼ҵ��̸�();
		MyDemo.myfunc();
		MyDemo.a();
		MyDemo.b("Hello");
		MyDemo.b("Java");

		System.out.println("--���α׷��� ���Դϴ�.--");
	}
}