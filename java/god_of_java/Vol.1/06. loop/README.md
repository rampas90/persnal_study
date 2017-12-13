## 6장 제가 조건을 좀 따져요

### Loop 
- if
- switch
- for
- while
- for

> 다른 언어들과 특별히 다른건 없어 보인다 Python 이나 Ruby 완 다르게 그냥 쓰던대로 쓰면 될것 같다. 
> 혹 문제가 생긴다면 구글링정도로..
> PHP 에서는 break 를 궂이 쓰지 않았는데 간단한 알고리즘 자체에서 구현에 문제가 없었던 건지 java와 다른점인지 정도만
> 짚고 넘어가면 될 것 같다.

```java
public class ControlOfFlow{
	// 중간 생략

	public void switchStatatement(int numberOfWheel)
	{
		switch(numberOfWheel){
			case 1:
				System.out.println("It is one foot bicycle");
				//break;
			case 2:
				System.out.println("It is a motor cycle or bicycle");
				//break;
			case 3: 
				System.out.println("It is a Three whell car.");
				break;
			case 4: 
				System.out.println("It is a Three whell car.");
				break;
			/*
				이하 케이스 생략
			*/
			default : 
				System.out.println("It is ans expensive car");
				break;
		}
	}
}

```

> Result
> 1~3번째 출력문 까지 출력
> break 문은 기타 언어와 사용법이 동일하다. 

> 다만 궂이 유의할점은 default 값은 마지막에 쓰길 권장하며
> 숫자비교시엔 되도록 적은 숫자부터 증가시켜 가는것이 좋다고 한다.
> 또한 JDK7 (회사 로컬에 셋팅한 자바도 7버전대로 기억한다...톰캣도 7대...) 부터는 String 도 사용할 수 있다고 한다.


```java
	public void switchStatement2(int month)
	{
		switch(month)
		{

			case 1:
			case 3: 
			case 5: 
			case 7:
			case 8:
			case 10:
			case 12:
				System.out.println(month + " has 31 days." );
				break;
			case 4:
			case 6:
			/*	
				이하 생략
				각각 30일 , 28 || 29
				그리고 기본값 코드 작성이다.
			*/
		}
	}

	public void whileLoop()
	{
		int loop = 0;

		while(loop<12)
		{
			loop++;
			switchStatement2(loop);
			if(loop == 6) break;

		}
	}
```



```java
	public void whileLoop2()
	{
		int loop=0;
		do
		{
			loop++;
			switchStatement2(loop);

		}
		while(loop<12);  // 세미콜론을 반드시 찍어줘야한다.
	}
```
> do~ while 문은 적어도 한번은 꼭 실행된다.
> 위 조건문은 whileLoop 와 동일한 로직이다.




