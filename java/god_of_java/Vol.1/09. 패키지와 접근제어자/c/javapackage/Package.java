package c.javapackage;

import c.javapackage.sub.Sub;
/*
import static c.javapackage.sub.Sub.subClassStaticMethod;
import static c.javapackage.sub.Sub.CLASS_NAME;
import static c.javapackage.sub.Sub.*;
*/

public class Package
{
	public static void main(String[] args)
	{
		System.out.println("Package class.");
		
		//	import static�� ������ SubŬ������ static ������ �޼ҵ带 �ҷ����� ��� 
		//Sub sub = new Sub();
		//sub.subClassMethod();
		
		// import static�� ����Ͽ� ��ܿ� �������־����� �����
		//subClassStaticMethod();
		//System.out.println(CLASS_NAME);

		Sub sub=new Sub();
		sub.publicMethod();
		sub.protectedMethod();
		sub.packagePrivateMethod();
		sub.privateMethod();

	}

	public static void subClassStaticMethod()
	{
		System.out.println("����� Package Ŭ�����Դϴ�.");
	}
}