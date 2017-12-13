## 5장. 자바랭 다음으로 많이 쓰는 애들은 컬렉션-Part1(List)

자바에서 컬렉션은 목록성 데이터를 처리하는 자료구조를 통칭

#### 자료구조란?
- Data Structure
- 하나의 데이터가 아닌 여러 데이터를 담을 때 사용한다.
- 배열이 가장 기본적인 자료구조다.

#### 자바에서 데이터를 담는 자료구조는 크게 다음과 같이 분류할 수 있다.
- 순서가 있는 목록인 List형
- 순서가 중요하지 않은 목록인 Set형
- 먼저 들어온 것이 먼저 나가는 Queue형
- 키-값(key-value)으로 저장되는 Map형

> 자바에서는 List와 Set, Queue는 Collection이라는 인터페이스를 구현하고 있다.

이 Collection 인터페이스는 java.util 패키지에 선언되어 있으며, 여러 개의 객체를 하나의 개체에 담아 처리할 때 공통적으로 사용되는 여러 메소드들을 선언해 놓았다.

위 목록에서 유일하게 Map만이 Collection과 관련 없는 별도의 인터페이스로 선언되어 있다.

> List와 Set, Queue의 기본이 되는 Collection 인터페이스는 다음과 같이 선언되어 있다.

```java
public interface Collection<E> extends Iterable<E>
```

> `Iterable<E>` 라는 인터페이스를 확장~extends~ 했다는 점.
> 이 `Iterable<E>`인터페이스에 선언되어 있는 메소드는 단지 `iterator()` 하나다.
> 또한 이 메소드는 `Iterator`라는 인터페이스를 리턴한다.

결론적으로, Collection 인터페이스가 Iterable 인터페이스를 확장했다는 의미는, Iterator 인터페이스를 사용하여 데이터를 순차적으로 가져 올 수 있다는 의미다.

---

### List 인터페이스와 그 동생들

배열과 비슷한 List에 대해서 알아보자.

- List 인터페이스는 방금 배운 Collection 인터페이스를 확장하였다.
- 따라서, 추가된 메소드를 제외하고는 Collection에 선언된 메소드와 큰 차이는 없다.
- Collection을 확장한 다른 인터페이스와 List 인터페이스의 가장 큰 차이점은 배열처럼 ++**순서**++가 있다는 것이다.

List 인터페이스를 구현한 클래스들은 매우 많지만 그중 java.util 패키지에서는 ArrayList, Vector, Stack, LinkedList를 많이 사용한다.

이 중에서 ArrayList와 Vector 클래스의 사용법은 거의 동일하고 기능도 거의 비슷하다.
이 두 클래스는 "크기 확장이 가능한 배열"이라고 생각하면 된다.

ArrayList의 객체는 여러 명이 달려들어 값을 변경하려고 하면 문제가 발생할 수 있고, Vector는 그렇지 않다
즉, ArrayList 는Thread safe하지 않고, Vector는 Thread safe하다고 이야기 한다.

Stack 클래스는 Vector 클래스를 확장하여 만들었다.
이 클래스를 만든 가장 큰 이유는 LIFO를 지원하기 위함이다 
LIFO는 Last In First Out의 약자로, 가장 마지막에 추가한 값을 가장 처음 빼내는 것이다.

> 프로그래밍 언어에서 "스택"이라는 의미는 보통 메소드가 호출된 순서를 기억하는 장소를 말한다.

LinkedList라는 클래스는 List에도 속하지만, Queue에도 속한다.

> 참고로 Vector보다 ArraList를 많이 선호한다.

---

### ArrayList에 대해서 파헤쳐보자

> 지금 배우는 컬렉션, 나중에 배울 쓰레드, IO, 네트워크 등의 관련 클래스를 사용할 때에는 한번 정도 그 클래스의 상속 관계를 살펴보자 왜냐하면 그 클래스의 API에 있는 메소드나 상수만 사용할 수 있는 것이 아니고, 부모 클래스에 선언되어 있는 메소드도 사용할 수 있기 때문이다.

우선은 ArrayList 클래스의 상속 관계를 살펴보자.

