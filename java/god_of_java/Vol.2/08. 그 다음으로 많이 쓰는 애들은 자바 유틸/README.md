## 8장. 그 다음으로 많이 쓰는 애들은 자바 유틸

### java.lang 다음으로 많이 사용되는 java.util 패키지

java.util 패키지의 유용한 클래스 목록

- Date 와 Calendar
- Collections
- Arrays
- StringTokenizer
- Properties
- Random
- Formatter

### 날짜를 처리하기 위한 Date와 Calendar
JDK 1.0 버전에서는 날짜를 처리하기 위해서 Date클래스를 사용했다. 
하지만 JDK1.1버전 부터는 Calendar 클래스를 사용하여 날짜 처리 작업을 하도록 변경되었다.
그런고로, Date 클래스에는 deprecated된 메소드들이 존재한다.

##### Date클래스 

> Data 클래스에는 두개의 생성자만 사용가능하다 (나머지는 deprecated)

- Date() : 객체가 생성된 시간을 갖는 Date 객체 생성
- Date(long date) : 매개 변수로 넘어온 long 타입 시간을 갖는 Date 객체 생성

여기서 long값으로 나타내는 시간은 UTC 시간으로 `System.currentTimeMillis()`메소드 호출시 리턴되는 시간과 동일하다.

> 생성자와 마찬가지로 Date클래스의 메소드들은 deprecated 된 것들이 매우 많다.
> 사용가능한 메소드들 중에서 쓸 만한 것은 getTime() 메소드와 setTime()메소드다

```java
import java.util.Date;
public class DateCalendarSample {

	public static void main(String[] args) {
		DateCalendarSample sample=new DateCalendarSample();
		sample.checkDate();
	}
	
	public void checkDate(){
		Date date=new Date();
		System.out.println(date);
		
		long time=date.getTime();
		System.out.println(time);
		
		date.setTime(0);
		System.out.println(date);
	}
}

```

> 출력결과

```java
Wed Nov 18 15:56:27 KST 2015
1447829787901
Thu Jan 01 09:00:00 KST 1970
```

> 지정한 날짜사이의 시간등을 계산하는 것은 쉽지 않다(Date로했을시) 그래서 제공되는 것이 Calendar 클래스다

##### Calendar 클래스

> Calendar 클래스의 선언

```java
public abstract class Calendar
extends Object implements Serializable, Cloneable, Comparable<Calendar>
```

> `abstract` 클래스인데다가, `Calendar` 클래스는 생성자들이 `protected`로 선언되어 있어 얼핏보기엔 이 클래스를 확장하여 구현하지 않으면,
> 생성자로 객체를 만들어낼 수 없어 보인다.

> 그렇다고 객체를 생성할수 없는 것은 아니다. 
> 왜냐하면 Calendar클래스는 `getInstance()`라는 메소드가 존재하기 때문이다.

`getInstance()`의 생성자는 아래처럼 네개가 존재한다.

- getInstance()
- getInstance(Locale aLocale)
- getInstance(TimeZone zone)
- getInstance(TimeZone zone, Locale aLocale)

Locale 클래스는 지역에 대한 정보를 담는다.
자바를 설치할 때 OS에 있는 지역 정보를 확인하여 JVM의 설정에 저장된다.

Timezone 클래스는 시간대를 나타내고 처리하기 위해 사용된다.
우리나라는 GMT기준으로 `+9` 시간이기 때문에 앞서 Date 클래스 예제에서도 `+09:00`으로 표시되는걸 확인했었다.
TimeZone 클래스도 마찬가지로 abstract 클래스이므로 이 클래스를 편하게 사용하려면 같은 패키지에 선언된
`SimpleTimeZone`클래스를 사용하면 된다.

또한, getInstance() 메소드외에 `GregorianCalendar` 라는 클래스도 있다.


```java
	public void makeCalendarObject(){
		Calendar cal=Calendar.getInstance();
		Calendar greCal=new GregorianCalendar();
	} 
```

> 이 중에서 편한방법을 사용하면 되는데, 일반적으로는 greCal 객체처럼 사용하는것을 권장한다.

그러면 Calendar 클래스는 어떻게 사용하면 될까?
Calendar 클래스를 제대로 사용하기 위해서는 API문서에 있는 상수들을 눈여겨 봐야만 한다.

예제를 통해 확인해보자

