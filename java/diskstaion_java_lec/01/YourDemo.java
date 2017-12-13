import java.lang.*;
// java.lang패키지는 디폴트 값이므로 생략해도 됨

public class YourDemo
{
	//1. main()메소드를 작성
	//2. 생성자를 구성하고
	//3. main()메소드 안에서 생성자를 호출
	
	//생성자 구성
	public YourDemo()
	{
		System.out.println("이것이 생성자");
	}

	//4. 클래스 메소드(static메소드)를 구성하고 이를 main()안에서 호출해보자.
	static public void abc()
	{
		System.out.println("abc()");
	}

	public static void def(String str)
	{
		System.out.println(str);
	}


	//멤버메소드 - static 지정자가 붙지 않는 메소드
	public void foo()
	{
		System.out.println("foo()...");
	}

	//위의 두개 static이 붙은 메소드는 반드시 클래스명을 붙여줘야 한다.


	public static void main(String[] args)
	{
		System.out.println("--main()시작--");

		//데이터타입 변수명 = new 생성자();
		YourDemo your = new YourDemo();
		YourDemo.abc();
		YourDemo.def("Welcome");
		
		//클래스 메소드 호출할 때는 클래스명.메소드명();
		
		//멤버메소드를 호출할 때는
		//1) 먼저 객체를 생성하고(생성자를 호출해주고)
		//   그 객체를 참조하고 있는 변수명.메소드명();  의 방식으로 호출한다
		//   즉, 객체명.메소드명();

		//객체생성
		YourDemo y = new YourDemo();
		y.foo();

	}
}




/*

comment

생성자 생성에대한 내용 숙지하고 넘어갈 것 


*/