## 14. 다 배운 것 같지만, 예외라는 중요한 것이 있어요

### 1. 자바에서 매우 중요한 예외

자바에서는 `예외(Exception)`라는 것이 있다. 예외를 모르면 자바를 모르는 것과 같다고 생각해도 된다.

예를 들어보자
- 가장일반적인 예로는 `null`인 객체에 메소드를 호출한다든지,
- 5개의 공간을 가지는 배열을 만들었는데 6번째 값을 읽으라고 하든지,
- 어떤 파일을 읽으라고 했는데 읽을 파일이 존재하지 않는다든지
- 네트워크 연결이 되어있는 어떤 서버가 갑자기 작동을 멈춰서 연결이 끊겨버린다든지

### 2. try-catch 는 짝이다

가장 일반적인 예로 설명했던 배열 범위 밖의 값을 읽으려고 할 때를 살펴보자.

```java
public class ExceptionSample {
	public static void main(String[] args) {
		ExceptionSample sample = new ExceptionSample();
        sample.arrayOutOfBounds();
	}
    public void arrayOutOfBounds() {
		int[] intArray = new int[5];
        System.out.println(intArray[5]);
	}
}
```
> 위 코드를 보면 당연히 "컴파일이 안되겠지..."라고 생각할 수 있지만 자바에서는 이러한 부분을 컴파일할때 점검해주지 않는다
> 실행결과를 살펴보자

```java
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 5
	at ExceptionSample2.arrayOutOfBounds(ExceptionSample2.java:11)
	at ExceptionSample2.main(ExceptionSample2.java:6)
```
> `ArrayIndexOutOfBoundsException`라는 것이 발생했다.
> 컴파일 할때 에러 메시지가 발생하는 것처럼,(위 에러는 컴파일이 아니라 실행시 에러다) 예외의 첫줄에는
> 어떤 예외가 발생했다고 출력된다. 그다음 줄부터는 탭~tab~을 주고 `at`로 시작하는 `스택 호출 추적`(영어로 call stack trace라고 하고, 보통 스택 트레이스라고 부르는)문장들이 출력된다.

>  이 호출 관계의 가장 윗줄에는 예외가 발생한 클래스와 메소드 이름과 줄의 번호를 출력하고, 그 아래에는 그 메소드를 호출한 클래스의 메소드의 이름 및 줄의 번호가 출력된다. 

> 위 코드는 간단한 소스로 두개의 스택트레이스(`at`가 맨 앞에 있는 줄이 두개)만 찍혔지만
> 실제 운영되는 자바 기반의 시스템들은 그렇게 단순하지 않기 때문에 몇십 줄에서 백줄까지 출력되기도 한다.

그렇다면 이와 같은 예외 메시지가 발생하지 않도록 할 수는 없을까?

> 물론 예외가 발생하지 않도록 개발하는 것이 우선이지만 완벽하게 처리하기는 어려운 부분들이 있다
> `arrayOutOfBounds()` 메소드를 다음과 같이 변경하자

```java
public void arrayOutOfBounds() {
	try{
		int[] intArray = new int[5];
		System.out.println(intArray[5]);
	}catch(Exception e){

	}
}
```

메소드 괄호 안에 if문처럼 try를 쓰고 중괄호로 감싸고, catch라고 쓰고 메소드를 호출하는 것처럼 소괄호 안에 `Exception` 이라는 클래스 이름과 매개 변수 이름같은 `e`라는 것을 써주었다.

> 위와 같이 예외를 처리하는 것이 "try~catch블록"이다.

위와같이 처리를 한후 재 컴파일->실행을 해보면 정상적으로 실행되는 것을 알수있다.
단. 이때 메시지를 출력하지 않을 뿐이지 실제로 예외는 발생한 것이다.

> 다시 말하면 `try~catch`의 try블록 안에서 예외가 발생되면 그 이하의 문장은 실행되지 않고 바로 catch 블록으로 넘어간다는 뜻이다.


### 3. try-catch를 사용하면서 처음에 적응하기 힘든 변수 선언

`try-catch`를 사용할 때 가장 하기 쉬운 실수가 있다. 바로 변수의 지정이다
- `try블록` 내에서 선언한 변수를 catch에서 사용할 수 없다.

