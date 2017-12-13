## 6. 미니 MVC 프레임워크 만들기
 
> 요지
대부분의 프로젝트들은 프레임워크 기반으로 개발을 진행한다.
그 이유는 여러가지가 있지만, 문제는 프레이워크를 사용하는 개발자다.
프레임워크를 기반으로 개발을 진행하다 보면 5년, 10년이 지나도 실력이 늘지 않는 경우가 많고,
프레임워크로 해결할 수 없는 요구사항이 들어오면 어떻게 대처해야할지 막막하기도 하다.
그렇다고, 프레임워크를 안 쓸수는 없고, 정확하게 알고 제대로 쓰기위해 무엇이 필요한지 알아보자

- 디자인 패턴과 라이브러리를 적용하여 MVC 프레임워크를 직접 만들어보자
- 스프링 프레임워크를 모방하여 작은 웹 MVC 프레임워크를 만들어보자.
- 프레임워크를 만드는 과정 속에서 디자인 패턴의 응용과 오픈소스 라이브러리의 사용, 자바 리플렉션 API와 어노테이션을 사용하는 방법을 익혀보자.


### 프런트 컨트롤러의 도입

`프런트 컨트롤러`라는 디자인 패턴을 이용하여 좀 더 유지보수가 쉬운 구조로 MVC를 개선해보자.
컨트롤러를 만들다 보면 요청데이터를 처리하는 코드나 모델과 뷰를 제어하는 코드가 중복되는 경우가 있다.
중복 코드들은 유지보수를 어렵게 하므로 `프런트 컨트롤러`를 통해 이 문제를 해결해 보자.

#### 프런트 컨트롤러 패턴

이전 장에서 배운 MVC는 컨트롤러가 하나였지만, 프런트 컨트롤러 디자인 패턴에서는 두 개의 컨트롤러를 사용하여 웹브라우저 요청을 처리한다.
즉 기존에 서블릿이 단독으로 하던 작업을 프론트 컨트롤러와 페이지 컨트롤러, 두 개의 역할로 나누어 수행하는 것이다.

프런트 컨트롤러는 VO 객체의 준비, 뷰 컴포넌트로의 위임, 오류 처리등과 같은 공통 작업을 담당하고, 페이지 컨트롤러는 이름 그대로 요청한 페이지만을 위한 작업을 수행한다.

> 디자인 패턴

자바가상머신이 가비지를 찾아서 자동으로 없애 준다고 하지만, 이 작업 또한 CPU를 사용하는 일이기에 시스템 성능에 영향을 끼친다.
따라서 개발자는 늘 인스턴스의 생성과 소멸에 대해 관심을 가지고 시스템 성능을 높일 수 있는 방법으로 구현해야 한다.

이런 개발자들의 노력은 시스템에 적용되어 많은 시간 동안 시스템이 운영되면서 검증된다.
디자인 패턴은 이렇게 검증된 방법들을 체계적으로 분류하여 정의한 것이다.


> 프레임워크

디자인 패턴이 특정 문제를 해결하기 위한 검증된 방법이라면, 프레임워크는 이런 디자인 패턴을 적용해 만든 시스템중에서 
우수사례를 모아 하나의 개발 틀로 표준화시킨 것이다.
프레임 워크의 대표적인 예로 스프링이 있으며, 이보다 기능은 작지만, MVC 프레임워크로 특화된 스트럿츠가 있다.

Mybatis는 데이터베이스 연동을 쉽게 해주는 프레임워크다.
자바스크립트를 위한 프레임워크인 Angular와 Ember도 있으니 참고하자.

#### 프런트 컨트롤러만들기


```java
package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	
	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		
		try{
			String pageControllerPath = null;
			
			if("/member/list.do".equals(servletPath)){
				pageControllerPath="/member/list";
			}
			else if("/member/add.do".equals(servletPath)){
				pageControllerPath="/member/add";
				if (request.getParameter("email") != null) {
			          request.setAttribute("member", new Member()
			            .setEmail(request.getParameter("email"))
			            .setPassword(request.getParameter("password"))
			            .setName(request.getParameter("name")));
		        }
				
			}
			else if("/member/update.do".equals(servletPath)){
				pageControllerPath="/member/update";
				if(request.getParameter("email") != null){
					request.setAttribute("member", new Member()
						.setNo(Integer.parseInt(request.getParameter("no")))
						.setEmail(request.getParameter("email"))
						.setName(request.getParameter("name")));
				}
			}
			else if("/member/delete.do".equals(servletPath)){
				pageControllerPath="/member/delete";
			}
			else if("auth/login.do".equals(servletPath)){
				pageControllerPath="/auth/login";
			}
			else if("auth/logout.do".equals(servletPath)){
				pageControllerPath="/auth/logout";
			}
			
			RequestDispatcher rd=request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);
			
			String viewUrl=(String) request.getAttribute("viewUrl");
			if(viewUrl.startsWith("redirect:")){
				response.sendRedirect(viewUrl.substring(9));
				return;
			}
			else{
				rd=request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
		} catch (Exception e){
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd=request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
```

프런트 컨트롤러도 서블릿이기 때문에 HttpServlet을 상속받는다.

```java
service(HttpServletRequest request, HttpServletResponse response)
```

> 여기서 주목할 점은 오버라이딩하는 메소드다. 

분명히 처음엔 `Servlet`을 상속받아 `Servlet인터페이스`에 선언된 다섯개의 메소드를 구현했고,
사실상 `service()`메소드만 구현해도 되기때문에 `init()`, `destroy()`, `getServletConfig()`, `getServletInfo()`를 미리 구현하여 상속해준 `GenericServlet`을 사용했다.
거기에 또다시` service()메소드`를 `doGet()`과 `doPost()`, `doPut()`등으로 상황에 따라 나누어 구현하기 위헤 `HttpServlet`을 상속받아 사용했었다

> 그런데 왜 이제와서 `HttpServle`t 을 상속받아놓고 `doGet()`, `doPost()`가 아니라 `service()`를 구현했을까?

이 메소드의 매개변수를 살펴보면, `ServletRequest`와 `ServletResponse`가 아니라 `HttpServletRequest`와 `HttpServletResponse`이다 

**즉 Servlet 인터페이스에 선언된 메소드가 아니라는 뜻이다.**

```java
protected void service(HttpServletRequest request, HttpServletResponse response)
```

그럼 이 메소드는 서블릿 컨테이너(톰캣)가 직접 호출하지 않는다는 것인데, 도대체 언제 호출되는 걸까? 순서를 통해 살펴보자

1. 클라이언트로부터 요청이 들어오면 서블릿 컨테이너는 규칙에 따라 Servlet인터페이스에 선언된 service() 메소드를 호출한다.

2. 이 `service(ServletRequest, ServletResponse)` 메소드는 HttpServlet 클래스에 추가된 동일한 이름의 `service(HttpServletRequest, HttpServletResponse)`메소드를 호출한다.  이름은 같지만, 매개변수가 다르다

3. service(HttpServletRequest, HttpServletResponse)메소드는 HTTP 요청 프로토콜을 분석하여 다시 doGet(),doPost()등을 호출한다.

어찌 되었든 GET요청이든, POST요청이든 service(HttpServletRequest, HttpServletResponse)메소드가 호출된다는 것이다.
그래서 이 메소드를 오버라이딩 한 것이다.


> 그럼 여기서 다시 의문 " 차라리 doGet(),doPost()를 오버라이딩하지 왜 궂이 service()를 오버라이딩하지?"

물론 doGet(), doPost()를 오버라이딩하여 `프론트 컨트롤러`를 만들 수 있다.
그럼에도 service()를 오버라이딩 한 이유는 
첫째, GET, POST뿐만 아니라 다양한 요청방식에도 대응하기 위해서다.
둘째, 가능한 HttpServlet의 내부 구조를 조금이라도 알아보기 위해서다



###### 요청URL에서 서블릿 경로 알아내기

프론트 컨트롤러의 역할은 클라이언트의 요청을 적절한 페이지 컨트롤러에게 전달하는 것이다.
그러려면 클라이언트가 요청한 서블릿의 URL을 알아내야 한다.
예를 들어 요청 URl이 다음과 같다고 가정해보자

`http://localhost:9999/web06/member/list.do?pageNo=1&pageSize=10`

| 메소드 | 설명 | 반환값
|--------|--------|--
| getRequestURL() | 요청 URL 리턴(단, 매개변수 제외) | http://localhost:9999/web06/member/list.do
| getRequestURI() | 서버 주소를 제외한 URL | /web06/member/list.do
| getContextPath() | 웹 어플리케이션 경로 | /web06
| getServletPath() | 서블릿 경로 | /member/list.do
| getQueryString() | 요청 매개변수 정보 | pageNo=1&pageSize=10

위의 표를 참고하여 클라이언트가 요청한 서블릿의 경로를 알고 싶다면 `getServletPath()`를 호출하면 된다.

###### 페이지 컨트롤러로 위임
서블릿 경로에 따라 조건문을 사용하여 적절한 페이지 컨트롤러를 인클루딩 한다 아래는 위 전체 코드중 
해당 부분을 보기 좋게 축약한 소스다

```java
	String pageControllerPath = null;
	if("/member/list.do".equals(servletPath)){
		pageControllerPath="/member/list";
	}
	else if("/member/add.do".equals(servletPath)){
		pageControllerPath="/member/add";
		...
	}
	else if("/member/update.do".equals(servletPath)){
		pageControllerPath="/member/update";
		...
	}
	else if("/member/delete.do".equals(servletPath)){
		pageControllerPath="/member/delete";
	}
	else if("auth/login.do".equals(servletPath)){
		pageControllerPath="/auth/login";
	}
	else if("auth/logout.do".equals(servletPath)){
		pageControllerPath="/auth/logout";
	}
   
   RequestDisptcher rd=request.getRequestDispatcher(pageControllerPath);
   rd.include(request,response);
```

###### 요청 매개변수로부터 VO 객체 준비
프론트 컨트롤러의 역할 중 하나는 페이지 컨트롤러가 필요한 데이터를 미리 준비하는 것이다.
가령 회원정보 등록과 변경은 사용자가 입력한 데이터를 페이지 컨트롤러에게 전달하기 위해, 요청 매개변수의 값을 꺼내서 VO객체에 담고, "member"라는 키로  ServletRequest에 보관했다

```java
request.setAttribute("member", new Member()
	.setEmail(request.getParameter("email"))
   .setPassword(request.getParameter("password"))
   .setName(request.getParameter("name")));
```

여기서는 쓸데없는 임시 변수의 사용을 최소화하기 위해 Member 객체를 생성한 후, 바로 값을 할당하고, ServletRequset에 보관했다.

###### JSP로 위임

페이지 컨트롤러의 실행이 끝나면, 화면 출력을 위해 ServletRequest에 보관된 뷰URL로 실행을 위임했다.
단 뷰 URL이 "redirec:" 로 시작한다면, 인클루딩 하는 대신에 sendRedirect()를 호출한다.

