## 3장. 가장 많이 쓴느 패키지는 자바랭

### java.lang 패키지는 특별하죠

- 자바의 패키지 중에서 유일하게 java.lang 패키지에 있는 클래스들은 import를 안해도 사용할 수 있다.

> java.lang 패키지에서 제공하는 인터페이스, 클래스, 예외 클래스 등은 다음과 같이 분류할 수 있다.

 - 언어 관련 기본
 - 문자열 관련
 - 기본 자료형 및 숫자 관련
 - 쓰레드 관련
 - 예외 관련
 - 런타임 관련

> p.101 표 참조


### 숫자를 처리하는 클래스들
- 자바에서 간단한 계산을 할 때에는 대부분 기본 자료형을 사용한다.
- 기본자료형은 자바의 `힙heap` 이라는 영역에 저장되지 않고, `스택stack`이라는 영역에 저장되어 관리된다.
따라서 계산할 때 보다 빠른 처리가 가능하다

> 이에 관한 더욱 자세한 내용은 [구글 "java stack primitive types" 검색 바로가기](https://www.google.co.kr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=java%20stack%20primitive%20types)

그런데, 이러한 기본 자료형의 숫자를 객체로 처리해야 할 필요가 있을 수도 있다.
따라서, 자바에는 다음과 같이 기본 자료형으로 선언되어 있는 타입의 클래스들이 선언되어 있다.
- Byte
- Short
- Integer
- Long
- Float
- Double
- Character
- Boolean

> 보다시피 Chracter, Integer 클래스를 제외하고 나머지는 각 기본자료형의 이름에서 첫문자만 대문자로 바뀌었다.

- Character와 Boolean을 제외한 숫자를 처리하는 클래스들은 감싼(Wrapper)클래스라고 불리며, 모두 `Number`라는 `abstract 클래스`를 `확장(extends)`한다.
- 그리고, 겉으로 보기에는 참조 자료형이지만, 기본 자료형 처럼 사용할 수 있다.
- 그 이유는 자바 컴파일러에서 자동으로 형 변환을 해주기 때문이다.

> Charcter 클래스를 제외하고 전부 공통적인 메소드를 제공한다.
> 바로 parse타입이름() 메소드와 valueOf()라는 메소드다. 이 두가지 메소드는 모두 static메소드다.
> ( 즉 객체생성없이 바로 사용이 가능하다는 말 )
> 둘다 String과 같은 문자열을 숫자 타입으로 변환한다는 공통점이 있지만,
> person타입이름() 메소드는 기본 자료형을 리턴하고, valueOf()메소드는 "참조 자료형"을 리턴한다.


```java
public class JavaLang
{
	public static void main(String[] args) 
	{
		JavaLang sample = new JavaLang();
		sample.numberTypeCheck();
	}

	public void numberTypeCheck() {
		String value1 = "3";
		String value2 = "5";

		// 3과 5라는 String값을 parseByte() 메소드를 사용하여 byte로 변환
		byte byte1 = Byte.parseByte(value1);
		byte byte2 = Byte.parseByte(value2);

		System.out.println(byte1 + byte2);

		Integer refInt1 = Integer.valueOf(value1);
		Integer refInt2 = Integer.valueOf(value2);
		System.out.println(refInt1 + refInt2 + "7");
	}
	public void numberTypeCheck2(){
		Integer refInt1;
		refInt1=100;
		System.out.println(refInt1.doubleValue());
	}
}
```

> 왜 혼동되게 이러한 숫자를 처리하는 참조 자료형을 만들었을까?

- 매개 변수를 참조자료형으로만 받는 메소드를 처리하기 위해서
- 제네리과 같이 기본자료형을 사용하지 않는 기능을 사용하기 위해서
- MIN_VALUE(최소값)나 MAX_VALUE(최대값)와 같이 클래스에 선언된 상수 값을 사용하기 위해서
- 문자열을 숫자로, 숫자를 문자열로 쉽게 변환한고, 2,8,10,16 진수 변환을 쉽게 처리하기 위해서


상수값과 메소드

기본자료형을 참조 자료형으로 만든 클래스들은 boolean클래스를 제외하고 모두 `MIN_VALUE`와 `MAX_VALUE`라는 상수를 갖고 있다.

해당 타입이 나타낼 수 있는 갑의 범위를 확인하려면 static으로 선언되어 있는 이 상수들을 다음과 같이 사용하면 된다.


```java
public void numberMinMaxCheck() {
	long startTime = System.currentTimeMillis();
	long startNanoTime = System.nanoTime();
	System.out.println("Byte min=" + Byte.MIN_VALUE + " max=" + Byte.MAX_VALUE);
	System.out.println("Short min=" + Short.MIN_VALUE + " max=" + Short.MAX_VALUE);
	System.out.println("Integer min=" + Integer.MIN_VALUE + " max=" + Integer.MAX_VALUE);
	System.out.println("Long min=" + Long.MIN_VALUE + " max=" + Long.MAX_VALUE);
	System.out.println("Float min=" + Float.MIN_VALUE + " max=" + Float.MAX_VALUE);
	System.out.println("Double min=" + Double.MIN_VALUE + " max=" + Double.MAX_VALUE);
	System.out.println("Character min=" + (int) Character.MIN_VALUE + " max=" + (int) Character.MAX_VALUE);
}
```

> 출력결과

```java
Byte min=-128 max=127
Short min=-32768 max=32767
Integer min=-2147483648 max=2147483647
Long min=-9223372036854775808 max=9223372036854775807
Float min=1.4E-45 max=3.4028235E38
Double min=4.9E-324 max=1.7976931348623157E308
Character min=0 max=65535
```

> 값을 알아보기 힘든 Long타입을 2진수나 16진수로 보고 싶으면 Integer클래스에서 제공하는
> `toBinaryString()`메소드 등을 사용하자



```java
System.out.println("Integer BINARY min=" + Integer.toBinaryString(Integer.MIN_VALUE));
		System.out.println("Integer BINARY max=" + Integer.toBinaryString(Integer.MAX_VALUE));

		System.out.println("Integer HEX min=" + Integer.toHexString(Integer.MIN_VALUE));
		System.out.println("Integer HEX max=" + Integer.toHexString(Integer.MAX_VALUE));
```

> 만약 돈 계산과 같이 중요한 연산을 수행할 때, 정수형은 BigIntefer, 소수형은 BigDecimal을 사용해야 정확한 계산이 가능하다.
> 이 두 클래스들은 모두 java.lang.Number 클래스의 상속을 받았으며, java.math 패키지에 선언되어 있다.


### 각종 정보를 확인하기 위한 System 클래스

- 이 클래스의 가장 큰 특징은 생성자가 없다는 것이다.

System클래스에 3개의 `static` 변수가 선언되어 있다.

- `static PrintStream` - `err` : 에러 및 오류를 출력할 때 사용
- `static InputStream` - `in` : 입력값을 처리할 때 사용
- `static PrintStream` - `out` : 출력값은 처리할 때 사용

> 익숙한 구문을 통해 분석해보자

```java
System.out.println();
```

- System : 클래스이름
- out : static으로 선언된 변수이름


> `out`은 `PrintStream` 타입이다. 그러므로 `println()`이라는 메소드는 `PrintStream 클래스`에 선언되어 있으며, static 메소드다

처음에 그냥 생각하기로는 System 클래스는 출력만을 위한 클래스라고 생각할 수 도 있다.
그러나 실제 System클래스에 선언되어 있는 메소드들을 살펴보면 출력과 관련된 메소드들은 없다.
System 클래스는 이름 그대로 시스템에 대한 정보를 확인하는 클래스이며, 이 클래스에서 제공되는 메소드를 분류해보면 다음과 같이 다양한 역할을 한다는 것을 알 수 있다.

- 시스템 속성(Property)값 관리
- 시스템 환경(Environment)값 조회
- GC 수행
- JVM 종료
- 현재시간조회
- 기타 관리용 메소드들

> 이 중 절대로 수행해서는 안 되는 메소드의 분류가 2개있다. 
> 바로 GC 수행과 JVM종료 관련 메소드다.

#### 시스템 속성(Property)값 관리

- `Properties`는 java.util 패키지에 속하며, `Hashtable`의 상속을 받은 클래스다.
- 우리의 필요 여부와 상관없지 자바 프로그램을 실행하면 지금 `Properties` 객체가 생성되며, 그 값은 언제, 어디서든지 
같은 JVM내에서는 꺼내서 사용할 수 있다.

```java
public void systemPropertiesCheck() {
	System.out.println("java.version=" + System.getProperty("java.version"));
}
```
> 출력결과

```java
java.version=1.7.0_80
```

#### 시스템 환경(Environment)값 조회
- 이전에 살펴본 Properties라는 것은 추가할 수도 있고, 변경도 할 수 있다.
- 하지만 환경 값이라는 enc라는 것은 변경하지 못하고 읽기만 할 수 있다.
- 이 값들은 대부분 OS나 장비와 관련된 것들이다.

```java
System.out.println("JAVA_HOME=" + System.getenv("JAVA_HOME"));
```

> 출력결과

```java
JAVA_HOME=C:\Program Files\Java\jdk1.7.0_80
```


#### GC 수행

- `static void gm()`
- `static void runFinalization()`
- 앞서 위 두개의 메소드는 사용하면 안된다고 했었다.

> 그 이유는 자바는 메모리 처리를 개발자가 별도로 하지 않는다.
> 따라서 `System.gc()`라는 메소드를 호출하면 가비지 컬렉션을 명시적으로 처리하도록 할 수 있다.
> 또한 Obejct 클래스에 선언되어 있는 finalize()메소드를 명시적으로 수행하도록 하는 runFinalization()메소드가 있다.

이 두개의 메소드들을 우리가 호출하지 않아도 알아서 `JVM`이 더 이상 필요 없는 객체를 처리하는 GC작업과 finalization 작업을 실행한다.
만약 명시적으로 이 두 메소드를 호출하는 코드를 집어 넣으면, 시스템은 하려던 일들을 멈추고 이 작업을 실행한다.


#### JVM 종료

- `static void exit(int status)`이 메소드 역시 절~~~~대 호출되면 안 되는 것중 하나다.
- 안드로이드 앱이나, 웹 애플리케이션에서 이 메소드를 사용하면 해당 애플리케이션의 JVM 이 죽어버린다.


#### 현재 시간 조회

- `static long currentTimeMillis()` - 현재 시간을 밀리초 단위로 리턴
- `static long nanoTime()` - 현재 시간을 나노초 단위로 리턴

---

### System.out을 살펴보자

- 객체를 출력할 때에는 toString()을 사용하는 것보다는 valueOf()메소드를 사용하는 것이 훨씬 안전하다
	> print() 메소드와 println()메소드에서는 단순히 toString()메소드 결과를 출력하지 않기 때문이다
	> String의 valueOf()라는 static 메소드를 호출하여 결과를 받은 후 출력한다.

### 수학적인 계산을 위해서 꼭 필요한 Math 클래스

- 자바에는 Math라는 수학을 계산하는 클래스를 제공한다.
- Math만이 아니라 StricMath 라는 클래스도 자바랭 패키지에 있다. (어떤 OS나 어떤 시스템 아키텍처에서 수행되든간에 동일한 값을 리턴해야 한다는 기준에 의해 만들어진 클래스다)

> Math 클래스에 있는 상수나 메소드는 모두 static으로 선언되어 있기 때문에 별도의 객체를 생성할 필요가 없다.
- (double) E :  자연 로그 알고리즘에 기반하여 지수인 e에 근사한 값을 제공한다.
- (double) PI : 파이값

##### 절대값 및 부호 관련
- abs() :  절대값 계산
- signum() :  부호확인 (양수는 1.0, 음수는 -1.0, 0은 0을 리턴)

##### 최대 /최소값 관련
- min()
- max()

```java
	public void mathCheck1() {
		double number1 = 45;
		double number2 = -45;
		System.out.println("Math.abs(45)=" + Math.abs(number1));
		System.out.println("Math.abs(-45)=" + Math.abs(number2));

		System.out.println("Math.signum(45)=" + Math.signum(number1));
		System.out.println("Math.signum(-45)=" + Math.signum(number2));

		System.out.println("Math.min(45,-45)=" + Math.min(number1, number2));
		System.out.println("Math.max(45,-45)=" + Math.max(number1, number2));
	}
```

##### 올림 / 버림 관련

- round() : 반올림 float->int, double->long
- rint() : 반올림 double->double
- ceil) : 올림
- floor() : 버림

