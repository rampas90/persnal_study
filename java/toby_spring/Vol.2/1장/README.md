## 1장 IoC 컨테이너와 DI

### 1.1 IoC 컨테이너 : 빈 팩토리와 애플리케이션 컨텍스트

스프링 애플리케이션에서는 오브젝트의 생성과 관계설정, 사용, 제거 등의 작업을 애플리케이션 코드 대신 독립된 컨테이너가 담당한다.
이를 컨테이너가 코드 대신 오브젝트에 대한 제어권을 갖고 있다고 해서 IoC라고 부른다.
그래서 스프링 컨테이너를 IoC컨테이너라고도 한다.

스프링에선 IoC를 담당하는 컨테이너를 `빈 팩토리` 또는 `애플리케이션 컨텍스트`라고 부르기도 한다.

1. 빈 팩토리 : 주로 오브젝트의 생성과 오브젝트 사이의 런타임 관계를 설정하는 DI관점으로 볼때 사용된다.
2. 애플리케이션 컨텍스트 : DI를 위한 빈 팩토리에 엔터프라이즈 개발을 위해 필요한 여러가지 컨테이너 기능을 추가한 조금더 확장된 의미로 사용된다.

> 스프링의 IoC 컨테이너는 일반적으로 애플리케이션 컨텍스트를 말한다.

- 스프링의 빈 팩토리와 애플리케이션 컨텍스트는 각각 기능을 대표하는 `BeanFactory`와 `Applicationcontext`라는 두 개의 인터페이스로 정의되어 있다.
- Applicationcontext 인터페이스는 BeanFactory 인터페이스를 상속한 서브인터페이스다.
- 스프링 애플리케이션은 최소한 하나 이상의 IoC컨테이너, 즉 애플리케이션 컨텍스트 오브젝트를 갖고 있다.


#### 1.1.1 IoC 컨테이너를 이용해 애플리케이션 만들기

가장 간단하게 IoC 컨테이너를 만드는 방법은 다음과 같이 ApplicationContext 구현 클래스의 인스턴스를 만드는 것이다.

```java
StaticApplicationContext ac = new StaticApplicationContext();
```

이렇게 만들어진 컨테이너가 본격적인 IoC 컨테이너로서 동작하려면 두 가지가 필요하다.
- `POJO 클래스`
- 설정 메타정보.


##### POJO 클래스

먼저 애플리케이션의 핵심코드를 담고 있는 POJO 클래스를 준비해야 한다.
각각의 POJO는 특정 기술과 스펙에서 독립적일뿐더러 의존관계에 있는 다른 POJO와 느슨한 결합을 갖도록 만들어야 한다.

간단히 두 개의 POJO클래스를 만들어보자.

- 지정된 사람에게 인사를 할 수 있는 Hello라는 클래스
- 메시지를 받아서 이를 출력하는 Printer 인터페이스를 구현한 StringPrinter 클래스

이 두개의 POJO 클래스는 Printer라는 인터페이스를 사이에 두고 느슨하게 연결되어 있다.
구체적으로 서로의 이름과 존재를 알 필요도 없다.
단지 서로 관계를 맺고 사용될 때 필요한 최소한의 인터페이스 정보만 공유하면된다.
그 역할을 `Printer 인터페이스`가 담당한다.

Hello는 Printer 인터페이스를 사용하고, StringPrinter 는  Printer 인터페이스를 구현하도록 만든다.

Hello는 Printer라는 인터페이스를 구현한 클래스의 오브젝트라면 어떤 것이든 사용 가능하다.
Printer 구현 클래스를 변경하더라도 Hello 코드의 수정은 필요 없다. 
단지 런타임 시에 오브젝트를 연결해주는 IoC 컨테이너의 도움만 있으면 된다.

> POJO 코드를 설계할 때는 일단 유연한 변경 가능성을 고려해서 만든다.
> 물론 여기서는 hHello 와 StringPrinter를 사용하기로 작정했다.
> 하지만 클래스 모델링 때나 클래스 코드를 작성할 대는 이런 의도는 아직 드러나지 않는다.
> 단지 유연한 확장성을 고려하고 자신의 기능에 충실한 POJO를 작성한다.

이렇게 만들어진 POJO 코드를 살펴보자

> 인사기능을 가진 Hello 클래스

```java
public class Hello {
	String name;
	Printer printer;
	
	public String sayHello(){
		//프로퍼티로 DI받은 이름을 이용해 간단한 인사문구 만들기
		return "Hello " + name;
	}
	
	public void print(){
		this.printer.print(sayHello());
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setPrinter(Printer printer){
		this.printer=printer;
	}
}
```

위의 Hello 클래스는 코드로만 보자면 Printer라는 인터페이스에만 의존한다.
실제로 런타임 시에 어떤 구체적인 클래스의 오브젝트를 사용하게 될지는 알지도 못하고, 사실 관심도 없다.

> 간단히 메시지를 받아서 출력하는 기능을 정의한 Printer 인터페이스

```java
public interface Printer{
	void print(String message);
}
```

이 `Printer` 인터페이스를 구현한 클래스는 얼마든지 만들 수 있다.

> `StringBuffer`에 메시지를 넣는 방식으로 메시지를 출력하는 StringPrinter 클래스

```java
public class StringBuffer implements Printer {	
	private StringBuffer buffer = new StringBuffer();
	
	public void print(String message) {
		this.buffer.append(message);
	}
	
	public String toString(){
		return this.buffer.toString();
	}
}
```

> 메시지를 받아서 표준출력 스트림으로 출력하는 ( 콘솔화면에 출력하는) ConsolePrinter 클래스

```java
public class ConsolePrinter implements Printer {
	public void print(String message) {
		System.out.println(message);
	}
}
```

이렇게 각자 기능에 충실하게 독립적으로 설계된 POJO 클래스를 만들고, 결합도가 낮은 유연한 관계를 가질 수 있도록 인터페이스를 이용해 연결해주는 것 까지가 IoC컨테이너가 사용할 POJO를 준비하는 첫 단계다.


##### 설정 메타정보
두 번째 필요한 것은 앞으로 만든 POJO 클래스들 중에 애플리케이션에서 사용할 것을 선정하고 이를 IoC컨테이너가 제어할 수 있도록 적절한 메타정보를 만들어 제공하는 작업이다.

`IoC 컨테이너`의 가장 기초적인 역할은 오브젝트를 생성하고 이를 관리하는 것이다.
스프링 컨테이너가 관리하는 이런 오브젝트는 `빈~bean~`이라고 부른다.
`IoC 컨테이너`가 필요로 하는 설정 메타 정보는 바로 이 빈을 어떻게 만들고 어떻게 동작하게 할 것인가에 관한 정보다.

스프링의 설정 메타정보는 XML 파일이 아니다.
스프링에 대한 대표적인 오해 중의 하나는 스프링의 설정정보는 XML로 되어 있다는 것이다.
스프링이 XML에 담긴 내용을 읽어서 설정 메타정보로 활용하는 건 사실이지만, 그렇다고 해서 스프링이 XML로 된 설정 메타정보를 가졌다는 말은 틀렸다.

스프링의 설정 메타정보는 `BeanDefinition 인터페이스`로 표현되는 순수한 추상 정보다.
스프링 IoC 컨테이너, 즉 애플리케이션 컨텍스트는 바로 이 `BeanDifinition`으로 만들어진 메타정보를 담은 오브젝트를 사용해 IoC와 DI작업을 수행한다.

- xml
- 소스코드 어노테이션
- 자바코드
- 프로퍼티 파일

이든 파일포맷이나 형식에 제한되지 않고 `BeanDifinition`으로 정의되는 스프링의 설정 메타정보의 내용을 표현한 것이 있다면 무엇이든 사용 가능하다.

`BeanDifinition` 인터페이스로 정의되는, IoC 컨테이너가 사용하는 빈 메타정보는 대략 다음과 같다.

