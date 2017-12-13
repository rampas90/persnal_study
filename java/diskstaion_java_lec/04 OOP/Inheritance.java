public class Inheritance
{
	public static void main(String[] args) 
	{	
		/*
			아래 소스는 부모클래스의 getInfo()메소드에 power가 없기때문에 
			이름과 키만 찍어주고 power는 안찍어준다
		*/
		Superman s1 = new Superman();
		s1.name="슈퍼맨1";
		s1.height=190;
		s1.power=2000;
		String info = s1.getInfo();
		System.out.println(info);
		
		String info2 = s1.getInfo("----슈퍼맨 정보-----");
		System.out.println(info2);

		System.out.println("=================");

		Human h1 = new Human();
		h1.name="사람1";
		h1.height=188;
		String str2 = h1.getInfo();
		System.out.println(str2);

		System.out.println("=================");

		Aquaman a1 = new Aquaman();
		a1.name="아쿠아맨1";
		a1.height=190;
		a1.speed=1200;
		String infoa = a1.getInfo();
		System.out.println(infoa);

		a1.getInfo("-----아쿠아맨 정보-----",50);


		System.out.println("=================");

	}
}

// 부모 클래스 - Super Class
class Human
{
	String name;
	int height;

	public String getInfo()
	{
		String str="이름: "+name+"\n키: "+height;
		return str;
	}
}

class Superman extends Human
{
	int power;

	// 메소드 Overriding(재정의) 부모
	// 1. 부모의 것과 동일한 메소드명
	// 2. 매개변수도 동일하게
	// 3. 반환타입도 동일하게
	// 4. 접근지정자는 부모의 것과 동일하거나 더 넓은 범위의 지정자를 사용
	// 5. Exception의 경우 부모 클래스의 메소드와 동일하거나 
	//    더 구체적인 Exception을 발생시켜야한다.

	//오버라이딩
	public String getInfo()
	{
		//String str="이름: "+name+"\n키: "+height+"\n힘: "+power;
		String str = super.getInfo()+"\n파워: "+power;
		return str;
	}

	//오버로딩
	public String getInfo(String title)
	{
		String str = title+"\n"+this.getInfo();
		return str;
	}
}

class Aquaman extends Human
{
	int speed;

	public String getInfo()
	{
		//String str="이름: "+name+"\n키: "+height+"\n속도: "+speed;
		String str=super.getInfo()+"\n속도: "+speed;
		return str;
	}

	//오버로딩
	public void getInfo(String title, int speed)
	{
		System.out.println(title);
		System.out.println(this.getInfo());
		System.out.println("--- 스피드 ---");
		this.speed += speed;
		System.out.println("속도: "+this.speed);
	}
}