```java
	String viewUrl=(String) request.getAttribute("viewUrl");
	if(viewUrl.startsWith("redirect:")){
		response.sendRedirect(viewUrl.substring(9));
		return;
	}
	else{
		rd=request.getRequestDispatcher(viewUrl);
		rd.include(request, response);
	}
```


###### 오류처리

서블릿을 만들 때 매번 작성한 것 중의 하나가 오류처리다.
이제 프런트 컨트롤러에서 오류 처리를 담당하기 때문에, 페이지 컨트롤러를 작성할 때는 오류 처리 코드를 넣을 필요가 없다.

```java
catch (Exception e){
	e.printStackTrace();
	request.setAttribute("error", e);
	RequestDispatcher rd=request.getRequestDispatcher("/Error.jsp");
	rd.forward(request, response);
}
```



###### 프론트 컨트롤러의 배치

@WebServlet 어노테이션을 사용하여 프론트 컨트롤러의 배치 URL을 `*.do`로 지정했다.
즉 클라이언트의 요청 중에서 서블릿 경로 이름이 .do로 끝나는 경우는 DispatcherServlet이 처리하겠다는 의미다.

```java
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
```

___

#### MemberListServlet을 페이지 컨트롤러로 만들기

프론트 컨트롤러가 준비됬으니 기존의 서블릿을 페이지 컨트롤러로 변경해 보자.
먼저 MemberListServlet을 바꿔보자

> spm.servlet.MemeberListServlet 클래스

```java
package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;

// 프론트 컨트롤러 적용  
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");

			request.setAttribute("members", memberDao.selectList());

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
```

> 응답 데이터의 문자 집합은 프론트 컨트롤러에서 이미 설정했기 때문에 페이지 컨트롤러에서 코드를 제거했다.

```java
response.setContentType("text/html; charset=UTF-8");
```

> 또한, 화면 출력을 위해 JSP로 실행을 위임하는 것도 프론트 컨트롤러가 처리하기때문에 역시 제거했다.

```java
RequestDispatcher rd =request.getRequestDisptcher("/member/MemberList.jsp");
rd.include(request,response);
```

> 대신 JSP URL정보를 프론트 컨트롤러에게 알려주고자 ServletRequest 보관소에 저장한다.

```java
request.setAttribute("viewUrl", "/member/MemberList.jsp");
```
> 오류가 발생했을 때 오류 처리 페이지(/Error.jsp)로 실행을 위임하는 작업도 프론트컨트롤러가 하기 때문에 코드를 제거했다.

```java
e.printStackTrace();
request.setAttribute("error", e);
RequestDispatcher rd=request.getRequestDispatcher("/Error.jsp");
rd.forward(request, response);
```

대신 Dao를 실행하다가 오류가 발생한다면, 기존의 오류를 ServletException 객체에 담아서 던지도록 했다.

```java
throw new ServletException(e/);
```

service() 메소드는 ServletException을 던지도록 선언되어 있기 때문에 기존의 예외 객체를 그냥 던질 수 없다.
그래서 ServletException객체를 생성한 것이다.


___

#### 프론트 컨트롤러를 통한 회원 목록 페이지 요청

###### `*.do` 요청

요청 URL이 `.do`로 끝나기 때문에, DispatcherServlet이 요청을 받는다.
/member/list.do 요청을 처리할 페이지 컨트롤러(MemberListServlet)를 찾아 실행을 위임하는 것이다.

###### 회원 목록 페이지에 있는 링크의 URL에 .do 접미사 붙이기

회원 목록 페이지에 있는 링크들들 보면 아직 예전의 서블릿 URL을 가리키고있는데. 이 링크 URL에 `.do`를 붙이자

> MemberList.jsp 

```java
<jsp:include page="/Header.jsp"/>
	<h1>회원목록2</h1>
	<p><a href='add.do'>신규 회원</a></p>
	<c:forEach var="member" items="${members}"> 
		${member.no},
		<a href='update.do?no=${member.no}'>${member.name}</a>,
		${member.email},
		${member.createdDate}
		<a href='delete.do?no=${member.no}'>[삭제]</a><br>
	</c:forEach>
<jsp:include page="/Tail.jsp"/>
```

이렇게 링크를 변경하면 프론트 컨트롤러(DispatcherServlet)에게 요청할 것이다.


#### MemberAddServlet을 페이지 컨트롤러로 만들기

회원리스트는 변경했으니 회원등록 서블릿에 프론트 컨트롤러를 적용해 보자

> spms.servlets.MemberAddServlet 

```java
package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

// 프론트 컨트롤러 적용  
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(
      HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
-    RequestDispatcher rd = request.getRequestDispatcher(
-        "/member/MemberForm.jsp");
-    rd.forward(request, response);
+	  request.setAttribute("viewUrl", "/member/MemberForm.jsp);
  }

  @Override
  protected void doPost(
      HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      ServletContext sc = this.getServletContext();
      MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
      
-      memberDao.insert(new Member()
-        .setEmail(request.getParameter("email"))
-        .setPassword(request.getParameter("password"))
-        .setName(request.getParameter("name")));
-
-      response.sendRedirect("list");
      
+      Member member = (Member)request.getAttribute("member");
+      memberDao.insert(member);
      
+      request.setAttribute("viewUrl", "redirect:list.do");

    } catch (Exception e) {
-      e.printStackTrace();
-      request.setAttribute("error", e);
-      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
-     rd.forward(request, response);
+		  throw new ServletException(e);
    }
  }
}
```

###### 뷰로 포워딩하는 코드를 제거
GET 요청을 처리하는 doGet() 메소드에서 MemberForm.jsp로 포워딩하는 코드를 제거하고 MemberForm.jsp의 URL을 ServletRequest에 저장했다.

```java
//    RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
//    rd.forward(request, response);
		request.setAttribute("viewUrl", "/member/MemberForm.jsp");
```

###### 요청매개변수의 값을 꺼내는 코드를 제거

회원등록을 요구하는 POST 요청이 들어오면 doPost()가 호출 된다.
클라이언트가 보낸 회원정보를 꺼내기 위해 getParameter()를 호출하는 대신, 프론트 컨트롤러가 준비해 놓은 Member 객체를 ServletRequest 보관소에서 꺼내도록 doPost() 메소드를 변경했다.

```java
//      memberDao.insert(new Member()
//        .setEmail(request.getParameter("email"))
//        .setPassword(request.getParameter("password"))
//        .setName(request.getParameter("name")));
//
//      response.sendRedirect("list");
      Member member = (Member)request.getAttribute("member");
      memberDao.insert(member);
```


###### 리다이렉트를 위한 뷰 URL설정

회원 정보를 데이터베이스에 저장한 다음, 회원 목록 페이지로 리다이렉트 해야 하는데, 기존 코드를 제거하고 대신에 ServletRequest에 리다이렉트 URL을 저장했다.

```java
//    response.sendRedirect("list");
      request.setAttribute("viewUrl", "redirect:list.do");
```

뷰 URL이 "redirec:"문자열로 시작할 경우, 프론트 컨트롤러는 그 URL로 리다이렉트 한다.
따라서 리다이렉트 해야하는 경우 반드시 URL앞부분에 "redirect:"문자열을 붙여야 한다.


###### 오류 처리 코드 제거

MemberListServlet과 마찬가지로 오류가 발생했을 때 오류 처리 페이지로 실행을 위임하는 코드를 제거하고, 그 자리에 ServletException 을 던지는 코드를 넣었다

```java
-	e.printStackTrace();
-	request.setAttribute("error", e);
-	RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
-	rd.forward(request, response);
+	throw new ServletException(e);
```

###### 회원 등록 폼의 action URL에 .do붙이기

MemberForm.jsp 에도 `<form>` 태그의 action 값을 변경하자

```java
-	<form action='add' method='post'>
+	<form action='add.do' method='post'>
```


나머지 MemberUpdateServlet, MemberDeleteServlet, LogInServlet, LogOutServlet 등도 같은 방식으로 변경해보자.

---

### 페이지 컨트롤러의 진화

프론트 컨트롤러를 도입하면 페이지 컨트롤럴르 굳이 서블릿으로 만들어야 할 이유가 없다.
이번 절에서는 페이지 컨트롤러를 일반 클래스로 전환해 보자.
일반 클래스로 만들면 서블릿 기술에 종속되지 않기 때문에 재사용성이 더 높아진다.
이제 프론트 컨트롤러에서 페이지 컨트롤러로 작업을 위임할 때는 포워딩이나 인클루딩 대신 메소드를 호출해야 한다.


#### 프론트 컨트롤러와 페이지 컨트롤러의 호출 규칙 정의

프론트 컨트롤러가 페이지 컨트롤러를 일관성 있게 사용하려면, 호출 규칙을 정의해야 한다.

프론트 컨트롤러와 페이지 컨트롤러 사이의 호출 규칙을 문법으로 정의해 두면 개발자들은 그 규칙에 따라 해당 클래스를 작성하고 호출하면 되기때문에 프로그래밍의 일관성을 확보할 수 있어 유지보수에 도움이 된다.
또한 페이지 컨트롤러를 서블릿이 아닌 일반 클래스로 만들면 web.xml 파일에 등록할 필요가 없어 유지보수가 쉬워진다.


#### 호출 규칙 정의

프론트 컨트롤러와 페이지 컨트롤러 사이의 호출 규칙을 정의할때 사용하는 문법이 `인터페이스`이다.
즉 인터페이스는 사용자와 피사용자(또는 호출자와 피호출자) 사이의 일관성 있는 사용을 보장하기 위해 만든 자바 ㅜㅁㄴ법이다.

> 서블릿으로 작성된 모든 페이지 컨트롤러를 프론트 컨트롤러의 호출 규칙에 맞추워 일반 클래스로 전환해보자

아래 순서는 인터페이스가 적용된 페이지 컨트롤러의 대략적인 사용시나리오다

1. 웹브라우저는 회원목록페이지를 요청한다.
2. 프론트 컨트롤러 `DispatcherServlet`은 회원 목록 요청 처리를 담당하는 페이지 컨트롤러를 호출한다.
이때 데이터를 주고받을 바구니 역할을 할 Map 객체를 넘긴다.
3. 페이지 컨트롤러 `MemberListController`는 `Dao`에게 회원 목록 데이터를 요청한다.
4. `MemberDao` 는 데이터베이스로부터 회원 목록 데이터를 가져와서, Member 객체에 담아 반환한다.
5. 페이지 컨트롤러는 Dao가 반환한 회원 목록 데이터를 Map 객체에 저장한다. 그리고 프론트 컨트롤러에게 뷰 URL을 반환한다.
6. 프론트 컨트롤러는 Map객체에 저장된 페이지 컨트롤러의 작업 결과물을 JSP가 사용할 수 있도록 ServletRequest로 옮긴다.
7. 나머지는 바로 앞에서 배운 과정과 동일하다.

