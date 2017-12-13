## 2장. String

### 자바에서 가장 많이 사용하는 String 클래스

- 자바 클래스들 중에서 VVIP 일정도로 특별 취급을 받는 중요 클래스
- 클래스 중에서 더하기 연산을 제공하는 유일한 클래스

> String 클래스가 어떻게 선언되어 있는지 다시한번 살펴보자

```java
public final class String extends Object
	implements Serializable, Comparable<String>, CharSequence
```

- `public` 로 설정되어있다 (누구나 접근 가능~)
- `final` 로 선언되어 있다.
	> 클래스가 `final`로 선언되어있으면 "이 클래스는 확장할 수 없다~"는 뜻! (다시말해서 String클래스는 자식클래스가 없다)

- `extends Object` 를 보면 모든 클래스으 부모 클래스는 `Object`이므로 이 외에 따로 확장한 클래스는 없다는 말
- `implements` 라고 선언. 즉 해당 인터페이스에 선언된 메소드들을 이 클래스에서 `구현`한다는 뜻!!!
	> 따라서, `String`은 `Serializable`, `Comparable`, `CharSequence`라는 인터페이스를 구현한 클래스다

	- 여기서 `Serializable` 인터페이스는 구현해야 하는 메소드가 하나도 없는 아주 특이한 인터페이스다.
	- 이 `Serializable` 인터페이스를 구현한다고 선언해 놓으면, 해당 객체를 파일로 저장하거나 다른 서버에 전송 가능한 상태가 된다.
	- (`Serializable`에 대한 더욱 자세한 사항은 11장 참고!)

- `Comparable` 인터페이스는 `compareTo()`라는 메소드 하나만 선언되어 있다. 
	- 이 메소드는 매개변수로 넘어가는 객체와 현재 객체가 같은지를 비교하는 데 사용된다.
	- 간단하게 이름과 내용만으로 보기에는 그냥 `equals()`메소드와 무슨차이지?????? 물음표가 뜬다..
		> 하지만 이 메소드의 리턴 타입은 `int`다. 
		> 같으면 `0`이지만, 순서 상으로 앞에 있으면 `-1`, 뒤에 있으면 `1`을 리턴한다.
		> 다시 말해서 객체의 순서를 처리할 때 유용하게 사용될 수 있다.
	- 선언문의 `<>`안에 `String`이라고 적어 주었는데, 이는 `제네릭~Generic~`이라는 것을 의미한다.(4장참고)

- `CharSequence` 인터페이스는 해당 클래스가 문자열을 다루기 위한 클래스라는 것을 명시적으로 나타내는 데 사용된다.
    - 이 장의 가장 끝 부분에서 설명하는 `StringBuilder`와 `StringBuffer`클래스도 이 `CharSequence`인터페이스를 구현해 놓았다.


### String의 생성자에는 이런 애들이 있다.

먼저 생성자를 살펴보자. 대부분 문자열을 만들 때에는 다음과 같이 간단하게 만든다.

```java
String name = "Myungcheol, Shin";
```

-대부분의 경우 이렇게 선언하지만, String의 생성자는 매우 많다. 그중에서 많이 사용하는 생성자를 보자

```java
String(byte[] bytes)
String(byte[] bytes, String charsetName)
```
> 위 생성자들은 한글을 사용하는 우리나라에서는 자주 사용할 수 밖에 없다. 
> 왜냐면, 대부분의 언어에서는 문자열을 변환할 때 기본적으로 영어로 해석하려고 하기 때문이다.

---

### String 문자열을 byte로 변환하기

생성자의 매개변수로 받는 `byte`배열은 어떻게 생성하지? 라는 의문이 든다.

>  String 클래스에는 현재의 문자열 값을 `byte` 배열로 변화하는 다음과 같은 `getBytes()`라는 메소드를 사용하면 된다.


| 리턴타입 | 메소드이름 및 매개변수 | 설명 |
|--------|--------|---|
| byte[] | getBytes() | 기본 캐릭터 셋의 바이트 배열을 생성한다. |
| byte[] | getBytes(Charset charset) |지정한 캐릭터 셋 객체 타입으로 바이트 배열을 생성한다.|
| byte[] | getBytes(String charsetName) | 지정한 이름의 캐릭터 셋을 갖는 바이트 배열을 생성한다. |

