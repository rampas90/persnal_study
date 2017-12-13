## 4장. 실수를 방지하기 위한 제네릭이라는 것도 있어요

### 실수를 방지할 수 있도록 도와주는 제네릭

- 이클립스를 사용하면 코딩단계에서 자잘한 오타등을 매우 쉽게 걸러낼 수 있다.
- 메소드 개발과 함께 `JUnit`과 같은 테스트 코드를 작성하는 것이 좋다

> JUnit 이란?
> 메소드나 클래스 같은 작은 단위를 쉽게 테스트할 수 있도록 도와주는 프레임워크다.

- 그런데, 테스트를 열심히 해도 개발자가 미처 생각지 못한 부분에 대해서는 테스트 케이스를 만들지 못할 수도 있다.
- 특히 자바는 여러 타입들이 존재하기 때문에, 형 변환을 하면서 예외가 발생할 수 있다.

```java
import java.io.Serializable;

public class CastingDTO implements Serializable{
	private Object object;

	public void setObject(Object object){
		this.object=object;
	}
	public Object getObject(){
		return object;
	}
}
```
> 이처럼 private변수, getter, setter, Serializable 구현을 해야만 제대로 된 DTO 클래스라고 할 수 있다.

```java
public class GenericSample
{
	public static void main(String[] args) 
	{
		GenericSample sample = new GenericSample();
		sample.checkCastingDTO();

	}

	public void checkCastingDTO(){
		CastingDTO dto1=new CastingDTO();
		dto1.setObject(new String());

		CastingDTO dto2=new CastingDTO();
		dto2.setObject(new StringBuilder());

		CastingDTO dto3=new CastingDTO();
		dto3.setObject(new StringBuffer());
	}

}
```

> 위 코드는 문제 없이 컴파일 되지만 문제는 저장되어 있는 값을 꺼낼 때 발생한다.
> 각 객체의 getObject()메소드를 호출했을 때 리턴 값으로 넘어오는 타입은 Object다. 그래서 다음과 같이 형변환을 해야한다.

```java
String temp1=(String)dto1.getObject();
StringBuffer temp2=(StringBuffer)dto2.getObject();
StringBuilder temp3=(StringBuilder)dto3.getObject();
```

> 그런데 dto2의 인스턴스 변수의 타입이 StringBuffer인지 , StringBuilder 인지 혼동될 경우는 어떻게 할까?

그럴때는 다음과 같이 instanceof라는 예약어를 사용하여 타입을 점검해야 한다.

```java
Object tempObject=dto2.getObject();

if(tempObject instanceof StringBuilder){
	System.out.println("StringBuilder");
}
else if(tempobject instanceof StringBuffer){
	System.out.println("StringBuffer");
}
```

> 그런데, 꼭 이렇게 타입을 점검해야 할까? 이러한 단점을 보완하기 위해서 JDK 5부터 새롭게 추가된 `제네릭`이라는 것이 있다.

---

### 제네릭이 뭐지?

- 제네릭은 앞에서 살펴본 타입 형 변환에서 발생할 수 있는 문제점을 "사전"에 없애기 위해서 만들어졌다.
- 여기서 "사전"이라고 하는 것은 실행시에 예외가 발생하는 것을 처리하는 것이 아니라, 컴파일할 때 점검할 수 있도록 한 것을 말한다.

> 위에서 다뤘던 클래스를 제네릭으로 선언하면 다음과 같다.

```java
package d.generic;

import java.io.Serializable;

public class CastingGenericDTO<T> implements Serializable {
	private T object;

	public void setObject(T obj) {
		this.object = obj;
	}

	public T getObject() {
		return object;
	}
}

```

- 여기서 T는 아무런 이름이나 지정해도 된다.
- `<>`안에 선언한 그 이름은 클래스 안에서 하나의 타입이름처럼 사용하면 된다.
- 가상의 타입 이름이라고 생각하자

> 그렇다면, 이렇게 선언한 클래스를 어떻게 사용하면 될까?

```java
public void checkGenericDTO() {
	CastingGenericDTO<String> dto1=new CastingGenericDTO<String>();
	dto1.setObject(new String());
    
	CastingGenericDTO<StringBuffer> dto2=new CastingGenericDTO<StringBuffer>();
	dto2.setObject(new StringBuffer());
    
	CastingGenericDTO<StringBuilder> dto3=new CastingGenericDTO<StringBuilder>();
	dto3.setObject(new StringBuilder());
}
```

> 얼핏보면 객체 선언시 각 타입도 `<>`안에 명시해줘야 하고 귀찮아지고 번거로워진것 같지만
> 이 객체들을 `getObject()`메소드로 가져올때는 다음과 같이 간단해진다.

```java
String temp1=dto1.getObject();
StringBuffer temp2=dto2.getObject();
StringBuilder temp1=dto3.getObject();
```

> 소스를 보면 형 변환을 할 필요가 없어진 것을 알수 있다.
> 왜냐하면 해당객체에 선언되어 있는 dto1~3의 제네릭 타입은 각각 String, StringBuffer, StringBuilder이기 때문에 
> 만약 잘못된 타입으로 치환하면 컴파일 자체가 안된다.
> 따라서, "실행시"에 다른 타입으로 잘못 형 변환하여 예외가 발생하는 일은 없다.

이와 같이 명시적으로 타입을 지정할 때 사용하는 것이 제네릭이라는 것이다.

---

### 제네릭 타입의 이름 정하기

제네릭 타입을 선언할 때 `<>`안에 어떤 단어가 들어가도 상관없지만 자바에서 정의한 기본규칙은 있다.

- E : 요소(Element, 자바 컬렉션(Collection)에서 주로 사용됨)
- K : 키
- N : 숫자
- T : 타입
- V : 값
- S, U, V : 두번째, 세번째, 네번째에 선언된 타입