> 여기서 중요한 점은 프론트 컨트롤러가 페이지 컨트롤러에게 작업을 위임할 때 더 이상 포워딩이나 인클루딩 방식을 사용하지 않고,
> 페이지 컨트롤러에게 일을 시키기 위해 `excute()`메소드를 호출한다.

또한 프론트와 페이지 사이에 데이터를 주고받기 위해 Map객체를 사용하고 있는데 이는,
페이지 컨트롤러가 Servlet API를 직접 사용하지 않도록 하기 위함으로
서블릿 기술에 종속되는 것을 줄일수록 페이지 컨트롤러의 재사용성이 높아지기 때문이다.


#### 페이지 컨트롤러를 위한 인터페이스 정의

> 새로운 spms.controls 패키지를 만들고 Controller 인터페이스를 생성하자

```java
package spms.controls;

import java.util.Map;

public interface Controller {
	String excute(Map<String, Object>model) throws Exception;
}
```

`excute()`는 프론트 컨트롤러가 페이지 컨트롤러에게 일을 시키기 위해 호출하는 메소드다.
여기서는 프론트컨트롤러가 excute를 호출하려면 Map객체를 매개변수로 넘겨줘야 한다.


#### 페이지 컨트롤러 MemberListServlet을 일반 클래스로 전환

회원목록을 처리했던 페이지 컨트롤러 `MemberListServlet`을 일반 클래스로 전환해보자

> spms.controls 패키지에 MemberListController 클래스 생성

```java
package spms.controls;

import java.util.Map;
import spms.dao.MemberDao;

public class MemberListController implements Controller {

	@Override
	public String excute(Map<String, Object> model)throws Exception{
		MemberDao memberDao = (MemberDao)model.get("memberDao");
		model.put("members", memberDao.selectList());
		return "/member/MemberList.jsp";
	}
}
```

###### Controller 인터페이스의 구현

페이지 컨트롤러가 되려면 Controller 규칙에 따라 클래스를 작성해야 한다

```java
public class MemberListController implements Controller {
	public String excute(Map<String, Object> model)throws Exception{
```

또한 페이지 컨트롤러는 더 이상 예외 처리를 할 필요가 없고,
예외가 발생했을 때 호출자인 프론트 컨트롤러에게 던지면 된다.

###### 페이지 컨트롤러에게 사용할 객체를 Map에서 꺼내기

MemberDao 객체는 프론트 컨트롤러가 넘겨준 마법의 상자, "Map 객체(model)"에 들어있다.
물론 지금 프론트단에 작업되었다는게 아니라 그렇게 할거라는 말이다.

```java
MemberDao memberDao = (MemberDao)model.get("memberDao");
```

###### 페이지 컨트롤러가 작업한 결과물을 Map에 담기

Map객체 'model' 매개변수는 페이지 컨트롤러가 작업한 결과를 담을 때도 사용한다.
예제에서는 회원 목록 데이터를 model에 저장하고 있다.

```java
model.put("members", memberDao.selectList());
```

Map 객체에 저장된 값은 프론트 컨트롤러가 꺼내서 ServletRequest 보관소로 옮길 것이다.
ServletRequest 보관소에 저장된 값은 다시 JSP가 꺼내서 사용할 것이다.

###### 뷰URL 반환

페이지 컨트롤러의 반환 값은 화면을 출력할 JSP의 URL 이며, 프론트 컨트롤러는 이 URL로 실행을 위임한다.

```java
return "/member/MemberList.jsp";
```


#### 프론트 컨트롤러 변경

이제 Controller 규칙에 따라 페이지 컨트롤러를 호출하도록 프론트 컨트롤러를 변경해보자.

> spms.servlets.DispatcherServlet 클래스의 service()메소드

```java
		ServletContext sc = this.getServletContext();

		// 페이지 컨트롤러에게 전달할 Map 객체를 준비한다. 
		HashMap<String,Object> model = new HashMap<String,Object>();
		model.put("memberDao", sc.getAttribute("memberDao"));

		String pageControllerPath = null;
		Controller pageController = null;

		if("/member/list.do".equals(servletPath)){
			..
		}
		else if("/member/add.do".equals(servletPath)){
			..
		}
		else if("/member/update.do".equals(servletPath)){
			..
		}
		else if("/member/delete.do".equals(servletPath)){
			..
		}
		else if("auth/login.do".equals(servletPath)){
			..
		}
		else if("auth/logout.do".equals(servletPath)){
			..
		}

		// 페이지 컨트롤러를 실행한다.
		String viewUrl = pageController.execute(model);

		// Map 객체에 저장된 값을 ServletRequest에 복사한다.
		for (String key : model.keySet()) {
			request.setAttribute(key, model.get(key));
		}

			/*
			RequestDispatcher rd=request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);

			String viewUrl=(String) request.getAttribute("viewUrl");
			*/

		if(viewUrl.startsWith("redirect:")){
			response.sendRedirect(viewUrl.substring(9));
			return;
		}
		else{
			RequestDispatcher rd=request.getRequestDispatcher(viewUrl);
			rd.include(request, response);
		}
```


###### Map 객체 준비

프론트 컨트롤러와 페이지 컨트롤러 사이에 데이터나 객체를 주고 받을 때 사용할 Map객체를 준비한다.
즉 MemberListController가 사용할 객체를 준비하여 Map객체에 담아서 전달해 준다

```java
ServletContext sc = this.getServletContext();

// 페이지 컨트롤러에게 전달할 Map 객체를 준비한다. 
HashMap<String,Object> model = new HashMap<String,Object>();
model.put("memberDao", sc.getAttribute("memberDao"));
```

MemberListController는 회원 목록을 가져오기 위해 MemberDao 객체가 필욯다.
그래서 ServletContext 보관소에 저장된 MemberDao 객체를 꺼내서 Map객체에 담았다.

###### 회원 목록을 처리할 페이지 컨트롤러 준비

> 페이지 컨트롤러는 Controller의 구현체이기 때문에, 인터페이스 타입의 참조변수를 선언했다.
그래야만 앞으로 만들 MemberAddController, MemberUpdateController, MemberDeleteController등의 객체 주소도 저장할수 있기 때문이다.

```java
Contoller pageController = null;
```

> 회원목록요청을 처리할 페이지 컨트롤러를 준비한다.

```java
if("/member/list.do".equals(servletPath)){
	pageController = new MemberListController();
}
```
일단은 회원 목록에 대해서만 위처럼 적용한다. 

###### 페이지 컨트롤러의 실행

이전에는 페이지 컨트롤러가 서블릿이었기 때문에 인클루딩(혹은 포워드)처리로 실행을 위임했지만,
이제는 MemberListController가 일반 클래스이기 때문에 메소드를 호출해야 한다.
그것도 Controller 인터페이스에 정해진 대로 `excute()`메소드를 호출해야 한다.

```java
String viewUrl = pageController.excute(model);
```

`excute()`를 호출할 때 페이지 컨트롤러를 위해 준비한 Map 객체를 매개변수로 넘긴다.
excute()의 반환값은 화면 출력을 수행하는 JSP의 URL 이다.

###### Map 객체에 저장된 값을 ServletRequest에 복사

Map객체는 페이지 컨트롤러에게 데이터나 객체를 보낼 때 사용되기도 하지만 페이지 컨트롤러의 실행 결과물을 받을 때도 사용한다.
따라서 페이지 컨트롤러의 실행이 끝난 다음, Map객체에 보관되어 있는 데이터나 객체를 JSP가 사용할 수 있도록 ServletRequest에 복사한다.

```java
for(String ket : model.keySet()){
	request.setAttribute(ket,model.get(key));
}
```

___

#### 회원 등록 페이지 컨트롤러에 Controller 규칙 적용하기

MemberListController처럼 MemberAddServlet에 대해서도 적용해 보자

> spms.controls.MemberAddController 

```java
package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller {
	@Override
	public String excute(Map<String, Object> model) throws Exception{
		if(model.get("member")==null){	// 입력폼을 요청할 때
			return "/member/MemberForm.jsp";
		}
		else{		// 회원등록을 요청할 때
			MemberDao memberDao = (MemberDao)model.get("memberDao");
			
			Member member = (Member)model.get("member");
			memberDao.insert(member);
			
			return "redirect:list.do";
		}
	}
}

```

MemberAddServlet은 서블릿이기 때문에 GET요청과 POST 요청을 구분하여 처리할 수 있었다.
그러나 MemberAddController는 일반 클래스이기 때문에 클라이언트 요청에 대해 GET과 POST를 구분할 수 없다.
그래서 Map객체에 VO객체 "Member"가 들어 있으면 POST요청으로 간주하고, 그렇지 않으면 GET요청으로 간주한 것이다.

Map 객체에 "Member" 인스턴스가 없으면, 입력폼을 위한 JSP URL을 반환한다.

```java
if(model.get("member")==null){
	return "/member/MemberForm.jsp";
}
```

Map 객체에 "Member" 인스턴스가 있으면, MemberDao를 통해 DB에 저장한다.

```java
else{		// 회원등록을 요청할 때
		MemberDao memberDao = (MemberDao)model.get("memberDao");

		Member member = (Member)model.get("member");
		memberDao.insert(member);

		return "redirect:list.do";
	}
```

#### 회원등록 요청을 처리하기 위해 DispatcherSevlet 변경

MemberListController 때와 마찬가지로 MemberAddController를 사용하도록 DispatcherServlet을 변경해야 한다.

> spms.servlets.DispatcherServlet 클래스의 service()메소드에서 회원 등록을 처리하는 부분

```java
else if("/member/add.do".equals(servletPath)){
	pageController=new MemberAddController();
	if (request.getParameter("email") != null) {
		model.put("member", new Member()
		.setEmail(request.getParameter("email"))
		.setPassword(request.getParameter("password"))
	   .setName(request.getParameter("name")));
	}
}
```

MemberAddController의 인스턴스를 생성했다.
또한 이전에는 사용자가 입력한 데이터에 대해 Member 객체를 만든후, ServletRequest 보관소에 담았지만, 이제는 Map 객체에 담는다.

> 새로운 소스가 잘 작동하는지 웹에서 테스트 해보자

이처럼 기존의 소스를 변경하거나 새로운 기능을 추가하거나 할때는 한꺼번에 모든것을 작성하지 말고,
지금처럼 회원목록, 등록 을 순차적으로 작성했듯이 해나가는것이 좋다.
디버깅하기도 쉽고...

> 나머지도 마저 바꿔보자 (로그인,아웃 경우에는 session 처리에 유의 )


---

### DI를 이용한 빈 의존성 관리

MemberListController 작업을 수행하려면 데이터베이스로부터 회원 정보를 가져다 줄 MemberDao가 필요하다.
이렇게 특정 작업을 수행할 때 사용하는 객체를 '의존객체'라고 하고, 이런 관게를 의존관게(dependency) 라고 한다.


