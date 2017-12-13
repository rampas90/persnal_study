## 8장. 참조 자료형에 대해서 더 자세히 알아봅시다.

### 1. 참조 자료형은 나머지 다에요.
- 기본자료형과 가장 큰 차이는 `new`라는 예약어를 사용해서 객체를 생성하는지 여부의 차이
- 단 `new`없이 객체를 생성할 수 있는 참조 자료형은 오직 `String`



---

### 2. 기본생성자
- 생성자를 만들지 않아도 자동으로 만들어지는 기본생성자는??
	-> 대표적으로 `main()` 메소드에서 클래스의 이름으로 객체를 생성한 것이 바로 기본생성자!.

> 반복학습 차원에서 다시한번 기본구조 작성해보기 (참고 없이 바로) --

```java
public class ReferenceTypes
{
	public static void main(String[] args)
    {
    	ReferenceTypes refer = new ReferenceTypes();
    }
}
```
> #####정리해보기
> - ReferenceTypes 클래스의 인스턴스인 reference를 만들었다.
> - 등호 우측에 `new` 옆에 있는 `ReferenceTypes()` <--이게 생성자

```java
	public ReferenceTypes(String data) {

	}

	public static void main(String[] args) {
		ReferenceTypes sample = new ReferenceTypes();
	}
```

> 위처럼 컴파일 하면 아무런 매개변수가 없는 생성자는 다른 생성자가 없을경우에 기본으로 만들어지기 때문에
> 매개변수가 없는 생성자를 사용하고 싶다면 아래처럼 기본생성자를 직접 생성해줘야 해야한다.

```java
	public ReferenceTypes() {

	}

	public ReferenceTypes(String data) {

	}

	public static void main(String[] args) {
		ReferenceTypes sample = new ReferenceTypes();
	}
```

##### **자바에서 생성자는 왜 필요할까?**
 생소한 개념으로 가장 헷갈렸던 부분인 만큼 확실한 숙지가 필요하다.
- 자바 클래스의 객체(또는 인스턴스)를 생성하기 위해서 존재한다
- 메소드와 생성자는 선언방식이 비슷하지만. 생성자는 **리턴타입이 없고**, 메소드 이름대신 **클래스 이름과 동일**하게 이름을 지정한다.
- 리턴타입이 없는 이유는 생성자의 리턴타입은 **클래스의 객체**이기 때문이며, 클래스와 이름이 동일해야 컴파일러가 "아~ 얘가 생성자구나~" 하고 알아 먹기 때문.
- 추가로. 생성자를 작성할때에는 클래스의 가장 윗부분에 선언하는 것이 좋다.

---

### 3. 생성자는 몇개까지 만들 수 있을까?
제목부터가 첫날부터 지금까지 끊임없이 생성자에 대한 정의로 날 혼란스럽게 했던 문구...주의깊게 살펴보자

- 1개여도 되고 100개가 되도 상관이 없다.
- 자바의 패턴중에서 `DTO`라는 것이 있다. 어떤 속성을 갖는 클래스를 만들고, 그 속성들을 쉽게 전달하기 위해서
`DTO` 라는 것을 만든다.
- `DTO`는 `Data Transfer Object`의 약자로 비슷한 클래스로 `VO`라는 것도 있다.
-  `VO`는 `Value Object`의 약자로 `DTO`와 형태는 동일하지만, 데이터를 담아두기 위한 목적으로 사용되며,`DTO`는 데이터를 다른 서버로 전달하기 위한 것이 주 목적이다. 
-  어떻게 보면 `DTO`가 `VO`를 포함한다고도 볼 수 있기 때문에 대부분 `DTO`라는 명칭을 선호한다.

