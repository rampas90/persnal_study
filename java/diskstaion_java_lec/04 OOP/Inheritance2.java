public class Inheritance2
{
	public static void main(String[] args) 
	{
		Student s1 = new Student("ȫ�浿",20,"�ڹ�");
		Teacher t1 = new Teacher();
		Person p1 = new Person("�ΰ�",1);
		
		Superman s1 = new Superman();
		s1.name="���۸�1";
		s1.height=190;
		s1.power=2000;
		String info = s1.getInfo();
		System.out.println(info);
		
		String info2 = s1.getInfo("----���۸� ����-----");
		System.out.println(info2);

		System.out.println("=================");

		Human h1 = new Human();
		h1.name="���1";
		h1.height=188;
		String str2 = h1.getInfo();
		System.out.println(str2);

		System.out.println("=================");

		Aquaman a1 = new Aquaman();
		a1.name="����Ƹ�1";
		a1.height=190;
		a1.speed=1200;
		String infoa = a1.getInfo();
		System.out.println(infoa);

		a1.getInfo("-----����Ƹ� ����-----",50);


		System.out.println("=================");

	}
}

class Person
{
	String name;
	int age;

	public Person()
	{
		this("�ΰ�",0);
	}

	public Person(String n, int a)
	{
		name=n;
		age=a;
	}
}

class Student extends Person
{
	String cName;	//�б�

	//���ڻ�����
	public Student(String n, int a, String c)
	{
		super(n,a); //�̸�, ���̴� �θ�����ڿ��� �ʱ�ȭ
		cName=c;
	}
}

class Teacher extends Person
{
	String subject;	//����
	
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