#### 의존객체의 관리

의존 객체 관리에는 필요할 때마다 의존 객체를 직접 생성하는 고전적인 방법에서부터 외부에서 의존 객체를 주입해 주는
최근읜 방법까지 다양한 방안이 존재한다.


> 의존 객체가 필요하면 즉시 생성 ( 고저적인방법 )

의존 객체를 사용하는 쪽에서 직접 그 객체를 생성하고 관리하는 것.

이전에 작성했던 MemberListServlet 의 일부 코드를 보자

```java
public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		try{
      	ServletContext sc = this.getServletContext();
         Connection conn = (connection) sc.getAttribute("conn");
         
         MemberDao memberDao = new MemberDao();
         memberDao.setConnection(conn);
         
         request.setAttribute("members", memberDao.selectList());
         ..
      }
}
```

회원 목록을 가져오기 위해 직접 MemberDao 객체를 생성하고 있다.
이 방식의 문제점은 doGet()이 호출될 때마다 MemberDao가 객체를 생성하기 때문에 비효율적이다.


###### 의존객체를 미리 생성해 두엇다가 필요할 때 사용

앞의 방법을 조금 개선한한것이  사용할 객체를 미리 생성해 둑 필요할 때마다 꺼내 쓰는 방식이다.

윂 에플리켜이션이 시작될때 MemberDao 객체를 미리 생성하여 ServletContext 에 보관해 둔다.
그리고 아래처럼 필요할 때 마다 꺼내 쓴다

```java
public void doGET{
	.. 
   try{
   	ServletContext sc = thisl.getServletContext();
      Member memberDao = (MemberDao) sc.getAttribute("memberDao"
      memberDao.setConnect(conn);
      
      request.setAttribute("members", memberDao.selectList());
   }
}
}
```

이전에 작성한 `MemberListController`또한 이러한 방식을 사용하고 있다.
다만,  ServletContext가 아닌 Map객체에서 꺼낸다는 것이 다를뿐이다.



###### 의존객체와의 결합도 증가에 따른 문제

앞에서 처럼 의존 객체를 생성하거나 보관소에서 꺼내는 방식으로 관리하다 보니 문제가 발생한다..

1) 코드의 잦은 변경

의존 객체를 사용하는 쪽과 의존 객체(또는 보관소)사이의 결합도가 높아져서 의존 객체나 보관소에 변경이 발생하면 바로 영향을 받는다는 것이다.

예를 들면 의존 객체의 기본생성자가 `public` 에서 `private`으로 바뀐다면 의존객체를 생성하는 모든 코드를 찾아 변경해 주어야 한다.
보관소도 마찬가지다. 보관소가 변경되면 그 보관소를 사용하는 모든 코드를 변경해야 한다.

2) 대체가 어렵다

의존 객체를 다른 객체로 대체하기가 어렵다.
현재 MemberListController가 사용하는 MemberDao는 MySQL DB를 사용하도록 작성되어있다.
만약 오라클 DB를 사용해야 한다면, 일부 SQL문을 그에 맞게끔 변경해야 한다.

다른 방법으로는 각 DB별로 MemberDao를 준비하는 것이다.
그래도 여전히 문제는 남아있다. 바로 DB가 바뀔 때 마다 DAO를 사용하는 코드도 변경해야 한다는 것이다.

> 그래서 이러한 문제들을 해결하기 위해 등장한 방법이 바로 다음에 소개할 내용이다.

#### 의존 객체를 외부에서 주입

초창기 객체 지향 프로그래밍에서는 의존객체를 직접 생성해왔다.
그러다가 위에서 언급한 문제를 해결하기 위해 의존객체를 외부에서 주입받는 방식`(Dependency Injection)`으로 바뀌게 된것이다.

> 이렇게 의존객체를 전문으로 관리하는 빈 컨테이너(Java Beans Container)가 등장한 것이다.

빈 컨테이너는 객체가 실행되기 전에 그 객체가 필요로 하는 의존 객체를 주입해 주는 역할을 수행한다.
이런 방식으로 의존객체를 관리하는 것을 `의존성 주입(DI:Dependenct Injection)`이라 한다.

좀 더 일반적인 말로 `역 제어(IoC:Inversion of Control)` 라고 부르는데
즉 역제어(IoC)방식의 한 예가 의존성 주입(DI)인 것이다.


#### MemberDao 와 DataSource

지금까지 사용했던 MemberDao에 DI 기법이 이미 적용되어 있다.
MemberDao가 작업을 수행하려면 데이터베이스와 연결을 수행하는 DataSource가 필요하다.
그런데 이 DataSource 객체를 MemberDao에서 직접 생성하는 것이 아니라 외부에서 주입 받는다.

다음 코드가 MemberDao에 DataSource를 주입하는 코드다

```java
public void contextInitialized(ServletContextEvent event) {
	try {
   	ServletContext sc = event.getServletContext();

      InitialContext initialContext = new InitialContext();
      DataSource ds = (DataSource)initialContext.lookup(
          "java:comp/env/jdbc/studydb");

      MemberDao memberDao = new MemberDao();
      memberDao.setDataSource(ds);
```

ContextLoaderListener의 contextInitialized()는 웹 에플리케이션이 시작될 때 호출되는 메소드다.
이 메소드를 살펴보면 setDateSource()를 호출하는 부분이 있는데, 바로 이 부분이
MemberDao가 사용할 의존 객체인 DataSource를 주입하는 부분이다.


#### MemberListController에 MemberDao 주입
MemberListController가 작업을 수행하려면 MemberDao가 필요하다.
MemberListController에도 DI를 적용해보자

> spms.controls.MemberListController

```java
public class MemberListController implements Controller {
	MemberDao memberDao;
	
	public MemberListController setMamberDao(MemberDao memberDao){
		this.memberDao=memberDao;
		return this;
	}
	
	@Override
	public String excute(Map<String, Object> model)throws Exception{
		MemberDao memberDao = (MemberDao)model.get("memberDao");
		model.put("members", memberDao.selectList());
		return "/member/MemberList.jsp";
	}
}
```

###### 의존객체 주입을 위한 인스턴스 변수와 세터 메소드

MemberDao에서 DataSource 객체를 주입 받고자 인스턴스 변수와 세터 메소드를 선언했듯이,
MemberListController에도 MemberDao를 주입 받기 위한 인스턴스 변수와 세터 메소드를 추가했다.

> 여기서는 세터 메소드를 좀 더 쉽게 사용하기 위해 Member 클래스에서처럼 리턴 타입이 그 자신의 인스턴스 값을 리턴하도록 했따.


###### 의존 객체를 스스로 준비하지 않는다.

외부에서 MemberDao 객체를 주입해 줄 것이기 때문에 이제 더 이상 Map 객체에서 MemberDao를 꺼낼 필요가 없다.
따라서 아래의 코드를 제거했다.

```java
MemberDao memberDao = (MemberDao)model.get("memberDao");
```

> 나머지 Controller 들도 (delete,login 등등...) MemberDao를 주입하기 위한 인스턴스 변수와 세터 메소드를 추가해보자


#### 페이지 컨트롤러 객체들을 준비

페이지 컨트롤러도 MemberDao 처럼 ContextLoaderListener 에서 준비하자

> spms.listeners.ContextLoaderListener 

```java
  public void contextInitialized(ServletContextEvent event) {
    try {
      ServletContext sc = event.getServletContext();
      
      InitialContext initialContext = new InitialContext();
      DataSource ds = (DataSource)initialContext.lookup(
          "java:comp/env/jdbc/studydb");
      
      MemberDao memberDao = new MemberDao();
      memberDao.setDataSource(ds);
      
      //sc.setAttribute("memberDao", memberDao);
      
      sc.setAttribute("/auth/login.do", 
              new MemberLogInController().setMemberDao(memberDao));
          sc.setAttribute("/auth/logout.do", new MemberLogOutController());
          sc.setAttribute("/member/list.do", 
              new MemberListController().setMemberDao(memberDao));
          sc.setAttribute("/member/add.do", 
              new MemberAddController().setMemberDao(memberDao));
          sc.setAttribute("/member/update.do", 
              new MemberUpdateController().setMemberDao(memberDao));
          sc.setAttribute("/member/delete.do", 
              new MemberDeleteController().setMemberDao(memberDao));

    } catch(Throwable e) {
      e.printStackTrace();
    }
  }
```

###### 페이지 컨트롤러 객체를 준비

페이지 컨트롤러 객체를 생성하고 나서 MemberDao가 필요한 객체에 대해서는 세터 메소드를 호출하여 주입해준다.

```java
new MemberLogInController().setMemberDao(memberDao)
```

이렇게 생성된 페이지 컨트롤러를 ServletContext에 저장한다.
단, 저장할 때 서블릿 요청 URL을 키(key)로 하여 저장했음을 주의하자.
프론트 컨트롤러에서 ServletContext에 보관된 페이지 컨트롤러를 꺼낼 때 이 서블릿 요청 URL을 사용할 것이다.

```java
sc.setAttribute("/auth/login.do", new MemberLogInController().setMemberDao(memberDao));
```

#### 프론트 컨트롤러의 변경

페이지 컨트롤러 객체를 ContextLoaderListener에서 준비했기 때문에 프론트컨트롤러를 변경해야 한다.

> spms.servlets.DispatcherServlet

```java
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();

		try{
			ServletContext sc = this.getServletContext();

			// 페이지 컨트롤러에게 전달할 Map 객체를 준비한다. 
		   HashMap<String,Object> model = new HashMap<String,Object>();
		   model.put("session", request.getSession());


		   Controller pageController = (Controller) sc.getAttribute(servletPath);

			if("/member/add.do".equals(servletPath)){
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
			            .setEmail(request.getParameter("email"))
			            .setPassword(request.getParameter("password"))
			            .setName(request.getParameter("name")));
		        }
			}
			else if("/member/update.do".equals(servletPath)){
				if(request.getParameter("email") != null){
					model.put("member", new Member()
						.setNo(Integer.parseInt(request.getParameter("no")))
						.setEmail(request.getParameter("email"))
						.setName(request.getParameter("name")));
				}
				else{
					model.put("no", new Integer(request.getParameter("no")));
				}
			}
			else if("/member/delete.do".equals(servletPath)){
				model.put("no", new Integer(request.getParameter("no")));
			}
			else if("auth/login.do".equals(servletPath)){
				if (request.getParameter("email") != null) {
					model.put("loginInfo", new Member()
							.setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password")));
			    }
			}
```


MemberDao 객체는 더 이상 Map객체에 담을 필요가 업어서 제거했다.

```java
model.put("memberDao", sc.getAttribute("memberDao"));
```

###### ServletContext 페이지 컨트롤러 꺼내기

페이지 컨트롤러는 ServletContext 보관소에 저장되어 있다.
이 보관소에서 페이지 컨트롤러를 꺼낼 때는 서블릿 URL을 사용한다

```java
Controller pageController=(Controller)sc.getAttribute(servletPath);
```