---

### 제네릭에 ?가 있는 것은 뭐야?

> 간단한 제네릭 클래스다

```java
public class WildcardGeneric<W> {
	W wildcard;
	public void setWildcard(W wildcard) {
		this.wildcard=wildcard;
	}
	public W getWildcard() {
		return wildcard;
	}
}

```
> 위 클래스를 사용하는 클래스다

```java

public class WildcardSample {

	public static void main(String[] args) {
		WildcardSample sample = new WildcardSample();
//		sample.callWildcardMethod();
//		sample.callBoundedWildcardMethod();
		sample.callGenericMethod();
	}

	public void callWildcardMethod() {
    	// WildcardGeneric 이라는 클래스에 String을 사용하는 제네릭한 객체를 생성한다.
		WildcardGeneric<String> wildcard = new WildcardGeneric<String>();
		wildcard.setWildcard("A");
        // 생성한 객체로 wildcardMethod()를 호출할때 넘겨준다.
		wildcardMethod(wildcard);
	}

	public void wildcardMethod(WildcardGeneric<?> c) {
		Object value=c.getWildcard();
		System.out.println(value);
	}
}
```

> 이렇게 String등 대신에 `?`를 적어주면 어떤 타입이 제네릭 타입이 되더라도 상관없다.

- 매소드 내부에서는 해당 타입을 정확히 모르기 때문에 앞서 사용한 것처럼 String으로 갑을 받을 수는 없고,
Object로 처리해야만 한다.

> 여기서 `?`로 명시한 타입을 영어로는 `wildcard` 타입이라고 부른다.

- 만약 넘어오는 타입이 두 세가지로 정해져 있다면, 메소드 내에서 instanceof 예약어를 사용하여 해당 타입을 확인하면 된다.

> 이렇게 사용한 `widlcard`는 메소드의 매개변수로만 사용하는 것이 좋다.


---

### 제네릭 선언에 사용하는 타입의 범위도 지정할 수 있다.

- 제네릭을 사용할 때 `<>`안에는 어떤 타입이라도 상관 없다고 했지만, wildcard로 사용하는 타입을 제한할 수는 있다
- `?`대신 `? extends 타입`으로 선택하는 것이다.


```java

public class Car {
	protected String name;
	public Car(String name) {
		this.name=name;
	}
	public String toString() {
		return "Car name="+name;
	}
}
```

> Car 클래스를 상속받은 Bus 클래스를 보자

```java
public class Bus extends Car {
	public Bus(String name) {
		super(name);
	}
	public String toString() {
		return "Bus name="+name;
	}
}
```

```java
public void callBoundedWildcardMethod() {
	WildcardGeneric<Car> wildcard=new WildcardGeneric<Car>();
	wildcard.setWildcard(new Car("BMW"));

	wildcardMethod(wildcard);
}
public void boundedWildcardMethod(WildcardGeneric<? extends Car> c) {
	Car value=c.getWildcard();
	System.out.println(value);
}
```

> 앞서 사용했던 `?`라는 wildcard는 어떤 타입이 오더라도 상관이 없었다.
> 하지만 `boundedWildcardMethod()`에는 `?`대신 `? extends Car`라고 적어 준것을 확인하자
> 이렇게 정의한 것은 제네릭 타입으로 Car를 상속받은 모든 클래스를 사용할 수 있다는 의미다.

> 따라서, `boundedWildcardMethod()`의 매개변수에는 다른 타입을 제네릭 타입으로 선언한 객체가 넘어올 수 없다.
> 즉, 컴파일시에 에러가 발생하므로 반드시 Car 클래스를 확장한 클래스가 넘어와야만 한다.


---

### 메소드를 제네릭하게 선언하기

앞에서 알아본 `wildcard`로 메소드를 선언하는 방법은 큰 단점이 있다.
이렇게 선언된 객체에 값을 추가할 수가 없다는 것이다.
그 방법을 알아보자

```java
public <T> void genericMethod(WildcardGeneric<T> c, T addValue) {
	c.setWildcard(addValue);
	T value=c.getWildcard();
	System.out.println(value);
}
```

메소드 선언부를 보면 리턴타입앞에 `<>`로 제네릭타입을 선언해 놓았다.
그리고, 매개변수에는 그 제네릭 타입이 포함된 객체를 받아서 처리한 것도 알 수 잇다.
그리고 메소드 첫문장에는 `setWildcard()`메소드를 통해 값을 할당까지 했다.

> 이처럼 메소드 선언시 리턴 타입 앞에 제네릭한 타입을 선언해 주고, 그 타입을 매개 변수로 사용하면 문제가 없다.
> 게다가 값도 할당할 수 있다.

`?`를 사용하는 `Wildcard`처럼 타입을 두리뭉실하게 하는 것보다는 명시적으로 메소드 선언시 타입을 지정해 주면 보다 더 견고한 코드를 작성할 수 있다.


---

### 정리해 봅시다

1. 제네릭은 타입 형 병환에서 발생할 수 있는 문제점을 "사전"에 없애기 위해서 만들어졌다.

2. 제네릭의 선언시 타입 이름은 예약어만 아니면 어떤 단어도 사용할 수 있다. 단, 일반적으로 대문자로 시작한다.

3. ? 를 제네릭 선언 꺽쇠 사이에 넣으면 Wildcard로 어떤 타입도 올 수 있다.

4. 특정 타입으로 제네릭을 제한하고 싶을 때에는 "? extends 타입"을 제네릭 선언 안에 넣으면 된다.

5. Wildcard 타입을 Object 타입으로만 사용해야 한다.

6. 꺽쇠 안에 원하는 제네릭 타입을 명시함으로써 제네릭한 메소드를 선언할 수 있다. 