## 1. 상속

##### 상속이란?
- 객체지향을 통해서 달성하고자 하는 목표 중에서 가장 중요한 것은 재활용성일 것이다. 상속은 객체지향의 재활용성을 극대화시킨 프로그래밍 기법이라고 할 수 있다. 동시에 객체지향을 복잡하게 하는 주요 원인이라고도 할 수 있다.

- 상속(Inheritance)이란 물려준다는 의미다. 어떤 객체가 있을 때 그 객체의 필드(변수)와 메소드를 다른 객체가 물려 받을 수 있는 기능을 상속이라고 한다. 부모와 자식의 관계에 따른 비유를 들을 수도 있지만, 비유는 얻는 것보다 잃는 것이 많기 때문에 구체적인 코드를 통해서 상속을 알아보자. 


- 객체지향 수업의 [첫 번째 예제인 CalculatorDemo 예제](https://opentutorials.org/module/516/5400#Calculator.java)로 이동하자. 이 예제에서 등장하는 객체 Calculator는 더하기와 평균에 해당하는 sum과 avg 메소드를 가지고 있다. 그런데 이 객체가 가지고 있는 기능에 빼기를 추가하고 싶다. 가장 쉬운 방법은 이 객체에 빼기를 의미하는 substract를 추가해서 아래와 같이 사용하고 싶다.

> 아래는 예제의 전체소스이다
> 두개의 `int`형 값을 받아 더하기와 평균값을 내주는 간단한 소스이다.

```java
package org.opentutorials.javatutorials.object;
 
class Calculator{
    int left, right;
      
    public void setOprands(int left, int right){
        this.left = left;
        this.right = right;
    }
      
    public void sum(){
        System.out.println(this.left+this.right);
    }
      
    public void avg(){
        System.out.println((this.left+this.right)/2);
    }
}
  
public class CalculatorDemo4 {
      
    public static void main(String[] args) {
          
        Calculator c1 = new Calculator();
        c1.setOprands(10, 20);
        c1.sum();       
        c1.avg();       
          
        Calculator c2 = new Calculator();
        c2.setOprands(20, 40);
        c2.sum();       
        c2.avg();
    }
  
}
```

> 만약 위 소스에 빼기 기능을 추가하고 싶다면??

```java
Calculator c1 = new Calculator();
c1.setOprands(10, 20);
c1.sum();
c1.avg(); 
c1.substract();
```


위 소스처럼 객체에 빼기를 하는 메소드를 추가하면 되지만 다음과 같은 경우에 문제가 생긴다.
1. 객체를 자신이 만들지 않았다. 그래서 소스를 변경할 수 없다. 변경 할 수 있다고 해도 원 소스를 업데이트 하면 메소드 substarct이 사라진다. 이러한 문제가 일어나지 않게 하기 위해서는 지속적으로 코드를 관리해야 한다.

2. 객체가 다양한 곳에서 활용되고 있는데 메소드를 추가하면 다른 곳에서는 불필요한 기능이 포함될 수 있다. 이것은 자연스럽게 객체를 사용하는 입장에서 몰라도 되는 것까지 알아야 하는 문제가 된다.
	-> 즉, 객체지향의 부품으로서의 가치가 떨어진다고 볼수있다.

> 그렇다면 기존의 객체를 그대로 유지하면서 어떤 기능을 추가하는 방법이 없을까?

이런 맥락에서 등장하는 것이 바로 **상속**이다.
즉, 기존의 객체를 수정하지 않으면서 새로운 객체가 기존의 객체를 기반으로 만들어지게 되는것이다.
이때 기존의 객체는 기능을 물려준다는 의미에서 부모 객체가 되고 새로운 객체는 기존 객체의 기능을 물려받는다는 의미에서 자식 객체가 된다. 그 관계를 반영해서 실제 코드로 클래스를 정의해보자.

> 부모 클래스와 자식 클래스의 관계를 상위(super) 클래스와 하위(sub) 클래스라고 표현하기도 한다. 또한 기초 클래스(base class), 유도 클래스(derived class)라고도 부른다. 


아래 코드를 `Calculator` 클래스에 추가로 입력한다.
```java
class SubstractionableCalculator extends Calculator {
    public void substract() {
        System.out.println(this.left - this.right);
    }
}
```

> `main()`메소드에는 아래와 같이 선언해주면 된다

```java
SubstractionableCalculator c1 = new SubstractionableCalculator();
c1.setOprands(10, 20);
c1.sum();
c1.avg();
c1.substract();
```

> 그럼 상속한 클래스를 다시 상속할 수 있을까?
> 정답은 가능하다! 또한 상속받은 모든 내용을 상속받는다..
> 즉 손자는 부모와 조부모의 유전자를 모두 가진다고 생각하면 될 것 같다.

결론적으로 상속의 장점을 나열해보면
1. 유지보수의 편의성
2. 코드중복제거
3. 재활용성 증가!

---
## 2. 상속과 생성자

상속은 마냥 장점만 있는것이 아니다.
편리함을 위해 어떠한 기능을 수용하면 그 기능이 기존의 체계와 관계하면서 다양한 문제를 발생시킨다.
한마디로 **복잡도의 증가**라고 할 수 있다.

> 생성자가 상속을 만나면서 발생한 복잡성을 알아보자
> 또한 그 맥락에서 `super`라는 키워드의 의미도 같이 다뤄보자

```java
package org.opentutorials.javatutorials.Inheritance.example4;
public class ConstructorDemo {
    public static void main(String[] args) {
        ConstructorDemo  c = new ConstructorDemo();
    }
}
```
> 기본생성자는 자동으로 생성되므로 위의 예제는 에러가 없다

> 반면 아래처럼 매개변수가있는 생성자를 정의하게되면 기본생성자가 생성되지 않으므로 에러가 발생한다.

```java
package org.opentutorials.javatutorials.Inheritance.example4;
public class ConstructorDemo {
    public ConstructorDemo(int param1) {}
    public static void main(String[] args) {
        ConstructorDemo  c = new ConstructorDemo();
    }
}
```
> 이 문제를 해결하려면 기본생성자를 명시적으로 선언해줘야 한다
	`public ConstructorDemo(){}`
    
#### super

```java
										// 부모 클래스
class SubstractionableCalculator extends Calculator {
    public SubstractionableCalculator(int left, int right) {
        super(left, right);
        // super() 로 부모 생성자에 접근할 수 있다.
        // 부모클래스의 생성자라는 뜻이다!
    }
 
    public void substract() {
        System.out.println(this.left - this.right);
    }
}
```
>  이때 초기화코드는 super 클래스보다 먼저 등장시키면 안된다.
>  항상 하위 클래스의 초기화코드는 super클래스 이후에 작성해야 한다.

---

## 3. Overriding 
#### 창의적인 상속