- 보통 캐릭터 셋을 잘 알고 있거나, 같은 프로그램 내에서 문자열을 byte 배열로 만들 때에는 `getByte()`메소드를 사용하면 된다.
- 한글을 처리하기 위해서 많이 사용하는 캐릭터 셋은 `UTF-16`이다. (예전에는 UTF-8이나 EUC-KR을 많이 썻지만 요즘에는~)

> 더욱 자세한건 코드를 통해 알아보자

```java
public class StringSample
{
	public static void main(String[] args) 
	{
		StringSample sample=new StringSample();
		sample.constructors();
	}

	public void constructors(){
		try{
			//"한글"이라는 값을 갖는 String객체 str생성
			String str="한글";	
			
			// str을 byte배열로 만듬
			byte[] array1=str.getBytes();	

			for(byte data:array1){
				System.out.print(data + " ");
			}

			System.out.println();

			// byte배열(array1)을 매개변수로 갖는 String객체 생성후 출력
			String str1=new String(array1);
			System.out.println(str1);

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
```

> 출력결과

```java
-57 -47 -79 -37 
한글
```

- 원래 만들어 놓았던 "한글"이라는 값이 그대로 출력되네?

> `getBytes()` 메소드는 플랫폼의 기본 캐릭터 셋으로 변환을 하고, String(byte[])생성자도 플랫폼의 기본 캐릭터 셋으로 변환을 하기 때문에 전혀 문제가 발생하지 않았다.

만약 이 소스가 실무라고 생각하면 byte 배열의 값을 출력하는 부분이 자주 사용될 것같으니 다음과 같이 별도의 메소드로 만들어보자

```java
public void printByteArray(byte[] array){
	for(byte data:array){
    	System.out.print(data+" ");
    }
    Systesm.out.println();    
}
```

> 이렇게 메소드 내에 자주 사용되는 부분을 별도의 메소드로 빼 놓는 버릇을 들이는 것이 향후에 재사용성을 위해서 좋다.


이번에는 캐릭터 셋을 변환해서 출력해보자

```java
	String str="한글";
   
	byte[] array1=str.getBytes();
	printByteArray(array1);
	String str1=new String(array1);
	System.out.println(str1);

	byte[] array2=str.getBytes();
	printByteArray(array2);
	String str2=new String(array2, "UTF-8");
	System.out.println(str2);
```

> 출력결과

```java
-57 -47 -79 -37 
한글
-57 -47 -79 -37 
???
```

> 잘못된 캐릭터 셋으로 변환을 하면 이와같이 알아볼수 없는 문자료 표기된다.

그렇다면, byte 배열로 변환할 때 캐릭터 셋을 변경해 버릴수는 없을까?

> 그럴때는 아래와같이 `getBytes()` 매개변수로 캐릭터 셋을 지정해주면 된다.

```java
byte[] array3=str.getBytes("UTF-16");
printByteArray(array3);
String str3=new String(array3, "UTF-16");
System.out.println(str3);
```

> 참고로 EUC-KR 은 한글 두 글자를 표현하기 위해서 4바이트를 사용하지만 UTF-16은 6바이트를 사용한다.
> 글자수와 상관없이 2바이트의 차이가 발생함을 볼수 있다.

> 또한 위 코드들을 try 블록으로 감싼 이유는, getBytes() 메소드 중에서 String 타입의 캐릭터 셋을 받는 메소드는
> UnsupporteEncodingException을 발생시킬 수 있기 때문이다.
> 반드시 이처럼 try~catch 문으로 감싸주거나 constuctors() 메소드 선언시 throws 구문을 추가해 줘야 한다.


---

### 객체의 널 체크는 반드시 필요하답니다.

- 어떤 참조 자료형도 널이 될 수 있다.
- 객체가 널이라는 말은 객체가 아무런 초기화가 되어 있지 않으며, 클래스에 선언되어 있는 어떤 메소드도 사용할 수 없다는말
- 널 체크를 하지 않으면 객체에 사용할 수 있는 메소드들은 모두 예외를 발생시킨다.

