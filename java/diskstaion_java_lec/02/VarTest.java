// 패키지 설정 및 import 를 먼저 해줘야 하지만 기본과정은 주로 lang패키지를 사용하고
// lang 패키지의 경우 자동으로 생성되기때문에 생략해도 된다.
/*

 변수(variable, field, property, parameter)
	1. 클래스 변수 : static 변수  ->  메소드 바깥에서 선언된 변수
	2. 멤버변수 : instance 변수    ->  메소드 바깥에서 선언된 변수
	3. 지역변수 : local 변수, automatic변수  ->   메소드 블럭 안, 또는 인스턴스 블럭안에서 선언된 변수
	              
*/

public class VarTest
{

          int a = 10; // 멤버변수, 인트턴스 변수 -> static이 안들어간 변수

	static int b = 20; // 클래스 변수, static변수

	
	public VarTest()
	{
		int c = 30;	// 지역변수, 로컬변수
		System.out.println("VarTest()생성자");
		System.out.println("지역변수 c: "+c);
	}

	public static void main(String[] args)
	{
		//1. 클래스변수 b의 값을 출력해보자
		// 클래스명.변수
		System.out.println("클래스변수 b : "+VarTest.b);

		//2. 멤버변수 a의 값을 출력해보자
		//   먼저 객체를 생성한뒤, 객체명.변수
		VarTest v = new VarTest();
		System.out.println("멤버변수 a : " + v.a);

		//3. 지역변수 c의 값을 출력해보자
		//System.out.println("지역변수 c: "+ v.c);
		//지역변수는 선언된 블럭 안에서만 사용 가능
		// 지역변수를 사용하려면 생성자 안에서 사용해야 함
		int c = 300;

		System.out.println("지역변수 c : "+ c);
	
	}
}
