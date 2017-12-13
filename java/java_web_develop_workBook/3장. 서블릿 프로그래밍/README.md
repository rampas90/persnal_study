## 3. 서블릿 프로그래밍

### 1) 서블릿(servlet)이란?

자바에서는 웹 브라우저와 웹서버를 활용하여 좀 더 쉽게 서버 어플리케이션을 개발할 수 있도록 `서블릿(Servlet)`이라는 기술을 제공하고 있다.
이 서블릿 기술을 이용하여 웹 어플리케이션을 개발하는 것을 보통 `서블릿 프로그래밍`이라 부른다.

### 2) CGI 프로그램과 서블릿

사용자가 직접 아이콘을 더블 클릭하거나 명령 창(터미널)을 통해 실행시키는 프로그램을 일반적으로 '애플리케이션' 또는 '데스크톱 애플리케이션'이라고 한다.
반면에 사용자가 웹을 통해 간접적으로 실행시키는 프로그램이 ' 웹 어플리케이션'이다.

`웹 어플리케이션`이 실행은 `웹브라우저`가 `웹서버`에게 실행을 요청하고, `웹 서버`는 `클라이언트`가 요청한 프로그램을 찾아서 실행한다. 해당 프로그램은 작업을 수행한 후 그 결과를 웹서버에게 돌려준다.
그러면 웹서버는 그결과를 `HTTP` 형식에 맞추어 웹 브라우저에게 보낸다.

> 이때 웹서버와 프로그램 사이의 데이터를 주고받는 규칙을 `CGI(Common Gateway Interface)`라고 한다

> 이렇게 웹서버에 의해 실행되며 `CGI` 규칙에 따라서 웹 서버와 데이터를 주고 받도록 작성된 프로그램을 `CGI 프로그램`이라고 한다.

##### CGI 프로그램
- `CGI`프로그램은 `C`나 `C++`, `Java` 와 같은 컴파일 언어로 작성할 수 있으며 `Perl`, `PHP`, `Python`, `VBScript` 등 스크립트 언어로도 작성할 수 있다.
- 컴파일 방식은 기계어로 번역된 코드를 바로 실행하기 때문에 실행속도가 빠르지만, 변경사항이 발생할 때마다 다시 컴파일 하고 재배포 해야 하는 문제가 있다.
- 스크립트 방식은 실행할 때마다 소스코드의 문법을 검증하고 해석해야 하기 때문에 실행속도가 느리다.
- 하지만 변경사항이 발생하면 단지 소스코드를 수정하고 저장만 하면 되기때문에 편리하다.

##### 서블릿

- 자바 `CGI 프로그램`은 `C/C++` 처럼 컴파일 방식이다.
- 자바로 만든 `CGI 프로그램`을 `서블릿`이라고 부른다.
- 자바 서블릿이 다른 `CGI 프로그램`과 다른점은, 웹 서버와 직접 데이터를 주고받지 않으며, 전문 프로그램에 의해 관리된다는 점이다.

1) 서블릿 컨테이너

- 서블릿의 생성과 실행, 소멸 등 생명주기를 관리하는 프로그램을 `서블릿 컨테이너(Servlet Container)`라고 한다
- 서블릿 컨테이너가 서블릿을 대신하여 `CGI`규칙에 따라 웹 서버와 데이터를 주고 받는다.
- 서블릿 개발자는 더이상 `CGI 규칙`에 대해 알 필요가 없다. 대신 `서블릿 컨테이너`와 `서블릿 사이의 규칙`을 알아야 한다.
- 자바 웹 어플리케이션 개발자는 `JaveEE`기술 사양에 포함된 `Servlet`규칙에 따라 `CGI 프로그램` 을 만들고 배포한다.


### 3) 서블릿, JSP vs. JavaEE vs. WAS

이번 절에서는 자바 웹 프로그래밍 기술인 `서블릿`, `JSP`와 `JavaEE(Java Platform, Enterprise Edition)`의 관계를 이해하고, `WAS(Web Application Server)`가 무엇인지 알아보자

##### Java EE

