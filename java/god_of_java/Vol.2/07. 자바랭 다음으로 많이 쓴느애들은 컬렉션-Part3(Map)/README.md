## 7장. 자바랭 다음으로 많이 쓰는 애들은 컬렉션-Part3(Map)

### Map이란?

`Map`은 `키` 와 `값`으로 이루어져 있다.
- 모든 데이터는 키와 값이 존재한다.
- 키가 없이 값만 저장될 수는 없다.
- 값이 없이 키만 저장할 수도 없다.
- 키는 해당 Map에서 고유해야만 한다.
- 값은 Map에서 중복되어도 전혀 상관없다.
- Map은 java.util 패키지의 Map이라는 이름의 인터페이스로 선언되어 있고, 구현해 놓은 클래스들도 많이 있다.

> 여러가지 메소드가 있지만 꼭 기억해야할 메소드는 `put()`, `get()`, `remove()` 가 있다


### Map을 구현한 주요 클래스들을 살펴보자

Map 인터페이스를 구현한 클래스들은 매우 다양하고 많다.
그 중에서 HashMap, TreeMap, LinkedHasMap 등이 가장 유명하고 애용하는 클래스다

그리고, Hashtable 클래스라는 것도 있는데, Map인터페이스를 구현하기는 했지만 일반적인 Map인터페이스를 구현한 클래스들과는 다르다
- Map은 컬렉션 뷰를 사용하지만, Hashtable은 Enumeration객체를 통해서 데이터를 처리한다.
- Map은 키,값, 키-값 쌍으로 데이터를 순환하여 처리할 수 있지만, Hashtable은 이중에서 키-값 쌍으로 데이터를 순환하여 처리할 수 없다.
- Map은 이터레이션을 처리하는 도중에 데이터를 삭제하는 안전한 방법을 제공하지만, Hashtable은 그러한 기능을 제공하지 않는다.

또한, HashMap 클래스와 Hashtable 클래스는 다음과 같은 차이가 있다.

| 기능 | HashMap | Hashtable
|--------|--------|
| 키나 값에 nul 저장 가능 여부 | 가능 | 불가능
| 여러 쓰레드에 동시 접근 가능 여부 | 불가능 | 가능


### HashMap 클래스에 대해서 자세히 알아보자

> HashMap의 상속관계
```java
java.lang.Object
	java.util.AbstractMap<K,V>
		java.util.HashMap<K,V>
```
- 대부분의 주요 메소드는 부모 클래스인 AbstractMap 클래스가 구현해 놓았다.

##### HashMap의 생성자는 4개가 있다.

이중 HashMap객체를 생성할 때에는 대부분 매개변수가 없는 생성자를 사용한다.
하지만, 담을 데이터 개수가 많은 경우에는 초기 크기를 지정해주는 것을 권장한다.

> HashMap의 키는 기본자료형과 참조자료형 모두 될 수 있다.
> 그래서 보통은 int나 long같은 숫자나 String클래스를 키로 많이 사용한다.
> 하지만, 직접 어떤 클래스를 만들어 그 클래스를 키로 사용할 때에는 Object클래스의 hashCode()메소드와 equals()메소드를 잘 구현해 놓아야만 한다.



### HashMap 객체에 값을 넣고 확인해보자

Map에서는 데이터를 추가한다고 표현하지 않고, 데이터를 넣는다고 표현한다.
따라서 `add()`가 아닌 `put()`이라는 메소드를 사용한다.

```java
import java.util.HashMap;
public class MapSample {

	public static void main(String[] args) {
		MapSample sample=new MapSample();
		sample.checkHashMap();

	}
	
	public void checkHashMap(){
		HashMap<String, String> map=new HashMap<String,String>();
		map.put("A", "a");
		System.out.println(map.get("A"));
		System.out.println(map.get("B"));
	}

}

```

> 출력결과

```java
a
null
```

Collection에서는 해당 위치에 값이 없을 때에는 `ArrayIndexOutOfBoundsException`이라는 예외가 발생하지만
Map에서는 `null`을 리턴한다.

> 그렇다면 HashMap 객체에 put()메소드를 사용하여 이미 존재하는 키로 값을 넣을때에는 어떻게 될까?

ArrayList 클래스가 add(), set()으로 이용했던것과는 달리
HashMap과 같이 Map관련 클래스에서는 새로운 값을 추가하거나, 수정할 때 모두 put()을 사용한다.



### HashMap 객체의 값을 확인하는 다른 방법들을 알아보자

HashMap에 어떤 키가 있는지를 확인하려면 어떻게 해야 할까?

그럴때 사용하는 것이 `keySet()`메소드다.

> 메소드 이름에서 알 수 있듯이 keySet()메소드의 리턴 타입은 `Set`이다 
> 그러므로, Set의 제네릭 타입은 "키"의 제네릭 타입과 동일하게 지정해 주면 된다.

