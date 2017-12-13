//Console Application
//import java.lang.System;
import java.lang.*;
public class MyDemo
{
	/*
		1. 생성자 : 객체를 생성할 때 호출한다.
		            지켜야할 규칙
					- 생성자 이름과 클래스명은 동일해야 한다.
					- 반환타입은 명시하지 않는다.
					   -> static void || static int .....
					
					하는일
					- 멤버변수의 초기화

		2. 속성(멤버변수)
		3. 메소드
	*/

	// 1. 생성자 (아래처럼 작성하면 생성자를 '구성'했다고 함) 
	public MyDemo()
	{
		System.out.println("MyDemo() 생성자 입니다.");
	}

	// 사용자 정의 메소드1
	public static void myfunc()
	{
		// static이 붙은 메소드는 => 클래스 메소드, static 메소드
		System.out.println("내가 만든 메소드 myfunc().");
	}

	// 사용자 정의 메소드2
	public static void a()
	{
		System.out.println("a()");
	}

	// 사용자 정의 메소드3
	public static void b(String str)
	{
		// str은 String타입의 매개변수(지역변수),인수,파라미터
		System.out.println(str);
	}



	public static void main(String [] args)
	{
		System.out.println("--프로그램의 시작입니다.--");

		//생성자 호출 (아래처럼 작성하면 생성자를 '호출' 했다고 함) 
		MyDemo my = new MyDemo();
		MyDemo my2 = new MyDemo();

		//메소드 호출
		// 1. 클래스 메소드라면....클래스이름.메소드이름();
		MyDemo.myfunc();
		MyDemo.a();
		MyDemo.b("Hello");
		MyDemo.b("Java");

		System.out.println("--프로그램의 끝입니다.--");
	}
}