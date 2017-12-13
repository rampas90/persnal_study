public class Inheritance2
{
	public static void main(String[] args) 
	{
		Student s1 = new Student("홍길동",20,"자바");
		Teacher t1 = new Teacher();
		Person p1 = new Person("인간",1);
		
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

class Person
{
	String name;
	int age;

	public Person()
	{
		this("인간",0);
	}

	public Person(String n, int a)
	{
		name=n;
		age=a;
	}
}

class Student extends Person
{
	String cName;	//학급

	//인자생성자
	public Student(String n, int a, String c)
	{
		super(n,a); //이름, 나이는 부모생성자에서 초기화
		cName=c;
	}
}

class Teacher extends Person
{
	String subject;	//과목
	
	public Teacher()
	{
		super(null,0);
	}

	public Teacher(String n, int a, String s)
	{
		super(n,a);
		subject=s;
	}
}

class Staff extends Person
{

}