> [자바오라클사이트](http://java.oracle.com) 사이트에 방문하여 Software Downloads 영역에서 `Java EE and GlassFish`링크를 클릭하여 `Technologies`탭을 클릭하면 `JavaEE`에 속한 하위 기술 목록이 출력된다.
> 여기서 웹어플리케이션 관련 기술을 확인할 수 있다.

- Java EE는 기능 확장이 쉽다.
- 이기종 간의 이식이 쉽다.
- 신뢰성과 보안성이 높다.
- 트랜잭션 관리와 분산 기능을 쉽게 구현할 수 있는 기술을 제공한다.
- `JavaEE`기술 사양은 한 가지 기술을 정의한 것이 아니라 기업용 어플리케이션과 클라우드 어플리케이션 개발에 필요한 여러가지 복합적인 기술들을 정의하고 모아 놓은 것이다.

##### WAS의 이해

- 클라이언트 서버 시스템 구조에서 서버쪽 애플리케이션의 생성과 실행, 소멸을 관리하는 프로그램을 `애플리케이션 서버(Application Server)`라 한다.
- 서블릿과 서블릿 컨테이너와 같이 웹 기술을 기반으로 동작되는 애플리케이션 서버를 `WAS`라고 부른다.
- 특히 `Java`에서 말하는 `WAS`란, `JavaEE`기술 사양을 준수하여 만든 서버를 가리킨다. 다른 말로 `JavaEE 구현체(Implementation)`라고도 말한다.

> 상용제품으로는 티맥스소프트의 `제우스(JEUS)`, 오라클의 `웹로직`, IBM의 `웹스피어`, 레드햇의 `제이보스엔터프라이즈`등이 있다. 
> 무료 또는 오픈소스로는 레드햇의 `제이보스 AS`, 오라클의 `GlassFish`, 아파치 재단의 `Geronimo` 등이 있다

---

> 이클립스를 이용한 웹프로젝트 생성 에제는 P.111 ~ 114 참조

```java
package lesson03.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/
public class Helloworld extends Servlet {
	ServletConfig config;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		System.out.println("init() 호출됨");
		this.config=config;
	}
	
	@Override
	public void destroy(){
		System.out.println("destroy() 호출됨");
	}
	
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException{
		System.out.println("service() 호출됨");
	}
	
	@Override
	public ServletConfig getServletConfig(){
		System.out.println("getServletConfig() 호출됨");
		return this.config;
	}
	
	@Override
	public String getServletInfo(){
		System.out.println("getServletInfo() 호출됨");
		return "version=1.0;author=eomjinyoung;copyright=eomjinyoung 2013";
	}
	
}
```

##### javax.servlet.Servlet 인터페이스

서블릿 클래스는 반드시 javax.servlet.Servlet 인터페이스를 구현해야 한다.
서블릿 컨테이너가 서블릿에 대해 호출할 메소드를 정의한 것이 Servlet 인터페이스다.


1) 서블릿의 생명주기와 관련된 메서드 : `init()`, `service()`, `destroy()`

`Servlet`인터페이스에 정의된 다섯 개의 메서드 중에서 서블릿의 생성과, 실행, 소멸, 즉 생명주기 Lifecycle 과 관련된 메서드가 `init()`, service(), `destroy()` 이다.

	init()
- 서블릿 컨테이너가 서블릿을 생성한 후 초기화 작업을 수행하기 위해 호출하는 메서드
- 서블릿이 클라이언트의 요청을 처리하기 전에 준비할 작업이 있다면 이 메서드에 작성해야한다.
- 예를 들어 이 메서드가 호출될 때 데이터베이스에 연결하거나 외부 스토리지 서버와의 연결, 프로퍼티 로딩 등 클라이언트 요청을 처리하는데 필요한 자원을 미리 준비할 수 있다.


	service
- 클라이언트가 요청할 때 마다 호출되는 메서드
- 실질적으로 서비스 작업을 수행하는 메서드로, 이 메소드에 서블릿이 해야 할 일을 작성하면 된다.
- 예제 소스는 단시 메소드가 호출되었음을 확인하기 위해 서버 실행 창으로 간단한 문구를 출력하도록 한것이다.


	destroy
- 서블릿 컨테이너가 종료되거나 웹 어플리케이션이 멈출 때, 또는 해당 서블릿을 비활성화 시킬때 호출된다.
- 따라서 이 메소드에는 서비스 수행을 확보했던 자원을 해제한다거나 데이터를 저장하는 등의 마무리 작업을 작성하면 된다.


##### 서블릿 배치 정보 작성

