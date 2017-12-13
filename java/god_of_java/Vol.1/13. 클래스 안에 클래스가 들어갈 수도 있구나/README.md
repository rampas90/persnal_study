## 13. 클래스 안에 클래스가 들어갈 수도 있구나

### 1. 클래스 안의 클래스

자바에서는 클래스 안에 클래스가 들어갈 수 있다. 이러한 클래스를 `Nested` 클래스라고 부른다.

`Nested`클래스가 존재하는 가장 큰 이유는 코드를 간단하게 표현하기 위함이다.
UI처리를 할 때 사용자의 입력이나, 외부의 이벤트에 대한 처리를 하는 곳에서 가장 많이 사용된다.

>  `Nested`클래스는 선언한 방법에 따라 
>  - `Static nested`클래스와 `내부(inner)`클래스로 구분된다.
>  - 두 클래스의 차이는 `static`으로 선언되었는지 여부다.

내부클래스
- `로컬(지역) 내부 클래스` -> 이름이 있는 내부 클래스
- `익명 내부 클래스` -> 이름이 없는 내부 클래스
- 일반적으로 줄여서 각각 `내부클래스`와 `익명클래스`로 부른다.

> `Nested`클래스를 만드는 이유
1. 한 곳에서만 상요되는 클래스를 논리적으로 묶어서 처리할 필요가 있을 때
2. 캡슐화가 필요할 때(예를 들어 A라는 클래스에 `private`변수가 있다. 이 변수에 접근하고 싶은 B라는 클래스를 선언하고, B클래스를 외부에 노출시키고 싶지 않을 경우가 여기에 속한다)
3. 소스의 가독성과 유지보수성을 높이고 싶을때

여기서 **1번**이 `Static Nested` 클래스를 사용하는 이유고, **2번**이 `내부클래스`를 사용하는 이유다


### 2. Static nested 클래스의 특징

내부클래스는 감싸고 있는 외부 클래스의 어떤 변수도 접근할 수 있다. 심지어 private로 선언된 변수까지도 접근 가능하다.
**하지만 `Static nested`클래스를 그렇게 사용하는 것은 불가능하다.**

```java
public class OuterOfStatic {
	static class StaticNested {
		private int value = 0;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
}
```

감싸고 있는 파일을 컴파일하면 자동으로 내부변수도 컴파일된다.

> 그러면 이 `StaticNested` 클래스의 객체는 어떻게 생성할 수 있을까?
> 아래 예제처럼 `.`을 통해 접근하면 된다.

```java
package c.inner;

public class NestedSample {

	public static void main(String[] args) {
		NestedSample sample = new NestedSample();
		sample.makeStaticNestedObject();
	}

	public void makeStaticNestedObject() {
		OuterOfStatic.StaticNested staticNested = new OuterOfStatic.StaticNested();
		staticNested.setValue(3);
		System.out.println(staticNested.getValue());
	}
}
```

### 3. 내부 클래스와 익명클래스

앞절에서 살펴본 Static nested 클래스와 내부 클래스의 차이는 겉으로 보기에는 그냥 static을 쓰느냐 쓰지 않느냐의 차이만 있을 뿐이다

```java
package c.inner;

public class OuterOfInner {
	class Inner {
		private int value = 0;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
}
```

> `StaticNested` 와 Inner 클래스의 내부 내용은 동일하다 하지만 클래스의 객체를 생성하는 방법은 다르다.

```java
public void makeInnerObject() {
	OuterOfInner outer = new OuterOfInner();
	OuterOfInner.Inner inner = outer.new Inner();
	inner.setValue(3);
	System.out.println(inner.getValue());
}
```
> 보다시피 같이 먼저 감싸고 있는 객체를 생성해준 후에 안쪽의 클래스객채를 생성해준걸 볼수 있다.

- 주로 GUI관련 프로그램을 개발할 때 가장 많이 사용한다.
- GUI 에서 내부 클래스들이 많이 사용되는 부분은 Listener(리스너)라는 것을 처리 할때다.
- 버튼클릭, 키보드 입력등 모두  Event라는 것이 발생하는 이때의 작업을 정의하기 위해서 내부클래스를 만들어 사용하게 된다.
- 즉 어떤 버튼이 눌럿렸을 때 수행해야하는 작업은 대부분 상이한 부분을 생각해보면 될 것 같다.
- 그리고 내부 클래스를 만드는 것 보다도 더 간단한 방법은 `익명 클래스`를 만드는 것이다.

```java
public class MagicButton {
	public MagicButton() {

	}

	private EventListener listener;

	public void setListener(EventListener listener) {
		this.listener = listener;
	}

	public void onClickProcess() {
		if (listener != null) {
			listener.onClick();
		}
	}
}
```
> EventListener.java

