## 9장. 자바를 배우면 패키지와 접근 제어자는 꼭 알아야 해요

### 1. 패키지는 그냥 폴더의 개념이 아니에요
- 클래스들을 구분 짓는 폴더와 비슷한 개념
- 이름이 중복되거나 어떤클래스가 어떤 일을 하는지 혼동되는 일을 방지하고자 만들어졌다

c/javapackage 폴더 생성후 거기에 아래의 자바파일 생성
```java
package c.javapackage;

public class Package
{
	public static void main(String[] args)
	{
		System.out.println("Package class.");
	}
}
```

> 이때 컴파일과 실행은 다음과 같이 한다.

```
javac c/javapackage/Package.java
java c/javapackage/Package
```
> 패키지 선언문

 - 소스에 가장 첫줄에 위치해야한다.
 패키지 선언 위에 주석이나 공백이 있어도 상관은 없다. 하지만 다른 자바 문장이 하나라도 있으면 컴페일이 제대로 되지 않는다.
	- php의 header와 기능상은 비슷한다 (물론 개념은 다르지만)
 - 패키지 선언은 소스 하나에는 하나만 존재해야한다.
 - 패키지 이름과 위치한 폴더 이름이 같아야만 한다.
**주의사항!! 패키지 이름이 `java`로 시작해서는 안된다.**
---
### 2, 패키지 이름은 이렇게 지어요
| 패키지 시작 이름 | 내용 |
|-------|--------|
| java  | 자바 기본 패키지(JDK 벤더에서 개발) |
| javax | 자바 확장 패키지(JDK 벤더에서 개발) |
| org   | 일반적으로 비 영리단체(오픈소스)의 패키지 |
| com   | 일반적으로 영리단체(회사)의 패키지 |

> 꼭 지켜야 하는 것은 아니지만 대부분의 코드는 이렇게 패키지가 시작된다.