###### 조건문 변경
if~else 조건문에서 페이지 컨트롤러가 사용할 데이터를 준비하는 부분을 제외하고는 모두 제거했다.


#### 인터페이스를 활용하여 공급처를 다변화 하자

실무에서는 오라클을 사용할 수도 있고, MySQL이나 MS-SQL을 사용할 수도 있다.
그렇게되면 각데이터베이스에 맞춰 DAO클래스를 준비해야 한다.
문제는 데이터베이스를 바꿀 때마다 DAO 클래스를 사용하는 코드도 변경해야 하기 때문에 여간 번거로운 것이 아니다.

바로 이런 경우에 인터페이스를 활용하면 손쉽게 해결할 수 있다.

의존객체를 사용할 때 구체적으로 클래스 이름을 명시하는 대신에 인터페이스를 사용하면, 그 자리에 다양한 구현체(인터페이스를 구현한 클래스)를 놓을 수 있다. 즉 인터페이스라는 문법으로 DAO가 갖춰야 할 규격을 정의하고, 그 규격을 준수하는 클래스라면 어떤 클래스를 상속받았는지 따지지 않고 허용하는 것이다.


#### MemberDao 인터페이스 정의

MemberDao에 인터페이스를 적용해 보자
지금까지 사용했던 기존의 MemberDao 클래스를 MySqlMemberDao 로 변경한 후 MemberDao 인터페이스를 생성하자

> spms.dao.MemberDao 인터페이스

```java
package spms.dao;

import java.util.List;
import spms.vo.Member;

public interface MemberDao {
	List<Member> selectList() throws Exception;
	int insert(Member member) throws Exception;
	int delete(int no) throws Exception;
	Member selectOne(int no) throws Exception;
	int update(Member member) throws Exception;
	Member exist(String email, String password) throws Exception;
}
```


###### MySqlMemberDao 클래스를 MemberDao 규격에 맞추기

MySqlMemberDao 클래스를 MemberDao 인터페이스를 구현하도록 변경해야 한다.

> spms.dao.MySqlMemberDao 클래스

```java
public class MySqlMemberDao implements MemberDao{}
```


###### ContextLoaderListener 클래스 변경

MemberDao는 이제 클래스가 아니므로, MemberDao 객체의 준비를 담당했던 ContextLoadListener 클래스를 변경하자

> spms.listeners.ContextLoaderListener 클래스

```java
-  MemberDao memberDao = new MemberDao();
+  MySqlMemberDao memberDao = new MySqlMemberDao();
```

이제 MemberDao는 인터페이스이기 때문에 인스턴스를 생성할 수 없으므로 그 코드를 제거했고
대신 MemberDao 인터페이스를 구현한 MySqlMemberDao 객체를 생성했다.
즉, 페이지 컨트롤러에 주입되는것은 바로 MySqlMemberDao 객체가 되는 것이다.

#### 정리

`의존성 주입` 기법으로 의존 객체를 관리하는 방법에 대해 알아보았지만 아직 개선할 점들은 뭐가 있을까?

DispatcherServlet의 경우 페이지 컨트롤러가 추가될 때마다 조건문을 변경해야하는 문제도 있을것이고,
ContextLoaderListener도 Dao나 페이지 컨트롤러가 추가될 때마다 변경해야 한다.


### 리플랙션 API를 이용하여 프론트 컨트롤러 개선하기

지금까지 작업한 프론트컨트롤러는 안타깝지만 페이지 컨트롤러를 추가할 때마다 코드를 변경해야 한다.
특히 매개변수 값을 받아서 VO객체를 생성하는 부분에 많은 손이 간다.
작성했던 코드를 통해 살펴보자

> spms.servlets.DispatcherServlet 의 일부분

```java
if ("/member/add.do".equals(servletPath)) {
   if (request.getParameter("email") != null) {
        		model.put("member", new Member()
       .setEmail(request.getParameter("email"))
       .setPassword(request.getParameter("password"))
       .setName(request.getParameter("name")));
	}
}
```

위 코드는 페이지 컨트롤러 MemberAddController 를 위해서 신규 회원 등록에 필요한 데이터를 준비하는 코드다.
사용자가 입력한 매개변수 값으로부터 Member 객체를 생성한 후 Map객체에 저장한다.

거기에 MemberUpdateController 를 위해서는 또 다른 코드가 필요하고 나머지들도 다 마찬가지로 
매개변수값에 대해 이런식으로 VO객체를 준비하게 되면, 데이터를 사용하는 페이지 컨트롤러를 추가할 때 마다 계속 프론트컨트롤러를 변경해야 하는 문제가 발생하고, 이는 자연적으로 유지보수를 매우매우매우매우 불편하고 어렵게 만든다

> 바로 이런 문제를 개선하기 위해 리플렉션 API를 활용하여 인스턴스를 자동생성하고, 메소드를 자동으로 호출하는 방법을 배워보자


#### 신규회원정보 추가 자동화

신규회원 정보를 추가할 때의 시나리오를 살펴보자
지금까지와 다른 점은 데이터를 준비하는 부분을 자동화하는 것이다.

1. 웹브라우저는 회원등록을 요청하고, 사용자가 입력한 매개변수 값을 서블릿에 전달한다.

2. 프론트컨트롤러 `DispatcherServlet`은 회원 등록을 처리하는 페이지 컨트롤러에게 어떤 데이터가 필요한지 물어고,
페이지 컨트롤러 `MemberAddController`는 작업하는 데 필요한 데이터의 이름과 타입 정보를 담은 배열을 리턴한다.

3. 프론트 컨트롤러는 `ServletRequestDataBinder`를 이용하여, 요청 매개변수로부터 페이지 컨트롤러가 원하는 형식의 값 객체(예:Member, Integer, Date등)을 만든다.

4. 프론트 컨트롤러는 `ServletRequestDataBinder`가 만들어 준 값 객체를 Map에 저장한다.

5. 프론트 컨트롤러는 페이지 컨트롤러 `MemberAddController`를 실행하고, 페이지 컨트롤러의 `execute()`를 호출할 때, 값이 저장된 Map 객체를 매개변수로 너긴다.



#### DataBinding 인터페이스 정의

위의 실행 시나리오에서 프론트 컨트롤러가 페이지 컨트롤러를 실행하기 전에 원하는 데이터가 무엇인지 묻는다고 했따.
이것에 대해 호출규칙을 정의해 보자
프론트 컨트롤러 입장에서는 이 규칙을 준수하는 페이지 컨트롤러를 호출할 때만 VO객체를 준비하면 된다.

> spms.bind 패키지를 새로 생성하고 DataBinding 인터페이스를 생성하자

```java
package spms.bind;

public interface DataBinding {
	Object[] getDataBinders();
}
```

페이지 컨트롤러 중에서 클라이언트가 보낸 데이터가 필요한 경우 이 DataBinding 인터페이스를 구현한다.
getDataBinders()의 반환값은 데이터의 이름과 타입 정보를 담은 Object의 배열이다.

여기서 배열을 작성하는 형식은 다음과 같다.
```java
new Object[]{"데이터이름", 데이터타입, "데이터이름", 데이터타입, ...}
```

데이터 이름과 타입(Class객체)이 한쌍으로 순서대로 오도록 작성한다. 물론 짝수가 되겠찌


#### 페이지 컨트롤러의 DataBinding 구현

클라이언트가 보낸 데이터를 사용하는 페이지 컨트롤러는 MemberAddController, MemberUpdateController, MemberDeleteController, LogInController 다.
이중 실행 시나리오에서 예로 살펴본 ADD 부터 인터페이스를 적용해보자

> spms.controls.MemberAddController 클래스

```java
package spms.controls;

import java.util.Map;

import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

// 의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가
//- 또한 의존 객체를 꺼내는 기존 코드 변경
public class MemberAddController implements Controller, DataBinding {
	MemberDao memberDao;

  public MemberAddController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }

  public Object[] getDataBinders(){
	  return new Object[]{
			  "member", spms.vo.Member.class
	  };
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {
	  Member member=(Member)model.get("member");
    if (model.get("member") == null) { // 입력폼을 요청할 때
      return "/member/MemberForm.jsp";
    } else { // 회원 등록을 요청할 때
      memberDao.insert(member);      
      return "redirect:list.do";
    }
  }
}

```


###### DataBinding 인터페이스 선언
MemberAddController 는 클라이언트가 보낸 데이터를 프론트 컨트롤러로부터 받아야 하기 때문에, 위에서 정의한 규칙에 따라
DataBinding 인터페이스를 구현한다.

```java
public class MemberAddController implements Controller, DataBinding{}
```

###### getDataBinders() 메소드 구현
DataBinding 인터페이스를 구현한다고 선언했으니 getDataBinders()메소드를 구현해야 한다.
MemberAddController 가 원하는 데이터는 사용자가 회원 등록 폼에 입력한 이름, 이메일, 암호값이다.
이 함수의 반환값, Obejct배열을 살펴보면~~

그 의미는 클라이언트가 보낸 매개변수 값을 Member인스턴스에 담아서 "member" 라는 이름으로 Map 객체에 저장해 달라는 뜻이다.

```java
public Object[] getDataBinderS(){
	return new Object[]{
   	"member", spms.vo.Member.class
   };
}
```

프론트 컨트롤러는 Object 배열에 지정된 대로 Member 인스턴스를 준비하여 Map 객체에 저장하고, execute()를 호출할 때 매개변수로 이 Map 객체를 넘길 것이다.


###### execute() 메소드
기존의 excute()에 비교해 조건문이 약간 달려졌다.
이전 코드에서는 Map 객체에 Member 가 들어있는지 없는지에 따라 작업을 분기했었는데

> 이전코드
```java
if(model.get("member") == null){
	...
}else{
	...
}
```

이제부터는 getDataBinders()에서 지정한 대로 프론트 컨트롤러가 VO객체를 무조건 생성할 것이기 때문에 Member가 있는지 여부로 판단해서는 안되고,
대신 Member에 이메일이 들어 있는지로 여부를 검사한 것이다.

> 바뀐 코드
```java
Member member (Member)model.get("member");
if(member.getEmail()==null){
	...
}else{
	...
}
```

> 클라이언트에서 데이터를 보내지 않는 MemberListController와 LogOutController를 빼고 나머지 페이지 컨트롤러들도
> DataBinding 인터페이스를 구현하자

#### 프론트컨트롤러의 변경

페이지 컨트롤러를 변경했으니, 프론트컨트롤러도 그에 맞게 변경해야 한다.

> spms.servlets.DispathcerServlet 

