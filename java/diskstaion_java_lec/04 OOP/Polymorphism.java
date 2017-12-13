public class Polymorphism
{
	public static void main(String[] args) 
	{
		Human h1 = new Human();
		h1.name = "인간";
		h1.showInfo();


		Superman s1 = new Superman("슈퍼맨1",1000);
		s1.showInfo();
		s1.showInfo("@@@@슈퍼맨타이틀@@@@");

		// (부모타입) (변수) = (new) (자식의 객체생성)이 가능하다
		// 부모와 자식의 상속관계일 때 가능
		Human hs = new Superman("이동준",300);

		System.out.println("hs.name = "+hs.name);
		//System.out.println("hs.power = "+hs.power);// 이구문이 컴파일 에러나는 이유는???
		System.out.println("=================");
		hs.showInfo();											// 예외구문
		//hs.showInfo("############");						// 이번엔 컴파일 에러
		/*
			부모타입으로 변수 선언을 하고 자식의 객체를 생성했을 경우
			그 참조변수로 참조할 수 있는 범위
			1. 부모로부터 물려받은 변수, 메소드[o]
			2. 자식이 가지는 고유한 변수 메소드[x]
			3. 그러나 자식이 오버라이딩한 메소드가 있을 경우 , 그 메소드를 우선호출한다.
		*/
	}
}

class Human
{
	String name;

	public void showInfo()
	{
		System.out.println("이름:"+name);
	}
}

class Superman extends Human
{
	int power;

	public Superman(String name, int power)
	{
		this.name=name;
		this.power=power;
	}
	
	//오버라이딩
	public void showInfo()
	{
		super.showInfo();
		System.out.println("파워: "+power);
	}

	//오버로딩
	public void showInfo(String title)
	{
		
		System.out.println(title);
		this.showInfo();
	}
}

class Aquaman extends Human
{
	int speed;
}	