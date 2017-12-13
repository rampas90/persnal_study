public class Overloading3
{
	public static void main(String[] args) 
	{
		Superman s1 = new Superman(600,"this 생성자");
		s1.showInfo();
	}
}

class Superman
{
	String name;	//null
	int height;		//0
	int power;		//0
	
	//기본 생성자 
	public Superman()
	{
		name="아무개";
		height=170;
		power=200;
	}

	//인자 생성자
	public Superman(String name)
	{
		this.name=name;
	}

	public Superman(String name, int h)
	{
		this.name=name;
		height=h;
	}

	public Superman(int power, String name)
	{
		this(name,180,power); // 바로 아래에 있는 생성자(매개변수가 3개있는)를 참조하게된다.
	}

	public Superman(String name, int h, int p)
	{
		this.name=name;
		height=h;
		power=p;
	}

	public void showInfo()
	{
		System.out.println("===Superman Info===");
		System.out.println("이름 : "+name);
		System.out.println("키 : "+height);
		System.out.println("힘 : "+power);
	}
}

class Aquaman
{
	String name;
	int height;
	int speed;

	public Aquaman()
	{
		name="기본형";
		height=100;
		speed=200;
	}

	public Aquaman(String name, int h, int s)
	{
		this.name=name;
		height=h;
		speed=s;
	}

	public Aquaman(String name)
	{
		this.name=name;
	}

	public void showInfo()
	{
		System.out.println("===Aquaman Info===");
		System.out.println("이름 : "+name);
		System.out.println("키 : "+height);
		System.out.println("속도 : "+speed);
	}
}