```java
import java.util.Collection;
import java.util.Set;
import java.util.HashMap;

public class MapSample {

	public static void main(String[] args) {
		MapSample sample=new MapSample();
		sample.checkHashMap();

	}

	public void checkHashMap(){
		HashMap<String, String> map=new HashMap<String,String>();
		map.put("A", "a");

		//System.out.println(map.get("A"));
		//System.out.println(map.get("B"));

		map.put("C", "c");
		map.put("D", "d");
		Set<String> keySet=map.keySet();
		for (String tempKey : keySet) {
			System.out.println(tempKey+"="+map.get(tempKey));
		}

	}
}

```

> 출력결과

```java
D=d
A=a
C=c
```

> 위 결과와 달리 "값"만 필요할 경우에는 이렇게 keySet() 메소드를 사용하여 키목록을 얻어내고, 하나 하나 받아올 필요는 없다.
> 왜냐하면, values()라는 메소드가 있기 때문이다.

```java
import java.util.Collection;
import java.util.Set;
import java.util.HashMap;

public class MapSample {

	public static void main(String[] args) {
		MapSample sample=new MapSample();
		sample.checkHashMap();

	}
	public void checkHashMap(){
		HashMap<String, String> map=new HashMap<String,String>();
		map.put("A", "a");
		map.put("C", "c");
		map.put("D", "d");

		Collection<String> values=map.values();
		for (String tempValue : values) {
			System.out.println(tempValue);
		}
	}
}

```
> values()라는 메소드를 사용하면 HashMap에 담겨 있는 값의 목록을 Collection 타입의 목록으로 리턴해준다.
> Map에 저장되어 있는 모든 값을 출력할 때에는 values()메소드를 사용하는 것이 편하다.

> 이렇게 데이터를 꺼내는 방법 외에 entrySet()메소드를 사용하는 방법도 있다.

```java
public void checkHashMapEntry(){
	HashMap<String,String> map=new HashMap<String,String>();
	map.put("A", "a");
	map.put("B", "b");
	map.put("C", "c");
	map.put("D", "d");

	Set<Entry<String,String>> entries=map.entrySet();
	for (Entry<String,String> tempEntry : entries) {
		System.out.println(tempEntry.getKey()+"="+tempEntry.getValue());
	}
}
```

> 출력결과

```java
D=d
A=a
B=b
C=c
```

이번에는 Map에 어떤 키나 값이 존재하는지를 확인하는 `containsKey()`와 `containsValue()`메소드에 대해 살펴보자


```java
	public void checkHashMapEntry2() {
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("A", "a");
		map.put("B", "b");
		map.put("C", "c");
		map.put("D", "d");

		System.out.println(map.containsKey("A"));
		System.out.println(map.containsKey("Z"));
		System.out.println(map.containsKey("a"));
		System.out.println(map.containsKey("z"));
	}
```

> 출력결과


```java
true
false
true
false
```



### 정렬된 키의 목록을 원한다면 TreeMap을 사용하자

만약 HashMap 객체의 키를 정렬하려면 어떻게 해야 할까?

- 가장 간단한 방법은 Arrays 라는 클래스를 사용하는 것이다. 하지만 불필요한 객체가 생긴다는 단점이 있다.

이러한 단점을 보완하는 TreeMap이라는 클래스가 있다.
- `TreeMap`은 저장하면서, 키를 정렬한다.
- 정렬되는 기본적인순서는 "숫자> 알파벳 대문자 > 소문자 > 한글" 순이다.
- 이는 String(문자열)이 저장되는 순서를 말하며, 객체나 숫자가 저장될때는 그 순서가 달라진다.

```java
public void checkTreeMap(){

	TreeMap<String,String> map=new TreeMap<String,String>();
	map.put("A", "a");
	map.put("B", "b");
	map.put("C", "c");
	map.put("D", "d");

	map.put("가", "e");
	map.put("1", "f");
	map.put("a", "g");

	Set<Entry<String,String>> entries=map.entrySet();
	for (Entry<String,String> tempEntry : entries) {
		System.out.println(tempEntry.getKey()+"="+tempEntry.getValue());
	}
}
```

> 출력결과

```java
1=f
A=a
B=b
C=c
D=d
a=g
가=e
```

> 이와 같이 TreeMap은 키를 정렬하여 저장함을 알 수 있다.
> 따라서, 매우 많은 데이터를 TreeMap을 이용하여 보관하여 처리할 때에는 HashMap보다는 느릴 것이다.
> 하지만 100건, 1,000건 정도의 데이터를 처리하고, 정렬을 해야 할 필요가 있다면 HashMap보다는 TreeMap을 사용하는 것이 더 유리하다

> 이렇게 TreeMap이 키를 정렬하는 것은 SortedMap 인터페이스를 구현했기 때문이다.


### Map을 구현한 Properties 클래스는 알아두면 편리하다.

System 클래스에 대해서 살펴보면서, Properties라는 클래스가 있다고 간단하게 소개했었다.
이 클래스는 Hashtable 클래스를 확장 했다고 살펴봤었는데
따라서, Map인터페이스에 제공하는 모든 메소드를 사용할 수 있다.