```java
java.lang.Object
	java.util.AbstractCollection<E>
    	java.util.AbstractList<E>
        	java.util.ArrayList<E>
```

Object를 제외한 나머지 부모 클래스들의 이름 앞에는 `Abstract` 가 붙어있다.
즉, 이 클래스들은 abstract 클래스다.

> abstract 클래스는 일반클래스와 비슷하지만, 몇몇 메소드는 자식에서 구현하라고 abstract로 선언한 메소드들이 있는 클래스

따라서, `AbstractCollection`은 `Collection` 인터페이스 중 일부 공통적인 메소드를 구현해 놓은 것이며, 
`AbstractList`는 `List` 인터페이스 중 일부 공통적인 메소드를 구현해 놓은 것이라고 기억하고 있으면 된다.


> 확장한 클래스 외에 구현한 모든 인터페이스들은 다음과 같다.
```
All Implemented Interfaces:
	Serializable, Cloneable, Iterable<E>, Collection<E>, List<E>, RandomAccess
```

> 이와 같은 인터페이스들을 ArrayList가 구현했다는 것은 각 인터페이스에서 선언한 기능을 ArrayList에서 사용할 수 있다는 말이다.



### ArrayList의 생성자는 3개다

- 앞서 ArrayList는 "크게 확장이 가능한 배열" 이라고 설명했다.
- 따라서, 배열처럼 사용하지만 대괄호는 사용하지 않고, 메소드를 통해서 객체를 넣고, 빼고, 조회한다.

> ArrayList 의 생성자들

- `ArrayList()` : 객체를 저장할 공간이 10개인 ArrayList를 만든다.
- `ArrayList(Collection<? extends E> c)` : 매개 변수로 넘어온 컬렉션 객체가 저장되어 있는 ArrayList를 만든다.
- `ArrayList(int initialCapacity)` : 매개 변수로 넘어온 initialCapacity 개수만큼의 저장 공간을 갖는 ArrayList를 만든다.

역시 설명만으로는 무리다. 예제코드를 보자

```java
import java.util.ArrayList;

public class ListSample
{
	public static void main(String[] args) 
	{
		ListSample sample=new ListSample();
		sample.checkArrayList1();
	}

	public void checkArrayList1(){
		ArrayList list1=new ArrayList();
	}
}
```

> 이렇게 list1 객체를 생성하면, 이 ArrayList에는 어떤 객체도 넣을 수 있다.
> 그리고 데이터를 넣을 때에는 add()라는 메소드를 사용한다. 이를테면 다음처럼~

```java
public void checkArrayList1(){}
	list1.add(new Object());
	list1.add("ArrayListSample");
    list1.add(new Double(1));
}
```

그런데 보통 ArrayList는 이렇게 사용하지 않는다. 
대부분 이렇게 다른 종류의 객체를 하나의 배열에 넣지 않고, 한가지 종류의 객체만 저장한다.

> 여러 종류를 하나의 객체에 담을 때에는 되도록이면 `DTO`라는 객체를 하나 만들어서 담는 것이 좋다.

그래서 , 컬렉션 관련 클래스의 객체들을 선언할 때에는 앞 장에서 배운 제네릭을 사용하여 선언하는 것을 권장한다.
예를 들어 String 만 담는 ArrayList를 생성할때에는 다음과 같이 사용하면 된다.

```java
ArrayList<String> list1=new ArrayList<String>();
```

> 참고로 JDK7부터는 생성자를 호출하는 부분(new 뒤에)에 따로 타입을 적지 않고 `<>`로만 사용해도 되지만 JDK6이하 버전을 위해
> 되도록 위와 같이 사용하자

> 각설하고, 이처럼 list1을 선언해 놓으면, list1에는 String타입의 객체만 넣을 수 있다.

이제 위에서 만들어본 `checkArrayList1`메소드를 제네릭으로 선언한 후 컴파일을 해보자

> 그럼 당연하게도 컴파일에러가 뜬다
> 이렇게 제네릭을 사용하면 컴파일 시점에 타입을 잘못 지정한 부분을 걸러낼 수가 있다.

