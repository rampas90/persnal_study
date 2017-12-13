## 12장. 인터페이스와 추상클래스, enum

### 1. 메소드 내용이 없는 interface

자바에서 `.class` 파일을 만들 수 있는 것에는 클래스만 있는 것이 아니다.
`interface`와 `abstract`클래스라는 것이 있다.

인터페이스와 abstract 클래스에 대해서 제대로 이해하려면 시스템을 만드는 절차가 어떻게 되는지 알아야 한다.

1.분석
2.설계
3.개발 및 테스트
4.시스템 릴리즈

> DAO~dataAccessObject~ 패턴
이 패턴은 데이터를 저장하는 저장소에서 원하는 값을 요청하고 응답을 받는다.

인터페이스와 abstract클래스를 사용하는 이유
1. 설계시 선언해 두면 개발할 때 기능을 구현하는 데에만 집중 할 수 있다.
2. 개발자의 역량에 따른 메소드의 이름과 매개변수 선언의 격차를 줄일 수 있다.
3. 공통적인 인터페이스와 abstract 클래스를 선언해 놓으면, 선언과 구현을 구분할 수 있다.
> 인터페이스는 다음과 같이 정의한다.

```java
package c.impl;

import c.inheritance.MemberDTO;

public interface MemberManagerInterface {
	public boolean addMember(MemberDTO member);
	public boolean removeMember(String name, String phone);
	public boolean updateMember(MemberDTO member);
}
```

> 이렇게 만든 인터페이스를 어떻게 활용할까?

```java
package c.impl;

import c.inheritance.MemberDTO;

public class MemberManager implements MemberManagerInterface {
}
```
> `implements`라는 예약어를 쓴후 인터페이스들을 나열한다.

단 이렇게만 하고 컴파일을 하면 에러가 난다.

> 에러내용을 번역해보면 "작성한 클래스는 abstract클래스도 아니고 abstract메소드도 구현하지 않았다." 정도이다

인터페이스를 구현할 경우 반드시 인터페이스에 정의된 메소드들의 몸통을 만들어 주어야만 한다.

> 즉 최소한 인터페이스에 구현된 addMember, removeMember, updateMember 세가지의 메소드는 작성하는 클래스에 만들어줘야 한다는 뜻이다.

-
> 정리하자면, 설계 단계에서 인터페이스만 만들어 놓고, 개발 단계에서 실제 작업을 수행하는 메소드를
> 만들면 설계 단계의 산출물과 개발 단계의 산출물이 보다 효율적으로 관리될 수 있다.

또한 이런 용도 외에도 외부에 노출되는 것을 정의해 놓고자 할 때 사용된다.


> 그럼 인터페이스는 어떻게 사용할까?

```java
MemberManagerInterface manager = new MemberManagerInterface();
```
> 위소스는 컴파일 에러가 난다. 다음과 같이 변경하자

```java
MemberManagerInterface manager = new MemberManager();
```

> 겉으로 보기에 manager 타입은 MemberManagerInterface이다. 
> 그리고 MemberManager 클래스에는 인터페이스에 선언되어 있는 모든 메소드들이 구현되어 있다.
> 따라서, 실제 manager 타입은 MemberManager가 되기때문에 manager에 선언된 메소드들을 실행하면 MemberManager에 있는 메소드들이 실행된다.



### 2. 일부 완성되어 있는 abstract 클래스

단어의 뜻을 찾아보면 "추상적인"의 의미다.

- abstract 클래스는 자바에서 마음대로 초기화하고 실행할 수 없다.
- abstract 클래스를 구현해 놓은 클래스로 초기화 및 실행이 가능하다.

> interface와 비교해보자

```java
public interface MemberManagerInterface {
	public boolean addMember(MemberDTO member);
	public boolean removeMember(String name, String phone);
	public boolean updateMember(MemberDTO member);
}
```

```java
public abstract class MemberManagerAbstract {
	public abstract boolean addMember(MemberDTO member);
	public abstract boolean removeMember(String name, String phone);
	public abstract boolean updateMember(MemberDTO member);
    public void printLog(String data){
    	System.out.println("Data="+data);
    }
}
```
> abstract 클래스는 선언시 class라는 예약어 앞에 abstract라는 예약어를 사용한 것을 알 수 있다.
> 또한, 몸통이 없는 메소드 선언문에도 abstract라는 예약어가 명시되어 있다.
> 이와 같이 abstract 클래스는 abstract로 선언한 메소드가 하나라도 있을 때 선언한다.
> 게다가 인터페이스와 달리 구현되어 있는 메소드가 있어도 상관없다.

- abstract 클래스는 클래스 선언시 abstract라는 예약어가 클래스 앞에 추가되면 된다.
- abstract 클래스 안에는 abstract로 선언된 메소드가 0개 이상 있으면 된다.
- abstract로 선언된 메소드가 하나라도 있으면 그 클래스는 반드시 abstract으로 선언되어야만 한다.
- abstract클래스는 몸통이 있는 메소드가 0개 이상 있어도 전혀 상관 없으며, `static`,`final`메소드가 있어도 된다( 인터페이스에는 `static` 이나 `final`메소드가 선언되어 있으면 안된다.)