- 빈 아이디, 이름, 별칭 : 빈 오브젝트를 구분할 수 있는 식별자
- 클래스 또는 클래스 이름 : 빈으로 만들 POJO 클래스 또는 서비스 클래스 정보
- 스코프 : 싱글톤, 프로토타입과 같은 빈의 생성 방식과 존재 범위
- 프로퍼티 값 또는 참조 : DI에 사용할 프로퍼티 이름과 값 또는 참조하는 빈의 이름
- 생성자 파라미터 값 또는 참조 : DI에 사용할 생성자 파라미터 이름과 값 또는 참조할 빈의 이름
- 지연된 로딩 여부, 우선 빈 여부, 자동와이어링 여부, 부모 빈 정보, 빈팩토리 이름등


> 결국 스프링 애플리케이션이란 POJO 클래스와 설정 메타 정보를 이용해 IoC컨테이너가 만들어주는 오브젝트의 조합이라고 할 수 있다.

일반적으로 설정 메타정보는 XML 파일이나 어노테이션 같은 외부 리소스를 전용 리더기가 읽어서 BeanDifinition 타입의 오브젝트로 만들어 사용한다.

하기 코드는 Hello 클래스를 IoC컨테이너에 빈으로 등록하는 학습 테스트 코드다.
빈 메타정보의 항목들은 대부분 디폴트 값이 있다.
싱글톤으로 관리되는 빈 오브젝트를 등록할 때 반드시 제공해줘야 하는 정보는 빈 이름과 POJO 클래스 뿐이다.

```java
// IoC 컨테이너 생성, 생성과 동시에 컨테이너로 동작한다.
StaticApplicationContext ac = new StaticApplicationContext();
// Hello 클래스를 hello1이라는 이름의 싱글톤 빈으로 컨테이너에 등록한다.
ac.registerSingleton("hello1",Hello.class);

// IoC 컨테이너가 등록한 빈을 생성했는지 확인하기 위해 빈을 용청하고 Null인지 아닌지 확인한다.
Hello hello1 = ac.getBean("hello1", Hello.class);
assertThat(hello1, is(notNullValue()));
```

위에서 사용한 `StaticApplicationContext` 는 코드에 의해 설정 메타정보를 등록하는 기능을 제공하는 애플리케이션 컨텍스트로
테스트용으로 사용하기에 적당하다.

여기서 주의할 점은 IoC컨테이너가 관리하는 빈은 오브젝트 단위지 클래스 단위가 아니라는 점이다.
또한, 보통은 클래스당 하나의 오브젝트를 만들지만, 경우에 따라서 하나의 클래스를 여러 개의 빈으로 등록하기도 하는데
이는 빈마다 다른 설정을 지정해두고 사용하기 위해서다.

예를 들어 사용할 DB가 여러 개라면 같은 SimpleDriverDataSource 클래스로 된 빈을 여러 개 등록하고 각각 다른 DB설정을
지정해서 사용할 경우다.

위 코드는 `StaticApplicationContext` 가 디폴트 메타정보를 사용해서 싱글톤 빈을 등록해주는 registerSingleton()메소드를 
사용했는데 이번에는 직접 `BeanDifinition` 타입의 설정 메타정보를 만들어서 IoC 컨테이너에 등록하는 방법을 사용해보자.

`RootBeanDifinition`은 가장 기본적인 `BeanDifinition` 인터페이스의 구현 클래스다.
다으과 같이 `RootBeanDifinition` 오브젝트를 만들어서 빈에 대한 설정정보를 넣어주고 IoC컨테이너에 등록할 수 있다.

```java
	//	빈 메타정보를 담은 오브젝트를 만든다.
	//	빈 클래스를 Hello로 지정한다.
	//	<bean class="springbook.learning....Hello" /> 에 해당하는 메타정보다
	BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
	
	// 빈의 name 프로퍼티에 들어갈 값을 지정한다.
	// <property name="name" value="Spring" /> 에 해당한다.
	helloDef.getPropertyValues().addPropertyValue("name", "String");
	
	// 앞에서 생성한 메타정보를 hello2라는 이름을 가진 빈으로 해서 등록한다.
	// <bean id="hello2" ... /> 에 해당한다.
	ac.registerBeanDefinition("hello2", helloDef);
```

IoC 컨테이너는 빈 설정 메타정보를 담은 BeanDefinition 을 이용해 오브젝트를 생성하고 DI 작업을 진행한 뒤에 빈으로 사용할 수 있도록 등록해준다.
이때 BeanDefinition의 클래스, 프로퍼티, 빈 아이디 등의 정보가 활용된다.

하기 코드는 `hello2` 빈을 컨테이너에서 가져와서 이를 검증하는 코드다.
빈은 오브젝트 단위로 등록되고 만들어지기 때문에 같은 클래스 타입이더라도 두 개를 등록하면 서로 다른 빈 오브젝트가 생성되는 것도 확인할 수 있다.

```java
	// BeanDifinition 으로 등록된 빈이 컨테이너에 의해 만들어지고 프로퍼티 설정이 됐는지 확인한다.
	Hello hello2 = ac.getBean("hello2", Hello.class);
	assertThat(hello2.sayHello(), is("Hello Spring"));
	
	// 처음 등록한 빈과 두번째 등록한 빈이 모두 동일한 Hello 클래스지만 별개의 오브젝트로 생성됐다.
	assertThat(hello1, is(not(hello2)));

	assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
```

위 코드 마지막 줄에서 볼 수 있듯이, IoC 컨테이너에서 등록된 빈 설정 메타정보를 가져올수도 있다.
빈에 DI 되는 프로퍼티는 스트링이나 숫자 등의 값과 다른 빈 오브젝트를 가리키는 레퍼런스로 구분할 수 있다.
레퍼런스로 지정된 프로퍼티는 다른 빈 오브젝트를 주입해서 오브젝트 사이의 관계를 만들어내느 데 사용된다.

Hello 클래스와 StringPrinter 클래스는 Printer라는 인터페이스를 사이에 두고 아주 느슨하고 간접적인 관계를 맺고 있을 뿐이다.
Hello 오브젝트와 StringPrinter 오브젝트 사이의 관계는 설정 메타정보를 참고해서 런타임 시에 IoC 컨테이너가 주입해준다.

> 설정 메타정보 책 55~59p 참고 


#### IoC 컨테이너의 종류와 사용 방법

ApplicationContext 인터페이스를 바르게 구현했다면 어떤 클래스든 스프링의 IoC컨테이너로 사용할 수 있다.
물론 스프링을 사용하는 개발자가 직접 이 인터페이스를 구현할 일은 없다.
왜냐하면 이미 스프링에는 다양한 용도로 쓸 수 있는 십여 개의 ApplicationContext 구현 클래스가 존재한다.

또한 스프링 애플리케이션에서 직접 코드를 통해 ApplicationContext 오브젝트를 생성하는 경우는 거의 없다.
대부분 간단한 설정으로 통해 자동으로 만들어지는 방법을 사용하기 때문이다.

> 스프링이 제공하는 ApplicationContext 구현 클래스에는 어떤 종류가 있고 어떻게 사용되는지 좀 더 살펴보자

##### StaticApplicationContext

- 코드를 통해 빈 메타정보를 등록하기 위해 사용
- 사실상 스프링의 기능에 대한 학습 테스트를 만들경우가 아니라면 실제로 사용되지 않는다.

##### GenericApplicationContext

- 가장 일반적인 애플리케이션 컨텍스트 구현클래스다.
- 실전에서 사용될 수 있는 모든 기능을 갖추고 있는 애플리케이션 컨텍스트다.
- 컨테이너의 주요 기능을 DI를 통해 확장할 수 있도록 설계되어 있다.
- XML 파일과 같은 외부의 리소스에 있는 빈 설정 메타정보를 리더를 통해 읽어들여서 메타정보로 전환해서 사용한다.

