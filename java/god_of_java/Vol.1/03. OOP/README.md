###객체란?

```java
public class Car
{
	//생성자
	public Car()
	{	

	}

	// 차의 속도 거리 색깔등 "상태"를 나타낸다
	int speed;
	int distance;
	String color;

	// Car 클래스의 "행위"를 나타낸다
	public void speedUp()
	{
		
	}

	public void breakDown()
	{
	
	}
}
```



> 위 Car 클래스처럼 핸드폰에 대한 클래스에 변수와 메소드를 만들어 보자

```java
public class Smartphone
{
	int battery;
	String brand;
	String color;

	public void on()
	{

	}

	public void off()
	{

	}


	public static void main(String[] args)
	{	

	}
}
```


> 클래스와 객체는 구분해야한다.
> '포르쉐'라는 차가 있더라도 개똥이의 '포르쉐'와 소똥이의 '포르쉐'는 다르다.
> 주행한 거리도 다를 것이고, 색도 다를것이며, 자동차 등록번호도 다르다

	> !!!
	> 그러면 이때 개똥이와 소똥이의 차를 나타내기 위해서는 별도의 DogCar, CowCar클래스를 만들어야 할까?

	> -> '포르쉐'라는 클래스를 만들고 그 클래스로 각각의 객체를 생성하면 될 것 같다.
	
```java
	Car dogCar = new Car();
	Car cowCar = new Car();
```


```java
public class CarManager
{	
	// 참고로 main()메소드의 매개변수인 '[]' 의 위치는 1차원배열일때는 아래처럼 바뀌어도 상관없다
	// 그냥 헷갈리니까 일반적인 방법으로 쓰자
	public static void main(String args[])
	{
		// 
	}
}
```

```java
public class Calcurator
{
	public int add(int num1, int num2)
	{
		return num1+num2;
	}

	public int addAll(int num1, int num2, int num3)
	{
		return num1+num2+num3;
	}

	public int subtract(int num1, int num2)
	{
		return num1-num2;
	}

	public int multiply(int num1, int num2)
	{
		return num1*num2;
	}

	public int divide(int num1, int num2)
	{
		return num1/num2;
	}

	public static void main(String[] args)
	{	
		System.out.println("Calcurator class start");

		// 클래스명 객채명 = new Calcurator
		Calcurator calc=new Calcurator();
	}
}
```


```java
public class CalcuratorGod
{
	public int add(int num1, int num2)
	{
		return num1+num2;
	}

	public int addAll(int num1, int num2, int num3)
	{
		return num1+num2+num3;
	}

	public int subtract(int num1, int num2)
	{
		return num1-num2;
	}

	public int multiply(int num1, int num2)
	{
		return num1*num2;
	}

	public int divide(int num1, int num2)
	{
		return num1/num2;
	}

	public static void main(String[] args)
	{	
		System.out.println("CalcuratorGod class start");

		// 클래스명 객채명 = new Calcurator
		CalcuratorGod calc=new CalcuratorGod();
		int a=10;
		int b=5;
		System.out.println("add : "+calc.add(a,b));
		System.out.println("subtract : "+calc.subtract(a,b));
		System.out.println("multiply : "+calc.multiply(a,b));
		System.out.println("divide : "+calc.divide(a,b));

	}
}
```

#### 직접해 봅시다 

1. 1-2장 에서 살펴본 Profile 클래스에 String 타입의 name과 int타입의 age 라는 변수를 선언해 보자.
   예를 들면 name은 다음과 같이 선언하면 된다.

```java
	public class Profile
	{
		
		String name ;
		int age;

		public void setName(String str)
		{
			name = str;
		}	

		public void setAge(int val)
		{
			age = val;
		}

		public void printName()
		{
			System.out.println("name 값은 : " + name);
		}
		
		public void printAge()
		{
			System.out.println("age 값은 : " + name);
		}


		public static void main(String[] args)
		{
			String name = "shin";
			int age = 29;

			System.out.println("My name is " + name);
			System.out.println("My age is "+ age);

			Profile p = new Profile();
			p.setName("Min");
			p.setAge(20);
			p.printName();
			p.printName();

		}
	}
```


#### 정리해 봅시다.

	1. 클래스와 객체의 차이점을 말해 주세요.
		-> 클래스는 설계도로 미리 정의 되어있는 변수 ..'포르쉐'
		-> 객체는 클래스를 이용해 생성한 인스턴스로 '개똥이의 포르쉐', '소똥이의 포르쉐'
		=> 클래스를 통해서 객체를 생성할 수 있다. 즉, 하나의 클래스를 만들면 그 클래스의 모습을 갖는 여러 객체들을 생성 할 수 있다. 
         그러므로, 일반적인 경우 클래스의 메소드나 변수들을 사용하려면 객체를 생성하여 사용하여야 한다.
	
	2. 객체를 생성하기 위해서 꼭 사용해야 하는 예약어는 뭐라고 했죠?
		-> new

	
	3. 객체를 생성하기 위해서 사용하는 메소드 같이 생긴 클래스 이름에 소괄호가 있는 것을 뭐라고 하나요?
		-> 생성자
	
	4. 객체의 메소드를 사용하려면 어떤 기호를 객체이름과 메소드 이름 사이에 넣어주어야 하나요?
		-> .
		=> 클래스의 변수나 메소드를 호출하려면 "객체이름.변수", "객체이름.메소드이름()"와 같이 사용하면 된다.
	
	5. 클래스의 일반 메소드를 사용하기 위해서는 어떤 것을 만들어야 하나요?
		-> 객체