> WebContent/WEB-INF/web.xml 파일

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
  <display-name>Lesson03</display-name>
  
  <!-- 서블릿 선언 -->
  <servlet>
  	<servlet-name>Hello</servlet-name>
  	<servlet-class>lesson03.servlets.HelloWorld</servlet-class>
  </servlet>
  <servlet>
  	<servlet-name>Calculator</servlet-name>
  	<servlet-class>lesson03.servlets.CalculatorServlet</servlet-class>
  </servlet>
  
  <!-- 서블릿을 URL과 연결 -->
  <servlet-mapping>
  	<servlet-name>Hello</servlet-name>
  	<url-pattern>/Hello</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
  	<servlet-name>Calculator</servlet-name>
  	<url-pattern>/calc</url-pattern>
  </servlet-mapping>
  
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```

- web.xml 파일을 `배치 기술서` 또은 약어로 `DD파일` 이라고 부른다.
- 웹 애플리케이션의 배치 정보를 담고 있는 파일이다.
- 따라서 서블릿을 만들었으면 DD파일에 배치 정보를 등록해야 한다.
- 그래야만 클라이언트에서 해당 서블릿의 실행을 요청할 수 있다.
- DD파일에 등록되지 않은 서블릿은 서블릿 컨테이너가 찾을 수 없다.


	서블릿 선언
- 서블릿 배치 정보를 작성할 때 먼저 `<servlet>`태그를 사용하여 서블릿 별명과 서블릿 클래스명을 설정한다
- `<servlet-name>`은 서블릿 별명을 설정하며, 클래스 이름일 필요는 없다. 공백을 포함해도 된다
- `<servlet-class>`는 패키지 이름을 포함한 서블릿 클래스명을 설정한다.
```xml
<servlet>
  	<servlet-name>Hello</servlet-name>
	<servlet-class>lesson03.servlets.HelloWorld</servlet-class>
</servlet>
```


> 패키지명 + 클래스명 = Fully qualified name = QName

	서블릿에 URL 부여
- 클라이언트에서 서블릿의 실행을 요청할 때는 URL을 사용한다.
- 따라서 서블릿에 URL을 부여해야 클라이언트에서 요청할 수 있다.
- `<servlet-name>`은 `<setvlet>` 태그에서 정의한 서블릿 별명이 온다.
- `<url-pattern>`은 서블릿을 요청할 때 클라이언트가 사용할 URL을 설정한다.


---

##### 서블릿 구동 절차

1) 클라이언트의 요청이 들어오면 서블릿 컨테이너는 서블릿을 찾는다.

2) 만약 서블릿이 없다면, 서블릿 클래스를 로딩하고 인스턴스를 준비한 후 생성자를 호출한다. 그리고 서블릿 초기화 메서드인 inint()을 호출한다.

3) 클라이언트 요청을 처리하는 service() 메소드를 호출한다. 메소드 이름을 보면 이미 그 용도를 짐작할수 있다. 즉, 클라이언트의 요청에 대해 서비스를 제공한다는 뜻이다.

4) service() 메소드에서 만든 결과를 HTTP 프로토콜에 맞추어 클라이언트에 응답하는 것으로 요청처리를 완료한다.

5) 만약 시스템 운영자가 서블릿 컨테이너를 종료하거나, 웹 어플리케이션을 종료한다면,

6) 서블릿 컨테이너는 종료되기 전에 서블릿이 마무리 작업을 수행할 수 있도록 생성된 모든 서블릿에 대해 destroy() 메소드를 호출한다.


> 콘솔창을 살펴보면,

```java
init() 호출됨
service() 호출됨
```

위와 같이 호출되고 브라우져의 새로고침을 눌러보면 service()메소드만 추가되는 것을 볼 수 있다.
그 이유는 이미 Helloword 객체가 존재하기 때문이다.

> 서블릿 컨테이너는 클라이언트로부터 요청을 받으면 해당 서블릿을 찾아보고, 만약 없다면 해당 서블릿의 인스턴스를 생성한다.
> 서블릿 객체가 생성되면 웹 애플리케이션을 종료할 때까지 계속 유지한다.

	서블릿 인스턴스는 하나만 생성되어 웹 애플리케이션이 종료될 때까지 사용된다. 따라서 인스턴스 변수에 특정 사용자를 위한 데이터를 보관해서는 안된다. 도한, 클라이언트가 보낸 데이터를 일시적으로 보관하기 위해 서블릿의 인스턴스 변수를 사용해서도 안된다.


> destroy()가 호출되는 경우는 톰캣서버를 종료하는 것이다.



---

##### 웰컴 파일들

- 웹서버에게 요청할때 서블릿 이름을 생략하고 디렉터리 위치까지만 지정한다면, 웹 서버는 해당 디렉터리에서 웰컴 파일을 찾아서 보내준다.
- 웰컴파일의 이름은 `web.xml`의 `<welcome-file-list>`태그를 사용하여 설정할 수 있다.
- 여러개의 웰컴파일을 등록하게 되면 디렉토리에서 웰컴파일을 찾을 때 위에서부터 순차적으로 조회하여 먼저 찾은 것을 클라이언트로 보낸다.

```xml
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
```


---


### 4) GenericServlet의 사용

##### GenericServlet이 없던 시절
> 지금까지는 서블릿클래스를 만들 때 Servlet 인터페이스를 구현했다.
> 인터페이스를 구현하려면 인터페이스에 선언된 모든 메소드를 구현해야 하므로, 서블릿을 만들 때마다 Servlet인터페이스에 선언된 다섯 개의 메소드를 모두 구현했다.
> 사실 이 메소드 중에서 반드시 구현해야 하는 메소드는 `service()`다. 클라이언트가 요청할 때마다 호출되기 때문이다.

> `init()`의 경우 서블릿이 생성될 때 딱 한번 호출되는데, 서블릿을 위해 특별히 준비해야 하는 작업이 없다면 굳이 구현할 필요가 없다. (`destroy()`메소드도 마찬가지)

> 그럼에도 '인터페이스를 구현하는 클래스는 반드시 인터페이스에 선언된 모든 메소드를 구현해야 한다.'라는 것이 자바의 법이기 때문에 다음과 같이 빈 메소드라도 구현해야 한다.

```java
public void init(ServletConfig config) throws ServletException{
	this.config = config;
}
public void destroy(){}
```

> 이런 불편한 점을 해소하기 위해 등장한 것이 바로 `GenericServlet` 추상 클래스이다.


##### GenericServlet의 용도

- `GenericServlet`은 추상클래스라는 말로 짐작할 수 있듯이 하위 클래스에게 공통의 필드와 메소드를 상속해 주고자 존재한다.
- 서블릿 클래스가 필요로 하는 init(), destroy(), getServletConfig(), getServletInfo()를 미리 구현하여 상속해 준다.
- service()는 어차피 각 서블릿 클래스마다 별도로 구현해야 하기 때문에 `GenericServlet`에서는 구현하지 않는다.

서블릿을 만들 때 `GenericServlet`을 상속받는다면 `Servlet` 인터페이스의 메소드 중에서 service()만 구현하면 된다.

> 이전절의 `HelloWorld` 클래스가 `GenericServlet`을 상속받는다면 그 코드는 다음과 같을 것이다

```java
public class HelloWorld extends GenericServlet{
	@Override
    public void service(ServletRequest request, ServletResponse response) 
    	throws ServletException, IOException{
        	System.out.println("service() 호출됨");
        }
}
```



##### ServletRequest

> 계산기 예제소스를 통해 알아보자

```java
// 생략
@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		int a = Integer.parseInt( request.getParameter("a") );
		int b = Integer.parseInt( request.getParameter("b") );

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println("a=" + a + "," + "b=" + b + "의 계산결과 입니다.");
		writer.println("a + b = " + (a + b));
		writer.println("a - b = " + (a - b));
		writer.println("a * b = " + (a * b));
		writer.println("a / b = " + ((float)a / (float)b));
		writer.println("a % b = " + (a % b));
	}