특정 포캣의 빈 설정 메타정보를 읽어서 이를 애플리케이션 컨텍스트가 사용할 수 있는 BeanDefinition 정보로 변환하는 기능을 가진 오브젝트는 BeanDefinitionReader 인터페이스를 구현해서 만들고, 빈 설정정보 리더라고 불린다.
XMl로 작성된 빈 설정정보를 읽어서 컨테이너에게 전달하는 대표적인 빈 설정정보 리더는 `XmlBeanDefinitionReader`다.
이 리더를 `GenericpplicationContext`가 이용하도록 해서 hello빈과 printer 빈을 등록하고 사용하게 만들어보자

> XML로 만든 빈 설정 메타정보

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
	http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="hello" class="springbook.learningtest.spring.Hello" >
		<property name="name" value="Spring" />
		<property name="printer" value="printer" />
	</bean>

	<bean id="printer" class="springbook.learningtest.spring.StringPrinter" />
</beans>
```

빈 메타정보의 내용은 앞에서 코드로 만들었던 것과 동일하다. 단지 `<bean>`태그를 사용해 XML문서로 표현했을 뿐인다.

XML로 만든 빈 설정 메타정보를 사용하는 `GenericApplicationContext`에 대한 테스트를 다으과 같이 작성한다.

> `GenericApplicationContext` 의 사용 방법에 대한 학습 테스트

```java
	@Test
	public void genericApplicationContext() {
		GenericApplicationContext ac = new GenericApplicationContext();

		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
		reader.loadBeanDefinitions("springbook/learningtest/spring/ioc/genericApplicationContext.xml");

		ac.refresh();

		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();

		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
	}
```

`XmlBeanDefinitionReader`는 스프링의 리소스 로더를 이용해 XML 내용을 읽어온다.
따라서 다양한 리소스 타입의 XML문서를 사용할 수 있다.
리소스 대신 스트링을 넘기면 기본적으로 클래스패스 리소스로 인식한다.

또한 프로퍼티 파일에서 빈설정 메타정보를 가져오는 `PropertiesBenDifinitionReader`도 제공한다.

대표적으로 `XML파일`, `자바 소스코드 애노테이션`, `자바클래스` 세 가지 방식으로 빈 설정 메타정보를 작성할 수 있다.
물론 이 세가지로 제한되진 않는다.
필요하다면 얼마든지 유연하게 확장할 수 있고, 이를 보면 스프링이 지지하는 객체지향적인 설계의 핵심 가치를 스프링 컨테이너에도 
충실하게 적용하고 있음을 알 수 있다.



##### GenericXmlApplicationContext
- 코드에서 `GenericApplicationContext`를 사용하는 경우에는 번거롭게 `XmlBeanDefinitionReader`를 직접 만들지 말고, 이 두 개의 클래스가 결합된 `GenericXmlApplicationReader` 를 사용하면 편리하다. 
- 이걸 사용하면 XML 파일을 읽어들이고 refresh()를 통해 초기화하는 것까지 한 줄로 끝낼 수 있다.
- XML 파일로 설정을 만들고 애플리케이션 컨텍스트에서 XML을 읽어서 사용하는 코드를 시험 삼아 만들어볼 필요가 있다면 사용하기에 적당하다.

##### WebApplictionContext

- 스프링 애플리케이션에서 가장 많이 사용되는 애플리케이션 컨텍스트다
- `ApplicationContext`를 확장한 인터페이스다
- 이름 그대로 웹 환경에서 사용할 때 필요한 기능이 추가된 애플리케이션 컨텍스트다.
- 스프링 애플리케이션은 대부분 서블릿 기반의 독립 웹 애플리케이션(WAR)으로 만들어지기 때문이다.

> 

- 가장 많이 사용되는 건, XML 설정파일을 사용하도록 만들어진 `XmlWebApplicationContext`다.
물론 XML 외의 설정정보 리소스도 사용할 수 있다.
- 애노테이션을 이용한 설정 리소스만 사용한다면 `AnnotationConfigWebApplicationContext`를 쓰면 된다.
디폴트는 `XmlWebApplicationContext`

> WebApplicationContext의 사용 방법을 이해하려면 스프링의IoC 컨테이너를 적용했을 때 애플리케이션을 기동시키는 방법에 대해 살펴볼 필요가 있다.

1. 스프링 IoC컨테이너는 빈 설정 메타정보를 이용해 빈 오브젝트를 만들고 DI 작업을 수행한다.
2. 하지만 그것만으로는 애플리케이션이 동작하지 않는다. 마치 자바 애플리케이션의 `main()`메소드처럼 어디에선가 특정 빈 오브젝트의 메소드를 호출함으로써 애플리케이션을 동작 시켜야 한다.

보통 이런 기동 역할을 맡은 빈을 사용하려면 IoC컨테이너에서 요청해서 빈 오브젝트를 가져와야 한다.
그래서 간단히 스프링 애플리케이션을 만들고 IoC 컨테이너를 직접 셋업했다면 다음과 같은 코드가 반드시 등장한다.

```java
ApplicationContext ac=...
Hello hello = ac.getBean("hello", Hello.class);
hello.print();
```

> 적어도 한 번은 IoC 컨테이너에게 요청해서 빈 오브젝트를 가져와야 한다.
> 이때는 필히 getBean()이라는 메소드를 사용해야 한다.

그러나 그 이후로는 다시 getBean()으로 빈 오브젝트를 가져올 필요가 없다. 빈오브젝트들끼리 DI로 서로 연결되어 있으ㅡ로 의존관계를 타고 필요한 오브젝트가 호출되면서 애플리케이션이 동작할 것이다.
즉 IoC 컨테이너의 역할은 이렇게 초기에 빈 오브젝트를 생성하고 DI 한 후에 최초로 애플리케이션을 기동할 빈 하나를 제공해주는 것까지다.

그런데 테스트나 독립형 애플리케이션이라면 모르겠지만, 웹 애플리케이션은 동작하는 방식이 근본적으로 다르다.
웹에서는 main()메소드를 호출할 방법이 없고, 사용자도 여럿이며 동시에 웹 애플리케이션을 사용한다.

그래서 웹 환경에서는 main()메소드 대신 서블릿 컨테이너가 브라우저로부터 오는 HTTP 요청을 받아서 해당 요청에 매핑되어 있는 서블릿을 실행해주는 방식으로 동작한다.

서블릿이 일종의 main()메소드와 같은 역할을 하는 셈이다.

> 그렇다면 웹 애플리케이션에서 스프링 애플리케이션을 기동시키는 방법은 무엇일까?

일단 main()메소드 역할을 하는 서블릿을 만들어두고, 미리 애플리케이션 컨텍스트를 생성해둔 다음, 요청이 서블릿으로 들어올 때마다 getBean()으로 필요한 빈을 가져와 정해진 메소드를 실행해주면 된다.

> 쉽게 main()메소드에서 했던 작업을 웹 애플리케이션과 그에 소속된 서블릿이 대신해 준다고 생각하자.


브라우저와 같은 클라이언트로부터 들어오는 `요청` -> `서블릿 컨테이너`(받아서 서블릿을 동작시켜줌) -> `서블릿`
`서블릿` -> 웹 애플리케이션이 시작 될 때 미리 만들어둔 웹 애플리케이션 컨텍스트에게 빈을 요청해서 받아둔다.

그리고 미리 지정된 메소드를 호출함으로써 스프링 컨테이너가 DI 방식으로 구성해둔 애플리케이션의 기능이 시작되는 것이다.

다행히도 스프링은 이런 웹 환경에서 애플리케이션 컨텍스트를 생성하고 설정 메타 정보로 초기화해주고, 클라이언트로부터 들어오는 요청마다 적절한 빈을 찾아서 이를 실행해주는 기능을 가진 `DispatcherServlet`이라는 이름의 서블릿을 제공한다.

스프링이 제공해준 서블릿을 `web.xml`에 등록하는 것만으로 웹 환경에서 스프링 컨테이너가 만들어지고 애플리케이션을 실행하는 데 필요한 대부분의 준비는 끝이다.

> 일단 `웹 애플리케이션`에서 만들어지는 `스프링 IoC 컨테이너`는 `WebApplicationContext 인터페이스`를 구현한 것임을 기억해두자
> `WebApplicationContext`의 특징은 자신이 만들어지고 동작하는 환경인 웹 모듈에 대한 정보에 접근할 수 있다는 점이다.



#### IoC 컨테이너 계층구조
빈을 담아둘 IoC 컨테이너는 애플리케이션마다 하나씩이면 충분하다.
하지만 한 개 이상의 IoC 컨테이너를 만들어 두고 사용해야 할 경우가 있는데 바로 트리모양의 계층구조를 만들때다.

##### 부모 컨텍스트를 이용한 계층 구조 효과

모든 애플리케이션 컨텍스트는 부모 애플리케이션 컨텍스트를 가질 수 있다.
이를 이용하면 트리구조의 컨텍스트 계층을 만들 수 있다.

- 계층구조 안의 모든 컨텍스트는 각자 독립적으로 자신이 관리하는 빈을 갖고 있긴 하지만 DI를 위해 빈을 찾을 때는 부모 애플리케이션 컨텍스트의 빈까지 모두 검색한다.

- 먼저 자신이 관리하는 빈 중에서 필요한 빈을 찾아보고, 없으면 부모 컨텍스트에게 빈을 찾아달라고 요청한다.

- 부모 컨텍스트에서도 원하는 빈을 찾을 수 없다면, 부모 컨텐스트의 부모 컨텍스트에게 다시 요청한다.
이렇게 계층구조를 따라서 가장 위에 존재하는 루트 컨텍스트까지 요청이 전달된다.

- 이때 부모 컨텍스트에게만 빈 검색을 요청하지 자식 컨텍스트에게는 요청하지 않는다.
그런 이유로 같은 레벨에 있는 형제 컨텍스트의 빈도 찾을 수 없다.

> 검색순서는 항상 자신이 먼저이고, 그런 다음 직계 부모의 순서다.

미리 만들어진 애플리케이션 컨텍스트의 설정을 그대로 가져다가 사용하면서 그중 일부 빈만 변경하고 싶다면,
애플리케이션 컨텍스트를 두개 만들어서 하위 컨텍스트에서 바꾸고 싶은 빈들을 다시 설정해줘도 된다.

> 이렇게 기존 설정을 수정하지 않고 사용하지만 일부 빈 구성을 바꾸고 싶은 경우, 애플리케이션 컨텍스트의 계층구조를 만드는 방법이 편리하다.

**계층 구조**를 이용하는 또 한가지 이유는 여러 애플리케이션 컨텍스트가 공유하는 설정을 만들기 위해서다.
각자 용도와 성격 달라서 웹 모듈을 여러 개로 분리하긴 했지만 핵심 로직을 담은 코드는 공유하고 싶을 때 이런 식으로 구성한다.
마찬가지로 애플리케이션 안에 성격이 다른 설정을 분리해서 두 개 이상의 컨텍스트를 구성하면서 각 컨텍스트가 공유하고 싶은 게 있을 때 **계층구조**를 이용한다.

> 이런 컨텍스트 계층구조의 특성을 이해하지 못한 채로 설정을 만들고 사용하면 뜻하지 않은 에러를 만나거나 원하는 대로 동작하지 않는 문제가 발생할 수도 있으니 주의하자

##### 컨텍스트 계층구조 테스트

컨텍스트 계층구조에 대해 간단히 학습 테스트를 만들어서 그 동작방식을 확인해보자.

> 부모(루트)컨텍스트가 사용하는 설정파일`parentContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

	<bean id="printer" class="springbook.learningtest.spring.StringPrinter" />

	<bean id="hello" class="springbook.learningtest.spring.Hello">
		<property name="name" value="Parent" />
		<property name="printer" ref="printer" />
	</bean>