```java
	public void mathCheck2() {
		double[] numbers = new double[] { 3.15, 3.62, -3.15, -3.62 };
		for (double number : numbers) {
			System.out.println(number);
			System.out.print("Math.round()=" + Math.round(number));
			System.out.print(" Math.rint()=" + Math.rint(number));
			System.out.print(" Math.ceil()=" + Math.ceil(number));
			System.out.print(" Math.floor()=" + Math.floor(number));
			System.out.println();
		}
	}
```

> round()를 제외한 나머지는 double 타입 그대로 리턴한다.
> 만약 double 형 반올림 결과가 필요하다면 round()가 아닌 rint()를 사용하면 된다.

##### 제곱과 제곱근 관련

- sqrt() :  매개변수의 제곱근을 구한다.
- cbrt() : 매개 변수의 세제곱근을 구한다.
- pow() :  첫번째 매개 변수의 두번째 매개 변수만큼의 제곱값을 구한다.

```java
	public void mathCheck3() {
		System.out.println("Math.sqrt(1)=" + Math.sqrt(1));
		System.out.println("Math.sqrt(2)=" + Math.sqrt(2));
		System.out.println("Math.cbrt(8)=" + Math.cbrt(8));
		System.out.println("Math.cbrt(27)=" + Math.cbrt(27));
		System.out.println("Math.pow(2,2)=" + Math.pow(2, 2));
		System.out.println("Math.scalb(2,4)=" + Math.scalb(2, 4));
		System.out.println("Math.hypot(3,4)=" + Math.hypot(3, 4));
		System.out.println("Math.sqrt(Math.pow(3,2)+Math.pow(4,2))=" + Math.sqrt(Math.pow(3, 2) + Math.pow(4, 2)));
	}
```