- ArrayList 객체를 선언할 때 매개변수를 넣지 않으면, 기본 초기 크기는 10이다.
- 따라서 10개 이상의 데이터가 들어가면 크기를 늘이는 작업이 ArrayList내부에서 자동으로 수행된다.
- 이러한 작업은 애플리케이션 성능에 영향을 주니 만약 저장되는 데이터의 크기가 어느정도 예측가능하다면 
- 다음과 같이 예측한 초기 크기를 지정할 것을 권장한다.

```java
ArrayList<String> list2=new ArrayList<String>(100);
```

### ArrayList에 데이터를 담아보자

생성자에 대해서 알아봤으니 ArrayList에 데이터를 담는 메소드를 살펴보자.

- add() : 하나의 데이터를 담을때 사용
- addAll() : Collection을 구현한 객체를 한꺼번에 담을 때에 사용

> 반복해서 설명하지만 ArrayList는 확장된 배열 타입이기 때문에 **배열처럼 처음 순서가 매우중요하다**

ㄱ) add(E e)
- add()메소드에 매개변수하나만을 넘겨서 데이터를 저장하면 배열의 가장 끝에 데이터를 담는다.
- 이 메소드를 사용하여 데이터를 추가했을 때 리턴되는 boolean 값은 제대로 추가가 되었는지 여부를 말한다.

ㄴ) add(int index, E e)
- 지정된 위치에 데이터를 담는다.
- 그러므로, 이 경우에는 지정된 위치에 있는 기존 데이터들은 위치가 하나씩 뒤로 밀려난다.

> 이 내용은 예제 코드로 확인해보자

```java
public void checkArrayList2(){
	ArrayList<String> list=new ArrayList<String>();
	list.add("A");
	list.add("B");
	list.add("C");
	list.add("D");
	list.add("E");
	list.add(1,"A1");
    
    for (String tmp:list){
		System.out.println(tmp);
	}
}
```

> 출력결과

```java
A
A1
B
C
D
E
```

ㄷ) addAll()
바로 예제를 통해 살펴보자

```java
	public void checkArrayList2(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add(1,"A1");

		ArrayList<String> list2=new ArrayList<String>();
		list2.add("0 ");
		list2.addAll(list);
		for (String tmp:list2){
			System.out.println("List2 "+tmp);
		}
	}
```

> 출력결과

```java
List2 0 
List2 A
List2 A1
List2 B
List2 C
List2 D
List2 E
```

방금 사용한 예제메소드에 새로운 객체를 만들고 `addAll()`메소드를 사용해 값을 추가했다.

> 위와 같이 list의 값을 list2에 복사해야 할 일이 생긴다면, 반드시 위처럼 할필요 없이 다음과 같이 생성자를 사용하면 편하다.

```java
ArrayList<String> list2=new ArrayList<String>(list);
```

> 어떻게 이게 가능해??? 라는 생각이 들지만
ArrayList에는 Collection 인터페이스를 구현한 어떠한 클래스도 포함시킬 수 있는 생성자가 있기 때문이다.

> 여기서 한가지 짚고 넘어갈 것이 있다.
> 도대체 왜 Collection을 매개변수로 갖는 생성자와 메소드가 존재할까?

> 이는 자바를 개발하다 보면 매우 다양한 타입의 객체를 저장하게되는데 모든 개발자들이 ArrayList만을 사용하는것도 아니고
> 앞으로 배울 `Set`과 `Queue`를 사용하여 데이터를 담을 수도 있으므로 이와 같은 생성자와 메소드를 제공하는 것이다.

한가지 더 알아볼 것이 있다 에제를 보자

```java
	public void checkArrayList2(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add(1,"A1");
	
		ArrayList<String> list2=list; //치환 수행
		list.add("Ooops");
		for (String tmp:list2){
			System.out.println("List2 "+tmp);
		}
	}
```

> 출력결과

```java
List2 A
List2 A1
List2 B
List2 C
List2 D
List2 E
List2 Ooops
```

