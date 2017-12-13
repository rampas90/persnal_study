## 6장. 자바랭 다음으로 많이 쓰는 애들은 컬렉션-Part2(Set과 Queue)

### Set이 왜 필요하지?

- Set은 순서에 상관없이, 어떤 데이터가 존재하는지를 확인하기 위한 용도로 많이 사용된다. 
- 다시 말해서 중복되는 것을 방지하고, 원하는 값이 포함되어 있는지를 확인하는 것이 주 용도다
- 어떤 값이 존재하는지, 없는지 여부몬 중요할 때 Set을 사용하면 된다.

자바에서 Set인터페이스를 구현한 주요 클래스는 HashSet, TreeSet, LinkedHashSet이 있다.

- HashSet : 순서가 전혀 필요 없는 데이터를 해시 테이블에 저장한다. Set중에 가장 성능이 좋다.
- TreeSet : 저장된 데이터의 값에 따라서 정렬되는 셋이다. 
red-black 이라는 트리 타입으로 값이 저장되며, HashSet보다 약간 성능이 느리다
- LinkedHashSet : 연결된 목록 타입으로 구현된 해시 테이블에 데이터를 저장한다.
저장된 순서에 따라서 값이 정렬된다. 대신 성능이 이 셋 중에서 가장 나쁘다.

> 이러한 성능 차이가 발생하는 이유는 데이터 정렬 때문이다. HashSet은 별도의 정렬작업이 없어 제일 빠르다.
> 하지만 몇백 만 건을 처리하지 않는 이상 성능차이를 체감하기는 힘들 것이다.


해시 테이블은 다음장에서 자세히 살펴보도록 하고, red-black 트리는 각 노드의 색을 붉은 색 혹은 검은색으로 구분하여
데이터를 빠르고, 쉽게 찾을 수 있는 구조의 이진 트리를 말한다.