##### 삼각함수 관련

- toRadians() : 각도를 레디안 값으로 변환
- toDegress() : 레디안 값을 각도로 변환
- sin() : 사인 값
- cos() : 코사인 값
- tan() : 탄젠트 값

> 삼각함수의 매개변수로 넘어가는 값은 모두 레디안 값으로 제공되어야만 한다 
> 따라서 360도 기준으로 되어있는 가도 값을 계산할때는 toRadians()메소드를 사용하여 레디안으로 변환후 값을 구하자

```java
	public void mathCheck4() {
		double number1 = 45;
		double number2 = 90;

		double radian45 = Math.toRadians(number1);
		double radian90 = Math.toRadians(number2);
		System.out.println("Radian value of 45 degree is " + radian45);
		System.out.println("Radian value of 90 degree is " + radian90);
		System.out.println("Math.sin(45 degree)=" + Math.sin(radian45));
		System.out.println("Math.sin(90 degree)=" + Math.sin(radian90));
		System.out.println("Math.cos(45 degree)=" + Math.cos(radian45));
		System.out.println("Math.cos(90 degree)=" + Math.cos(radian90));
		System.out.println("Math.tan(45 degree)=" + Math.tan(radian45));
		System.out.println("Math.tan(90 degree)=" + Math.tan(radian90));

	}
```


