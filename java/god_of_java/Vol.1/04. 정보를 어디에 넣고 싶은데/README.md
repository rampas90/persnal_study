```java	
public class VariableTypes
{
	int instanceVariable;
	static int classVariable;

	public void method(int parameter)
	{
		int localVariable;
	}
}
```

### 1.자바의변수
#####	1) 지역변수 ( local )
- 중괄호 내에서 선언된 변수
- 지역 변수를 선언한 중괄호 내에서만 유효하다.



#### 2) 매개변수 ( parameters )
- 메소드나 생성자에 넘겨주는 변수
- 메소드가 호출될 때 생명이 시작되고, 메소드가 끝나면 소멸된다(정확히 호출될때 시작하지는 않지만 일단은 이정도로기억해두자)


#### 3) 인스턴스 변수 ( instance )
- 메소드 밖에 , 클래스 안에 선언된 변수, 앞에는 static이라는 예약어가 없어야 한다.
- 객체가 생성될 때 생명이 시작되고, 그 객체를 참조하고 있는 다른 객체가 없으면 소멸된다.

#### 4) 클래스 변수 ( class )
- 인스턴스 변수처럼 메소드 밖에, 클래스 안에 선언된 변수 중에서 타입 선언앞에 static 이라는 예약어가 있는 변수
- 클래스가 생성될 때 생명이 시작되고 , 자바 프로그램이 끝날 때 소멸된다. 


	
	
> ##### 생각해보기 #####
>    아래 두개의 지역변수는 서로 같은 변수일까?


```java
public class VariableTypes
{
	int instanceVariable;
	static int classVariable;

	public void method(int parameter)
	{
		int localVariable;
	}

	public void anothermethod(int parameter)
	{
		int localVariable;
	}
}
```

> ##### 생각해보기 #####

> 아래 1번과 2번의 의 지역변수는 서로 같은 변수일까?
> 아래는 컴파일 에러가 난다 그이유는 글 작성시점에선 알고있지만 나중에 봤을때 생각이 안난다면 p.87 참조


```java
	public class VariableTypes
	{
		int instanceVariable;
		static int classVariable;

		public void method(int parameter)
		{
			int localVariable;
		}

		public void anothermethod(int parameter)
		{
			if(true){
				int localVariable;   //1
				if(true){
					int localVariable;//2
				}
			}				   //3
			
			if(true){
				int localVariable;
			}
		}
	}

```

### 2. 자바의 자료형

#### 1) 기본자료형 (Primitive Data Type)
- 추가로 만들 수 없다. 
- 이미 정해져 있다
- int a = 10;
- new 없이 바로 초기화가 가능한것을 기본자료형이라 한다.

* 기본자료형의 종류
	- ㄱ)정수형 : byte, short, int, long, char
	- ㄴ)소수형 : float, double
	- ㄷ)기타   : boolean
      
ㄱ) 정수형

타입 | 최소 | 최대
------------ | ------------- | -------------
byte | -128 | 127
short | -32,768 | 32,767
int | -2,147,483,648 | 2,147,483,647
long | -9,223,372,036,854,775,808 | 9,223,372,036,854,775,807
char | 0('\u0000') | 65,535('\uffff')

8비트와 byte타입
> 아래 표를 보고 C를 생각해보면 생각날듯...(포인터)

2 ^7 | 2^6 | 2^5 | 2^4 | 2^3 | 2^2 | 2^1 | 2^0 
--- | --- | --- | --- | --- | --- | --- | --- 
0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 

> 위 테이블에서 모든공간이 1로 채워지면 255이다. 
> 근데 왜 byte의 범위는 127로 표기할까? 계산식에서 정확히 반토막이다.
> 자바의 기본 자료형에 포함된 숫자들은 모두 부호가 있는 **signed**타입들이다. 
> 그 방법들을 선배 엔지니어들이 고민에 고민을 하다가 "맨앞에 있는 값이 0이면 양수, 1이면 음수로 정하자"
> 라고 결정했다. 다시말하면, 가장 왼쪽의 공간은 2의7승이 아니라 그냥 음수와 양수를 구분하기 위한 공간이다. 
> 그래서!!
> 위 계산식을 다시 해보면 127이 되는것이다.

