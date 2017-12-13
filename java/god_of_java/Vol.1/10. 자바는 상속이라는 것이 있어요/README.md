## 10장. 자바는 상속이라는 것이 있어요

### 1. 자바에서 상속이란?

상속에대해 알아보기 전에 예제 코드를 먼저 보자.

> Parent.java (부모클래스)

```java
package c.inheritance;

public class Parent {
	public Parent() {
		System.out.println("Parent Constructor");
	}

	public void printName() {
		System.out.println("printName() - Parent");
	}
}
```

> Child.java (자식클래스)

```java
package c.inheritance;

public class Child extends Parent {
	public Child() {
		System.out.println("Child Constructor");
	}
}
```

> `extends` 는 자바의 예약어로 상속에 사용된다.
> 이렇게 하면 **부모 클래스에 선언되어 있는 `public` 및 `protected`로 선언되어 있는 모든 변수와 메소드를 내가 갖고 있는 것처럼 사용할 수 있다.**

> 즉 접근제어자가 없거나 `private`로 선언된 것들은 자식클래스에서 사용할 수 없다.

UML의 클래스 다이어그램 -> 빈화살표로 상속 표시는 이고잉 강의 생각하면 될 듯

```java
package c.inheritance;

public class Inheritance {
	public static void main(String[] args) {
		Child child = new Child();
		child.printName();
	}
}
```

> 상속클래스까지 구현한 후 컴파일 실행해보면 부모클래스의 메소드를 호출하지도 않았는데, 확장을 한 클래스가 생성자를 호출하면, 자동으로 부모 클래스의 기본생성자(매개변수없는)가 호출되는 것을 알수 있다.

정리해보면
- 부모 클래스에서는 기본생성자를 만들어 놓는 것 이외에는 상속을 위해서 별도로 작업을 할 필요는없다.

- 자식 클래스는 클래스 선언시 `extends`다음에 부모 클래스 이름을 적어준다

- 자식 클래스의 생성자가 호출되면, 자동으로 부모 클래스의 매개 변수 없는 생성자가 실행된다.

- 자식 클래스에서는 부모 클래스에 있는 public, protected로 선언된 모든 인스턴스 및 클래스 변수와 메소드를 사용할 수 있다.

- 또한 확장된 클래스(여기서는 자식클래스)는 추가적인 메소드를 만들어도 전혀 문제가 없다.

### 2. 상속과 생성자

앞 절에서 상속에 대해 정리할 때 다음과 같이 말했다.
- 부모 클래스에서는 기본 생성자를 만들어 놓는 것 이외에는 상속을 위해서 아무런 작업을 할 필요는 없다.

만약 부모 클래스에 기본 생성자가 없다면 어떻게 될까?

```java
public class Parent {
	/*
	public Parent() {
		System.out.println("Parent Constructor");
	}
	*/
	public void printName() {
		System.out.println("printName() - Parent");
	}
}
```
> 위와 같이 부모클래스의 기본생성자를 주석처리하고 부모파일을 재컴파일한후 상속클래스를 실행하면
> 문제가 없다.

> 다만 이때 만약 기본생성자가 아닌 매개변수가 필요한 생성자가 있을경우에는 이야기가 달라진다.

이건 자식클래스의 모든 생성자가 실행될 때 `Parent()`의 기본생성자를 찾는데,
이미 정의한 생성자의 경우 기본생성자가 없기 때문이다.

> 해결방법은 다음과 같다

- 부모클래스인 `Parent` 클래스에 기본생성자를 만든다(매개변수가 없는)
- 자식 클래스에서 부모클래스의 생성자를 명시적으로 지정하는 `super()`를 사용한다.

> 메소드처럼 사용하지 않고 `super.printName()`으로 사용하면 부모클래스에 있는 `printName()`이라는 메소드를 호출한다는 의미다.

만약 여기서 참조자료형을 매개변수로 받는 생성자가 하나 더 있다면 어떻게 될까?

> 일반적으로는 이렇게 부모클래스에 같은 타입을 매개 변수로 받는 생성자가 있으면 다음과 같이 처리한다

```java
package c.inheritance;

public class Child extends Parent {
	public Child()
	{
		super(null);
		System.out.println("Child Constructor");
	}

	public Child(String name)
	{	
    	super(name);   /// 이부분
		System.out.println("Child(String) Constructor");
	}

}
```
> 또한 `super()`의 위치는 반드시 **생성자 첫줄**에 있어야만 한다.


