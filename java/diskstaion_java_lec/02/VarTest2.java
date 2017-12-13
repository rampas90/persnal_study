/*
	한페이지에서 여러 클래스를 구성할 경우 
	public 속성은 한 클래스만 가져야 한다.
	주로 main()메소드를 가지는 클래스가 public 속성을 가지며, 저장할 때는 public 속성을 가지는
	클래스 이름으로 파일명을 주어야 한다.
	이때 컴파일을 하면 관련된 클래스파일이 모두 생김.
*/
class Demo
{
	static int x = 50; 
	       int y = 60;

	//생성자 이름은 클래스이름과 동일해야한다
	public Demo()
	{
		
	}

	public void foo()
	{
		System.out.println("foo()***");
	}

	public static String bar()
	{
		return "Demo의 public static string bar() 메소드 입니다.";
	}
}



public class VarTest2
{

	//String 타입의 변수 str1, str2를 선언해보자
	// str1은 클래스 변수로, str2는 인스턴스 변수로 선언
	// main() 메소드 안에서 str1과 str2의 값을 출력해보자

	static String str1 = "클래스변수";
          String str2 = "인스턴스변수";

	public VarTest2()
	{

	}	

	public static void main(String[] args)
	{
		//클래스 변수는 아래처럼 클래스 이름으로 바로 사용가능
		System.out.println("str1 : "+VarTest2.str1);

		//인스턴스 변수는 생성자를 불러줘야 사용가능 (아래처럼 사용불가)
		//System.out.println("str1 : "+VarTest2.str1);
		VarTest2 v = new VarTest2();
		System.out.println("str2 : "+v.str2);


		//Demo클래스의 멤버, 클래스 변수인 x,y의 값을 출력해보자
		System.out.println("Demo의 x : " + Demo.x);

		Demo d = new Demo();
		System.out.println("Demo의 y : " + d.y);

		//Demo클래스의 foo()와 bar()메소드를 호출해보자.

		Demo d2 = new Demo();
		d2.foo();

		String s = Demo.bar();
		System.out.println(s);
		System.out.println("--변경전---");
		System.out.println("d.y : "+ d.y);
		System.out.println("d2.y : "+ d2.y);

		d2.y = 80;

		System.out.println("--변경후---");
		System.out.println("d.y : "+ d.y);
		System.out.println("d2.y : "+ d2.y);

/*
		int xx; //지역변수
		System.out.println(xx);	//지역변수를 초기화 하지 않았으므로 에러가 난다.
*/
		int xx =0 ; //지역변수
		System.out.println(xx);	


	}
}

/*
	static 이 붙은 변수든 메소드는 가장먼저 메모리에 올라간다. -> stack 영역
	클래스를 불러올때 -> Class Loader
	전역변수 -> 전체영역에서 사용가능한 변수로 클래스 로딩타임에 들어와 클래스가 종료될때 사라짐
	인스턴스 변수는 new VarTest(); 처럼 객체생성을 해야 올라간다
	heap 영역에 올라감


	VarTest2 v = new VarTest2();

*/