> [자바패턴 검색 바로가기](https://www.google.co.kr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=%EC%9E%90%EB%B0%94%20%ED%8C%A8%ED%84%B4)




- 한 사람의 개인정보를 담은 `DTO`클래스가 있다고 생각해보자. 

> 이름, 전화번호, 이메일주소 세가지의 정보를 담고 있는 `MemberDTO`라는 클래스를 만들어보자

```java
public class MemberDTO
{
	public String name;
    public String phone;
    public String email;
}
```
> 위처럼 `DTO`를 만들어두면 무슨 장점이 있을까?

자바의 메소드를 선언할 때 리턴 타입은 한 가지만 선언할 수 있다. 
이때 `DTO`를 만들어 놓으면 메소드의 리턴 타입에 `MemberDTO`로 선언하고, 그 객체를 리턴해주면 된다.
```java
public MemberDTO getMemberDTO
{
	MemberDTO dto=new MemberDTO();
    return dto;
}
```
> 위에 만든 클래스와 패턴까지 연계해가면서 알아보는 이유는 바로 생성자 때문이다.
> 이 클래스의 객체를 생성할때는
	- 그사람의 아무 정보도 모를 때
	- 이름만 알때
	- 이름과 전화번호만 알때
	- 모든 정보를 알고 있을 때도 있다.
> 이러한 각각의 상황에 따른 생성자를 `MemberDTO`에 추가하면 다음과 같이 구현할 수 있다.

```java
public class MemberDTO {
	public String name;
	// public static String name;
	public String phone;
	public String email;

	/**
	 * 아무 정보도 모를때
	 */
	public MemberDTO() {
	}

	/**
	 * 이름만 알때
	 * 
	 * @param name
	 */
	public MemberDTO(String name) {
		this.name = name;
	}

	/**
	 * 이름과 전화번호만 알 때
	 * 
	 * @param name
	 * @param phone
	 */
	public MemberDTO(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	/**
	 * 모든 정보를 알고 있을 
	 * 
	 * @param name
	 * @param phone
	 * @param email
	 */
	public MemberDTO(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
}
```

> 위의 코드를 보면 매개변수가 없는 생성자를 제외하고 모두 `this`라는 예약어가 사용된 것을 볼 수 있다.
> 이 예약어를 변수에 사용할 때에는 객체의 변수와 매개 변수의 이름이 동일 할 때, 인스턴스의 변수를 구분하기 위해서 사용한다. 

방금 만든 네 개의 생성자를 사용하여 `MemberDTO`의 객체를 생성해보자.

```java
public void makeMemberObject() 
{
	MemberDTO dto1 = new MemberDTO();
	MemberDTO dto2 = new MemberDTO("Sangmin");
	MemberDTO dto3 = new MemberDTO("Sangmin", "010XXXXYYYY");
	MemberDTO dto4 = new MemberDTO("Sangmin", "010XXXXYYYY",
			"god@godofjava.com");
}
```
> 이렇게 네 가지 생성자를 모두 활용하여 객체를 생성할 수 있다.
> 어떻게 보면 모두 `MemberDTO`의 객체들이지만 이 네 가지 생성자로 만든 객체들은 서로 다른 속성값들을 갖고 있다.

- dto1 : 아무런 속성값도 설정된 것이 없기 때문에 모든 문자열의 값이 지정되어 있지 않다.
- dto2 : 이름만 지정되어 있다.
- dto3 : 이름과 전화번호가 지정되어 있다.
- dto4 : 모든 속성값이 지정되어 있다.

---

### 4. 이 객체의 변수와 매개변수를 구분하기 위한 ==this==

`javascript`에서 사용하면서도 명확한 개념이 없었던 `this`에관한 설명

- 말그대로 "이 객체"의 의미다.
- 생성자와 메소드 안에서 사용할 수 있다.

> 앞장에서 살펴본 생성자 중 제일 상단에 매개변수를 하나만 받는 생성자를 다시 살펴보자

```java
public class MemberDTO
{
	public String name;
    public String phone;
    public String email;
    public MemberDTO(String name)
    {
    	this.name=name;
    }
}
```
> 이 코드에 `this`라는 예약어가 없으면 어떻게 될까? (생각해보기)

컴파일러(javac)입장에서 생각해보자.

변수인 name도 있고 매개 변수로 넘어온 name도 있다.
즉 `this.name`이라고 지정해 주면, 매개 변수 안에 있는 name이 아닌 "이 객체의 name"이라고 명시적으로 표현하는게 된다!

> `this`예약어는 이렇게 변수에만 지정할 수 있는 것이 아니고, 메소드에도 지정할 수 있다.
> ==상속==에 관한 내용을 배운후에 살펴보자

---

### 5. 메소드 overloading
이 장의 앞 부분에서 살펴본 것처럼 클래스의 생성자는 매개 변수들을 서로 다르게하여 선언할 수 있다.
그렇다면 메소드도 이렇게 이름은 같게 하고 매개변수들을 다르게 하여 만들 수 있을까?

> 자바의 중요한 개념이라고 하니 반복숙달 차원에서 long, byte등으로 컴파일 실행 과정

```java
	public void print(int data) {
	}

	public void print(String data) {
	}

	public void print(int intData, String stringData) {
	}

	public void print(String stringData, int intData) {
	}
```

> 

 위의 메소드들은 모두 이름이 동일하지만 다른 메소드로 취급된다.

| 꼭 기억하세요! | 
|--------|
| 여기에 있는 메소드들은 이름이 같지만 전부 다른 메소드로 취급된다. 즉 ==매개변수의 타입==에 따라 다르게 인식되며 3,4번째메소드처럼 순서가 다르더라도 다르게 인식된다 |

> 이와같이 메소드의 이름을 같도록 하고, 
매개 변수만을 다르게 하는 것을 바로 오버로딩 ~overloading~이라고한다.

개념이 헷갈린다면 그동안 많이 사용한 `System.oub.println()`메소드를 생각해보자.
이 메소드의 매개변수로, `int`만 넘겨줘도 되고, `long`만 넘겨줘도 `String`만 넘겨줘도 된다.
이게 바로 오버로딩의 잇점이다. 
`int`형일 경우에는 `System.out.printlnInt()`라는 메소드를 사용하고,
`long`형일 경우는 `System.out.printlnLong()`라고 넘겨야한다면 얼마나 귀찮겠는가?

> 쉽게 설명해서 메소드 오버로딩은 =="같은 역할은 같은 메소드 이름을 가져야 한다."== 라는 모토로 사용하는 것이라고 기억하자


---

### 6. 메소드에서 값 넘겨주기
이번 절에서는 메소드의 수행과 종료에 대해서 알아보자.
자바에서 메소드가 종료되는 조건은 다음과 같다.
- 메소드의 모든 문장이 실행되었을 때
- return 문장에 도달했을 때
- 예외를 발생~throw~했을때

지금까지는 대부분의 메소드를 선언할 때 메소드 이름 앞에 `void`라고 썻었다. 이의 사전적의미는
- 빈 공간, 공동;공허감
- ...이 하나도 없는
- 무효의, 법적 효력이 없는

라고 나온다. 즉 자바에서 "이 메소드는 아무것도 돌려주지 않습니다" 라는 의미로 생각하자 
이렇게 `void`라고 선언했을 때에는 메소드의 마지막 부분까지 수행되면 메소드가 종료된다.

그러면  `void`외에 다른 것은 어떤 것을 넘겨줄 수 있을까?
> 기본적으로 자바에서는 모든타입을 한 개만 리턴타입으로 넘겨줄 수 있다. 
> 모든 기본자료형과 참조자료형 중 하나를 리턴할 수 있다.
그러면 `int,int`의 배열 ,`String`을 리턴하는 예제를 작성해보자

```java
	public int intReturn() {
		int returnInt = 0;
		return returnInt;
	}
	
	public int[] intArrayReturn() {
		int returnArray[] = new int[10];
		return returnArray;
	}

	public String stringReturn() {
		String returnString = "Return value";
		return returnString;
	}
```

> 리턴 타입을 명시해주지 않으면 컴파일 에러가 난다~

- 또한 return 이라는 예약어를 사용하면 그 아래에 조건문등이 아닌이상 코드가 있으면 안된다.
- `MemberDTO`를 사용하여 여러개를 넘겨 줄 경우도 생각해보자.
> 자세한 사항은 p.230~p.234 참조

---

### 7. static 메소드와 일반메소드의 차이