```

service()의 매개변수 중에서 `ServletRequest` 객체는 클라이언트의 요청 정보를 다룰 때 사용한다.
이 객체의 주요 기능 몇 가지를 알아보자
위 소스에서 사용한 `getParameter()`는 GET 이나 POST 요청으로 들어온 매개변수 값을 꺼낼때 사용한다.


`http://...생략.../calc?a=20&b=30`
```java
request.getParameter("a");
```


1) getRemoteAddr() 
- 서비스를 요청한 클라이언트의 IP주소를 반환합니다.

2) getScheme()
- 클라이언트가 요청한 URI 형식 Shceme을 반환합니다..
- 즉 URL에서 `:`문자 전에 오는 값을 반환합니다. URL에서 스킴의 의미는 자원을 식별하는 최상위 분류 기호다
- 스킴의 예로 `http`, `https`, `ftp`, `file`, `news`등이 있다.

3) getProtocol()
- 요청 프로토콜의 이름과 버전을 반환한다.
- 예 : HTTP/1.1

4) getParameterNames()
- 요청정보에서 매개변수 이름만 추출하여 반환한다.

5) getParameterValues()
- 요청정보에서 매개변수 값만 추출하여 반환한다.

6) getParameterMap()
- 요청정보에서 매개변수들을 Map 객체에 담아서 반환한다.