> [red-black 트리 참고 위키디피아 바로가기](http://ko.wikipedia.org/wiki/레드-블랙_트리)


### HashSet에 대해서 파헤쳐 보자

먼저 상속관계부터 보자

```java
java.lang.Object
	java.util.AbstractCollection<E>
		java.util.AbstractSet<E>
			java.util.HashSet<E>
```

- `AbstractSet` 을 확장했으며 이 클래스(AbstractSet)는 3개의 메소드만 구현이 되어있다.
- Object 클래스에 선언되어 있는 메소드는 `equals()`, `hashCode()`, `removeAll()` 세가지다
- Set은 무엇보다도 데이터가 중복되는 것을 허용하지 않으므로, 데이터가 같은지를 확인하는 작업은 Set의 핵심이다.
- `equals()`메소드는 `hashCode()`메소드와 떨어질 수 없는 불가분의 관계다.
- `equals()`메소드와 `hashCode()`메소드를 구현하는 부분은 Set에서 매우 중요하다.


### HashSet의 생성자들도 여러종류가 있다.

- HashSet 클래스에는 다음과 같이 4개의 생성자가 존재한다.

> 로드팩터~loadfactor~라는 것은 뭘까?

로드팩터는 (데이터의개수)/(저장공간)을 의미한다.
로드 팩터라는 값이 클수록 공간은 넉넉해지지만, 데이터를 찾는 시간은 증가한다.
따라서, 초기 공간 개수와 로드팩터는 데이터의 크기를 고려하여 산정하는 것이 좋다.
왜냐하면, 초기크기가 (데이터의 개수)/(로드팩터)보다 클 경우에는 데이터를 쉽게 찾기 위한 해시 재정리 작업이 발생하지 않기 때문이다.

따라서, 대량의 데이터를 여기에 담아 처리할 때에는 초기 크기와 로드팩터의 값을 조절해 가면서 가장 적당한 크기를 찾아야 한다.


### HashSet의 주요 메소드를 살펴보자

- 선언되어 있는 메소드는 그리 많지 않다.
- 부모 클래스인 AbstractSet과 AbstracCollectioin에 선언 및 구현되어 있는 메소드를 그대로 사용하는 경우가 많다.


```java
import java.util.HashSet;                                                                 

public class SetSample 
{
	public static void main(String[] args) 
	{
		SetSample sample=new SetSample();
		String[] cars = new String[] { 
			"Tico", "Sonata", "BMW", "Benz", "Lexus",
			"Zeep", "Grandeure", "Morning", "Mini Cooper",
				"i30", "BMW", "Lexus", "Carnibal", "SM5", "SM7", "SM3", "Tico" 
		};
		System.out.println(sample.getCarKinds(cars));
	}

	public int getCarKinds(String[] cars){
		
		if(cars==null)
			return 0;

		if(cars.length==1) 
			return 1;
		
		HashSet<String> carSet=new HashSet<String>();

		for(String car:cars){
			carSet.add(car);
		}
		return carSet.size();
	}
}
```


> 출력결과

```java
14
```

> 이렇게 HashSet과 같은 set을 사용하면 여러 중복되는 값들을 걸러내는 데 매우 유용하다.
> 그러면 HashSet에 저장되어 있는 값을 어떻게 꺼낼까? 가장 편한 방법은 for루프를 사용하는것이다.

```java
	public int getCarKinds(String[] cars){
		
		if(cars==null)
			return 0;

		if(cars.length==1) 
			return 1;
		
		HashSet<String> carSet=new HashSet<String>();

		for(String car:cars){
			carSet.add(car);
		}
		printCarSet(carSet);
		return carSet.size();
	}
	public void printCarSet(HashSet<String> carSet){
		for(String temp:carSet){
			System.out.print(temp+" ");
		}
		System.out.println();
	}
    
```

> 출력결과

```java
Carnibal Sonata Lexus BMW Tico Mini Cooper Zeep Benz Morning i30 SM7 Grandeure SM5 SM3 
14
```

> 결과를 보면 저장한 순서대로가 아닌 뒤죽박죽으로 출력된다. 순서가 중요하지 않을때 사용해야 한다.

---

### Queue는 왜 필요할까?

앞 장에서 간단히 소개만 된 `LinkedList`라는 클래스가 있다.
- LinkedList는 자료구조에 대해서 배울때 중요한 항목중 하나이기 때문에 꼭 기억하고 있어야만 한다.
- 앞에있는 애와 뒤에 있는 애만 기억한다. (열차를 연상해서 기억하자)

> 그럼 배열과 차이점은 뭘까?

배열의 중간에 있는 데이터가 지속적으로 삭제되고, 추가될 경우에는 LinkedList가 배열보다 메모리 공간 측면에서 훨씬 유리하다.

- LinkedList는 List인터페이스뿐만 아니라 Queue와 Deque 인터페이스도 구현하고 있다
- 즉, LinkedList 자체가 List이면서도 Queue, Deque도 된다.

Queue 는 Stack클래스와 반대되는 개념인 FIFO이다(선입선출)

> 그럼 큐는 왜 사용할까?

사용자의 요청을 들어온 순서대로 처리할 때 큐를 사용한다.

LinkedList 클래스가 구현한 인터페이스 중에서 JDK 6에서 추가된 Deque 라는 것도 있다.
- Double Ended Queue 의 약자이며, Deque 는 Queue 인터페이스를 확장하였기 때문에, Queue 의 기능을 전부 포함한다.
- 대신 맨앞에 값을 넣거나 빼는 작업, 맨뒤에 값을 넣거나 빼는 작업을 수행한느데 용이하도록 되어있다


### LinkedList를 파헤쳐보자

> LinkedList 의 상속관계

```java
java.lang.Object
	java.util.AbstractCollection<E>
		java.util.AbstractList<E>
			java.util.AbstractSequentialList<E>
				java.util.LinkedList<E>
```

- 앞에서 살펴본 것처럼 LinkedList 클래스는 List도 되고 Queue도 된다.
- 두 인터페이스의 기능을 모두 구현한 특이한 클래스라고 볼 수 있다. 게다가 Deque인터페이스도 구현한다.



### LinkedList의 생성자의 주요 메소드를 살펴보자

- 일반적인 배열타입의 클래스와 다르게 생성자로 객체를 생성할 때 처음부터 크기를 지정하지 않는다.
- 왜냐하면 각 데이터들이 앞 뒤로 연결되는 구조이기 때문에, 미리 공간을 만들어 놓을 필요가 없는 것이다.
- 따라서 다음의 두가지 생성자만 존재한다.
	- LinkedList() : 비어있는 LinkedList 객체를 생성한다.
	- LinkedList(Collection<? extends E> c) : 매개 변수로 받은 컬렉션 객체의 데이터를 LinkedList에 담는다.
- 구현한 인터페이스의 종류가 매우 많은 만큼 메소드 종류도 다양하다. (중복되는 기능을 구현한 메소드들도 많다.)


```java

import java.util.LinkedList;

public class QueueSample
{
	public static void main(String[] args) 
	{
		QueueSample sample=new QueueSample();
		sample.checkLinkedList1();
	}

	public void checkLinkedList1(){
		LinkedList<String> link=new LinkedList<String>();
		
		link.add("A");
		link.addFirst("B");
		System.out.println(link);
		link.offerFirst("C");
		System.out.println(link);
		
		link.addLast("D");
		System.out.println(link);
		link.offer("E");
		System.out.println(link);
		link.offerLast("F");
		System.out.println(link);
		link.push("G");
		System.out.println(link);
		link.add(0, "H");
		System.out.println(link);
		System.out.println("EX=" + link.set(0, "I"));
		System.out.println(link);
	}
}
```

> 출력결과

```java
[B, A]
[C, B, A]
[C, B, A, D]
[C, B, A, D, E]
[C, B, A, D, E, F]
[G, C, B, A, D, E, F]
[H, G, C, B, A, D, E, F]
EX=H
[I, G, C, B, A, D, E, F]
```

> 보다시피 같은 기능을 하는 메소드들이 많다... 그렇다면 이중에서 어떤 메소드를 대표적으로 사용하는게 좋을까?

실제 자바의 LinkedList 소스를 보면 맨 앞에 추가하는 메소드는 동일한 기능을 수행하는 어떤 메소드를 호출해도 addFirst()메소드를,
offer()관련 메소드를 사용하면 add()나 addLast()메소드를 호출하도록 되어있다.

따라서 되도록 add가 붙은 메소드를 사용해서 오해의 소지를 줄이자!

> 특정 위치의 데이터를 꺼내는 메소드들 역시 위와 비슷한데 예를 들면 맨앞에 있는 데이터를 가져올땐 그냥 getFirst()를 사용하자

> 비슷한 이유로 삭제는 remove_가 붙은 메소드를 사용하자


#### 정리를 해보자면 

`Set`은 주로 중복되는 데이터를 처리하기 위해 사용되며,
`Queue`는 먼저 들어온 데이터를 먼저 처리해주는 FIFO기능을 처리하기 위해서 사용한다.
특히 `Queue`는 여러 쓰레드에서 들어오는 작업을 순차적으로 처리할 때 많이 사용된다.



---

### 정리해 봅시다.

1. Set 인터페이스는 데이터의 순서와 상관없이 데이터를 담을 때 사용한다.

2. HashSet도 ArrayList처럼 int를 매개변수로 갖는 생성자를 통하여 데이터 저장 공간을 명시적으로 지정할 수 있다. 

3. HashSet에 데이터를 담는 메소드는 add()이다.

4. HashSet의 contains() 메소드를 사용하면 매개변수로 넘긴 값이 존재하는지 확인할 수 있다.

5. HashSet의 데이터를 지우는 메소드는 remove()이다.

6. FIFO는 First In First Out의 약자로 처음 들어온 값이 먼저 나간다는 것을 의미한다.

7. Deque는 Double Eneded Queue의 약자이다. 

8. LinkedList는 List 인터페이스뿐만 아니라 Queue와 Deque 인터페이스도 구현하고 있다. 즉, 목록형 클래스이기도 하면서 큐의 역할도 수행할 수 있다.







