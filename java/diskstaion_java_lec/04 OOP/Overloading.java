public class Overloading
{
	public static void main(String[] args) 
	{	

		Superman s1 = new Superman();
		s1.showInfo();

		Superman s2 = new Superman("슈퍼맨2");
		s2.showInfo();

		Superman s3 = new Superman("슈퍼맨3",200);
		s3.showInfo();
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

s