> 돈이 없으면 주문도 할 수 없는 선불 식당을 떠올려 보자

```java
public boolean nullCheck(String text){
	int textLength=text.length();
   System.out.println(textLength);
   if(text=null) return true;
   else return false;
}
```
> 출력결과

```java
Exception in thread "main" java.lang.NullPointerException
	at StringSample3.nullCheck(StringSample3.java:12)
	at StringSample3.main(StringSample3.java:7)
```
- null인 객체의 메소드에 접근하면 `NullPointerException`이 발생한다.
- 객체가 널인지 아닌지 체크하는 것은 이처럼 `!=`이나 `==`를 사용하면 된다.


---


### String의 내용을 비교하고 검색하는 메소드들도 있어요

- String클래스는 문자열을 나타낸다.
- 문자열 내에 특정 위치를 찾거나, 값을 비교하는 등의 작업은 아주 빈번히 발생된다.

> String클래스객체의 내용을 비교하고 검색하는 메소드들을 알아보자

- 문자열의 길이를 확인하는 메소드
- 문자열이 비어 있는지 확인하는 메소드
- 문자열이 같은지 바교하는 메소드
- 특정 조건에 맞는 문자열이 있는지를 확인하는 메소드

> 위 글만 보고 " 이 메소드 이름이 뭐지?" 하고 생각해보자 메소드 이름을 직관적으로 지어야 한다.


##### 1) 문자열의 길이를 확인하는 메소드
```java
public void comparecheck(){
	String text="You must know String class.";
	System.out.println("text.length()="+text.length());
}
```

> 출력결과

```java
text.length()=27
```

##### 2) 문자열이 비어 있는지 확인하는 메소드

```java
System.out.println("text.isEmpty()="+text.isEmpty());
```

> 출력결과

```java
text.isEmpty()=false
```
- 만약 text가 공백 하나로 되어 있는 문자열이라도, 이 메소드는 false를 리턴한다.


##### 3) 문자열이 같은지 비교하는 메소드

- String클래스에서 제공하는 문자열이 같은지 비교하는 메소드들은 매우 많다.

| 리턴타입 | 메소드 이름 및 매개변수 |
|--------|--------|
| boolean | equals(Object anObject)|
| boolean | equalsIgnoreCase(String anotherStr)|
| int | compareTo(String anotherStr)|
| int | compareToIgnoreCase(String str)|
| boolean | contentEquals(CharSequence cs)|
| boolean | contentEquals(StringBuffer sb)|

> `IgnoreCase` 가 붙은 메소드들은 대소문자 구분을 할지 안할지 여부만 다르다.

###### ㄱ) equals()


```java
public void equalCheck(){
	String text="Check value";
	String text2="Check value";
    if(text==text2){
    	System.out.println("text==text2 result is same");
    }
    else{
    	System.out.println("text==text2 result is different");
    }
    if(text.equals("Check value")){
    	System.out.println("text.equals(text2) result is same");
    }
}
```

> 출력결과

```java
text==text2 result is same
text.equals(text2) result is same
```

**객체는 `equals()`메소드로 비교 해야 한다고 배웠는데 왜 `if(text==text2)`구문도 정상적으로 출력이 되지???**

> 이는 자바에 `Constant Pool`이라는 것이 존재하기 때문이다.
> 자바에서는 객체들을 재사용하기  위해서 `Constant Pool`이라는 것이 만들어져 있고, 
> String의 경우 동일한 값을 갖는 객체가 있으면, 이미 만든 객체를 재사용한다.
> 따라서 text와 text2객체는 실제로는 같은 객체다

> 이 첫번째 연산결과가 우리가 원하는 대로 나오도록 하려면 다음과 같이 변경하면 된다.

```java
//String text2="Check value";
String text2=new String("Check value");
```
> 이렇게 String객체를 생성하면 값이 같은 String 객체를 생성한다고 하더라도
> `Constant Pool`의 값을 재활용하지 않고 별도의 객체를 생성한다.

###### ㄴ) equalsIgnoreCase()