```java
package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.controls.Controller;

// DataBinding 처리
@SuppressWarnings("serial")
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
  @Override
  protected void service(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    String servletPath = request.getServletPath();
    try {
      ServletContext sc = this.getServletContext();
      
      // 페이지 컨트롤러에게 전달할 Map 객체를 준비한다. 
      HashMap<String,Object> model = new HashMap<String,Object>();
      model.put("session", request.getSession());
      
      Controller pageController = (Controller) sc.getAttribute(servletPath);
      
      if (pageController instanceof DataBinding) {
        prepareRequestData(request, model, (DataBinding)pageController);
      }

      // 페이지 컨트롤러를 실행한다.
      String viewUrl = pageController.execute(model);
      
      // Map 객체에 저장된 값을 ServletRequest에 복사한다.
      for (String key : model.keySet()) {
        request.setAttribute(key, model.get(key));
      }
      
      if (viewUrl.startsWith("redirect:")) {
        response.sendRedirect(viewUrl.substring(9));
        return;
      } else {
        RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
        rd.include(request, response);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);
    }
  }

  private void prepareRequestData(HttpServletRequest request,
      HashMap<String, Object> model, DataBinding dataBinding)
      throws Exception {
    Object[] dataBinders = dataBinding.getDataBinders();
    String dataName = null;
    Class<?> dataType = null;
    Object dataObj = null;
    for (int i = 0; i < dataBinders.length; i+=2) {
      dataName = (String)dataBinders[i];
      dataType = (Class<?>) dataBinders[i+1];
      dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
      model.put(dataName, dataObj);
    }
  }
}
```

###### service()메소드

드디어 service()메소드에서 조건문이 사라졌다.
이제부터는 매개변수 값을 사용하는 페이지 컨트롤러를 추가하더라도 조건문을 삽입할 필요가 없다.
대신 데이터 준비를 자동으로 수행하는 `prepareRequestData()`를 호출한다.

```java
if(pageController instanceof DataBinding){
	prepareRequestData(request, model, (DataBinding)pageController);
}
```

매개변수 값이 필요한 페이지 컨트롤러에 대해 DataBinding 인터페이스를 구현하기로 규칙을 정했다.
따라서 DataBiding을 구현했는지 여부를 검사하여, 해당 인터페이스를 구현한 경우에만 prepareRequestData()를 호출하여
페이지 컨트롤러를 위한 데이터를 준비했다

###### prepareRequestData()메소드
prepareRequestData()에서 어떤 방법으로 데이터를 준비하는지 살펴보자

먼저 페이지 컨트롤러에게 필요한 데이터가 무엇인지 묻는다

```java
Object[] dataBinders = dataBinding.getDataBinders();
```

getDataBinders()메소드가 반환하는 것은 ["데이터이름",데이터타입....]으로 나열된 Object 배열일 것이다.
배열을 반복하기 전에 배열에서 꺼낸 값을 보관할 임시 변수를 준비하고, 데이터 이름(String), 데이터 타입(Class), 데이터 객체(Object)를 위한 참조 변수다.

```java
    String dataName = null;
    Class<?> dataType = null;
    Object dataObj = null;
```

데이터 이름과 데이터 타입을 꺼내기 쉽게 2씩 증가하면서 반복문을 돌린다.

```java
for (int i = 0; i < dataBinders.length; i+=2) {
   dataName = (String)dataBinders[i];
   dataType = (Class<?>) dataBinders[i+1];
}
```

for문 안을 들여다보면, ServletRequestDataBinder 클래스의 bind() 메소드를 호출하고 있따.
이 메소드는 dataName과 일치하는 요청 매개변수를 찾고 dataType 을 통해 해당 클래스의 인스턴스를 생성한다.
찾은 매개변수 값을 인스턴스에 저장하며 그 인스턴스를 반환한다.

```java
	dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
   model.put(dataName, dataObj);
```

bind() 메소드가 반환한 데이터 객체는 Map 객체에 담는다.
이 작업을 통해 페이지 컨트롤러가 사용할 데이터를 준비하는 것이다.


#### ServletRequestDataBinder 클래스 생성

ServletRequestDataBinder 클래스는 클라이언트가 보낸 매개변수 값을 자바 객체에 담아 주는 역할을 수행한다.

> spms.bind 패키지에 ServletRequestDataBinder 클래스를 생성하다

```java
package spms.bind;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

public class ServletRequestDataBinder {
  public static Object bind(
      ServletRequest request, Class<?> dataType, String dataName) 
      throws Exception {
    if (isPrimitiveType(dataType)) {
      return createValueObject(dataType, request.getParameter(dataName));
    }
    
    Set<String> paramNames = request.getParameterMap().keySet();
    Object dataObject = dataType.newInstance();
    Method m = null;
    
    for (String paramName : paramNames) {
      m = findSetter(dataType, paramName);
      if (m != null) {
        m.invoke(dataObject, createValueObject(m.getParameterTypes()[0], 
            request.getParameter(paramName)));
      }
    }
    return dataObject;
  }
  
  private static boolean isPrimitiveType(Class<?> type) {
    if (type.getName().equals("int") || type == Integer.class ||
        type.getName().equals("long") || type == Long.class ||
        type.getName().equals("float") || type == Float.class ||
        type.getName().equals("double") || type == Double.class ||
        type.getName().equals("boolean") || type == Boolean.class ||
        type == Date.class || type == String.class) {
      return true;
    }
    return false;
  }
  
  private static Object createValueObject(Class<?> type, String value) {
    if (type.getName().equals("int") || type == Integer.class) {
      return new Integer(value);
    } else if (type.getName().equals("float") || type == Float.class) {
      return new Float(value);
    } else if (type.getName().equals("double") || type == Double.class) {
      return new Double(value);
    } else if (type.getName().equals("long") || type == Long.class) {
      return new Long(value);
    } else if (type.getName().equals("boolean") || type == Boolean.class) {
      return new Boolean(value);
    } else if (type == Date.class) {
      return java.sql.Date.valueOf(value);
    } else {
      return value;
    }
  }
  
  private static Method findSetter(Class<?> type, String name) {
    Method[] methods = type.getMethods();
    
    String propName = null;
    for (Method m : methods) {
      if (!m.getName().startsWith("set")) continue;
      
      propName = m.getName().substring(3);
      if (propName.toLowerCase().equals(name.toLowerCase())) {
        return m;
      }
    }
    return null;
  }
}
```


이 클래스는 외부에서 호출할 수 있는 한개의 public 메소드와 내부에서 사용할 세 개의 private메소드를 가지고 있다.
이 클래스에 있는 메소드 모두 static으로 선언했다.
즉 인스턴스를 생성할 필요가 없이 클래스 이름으로 바로 호출하겠다는 의도다.
이렇게 특정 인스턴스의 값을 다루지 않는다면 static으로 선언하여 클래스 메소드로 만드는 것이 좋다.

###### bind()메소드
프론트 컨트롤러에서 호출하는 메소드다.
요청 매개변수의 값과 데이터이름, 데이터 타입을 받아서 데이터객체(예:Member, String, Date, Integer 등)를 만드는 일을 한다.

```java
public static Object bind(ServletRequest request, Class<?> dataType, String dataName)
	throws Exception{
	...
   return dataObject;
}
```

이 메소드의 첫 번째 명령문은 dataType이 기본 타입인지 아닌지 검사하는 일이다.
만약 기본타입이라면 즉시 객체를 생성하여 반환할 것이다.

```java
if (isPrimitiveType(dataType)) {
	return createValueObject(dataType, request.getParameter(dataName));
}
```

> `isPrimitiveType()` 메소드는 int,long,float,double,boolean,java.util.Date, java.lang.String 타입에 대해 
> 기본타입으로 간주하며 treu를 반환하는 메소드다.

> `createValueObject()`메소드는 기본 타입의 객체를 생성할 때 호출한다.
> 요청 매개변수의 값으로부터 String이나 Date등의 기본타입 객체를 생성한다.

Member 클래스처럼 dataType이 기본타입이 아닌 경우는 요청 매개변수의 이름과 일치하는 세서 메소드를 찾아서 호출한다.
먼저 요청 매개변수의 이름 목록을 얻는다.

```java
Set<String> paramNames = request.getParameterMap().ketSet();
```

request.getParameterMap()은 매개변수의 이름과 값을 맵 객체에 담아서 반환한다.
우리가 필요한것은 매개변수의 이름이기 때문에 Map의 keySet()을 호출하여 이름 목록만 꺼낸다.

그리고 값을 저장할 객체를 생성하고.
Class의 newInstance()를 사용하면 해당 클래스의 인스턴스를 얻을 수 있따.
new 연산자를 사용하지 않고도 아래 코드처럼 이런 식으로 객체를 생성할 수 있다.

```java
Object dataObject = dataTyep.newInstance();
```

요청 매개변수의 이름 목록이 준비되었으면 for 반복문을 실행gkrh,
데이터 타입 클래스에서 매개변수 이름과 일치하는 프로퍼티(세터메소드)를 찾느다

```java
m = findSetter(dataType, paramName);
```

> `findSetter()` 는 데이터타입(Class)과 매개변수 이름(String)을 주면 세터 메소드를 찾아서 반환하는 메소드 이다.

세터 메소드를 찾았으면 이전에 생성한 dataObject에 대해 호출한다.

```java
if(m != null){
	m.invoke(dataObject, ....);
}
```

세터 메소드를 호출할 때 요청 매개변수의 값을 그 형식에 맞추어 넘긴다.

```java
createValueObject( m.getParameterTypes()[0], request.getParameter(paramName) )
```

createValueObject() 메소드는 앞에서 설명한 바와 같이, 요청 매개변수의 값을 가지고 기본 타입의 객체를 만들어 준다.

이렇게 요청 매개변수의 개수만큼 반복하면서, 데이터 객체(예:Member)에 대해 값을 할당한다.



###### createValueObject() 메소드

기본타입의 경우 세터메소드가 없기 때문에 값을 할당할 수 없고,
보통 생성자를 호출할 때 값을 할당한다.
그래서 createValueObject()메소드를 만든 것이다.
이 메소드는 세터로 값을 할당할 수 없는 기본타입에 대해 객체를 생성하는 메소드이다.


###### findSetter() 메소드

findSetter()는 클래스(type)을 조사하여 주어진 이름(name)과 일치하는 세터 메소드를 찾는다.

```java
private static Method findSetter(Class<?> type, String name){}
```

제일 먼저 데이터 타입에서 메소드 목록을 얻는다.

```java
Method[] methods = type.getMethods();
```

메소드 목록을 반복하여 세터메소드에 대해서만 작업을 수행한다.
만약 메소드 이름이 "set"으로 시작하지 않는다면 무시한다.

```java
for(Method m : methods){
	if(!m.getName().startsWith("set")) continue;
}
```

세터 메소드일 경우 요청매개변수의 이름과 일치하는지 검사한다.
단 대소문자를 구분하지 않기 위해 모두 소문자로 바꾼 다음에 비교한다.
그리고 세터 메소드의 이름에서 "set"은 제외한다

```java
propName = m.getName().substring(3);
if (propName.toLowerCase().equals(name.toLowerCase())) {
	return m;
}
```

마지막으로 일치하는 세터메소드를 찾았다면 즉시 반환한다.

