## 1장. 매번 만들기 귀찮은데 누가 만들어 놓은 거 쓸 수 없나요?

### 미리 만들어 놓은 클래스들은 아주 많아요

밑바닥에서부터 자바 프로그램을 아무 것도 없는 상태에서 개발한다고 생각해보자..
아주 간단한 프로그램을 작성하더라도 꽤 오랜 시간과 노력이 소요될 것이다.

> 그래서 우리가 설치해서 사용중인 `JDK`에는 엄청나게 많은 클래스와 메소드들이 포함되어 있다.
> 물론, `JDK`에 포함된 클래스들 외에도 많은 클래스들이 존재한다.

이런 많은 클래스를 사용할 때 참조하는 문서가 바로 `API`라는 것이다.

> [자바 API 바로가기(JDK 7)](http://docs.oracle.com/javase/7/docs/api/) 

`java.lang`패키지의 `String`클래스를 살펴보자 [해당 링크 바로가기](http://docs.oracle.com/javase/7/docs/api/)


##### 1) 패키지와 클래스 / 인터페이스 이름
- 패키지와 클래스 이름이 다음과 같이 제공된다.


```java
java.lang
   Class String
```

##### 2) 클래스 상속 고나계 다이어그램(Class Inheritance Diagram)
- 이 클래스가 어떤 클래스들의 상속을 받았는지에 대한 관계를 간단한 계단식을 보여준다.

```java
java.lang.Object
	java.lang.String
```

> 이 관계를 보는 것은 아주~~~~~~~중요하다. 왜냐하면, 해당 클래스의 객체에서 사용할 수는 있지만, 지금 보고 있는 페이지에 메소드에 대한 상세 설명은 존재하지 않을 수도 있기 때문이다.

> API 문서는 부모클래스에 선언되었지만, 자식 클래스에서 별도로 `오버라이딩`을 하지 않은 메소드는 자세한 설명이 제공되지 않는다.
> 따라서, 사용 가능한 메소드가 있는데 그 클래스의 API에 없다면 부모 클래스들의 메소드들을 살펴봐야만 한다.

##### 3) 직속 자식 클래스(Direct Known Subclasses)
- 현재 보고 있는 클래스를 확장한 클래스들의 목록이 나타난다.
- 지금 보고있는 `String`클래스는 `final`클래스이기 때문에 더이상 자식을 갖지 못한다.
- 참고를 위해 같은 패키지에 있는 `Throwable`클래스의 직속 자식 클래스 내용을 보자

```java
Direct Known Subclasses:
	 Error, Exception
```

##### 4) 알려진 모든 하위 인터페이스 목록(All Known Subinterfaces): 인터페이스에만 존재함
- 인터페이스를 상속받은 인터페이스 목록을 나타낸다.

> `String`클래스는 인터페이스가 아니므로, `java.lang` 패키지의 `Runnable`이라는 인터페이스의 관련된 정보를 보자

```java
All Known Subinterfaces:
RunnableFuture<V>, RunnableScheduledFuture<V>
```

##### 5) 알려진 모든 구현한 클래스 목록(All Known Implementing Classes) : 인터페이스에만 존재함
- 해당 인터페이스를 구현한 클래스들의 목록이다. 방금 살펴본 `Runnable`이라는 인터페이스와 관련된 정보는 다음과 같다.

```java
All Known Implementing Classes:
AsyncBoxView.ChildState, ForkJoinWorkerThread, FutureTask, RenderableImageProducer, SwingWorker, Thread, TimerTask
```

> 어떤 인터페이스로 선언된 매개 변수를 메소드에 넘겨줘야 할 때, 여기에 구현된 클래스의 객체를 넘겨주면 된다.
> 즉, `Runnable`인터페이스를 매개 변수로 사용하는 메소드가 있으면, 끝에서 두번째에 있는 `Thread`객체를 생성해서 넘겨주면 된다는 것이다.

##### 6) 구현한 모든 인터페이스 목록(All Implemented Interfaces) : 클래스에만 존재함
- 클래스에서 구현~implements~한 모든 인터페이스의 목록을 나열한다.

```java
All Implemented Interfaces:
    Serializable, CharSequence, Comparable<String>
```

여기에 명시되어 있는 모든 인터페이스의 메소드들은 해당 클래스에서 반드시 구현되어 있을 것이다.

##### 7) 클래스/인터페이스의 선언상태(Class/Interface Declaration)
- 클래스의 선언 상태를 볼 수 있다.

```java
public final class String
extends Object
implements Serializable, Comparable<String>, CharSequence
```

> 즉, 클래스가 어떤 접근 제어자를 사용했는지, `final`로 선언된 클래스인지 등을 확인할 수 있다.

##### 8) 클래스 / 인터페이스의 설명(Class / Interface Description)
- 클래스에 대한 상세한 설명을 볼 수 있다.
- 이 영역에서 제공되는 내용은 클래스의 용도, 클래스사용법, 사용 예 등이 자유롭게 기술되어 있다.

```java
/*
The String class represents character strings. All string literals in Java programs, such as "abc", are implemented as instances of this class.
Strings are constant; their values cannot be changed after they are created. String buffers support mutable strings. Because String objects are immutable they can be shared. For example:
*/
     String str = "abc";
 
//is equivalent to:

     char data[] = {'a', 'b', 'c'};
     String str = new String(data);
 
//Here are some more examples of how strings can be used:

     System.out.println("abc");
     String cde = "cde";
     System.out.println("abc" + cde);
     String c = "abc".substring(2,3);
     String d = cde.substring(1, 2);
/*
The class String includes methods for examining individual characters of the sequence, for comparing strings, for searching strings, for extracting substrings, and for creating a copy of a string with all characters translated to uppercase or to lowercase. Case mapping is based on the Unicode Standard version specified by the Character class.

이하 생략
*/
//Since:
	//JDK1.0
//See Also:
	Object.toString(), StringBuffer, StringBuilder, Charset, Serialized Form

```
> 그런데, 이 설명 부분에서 잊지 않고 봐야 하는 영역이 있다.
> 바로 설명의 가장 아래에 있는 `Since`와 `Also`부분이다.

- `Since`는 해당 클래스가 `JDK`에 추가된 버전이 명시된다.
	- `String`클래스는 자바가 처음 만들어지면서부터 생긴 클래스기 때문에 `JDK 1.0`으로 되어 있지만, 
	- `JDK`버전이 올라가면서 많은 클래스들이 추가되었기 때문에 개발할 때에는 이 부분을 꼭 확인해 봐야한다.
	예를 들어 `JDK 5`기반의 시스템을 만드는데 `JDK 6`나 `7`의 API문서를 보면서 개발하면, 안된다.
    > 현재 클래스가 만들어질 때 같이 만든 상수 필드나 메소드에는 `Since`가 없지만, 그 이후의 `JDK`에 포함된
    > 상수 필드나 메소드에는 `Since`가 명시되어 있다.
    > 생소한 메소드가 있다면 , API 문서를 확인하여 언제부터 추가됬는지 확인하는 습관을 가지자

- `See Also`는 그 클래스와 관련되어 있는 모든 클래스나 인터페이스, 메소드 등의 링크가 제공된다.


##### 9) 내부 클래스 종합(Nested Class Summary)
- 클래스 안에 `public`하게 선언되어 있는 클래스인 내부 클래스의 목록이 제공된다.
- 내부 클래스는 해당 클래스에서 직접 호출하여 사용할 수 있기 때문에 쉽게 사용할 수 있다.

> `String`클래스에는 내부 클래스가 없으니, `Thread`클래스의 내부 클래스를 살펴보자

	


##### 10) 상수 필드 종합(Field Summary)
- 클래스에는 public static으로 선언한 상수 필드가 존재할 수 있다.
- 이 값은 바뀌지 않기 때문에 여러모로 많이 사용된다.

> `String` 클래스를 살펴보자.

##### 11) 생성자 종합(Constructor Summary)
- 클래스에 어떤 생성자들이 선언되어 있는지를 목록으로 제공한다.
- 어떤 생성자가 있는지 한눈에 볼 수 있기 때문에 아주 유용하다.
- 이 종합 목록에 있는 생성자에 대한 설명이 부족하면, 그 생성자에 걸려 있는 링크를 클릭하면 아래에 있는 생성자 상세 설명으로 이동한다.

##### 11) 메소드 종합(Method Summary)
- 클래스에 선언되어 있는 모든 `public` 및 `protected` 메소드에 대한 종합 정보를 제공한다.
- 이 내용이 앞으로 개발하면서 가장 많이 봐야 하는 부분이다.
- 왜냐하면 어떤 메소드가 있는지 쉽게 확인할 수 있고, 각 메소드의 리턴 타입이 어떤 것이고, 매개변수로 어떤 것을 넘겨줘야 하는지를 알 수 있기 때문이다.

> 표를 보면 가장 왼쪽 열에 있는 것은 해당 메소드에 선언되어 있는 `modifier`들이 나열된다.
> 만약 메소드의 `modifier`가 `public` 뿐이라면 리턴 타입만 표시된다.
> `String`에 나오는 내용들은 모두 리턴 타입뿐이다.
> 그런데, 해당 메소드가 `static`메소드라면 `static`이 추가로 명시된다.

`public`이 아니라 `protected`라고 선언되어 있는 메소드도 이 API에 표시된다. 왜그럴까?

> `protected`는 상속을 받은 클래스에서만 사용할 수 있다. 다시말해서, 해당클래스를 상속받아 자식 클래스를 개발할 때
> 어떤 메소드가 있는지를 알아야 하는데, API에 `protected`메소드에 대한 정보가 없으면 `public`메소드만 `오버라이딩` 할 수 있을 것이다.

> 그래서 API문서에는 `protected`로 선언된 메소드도 포함되어야만 한다.

##### 12) 부모 클래스로부터 상속받은 메소드(Methods ingerited from parent)
- 여기에는 간단히 부모 클래스로부터 상속받은 메소드들이 나열된다.
- 부모클래스가 여러개라면 각 클래스별로 목록을 별도로 제공한다.

```java
Methods inherited from class java.lang.Object
clone, finalize, getClass, notify, notifyAll, wait, wait, wait
```

##### 13) 상수 필드 상세 설명(Field Detail)
- 클래스에 선언된 상수 필드가 어떤 내용을 제공하는지에 대한 상세한 설명을 나열한다.
- 여기에도 Since와 See Also가 제공되는 경우가 있다.

##### 14) 생성자 상세 설명(Constructor Detail)
- 생성자를 어떻게 사용하고, 매개변수에 어떤 값들이 제공되어야 하는지, 어떤 리턴 값을 제공하는지,
- 이 생성자에서 던지는(throws하는) 예외는 언제 발생하는지를 확인할 수 있다.
- 마찬가지로 Since와 See Also가 제공되는 경우가 있다.


##### 15) 메소드 상세 설명(Method Detail)



### Deprecated 라고 표시되어 있는 것은 뭐야?
- "디프리케이티드"라고 읽으며, 사전적의미는 "(강력히)반대하다" 라는 말이다.
- 이 `Deprecated`는 생성자, 상수 필드, 메소드에 선언되어 있다.

> 쉽게 말해서 필요해서 말했는데 나중에 쓰다보니 여러가지 문제를 야기시키거나, 혼동을 가져와서, 혹은 더이상 가치가 없을때 `Deprecated`로 처리되어 버린다.

그럼 왜 없애버리지 않고 `Deprecated`라고 표시하는 걸까?

> 그 이유는 호환성~compatibility~때문이다. (버전업을 생각해보자)
> 이 외에 여러 발생 가능한 문제들이 많기 때문에 `Deprecated`로 표시하는 것이다.

참고로 이클립스에서는 중간에 줄을그어 `Deprecated`된 메소드등을 표시해준다.
그외에 는 컴파일단에서 알려주거나 가이드를 해준다...참고하자

### Header와 Footer