```java
	String text3="check value";
	if(text.equalsIgnoreCase(text3)){
		System.out.println("text.equalsIgnoreCase(text3) result is same");
	}
```
> 출력결과

```java
text.equalsIgnoreCase(text3) result is same
```

###### ㄷ) compareTo()

- `compareTo()` 메소드는 `Comparable`인터페이스에 선언되어 있다.
- `compareTo()` 메소드는 보통 정렬을 할 때 사용한다. 
- 따라서 true,false의 결과가 아니라, 비교하려는 매개 변수로 넘겨준 String 객체가 알파벳 순으로 앞에 있으면 양수를, 뒤에 있으면 음수를 리턴한다.

```java
public void compareToCheck(){
	String text="a";
	String text2="b";
	String text3="c";
    
	System.out.println(text2.compareTo(text));
	System.out.println(text2.compareTo(text3));
	System.out.println(text.compareTo(text3));
}
```


> 출력결과

```java
1
-1
-2
```

###### ㄹ) contentEquals()
- 매개 변수로 넘어오는 `CharSequence`와 `StringBuffer` 객체가 String 객체와 같은지를 비교하는 데 사용된다.
- 뒤에서 자세히 알아보자

###### ㅁ) 특정 조건에 맞는 문자열이 있는지를 확인하는 메소드

여러가지가 있지만 가장 많이 사용하는 것이 `startWith()`메소드다.

`startWith()`메소드는 이름 그대로 매개 변수로 넘겨준 값으로 시작하는지를 확인한다.

예를 들어 "서울시 구로구 신림동", "경기도 성남시 분당구"와 같이 주소를 나타내는 문자열들이 있을 때
"서울시"의 주소를 갖는 모든 문자열을 쉽게 찾을 수 있다.

마찬가지로 `endsWith()`메소드는 매개변수로 넘어온 값으로 해당 문자열이 끝나는지를 확인하는 메소드다.

> 위 두개의 메소드를 사용하여 점검대상의 문자열에 확인하고자 하는 문자열로 시작하는 개수와 끝나는 개수를 세어보는 예제를 살펴보자

```java
public void addressCheck(){
	String addresses[]=new String[]{
		"서울시 구로구 신도림동",
		"경기도 성남시 분당구 정자동 개발 공장",
		"서울시 구로구 개봉동",
	};

	int startCount=0,endCount=0;

	String startText="서울시";
	String endText="동";

	for(String address:addresses){
		if(address.startsWith(startText)){
			startCount++;
		}
		if(address.endsWith(endText)){
			endCount++;
		}
	}	

	System.out.println("Starts with "+startText+" count is "+startCount);
	System.out.println("Ends with "+endText+" count is "+endCount);
}
```

> 출력결과

```java
Starts with 서울시 count is 2
Ends with 동 count is 2
```

> 그렇다면 중간에 있는 값은 어떻게 확인 가능할까?

그래서 존재하는 것이 `contains()`메소드다.
- 이 메소드는 매개 변수로 넘어온 값이 문자열에 존재하는지를 확인한다.
- 사용법은 위 두개 메소드와 동일하다

`matches()`메소드는 매개변수로 넘어오는 값이 `정규표현식`으로 되어 있어야만 한다.

> 정규표현식은 `java.util.regex`패키지의 `Pattern`클래스 API를 참고하자.
> 인사이트의 <손에 잡히는 정규 표현식> 책 참고


`regionMatches()`메소드는 문자열 중에서 특정영역이 매개 변수로 넘어온 문자열과 동일한지를 확인하는데 사용한다.
매개변수가 많으니 5개인 메소드를 기준으로 각각이 어떤 값인지 알아보자
```java
regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)
```


```java
public void matchCheck(){
	String text="This is a text";
	String compare1="is";
	String compare2="this";

	// 매개변수가 4개인 메소드
	System.out.println(text.regionMatches(2,compare1,0,1));

	// 매개변수가 4개인 메소드
	System.out.println(text.regionMatches(5,compare1,0,2));

	// 매개변수가 5개인 메소드
	System.out.println(text.regionMatches(true,2,compare2,0,4));
}
```


--- 

### String 내에서 위치를 찾아내는 방법은 여러가지에요

	"Java technology is both a programming language and a platform."