> 여기서 또 의문!
> 왜 음수는 -128까지일까?  이론상 모든 byte의 공간을 1로 채우면 -127이 되어야 정상이다...
> 그이유는 **하나의 값이라도 더 제공할 수 있도록 엔지니어들이 고민했기 때문이다**
> 저 자세한 사항은 p.95 참조 및 부록의 비트연산자 참조

```java
public class PrimitiveTypes
{
	public void checkByte()
	{
		byte byteMin = -128l
		byte byteMax = 127;
		System.out.println("byteMin="byteMin);
		System.out.println("byteMax="byteMax);
		byteMin--;
		byteMax++;
		System.out.println("byteMin--="byteMin);
		System.out.println("byteMax++="byteMax);
	}
	
	public void checkOtherTypes()
	{
		short shortMax=32767;
		int intMax=2147483647;
		long longMax=9223372036854775807l;
		//  맨 마지막은 소문자L 이다. long 타입을 명시하기 위해 사용
	}
	
	public static void main(String[] args)
	{
		PrimitiveTypes types=new PrimitiveTypes();
		types.checkByte();
	}
}
```
> 위 내용을 출력해보면 최소값에서 1을 빼면 최대값이 출력되고,
> 최대값에서 1을 더하면 최소값이 된다.

ㄴ) 소수형

- float과 double은 모두 소수점 값을 처리하기 위해서 사용된다
- float는 32비트, double은 64비트
- 간단한 계산에서는 사용해도 무방하지만 중요한 돈계산등에는 이 타입들을 사용하지 않는다.
	- 32비트와 64비트를 넘어서면 값의 정확성을 보장하지 못하기 때문!
	- 그래서 보통 자바에서는 돈계산과 같이 정확한 계산이 요구될 경우 
	`java.math.BigDecimal 
	- 이라는 클래스를 사용하며 이는 Vol.2 에 자세히 설명되어있다고 한다~
- 일반적으로 double을 많이 사용한다

ㄷ) 기타

* 기본 자료형의 기본 값은 뭘까?
	* 자바의 모든 자료형은 값을 지정하지 않으면 기본값을 사용한다
	* 단!! 지역변수는 기본값이 적용되지 않는다.
	* 즉 메소드 안에서 지정한 변수에 값을 지정하지 않고 사용하려고 하면 컴파일에러!
	* 인스턴스변수, 클래스변수,매개변수는 값을 지정하지 않아도 컴파일이 되기는 하지만 이는 안좋은 습관이다




- char와 boolean은 어떻게 쓸까?
```java
public void checkChar()
{
	char charMin = '\u0000';
	char charMax = '\uffff';
	System.out.println("charMin=["+ charMin+"]");
	System.out.println("charMax=["+ charMax+"]");
}
```
```
Result : charMin=[ ]
         charMax=[?]
```
> 위 내용처럼 min의 경우 아무것도 없는것처럼 보이나 실제로는 공백이 출력되어있는것이다.
- 자세한 사항은 p.102참조
- 자바의 정수중 유일하게 부호가 없는 **unsigned**값이다.
- boolean 값은 생략~


#### 2) 참조자료형 (Reference Data Type)
- 마음대로 만들 수 있다. ex) Calculator, Car 같은 클래스들
- Calculator calc = new Calculator();
- new를 사용해서 초기화하는 것을 참조자료형
- String의 경우는 참조자료형

` 일반적으로 아래와 같이 사용하여 기본자료형이라고 생각할 수 있지만.!!
```
String str1 = "book1";
```				
` 다음과 같이 정의해도 상관없다.
```
String str2 = new String("book2");
```



