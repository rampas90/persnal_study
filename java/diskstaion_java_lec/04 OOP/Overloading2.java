public class Overloading2
{
	public static void main(String[] args) 
	{

		Aquaman a1 = new Aquaman();
		a1.showInfo();

		Aquaman a2 = new Aquaman("아쿠아맨2");
		a2.showInfo();

		Aquaman a3 = new Aquaman("아쿠아맨3",189,490);
		a3.showInfo();

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
		name="아무개";  // 멤버변수의 초기화 기능 ( 생성자의 주된 기능 )
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
		this.name=name;
		this.power=power;
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