---

### 직접해 봅시다

```java
public class NumberObject 
{
	public static void main(String[] args) 
	{
		NumberObject no=new NumberObject();
		//no.parseLong("r1024");
		//no.parseLong("1024");
		no.printOtherBase(1024);
	}

	public long parseLong(String data){
		long longdata=-1;
		
		try{
			
			longdata=Long.parseLong(data);
			System.out.println(longdata);

		} catch(NumberFormatException ne){
			System.out.println(data+" is not a number");
		}
		catch(Exception e){

		}
		return longdata;
		
	}

	public void printOtherBase(long value){

		System.out.println("Original:"+value);
		System.out.println("Binary:"+Long.toBinaryString(value));
		System.out.println("Hex:"+Long.toHexString(value));
		System.out.println("Octal:"+Long.toOctalString(value));
	}
}
```

---

### 정리해 봅시다

1. java.lang 패키지는 별도로 import하지 않아도 된다. 

2. 자바의 메모리가 부족하여 발생하는 에러는 OutOfMemoryError이다. 
이 에러에 대한 보다 자세한 설명은 "자바 개발자와 시스템 운영자를 위한 트러블 슈팅 이야기"를 참조하기 바란다.

3. 자신의 메소드를 자기가 다시 부르는 재귀 호출 메소드와 같은 것을 잘못 구현하면 StackOverflowError가 발생한다.

