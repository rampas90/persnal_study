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
		
		//	import static이 없을때 Sub클래스의 static 변수와 메소드를 불러오는 방법 
		//Sub sub = new Sub();
		//sub.subClassMethod();
		
		// import static을 사용하여 상단에 선언해주었을때 사용방법
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
		System.out.println("여기는 Package 클래스입니다.");
	}
}