이번 절의 총정리
1. 생성자에서는 `super()`를 명시적으로 지정하지 않으면, 자동으로 super()가 추가된다
2. 만약, 부모클래스에 매개변수가 없는 생성자가 정의되어 있지 않고, 매개 변수가 있는 생성자만 정의되어 있을 경우에는 명시적으로 `super()`에서 매개변수가 있는 생성자를 호출하도록 해야만 한다.

---

### 3. 메소드 Overriding

자식클래스에서 부모클래스에 있는 메소드와 동일하게 선언하는 것을 **"메소드 Overriding"**이라고 한다.
- 접근제어자,리턴타입, 메소드이름, 매개변수 타입 및 개수가 모두 동일해야만 **Overriding**이라고 부른다.

> 먼저 부모클래스엔 다음과 같은 메소드가 있다.

```java
public void printName() {
	System.out.println("printName() - Parent");
}
```
> 여기에 자식클래스에 위와 동일한 메소드를 생성해보자

그 후에 모든클래스를 컴파일하고 상속클래스를 실행하면
부모클래스의 메소드가 아닌 자식클래스에 똑같이 선언한 메소드가 호출된 것을 알수 있다.

> 다시말해서 부모클래스보다 자식클래스의 메소드가(동일한) 우선시된다는 뜻이다.
	-> 동일한 시그네쳐를 가진다 라고 한다.

- 생성자의 경우 자동으로 부모클래스에 있는 생성자를 호출하는 `super()`가 추가되지만, 메소드는 그렇지 않다.  이게 바로 **"메소드 Overriding"**이다


- 이때 오버라이딩이 제대로 동작하려면 부모클래스를 오버라이딩한 메소드의 리턴타입을 다르게 리턴하면 안 된다.
- 또한 접근제어자는 범위가 확대대는것은 문제가 안되지만 축소되는 것은 문제가 된다.

> `Overriding`에 대한 정리

1. 메소드 Overriding은 부모 클래스의 메소드와 동일한 시그네쳐를 갖는 자식 클래스의 메소드가 존재할 때 성립된다.
2. Overriding된 메소드는 부모클래스와 동일한 리턴 타입을 가져야만 한다.
3. Overriding된 메소드의 접근 제어자는 부모 클래스에 있는 메소드와 달라도 되지만, 접근 권한이 확장되는 경우에만 허용된다. 접근 권한이 축소될 경우에는 컴파일 에러가 발생한다.

용어가 비슷해서 헷갈릴수 있는  `Overloading`,  `Overriding`을  구별해보자
1. Overloading : 확장 (메소드의 매개 변수들을 확장하기 때문에, 확장)
2. Overriding : 덮어씀 (부모 클래스의 메소드 시그니쳐를 복제해서 자식클래스에서 새로운 것을 만들어 내어 부모 클래스의 기능은 무시하고, 자식 클래스에서 덮어씀)

### 4.참조 자료형의 형 변환

> Parent 클래스

```java
public class Parent2{
	public Parent(){
    
    }
    pubilc Paretn2(String name){
    
    }
    public void printName(){
    	System.out.println("printName() - Parent");
    }
}
```

> Child 클래스

```java
public class Child2 extends Parent2{
	public Child2(){
    
   }
   pubilc Child2(String name){
    
   }
   public void printName(){
		System.out.println("printName() - Parent");
   }

	public void printAge(){
		System.out.println("printAge() - 18 month");
   }
}
```

이렇게 부모 자식간의 상속관계가 성립될 경우 다음과 같은 객체 선언이 가능하다.
```java
Parent2 p2 = new Chile2();
```

> Parent 클래스에서는 Child 클래스에 있는 모든 변수와 메소드를 사용할 수도 있고 그렇지 않을 수도있다.
> 즉, Chile클래스에 추가된 메소드나 변수가 없을 경우 가능하다

참조 자료형은 자식 클래스의 타입을 부모클래스의 타입으로 형 변환하면 부모 클래스에서 호출할 수 있는 메소드들은 자식 클래스에서도 호출 할 수 있으므로 전혀 문제가 안된다. 
따라서 형 변환을 우리가 명시적으로 해 줄 필요가 없다.

> 소스를 통해 알아보자

```java
public class Inheritance2{
	public void objectCas(){
    	Parent parent =new Parent();
        Child child=new child();
        
        Parent paretn2=child;
        Child child2=(Child)parent2;
    }
}
```

그럼 이런 형변환은 왜 이렇게 머리아프게 써야하는 걸까?

> 다음의 메소드를 보자

```java
public Class Inheritance3{
	public void objectCast2(){
    	Parent[] parentArray=new Parent[3];
        parentArray[0]=new Child();
        parentArray[2]=new Parent();
        parentArray[2]=new Child();
    }
}
```