</beans>
```

> 자식 컨텍스트가 사용하는 설정파일`childContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

	<bean id="hello" class="springbook.learningtest.spring.Hello">
		<property name="name" value="Child" />
		<property name="printer" ref="printer" />
	</bean>
</beans>
```

parentContext.xml 은 그 자체적으로 완전한 빈 설정정보를 갖고 있다.
그에 반해 childContext.xml에는 hello 빈의 printer 프로퍼티가 참조하고 있는 printer라는 이름의 빈이 존재하지 않는다.
이는 childeContext.xml은 parentContext.xml을 이용하는 컨텍스트를 부모 컨텍스트로 이용할 것을 전제로 만들어지기 때문에 아무런 문제가 없다.

즉 `자식컨텍스트`의 설정은 `부모컨텍스트`에 의존적이라고 할 수 있다. 동시에 `자식컨텍스트`를 통해 정의된 빈은 `부모컨텍스트`를 통해 정의된 같은 종류의 빈 설정을 오버라이드 한다.
따라서 childContext.xml은 parentContext.xml보다 우선한다고 할 수 있다.

> 이 두개의 설정을 부모/자식 관계의 컨텍스트 계층으로 만들어보면서 컨텍스트 계층구조의 동작방식을 확인해보자

`parentContext.xml`을 사용하는 부모 컨텍스트를 다음과 같이 만들어보자

```java
ApplicationContext parent = new GenericXmlApplicationContext(basePath+"parentContext.xml");
// basePath 는 현재 클래스의 패키지 정보를 클래스패스 형식으로 만들어서 미리 저장해둔 것이다.
```

이 부모 컨텍스틑 더이상 상위에 부모 컨텍스트가 존재하지 않는 루트 컨텍스트이므로 반드시 **스스로 완전한 빈 의존관계를 보장해야 한다.**

> 다음은 `childContext.xml`을 사용하는 자식 컨텍스트를 만들어보자

```java
GenericApplicationContext child = new GenericApplicationContext(parent);
```

앞에서 만든 `parent`를 부모 컨텍스트로 지정해줬다.
이렇게 해서 `child`라는 이름의 애플리케이션 컨텍스트는 parent 컨텍스트를 부모 컨텍스트로 갖게 된다.

> 다음은 자식컨텍스트가 사용할 설정정보를 읽어들이고 초기화해보자

설정 메타정보를 읽고 `refresh()` 해주면 컨텍스트를 초기화하면서 DI를 진행한다. 
이때 child 컨텍스트에서 필요한 빈이 존재하지 않을 경우 부모컨텍스트에게 빈 검색을 요청하게 된다.

```java
XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
reader.loadBeanDefinitions(basePath+"childContext.xml");
child.refresh();	// 리더를 사용해서 설정을 읽은 경우에는 반드시 refresh()를 통해 초기화해야 한다.
```

이제 parent와 child에 담긴 두개의 컨텍스트는 부모/자식 관계로 연결되어 있다.
이때 자식 컨텍스트인 child 에게 printer라는 이름의 빈을 요청하면 어떻게 될까?
이름이 printer인 빈은 childContext.xml안에는 존재하지 않는다.
그래서 부모인 parent 컨텍스트에서 검색을 시도한다.

> 다음과 같이 확인해보자

```java
Printer printer = child.getBean("printer", Printer.class);
assertThat(printer, is(notNullValue()));
```


#### 웹 애플리케이션의 IoC컨테이너 구성

##### 웹 애플리케이션의 컨텍스트 구성방법

웹 애플리케이션의 애플리케이션 컨텍스트를 구성하는 방법으로는 다음 세 가지를 고려해볼 수 있다.
첫 번째 방법은 컨텍스트 계층구조를 만드는 것이고, 나머지 두 가지 방법은 컨텍스트를 하나만 사용하는 방법이다.
첫 번째와 세 번째 방법은 스프링 웹 기능을 사용하는 경우고, 두 번째 방법은 스프링 웹기술을 사용하지 않을때 적용가능한 방법이다.

1. `서블릿 컨텍스트`와 `루트 애플리케이션 컨텍스트` 계층구조
2. `루트 애플리케이션 컨텍스트` 단일구조
3.  `서블릿 컨텍스트` 단일구조


###### 1) 서블릿 컨텍스트와 루트 애플리케이션 컨텍스트 계층구조
- 가장 많이 사용되는 기본적인 구성 방법이다. 
- 스프링 웹 기술을 사용하는 경우 웹 관련 빈들은 서블릿의 컨텍스트의 두고, 나머지는 루트 애플리케이션...에 등록
- 루트 컨텍스트는 모든 서블릿 레벨 컨텍스트의 부모 컨텍스트가 된다.


###### 2) 루트 애플리케이션 컨텍스트 단일구조
- 서드파티 웹 프레임워크나 서비스 엔진만을 사용해서 프레젠테이션 계층을 만든다면 스프링 서블릿을 둘 이유가 없다.
- 따라서 서블릿의 애플리케이션컨텍스트도 사용하지 않는다.
- 이때는 루트애플리케이션 컨텍스트만 등록해주면 된다.

###### 3) 서블릿 컨텍스트 단일구조
- 스프링 웹 기술을 사용하면서 스프링 외의 프레임워크나 서비스 엔진에서 스프링의 빈을 이용할 생각이 아니라면 루트 애플리케이션 컨텍스트를 생략할 수도 있다.
- 대신 서블릿 컨텍스트에 모든 빈을 다 등록하면 된다.
- 게층구조를 사용하면서 발생할 수 있는 혼란을 근본적으로 피하고 단순한 설정을 선호한다면 이 방법을 선택할 수 있다.


##### 루트 애플리케이션 컨텍스트 등록

- 루트 웹 애플리케이션 컨텍스트를 등록하는 가장 간단한 방법은 서블릿의 `이벤트 리스너`를 이용하는 것이다.

스프링은 웹 애플리케이션의 시작과 종료 시 발생하는 이벤트를 처리하는 리스너인 `ServletContextListener` 를 이용한다.
`ServletContextListener` 인터페이스를 구현한 리스너는 웹 애플리케이션 전체에 적용 가능한 DB연결 기능이나 로깅 같은 서비스를 만드는 데 유용하게 쓰인다.

웹 애플리케이션 시작 -> 루트 애플리케이션 생성 후 초기화
웹 애플리케이션 종료 -> 컨텍스트 종료

> 스프링인 위와 같은 기능을 가진 리스너인 `ContextLoaderListener`를 제공한다.
> 사용법은 web.xml 파일안에 아래처럼 리스너 선언을 넣어주기만 하면 된다.

```xml
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

