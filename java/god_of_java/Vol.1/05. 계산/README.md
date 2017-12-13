## 계산을 하고 싶어요

### 1.연산자라는게 뭐지?
- PHP와 달리 명시해줘야될 부분이 있나?
- jsp로 게시판 페이징 구현시 ceil이 원하는대로 써지지않아 애먹었던 기억을 떠올려보자

```java
public class Operators
{
  //중간생략
  public void multiplicative()
  {
    int intValue1=5;
    int intValue2=10;
    
    int result=intValue1 * intValue2;
    System.out.println(result);
    
    // 아래 계산내용은 0.5가아닌 0으로 출력... 왜?
    result= intvalue1/intValue2;
    
    // 아래 (float)처럼 명시해줘야 원하는 값인 0.5가 출력된다.
    float result2=(float)intValue1/intValue2;
    System.out.println(result2);
  }   
}
```

> 자바는 계산하는 두 값이 정수형이더라도, 결과가 소수형이면 알아서 소수형으로 결과가 나오지 않는다
> 그래서 float으로 변환한 것이다. 더 자세한 사항은 이장의 마지막절의 형변환을 참조

== != 등의 등가비교연산자의 경우 같은 종류끼리 사용가능하다.
- char == int
- double == int
- boolean == boolean

연산자 | 사용가능한 타입
------ | ------
==, != | 모든 기본 자료형과 참조 자료형 -> 즉 모든 타입
>,<,>=,<= | boolean을 제외한 기본자료형


그 외 연산자들은 그냥 생략...필요하면 책찾아보자

###2.? :  연산자( 삼항연산자 )...

```java
public class Operators
{
  //중간 생략
  public boolean doBlindDate(int point)
  {
    boolean doBlindDateFlag = false;
    doBlindDateFlag = (point>=80) ? true : false;
    System.out.println(point+" : " + doBlindDateFlag);
    return doBlindDateFlag;
  }
}

```
> 위 삼항연잔자를 표기해보면 아래와 같다
`변수 = (boolean조건식) ? true일때값 : false일때값;`


###3. 캐스팅

- 형 변환은 **casting**이라고 한다. 
- 자바의 형변환은 기본자료형과 참조 자료형 모두 괄호로 묶어주면 된다.
- 단 불린값의 경우는 형변환이 되지 않는다...당연하게도
- 기본자료형 -> 참조자료형, 혹은 그반대의 경우는 형변환이 불가능하다.