이 문장에서 "both"라는 단어가 시작하는 위치를 알고 싶을 때 어떻게 해야 할까?

- 자바의 String클래스에서는 `indexof`라는 단어가 포함되어 있는 메소드를 제공한다.
- 이 메소드를 사용하면 해당 객체의 특정 문자열이나 char가 있는 위치를 알 수 있다.
- 만약 찾고자 하는 문자열이 없으면 이 메소드는 `-1`을 리턴한다.

> 그럼 위에서 설명한 위치를 찾는 메소드의 종류를 살펴보자

- `indexOf(int ch)`
- `indexOf(ing ch, int fromIndex)`
- `indexOf(String str)`
- `indexOf(String str, int fromIndex)`
- `lastIndexOf(int ch)`
- `lastIndexOf(int ch, int fromIndex)`
- `lastIndexOf(String str)`
- `lastIndexOf(String str, int fromIndex)`

> `indexOf()` 메소드는 String클래스의 가장 많이 사용되는 메소드 중 하나다.
indexOf()는 앞에서부터, lastIndexOf()는 뒤에서부터 문자열을 찾는다.

```java
public void indexOfCheck(){
	String text="Java technology is both a programming language and ad platform.";

	System.out.println(text.indexOf('a'));
	System.out.println(text.indexOf("a "));
	System.out.println(text.indexOf('a',20));
	System.out.println(text.indexOf("a ",20));
	System.out.println(text.indexOf('z'));
}
```

> 출력결과

```java
1
3
24
24
-1
```


---

### String 값의 일부를 추출하기 위한 메소드들은 얘네들이다.

문자열에서 특정 값을 추출할 때 사용하는 메소드를 알아보자 그 종류는 크게 다음과 같이 나눌 수 있다.

- char 단위의 값을 추출하는 메소드
- char 배열의 값을 String으로 변환하는 메소드
- String의 값을 char배열로 변환하는 메소드
- 문자열의 일부 값을 잘라내는 메소드
- 문자열을 여러 개의 String배열로 나누는 메소드

#### char 단위의 값을 추출하는 메소드
- 생략

#### char 배열의 값을 String으로 변환하는 메소드
- static 메소드이기 때문에 현재 사용하는 문자열을 참조하여 생성하는 것이 아닌, static 하게 호출하여 사용해야 한다

```java
char values[]=new char[]{'J', 'a', 'v', 'a'};
String javaText=String.copyValueOf(values);
```


#### String의 값을 char배열로 변환하는 메소드
- 어떤 String객체를 만들더라고, 그 객체는 내부에 char배열을 포함한다.

#### 문자열의 일부 값을 잘라내는 메소드
- 문자열을 다룰 때 `indexOf()`메소드와 더불어 가장 많이 사용하는 메소드

```java
public void substringCheck1(){
	String text="Java technology";
}
```
> 위 문자열 중 "technology"라는 단어만 추출하려고 한다면?

```java
public void substringCheck1(){
	String text="Java technology";
	String technology=text.substring(5);
	System.out.println(technology);
}
```
> 그런데, 만약 "tech"라는 단어만 잘라내고 싶을때는 어떻게 해야 할까?

```java
String tech=text.substring(5,9);
System.out.println(tech);
```

> 여기서 주의 할점은 두번째 매개변수는 **데이터 길이가 아닌 끝나는 위치다.**

#### 문자열을 여러 개의 String배열로 나누는 메소드
- String클래스에 선언된 split()메소드를 사용하는 것과 java.util 패키지에 선언되어 있는 StringTokenizer라는 클래스를 사용하는 것이다.

- 정규표현식을 사용하여 문자열을 나누려고 한다면 String클래스의 split()메소드를 사용하면 된다.
- 그냥 특정 String으로 문자열을 나누려고 한다면 StringTokenizer클래스를 사용하는 것이 편하다.
- 물론 특정 알파벳이나 기호하나로 문자열을 나누려고 한다면 뭘쓰든 큰 상관없다

```java
public void splitCheck(){
	String text="Java technology is both a programming language and ad platform.";

	String[] splitArray=text.split(" ");
	for(String temp:splitArray){
		System.out.println(temp);
	}
}
```