이 리스너가 만들어주는 컨텍스트는 어떤 종류이고 어떤 설정파일을 사용할까?
별다른 파라미터를 지정하지 않으면, 디폴트로 설정된 다음의 값이 적용된다.
- 애플리케이션 컨텍스트 클래스 : XmlWebApplicationContext
- XML 설정파일 위치 : /WEB-INF/applicationContext.xml

디폴트 XML설정파일 위치는 다음과 같이 파라미터를 선언해주면 된다

```xml
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>
			/WEB-INF/spring/root-context.xml
			/WEB-INF/applicationContext.xml
		</param-value>
	</context-param>
```

ContextLoaderListener 가 자동으로 생성하는 컨텍스트의 클래스는 기본적으로 `XmlApplicationContext`다 
이를 다른 구현 클래스로 변경하고 싶으면 `contextClass`파라미터를 이용해 지정해주면 된다.
단. 여기에 사용될 컨텍스트는 반드시 `WebApplictionContext` 인터페이스를 구현해야 한다.

`XmlApplicationContext`외에 자주 사용하는 `AnnotationConfigApplicationContext`를 사용해 루트애플리케이션 컨텍스트를 생성하는 코드를 살펴보자

```xml
<context-param>
	<param-name>contextClass</param-name>
	<param-value>
		org.springframework.web.context.support.AnnotationConfigWebApplicationContext
	</param-value>
</context-param>
```

`AnnotationConfigApplicationContext`를 컨텍스트 클래스로 사용할 때는 `contextConfigLocation`파라미터를 반드시 선언해줘야 한다.


##### 서블릿 애플리케이션 컨텍스트 등록

스프링의 웹 기능을 지원하는 프론트 컨트롤러 서블릿은 `DispatcherServlet` 이다.

- web.xml 에 등록해서 사용할 수 있는 평범한 서블릿이다.
- 이름을 달리해서 여러개의 디스패쳐서블릿을 등록할 수도 있다.

사용법은 아래와 같다

```xml
	<servlet>
		<servlet-name>spring</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>
```

> 만약 applictionContext.xml 과 웹 계층을 위한 spring-servlet.xml을 서블릿 컨텍스트가 모두 사용하게 한다면
> 다음과 같이 한다. ( 이때 루트 컨텍스트의 등록은 생략할 수 있다. )

```xml
<servlet>
	<servlet-name>spring</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>
			/WEB-INF/application.xml
         /WEB-INF/spring-servlet.xml
		</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>
```

---

### IoC/DI를 위한 빈 설정 메타정보 작성

IoC 컨테이너의 가장 기본적인 열할은 코드를 대신해서 애플리케이션을 구성하는 오브젝트를 생성하고 관리하는 것이다.

POJO로 만들어진 애플리케이션 클래스와 서비스 오브젝트들이 그 대상이다.

> 그렇다면 컨테이너는 어떻게 자신이 만들 오브젝트가 무엇인지 알 수 있을까?

컨테이너는 빈 설정 메타정보를 통해 빈의 클래스와 이름을 제공받는다.
파일 혹은 어노테이션 같은 리소스로부터 읽혀서 BeanDefinition 타입의 오브젝트로 변환되고, 이 정보를 IoC 컨테이너가 활용하는 것이다.


#### 빈 설정 메타정보

BeanDefinition에는 IoC 컨테이너가 빈을 만들 때 필요한 핵심 정보다 담겨있고,
몇 가지 필수항목을 제외하면 컨테이너에 미리 설정된 디폴트 값이 그대로 적용된다.
또한 여러 개의 빈을 만드는 데 재사용될 수도 있다.

설정 메타정보가 같지만 이름이 다른 여러 개의 빈 오브젝트를 만들 수 있기때문이다.
따라서 BeanDefinition에는 빈의 이름이나 아이디를 나타내는 정보는 포함되지 않는다.
대신 IoC 컨테이너에 이 BeanDefinition 정보가 등록될 때 이름을 부여해줄 수 있다.


##### 빈 설정 메타정보 항목

> p.84 표1-1 참조



#### 빈 등록 방법

빈 등록은 빈 메타정보를 작성해서 컨테이너에게 건네주면 된다.

보통 XML문서, 프로퍼티 파일, 소스코드 어노테이션과 같은 외부 리소스로 빈 메타정보를 작성하고 이를 적절한 리더나 변환기를 통해 애플리케이션 컨텍스트가 사용할 수 있는 정보로 변환해주는 방법을 사용한다.

스프링에자 자주 사용되는 빈의 등록방법은 크게 다섯가지가 있다.

###### 1) XML : `<bean>` 태그
- 가장 단순하면서도 가장 강력한 설정 방법
- 스프링 빈 메타정보의 거의 모든 항목을 지정할 수 있으므로 세밀한 제어가 가능하다.
- 기본적으로 id와 class 라는 두 개의 애트리뷰트가 필요하며, id는 생략가능하다.
- 다른 빈의 `<property>`태그 안에 정의할 수도 있으며, 이때는 `<bean>`의 아이디나 이름을 지정하지 않는다. 또한 이렇게 다른 빈의 설정안에 정의되는 빈을 **내부 빈** 이라고 한다.

##### 2) XML : 네임스페이스와 전용태그
`<bean>` 태그 외에도 다양한 스키마에 정의된 전용 태그를 사용해 빈을 등록하는 방법이 있다.
스프링의 빈은 크게 애플리케이션의 핵시코드를 담은 컴포넌트와 서비스 또는 컨테이너 설정을 위한 빈으로 구분 할 수 있다.