```java
	public void useCalendar(){
		// Calendar 클래스의 객체를 Calendar 클래스에 선언된 getInstance()메소드로 생성했다.
		Calendar cal=Calendar.getInstance();
        // Calendar 클래스의 객체를 GregorianCalendar 클래스의 생성자를 사용하여 만들었다.
		Calendar greCal=new GregorianCalendar();
		
		System.out.println(cal);
		System.out.println(greCal);
		
		int year=greCal.get(Calendar.YEAR);
		int month=greCal.get(Calendar.MONTH);  //자바의 캘린더에서 월은 1월부터 시작하지 않고, 0부터 시작한다.
		int date=greCal.get(Calendar.DAY_OF_MONTH);
		
		System.out.println(year+"/"+month+"/"+date);
		
        // getDisplayName()메소드를 호출하면 문자열로 표시 가능한 값이 리턴된다(월 요일등...)
		String monthKorea=greCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.KOREA);
		String monthUS=greCal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
		
		System.out.println(monthKorea);
		System.out.println(monthUS);
	}
```

> 출력결과

```java
java.util.GregorianCalendar[time=1447830799240,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Asia/Seoul",offset=32400000,dstSavings=0,useDaylight=false,transitions=22,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2015,MONTH=10,WEEK_OF_YEAR=47,WEEK_OF_MONTH=3,DAY_OF_MONTH=18,DAY_OF_YEAR=322,DAY_OF_WEEK=4,DAY_OF_WEEK_IN_MONTH=3,AM_PM=1,HOUR=4,HOUR_OF_DAY=16,MINUTE=13,SECOND=19,MILLISECOND=240,ZONE_OFFSET=32400000,DST_OFFSET=0]
java.util.GregorianCalendar[time=1447830799240,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Asia/Seoul",offset=32400000,dstSavings=0,useDaylight=false,transitions=22,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2015,MONTH=10,WEEK_OF_YEAR=47,WEEK_OF_MONTH=3,DAY_OF_MONTH=18,DAY_OF_YEAR=322,DAY_OF_WEEK=4,DAY_OF_WEEK_IN_MONTH=3,AM_PM=1,HOUR=4,HOUR_OF_DAY=16,MINUTE=13,SECOND=19,MILLISECOND=240,ZONE_OFFSET=32400000,DST_OFFSET=0]
2015/10/18
11월
Nov
```

> 첫번째 출력결과를 보면, Calendar의 getInstance() 메소드를 호출하면 Calendar 클래스의 객체가 리턴되는 것이 아니라
> GregorianCalendar 객체가 리턴되는 것을 볼 수 있다.

> 여기서 중요한점은 현재 코드의 작성시점은 `2015/11/18`일인데 출력결과는 `2015/10/18`로 나온것이다.

> `Nov`의 경우는 `Calendar.SHORT`로 지정했기 때문이며 `LONG`으로 지정한다면 `December`처럼 전체이름이 나온다.


##### 추가적으로 add()메소드와 roll()메소드를 알아보자

```java
	public void addAndRoll(Calendar calendar, int amount) {
		calendar.add(Calendar.DATE, amount);
		printCalendar(calendar);
		calendar.add(Calendar.DATE, -amount);
		printCalendar(calendar);
		calendar.roll(Calendar.DATE, amount);
		printCalendar(calendar);
	}

	public void printCalendar(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.KOREA);
		int date = calendar.get(Calendar.DATE);
		System.out.println(year + "/" + month + "/" + date);
	}
```

> roll()메소드는 상위값을 변경하지 않는다. 그러므로 날짜만 10일을 더하고 월은 바꾸지 않는다.


### 컬렉션 객체들의 도우미 Collections

앞서 배운 Collection 관련 클래스들을 위한 Collections라는 도우미 클래스가 있다.
이 클래스에 있는 메소드들은 모두 static 메소드다. (즉 객체를 생성할 필요가 없다.)

매우 많은 메소드들이 제공되고 있지만 그중에서 눈여겨 봐야 하는 것은 바로 `synchronize`로 시작하는 메소드들이다.

> 기본적으로 대부분의 Collection 클래스들은 쓰레드에 안전하게 구현되어 있지 않다.
> 따라서, 쓰레드에 안전하지 않은 Collection 클래스의 객체를 생성할 때에는 `synchronize`로 시작하는 메소드를 사용하면
> 쓰레드에 안전한 클래스가 된다.