> Parent 배열은 3개의 값을 저장할 수 있는 공간을 가진다. 그런데, 0번째 배열과 2번째 배열은 Child 클래스의 객체를 할당한 것을 볼 수 있다.
> 이렇게 코딩해도 전혀 문제는 없다 이와 같이 일반적으로 여러개의 값을 처리하거나, 매개변수로 값을 전달 할 때에는 보통 부코 클래스의 타입으로 보낸다. 이렇게 하지 않으면 배열과 같이 여러 값을 한번에 보낼 때 각 타입별로 구분해서 메소드를 만들어야 하는 문제가 생길수도 있기 때문이다.

그럼 여기서 `parentArry` 라는 배열의 타입이 Child 인지 Parent 인지 어떻게 구분할 수 있을까?

> 이런 경우에 사용하라고 있는 것이 바로 `instanceof` 라는 예약어다.
> 이 objectCast2()메소드에 instanceof를 사용하여 타입을 구분하는 코드를 넣으면 다음과 같다

```java
public void objectCast2(){
// 생락
	for(Parent tempParent:parentArray){
    	if(tempParent instanceof Child){	// 1
        	System.out.println("Child");
        }
        else if(tempParent instanceof Parent){	//2
        	System.out.println("Parent");
        }
    }
}
```

`instanceof`의 앞에는 객체를 , 뒤에는 클래스(타입)를 지정해 주면 
> 그러면 이 문장은 true나 false와 같이 boolean 형태의 결과를 제공한다.
> 그래서 parentArray의 0번재 갑은 child 클래스의 객체이므로 1번 조건에서 true
> 2번 조건에서 true 2번째 값은 true 여야 한다.


`instanceof`의 유의할 점
> `instanceof`를 사용하여 타입을 점검할 때에는 가장 하위에 있는 자식 타입부터 확인을 해야 제대로 타입점검이 가능하다.

- 참조자료형도 형 변환이 가능하다.
- 자식 타입의 객체를 부모 타입으로 형 변환 하는 것은 자동으로 된다.
- 부모 타입의 객체를 자식 타입으로 형 변환을 할 때에는 명시적으로 타입을 지정해 주어야 한다.  이때,  부모타입의 실제 객체는 자식 타입이어야 한다.
- `instanceof`예약어를 사용하면 객체의 타입을 확인할 수 있다.
- `instanceof`로 타입 확인을 할 때 부모 타입도 true라는 결과를 제공한다.

객체의 형 변환은 매우 중요하다.



### 5.Polymorphism
Polymorphism(폴리몰피즘)이란 말은 우리나라말로 다형성이이다.

다형성이란 말은 "형태가 다양하다"라는 말이다. 

```java
public class ChildOther extends Parent{
	public ChildOther(){
    
    }
    public void printName(){
    	System.out.println("printName() - childOther");
    }
}
```
> 자바의 자식 클래스는 백개를 만들든, 만개를 만들든 상관없다.

```java
public void callPrintName(){
	Parent2 parent1=new Parent2();
	Parent2 parent2=new Child2();
	Parent2 parent3=new ChildOther();
    
	parent1.printName();
	parent2.printName();
	parent3.printName();
}	
```
> 위 소스를 main()메소드해서 불러와서 실행해 보면 각 객체의 타입은 모두 Parent 타입으로 선언되어 있는데도 불구하고
> `printName()` 의 결과는 상의하다

> 다시 말해 선언시에는 모두 Parent 타입으로 선언했지만, 실제 메소드 호출된 것은 생성자를 사용한 클래스에 있는 것이 호출되었다.

> 이와 같이 **"형 변환을 하더라도, 실제 호출되는 것은 원래 객체에 있는 메소드가 호출된다"**는 것이 바로 **다형성**이다.


### 6. 자식 클래스에서 할 수 있는 일들을 다시 정리해보자
1. 생성자
	- 자식 클래스의 생성자가 호출되면 자동으로 부모클래스의 매개변수가 없는 기본생성자가 호출된다.명시적으로 `super()`라고 지정할 수도 있다.
	- 부모 클래스의 생성자를 명시적으로 호출하려면 `super()`를 사용하면 된다.

2. 변수
	- 부모 클래스에 `private`로 선언된 변수를 제외한 모든 변수가 자신의 클래스에 선언된 것처럼 사용할 수 있다.
	- 부모 클래스에 선언된 변수와 동일한 이름을 가지는 변수를 선언할 수도 있다. 하지만, 이렇게 엎어 쓰느 것은 권장하지 않는다.
	- 부모 클래스에 선언되어 있지 않는 이름의 변수를 선언할 수 있다.