> 분명 list2객체에 "Ooops"라는 데이터를 저장한 일이 없는게 이게 뭔일일까?

```java
list2=list
```
> 위의 문장은 list2가 list의 값만 사용하겠다는 것이 아니다.
> list라는 객체가 생성되어 참조되고 있는 주소까지도 사용하겠다는 말이다.

- 자바의 모든 객체가 생성되면 그 객체가 위치하는 주소가 내부적으로 할당된다.
- 따라서 list2=list라고 문장을 작성하게 되면, 두 객체의 변수 이름은 다르지만, 하나의 객체가 변경되면 다른 이름의 변수를 갖는 객체도 바뀐다.


> 따라서, 하나의 Collection관련 객체를 복사할 일이 있을 때에는 이와 같이 생성자를 사용하거나, addAll()메소드를 쓰자

### ArrayList에서 데이터를 꺼내자

ArrayList 객체에서 데이터를 꺼내오는 법을 알아보기전에 먼저 알아봐야 할 메소드가 있다.
바로 ArrayList 객체에 들어가 있는 데이터의 개수를 가져오는 `size()` 메소드다.
배열에 넣을 수 있는 공간의 개수를 가져올 때에는 `배열.length`를 사용한다.(String문자열 길이도 마찬가지)

> 하지만 Collecttion을 구현한 인터페이스는 size()메소드를 통하여 데이터 개수를 확인한다. 
> 이때 리턴 타입은 `int`!!!

`배열.length`는 배열의 저장 공간 개수를 의미하지만, `size()`메소드의 결과는 데이터 개수를 의미한다.

```java
	public void checkArrayList3(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		int listSize=list.size();

		for(int loop=0; loop<listSize; loop++){
			System.out.println("list.get("+loop+")="+list.get(loop));
		}
	}
```

> 위와 같은 for 루프 기본방식으로 ArrayList 객체에 있는 값을 가져올 때에는 `get()`메소드를 사용한다.

한 건의 데이터를 꺼내는 메소드는 이 get()메소드 뿐이다.
그리고, 위치로 데이터를 꺼내는 `get()`메소드와는 반대로 데이터로 위치를 찾아내는 `indexOf()`라는 메소드와 `lastIndexOf()`
라는 메소드도 있다. 

> indexOf()와 lastIndexOf()메소드가 있는 이유가 뭘까?

ArrayList는 중복된 데이터를 넣을 수 있다.
즉, 0번째에도 `A` 1번째에도 `A` 가 들어있을 경우를 말한다.

> 그런데 간혹 ArrayList객체에 있는 데이터들을 배열로 뽑아낼 때도 있다. 그럴때에는 `toArray()` 메소드를 사용하면 된다.

> 여기서 중요한점은 매개변수가 없는 `toArray()`메소드는 `Object`타입의 배열로만 리턴을 한다는 것이다.
> 그러므로, 제네릭을 사용하여 선언한 ArrayList객체를 배열로 생성할때에는 이 메소드를 사용하는 것은 좋지 않다.
> 그러니 이메소드는 `toArray(T[] a)` 형식으로 사용하자

```java
String[] strList=list.toArray(new String[0]);
```

이와 같이 toArray() 메소드의 매개 변수로 변환하려는 타입의 배열을 지정해주면 된다.

- 매개변수로 넘기는 배열은 그냥 이와 같이 의미없이 타입만을 지정하기 위해서 사용할 수도 있다.
- 그런데 실제로는 매개변수로 넘긴 객체에 값을 담아준다.
- 하지만, ArrayList객체의 데이터 크기가 매개 변수로 넘어간 배열 객체의 크기보다 클 경우에는 매개 변수로 배열의 모든 값이
`null`로 채워진다.

```java
	public void checkArrayList3(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		
		String[] tempArray=new String[5];
		String[] strList=list.toArray(tempArray);
		for(String tmp:strList){
			System.out.println(tmp);
		}
	}
```

> 출력결과

```java
A
B
C
D
E
```

> 이번엔 tempArray 배열의 크기를 `[7]`로늘려서 테스트 해보자 (앞 서 말했던 null로 채워지는 문제를 확인하기위해)