4. java.lang 패키지에 선언되어 있는 어노테이션은 다음과 같다.
- Deprecated : 더 이상 사용하지 않는 다는 것을 명시
- Override : Override 명시
- SuppressWarnings : 경고 무시

5. 기본 자료형을 참조자료형으로 만든 클래스들의 MIN_VALUE(최소값)와 MAX_VALUE(최대값) 를 사용하면, 각 타입의 최대 최소값을 확인할 수 있다.

6. Integer클래스의 toBinaryString() 메소드를 호출하면 매개변수의 값을 2진법으로 나타낸다.

7. Integer클래스의 toHexString() 메소드를 호출하면 매개변수의 값을 2진법으로 나타낸다.

8. Properties는 JVM에서 사용하는 속성 값을 제공하며, Environmemt는 시스템(장비)에서 사용하는 환경 값을 제공한다.

9. System.out과 System.err는 모두 java.io.PrintStream 클래스를 의미한다. 

10. System.currentTimeMillis() 메소드를 호출하면 현재 시간을 밀리초(1/1000)단위로 제공한다. 이 시간은 1970년 1월 1일 00:00 부터 현재까지의 시간이다.

11. System.nanoTime() 메소드는 나노초 단위로 결과를 제공하며, 이 메소드에서 제공하는 시간은 오직 소요 시간을 측정하기 위해서 사용된다.

12. System.out.print() 메소드는 데이터를 출력후 줄바꿈을 하지 않으며, System.out.println()메소드는 데이터를 출력후 줄바꿈을 수행한다.

13. System.out.println() 메소드에서 출력을 할 때에는 String 클래스에 선언된 valueOf()메소드가 수행된다. toString()메소드가 수행되는 것이 아니다. 

14. 숫자 계산을 위해서 Math라는 클래스가 존재한다. 

15. Math 클래스에 있는 상수와 메소드는 모두 static 으로 선언되어 있기 때문에 별도의 객체를 선언할 필요가 없다.

16. 숫자의 절대값은 Math 클래스의 abs() 메소드를 사용하면 된다.

17. 반올림을 하는 Math 클래스의 메소드는 round()와 rint() 이다. 

18. Math클래스에서 Radian으로 변환하는 메소드는 toRadians()메소드이며, Degree로 변환하는 메소드는 toDegrees() 메소드이다. 

19. Math 클래스의 pow() 메소드는 제곱값을 구하는 데 사용한다. 5의 4제곱은 Math.pow(5,4)과 같이 사용하면 된다. 