[아파치 그룹의 오픈소스 프로젝트들 참고](http://apache.org)

**패키지 이름을 지정할 때 유의점**
- 패키지 이름은 모두 소문자로 지정해야한다. (가이드 권고사항) 
- 자바의 예약어를 사용하면 절대 안된다
	-`int`,`static`등의 예약어가 패키지 이름에 들어 있으면 안된다.
    - 예를들면 `com.int.util`

이렇게 상위 패키지 이름을 정했으면, 그다음에는 하위 패키지 이름을 정해야만 한다.

자바의 기본 패키지인 java패키지는 java패키지 아래에 io, lang, nio, text, util 등의 여러 패키지가 존재한다.

### 3. import를 이용하여 다른 패키지에 접근하기

패키지가 있을 때, 같은 패키지에 있는 클래스들과 기본~default~ 패키지에 있는 클래스들만 찾을 수 있다.
여기서 기본 패키지에 있는 클래스라고 함은 Package 선언이 되어 있지 않은, 패키지가 없는 클래스들을 말한다.

자세한 내용은 아래 코드를 통해 살펴보자
```java
package c.javapackage.sub;

public class Sub
{
	public sub()
	{

	}

	public void subClassMethod()
	{

	}
}
```
> 위의 `Sub`클래스의 소스는 c/javapackage/sub 라는 폴더에 있어야 한다.

이제 c/javapackage 경로에있는 `Package`클래스의 `main()`메소드에 다음과 같이 `Sub클래스`의 객체를 생성하고 메소드를 호출하자.

```java
public class Package
{
	public static void main(String[] args)
	{
		Sub sub=new Sub();
		sub.subClassMethod();
	}
}
```

> 위 내용을 컴파일 해보면 `Sub` 클래스를 찾지 못한다는 에러가 뜬다

이처럼 다른 패키지에 있는 클래스를 찾지 못할때 사용하는 것이 바로 import이다.
즉 `Package`클래스에 아래와 같이 import 해줘야 한다.

```java
// import 패키지이름.클래스이름
import c.javapackage.sub.Sub;
```

> 또한 `import`해야하는 클래스가 100개일 경우 전부 할수가 없기 때문에 아래와 같이 가능하다.

```java
import c.javapackage.sub.*;
```

___

JDK 5 부터는 `import static`이라는게 추가되었는데 이는
`static`한 변수(클래스변수)와 static 메소드를 사용하고자 할 때 용이하다.
잘 이해가 안가니 Sub클래스에 다음과 같이 추가하자


```java
public final static String CLASS_NAME = "Sub";

public static void subClassStaticMethod() {
	System.out.println("subClassStaticMethod() is called.");
}
```
> 이와 같이 선언되어있는 static변수나 메소드를 사용할때 `import static`을 사용하면된다.

그전에! `import static` 이 없을때 어떻게 하는지를 먼저 알아보자

```java
package c.javapackage;

import c.javapackage.sub.Sub;

public class Package
{
	public static void main(String[] args)
	{
    	sub.subClassStaticMethod();
		System.out.println(Sub.CLASS_NAME);
	}
}
```
> 위처럼 Sub클래스에 선언된 메소드를 사용하겠다는 것을 명시적으로 지정하기 위해서 `Sub.subClassStaticMethod()`로 사용해야 한다.(static 변수도 마찬가지) 

하지만 import static 을 사용한다면 다음과 같이 하면된다.

```java
//생략

import static c.javapackage.sub.Sub.subClassStaticMethod;
import static c.javapackage.sub.Sub.CLASS_NAME;

public class Package {
	public static void main(String[] args) {
		subClassStaticMethod();
		System.out.println(CLASS_NAME);
	}
}
```
> 그리고 위 import처럼 여러줄 입력하기 귀찮다면 다음과 같은 방법도 있다.

```java
import static c.javapackage.sub.Sub.*;
```

여기서 질문!
만약 Package 클래스에 import한 동일한 이름의 static변수나 메소드가 자신의 클래스에 있으면 어떻게 될까?

- 이때는 자신의 변수나 메소드가 import로 불러온 것보다 우선이다.


> import는 꼭 기억하고 있어야 하는 자바의 기본 키워드이다. 
> 그리고, import하지 않아도 되는 패키지는 다음과 같다.

- java.lang 패키지
- 같은 패키지

> 지금까지 사용한 `String`과 `System` 클래스가 전부 java.lang 패키지에 있다.

- 패키지가 같은지 다른지에 따라서 import 여부가 결정된다.
- 폴더구조상 상위 패키지와 하위패키지에 있는 클래스의 상관관계는 전혀 없다.
- 참고로 이클립스를 사용하는 경우는 `Ctrl`+`Shift`+`o` 를 동시에 누르면 자동으로 필요한 패키지를 `import` 해준다

### 4. 자바의 접근 제어자

자바를 배우면 꼭 외우고 이해하고 있어야 하는 것중 하나인 접근제어자에 대해 알아보자
> ##### 접근제어자~Accessmodifier~

접근제어자는 4개가 있으며 클래스,메소드,인스턴스 및 클래스 변수를 선언할 때 사용된다.

1. public : 누구나 접근가능
2. protected : 같은 패키지 내에 있거나 상속받은 경우에만 접근 가능
3. package-private(접근제어자없음) : 같은 패키지 내에 있을 때만 접근 가능
4. private : 해당 클래스 내에서만 접근 가능

```java
public class Sub
{
	public void publicMethod() {
	}

	protected void protectedMethod() {
	}

	void packagePrivateMethod() {
	}

	private void privateMethod() {
	}
}
```
> 이 클래스를 `Package`클래스의 `main()`메소드에서 호출후 컴파일 해보자


이런 접근 제어자는 다른 사람이 가져다 쓰면 안될경우 필요하다 (가령 암호를 계산하는 로직)
변수의 경우 직접 접근해서 변경하지 못하게 하고 꼭 메소드를 통해서 변경이나 조회만 할 수 있도록 할 때 ==접근제어자== 를 많이 사용한다.

> 예를 들어 앞장에서 만든 `MemberDTO` 의 `name` 이라는 변수를 조회만 할 수 있도록 하려면 `name`이라는 변수는 `private`로 선언하고, `name`값을 조회하는 메소드만 만들어 놓으면 된다.
> 이렇게 해놓으면 `name`의 값을 생성자로만 선언하고, 아무도 그 값을 변경하지 못할 것이다.

```java
public class MemberDTO
{
	private String name;
    //중간생략
    public MemberDTO(String name){	//생성자를 통해서 name값 지정
    	this.name=name
    }
    public String getName(){	// 조회용
    	return name;
    }
}
```

> 아래표를 참고하자

|  | 해당 클래스 안에서 | 같은 패키지에서 | 상속받은 클래스에서 | import한 클래스에서 
|--------|--------|--------|--------
| public | O | O | O | O
| protected | O | O | O | X
| package private | O | O | X | X
| private | O | X | X | X

> 은행을 예로 들면

- **public : 은행창구**
- protected : 은행 창구의 직원 자리
- package-private : 지점장실
- **private : 금고**

### 5. 클래스 접근제어자 선언할때의 유의점

지금까지 알아본 사항은 주로 메소드에 대한 내용이었다. 
이 접근 권한은 인스턴스 변수와 클래스 변수에도 동일하게 적용하면 된다.
이 외에, 접근 제어자를 사용할 수 있는 곳은 바로 클래스 선언문이다.

- 클래스를 선언할 때에는 반드시 파일 이름에 해당하는 클래스가 존재해야한다.
	즉 Profile.java 라는 소스코드에는 `Profile`이라는 클래스를 `public`으로 선언해야한다.

- 하나의 파일에 두개의 클래스가 있어도 상관없지만 파일이름과 동일한 이름의 클래스는 `public` 이어야 한다.


==직접해 봅시다== (p.280)

```java
package b.array;

public class Array{

}
```

```java
package b.control;

class ControlOfFlow{

}
```

```java
package b.operator;

class Operators{

}
```

**정리해 봅시다**

1. 패키지를 선언할 때 사용하는 예약어는 무엇인가요?
	-> package

2. 패키지 선언은 클래스 소스 중 어디에 위치해야 하나요?
	-> 소스 최상단

3. 패키지를 선언할 때 가장 상위 패키지의 이름으로 절대 사용하면 안되는 단어는 무엇인가요?
	-> java 

4. 패키지 이름에 예약어가 포함되어도 되나요?
	-> 아니오

5. import는 클래스 내에 선언해도 되나요?
	-> 아니오
	=> **import는 클래스 선언 전에 명시되어 있어야만 한다.**

6. 같은 패키지에 있는 클래스를 사용할 때 import를 해야 하나요?
	-> 아니오
	=> java.lang 패키지도 할필요 없다.

7. 특정 패키지에 있는 클래스들을 모두 import할 때 사용하는 기호는 무엇인가요?
	-> *

8. 클래스에 선언되어 있는 static한 메소드나 변수를 import하려면 어떻게 선언해야 하나요?
	-> import static

9. 접근 제어자 중 가장 접근 권한이 넓은 (어떤 클래스에서도 접근할 수 있는) 것은 무엇인가요?
	-> public

10. 접근 제어자 중 가장 접근 권한이 좁은 (다른 클래스에서는 접근할 수 없는) 것은 무엇인가요?
	-> private

11. 접근 제어자 중 같은 패키지와 상속관계에 있는 클래스만 접근할 수 있도록 제한하는 것은 무엇인가요?
	-> protected

12. Calculate.java라는 자바 소스가 있을 경우, 그 소스 내에는 Calculate라는 클래스외에는 ( )으로 선언된 클래스가 있으면 안된다. 여기서 괄호 안에 들어가야 하는 것은 무엇인가요?
	-> public

