### 객체지향 프로그래밍 OOP 

#### 1. 추상화
> 생략

#### 2. 캡슐화

> 비유생성 - 설탕,커피,프림 - 동전자판기

- `Data`를 캡슐화 하되
- `Data`에 접근할 때는 메소드로 접근하도록 한다.

> 캡슐화방법


- 멤버 변수 앞에 `private` 접근 지정자를 붙인다. (설탕, 프림 에는 접근 불가능하도록)
- 이 지정자는 자기 클래스에서만 접근 가능하다.
- 멤버 변수에 값을 할당하고 값을 꺼내올 수 있는 메소드를 만든다

##### `setter` 메소드
> 메소드의 접근 지정자는 `public`으로 주고 `setter`메소드들은 반환타입이 없되, 매개변수를 받아들이도록
	
```java
public void setName(String n){
	name=n;
}


```
##### `getter` 메소드
> `getter`메소드 들은 반환타입이 있되 매개변수는 받아들이지 않는다.

```java
public String getName(){
	return name;
}
```



> UML 툴 알아보기



1056~1057 강의 소스 작성하기



---

#### 3. 다형성 ( Polymorphism )
1) 오버로딩(Overloading) - 다중정의,중복정의
2) 오버라이딩(Overriding) - 재정의

절차지향 프로그램은 아래처럼 비슷한 기능임에도 전부 다르게 줘야 했다.

> 밀크커피 - `void getMilkCoffee(int cf, int s, int cr)`
> 블랙커피 - `void getBlackCoffee(int cf)`
> 프림커피 - `void getCreamCoffee(int cf, int cr)`

이를 `오버로딩` 개념으로 살펴보면 아래와 같이 바꿀수 있다.

> `void getTea(int cf, int su, int cr)`
> `void getTea(int cf)`
> `void getTea(int cf, int cr)`
> `void getTea(Yuja y)`

메소드 이름을 동일하게 주되, 매개변수의 데이터 타입과 갯수, 순서를 다르게 주어 구성하는 것

**오버로딩의 조건**
1. 오버로딩하려는 메소드 이름이 같아야 한다.
2. 메소드의 매개변수의 데이터 타입이 다르거나, 갯수가 다르거나, 순서가 달라야 한다.
3. 메소드의 반환타입은 신경 안써도 된다 - 같아도 되고 달라도 됨

아래는 매개변수의 데이터 **타입**이 같으므로 안된다.
```java
void getTea(int cf, int cr)		// int, int
void getTea(int cf, int sugar)  // int, int 형이 이미 선언됬으므로 불가능
```

아래는 반환타입이 다르지만 가능하다
```java
void getTea(int cf, int cr)		// int, int
String getTea(int cf, int sugar)  // int, int 형이 이미 선언됬으므로 불가능
```

오버로딩의 종류
1. 생성자 오버로딩
2. 메소드 오버로딩

> 생성자 오버로딩

	Cafe.java 파일 참조
    
```java
public class Cafe
{
	public static void main(String[] args) 
	{
		CoffeeMachine cm = new CoffeeMachine();
		int n = cm.getTea(2,2,3);
		System.out.println("n: "+n);

		n=cm.getTea(1,2);
		System.out.println("n: "+n);

		// Yuja 클래스 꺼내오기 유자=3, 설탕2
		Yuja y = new Yuja();
		y.yuja=3;  // 만약 캡슐화 했다면? y.setYuja(3); 물론 setYuja 메소드를 만들었다고 가정하고
		y.sugar=2;
		cm.getTea(y);

	}
}

class CoffeeMachine
{
	int coffee, sugar, cream;
	
	//메소드 오버로딩
	public int getTea(int cf, int s, int cr)
	{
		coffee=cf;
		sugar=s;
		cream=cr;
		System.out.println("밀크커피." + "농도 : " + (coffee+sugar+cream));
		return (coffee+sugar+cream);
	}

	public int getTea(int cf, int cr)
	{
		coffee=cf;
		cream=cr;
		int r = coffee+cream;
		System.out.println("프림커피." + "농도 : " + r);
		return r;
	}

	public String getTea(int cf, String su)
	{
		System.out.println("설탕커피." + "농도 : " + cf+su);
		return cf+su;
	}

	public void getTea(Yuja y)
	{
		System.out.println("유자 차가 나가요.");
		System.out.println("유자 농도 : "+y.yuja);
		System.out.println("설탕 농도 : "+y.sugar);
		System.out.println("=====================");
		System.out.println("유자차농도 : "+ (y.sugar+y.sugar));
	}
}

class Yuja
{
	int yuja;
	int sugar;
}
```