> 예를 들어 ArrayList는 다음과 같이 객체를 생성해주면 쓰레드에 안전한 클래스가 된다.

```java
List list = Collections.synchronizedList(new ArrayList(...));
```

그렇다고, 무조건 쓰레드에 안전하게 작성하려고 이렇게 변경해버리면 성능에 좋지 않다.
딸사ㅓ, 곡 필요할 때에만 사용할 것을 권장한다.


### 배열을 쉽게 처리해주는 Arrays

Arrays 클래스도 Collections 클래스처럼 도우미 클래스이며, 배열을 쉽게 처리할 수 있도록 도움을 주는 클래스다.

이 중에서 가장 많이 사용되는 sort()메소드와 fill() 메소드만 예제를 통해서 알아보자

```java
import java.util.Arrays;

public class ArraySample {

	public static void main(String[] args) {
		ArraySample sample=new ArraySample();
		sample.checkSort();
	}
	
	public void checkSort(){
		int[] values=new int[]{1,5,3,2,4,7,6,10,8,9};
		Arrays.sort(values);
		String stringValues=Arrays.toString(values);
		System.out.println(stringValues);
	}

}
```

> 이렇게 숫자들이 순서 없이 나열되어 있을 때 어렵게 구현하고 계산할 필요 없이 static으로 선언되어 있는 `Arrays`클래스의 
> `sort()`메소드를 호출하면, 해당 배열의 값이 순서대로 나열된다.

> 추가로 `Arrays.toString()`메소드를 호출하면, 자동으로 배열의 각 항목이 순서대로 나열되어 있는 문자열을 받을 수 있다.
> 이 메소드를 안쓴다면 for나 while등으로 루프돌려서 값을 봐야했을 것이다.


이번에는 특정 값으로 데이터를 채우는 `fill()`메소드를 살펴보자.


```java
	public void checkFill(){
		int[] emptyArray=new int[10];
		Arrays.fill(emptyArray, 1);
        Arrays.fill(emptyArray,0,5,9);
		
		String stringValues=Arrays.toString(emptyArray);
		System.out.println(stringValues);
	}
```

> fill()메소드는 두 가지를 제공한다.
> - 모든 위치에 있는 값들을 하나의 값으로 변경하는 것과 
> - 특정 범위의 값들을 하나의 값으로 변경하는 것이 있다.

> 출력결과는 각각 아래와 같다

```java
[1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
[9, 9, 9, 9, 9, 1, 1, 1, 1, 1]
```


### 임의의 값을 생성하기 위한 Random

난수를 생성한느 Random 클래스는 두개의 생성자가 있다.

- Random()
- Random(Long long)

이중 매개변수로 long을 받는 생성자는 씨드~seed~값을 지정하는 것이다.
씨드값을 지정하게 되면, 해당 Random에서 만들어 낸 값들은 임의의 숫자가 나오긴 하지만, 그 결과는 항상 동일하게 된다.

```java
import java.util.Random;
public class RandomSample {

	public static void main(String[] args) {
		RandomSample sample=new RandomSample();
		int randomCount=10;
		sample.generateRandomNumbers(randomCount);
	}

	public void generateRandomNumbers(int randomCount){
		Random random=new Random();
		for (int loop = 0; loop < randomCount; loop++) {
			System.out.print(random.nextInt(100)+",");
		}
	}

}
```



### 문자열을 자르기 위한 StringTokenizer

"This is a basic java book"

이 문자열을 단어 단위로 분리하려고 하면 어떻게 해야 할까?

> 여러가지 방법이 있지만 일반적인 방법은 `StringTokenizer`를 사용하는 것이다.
> 이 클래스는 어떤 문자열이 일정한 기호(구분자)로 분리되어 있을 때 적합한 클래스다.

다만 String클래스의 Split()메소드보다 이 클래스를 사용하는 것은 권장하지 않는다.
허나 사용된 코드를 볼 수도 있으니 알아는 두자.


```java

import java.util.StringTokenizer;

public class StringTokenizerSample {

	public static void main(String[] args) {
		StringTokenizerSample sample = new StringTokenizerSample();
		String data = "This is a basic java book";
		sample.parseString(data);
	}

	public void parseString(String data) {
		StringTokenizer st = new StringTokenizer(data);
		// StringTokenizer st=new StringTokenizer(data,"a");
		while (st.hasMoreElements()) {
			String tempData = st.nextToken();
			System.out.println("[" + tempData + "]");
		}
	}

	public void parseStringWithSplit(String data) {
		String[] splitString = data.split("\\s");
		for (String tempData : splitString) {
			System.out.println("[" + tempData + "]");
		}
	}
}
```