---

### String 값을 바꾸는 메소드들도 있어요

문자열의 값을 바꾸고, 변환하는 메소드도 다음과 같이 구분할 수 있다.
- 문자열을 합치는 메소드와 공백을 없애는 메소드
- 내용을 교체~replace~하는 메소드
- 특정 형식에 맞춰 값을 치환하는 메소드
- 대소문자를 바꾸는 메소드
- 기본 자료형을 문자열로 변환하는 메소드

#### 문자열을 합치는 메소드와 공백을 없애는 메소드
- `trim()`메소든느 공백을 제거할 때 매우 유용하게 사용된다.

```java
public void trimCheck() {
	String strings[] = new String[] { " a", " b ", "    c", "d    ",
			"e   f", "   " };
	for (String string : strings) {
		System.out.println("[" + string + "] ");
		System.out.println("[" + string.trim() + "] ");
	}
}
```

> `trim()`메소드의 용도는 매우 많지만, 작업하려는 문자열이 공백만으로 이루어진 값인지, 아니면 공백을 제외한 값이 있는지 확인하기에 매우 편리하다.
> 
> 다음의 if 문을 통과하여 "OK"를 출력하면, 해당 문자열은 공백을 제외한 char값이 하나라도 존재한다는 의미다.

```java
String text=" a ";
if(text!=null && text.trim().length()>0 ){
	System.out.println("OK");
}
```

> 만약 null 체크를 하지 않으면, 값이 null인 객체의 메소드를 호출하면 `NullPointerException`이 발생한다.
> 이와 같이 String을 조작하기 전에 null체크하는 습관을 갖자.


#### 내용을 교체~replace~하는 메소드
- `replace`로 시작하는 메소드는 문자열에 있는 내용 중 일부를 변경하는 작업을 수행한다.
- 참고로, 이 메소드를 수행한다고 해서, 기존 문자열의 값은 바뀌지 않는다.

```java
public void replaceCheck() {
	String text = "The String class represents character strings.";
	System.out.println(text.replace('s', 'z'));
	System.out.println(text);
	System.out.println(text.replace("tring", "trike"));
	System.out.println(text.replaceAll(" ", "|"));
	System.out.println(text.replaceFirst(" ", "|"));
}
```
> 참고로 `replace`관련 메소드는 대소문자를 구분한다.



#### 특정 형식에 맞춰 값을 치환하는 메소드
- format()메소드는 정해진 기준에 맞춘 문자열이 있으면, 그 기준에 있는 내용을 변환한다.
- `%s`는 String
- `%d`는 정수형
- `%f`는 소수점이 있는 숫자
- `%%`는 `%`fmf dmlalgksek.

```java
public void formatCheck() {
	String text = "제 이름은 %s입니다. 지금까지 %d 권의 책을 썼고, "
			+ "하루에 %f %%의 시간을 책을 쓰는데 할애하고 있습니다.";
	String realText = String.format(text, "이상민", 3, 10.5);
	// String realText=String.format(text, "이상민",3);
	System.out.println(realText);
}
```

> 만약 출력만을 위해서 이 메소드를 사용하면 굳이 이렇게 사용할 필요 없이 `System.out.format()`을 쓰자

#### 대소문자를 바꾸는 메소드
- `toLower`로 시작하는 메소드는 모든 대문자를 소문자로
- `toUpper`로 시작하는 메소드는 모든 소문자를 대문자로


#### 기본 자료형을 문자열로 변환하는 메소드
- 기본자료형을 String 타입으로 변환한다.

> `valueOf()`메소드를 사용하여 기본 자료형 값들을 문자열로 변경해도 되지만 다음고 같이 써도 된다.

```java
byte b = 1;
String byte1=String.valueOf(b);
String byte2=b+"";
```


### 절대로 사용하면 안되는 메소드가 하나 있어요

	intern() 메소드를 사용하지 말자

### immutable한 String의 단점을 보완하는 클래스에는 StringBuffer와 StringBuilder가 있다.
- String은 immutable한 객체다. (사전적인 의미로 "불변의")
- 다시 말해서 한번 만들어지면 더 이상 그 값을 바꿀 수 없다.