이 두가지가 모두 `<bean>` 이라는 태그로 등록되 돼서 구별이 쉽지 않은데, 이를위해 네임스페이스와 태그를 가진 설정 방법을 제공한다.

> 예를 들어 aop에 사용되는 포인트컷 `<bean>`은 아래와 같이 사용가능하다.

```xml
<bean id="mypointcut" class="org.spring......AspectJExpressionPoiintcut">
		...
</bean>

<aop:pointcut id="mypointcut" expression="execution**..*ServiceImpl.upgrade*(..))"
```

##### 3) 자동인식을 이용한 빈등록 : 스트레오타입 어노테이션과 빈 스캐너

모든 빈을 XML에 일일히 선언하는 것이 귀찮게 느껴질 수도 있다.
특히 규모가 커지고 빈의 개수가 많아지만 당연한 일이다.
바로 이럴때 사용할수 있는 방법이다.

- 빈으로 사용될 클래스에 어노테이션을 부여해주면 이런 클래스를 자동으로 찾아서 빈으로 등록해주게 할 수 있다. 이런 방식을 `빈 스캐닝(scanning)`을 통한 자동인식 빈 등록 기능이라고 하고, 이런 스캐닝 작업을 담당하는 오브젝트를 `빈 스캐너(scanner)`라고 한다.

빈 스캐너는 지정된 클래스패스 아래에 있는 모든 패키지의 클래스를 대상으로 필터를 적용해서 빈등록을 위한 클래스들을 선별해낸다.

디폴트 필터인 `@Component` 을 포함해 디폴트 필터에 적용되는 어노테이션을 `스테레오타입` 어노테이션이라고 부른다

```java
import org.springframework.stereotype.Component;

@Component
public clss AnnotatedHello{
	...
}
```

- 위 소스의 클래스는 빈 스캐너가 감지해서 자동으로 빈으로 등록해주는 후보가 된다.
- 하나의 빈이 등록되려면 최소한 아이디와 클래스 이름이 메타정보로 제공돼야 한다.

빈 스캐너가 클래스를 감지하는 것이니 클래스 이름으 간단히 가져올 수있다.
그런데 빈의 아이디는 어디서 찾을 수 있을까?

> 빈 스캐너는 기본적으로 클래스 이름을 빈의 아이디로 사용한다.
> 정확히는 클래스 이름의 첫 글자만 소문자로 바꾼 것을 사용한다.

즉 위 코드의 클래스 이름은 `AnnotatedHello` 이니 빈의 아이디는 자동으로 `annotatedHello` 가 된다.

`AnnotationConfigApplicationContext`는 빈 스캐너를 내장하고 있는 애플리케이션 컨텍스트 구현 클래스다.
이 클래스의 오브젝트를 생성하면서 생성자에 빈 스캔 대상 패키지를 지정해주면 `스테레오타입 어노테이션`이 붙은 클래스를 모두 찾아
빈으로 등록해준다.

이렇게 어노테이션을 부여하고 자동스캔으로 빈을 등록하면 확실히 XML 에 설정하는것에 비하면 간단하긴 한다.
반면에 애플리케이션에 등록될 빈이 어떤 것들이 있고, 그 정의는 어떻게 되는지를 한눈에 파악할 수 없다는 단점이 있으며,
구성을 파악하기 어렵다.
또한, 이처럼 빈 스캔에 의해 자동등록되는 빈은 XML처럼 상세한 메타정보 항목을 지정할 수 없고, 클래스당 한개 이상의 빈을 등록할수 없다는 제한이 있다.

> 개발 중에는 생산성을 위해 빈 스캐닝 기능을 사용해서 빈을 등록하고, 개발이 어느정도 마무리되고 세밀한 관리와 제어가 필요한 운영 시점이 되면 XML 형태의 빈 선언을 적용하는 것도 좋은 전략이다.

애플리케이션 개발, 테스트, 인수 테스트, 운영과 같은 단계마다 독립된 설정정보를 두자.


> 자동인식을 통한 빈 등록을 사용하려면 다음 두 가지 방법 중 하나를 쓰면된다.

###### XML을 이용한 빈 스캐너 등록

XML 설정파일 안에 context 스키ㅏ의 전용 태그를 넣어서 간단히 빈 스캐너를 등록할 수 있다.
`springbook.learningtest.spring.ioc.bean` 패키지 밑의 모든 빈을 스캔하고 싶다면 XML 설정파일에 `<context:component-scan>` 태그를 넣어주기만 하면 된다.

```xml
<context:component-scan base-package="springbook.learningtest.spring.ioc.bean" />
```


###### 빈 스캐너를 내장한 애플리케이션 컨텍스트 사용

XML에 빈 스캐너를 지정하는 대신 아ㅖ 빈 스캐너를 내장한 컨텍스트를 사용하는 방법도 있다.
아래는 web.xml 안에 컨텍스트르 파라미터를 설정한 예다.

```xml
<context-param>
		<param-name>contextClass</param-name>
		<param-value>
		org.springframework.web.context.support.AnnotationConfigWebApplicationContext
		</param-value>
	</context-param>

	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>
			/WEB-INF/spring/root-context.xml
			/WEB-INF/applicationContext.xml
         // 하나 이상의 스캔 대상 패키지를 지정할 수도 있다. 이때는 각 패키지 사이에 공백을 넣어주면 된다.
		</param-value>
	</context-param>
```

만약 서블릿 컨텍스트라면 서블릿 안의 `<init-param>`을 이용해 동일한 정보를 설정해 주면 된다.

빈 클래스 자동인식에는 `@Component` 외에도 다음과 같은 스테레오타입 어노테이션을 사용할 수 있다.

- `@Repository`
- `@Service`
- `@Controller`

각 계층의 핵심 클래스에는 이 세 가지 스테레오타입 어노테이션을 사용하고, 특정 계층으로 분류하기 힘든 경우에는 `@Component`를 사용하는 것이 바람직하다.

##### 4) 자바 코드에 의한 빈 등록 : @Configuration 클래스의 @Bean apthem

의존관계 내에 있찌 않은 제3의 오브젝트를 만들어 의존관계를 가진 오브젝트를 생성하고 메소드 주입을 통해 의존관계를 만들어줬었다.
이처럼 오브젝트 생성과 의존관계 주입을 담당하는 오브젝트를 `오브젝트 팩토리`라고 불렀고,
`오브젝트 팩토리`의 기능을 일반화해서 컨테이너로 만든 것이 지그의 스프링 컨테이너, 즉 빈 팩토리라고 볼 수 있다.

XML처럼 간략한 표현이 간으한 문서를 이용해서 메타정보를 작성해두고, 컨테이너가 이를 참고해서 오브젝트를 생성하고 DI 해주도록 만드는 것이 효과적이다.
하지만 때로는 오브젝트 팩토리를 직접 구현했을 때처럼 자바 코드를 통해 오브젝트를 생성하고 DI해주는게 유용할 때가 있다.

스프링은 코드를 이용해서 오브젝트를 생성하고 DI를 진행하는 방식으로 만들어진 오브젝트를 빈으로 쓸 수 있는 방법을 제공한다.
바로 `자바코드에 의한 빈 등록기능`이다.

- 팩토리 빈과 달리 하나의 클래스 안에 여러 개의 빈을 정의할 수 있다.
- 어노테이션을 이용해 빈 오브젝트의 메타정보를 추가하는 일도 가능하다.
- XML을 통해 명시적으로 등록하지 않아도 된다.

> 사용방법

- `@Configuration` 어노테이션을 클래스위에 건다.
- `@Bean`을 빈으로 등록할 메소드에 건다.

XML문서와 대비해서 생각해보면 `<beans>` = `@Configuration`  /  `<bean>` = `@Bean` 