```java
// 생략
	String[] tempArray=new String[7];
	String[] strList=list.toArray(tempArray);
	for(String tmp:strList){
		System.out.println(tmp);
	}
```

> 출력결과

```java
A
B
C
D
E
null
null
```

> 이번에는 반대로 `[2]`로 줄여서 테스트해보자 

```java
	String[] tempArray=new String[2];
	String[] strList=list.toArray(tempArray);
	for(String tmp:tempArray){
		System.out.println(tmp);
	}
```

> 출력결과

```java
null
null
```

> 이처럼 ArrayList에 저장되어 있는 데이터의 크기보다 매개변수로 넘어온 배열의 크기가 작으면 새로운 배열을 생성하여 넘겨주므로,
> 가장 처음에 사용한 것과 같이 크기가 `0`인 배열을 넘겨주는 것이 가장 좋다.

### ArrayList에 있는 데이터를 삭제하자

- clear() : 모든 데이터를 삭제
- remove(int index) : 매개 변수에서 지정한 위치에 있는 데이터를 삭제하고, 삭제한 데이터를 리턴
- remove(Object o) : 매개 변수에 넘어온 객체와 동일한 첫번째 데이터를 삭제
- removeAll(Collection<?> c) : 매개 변수로 넘어온 컬렉션 객체에 있는 데이터와 동일한 모든 데이터를 삭제


객체를 넘겨주는 `remove()`와 컬렉션 객체를 넘겨주는`removeAll()`은 비슷해보이지만 약간 다르다.
`remove()` 메소드는 매개 변수로 넘어온 객체와 동일한 첫번째 데이터만 삭제한다.
`removeAll()`메소드는 매개 변수로 넘어온 컬렉션에 있는 데이터와 동일한 모든 데이터를 삭제한다.

```java
	public void checkArrayList4(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("A");

		System.out.println("Removed "+list.remove(0));

		for(int loop=0;loop<list.size();loop++){
			System.out.println("list.get("+loop+")="+list.get(loop));
		}
	}
```

> 출력결과

```java
Removed A
list.get(0)=B
list.get(1)=C
list.get(2)=D
list.get(3)=E
list.get(4)=A
```

이번에는 메소드 삭제부분을 변경해서 테스트 해보자

```java
//System.out.println("Removed "+list.remove(0));
System.out.println(list.remove("A"));
```

> 출력결과

```java
true
list.get(0)=B
list.get(1)=C
list.get(2)=D
list.get(3)=E
list.get(4)=A
```

결과를 보면 A값을 삭제하고 그 결과인 true값이 리턴됬지만 마지막에 있는 `A`는 삭제되지 않았다.

> 다시한번 코드를 수정해보자

```java
ArrayList<String> temp=new ArrayList<String>();
temp.add("A");
list.removeAll(temp);
```

> 출력결과

```java
list.get(0)=B
list.get(1)=C
list.get(2)=D
list.get(3)=E
```

> `A`라는 값을 갖는 모든 데이터가 사라진 것을 볼 수 있다.

마지막으로 `set()` 메소드는 값을 변경하는 메소드다 사용법은 비슷하므로 직접 테스트 해보자




---

### Stack 클래스는 뭐가 다른데?

List 인터페이스를 구현한 또 하나의 클래스인 Stack 클래스에 대해서 살펴보자.
- LIFO 기능을 구현하려고 할 때 필요한 클래스

> LIFO는 간단하게 말해서 후입선출

- 이 클래스보다 빠른 `ArrayDeque`라는 클래스가 있다 ( 다만 이클래스는 쓰레드에 안전하지 못하다.)
- Stack 클래스의 부모 클래스는 `Vector`다 
- Stack 클래스에서 구현한 인터페이스는 ArrayList 클래스에서 구현한 인터페이스와 모두 동일하다
- 상속을 잘못 받은 클래스지만 하위호환성을 위해서 이 상속관계를 유지하고 있다고 생각하자.
- 생성자가 `Stack()` 하나다. (매개변수가 없다는 말인듯)