> 위와 같은 문제때문에 일반적으로 `catch` 문장에서 사용할 변수에 대해서는 `try` 전에 미리 선언해 놓는다.

여기서 주의 할점은 `catch 블록`이 실행된다고 해서 `try`블록 내에서 실행된 모든 문장이 무시되는 것은 아니다. 예외가 발생하기 전까지의 문장들은 전부 작동을 한다고 생각하면 된다.

아래 코드를 컴파일 하고 실행해보자

```java
public void arrayOutOfBounds() {
	int[] intArray = null;
	try{
		intArray=new int[5];
		System.out.println(intArray[5]);
	}catch(Exception e){
		System.out.println(intArray.length);
	}
	System.out.println("this code shoul run.");
}
```

위 소스를 실행해보면 아래와 같다.

```
5
this code should run
```

> 위 소스를 보면 `try`내의 모든 문장이 모두 무시되는것이 아닌 배열선언까지는 읽혔다는것을 알수있다 그게 아니라면 `intArray[]` 의 길이를 불러올수 없었을 것이다.

### 4. finally 야 ~ 넌 무슨일이 생겨도 반드시 실행해야 돼

try-catch 구문에 추가로 붙을 수 있는 블록이 하나 더 있다. 바로  `finally`다

- `finally` 블록은 예외 발생여부와 상관없이 실행된다
- 코드의 중복을 피하기 위해서 반드시 필요하다.

### 5. 두개 이상의 catch

`try-catch`문을 쓸때 catch에 `Exception e`라고 아무 생각없이 썻다. 이 `catch`블록이 시작되기전에 (중괄호가 시작되기전에) 있는 소괄호에는 예외의 종류를 명시한다. 
다시말해서 반드시 `Exception e`라고 쓰는 것은 아니라는 것이다.


```java
public void multiCatch() {
	int[] intArray = new int[5];
	try {
		System.out.println(intArray[5]);
	} catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("ArrayIndexOutOfBoundsException occured");
	} catch (Exception e) {
		System.out.println(intArray.length);
	}
}
```
>실행결과
`ArrayIndexOutOfBoundsException occured`

위 코드의 실행결과를 보면 `catch`문에 작성된 것만 처리되는구나..라고 생각할 수 있다.
이는 어떻게 보면 맞는말이지만 틀린 말이기도 하다

- `catch`블록의 순서는 매우 중요하다. `catch`블록은 순서를 따진다.

이번에는 위의소스를 순서를 바꾸어 실행해보자

```java
public void multiCatch() {
	int[] intArray = new int[5];
	try {
		System.out.println(intArray[5]);
	} catch (Exception e) {
		System.out.println(intArray.length);
	} catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("ArrayIndexOutOfBoundsException occured");
	}
}
```
> 이렇게 해놓고 컴파일을 해보면 다음과 같은 에러메시지가 출력된다.

```
ExceptionSample2.java:28: error: exception ArrayIndexOutOfBoundsException has already been caught
		} catch (ArrayIndexOutOfBoundsException e) {
		  ^
1 error
```

**지난 과정에서 모든 객체의 부모클래스는 바로 java.lang.Object클래스라고 했다**그렇다면 모든 예외의 부모 클래스는 뭘까?

> 모든 예외의 부모 클래스는 `java.lang.Exception` 클래스다.

`java.lang.Exception` 클래스는 java.lang 패키지에 선언되어 있기 때문에 별도로 import할 필요는 없었다.

그런데 왜 단순히 순서만 바꾼 위 코드는 컴파일조차 되지 않았알까?
예외는 부모 예외 클래스가 이미 catch를 하고, 자식 클래스가 그 아래에서 catch를 하도록 되어 있을 경우에는 자식 클래스가 예외를 처리할 기회가 없다.
> 다시 말하면 `Exception`클래스가 모든 클래스의 부모클래스이고, 배열에서 발생시키는
> `ArrayIndexOutOfBoundsException`은 `Exception` 클래스의 자식 클래스이기 때문에 
> 절대로 `Exception` 클래스로 처리한 catch블록 이후에 선언한 블록은 처리될 일이 없다.