```java
@Configurtion
public class AnnotatedHelloConfig{
	@Bean
   public AnnotatedHello annotatedHello(){	// 메소드 이름이 등록되는 빈의 이름이다. 여기서는 annotatedHello
   	return new AnnotatedHello();	// 자바코드를 이용해 빈 오브젝트를 만들고, 초기화한 후에 리턴해준다.
      								 // 컨테이너는 이 리턴 오브젝트를 빈으로 활용한다.
   }
}
```

`AnnotationConfigApplicationContext`를 이용하면, 생성자 파라미터로 `@Configuration`이 부여된 클래스를 넣어주면 된다
이때 설정을 담은 자바 코드에 해당하는 클래스 자체도(AnnotatedHelloConfig) 하나의 빈으로 등록된다

> `@Bean`이 붙은 메소드에서 스프링 컨테이너가 얻을 수 있는 정보는 빈클래스의 종류와 빈의 이름뿐이다.
> 자연히 나머지 설정 메타정보는 디폴트 값이 되고, 이에 따라 스코프는 디폴트값인 `싱글톤`으로 만들어진다.

메소드 이름과 리턴 타입에서 얻을 수 있는 기초 메타정보 외에도 부가적인 어노테이션을 이용해 다양한 빈 메타정보 항목을 추가할 수도 있다.

> 자바 코드를 이용한 빈 등록은 단순한 빈 스캔닝을 통한 자동인식으로는 등록하기 힘든 기술 서비스 빈의 등록이나 컨테이너 설정용 빈을 XML 없이 등록하려고 할 때 유용하게 쓸 수 있다.


###### 자바 코드에 의한 설정이 XML과 같은 외부 설정파일을 이용하는 것보다 유용한 점을 몇가지 살펴보자.

- 컴파일러나 IDE를 통한 타입 검증이 가능하다.
- 자동완성과 같은 IDE 지원 기능을 최대한 이용할 수 있다.
- 이해하기 쉽다.
- 복잡한 빈 설정이나 초기화 작업을 손쉽게 적용할 수 있다.


##### 5) 자바 코드에 의한 빈 등록 : 일반 빈 클래스의 @Bean 메소드

자바 코드에 의한 빈 등록은 기본적으로 `@Configuration` 어노테이션이 붙은 설정 전용 클래스를 이용한다.
그런데 @Configuration이 붙은 클래스가 아닌 일반 POJO 클래스에도 @Bean을 사용할 수 있다.

일반 클래스에서 @Bean을 사용할 경우 싱글톤으로 불러와지지 않기때문에 위험하다
그래서 항상 private으로 선언해두고, 클래스 내부에서도 DI를 통해 찹조해야지 메소드를 직접 호출하면 안된다.


##### 빈 등록 메타정보 구성 전략

지금까지 다섯 가지 대표적인 빈 등록 방법을 간단히 살펴봤다.
다섯 가지 방법 모두 장단점이 있고 사용하기 적절한 조건이 있다.
또한 빈 등록 방법은 꼭 한가지만 사용해야되는 것도 아니다.
즉 두가지 이상의 방법을 섞어서 사용할 수도 있다는 말이다.

대표적인 설정 방법들을 한번 살펴보자


> 1) XML 단독사용

- 컨텍스트에서 생성되는 모든 빈을 XML에서 확인할 수 있다는 장점이 있다.
- 빈의 개수가 많아지면 XML파일을 관리하기 번거로워 진다.
- 모든 설정정보를 자바 코드에서 분리하고 순수한 POJO 코드를 유지하고 싶다면 가장 좋은 선택이다
- BeanDefinition을 코드에서 직접 만드는 방법을 제외하면 모든 종류의 빈 설정 메타정보 항목을 지정할 수 있는 유일한 방법이다

> 2) XML과 빈 스캐닝 혼용

- 두가지를 혼용하는 방법
- 애플리케이션 3계층의 핵심 로직을 담고 있는 빈 클래스는 그다지 복잡한 빈 메타정보를 필요로 하지 않는다.
대부분 싱글톤이며 클래스당 하나만 만들어지므로 빈 스캐닝에 의한 자동인식 대상으로 적절하다.
- 그외 자동인식 방식이 불편한 기술서비스, 기반서비스, 컨테이너 설정 등은 XML로 등록하여 사용한다.
- 빈 스캐닝과 XML 각 방법의 장점을 잘 살려서 사용하면 된다.
- 단 이때는 컨텍스트 계층구조 내에서 같은 클래스의 빈이 중복등록될 수 있으니 주의하자.
- XML을 사용하는 애플리케이션 컨텍스트를 기본으로 하고, 다음과 같이 빈 스캐너를 context 스키마의 `<context:component-scan>`태그를 이용해 등록해주면 된다.
```xml
<context:component-scan base-packge="com.mycompany.app" />
```
- `component-scan`을 사용하면 자바 코드에 의한 설정 방식을 하께 적용할 수도 있다.
- `@Configurtion`이 붙은 클래스를 빈 스캔의 대상으로 만들거나 직접 `<bean>`태그로 등록해주면 된다.

> 3) XML 없이 빈 스캐닝 단독 사용
- 아예 모든 빈의 등록을 XML 없이 자동스캔만으로 가져가는 방식
- 이때 자바 코드에 의한 빈 등록 방법이 반드시 필요하다.
- 주요한 컨테이너 설정과 기반 서비스를 위한 설정은 모두 자바 코드에 의한 빈 설정 방식을 사용해야 한다. (스프링 3.0부터 가능)


#### 빈 의존관계 설정 방법

빈 오브젝트 사이의 DI를 위한 의존관계 메타정보를 작성하는 방법을 알아보자.

1. DI 할 대상을 선정하는 방법으로 분류

- 명시적으로 구체적인 빈을 지정하는 방법 
	> DI할 빈의 아이디를 직접 지정
- 규칙에 따라 자동으로 선정하는 방법 
	> 주로 타입 비교를 통해서 호환되는 타입의 빈을 DI후보로 삼는방법 `자동와이어링`이라 부른다.

2. 메타정보 작성 방법으로  분류
- XML `<bean>`태그
- 스키마를 가진 전용 태그
- 어노테이션
- 자바코드에 의한 직접적인 DI

이 네가지 방법은 다시 명시적으로 빈을 지정하는 방식과 자동 선정 방식으로 구분할 수 있다.
결과적으로 총 8가지의 빈 의존관계 주입 방법이 있다고 보면 된다.

> 빈 의존관계 메타정보 작성 방법을 살펴보자

##### XML : `<property>`, `<constructor-arg>`
- `<bean>`을 이용해 빈을 등록했다면 프로퍼티와 생성자 두 가지 방식으로 DI를 지정할 수 있다.
- 프로퍼티 : 자바빈 규약을 따르는 수정자 메소드 사용
- 생성자 : 빈 클래스의 생성자를 이용하는 방법

두 가지 방법 모두 파라미터로 의존 오브젝트 또는 값을 주입해준다.

##### <property> : 수정자 주입
- 수정자를 통해 의존관계에 빈을 주입하려면 `<property>` 태그를 사용할 수 있다.
- DI의 가장 대표적인 방법
- 하나의 프로퍼티가 하나의 빈 또는 값을 DI 하는 데 사용될 수 있다
- `ref` 애트리뷰트를 사용하면 빈 이름을 이용해 주입할 빈을 찾는다.
- `value` 애트리뷰트는 단순 값 또는 빈이 아닌 오브젝트를 주입할 때 사용한다.
- `value` 애트리뷰트로 넣을 수 있는 값의 타입에는 제한이 없다.

##### `<constructor-arg>`
- 생성자를 통한 빈 또는 값의 주입에 사용된다.
- 생성자의 파라미터를 이용하기 때문에 한 번에 여러 개의 오브젝트를 주입할 수 있다.
- 생성자 파라미터는 수정자 메소드처러 간단히 이름을 이용하는 대신 파라미터의 순서나 타입을 명시하는 방법이 필요하다. 
(특히 같은 타입이 여럿 있는 경우 주의해야 한다.)