```java
public interface EventListener {
	public void onClick();
}
```

> `setButtonListener()` 메소드

```java
public void setButtonListener() {
	MagicButton button = new MagicButton();

	MagicButtonListener listener = new MagicButtonListener();
	button.setListener(listener);
	//or
	button.setListener(new EventListener() {
		public void onClick() {
			System.out.println("Magic Button Clicked !!!");
		}
	});
	// 클래스 이름도 없고, 객체 이름도 없기 때문에 다른곳에서는 참조할 수 없기 때문에
    // 재사용하려면 아래처럼 객체를 생성한 후 사용하면 된다.
	EventListener listener2=new EventListener() {
		public void onClick() {
			System.out.println("Magic Button Clicked !!!");
		}
	};
	button.setListener(listener2);
	button.onClickProcess();
}
```

> `MagicButtonListener()`

```java
class MagicButtonListener implements EventListener {
	public void onClick() {
		System.out.println("Magic Button Clicked !!!");
	}
}
```

> `setListener()`메소드를 보면 `new EventListener()`로 생성자를 호출한 후 바로 중괄호를 열었다
> 그리고 그 안에 onClick()메소드를 구현한 후 중괄호를 닫았다. 이렇게 구현한 것이 바로 `익명클래스`다

그냥 내부 클래스를 만들면 되는데 왜 자바에서는 이렇게 복잡하게 익명클래스라는 것을 제공하는 것일까?

- 클래스를 만들고, 그 클래스를 호출하면 그 정보는 메모리에 올라간다.
- 즉 클래스를 많이 만들면 만들수록 메모리는 많이 필요해지고, 애플리케이션을 시작 할 때 더 많은 시간이
- 소요된다. 따라서 자바에서는 이렇게 간단한 방법으로 객체를 생성할 수 있도록 해 놓았다. 

> 결론적으로 익명클래스나 내부 클래스는 모두 다른 클래스에서 재사용할 일이 없을 때 만들어야 한다.


**정리해봅시다**

1. Nested 클래스에 속하는 3가지 클래스에는 어떤 것들이 있나요?
	->	static, 내부클래스, 익명클래스

2. Nested 클래스를 컴파일하면 Nested클래스 파일의 이름은 어떻게 되나요?
	-> 외부클래스$내부클래스

3. Static Nested 클래스는 다른 Nested 클래스와 어떤 차이가 있나요?
	-> static이 붙어있다.
	=>	Static nested 클래스와 다른 nested 클래스의 차이점은 객체를 생성하는 방법이 다르다는 것이다.

4. StaticNested 클래스의 객체 생성은 어떻게 하나요?
	->	외부클래스.내부클래스 = new 객체생성
	=> OuterClass클래스 내에 StaticNestedClass 이라는 static nested 클래스가 있다면,
    OuterClass.StaticNestedClass staticNested=new OuterClass.StaticNestedClass();
    와 같이 선언한다.

5. 일반적인 내부 클래스의 객체 생성은 어떻게 하나요?
	->  외부클래스먼저 객체생성후 내부클래스를 객체생성
	=>	OuterClass클래스 내에 NestedClass 라는 inner 클래스가 있다면,
    OuterClass outer=new OuterClass();
    OuterClass.NestedClass nested=outer.new NestedClass();
    로 선언한다. 

6. Nested 클래스를 만드는 이유는 무엇인가요?
	-> 코드의 중복을 피하고 가독성을 높이며 , 다른클래스에서 접근할 필요가 없을때

	=>	Nested 클래스를 생성하는 이유는 다음과 같다.
- 한 곳에서만 사용되는 클래스를 논리적으로 묶어서 처리할 필요가 있을 때
- 캡슐화가 필요할 때(예를 들어 A 라는 클래스에 private 변수가 있다. 이 변수에 접근하고 싶은 B라는 클래스를 선언하고, B 클래스를 외부에 노출시키고 싶지 않을 경우가 여기에 속한다.) 
- 소스의 가독성과 유지보수성을 높이고 싶을 때 


7. Nested 클래스에서 감싸고 있는 클래스의 private 로 선언된 변수에 접근할 수 있나요?
	->	예
	=> 내부 클래스와 익명 클래스는 감싸고 있는 클래스의 어떤 변수라도 참조할 수 있다. 


8. 감싸고 있는 클래스에서 Nested 클래스에 선언된 private 로 선언된 변수에 접근할 수 있나요?
	-> 예
	=>	감싸고 있는 클래스에서 Static Nested 클래스의 인스턴스 변수나 내부 클래스의 인스턴스 변수로의 접근하는 것도 가능하다. 



