public class HisDemo
{
	//생성자를 구성(생성) 이때 이름은 클래스명과 동일하게 
	// 즉 main()메소드 안에서 생성자를 '호출'할때는 클래스명이 아니라 생성자명을 생성한다고한다.

	public HisDemo()
	{
		System.out.println("생성자..");
	}

	// 1. 클래스 메소드 - 반환타입이 있는 메소드
	public static int a()
	{
		System.out.println("a()메소드...");
		return 100;
	}

	//2. 클래스 메소드 - 반환타입이 String인 메소드를 만들고 이를 main()에서 호출
	public static String b()
	{
		System.out.println("b()메소드...");
		return "200";
	}
	
	//3. 멤버메소드를 만들되 반환타입이 int인 메소드를 구성하고, main()안에서 이를 호출해보자
	public int c()
	{
		System.out.println("c()");
		return 300;

	}


	public static void main(String[] args)
	{
		//클래스 메소드 a()호출
		int money=HisDemo.a();
		System.out.println(money);
		//클래스 메소드 ba()호출
		String money2=HisDemo.b();
		System.out.println(money2);
		//멤버메소드 c()호출
		HisDemo h = new HisDemo();
		int m = h.c();
		System.out.println(m);
	}
}