##### "무슨말이지? 더하기 하면 잘만 더해지는데....."

> 위 생각은 틀렸다. String객체는 변하지 않는다.

만약 String문자열을 더하면 새로운 String객체가 생성되고, 기존객체는 버려진다.
그러므로, 계속 하나의 String을 만들어 계속 더하는 작업을 한다면, 계속 쓰레기를 만들게 된다.
즉 다음과 같은 경우다

```java
String text="Hello";
text=text+" wolrd";
```

이 경우 "Hello"라는 단어를 갖고 있는 객체는 더 이상 사용할 수 없다.
즉, 쓰레기가 되며, 나중에 GC~Garbagecollection~(가비지 컬렉션)의 대상이 된다.

> 이러한 String클래스의 단점을 보완하기 위해서 나온 클래스가 `StringBuffer`와 `Stringbuilder`다.

- 두 클래스에서 제공하는 메소드는 동일하다.
- 하지만, `StringBuffer`는 **Thread safe하다**고 하며, 
- `StringBuilder`는 **Thread safe 하지 않다**고 한다.

> `StringBuffer` 가 `StringBuilder` 보다 더 안전하다고만 기억해두자. (속도는 `Stringbuilder`가 더 빠르다)

- 이 두 클래스는 문자열을 더하더라도 새로운 객체를 생성하지 않는다.
- 이 두개의 클래스에서 가장 많이 사용하는 메소드는 `append()`라는 메소드다
- `append()`메소드는 매개 변수로 모든 기본자료형과 참조 자료형을 모두 포함한다. 따라서 어떤 값이라도 이 메소드의 매개변수로 들어갈 수 있다.

> 보통 다음과 같이 사용한다.

```java
StringBuilder sb=new Stringbuilder();
sb.append("Hello");
sb.append(" world");
```
> 그냥 보기엔 별 차이가 없어보인다.
> 하지만, append()메소드에 넘어가는 매개 변수가 이처럼 정해져 있는 문자열이라면 사용하나 마나지만,
> 매개변수가 변수로 받은(항상변하는)값이라면 이야기는 달라진다. 또한 다음과같이 사용할 수 도 있다

```java
StringBuilder sb=new StringBuilder();
sb.append("Hello").append(" world");
```
> 추가로, JDK 5 이상에서는 String의 더하기 연산을 할 경우, 컴파일할 때 자동으로 해당 연산을 Stringbuilder로 변환해 준다.

세 메소드의 공통점
- 모두 문자열을 다룬다.
- CharSequence 인터페이스를 구현했다는 점
	> 따라서 이 세가지중 하나의 클래스를 사용하여 매개 변수로 받는 작업을 할 때 String이나 StringBuilder 타입으로 받는 것보다
	> CharSequence 타입으로 받는 것이 좋다.
- 하나의 메소드 내에서 문자열을 생성하여 더할 경우에는 StringBuilder 를 사용해도 전혀 상관없다.
- 어떤 문자열을 생성하여 더하기 위한 문자열을 처리하기 위한 인스턴스 변수가 선언되었고, 여러 쓰레드에서 이 변수를 동시에
접근하는 일이 있을 경우에는 반드시 StringBuffer를 사용해야만 한다.


---
### 직접해봅시다

```java
public class UseStringMethods
{

	public static void main(String[] args) 
	{
		UseStringMethods usm=new UseStringMethods();
		String text="The String class represents character strings.";
		String fstr="string";
		char c='s';
		String fstr2="ss";

		//usm.printWords(text);
		//usm.findString(text, fstr);
		//usm.findAnyCaseString(text, fstr);
		//usm.countChar(text, c);
		
		usm.printContainWords(text, fstr2);
	}

	public void printWords(String str){
		
		String str2[]=str.split(" ");

		for(String tmp:str2){
			System.out.println(tmp);
		}
	}

	public void findString(String str, String findStr){
		System.out.println("string is appeared at "+str.indexOf(findStr));
	}

	public void findAnyCaseString(String str, String findStr){
		String str2 = str.toLowerCase();
		System.out.println("string is appeared at "+str2.indexOf(findStr));
	}
	
	public void countChar(String str,char c){
		int charCount=0;
		char[] strArray=str.toCharArray();
		
		for(char tmp:strArray){
			if(tmp=='s'){
				charCount++;
			}
		}

		System.out.println("char 's' count is "+charCount);
	}

	public void printContainWords(String str, String findStr){
		String[] strArray=str.split(" ");

		for(String tmp:strArray ){
			if(tmp.contains(findStr)){
				System.out.print(tmp+" contains ss");
			}
		}
	}
}

```