> 그럼 더 나은 방법이라고했던 String클래스의 split()메소드를 살펴보자

```java
public void parseStringWithSplit(String data) {
	String[] splitString = data.split("\\s");
	for (String tempData : splitString) {
		System.out.println("[" + tempData + "]");
	}
}
```

### java.math 패키지의 BigDecimal

java.util에 선언되어있진 않고, java.math에 선언되어있다.
- 특히 돈 계산과 같은 프로그램을 작성할때 사용된다

```java
import java.math.BigDecimal;

public class BigDecimalSample {
	
	public static void main(String[] args) {
		BigDecimalSample sample = new BigDecimalSample();
		//sample.normalDoubleCalc();
		sample.bigDecimalCalc();
	}
	
	public void normalDoubleCalc() {
		double value = 1.0;
		for (int loop = 0; loop < 10; loop++) {
			value += 0.1;
			System.out.println(value);
		}
	}
	
	public void bigDecimalCalc() {
		BigDecimal value = new BigDecimal("1.0");
		BigDecimal value2 = new BigDecimal(1.0);
		BigDecimal addValue = new BigDecimal("0.1");
		BigDecimal addValue2 = new BigDecimal(0.1);
		for (int loop = 0; loop < 10; loop++) {
			value = value.add(addValue);
			System.out.println(value.toString());
		}
		for(int loop=0;loop<10;loop++) {
			value2 = value2.add(addValue2);
			System.out.println("value2="+value2);
		}
	}
}
```

> 주의 할점은 문자열을 매개변수로 넘겨줘야 한다. ( 그렇지 않으면 0.1이 아닌 근사치로 계산되기때문에..)


---

### 정리해 봅시다

1. `Date`, `Calendar` 클래스가 대표적인 날짜와 시간을 처리하는 클래스이다. 
하지만, `Date 클래스`는 Deprecated된 메소드들이 많아서, `Calendar클래스`를 사용할 것을 권장한다. 
그리고, `Calendar클래스`는 abstract 클래스이기 때문에 보통 `GregorianCalendar 클래스`를 사용한다.

2. Date 클래스의 Deprecated되지 않은 생성자는 Date()와 Date(long date) 두개 뿐이다.

3. getTime() 메소드는 long 형태로 관리되는 시간을 처리하므로, getTime(0)은 1970년 1월 1일 00시를 의미한다. 한국은 GMT + 9 이기 때문에 09시로 출력될 수 있다.

4. Calendar 클래스는 new를 사용하여 객체를 생성하지 않고, getInstance() 메소드를 사용하여 객체를 생성한다.

5. Calendar 클래스에서 1월은 0이고, 12월은 11을 사용한다.

6. Collection 클래스들의 내용을 쉽게 처리하기 위해서 Collections 클래스가 존재한다. 

7. Collections 클래스의 synchronized로 시작하는 메소드들은 Thread에 안전하지 않은 Collection들을 쉽게 안전하게 바꿀 수 있도록 해준다.

8. Arrays 클래스도 Collections 클래스처럼 배열과 관련된 객체들을 쉽게 처리할 수 있도록 만들어졌다. 

9. 자바의 Random 클래스는 임의의 수를 생성하기 위해서 사용한다. 

10. StringTokenizer 클래스의 hasMoreElements() 메소드는 생성된 데이터가 더 존재하는지를 확인하기 위한 메소드로, boolean 타입의 결과를 리턴한다.

11. StringTokenizer 클래스의 nextToken() 메소드는 생성된 데이터에서 다음 데이터를 꺼낼 때 사용한다. 

12. String클래스의 split() 메소드의 기능은 StringTokenizer와 비슷한 역할을 수행한다.

13. Properties 클래스의 load() 메소드는 저장되어 있는 Properties 파일의 내용을 읽어 Properties 객체에 담아준다.

14. 숫자의 정확한 계산을 위한 BigDecimal 클래스는 java.math 패키지에 선언되어 있다. 

15. BigDecimal 클래스에서 값을 더할 때에는 add() 메소드를, 뺄 때에는 subtract() 메소드를, 곱하기는 multiply(), 나누기는 subtract() 메소드를 각각 사용한다.