##### XML : 자동와이어링
- XML 문서의 양을 대폭 줄여줄수 있는 획기적인 방법
- 명시적으로 프로퍼티나 생성자 파라미터를 지정하지 않고 미리 정해진 규칙을 이용해 자동으로 DI 설정을 컨테이너가 추가하도록 만드는것
- 자동으로 관계가 맺어져야 할 빈을 찾아서 연결해준다는 의미로 자동와이어링이라고 부른다.
- 이름을 사용하는 자동와이어링과 타입을 사용하는 자동와이어링 두 가지가 있다.

1) byName : 빈 이름 자동와이어링

많은 경우 프로퍼티의 이름과 프로퍼티에 DI할 빈의 이름이 비슷하거나 같다.
보통 빈의 이르은 클래스 이르이나 구현한 대표적인 인터페이스 이름을 따른다.
`<property>`를 사용해 명시적으로 DI 설정을 해주는 XML을 살펴보면 다음과 같이 프로퍼티 이름과 빈 아이디에 같은 이름이 반복적으로 나타나는 모습을 쉽게 찾을 수 있다.

```xml
<bean id="hello"...>
	<property name="printer" ref="printer" />
</bean>

<bean id="printer" class="..StringPrinter" >
```

빈 이름 자동와이어링은 위와같이 이름이 같은 관례를 이용하는 방법으로 hello 빈의 `<bean>` 태그에 다음과 같이 `autowire` 모드를 지정하면
`<property name="printer" ...>`를 생략할 수 있다.

```xml
<bean id="hello" class="...Hello" autowire="byName">
	<property name="printer" value="Spring" />
</bean>

<bean id="printer" class="..StringPrinter" >
```

> 이때 `<beans>`의 디폴트 자동와이어링 옵션을 줄 수 도 있다.

```xml
<beans default-autowire="byName">
	<bean>....</bean>
</beans>
```

> 이처럼 이름에 의한 자동와이어링 방식을 사용하는 경우는 대부분 디폴트 자동와이어링 기법을 사용한다.
> 한 두개의 빈만 자동와이어링을 사용할 경우 별 장점이 없기 때문이다.

2) byType : 타입에 의한 자동와이어링

타입에 의한 자동와이어링은 프로퍼티의 타입과 각 빈의 타입을 비교해서 자동으로 연결해주는 방법이다.
이름을 이용하는 자동와이어링 방식은 명명 규칙을 엄격하게 지켜야만 한다.
예를 들자면 이전에 만들어진 클래스를 재사용하거나 규모가 큰 프로젝트라 모든 개발자가 규칙에 따라 정확하게 이름을 부여하기가 어렵다면 이르대신 타입에 의한 자동와이어링을 사용할 수 있다.

> 타입에 의한 기법도 `<beans default-autowire="byType"> 으로 사용할 수 있다.

이 방식에 장점만 있는건 아니고 단점도 있는데,
같은 빈이 두 개 이상 존재하는 경우에는 적용되지 못한다.
또한 성능에 문제가 있다. (상대적으로 이름에 의한 와이어링보다 느리다)


##### 1) 어노테이션 : @Resource
XML 대신 어노테이션을 이용해 빈의 의존관계를 정의할 수 있는 방법은 두 가지가 있다.

먼저 `@Resource`는 `<property>` 선언과 비슷하게 주입할 빈을 아이디로 지정하는 방법이다.
이때 자바클래스의 수정자뿐만 아니라 필드에도 붙일 수 있다.

(1) 수정자 메소드

수정자~setter~는 가장 대표적인 DI방법이다.
수정자 메소드를 이용해 오브젝트 외부에서 내부로 다른 오브젝트의 레퍼런스 또는 값을 전달할 수 있는 주입 경로가 된다.

```java
public class Hello{
	private Printer printer;
   ...
   @Resource(name="printer")
   public void setPrinter(Printer printer){
   	this.printer=printer;
   }
}
```

수정자 메소드의 `@Resource`는 XML의 `<property>`태그에 대응된다고 볼 수 있다.

> 참고할점은 프로퍼티의 이름은 따로 정해준게 없다는건데. 이는 자바빈의 수정자메소드의 관례를 따라서 메소드 이름으로부터 프로퍼티 이름을 끌어낼 수 있기 때문이다.
> 이렇게 이름이나 타입과 같은 소스코드의 메타정보를 활용할 수 있다는 게 어노테이션 방식의 장점이다.

@Resource와 같은 어노테이션으로 된 의존관계 정보를 이용해 DI가 이뤄지게 하려면 다음 세가지 방법 중 하나를 선택해야 한다.

- XML의 `<context:annotation-config />`
- XML의 `<context:component-scan />`
- AnnotationConfigApplicationContext 또는 AnnotationConfigWebApplicationContext


`<context:annotation-config />` 에 의해 등록되는 빈 후처리기는 AOP의 자동 프록시 생성기처럼 새로운 빈을 등록해주지는 않는다.
대신 이미 등록된 빈의 메타정보에 프로퍼티 항목을 추가해주는 작업을 한다.

`<context:component-scan />` 은 빈 등록도 자동으로 이뤄지기 때문에 `<bean>`은 모두 제거해도 된다. 대신 빈 스캐닝을 위해 Hello와 StringPrinter클래스에 `@Component~를 붙여줘야 한다.
빈 스캐닝은 항상 어노테이션 의존관계 설정을 지 원한다고 기억하면 된다.


(2) 필드

`@Resource`는 필드에도 붙을 수 있다. 다음과 같이 지정하면 이 필드의 정보를 참고해서 프로퍼티를 추가해준다.

```java
@Component
public class Hello{
	@Resource(name="printer")
   private Printer printer;
	
   // setPrinter() 메소드 없음
}
```

이처럼 필드에 붙어 있을 때는 그에 대응되는 수정자가 없어도 상관없다.
이때 필드의 접근자는 `public`이 아니어도 상관없다.
위에서 볼 수 있듯이 private 필드에 setPrinter()메소드가 없어도 문제없다. 스프링이 알아서 필드에 바로 DI해주기 때문이다.
> 이런 방법을 `필드주입(field injection)`이라고 한다. 

필드 주입은 원한다면 수정자를 넣어도 되고 제거해도 상관없다.

수정자가 없는 필드 주입 `@Resource`의 대표적인 적용 예는 DAO에 DataSource를 DI 하는 경우다.

또한 아래처럼 참조하는 빈의 이름을 생략할수도 있는데
```java
@Resource
private Printer printer;
```
이는 name 엘리먼트를 생략하면 DI할 빈의 이름이 프로퍼티나 필드 이름과 같다고 가정한다. 즉 위의 경우는 name="printer" 라고 한 것이나 마찬가지다.

이처럼 자동으로 프로퍼티 이름과 참조할 빈 이름을 결정해준다는 면에서 XML에서 사용하는 이름을 이용한 자동와이어링과 비슷하게 보이기도 하는데
++몇 가지 차이점이 있다.++

XML의 자동와이어링은 각 프로퍼티에 주입할 만한 후보 빈이 없을 경우에 무시하고 넘어간다.
즉 Hello클래스에 setPrinter() 메소드가 있지만, 그에 대응되는 printer라는 이름의 빈이 없는 경우에도 에러가 나지 않는다.
단지 빈 전체 프로퍼티 중에서 DI 가능한 것만 찾아내서 주입해주는 상당히 느슨한 방법이다.

반면에 @Resource는 자동와이어링 처럼 프로퍼티 정보를 코드와 관례를 이용해서 생성해내지만 DI 적용 여보를 프로퍼티마다 세밀하게 제어할 수 있다는 점에서 다르다.

@Resource가 붙어 있으면 반드시 참조할 빈이 존재해야 한다. 만약 DI할 빈을 찾을 수 없다면 예외가 발생한다.

@Resource는 기본적으로 참조할 빈의 이름을 이용해서 빈을 찾는다.





