---

### 정리해 봅시다



1) String 클래스는 final 클래스인가요? 만약 그렇다면, 그 이유는 무엇인가요?

- 그렇다. 자식클래스가 없다. 더이상 상속을 줄 수 가 없다.

> 자바의 String클래스는 final로 선언되어 있으며, 더 이상 확장해서는 안된다. 


2) String 클래스가 구현한 인터페이스에는 어떤 것들이 있나요? 

- Serializable, Comparable, CharSequence를 구현했다.

3) String 클래스의 생성자 중에서 가장 의미없는 (사용할 필요가 없는) 생성자는 무엇인가요?

- ~~new String(String str)~~

> new String() 생성자는 가장 의미가 없는 String 클래스의 생성자이다. 왜냐하면, 생성된 객체는 해당 변수에 새로운 값이 할당되자마자 GC의 대상이 되어버리기 때문이다.

4) String 문자열을 byte 배열로 만드는 메소드의 이름은 무엇인가요?

- getBytes

5) String 문자열의 메소드를 호출하기 전에 반드시 점검해야 하는 사항은 무엇인가요?

- null 체크

6) String 문자열의 길이를 알아내는 메소드는 무엇인가요?

- length()

7) String 클래스의 equals() 메소드와 compareTo() 메소드의 공통점과 차이점은 무엇인가요?

- 값이 같은지 확인하는 메소드지만 compareTo() 메소드는 리턴값이 int이며 보통 정렬에 사용한다.

> equals()메소드와 compareTo()메소드의 공통점은 두 개의 문자열을 비교한다는 것이고, 다른 점은 리턴 타입이 다르다는 것이다.
equals() 메소드는 boolean 타입의 리턴을, compareTo() 메소드는 int 타입의 리턴값을 제공한다.


8) 문자열이 "서울시"로 시작하는지를 확인하려면 String의 어떤 메소드를 사용해야 하나요?

- startWith()

9) 문자열에 "한국"이라는 단어의 위치를 찾아내려고 할 때에는 String의 어떤 메소드를 사용해야 하나요?

- ~~indexOf()~~

> contains()나 matches()메소드를 사용하여 원하는 문자열이 존재하는지 확인할 수 있다. 고전적인 방법으로는 indexOf() 메소드를 사용할 수도 있다.

10) 9번 문제의 답에서 "한국"이 문자열에 없을 때 결과값은 무엇인가요? 

- ~~-1~~

> contains()나 matches() 메소드의 리턴타입은 boolean 이다.

11) 문자열의 1번째부터 10번째 위치까지의 내용을 String으로 추출하려고 합니다. 어떤 메소드를 사용해야 하나요?

- ~~split()~~

> substring()이나 subSequence()메소드를 사용하면 원하는 위치에 있는 문자열을 자를 수 있다. 

12) 문자열의 모든 공백을 * 표시로 변환하려고 합니다. 어떤 메소드를 사용하는 것이 좋을까요? 

- replace

> replace()나 replaceAll() 메소드를 사용하면 문자열의 특정 부분을 바꿀 수 있다. 여기서 중요한 것은 원본 문자열은 변경되지 않는다는 것이다. 변경된 값을 사용하려면 해당 메소드의 리턴값을 사용해야만 한다.

13) String의 단점을 보완하기 위한 두개의 클래스는 무엇인가요?

- StringBuilder, StringBuffer

14) 13번 문제의 답에서 문자열을 더하기 위한 메소드의 이름은 무엇인가요?

- ~~StringBuilder~~

> StringBuilder와 StringBuffer클래스의 append() 메소드를 사용하여 문자열을 더할 수 있다. 