이러한 abstract클래스는 인터페이스처럼 implements라는 예약어를 사용하여 구현할 수 없다.
왜냐하면 인터페이스가 아니라 클래스이기 때문이다.

> 그러면 어떤 예약어를 상요하여 이 abstract 클래스를 구현한다고 선언할까?

바로 `extends`라는 예약어를 사용한다.
> abstract 클래스를 구현하는 예는 다음과 같다.

```java
public class MemberManager2 extends MemberManagerAbstract{
	public boolean addMember(MemberDTO member) {
		return false;
	}

	public boolean removeMember(String name, String phone) {
		return false;
	}

	public boolean updateMember(MemberDTO member) {
		return false;
	}
}
```

> 왜 배우기 힘들게 이런 abstract 클래스를 만들었을까?

> 인터페이스를 선언하다 보니, 어떤 메소드는 미리 만들어 놓아도 전혀 문제가 없는 경우가 발생한다.
> 그렇다고 해당 클래스를 만들기는 좀 애매하고, 특히 아주 공통적인 기능을 미리 구현해 놓으면 많은 도움이 된다. 이럴때 사용하는 것이 바로 abstract 클래스다.


---

### 3. 나는 내 자식들에게 하나도 안물려 줄꺼여

상속과 관련하여 알아두어야 하는 예약어가 하나 더 있다. 바로 `final`이라는 예약어다. 
`final`은 클래스, 메소드, 변수에 선언할 수 있다.

1) 클래스에 `final`을 선언할 때에는..
`final`을 접근 제어자와 class 예약어 사이에 추가할 수 있다.
```java
public final class FinalClass{

}
```

> 위와 같이 작성하면 상속을 해줄 수가 없다. 즉 자식 클래스를 만들수 없다.

2) 메소드를 `final`로 선언하는 이유는...

메소드를 `final`로 선언하면 더이상 `Overriding`을 할 수가 없다.

```java
public final void printLog(String data){
	
}
```

> final 클래스는 종종 사용하지만 final 메소드는 사용할 일이 거의 없을 것이다.

3) 변수에서 `final`을 쓰는 것은?

클래스나 메소드에 `final`을 사용하면 더 이상 상속을 못 받게 하고, 더이상 `Overriding`할 수 없게 하는것이다. 
하지만, 변수에 `final`을 사용하는 것은 개념이 조금 다르다.
변수에 final을 사용하면 그 변수는 "더 이상 바꿀 수 없다" 라는 말이다.
그래서, 인스턴스 변수나 static으로 선언된 클래스 변수는 선언과 함께 값을 지정해야만 한다.

> 사용시 주의사항을 코드를 통해 살펴보자

```java
public class ....생략{
	final int instanceVariable;
}
```

> 위소스는 컴파일 에러가 발생한다 `final`로 선언되어 있을경우는 다음과 같이 변수생성과 동시에 초기화를 해야한다

```java
public class ....생략{
	final int instanceVariable=1;
}
```

> 그렇다면 매개변수나 지역변수는 어떻까?

```java
public void method(final int parameter){
	final int localVariable;
}
```
> 매개 변수나 지역변수를 `final`로 선언하는 경우에는 반드시 선언할 때 초기화할 필요는 없다.
> 왜냐하면, 매개변수는 이미 초기화가 되어서 넘어왔고, 지역변수는 메소드를 선언하는 중괄호 내에서만 참조되므로 다른 곳에서 변경할 일이 없다. 따라서 컴파일은 전혀 문제없이 된다.
> 단!! 다음과 같이 사용해서는 안된다.

```java
public void method(final int parameter){
	final int localVariable;
    localVariable=2;
    localVariable=3;
    parameter=4;
}
```
> 지역변수를 2로 선언할 때에는 아무런 문제가 없다 , 하지만 바로 다음줄에 3으로 변경했다. 이렇게 사용시엔 컴파일 에러가 발생한다 매개변수 역시 `final`로 선언되어 있기 때문에 다시 값을 할당하면 안된다.
> 즉 fianl로 선언한 변수에 값을 재할당 하면 컴파일 에러가 발생한다.

절대 변하지 않을수 (가령 1월과 12월은 31일까지 있다...)

> 헷갈릴수 있는 부분 객체가 `final`로 선언되어 있을 경우는 어떨까?

-

> 당연히 해당 final 객체는 한 클래스에서 두번 이상 생성할 수 없다 하지만 그객체 안에 있는 객체들은 그러한 제약이 없다 왜냐하면 객체 안에 있는 name,email 등은 final 변수가 아니기 때문이다.