> 이제 서버를 재시작한 후 기능을 테스트 해보자



#### 리플렉션 API 

이번 절에서 가장 중요한 내용은 `리플렉션 API`이다.
이 도구가 없다면 클래스에 어떤 메소드가 있는지, 메소드의 이름은 무엇인지, 클래스의 이름은 무엇인지 알 수가 없다.
사전적 정의처럼 클래스나 메소드의 내부 구조를 들여다 볼 때 사용하는 도구라는 뜻으로 
이번에 사용한 리플렉션 API를 정리해보면 다음 표와 같다

| 메소드 | 설명 |
|--------|--------|
| Class.newInstance() | 주어진 클래스의 인스턴스를 생성 |
| Class.getName() | 클래스의 이름을 반환 |
| Class.getMethods() | 클래스에 선언된 모든 public메소드의 목록을 배열로 반환 |
| Method.invoke() | 해당 메소드를 호출 |
| Method.getParameterTypes() | 메소드의 매개변수 목록을 배열로 반환 |



### 프로퍼티를 이용한 객체 관리

리플렉션 API를 사용하여 프론트 컨트롤러를 개선해서 이제는 페이지 컨트롤러를 추가하더라도 프론트 컨트롤러를 손댈 필요가 없어졌다.

하지만 ContextLoaderListener는 변경해야 한다. 
왜냐하면 이 리스너에서 페이지 컨트롤러 객체를 생성하기 때문이다.

코드를 통해 살펴보자

> ContextLoaderListener

```java
public void contextInitialized(ServletContextEvent event) {
    try {
      ServletContext sc = event.getServletContext();

      InitialContext initialContext = new InitialContext();
      DataSource ds = (DataSource)initialContext.lookup(
          "java:comp/env/jdbc/studydb");

      MySqlMemberDao memberDao = new MySqlMemberDao();
      memberDao.setDataSource(ds);

      sc.setAttribute("/auth/login.do", 
          new LogInController().setMemberDao(memberDao));
      sc.setAttribute("/auth/logout.do", new LogOutController());
      sc.setAttribute("/member/list.do", 
          new MemberListController().setMemberDao(memberDao));
      sc.setAttribute("/member/add.do", 
          new MemberAddController().setMemberDao(memberDao));
      sc.setAttribute("/member/update.do", 
          new MemberUpdateController().setMemberDao(memberDao));
      sc.setAttribute("/member/delete.do", 
          new MemberDeleteController().setMemberDao(memberDao));
    }
}
```

페이지 컨트롤러 뿐만 아니라 DAO를 추가하는 경우에도 ContextLoaderListener 클래스에 코드를 추가해야 한다.

> 이번에는 바로 이부분, 객체를 생성하고 의존객체를 주입하는 부분을 자동화 해보자


#### 프로퍼티 파일 작성

> WebContent/WEB-INF 폴더에 application-context.properties 파일 생성

```
jndi.dataSource-java:comp/env/jdbc/studydb
memberDao=spms.dao.MySqlMemberDao
/auth/login.do=spms.controls.LogInController
/auth/logout.do=spms.controls.LogOutController
/member/list.do=spms.controls.MemberListController
/member/add.do=spms.controls.MemberAddController
/member/update.do=spms.controls.MemberUpdateController
/member/delete.do=spms.controls.MemberDeleteController
```

이 프로퍼티 파일은 ApplicationContext에서 객체를 준비할 때 사용한다.'
객체에 따라 준비하는 방법이 다르므로 몇 가지 작성 규칙을 정의 했다.
이 규칙에 대해서 알아보자 ( 물론 이 규칙은 우리가 만드는 미니 프레임워크에만 해당한다~ )


###### 톰캣 서버에서 제공하는 객체
DataSource 처럼 톰캣 서버에서 제공하는 객체는 ApplicationContext에서 생성할 수 없다.
대신 InitialContext를 통해 해당 객체를 얻어야 한다.

```java
jndi.{객체이름}={JNDI이름}
```

프로퍼티의 키(key)는 `jndi.`와 객체 이름을 결합하여 작성한다.
프로퍼티의 값(value)은 톰캣 서버에 등록된 객체의 JNDI 이름이다.
앞의 프로퍼티 파일에서 첫 번째 줄의 내용이 톰캣 서버가 제공하는 DataSource객체에 대한 것이다.

###### 일반객체
MemberDao와 같은 일반객체는 다음의 규칙으로 설정한다.

```java
{객체이름}={패키지 이름을 포함한 클래스 이름}
```

프로퍼티의 키는 객체를 알아보는 데 도움이 되는 이름을 사용한다.
단 다른 이름과 중복되어서는 안된다.
프로퍼티의 값은 패키지이름을 포함한 전체 클래스 이름(fully qualified class name)이어야 한다.
두 번째 줄이 여기에 해당하는 MemberDao를 설정하는 코드이다.

#### 페이지 컨트롤러 객체
페이지 컨트롤러는 프론트 컨트롤러에서 찾기 쉽도록 다음의 규칙으로 작성한다.

```java
{서블릿 URL}={패키지 이름을 포함한 클래스 이름}
```

프로퍼티의 키는 서블릿 URL이다.
프론트 컨트롤러는 DispatcherServlet은 서블릿 URl을 이용하여 페이지 컨트롤러를 찾는다

```
/auth/login.do=spms.controls.LogInController
```


#### ApplicationContext 클래스

ApplicationContext 클래스는 앞 절의 실습 시나리오에서 언급한 대로 프로퍼티 파일에 설정된 객체를 준비하는 일을 한다.

> spms.context 패키지를 새로 생성한 후 spms.context패키지에 ApplicationContext 클래스를 생성

```java
package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

// 프로퍼티 파일을 이용한 객체 준비
public class ApplicationContext {
  Hashtable<String,Object> objTable = new Hashtable<String,Object>();
  
  public Object getBean(String key) {
    return objTable.get(key);
  }
  
  public ApplicationContext(String propertiesPath) throws Exception {
    Properties props = new Properties();
    props.load(new FileReader(propertiesPath));
    
    prepareObjects(props);
    injectDependency();
  }
  
  private void prepareObjects(Properties props) throws Exception {
    Context ctx = new InitialContext();
    String key = null;
    String value = null;
    
    for (Object item : props.keySet()) {
      key = (String)item;
      value = props.getProperty(key);
      if (key.startsWith("jndi.")) {
        objTable.put(key, ctx.lookup(value));
      } else {
        objTable.put(key, Class.forName(value).newInstance());
      }
    }
  }
  
  private void injectDependency() throws Exception {
    for (String key : objTable.keySet()) {
      if (!key.startsWith("jndi.")) {
        callSetter(objTable.get(key));
      }
    }
  }

  private void callSetter(Object obj) throws Exception {
    Object dependency = null;
    for (Method m : obj.getClass().getMethods()) {
      if (m.getName().startsWith("set")) {
        dependency = findObjectByType(m.getParameterTypes()[0]);
        if (dependency != null) {
          m.invoke(obj, dependency);
        }
      }
    }
  }
  
  private Object findObjectByType(Class<?> type) {
    for (Object obj : objTable.values()) {
      if (type.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }
}
```


###### 객체의 보관
프로퍼티에 설정된 대로 객체를 준비하면, 객체를 저장할 보관소가 필요한데 이를 위해 해시테이블을 준비한다.
또한 해시 테이블에서 객체를 꺼낼(getter)메소드도 정의한다.

```java
Hashtable<String,Object> objTable = new Hashtable<String,Object>();
public Object getBean(String key) {
```

###### 프로퍼티 파일의 로딩
ApplicationContext 생성자가 호출되면 매개변수로 지정된 프로퍼티 파일의 내용을 로딩해야 한다.
이를 위해 java.util.Properties 클래스를 사용했다

```java
Properties props = new Properties();
props.load(new FileReader(propertiesPath));
```

Properties는 `이름=값`형태로 된 파일을 다룰때 사용하는 클래스다.
load()메소드는 FileReader를 통해 읽어들니 프로퍼티 내용을 키-값 형태로 내부 맵에 보관한다.

###### prepareObjects()메소드

프로퍼티 파일의 내용을 로딩했으면, 그에 따라 객체를 준비해야 한다.
prepareObjects()가 바로 그 일을 수행하는 메소드로,
먼저 JNDI객체를 찾을 때 사용할 InitialContext를 준비한다.

```java
Context ctx = new InitialContext();
```

그리고 반복문을 통해 프로퍼티에 들어있는 정보를 꺼내서 객체를 생성한다.

```java
for (Object item : props.keySet()) {...}
```

Properties로부터 클래스 이름을 꺼내려며 키(key)를 알아야 한다.
keySet()메소드는 Properties에 저장된 키 목록을 반환한다.

만약 프로퍼티의 키가 "jndi."로 시작한다면 객체를 생성하지 않고, InitialContext를 통해 얻는다.

```java
if(key.startsWith("jndi.")){
	objTable.put(key, ctx.lookup(value));
}
```

InitialContext의 lookup() 메소드는 JNDI 인터페이스를 통해 톰캣 서버에 등록된 객체를 찾는다.

그 밖의 객체는 Class.forName()을 호출해 클래스를 로딩하고, newInstance()를 사용하여 인스턴스를 생성한다.

```java
else{
	objTable.put(key, Class.forName(value).newInstance());
}
```


###### injectDependency() 메소드
톰캣 서버로부터 객체를 가져오거나(예:DataSource) 직접 객체를 생성했으면(예:MemberDao),
이제는 각 객체가 필요로 하는 의존 객체를 할당해 줘야 한다.
이런일을 하는 메소드가 injectDependency() 다.

```java
if (!key.startsWith("jndi.")) {
	callSetter(objTable.get(key));
}
```

객체 이름이 "jndi."로 시작하는 경우 톰캣 서버에서 제공한 객체이므로 의존객체를 주입해서는 안되므로, 제외시켰다.
나머지 객체에 대해서는 세터 메소드를 호출한다.

###### callSetter() 메소드

callSetter()는 매개변수로 주어진 객체에 대해 세터 메소드를 찾아서 호출하는 일을 하며,
먼저 세터 메소드를 찾는다.

```java
for (Method m : obj.getClass().getMethods()) {
	if (m.getName().startsWith("set")) {
```

세터메소드를 찾았으면 세터 메소드의 매개변수와 타입이 일치하는 객체를 objTable에서 찾는다

```java
dependency = findObjectByType(m.getParameterTypes()[0]);
```

의존 객체를 찾았으면, 세터 메소드를 호출한다.

```java
if (dependency != null) {
	m.invoke(obj, dependency);
}
```

###### findObjectByType() 메소드

이 메소드는 세터 메소드를 호출할 때 넘겨줄 의존객체를 찾는 일을 하며,
objTable에 들어있는 객체를 모두 뒤진다.

```java
for(Object obj : objTable.values()){}
```