- 그래서 일반적으로 `catch` 문을 여러개 사용할 경우에는 `Exception`클래스로 `catch`하는 것을 가장 아래에 추가할 것을 권장한다.

> 정리해보자

- try 다음에 오는 catch 블록은 1개 이상 올 수 있다.
- 먼저 선언한 catch 블록의 예외 클래스가 다음에 선언한 catch블록의 부모에 속하면, 자식에 속하는 catch블록은 절대 실행될 일이 없으므로 컴파일이 되지 않는다.
- 하나의 try블록에서 예외가 발생하면 그예외와 관련이 있는 catch블록을 찾아서 실행한다
- catch 블록 중 발생한 예외와 관련있는 블록이 없으면, 예외가 발생되면서 해당 쓰레드는 끝난다(이 부분에 대해서는 나중에 쓰레드를 배워야 이해가 쉽다. 일단은 main() 메소드가 종료되어 프로그램이 종료된다고 생각하면 된다.) 따라서, 마지막 catch블록에는 Exception클래스로 묶어주는 버릇을 들여 놓아야 안전한 프로그램이 될 수 있다.

### 6. 예외의 종류는 세가지다

자바에는 세가지 종류의 예외가 존재하며, 각 예외는 다음과 같다
- checked exception
- error
- runtime exception 혹은 unchecked exception

각 예외의 구분은 간단하다 두번째와 세번째에 있는 error 와  unchecked exception을 제외한 모든 예외는 checked exception이다. 

1) error (이하 에러)

에러는 자바 프로그램 밖에서 발생한 예외를 말한다. 가장 흔한 예가 서버의 디스크가 고장났다든지, 메인보드가 맛이가서 자바프로그램이 제대로 동작하지 못하는 경우가 여기에 속한다.

> 뭔가 자바 프로그램에 오류가 발생했을 때, 오류의 이름이 Error로 끝나면 에러이고, Exception으로 끝나면 예외다.

Error와  Exception으로 끝나는 오류의 가장 큰 차이는 프로그램 안에서 발생했는지, 밖에서 발생했는지 여부이다. 하지만, 더 큰 차이는 프로그램이 멈추어 버리느냐 계속 실행할 수 있느냐의 차이다

> 더 정확하게 말하면 Error는 프로세스에 영향을 주고, Exception은 쓰레드에만 영향을 준다.


2) runtime exception (이하 런타임 예외)

- 런타임 예외는 예외가 발생할 것을 미리 감지하지 못했을 때 발생한다.
- 컴파일시에 체크를 하지 않기 때문에 unchecked exception 이라고도 부른다.

### 7. 모든 예외의 할아버지는 java.lang.Throwable 클래스다

앞절에서 살펴본 `Exception`과 `Error`의 공통 부모 클래스는 당연히 `Object`클래스다.
그리고 공통 부모 클래스가 또 하나 있는데 , 바로 java.lang 패키지에 선언된 `Throwable`클래스다.

Exception 과 Error 클래스는 Throwable 클래스를 상속받아 처리하도록 되어 있다.
그래서, Exception이나 Error를 처리할 때 Throwable로 처리해도 무관하다.

상속 관계가 이렇게 되어 있는 이유는 Exception이나 Error의 성격은 다르지만 모두 동일한 이름의 메소드를 상요하여 처리할 수 있도록 하기 위함이다.

> Throwable에 어떤 생성자가 선언되어 있는지 살펴보자
- Throwable()
- Throwable(String message)
- Throwable(String cause)

아무런 매개변수가 없는 생성자는 기본적으로 제공한다. 그리고, 예외 메시지를 String으로 넘겨줄 수도 있다. 그리고, 별도로 예외의 원인을 넘길 수 있도록 Throwable 객체를 매개 변수로 넘겨 줄 수도 있다.

> Throwable 클래스에 선언되어 있고 Exception 클래스에서 오버라이딩 하는 메소드는 10개가 넘지만 그 중에서 가장 많이 사용되는 메소드는 다음과 같다.

- getMessage()
- toString()
- printStackTrace()

