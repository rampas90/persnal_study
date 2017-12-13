## 11장. 모든 클래스의 부모 클래스는 Object에요

### 1. 모든 자바 클래스의 부모인 `java.lang.Object` 클래스

> 아래소스를 `javap`로 확인해보자

```java
public class InheritanceObject{
	public static void main(String[] args){
    
    }
}
```
> 클래스 선언문이 다음과 같이 되어있다.

`public class InheritanceObject extends java.lang.Object`

> 즉 자동으로 Object 클래스를 확장한 것을 알 수 있다.

- 여기서 주의점은 자바는 이중상속은 안되지면 여러단계로 상속은 가능하다

---

### 2. 가장 많이 쓰이는 `toString()` 메소드

객체를 처리하기 위한 메소드
- toString()
- equals()
- hashCode()
- getClass()
- clone()
- finalize()

> `to String()` 메소드

```java
public void toStringMethod(InheritanceObject obj) {
	System.out.println(obj);
	System.out.println(obj.toString());
	System.out.println("plus " + obj);
}
```
> 결과를 보면 그냥 출력하는 것과 toString() 메소드를 호출하는 것은 동일한 것을 볼수 있다.

> 위 코드를 개선해보자

```java
public void toStringMethod2() {
	System.out.println(this);
	System.out.println(toString());
	System.out.println("plus " + this);
}
```

`toString()`메소드는 그냥 사용하는게 아니라 직접 구현해야한다( 오버라이딩 )

```java
public String toString() {
	return "InheritanceObject class";
}
```

> 변수가 몇개 안될경우는 상관없지만 10개를 넘어가거나 할 경우 효율이 떨어질 우려가 있다
> 이런 상황은 이클립스같은 IDE를 통해 추후 알아보자
> 
---

### 3. 객체는 == 만으로 확인이 안 되므로, equals()를 사용한다.

- 비교연산자인 `==`, `!=`는 기본자료형에서만 사용가능하다

> 정확하게는 "값"을 비교하는 것이 아니라 "주소값"을 비교한다고 생각하자.

```java
MemberDTO obj1 = new MemberDTO("Sangmin");
MemberDTO obj2 = new MemberDTO("Sangmin");
```
> 두 객체는 각각의 생성자를 사용하여 만들었기 때문에 주소값이 다름을 알 수 있다.
> 속성값은 같지만 서로 다른 객체라는 말이다. 
> 바로 이때 사용하는것이 `equals()`메소드 이다.
> 이 메소드 역시 Object 클래스에 선언되어있는 `equals()`메소드를 오버라이딩 해 놓아야지만
> 제대로 된 비교가 가능하다.
> 그럼 비교를 아래와 같이 해보자

```java
if (obj1.equals(obj2)) {
	System.out.println("obj1 and obj2 is same");
} 
```

> 이래도 결과는 다르다고 나온다
> 바로 이게 equals()메소드를 오버라이딩 하지 않았기 때문이다 
> 즉 `hashCode()` 값을 비교하기 때문이다.
> 아무리 클래스의 인스턴스 변수값들이 같다고 하더라도 , 서로 다른 생성자로 객체를 생성했으면
> 해시 코드가 다르니 위처럼 객체가 다르다는 결과가 나오는 것이다.

`equals()`메소드를 Overriding 할때는 다음의 다섯가지 조건을 충족해야 한다.

-  재귀 : null이 아닌 x라는 객체의 x.equals(x)결과는 항상 true 여야만 한다.
-  대칭 : null이 아닌 x와 y객체가 있을 때 y.equals(x)가 true를 리턴했다면, x.equals(y)도 반드시 true를 리턴해야만 한다.
-  타동적 : null이 아닌 x,y,z가 있을 때 x.equals(y)가 True를 리턴하고, y.equals(z)가  true 를 리턴하면, x.equals(z)는 반드시 true를 리턴해야만 한다.
-  일관 : null이 아닌 x와y가 있을 때 객체가 변경되지 않은 상황에서는 몇 번을 호출하더라도, x.equals(y)의 결과는 항상 true이거나 항상 false여야 한다.
-  null과의 비교 : null이 아닌 x라는 객체의 x.equals(null) 결과는 항상 false여야만 한다.

> `equals()`메소드를 Overriding 할때에는 `hashCode()`메소드도 같이 Overriding 해야만 한다.


---