만약 세터메소드의 매개변수 타입과 일치하는 객체를 찾았다면 그 객체의 주소를 리턴한다.

```java
if (type.isInstance(obj)) {
	return obj;
}
```

Class의 isInstance() 메소드는 주어진 객체가 해당 클래스 또는 인터페이스의 인스턴스인지 검사한다.


#### ContextLoaderListener 변경

ApplicationContext를 만든 이유는 페이지 컨트롤러나 DAO가 추가되더라도 ContextLoaderListener를 변경하지 않기 위함이다.
정말 그게 가능하닞 확인해보자

> spms.listeners.ContextLoadListener 클래스를 변경한 소스다

```java
package spms.listeners;

// 페이지 컨트롤러 객체 준비
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MySqlMemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
  @Override
  public void contextInitialized(ServletContextEvent event) {
    try {
      ServletContext sc = event.getServletContext();
      
      String propertiesPath=sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
      applicationContext=new ApplicationContext(propertiesPath);
      
    } catch(Throwable e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {}
}
```

얼핏봐도 소스가 한결 간결해졌다.
그것 외에도 정말 중요한 것은 더이상 이제 더이상 이 클래스를 변경할 필요가 없다는 것이다.
페이지 컨트롤러나 DAO등을 추가할 때는 프로퍼티 파일에 그 클래스에 대한 정보를 한줄 추가하면 자동으로 그 객체가 생성된다


###### 프로퍼티 파일의 경로

프로퍼티 파일이ㅡ 이름과 경로 정보도 web.xml 파일로부터 읽어 오게 처리했다.
ServletContext의 getInitParameter()를 호출하여 web.xml에 설정된 매개변수 정보를 가져온다.

```java
String propertiesPath=sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
```

그리고 ApplicationContext 객체를 생성할 때 생성자의 매개변수로 넘겨준다.

```java
applicationContext = new ApplicationContext(propertiesPath);
```

이렇게 생성한 ApplicationContext 객체는 프론트 컨트롤러에서 사용할 수 있게 ContextLoaderListenr의 클래스 변수
applicationContext에 저장된다


###### getApplicationContet() 클래스 메소드
이 메소드는 ContextLoaderListerner에서 만든 ApplicationContext 객체를 얻을 때 사용하며,
당장 프론트 컨트롤러에서 사용해야 한다.
클래스 이름만으로 호출할 수 있게 static으로 선언했다

```java
public static ApplicationContext getApplicationContext(){
	return applicationContext;
}
``


#### Wenb.xml 파일에 프로퍼티 경로 정보 설정

ContextLoaderListener 가 프로퍼티 파일을 찾을 수 있도록 web.xml 파일에 프로퍼티에 대한 파일 경로 정보를 설정하자

> web.xml 에 컨택스트 매개변수를 추가

```xml
<context-param>
	<param-name>contextConfigLocation</param-name>
	<param-value>/WEB-INF/application-context.properties</param-value>
</context-param>
```

#### DispatcherServlet 변경

> spms.servlets.DispatcherServlet

```java
  protected void service(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    String servletPath = request.getServletPath();
    try {
-     ServletContext sc = this.getServletContext();
+     ApplicationContext ctx = ContextLoaderListener.getApplicationContext();

      // 페이지 컨트롤러에게 전달할 Map 객체를 준비한다. 
      HashMap<String,Object> model = new HashMap<String,Object>();
      model.put("session", request.getSession());

-     Controller pageController = (Controller) sc.getAttribute(servletPath);
+     Controller pageController = (Controller) ctx.getBean(servletPath);

      if (pageController == null){
    	  throw new Exception("요청한 서비스를 찾을 수 없습니다.");ㅇ
      }

      if (pageController instanceof DataBinding) {
        prepareRequestData(request, model, (DataBinding)pageController);
      }
```


변경된 소스를 살펴보면 ApplicationContext 를 도입하면서 ServletContext 를 제거했다.

```java
-     ServletContext sc = this.getServletContext();
+     ApplicationContext ctx = ContextLoaderListener.getApplicationContext();
```

대신 ContextLoadListener의 getApplicationContext()를 호출하여 ApplicationContext 객체를 꺼냈다.

페이지 컨트롤러를 찾을 때도 ServletContext에서 찾이 않기 때문에 해당 코드를 역시 제거했다

```java
-     Controller pageController = (Controller) sc.getAttribute(servletPath);
+     Controller pageController = (Controller) ctx.getBean(servletPath);
```

대신 ApplicationContext의 getBean()을 호출해서 페이지 컨트롤러를 찾는다 
만약 차지 못한다면 오류를 발생시킨다.

```java
if (pageController == null){
	throw new Exception("요청한 서비스를 찾을 수 없습니다.");ㅇ
}
```
> ### 파일점검해보기



### 어노테이션을 이용한 객체 관리
프로퍼티 파일을 이용해 DAO나 페이지컨트롤러 등을 관리해 봤는데,

예전보다는 DAO나 페이지 컨트롤러를 추가하더라도 손이 덜 가는 구조이지만, 그럼에도 이런 객체들을 추가할 때 마다 프로퍼티 파일에 한줄 추가해야 하는 약간의 번거로움이 남아있다.

이번 절에서는 이런 약간의 번거로움 마저도 없애보자.
이를 위해서 `어노테이션`을 사용하여 처리한다

> `어노테이션`은 컴파일이나 배포, 실행할 때 참조할 수 있는 아주 특별한 주석이다.
> 어노테이션을 사용하면 클래스나 필드, 메소드에 대해 부가 정보를 등록할 수 있다.


#### 어노테이션 활용 시나리오

1. 웹에플리케이션이 시작되면 서블릿 컨테이너는 ContextLoadListener에 대해 contextInitialized()를 호출한다.

2. contextInitialized()는 ApplicationContext를 생성한다.
생성자의 매개변수 값으로 프로퍼티 파일의 경로를 넘긴다.

3. ApplicationContext 생성자는 프로퍼티 파일을 로딩하여 내부 맵에 보관한다.

4. ApplicationContext 는 맵에 저장된 정보를 껕내 인스턴스를 생성하거나 또는 톰캣 서버에서 객체를 가져온다

5. 또한, 자바 classpath를 뒤져서 어노테이션이 붙은 클래스를 찾는다.
그리고 어노테이션에 지정된 정보에 따라 인스턴스를 생성한다.

6. 객체가 모두 준비되었으면, 각 객체에 대해 의존 객체를 찾아서 할당한다.

이전의 `프로퍼티`를 이용한 객체관리 시나리오와 같지만 중간에 어노테이션이 선언된 클래스를 탐색하는 부분이 추가되었다.

#### 어노테이션 정의

이제 DAO나 페이지 컨트롤러에 붙일 어노테이션을 정의해보자
다음과 같이 클래스 선언에 붙일 @Component 어노테이션이고 어노테이션의 기본값은 객체 이름이다

> @Component 어노테이션의 사용 예

```java
@Component("memberDao") //어노테이션
class MemberDao{
	...
}
```

> spms.annotation 패키지 생성후 Component 어노테이션 생성

```java
package spms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;;

@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
	String value() default "";
}
```

보다시피 어노테이션 문법은 인터페이스와 비슷하다. 단 interface 키워드 앞에 `@`가 붙는다.

```java
public @interface Component {
```

객체 이름을 저장하는 용도로 사용할 `value`라는 기본 속성을 정의했다.
value속성은 값을 설정할 때 이름을 생략할 수 있는 특별한 기능이 있다.

```java
String value() default "";
```

어노테이션의 속성을 선언하는 문법은 인터페이스에서 메소드를 선언하는 문법과 비슷하다.
그러나 인터페이스의 메소드와 달리 `기본값`을 지정할 수 있다.
속성 선언 다음에 오늘 `default`키워드가 기본값을 지정하는 문법이다.
즉 value 속성의 값을 지정하지 않으면 default로 지정한 값(예시에서는 빈문자열)이 할당되는 것이다


###### 어노테이션 유지 정책

어노테이션을 정의할 때 잊지 말아야 할 것은 어노테이션의 유지 정책을 지정하는 것이다.
이것을 깜박 잊고 개발하다가 한참을 헤매는 개발자들도 많으니 유의하자!
`어노테이션 유지 정책`이란 어노테이션 정보를 언제까지 유지할 것인지 설정하는 문법이다.

```java
@Retention(RetentionPolicy.RUNTIME)
```

앞의 코드는 `RUNTIME`으로 지정했기 때문에, 실행 중에도 언제든지 `@Componetn` 어노테이션의 속성값을 참조할 수 있다.

정책 | 설명
-----|----|
RetentionPolicy.SOURCE | 소스 파일에서만 유지, 컴파일할 때 제거됨, 즉 클래스 파일에 어노테이션 정보가 남아 있지 않음
CLASS | 클래스 파일에 기록됨, 실행 시에는 유지되지 않음, 즉 실행 중에서는 클래스에 기록된 어노테이션 값을 꺼낼 수 없음(기본정책)
RUNTIME | 클래스 파일에 기록됨. 실행 시에도 유지됨. 즉, 실행 중에 클래스에 기록된 어노테이션 값을 참조할 수 있음

어노테이션 유지 정책을 지정하지 않으면 기본으로 `RetentionPolicy.CLASS` 가 된다.


#### 어노테이션 적용
어노테이션을 정의했으면 이제 DAO나 페이지 컨트롤러에 적용해 보자
(MySqlMemberDao 파일부터)

> spms.dao.MySqlMemberDao 클래스

```java
@Component("memberDao")
public class MySqlMemberDao implements MemberDao {
```

> 나머지 컨트롤러들도 어노테이션을 적용하자


#### 프로퍼티 파일 변경

어노테이션을 적용했다고 프로퍼티 파일이 필요없는 것은 아니다.
우리가 만든 클래스에 대해서는 어노테이션을 적용할 수 있지만 
**DataSource와 같은 톰캣 서버가 제공하는 객체에는 어노테이션을 적용할 수 없다.
그래서 프로퍼티를 이용한 객체 관리 방법은 그대로 유지**해야 한다


톰캣 서버가 관리하는 JNDI객체나 외부 라이브러리에 들어있는 개체는 우리가 만든 어노테이션을 적용할 수 없기 때문에
프로퍼티 파일에 등록해야 한다


#### ApplicationContext 변경

`@Component` 어노테이션이 붙은 클래스에 대해서도 객체를 생성하도록 ApplicationContext를 변경하자

> spms.context.ApplicationContext 클래스를 다음과 같이 변경하자

```java

```



___




> 궁금한점
 
 1. `import javax.servlet.http.*` 형식으로 사용 가능한가?
 2. 소스작성간 뭘 import 해야하는지는 소스 작성후에 에러 메시지를 통해서 아는 방법 말고 다른 방법은 외우는것 말고는 없는지?
 3. 이클립스에서 자동생성안되는 것들은 외워서 작성해야하는지? 가령 `javax.servlet.RequestDispatcher`