1) getMessage()
예외 메시지를 String 형태로 제공 받는다. 예외가 출력되었을 때 어떤 예외가 발생됬는지 확인할 때 매우 유용하다. 그 메시지를 활용하여 별도의 예외 메시지를 사용자에게 보여주려고 할 때 좋다

2) toString()
예외 메시지를 String 형태로 제공 받는다. 그런데 , getMessage()메소드보다는 약간 더 자세하게 , 예외 클래스 이름도 같이 제공한다.

3) printStackTrace()
가장 첫 줄에는 예외 메시지를 출력하고, 두번째 줄부터는 예외가 발생하게 된 메소드들의 호출관계(스택 트레이스)를 출력해준다.

### 8. 난 예외를 던질 거니까 throws라고 써놓을께

지금까지는 예외를 처리하는 방법을 배웠다. 이제부터는 예외를 발생시키는 방법을 알아보자.
예외를 발생시킨다는 표현이 좀 어색할 것이다. 정확하게 말하면 자바에서는 예외를 던질 수 있다.

```java
public void throwException(int number) throws Exception {
	try {
		if (number > 12) {
			throw new Exception("Number is over than 12");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
```

이렇게 메소드 선언에 해 놓으면, 예외가 발생했을 때 try-catch로 묶어주지 않아도 그 메소드를 호출한 메소드로 예외처리를 위임하는 것이기 때문에 전혀 문제가 되지 않는다.
즉, 위 코드에서 try-catch블록으로 묶지 않아도 예외를 throw한다고 할지라도, throws가 선언되어 있기 때문에 전혀 문제가 없다. 하지만, 이렇게 throws로 메소드를 선언하면 개발이 어려워진다.
왜냐하면, 이 throwException() 이라는 메소드는 Exception을 던진다고 throw 선언을 해 놓았기 때문에, throwException() 메소드를 호출한 메소드에서는 throwException() 메소드를 수행하는 부분에는 반드시 try-catch 블록으로 감싸주어야만 한다.

정리해보자
- 메소드를 선언할 때 매개변수 소괄호 뒤에 throws 라는 예약어를 적어 준 뒤 예외를 선언하면, 해당 메소드에서 선언한 예외가 발생했을 때 호출한 메소드로 예외가 전달된다
만약 메소드에서 두 가지 이상의 예외를 던질 수 있다면, implements처럼 콤마로 구분하여 예외클래스 이름을 적어주면 된다.
- try블록 내에서 예외를 발생시킬 경우에는 throw라는 예약어를 저어 준 뒤 예외 객체를 생성하거나, 생성되어 있는 객체를 명시해준다
throw한 예외 클래스가 catch블록에 선언되어 있지 않거나, throws 선언에 포함되어 있지 않으면 컴파일 에러가 발생한다.
- catch 블록에서 예외를 throw 할 경우에도 메소드 선언의 throws 구문에 해당 예외가 정의되어 있어야만 한다.

> 예외를 throw하는 이유는 해당 메소드에서 예외를 처리하지 못하는 상황이거나, 미처 처리하지 못한 예외가 있을 경우에 대비하기 위함이다.
> 자바에서 예외 처리를 할때 throw와 throws 는 매우 중요하다.

### 9. 자바 예외 처리 전략

자바에 예외를 처리할 때에는 표준을 잡고 진행하는 것이 좋다.

> 예외를 직접 만들 때 Exceptioin 클래스를 확장하여 나만의 예외 클래스를 만들었을 경우 
> 이 예외가 항상 발생하지 않고, 실행시에 발생할 확률이 매우 높은 경우에는 런타임 예외로 만드는 것이 나을 수도 있다.
> 즉 클래스 선언시 extends Exception 대신에 extends RuntimeException 으로 선언하는 것이다
> 이렇게 되면, 해당 예외를 던지는(throw하는) 메소드를 사용하더라도 try-catch 로 묶지 않아도 컴파일시에 예외가 발생하지 않는다. 하지만, 이 경우에는 예외가 발생할 경우 해당 클래스를 호출하는 다른 클래스에서 예외를 처리하도록 구조적인 안전장치가 되어 있어야만 한다.
> 여기서 안전장치라고 하는 것은 try-catch로 묶지 않은 메소드를 호출하는 메소드에서 예외를 처리하는 try-catch가 되어 있는 것을 이야기 한다.