3. 메소드
	- 변수처럼 부모 클래스에 선언된 메소드들이 자신의 클래스에 선언된 것처럼 사용할 수 있다.
	- 부모 클래스에 선언된 메소드와 동일한 시그니쳐를 사용함으로써 메소드를 `Overriding`할 수 있다.
	- 부모 클래스에 선언되어 있지 않은 이름의 새로운 메소드를 선언할 수 있다.



==직접해봅시다.(p. 312)==

```java
public class Animal
{

	String name;
	String kind;
	int legCount;
	int iq;
	boolean hasWing;
	
	public void move(){
		System.out.println("움직였다.");
	}

	public void eatFood(){
		System.out.println("음식을 먹었다.");
	}

	public static void main(String[] args) 
	{
		Animal a = new Animal();
		Animal ad = new Dog();
		Animal ac = new Cat();
		
		System.out.println("=======Animal=======");
		a.move();
		a.eatFood();

		System.out.println("=======Dog=======");
		ad.move();
		ad.eatFood();

		System.out.println("=======Cat=======");
		ac.move();
		ac.eatFood();
	}
}

class Dog extends Animal
{
	int age;
	String color;

	public void move(){
		System.out.println("개가 움직였다.");
	}

	public void eatFood(){
		System.out.println("개가 사료를 먹었다.");
	}
}

class Cat extends Animal
{
	int jumpDistance;

	public void move(){
		System.out.println("고양이가 움직였다.");
	}

	public void eatFood(){
		System.out.println("고양이가 우유를 먹었다.");
	}
}
```


#### 정리해 봅시다.

1. 상속을 받는 클래스의 선언문에 사용하는 예약어는 무엇인가요?
	-> extends

2. 상속을 받은 클래스의 생성자를 수행하면 부모의 생성자도 자동으로 수행되나요?
	-> 예
	=> 확장을 한 클래스가 생성자를 호출하면, 자동으로 부모 클래스의 "기본 생성자"가 호출된다.

3. 부모 클래스의 생성자를 자식 클래스에서 직접 선택하려고 할 때 사용하는 예약어는 무엇인가요?
	-> super()

4. 메소드 Overriding과 Overloading을 정확하게 설명해 보세요.
	->오버라이딩은 덮어쓰는 개념으로 부모클래스의 메소드를 재선언하여 확장성을 높힐수 있다. 부모의 기능은 가져오면서 추가적인 메소드를 구현할 수 있다. 오버로딩은 확장의 의미로 매개변수를 달리 받는 메소드나 생성자를 구현할수 있다.

	**=> Overriding은 자식 클래스에서 부모 클래스에 선언된 메소드의 선언 구문을 동일하게 선언하여 사용하는 것을 의미한다.
    즉, public void printName()이라는 메소드가 부모클래스에 있고, 자식 클래스에도 동일한 메소드를 선언한다는 것이다.
    혼동되기 쉬운 Overloading은 상속관계와 거의 상관 없이 메소드의 이름을 동일하게 하고, 매개변수만 다르게 하는 것을 의미한다.**

5. A가 부모, B가 자식 클래스라면 A a=new B(); 의 형태로 객체 생성이 가능한가요?
	-> 가능하다

6. 명시적으로 형변환을 하기 전에 타입을 확인하려면 어떤 예약어를 사용해야 하나요?
	-> instanceof

7. 6번 문제에서 사용한 예약어의 좌측에는 어떤 값이, 우측에는 어떤 값이 들어가나요?
	-> 좌측에는 객체를 우측에는 클래스타입을 
	=> 좌측에는 확인하고자 하는 변수를, 우측에는 클래스 이름이 위치

8. 6번 문제 예약어의 수행 결과는 어떤 타입으로 제공되나요?
	->boolean

9. Polymorphism이라는 것은 도대체 뭔가요?
	-> 다형성으로 형변환을 하더라도 실제 객체의 메소드를 불러오는걸 말한다.
	=> 자식 클래스는 자신만의 "행위"를 가질 수 있지만, 부모 클래스에 선언된 메소드들도 공유 가능하다는 것을 의미한다. 
    다시 말해, 부모 클래스의 타입으로 변수를 선언하고, 자식 클래스의 생성자를 사용할 경우 overriding된 메소드를 호출하면 자식 클래스에 선언된 메소드가 호출되고, 부모 클래스의 메소드도 공유 가능하다는 것을 의미한다. 