즉 해당 클래스가 final이라고 해서, 그 안에 있는 인스턴스 변수나 클래스 변수가 final은 아니라는 부분은 꼭 기억해 두자.


### 4. enum 클래스라는 상수의 집합도 있다.

상수 :  고정된 값

만약 어떤 클래스가 상수만으로 만들어져 있을 경우에는 반드시 class로 선언할 필요는 없다.
이럴때 class라고 선언하는 부분에 enum이라고 선언하면 "이 객체는 상수의 집합이다."라는 것을 명시적으로 나타낸다

- enum 클래스는 어떻게 보면 타입이지만, 클래스의 일종이다
- 한글로 "열거형" 클래스라고 불러도 무방하다.
- enum 클래스에 있는 상수들은 지금까지 살펴본 변수들과 다르게 별도로 타입을 지정할 필요도, 값을 지정할 필요도 없다
- 해당 상수들의 이름만 콤마로 구분하여 나열해 주면 된다.

> enum 클래스는 어떻게 사용할까?

가장 효과적으로 사용하는 방법은 switch문에서 사용하는 것이다. 

```java
	public int getOverTimeAmount(OverTimeValues value) {
		int amount = 0;
		System.out.println(value);
		switch (value) {
		case THREE_HOUR:
			amount = 18000;
			break;
		case FIVE_HOUR:
			amount = 30000;
			break;
		case WEEKEND_FOUR_HOUR:
			amount = 40000;
			break;
		case WEEKEND_EIGHT_HOUR:
			amount = 60000;
			break;
		}
		return amount;
	}
```
> 그럼 `OverTimeValues` 라는 enum 타입을 어떻게 위 메소드에 전달할까?

```java
public static void main(String args[]) {
	OverTimeManager manager = new OverTimeManager();
	int myAmount = manager.getOverTimeAmount(OverTimeValues.THREE_HOUR);
	System.out.println(myAmount);
}
```

`enum클래스이름.상수이름` 방식으로 지정함으로써 클래스의 객체 생성이 완료된다고 생각하면 된다.

- enum클래스는 생성자를 만들 수 있지만, 생성자를 통하여 객체를 생성할 수는 없다.

> 그럼 `switch` 문이 아닌 enum클래스 선언시 각 상수의 값을 지정할 수는 없을까?

값을 지정하는 것은 가능하다. 단, 값을 동적으로 할당하는 것은 불가능하다.

- enum클래스도 생성자를 사용할 수는 있지만, 생성자의 선언부에 `public`이라고 하지 않고
- 접근지정자가 없거나(package-private)와 private만 사용가능하다.
- 보통 클래스와 마찬가지로 메소드를 선언해서 사용할 수 있다.


### 4. enum 클래스의 부모는 무조건 java.lang.Enum

- enum클래스는 개발자들이 Object클래스 중 일부 메소드를 Overriding 하지 못하도록 막아놓았다.
- hashCode()과 equals()는 사용해도 무방하다
- Object클래스의 메소드를 Overriding한 마지막 메소드는 `toString()`메소드다



#### 정리해봅시다

1. 인터페이스에 선언되어 있는 메소드는 body(몸통)이 있어도 되나요?

	-> 아니요


2. 인터페이스를 구현하는 클래스의 선언시 사용하는 예약어는 무엇인가요?

	-> implements
	=> 클래스 선언시 class 가 들어가는 자리에 interface가 위치해야 한다.

3. 메소드의 일부만 완성되어 있는 클래스를 무엇이라고 하나요?

	->	abstract
	=>	abstract 클래스는 인터페이스처럼 메소드를 선언만 할 수도 있고, 일부 메소드를 구현 할 수도 있다.

4. 3번의 문제의 답에 있는 클래스에 body(몸통)이 없는 메소드를 추가하려면 어떤 예약어를 추가해야 하나요?

	->	extends
	=> abstract 메소드 선언시에는 abstract 예약어를 사용해야 한다. 당연히 해당 클래스도 abstract class로 선언 되어 있어야만 한다.


5. 클래스를 final로 선언하면 어떤 제약이 발생하나요?

	->	상속을 해줄 수 없다


6. 메소드를 final로 선언하면 어떤 제약이 발생하나요?

	->	오버라이딩을 해줄 수 없다


7. 변수를 final로 선언하면 어떤 제약이 발생하나요?

	->	값을 변경하거나 할 수 없다


8. enum 클래스 안에 정의하는 여러 개의 상수들을 나열하기 위해서 상수 사이에 사용하는 기호는 무엇인가요?

	->	enum 클래스의 상수들은 콤마 , 로 구분한다. 


9. enum 으로 선언한 클래스는 어떤 클래스의 상속을 자동으로 받게 되나요? 

	->	java.lang.enum


10. enum 클래스에 선언되어 있지는 않지만 컴파일시 자동으로 추가되는. 상수의 목록을 배열로 리턴하는 메소드는 무엇인가요?

	->	value()








