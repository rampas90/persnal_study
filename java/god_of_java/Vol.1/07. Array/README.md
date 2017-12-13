## 7장. 여러 데이터를 하나에 넣을 수는 없을까요?

### 1. 하나에 많은 것을 담을 수 있는 배열이라는 게 있다는데...
- PHP에서 가장 많이 다루고 제일 익숙한 만큼 차이점을 위주로 체크
- PHP로 구현했던 다차원배열 알고리즘 **Java**로 구현해보기
#### *참고사이트*
	- [coderbyte.com](http://coderbyte.com)
	- [checkio.org](http://checkio.org)


_ _ _

  Java에서 기본자료형의 배열은 다음과 같다.
`int [] lottoNumbers`
`int lottoNumbers[];`

이렇게 선언한 배열은 아직 몇 개의 데이터가 들어가는지 알 수가 없다.
100개가 들어가는지 1000개가 들어가는지 ...따라서 아래와 같이 초기화를 시켜줘야 한다.
`int [] lottoNumbers = new int[7];`


> 이때 `int` 자체는 기본 자료형이지만 , `int[]`와 같이 `int`를 배열로 만든
> `lottoNumbers`는 참조자료형이다. 
> 이전장에서 참조자료형의 객체를 생성할 때에는 반드시 **new** 를 사용해야한다고 했던것 기억
> 배열역시 참조 자료형이기때문에 일반적으로 *new*를 사용한다고 알아두자 (예외도 있다고 한다.) 

`int[] lottoNumbers;`
`lottoNumbers = new int[7];`
> 위와 같이 두줄로 선언해도 무방!


- - -

### 2. 배열의 기본값

1. 기본 자료형 배열의 기본값은 각 자료형의 기본값과 동일하다.
2. 지역변수(메소드내에서 선언한 변수)의 경우에는 초기화를 하지 않으면 사용이 불가능하지만
배열에서는 지역변수라고 할지라도, 배열의 크기만 정해주면 에러가 발생하지 않는다....

```java
public void primitiveTypes()
{
		byte[] byteArray = new byte[1];
		short[] shortArray = new short[1];
		int[] intArray = new int[1];
		long[] longArray = new long[1];
		float[] floatArray = new float[1];
		double[] doubleArray = new double[1];
		char[] charArray = new char[1];
		boolean[] booleanArray = new boolean[1];
		System.out.println("byteArray[0]=" + byteArray[0]);
		System.out.println("shortArray[0]=" + shortArray[0]);
		System.out.println("intArray[0]=" + intArray[0]);
		System.out.println("longArray[0]=" + longArray[0]);
		System.out.println("floatArray[0]=" + floatArray[0]);
		System.out.println("doubleArray[0]=" + doubleArray[0]);
		System.out.println("charArray[0]=[" + charArray[0] + "]");
		System.out.println("booleanArray[0]=" + booleanArray[0]);
}
```
> 위 내용을 돌려보면 기본자료형의 기본값이 잘 출력된다.
> 다만 char의 경우엔 **space** 임을 상기.~




---


### 추가 정리 (보충)

1. 배열 사용방법
	1) 선언
    2) 메모리 할당
    3) 초기화

예1)
`int a[];` -배열 선언  -- 이단계에서 크기정하면 안됨 주의
`a=new int[2]` - 메모리할당   -- **!!대괄호 주의**
`a[0]=10` - 초기화

예2) 선언과 메모리 할당을 동시에 하는 방법
`int b[]=new int[3];` -배열 선언 + 메모리 할당
`b[0]=100` - 초기화
`b[3]=400` - 인덱스 초과오류 이미 초기화한 크기를 바꿀수 없음- **javascript와 다른점!**
예3) 선언과 메모리할당, 초기화를 동시에 하는 방법
`int [] c={1,2,3,4,5,}` 

> 또한 참조형 배열의 특징도 되새겨 보기 (스왑예제 생각)
     