> Stack 클래스의 메소드는 API를 참고하자


---


### 직접해 봅시다.

```java
import java.util.ArrayList;

public class ManageHeight
{
	
	public static void main(String[] args) 
	{
		ManageHeight sample=new ManageHeight();
		sample.setData();

		for (int loop=1;loop<6 ;loop++ )
		{
			//sample.printHeight(loop);
			sample.printAverage(loop);
		}
		
	}

	ArrayList<ArrayList<Integer>> gradeHeights = new ArrayList<ArrayList<Integer>>();

	public void setData(){

		ArrayList<Integer> list1=new ArrayList<Integer>();
		list1.add(170);
		list1.add(180);
		list1.add(173);
		list1.add(175);
		list1.add(177);

		ArrayList<Integer> list2=new ArrayList<Integer>();
		list2.add(160);
		list2.add(165);
		list2.add(167);
		list2.add(186);
		
		ArrayList<Integer> list3=new ArrayList<Integer>();
		list3.add(158);
		list3.add(177);
		list3.add(187);
		list3.add(176);

		ArrayList<Integer> list4=new ArrayList<Integer>();
		list4.add(173);
		list4.add(182);
		list4.add(181);
		
		ArrayList<Integer> list5=new ArrayList<Integer>();
		list5.add(170);
		list5.add(180);
		list5.add(165);
		list5.add(177);
		list5.add(172);

		gradeHeights.add(list1);
		gradeHeights.add(list2);
		gradeHeights.add(list3);
		gradeHeights.add(list4);
		gradeHeights.add(list5);
	}

	public void printHeight(int classNo){
		
		ArrayList<Integer> classHeight = gradeHeights.get(classNo -1);
		System.out.println("classNo = "+classNo);

		for(int tempArr: classHeight){
			System.out.println(tempArr);
		}
		System.out.println();
	}

	public void printAverage(int classNo){
		ArrayList<Integer> classAverage = gradeHeights.get(classNo -1);

		System.out.println("Class No. : "+classNo);

		double sum=0;
		for(int tempArr:classAverage){
			sum += tempArr;
		}
		int count = classAverage.size();

		System.out.println("Height average : "+sum/count);
	}


}
```



--- 


### 정리해 봅시다.

1. Collection 인터페이스를 구현한 대표적인 타입은 List, Set, Queue 이다. 

2. 배열과 같은 형태는 List 인터페이스에서 선언되어 있다.

3. 별도로 정하지 않을 경우 자바에서 제공하는 List 를 구현한 클래스의 데이터 개수는 10개이다.

4. ArrayList(int initialCapacity) 를 사용하면 초기 데이터 개수를 생성과 동시에 지정할 수 있다.

5. 제네릭을 사용하면 컴파일 시점에 타입을 잘못 지정한 부분을 걸러낼 수가 있기 때문이다.

6. add()와 addAll()메소드를 사용하면 ArrayList에 데이터를 담을 수 잇다.

7. 만약 String타입을 담는 list라는 ArrayList를 만들었다면
```java
for(String data:list) { 
//
}
```
와 같이 사용하면 된다.

8. size() 메소드를 사용하면 Collection을 구현한 클래스들에 들어 있는 데이터 개수를 확인할 수 있다. 

9. get() 메소드를 사용하면 매개변수로 넘긴 위치에 있는 값을 리턴한다.

10. remove() 메소드를 사용하면 매개변수로 넘긴 위치에 있는 값을 삭제한다. 만약 매개변수로 객체를 넘기면, 동일한 첫번째 객체를 삭제한다. 

11. set() 메소드를 사용하면 첫번째 매개변수로 넘긴 위치에 있는 값은 두번째 매개변수로 넘긴 값으로 대체한다.

12. Stack 클래스는 List 인터페이스를 구현하였다.

13. Stack 클래스에 데이터를 담을 때에는 push() 메소드를 사용한다. 

14. Stack 클래스의 peek() 메소드는 가장 위에 있는 값을 리턴만한다.

15. Stack 클래스의 pop() 메소드는 가장 위에 있는 데이터를 지우고 리턴한다.