생성자 오버로딩

	Ovaerloading.java, Overloading2.java, Overloading3.java 파일 참조

> this() 사용
> 한 클래스 안에 여러 개의 생성자가 오버로딩된 형태로 존재하고, 그 기능이 유사할 때, this라는 키워드를 이용해서 자기 자신의 다른 생성자를 호출할 수 있다.
> ㄱ. `this()`는 생성자 안에서만 호출해야 한다.
> ㄴ. `this()`를 호출할 때는 반드시 생성자의 첫번째 문장이어야 한다.
> ㄷ. 또한 생성자 안에서 `this()`와 `super()`는 함께 쓸 수 없다.
> ㄹ. `this()`라는 키워드는 `static`메소드 안에서는 사용할 수 없다.

---

#### 4. 상속성 ( Inheritance )

> Overloading.java 파일들을 참조하여 예제
> 각 클래스들의 겹치는 항목들(name, height)와 `showInfo()`메소드같이 겹치는 항목들을
> 부모 클래스로부터 상속받도록 구현하면 재사용성의 이점과, 코드의 중복이 줄어든다.

예를 들어 `Human`이라는 클래스(부모클래스, 슈퍼클래스)를 만들어 기본적인 기능들을 구현하고
`Superman`, `Aquaman` 등의자식클래스(서브클래스)를 만들어 상속받는다.

> 자바에서 상속을 받을 때는 `extends`란 키워드를 사용한다.
> 단 자바는 단일 상속개념이므로 `extends`로 상속 받을 수 있는 클래스는 단 하나뿐이다.

오버라이딩

메소드 Overriding(재정의) 부모
1. 부모의 것과 동일한 메소드명
2. 매개변수도 동일하게
3. 반환타입도 동일하게
4. 접근지정자는 부모의 것과 동일하거나 더 넓은 범위의 지정자를 사용
5. Exception의 경우 부모 클래스의 메소드와 동일하거나 더 구체적인 Exception을 발생시켜야한다.

```java
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
```

상속과 생성자 관계
```java
public class Inheritance2
{
	public static void main(String[] args) 
	{
		Student s1 = new Student("홍길동",20,"자바");
		Teacher t1 = new Teacher();
		Person p1 = new Person("인간",1);


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
		/*
			부모와 자식의 관계를 맺을 경우 자식클래스의 생성자에서 맨처음 하는 일은 
			부모의 기본생성자 super() 를 호출하는 일이다. 이는 컴파일러가 자동으로 호출한다.
		*/
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
	// 부모의 기본생성자를 불러온다.
}
```



> 그 참조변수로 참조할 수 있는 범위

1. 부모로부터 물려받은 변수, 메소드[o]
2. 자식이 가지는 고유한 변수 메소드[x]
3. 자식이 오버라이딩한 메소드를 우선호출한다.

(부모타입) (변수) = (new) (자식의 객체생성)
```java
Parent hs = new Child();
```
	예제코드 Polymorphism.java 참조

```java
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
		System.out.println("hs.power = "+hs.power);// 이구문이 컴파일 에러나는 이유는???
		System.out.println("=================");
		hs.showInfo();											// 예외구문
		hs.showInfo("############");						// 이번엔 컴파일 에러
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
```

---