> 기본적으로 자바에서는 시스템의 속성을 이 클래스르 사용하여 확인할 수 있다.

그럼 시스템의 속성 값들을 확인하는 방법을 살펴보자

```java
public void checkProperties() {
	Properties prop = System.getProperties();
	Set<Object> keySet = prop.keySet();
	for (Object tempObject : keySet) {
		System.out.println(tempObject + "=" + prop.get(tempObject));
	}
}
```

> 출력결과

```java
java.runtime.name=Java(TM) SE Runtime Environment
sun.boot.library.path=C:\Program Files\Java\jdk1.7.0_80\jre\bin
java.vm.version=24.80-b11
java.vm.vendor=Oracle Corporation
java.vendor.url=http://java.oracle.com/
// 이하생략
```

Hashtable을 확장한 클래스이기 때문에 키와 값 형태로 데이터가 저장되어 있다.

> 그런데 왜 Properties 클래스를 사용할까?

그냥 Hashtable이나 HashMap에 있는 속성을 사용하면 편할텐데...
그 이유는 Properties 클래스에서 추가로 제공하는 메소드들을 보면 알 수 있다.

- load()
- loadFromXML()
- store()
- storeToXML()

당연히 이 외에도 여러가지 메소드가 존재하지만 Properties 클래스를 이용하는 주된 이유는 여기에서 제공하는 메소드들 때문이다.

```java
	public void saveAndLoadProperties() {
		try {
			//String fileName = "test.properties";
			 String fileName="text.xml";
			File propertiesFile = new File(fileName);
			FileOutputStream fos = new FileOutputStream(propertiesFile);
			Properties prop = new Properties();
			prop.setProperty("Writer", "Sangmin, Lee");
			prop.setProperty("WriterHome", "http://www.GodOfJava.com");
			//prop.store(fos, "Basic Properties file.");
			 prop.storeToXML(fos, "Basic XML Property file.");
			fos.close();

			FileInputStream fis = new FileInputStream(propertiesFile);
			Properties propLoaded = new Properties();
			//propLoaded.load(fis);
			 propLoaded.loadFromXML(fis);
			fis.close();
			System.out.println(propLoaded);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
```


> 시스템 속성중 `user.dir`경로에 프로퍼티확장자를 가진 파일과 XML파일이 생성된걸 볼수 있다.
> 이 절에서 살펴본 Properties 클래스를 사용하면 여러 속성을 저장하고, 읽어 들이는 작업을 보다 쉽게 할 수 있다.


### 직접해 봅시다

```java
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

public class RandomNumberStatistics {
	private final int DATA_BOUNDARY = 50;

	Hashtable<Integer,Integer> hashtable=new Hashtable<Integer,Integer>();

	public static void main(String[] args) {
		RandomNumberStatistics sample=new RandomNumberStatistics();
		sample.getRandomNumberStatistics();
	}

	public void getRandomNumberStatistics(){
		Random random=new Random();

		for (int i = 0; i < 5000; i++) {
			putCurrentNumber(random.nextInt(50)+1);
		}
		printStatistics();
	}

	public void putCurrentNumber(int tempNumber){
		if(hashtable.containsKey(tempNumber)){
			hashtable.put(tempNumber, hashtable.get(tempNumber)+1);
		}
		else{
			hashtable.put(tempNumber, 1);
		}
	}

	public void printStatistics(){
		Set<Integer> keySet=hashtable.keySet();
		for (int key : keySet) {
			int count = hashtable.get(key);
			System.out.println(key+"="+count+"\t");
			if(key%10-1==0)
				System.out.println();
		}
	}
}
```


---


### 정리해 봅시다

1. Map은 키(Key)와 값(Value)로 구성된다.

2. Map에서 데이터를 저장하는 메소드는 put()이다. 이 메소드의 첫 매개변수는 키이고, 두번째 매개변수는 값이다.

3. 특정 키에 할당된 값을 가져오는 메소드는 get()이다. 이 메소드의 매개변수로 찾고자 하는 키를 지정해 주면 된다.

4. 데이터를 지우는 메소드는 remove()이며, 매개변수에는 지우고자하는 키를 지정해 주면 된다.

5. keySet() 메소드를 호출하면 키의 목록을 Set 구조로 리턴한다.

6. size() 메소드는 저장되어 있는 데이터의 개수를 리턴한다.

7. Hashtable은 null을 저장할 수 없다. 

8. Hashtable은 Thread에 안전하게 만들어져 있다. 

9. containsKey()메소드를 사용하면 해당 키를 갖는 값이 존재하는지 확인할 수 있다.

10. TreeMap을 사용하면 키를 정렬하면서 데이터를 저장한다. 순서는 숫자, 대문자, 소문자, 한글 순이다.

11. Properties 클래스는 Hashtable을 확장한 클래스이다.

12. Properties 클래스의 store() 메소드를 사용하면 데이터를 파일로 저장한다.