정리해보면

- 임의의 예외 클래스를 만들 때에는  반드시 try-catch 로 묶어줄 필요가 있을 경우에만 Exception 클래스를 확장한다.
- **일반적으로 실행시 예외를 처리할 수 있는 경우에는 RuntimeException 클래스를 확장하는 것을 권장한다(아주 중요함!!)**
- catch문 내에 아무런 작업 없이 공백을 놔 두면 안된다.

예외의 처리 전략에 대해선 구글에 "[자바 에외 전략](https://www.google.co.kr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=%EC%9E%90%EB%B0%94%20%EC%98%88%EC%99%B8%20%EC%A0%84%EB%9E%B5)" 이나 "[Java Exception Strategy](https://www.google.co.kr/search?q=Java+Exception+Strategy&oq=Java+Exception+Strategy&aqs=chrome..69i57j0.391j0j4&sourceid=chrome&es_sm=122&ie=UTF-8)"로 검색을 해보면 많은 자료들을 찾을 수 있다.



> 예제

```java
public class Calculator 
{
	public static void main(String[] args) 
	{
		Calculator calc=new Calculator();
		calc.printDivide(1,2);
		calc.printDivide(1,0);
	}
	public void printDivide(double d1, double d2){
		double result=d1/d2;
		System.out.println(result);
	}
}
```

> 실행결과

```
0.5
Infinity
```

> 만약 두번째 값이 "0" 이면 "Second value can`t be Zero"라는 메시지를 갖는 예외를 발생시키고, 발생시킨 예외를 throw할 수 있도록 코드를 수정해보자

```java
public class Calculator 
{
	public static void main(String[] args) 
	{
		Calculator calc=new Calculator();
		try{
			calc.printDivide(1,2);
			calc.printDivide(1,0);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	public void printDivide(double d1, double d2) throws Exception{
		if(d2==0) throw new Exception("Second value can't be Zero");

		if(d2==0){
			throw new Exception("Second value can't be Zero");
		}
		double result=d1/d2;
		System.out.println(result);
	}
}

```


#### 정리해 봅시다

1. 예외를 처리하기 위한 세가지 블록에는 어떤 것이 있나요?
	->  try, catch, fially


2. 1번 문제의 답 중에서 "여기에서 예외가 발생할 것이니 조심하세요"라고 선언하는 블록은 어떤 블록인가요?
	->  try


3. 1번 문제의 답 중에서 "예외가 발생하던 안하던 얘는 반드시 실행되어야 됩니다."라는 블록은 어떤 블록인가요?
	->  finally
    
    =>

4. 예외의 종류 세가지는 각각 무엇인가요?
	->  errow, checked, unchecked
    
    =>	
    checked exception
    error
    runtime exception 혹은 unchecked exception

5. 프로세스에 치명적인 영향을 주는 문제가 발생한 것을 무엇이라고 하나요?
	->  error
    
    =>  error는 치명적인 오류를 의미한다. 기본적으로는 프로그램 내에서 발생한다기 보다는 JVM 이나 시스템에서 문제가 발생했을 때 error가 발생한다.

6. try나 catch 블록 내에서 예외를 발생시키는 예약어는 무엇인가요?
	->  throw
    
    =>  throw를 사용하여 새로운 예외를 발생시키면, 해당 예외를 호출한 메소드로 던진다.

7. 메소드 선언시 어떤 예외를 던질 수도 있다고 선언할 때 사용하는 키워드는 무엇인가요?
	->  throws
    
    =>  throw가 메소드 내에 있다면 메소드 선언시 throws 를 사용하여 던질 예외의 종류를 명시하는 것이 좋다.

8. 직접 예외를 만들 때 어떤 클래스의 상속을 받아서 만들어야만 하나요?
	->  Exception
    
    =>  Exception클래스를 확장하여 예외 클래스를 만들 수 있다.
    하지만, 이렇게 되면 무조건 해당 예외를 던지는 메소드에서 try-catch로 묶어야 한다는 단점이 있다.
    따라서, RuntimeException 클래스를 확장하여 선언하는 것을 권장한다.