7) setCharacteEncoding()
- POST 요청의 매개변수에 대해 문자 집합을 설장한다.
- 기본값은 `ISO-8859-1`로 설정되어 있다.
- 매개변수의 문자 집합을 정확히 지정해야만 제대로 변환된 유니코드 값을 반환한다.
- 매개변수의 문자 집합을 지정하지 않으면 무조건 ISO-8859-1이라 가정하고 유니코드로 변환한다.
- 주의할 점은 처음 `getParameter()`를 호출하기 전에 이 메서드를 먼저 호출해야만 적용이 된다.


---

##### ServletResponse

- `ServletResponse`객체는 응답과 관련된 기능을 제공한다.
- 클라이언트에게 출력하는 데이터의 인코딩 타입을 설정하고, 문자집합을 지정하며, 출력 데이터를 임시보관하는 버퍼의 크기를 조정하거나, 데이터를 출력하기 위해 출력 스트림을 준비할 때 이 객체를 사용한다.

1) setContentType()
- 출력할 데이터의 인코딩 형식과 문자 집합을 지정한다.
- 클라이언트에게 출력할 데이터의 정보를 알려주어야 클라이언트는 그 형식에 맞추어 올바르게 화면에 출력(Rendering)할 수 있다.
- 예를 들어 `HTML`형식이면 태그 규칙에 맞추어 화면에 출력할 것이고, `XML`형식이면 각 태그를 트리 노드로 표현할 것이다.

2) setCharacterEncoding()
- 출력할 데이터의 문자 집합을 지정한다.
- 기본값은 `ISO-8859-1`이다.
- 가령 아래 코드는 출력할 데이터의 문자 집합을 'UTF-8'로 설정하고 있다는 뜻이다. 즉 데이터를 출력할때 유니코드 값을 UTF-8 형식으로 변환하라는 뜻이다.
`response.setCharacterEncoding("UTF-8");`
- 출력 데이터의 문자 집합은 다음과 같이 `setContentType()`을 사용하여 설정할 수도 있다.
`response.setContentType("text/plain;chartest=UTF-8");`

3) getWriter()
- 클라이언트로 출력할 수 있도록 출력 스트림 객체를 반환한다.
- 이미지나 동영상과 같은 바이너리 데이터를 출력하고 싶을때는 `getOutputStream()`을 사용하자



---



### 5) 정리

##### CGI

> 웹 서버가 실행시키는 프로그램을 `웹 어플리케이션`이라고 한다.
웹 서버와 웹 어플리케이션 사라이에는 데이터를 주고받기 위한 규칙이 있는데 이것을 `CGI(Common Gateway Interface)`라고 한다.
그래서 보통 웹 어플리케이션을 `CGI프로그램`이라고도 부른다.


##### 서블릿
> 특히 자바로 만든 웹 어플리케이션을 서블릿이라고 부르는데, Server와 Applet의 합성어다
> 즉, '클라이언트에게 서비스를 제공하는 작은 단위의 서버프로그램'이라는 뜻이다.

##### 서블릿컨테이너
> 서블릿의 생성에서 실행, 소멸까지 서블릿의 생명주기를 관리하는 프로그램이다.
> 클라이언트로부터 요청이 들어오면, 서블릿 컨테이너는 호출 규칙에 따라 서블릿의 메소드를 호출한다.
> 서블릿 호출 규칙은 javax.servlet.Servlet 인터페이스에 정의되어 있다.
> 따라서 서블릿을 만들 때는 반드시 `Servlet 인터페이스`를 구현해야만 한다.

##### JavaEE
> `Servlet`라는 규칙 외에 `JSP`를 만들고 실행하는 규칙, `EJB(Enterprise JavaBeans)`라는 분산 컴포넌트에 관한 규칙, 웹 서비스에 관한 규칙 등 기업용 어플리케이션 제작에 필요한 기술들의 사양을 정의한 것을 `JavaEE`라고 한다.
> `JavaEE`는 이기종 간의 이식이 쉬우며, 신뢰성과 보안성이 높고 트랜잭션 관리와 분산기능을 쉽게 구현할 수 있는 기술을 제공한다.

##### WAS
> JavaEE 사양에 따라 개발된 서버를 `Java EE 구현체(implementation)` 또는 `WAS`라고 부른다.

##### 서블릿 라이브러리
> 서블릿을 좀 더 편리하게 개발할 수 있도록 javax.servlet.GenericServlet 이라는 추상클래스를 제공한다.
> GenericServlet 추상클래스는 Servlet 인터페이스에 선언된 메소드 중에서 service()를 제외한 나머지 메소드를 모두 구현해놓았다.
> 



