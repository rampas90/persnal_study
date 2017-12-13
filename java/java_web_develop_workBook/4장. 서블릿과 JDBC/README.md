## 3. 서블릿과 JDBC

> 이번장의 학습과제

- GenericServlet 클래스를 확장한 HttpServlet 클래스를 이용한 서블릿 생성
- 클라이언트의 요청을 GET과 POST등으로 구분하여 처리하는 방법
- 리다이렉트, 리프레시
- 초기화 매개변수를 이용하여 설정 정보를 외부 파일에 두는 방법
- 서블릿에서 이를 참고하는 방법
- 서블릿 실행 전, 후에 필터를 끼우는 방법
- JDBC를 이용하여 데이터베이스에 회원정보를 등록, 조회, 변경, 삭제하는 회원 관리 예제를 생성 (이와 같은 기능을 보통 CRUD라고 부른다.)



### 1) 데이터베이스에서 데이터 가져오기

- 서블릿이 하는 주된 일은 클라이언트가 요청한 데이터를 다루는 일이다.
- 데이터를 가져오거나 입력, 변경, 삭제 등을 처리하려면 데이터베이스의 도움을 받아야 한다.


#### sql 준비

> MySQL 설치및 테스트를 위한 테이블 생성 ( p.163~ 165 참조 )

#### JDBC 드라이버

자바에서 데이터베이스에 접근하려면 JDBC 드라이버가 필요하다. 
자바실행환경(JRE)에는 기본으로 Type 1 JDBC 드라이버가 포함되어 있다.
Type 1 드라이버는 ODBC 드라이버를 사용하기 때문에 개발 PC에 MySQL ODBC 드라이버를 설치해야 한다.
따라서 JRE에서 기본제공하는 JDBC 드라이버를 쓰지 않고 MySQL에서 제공하는 Type 4 드라이버를 사용해보자.

Type 4 JDBC 드라이버는 MySQL 통신 프로토콜에 맞추어 데이터베이스와 직접 통신하기 때문에 ODBC드라이버를 필요로 하지 않는다. 
이러한 이유로 실무에서도 Type 4 드라이버를 주로 사용하고 있다.


>  아래 클래스를 통해 알아보자

```java
package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/jsp", //JDBC URL
					"root",	// DBMS 사용자 아이디
					"apmsetup");	// DBMS 사용자 암호
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + 
					" FROM MEMBERS" +
					" ORDER BY MNO ASC");
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			while(rs.next()) {
				out.println(
					rs.getInt("MNO") + "," +
					rs.getString("MNAME") + "," +
					rs.getString("EMAIL") + "," + 
					rs.getDate("CRE_DATE") + "<br>"
				);
			}
			out.println("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
			
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}

```

> GenericServlet을 상속받음
> 서블릿을 만들고자 javax.servlet.GenericServlet 클래스를 상속받고 service()메소드를 구현한다

```java
public class MemberListServlet extends GenericServlet {
```

> 데이터베이스 관련 객체의 참조 변수 선언
> service()에서 처음 부분은 JDBC 객체 주소를 보관할 참조 변수의 선언이다.

```java
Connection conn = null;
Statement stmt = null;
ResultSet rs = null;
```

> 데이터베이스 관련 코드를 위한 try~catch
> 예외발생을 고려한 try~catch 블록으로 에외가 발생하면 그 예외를 `ServletException`객체에 담아서 이 메소드를 호출한 서블릿 컨테이너에 던진다.
> 서블릿 컨테이너는 예외에 따른 적절한 화면을 생성하여 클라이언트에게 출력할 것이다.

```java
try{

}catch(Exception e){
	throw new ServletException(e);
}finally{

}
```

> DriverManager가 사용할 JDBC 드라이버 등록
JDBC 프로그래밍의 첫 번째 작업은 DriverManager를 이용하여 java.sql.Driver 인터페이스 구현체를 등록하는 일이다. JDBC 드라이버의 문서를 찹조하여 해당 클래스를 찾자

`DriberManager.registerDriver(new com.mysql.jdbc.Driver());`

> MySQL 드라이버의 경우`com.mysql.jdbc.Driber`클래스가 해당 인터페이를 구현한 클래스다.
registerDriver()를 호출하여 구현체를 등록한다.

##### 데이터베이스에 연결

다음과 같이 DriverManager의 getConnection(0을 호출하여 MySQL서버에 연결할 수 있다.

```java
conn = DriverManager.getConnection(
	"jdbc:mysql://localhost/jsp", //JDBC URL
	"root",	// DBMS 사용자 아이디
	"apmsetup");	// DBMS 사용자 암호
)
```

- getConnecttion()의 첫 번째 인자값은 MySQL 서버의 접속정보다. (JDBC URL)
- DriverManager는 등록된 JDBC드라이버 중에서 이 URL을 승인하는 드라이버를 찾아 java.sql.Driver 구현체를 사용하여 데이터베이스에 연결한다.
- JDBC URL의 형식을 살펴보면 'jdbc.:mysql'은 JDBC 드라이버의 이름이고, '//localhost/jsp'는 접속할 서버의 주소(localhost)와 데이터베이스 이름(jsp)이다.
- 이때 JDBC URL 형식은 드라이버에 따라 조금씩 다르므로 자세한 내용은 각 드라이버에서 제공하는 개발 문서를 참고하자

```
jdbc:mysql:thin:@localhost:3306:jsp
사용할 JDBC드라이버 : 드라이버타입 : @서버주소:포트번호 : DB서비스 아이디
```

- getConnection()의 두 번째와 세번째 인자값은 데이터베이스의 사용자 아이디와 암호다
- 이렇게 JDBC URL정보와 사용자 아이디, 암호를 가지고 MySQL서버에 연결을 요청하고, 연결에 성공하면 DB접속 정보를 다루는 객체를 반환한다.
- 반환된 객체는 java.sql.Connection 인터페이스의 구현체 이다.
- 이 반환 객체를 통해서 데이터베이스에 SQL문을 보내는 객체를 얻을 수 있다.

> `java.sql.Connection` 인터페이스의 구현체
> 이 인터페이싀 정의된 주요 메소드는 다음과 같다
> - createStatement(), prepareStatement(), prepareCall()은 SQL 문을 실행하는 객체를 반환한다.
> - commit(), rollback()은 트랜잭션 처리를 수행하는 메소드다.
> **즉 이 구현체를 통해 SQL문을 실행할 객체를 얻을 수 있다.**

##### SQL 실행 객체 준비
Connection 구현체를 이용하여 SQL문을 실행할 객체를 준비한다.

```java
stmt = conn.createStatement();
```

- createStatement()가 반환하는 것은 java.sql.Statement 인터페이스의 구현체이다.
- 이 객체를 통해 데이터베이스에 SQL문을 보낼 수 있다.
- Statement 인터페이스에는 데이터베이스에 질의하는 데 필요한 메소드가 정의되어 있고, 이 인터페이스를 구현했다는 것은 반환 객체가 이러한 메소드들을 가지고 있다는 뜻이다.
- 즉 반환 객체를 이용하면 SQL문을 서버에 보낼 수 있다.

> `java.sql.Statement` 인터페이스의 구현체
> 이 인터페이스의 주요 메소드는 다음과 같다
> - excuteQuery() : 결과가 만들어지는 SQL문을 실행할 때 사용한다. 보통 SELECT문에 사용
> - excuteUpdate() : `DML`과 `DDL`관련 SQL문을 실행할 때 사용한다.
> DML에는 `INSERT`, `UPDATE`, `DELETE` 명령문이 있고, `DDL`에는 `CREATE`, `ALTER`, `DROP` 명령문이 있다.
> - excute() : SELECT, DML, DDL 명령문 모두에 사용 가능하다.
> - excuteBatch() : addBatch()로 등록한 여러 개의 SQL문을 한꺼번에 실행할 대 사용한다.
> **즉 이 구현체를 통해 연결된 데이터베이스에 SQL문을 보낼 수 있다.**


##### 데이터베이스에 SQL문을 보내기

아래 코드는 회원정보를 조회하는 SQL 문을 서버에 보내는 명령문이다. (SELECT 명령문은 excuteQuery)
```java
rs = stmt.excuteQuery(
	"select MNO, MNAME, EAMIL, CRE_DATE from MEMBERS order by MNO ASC"
);
```
- `excuteQuery()`가 반환하는 객체는 java.sql.ResultSet 인터페이스의 구현체이다. 
- 이 반환객체를 통하여 서버에서 질의 결과를 가져올 수 있다.

> `java.sql.ResultSet` 인터페이스의 구현체
> 이 인터페이스의 주요 메소드는 다음과 같다
> - `first()`는 서버에서 첫 번째 레코드를 가져온다
> - `last()`는 서버에서 마지막번째 레코드를 가져온다
> - `previous()`는 서버에서 이전 레코드를 가져온다.
> - `next()`는 서버에서 다음 레코드를 가져온다
> - `getXXX()`는 레코드에서 특정 칼럼의 값을 꺼내며 XXX는 칼럼의 타입에 따라 다른 이름을 갖는다
> - `updateXXX()` 는 레코드에서 특정 칼럼의 값을 변경한다.
> - `deleteRow()`는 현재 레코드를 지운다.
> **즉 이 구현체를 사용하여 서버에 만들어진 SELECT결과를 가져올 수 있고, 가져온 레코드에서 특정 칼럼 값을 꺼낼수 있다.**

##### HTML페이지에 쿼리결과 뿌려주기

```java
while(rs.next()) {
	out.println(
		rs.getInt("MNO") + "," +
		rs.getString("MNAME") + "," +
		rs.getString("EMAIL") + "," + 
		rs.getDate("CRE_DATE") + "<br>"
	);
}
```

- 서버에서 받은 레코드는 ResultSet 객체에 보관된다
- next()는 서버에서 레코드를 받으면 true, 없으면 false를 반환한다.

>  레코드에서 칼럼값을 꺼낼 때는 getXXX()를 호출한다.

- getXXX의 인자값은 SELECT결과의 칼럼 인덱스나 칼럼 이름이 될 수 있다.
- 이때 주의할점은 칼럼 인덱스는 `1`부터 시작한다는 것이다.


```sql
select MNO from MEMBERS order by MNO ASC
```


>  예를 들어 위 쿼리의 결과 값인 `MNO`를 꺼내온다면 다음과 같다

```java
// MNO가 숫자이기 때문에 getInt 만약 이름(MNAME)을 불러온다면 getString(MNAME)
getInt(1);      
getInt("MNO");   // 인덱스는 순서에 영향을 받기 때문에 보통 이와같이 칼럼명으로 사용하면 된다.
```

##### 마무리

JDBC 프로그래밍을 할 때 주의할 점은 정상적으로 수행되든 오류가 발생하든 간에 반드시 자원해제를 수행하는 것이다.
자원을 해제하기 가장 좋은 위치는 `finally 블록`이다.

자원을 해제할 때는 역순으로 처리한다.


##### 서블릿 배치 정보 설정
> `@WebServlet` 어노테이션으로 서블릿의 배치 정보를 설정한다.
> 만약 서블릿 컨테이너가 Servlet2.5 사양을 따른다면 어노테이션을 이용한 설정 방법은 사용할 수 없기때문에
> Web.xml 파일에 배치 정보를 작성하잗.

```java
@WebServlet("/member/list")
```

---

### 2) HttpServlet 으로 GET 요청 다루기

HttpServlet 클래스를 사용하여 서블릿을 만드는 방법을 이용하면, 
지금까지 처럼 서블릿 클래스를 만들 때 service() 메소드를 정의했는데, HttpServlet 클래스를 상속받게 되면 service() 대신 doGet()이나 doPost()를 정의한다.

```java
package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
	}
}

```



>  우선 아래처럼 `GenericServlet` 추상 클래스 대신 `HttpServlet`추상 클래스를 상속받자.

```java
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
```

- `HttpServlet` 클래스는 `GenericServlet` 클래스의 하위 클래스기때문에 `HttpServlet`을 상속받으면`GenericServlet` 클래스를 상속받는 것과 마찬가지로 `java.servlet.Servlet`인터페이스를 구현한 것이 된다.

> doGet() 메소드의 오버라이딩

```java
protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
```

`HttpServlet` 클래스에 정의된 메소드 중에서 doGet() 메소드를 오버라이딩 한다.
- 클라이언트 요청이 들어오면 서블릿 컨테이너는 service()를 호출하기 때문에 지금까지 서블릿을 만들때 service()메소드를 작성해왔다.

> 그러나 `service()`가 아닌 `doGet()`을 작성하는 이유는 다음과 같다

클라이언트 요청이 들어오면, 첫째로 상속받은 HttpServlet의 service() 메소드가 호출되고, 둘재 service()는 클라이언트 요청방식에 따라 doGet()이나 doPost(), doPut()등의 메소드를 호출한다.
따라서. HttpServlet을 상속받을 때 service()메소드를 직접 구현하기 보다는 클라이언트의 요청방식에 따라 doXXX() 메소드를 오버라이딩 하는 것이다.


---

### 3) HttpServlet 으로 POST 요청 다루기

doGET 처럼 doPOST()메소드를 작성하자.

```java
@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/studydb", //JDBC URL
					"study",	// DBMS 사용자 아이디
					"study");	// DBMS 사용자 암호
			stmt = conn.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"
					+ " VALUES (?,?,?,NOW(),NOW())");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			stmt.setString(3, request.getParameter("name"));
			stmt.executeUpdate();
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원등록결과</title></head>");
			out.println("<body>");
			out.println("<p>등록 성공입니다!</p>");
			out.println("</body></html>");
			
		} catch (Exception e) {
			throw new ServletException(e);
			
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
```

> JDBC 객체를 위한 참조 변수 선언

- '회원목록조회' 와 마찬가지로 JDBC 객체를 보관할 참조 변수를 선언한다.
- 이번에는 insert SQL 문을 실행하기 때문에 ResultSet의 참조변수는 선언하지 않는다.

```java
Connection conn = null;
PreparedStatement stmt = null;
```

이전 소스(MemberListServlet)와 다른점은 SQL 문을 실행하는데 `Statement` 대신 `PreparedStatement`를 사용한다는 것이다..
`PreparedStatement`는 반복적인 질의를 하거나, 입력 매개변수가 많은 경우에 유용하다.
특히 바이너리 데이터를 저장하거나 변경할 때는 `PreparedStatement` 만이 가능하다


##### 입력 매개변수의 값 설정
입력 매개변수는 SQL문에서 `?` 문자로 표시된 입력 항목을 가리키는 말이다.
입력항목의 값은 SQL문을 실행하기 전에 setXXX() 메소드를 호출하여 설정한다.


```java
stmt.setString(1, request.getParameter("email"));
```

getXXX와 마찬가지로 문자열이면 setString() 정수형이면 setInt(), 날짜형이면 setDate()을 호출하면 된다.
매개변수의 번호 역시 배열의 인덱스와 달리 `0`이 아닌 `1`로 시작한다.


##### SQL 질의 수행

SQL문을 준비하고 입력 매개변수에 값을 할당했으면 데이터베이스에 질의하면 된다 이때 excuteUpdate()를 호출하여  SQL을 실행한다.
```java
stmt.executeUpdate();
```

>  결과를 반환하는 SELECT 문을 실행할 때는 `excuteQuery()`를 호출하고, INSERT처럼 결과 레코드를 만들지 않는 DDL이나 DML종류의 SQL문을 실행할 때는 `excuteUpdate()`을 호출한다.


##### Statement vs.PreparedStatement


| 비교항목 | Statement | PreparedStatement 
|--------|--------|---------|
| 실행속도 | 질의할 때마다 SQL문을 컴파일한다. | SQL문을 미리 준비하여 컴파일해둔다. 입력 매개변수 값만 추가하여 서버에 전송한다. 특히 여러 번 반복하여 질의하는 경우, 실행속도가 빠름.  |
|바이너리 데이터전송 |불가능 |  가능
| 프로그래밍 편의성 |SQL문 안에 입력 매개변수 값이 포함되어 있어서 SQL문이 복잡하고 매개변수가 여러개인경우 코드관리가 힘들다. | SQL문과 입력 매개변수가 분리되어 있어서 코드 작성이 편리하다


--- 

### 4) 요청매개변수의 한글 깨짐 처리

웹 브라우저에서 보낸 한글 데이터를 서블릿에서 꺼낼 때 글자가 깨지는 경우가 있다. 그 이유와 해결책을 알아보자

- 웹브라우저(내 경우엔 크롬) 기본 문자집합설정이 유니코드(UTF-8)로 설정된걸 확인 할수 있다.

> 웹 브라우저가 웹 서버에 데이터를 보낼 때 문자형식

웹브라우저가 웹 서버로 데이터를 보낼 때는 웹 페이지의 기본 문자집합으로 인코딩하여 보내기 때문에 사용자가 입력한 값은 UTF-8 로 인코딩되어 서버에 전달된다.

> 서블릿에서 데이터를 꺼낼 때 문자 형식

- 서블릿에서 getParameter()를 호출하면 이 메소드는 기본적으로 매개변수의 값이 ISO-8859-1(다른 말로 ISO-Latin-1)로 인코딩되었다고 가정하고 각 바이트를 유니코드로 바꾸고 나서 반환하게된다.
- 즉, 클라이언트가 보낸 문자를 영어라고 간주하고 유니코드로 바꾼다는 말이다.
- 바로 여기서 문제가 발생한다.

- 서블릿이 웹 브라우져로부터 받은 한글 데이터는 UTF-8로 인코딩된 값이다.
- UTF-8은 한글 한 자를 3바이트로 표현한다.
- 서블릿은 3바이트를 하나의 문자로 인식하지 않고 각각의 바이트를 개별문자로 취급하여 유니코드로 변환한다.
- 이렇게 각각의 의미없는 바이트들을 유니코드로 바꿨기 때문에 한글이 깨진것이다.

> 한글깨짐 해결책
- getParameter()를 호출하기 전에 클라이언트가 보낸 매개변수의 값이 어떤 인코딩으로 되어 있는지 지정해야 한다.


```java
throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");   <<이부분을 추가했다.
		Connection conn = null;
		PreparedStatement stmt = null;
```

>  HttpServletRequest의 setCharacterEncoding()은 매개변수 값의 인코딩 형식을 지정하는 메소드다.
>  단, 이 메소드가 호출되기 전에 getParameter()가 먼저 호출된다면 아무 소용이 없다.


GET요청의 쿼리스트링에 대해서는 위 내용이 적용안되기 때문에 GET요청으로 받아오는 문자열이 깨질때는 톰캣서버의 server.xml을 수정하자

---

### 5) 리프레시

일정시간이 지나고 나서 자동으로 서버에 요청을 보내는 방법을 알아보자
실행 프로세스는 다음과 같다.

1. 웹 브라우저에서 '회원 등록 입력폼'을 요청
2. `MemberAddServlet`은 회원 등록폼을 클라이언트로 보낸다.
3. 웹 브라우저는 회원 등록폼을 화면에 출력한다. 사용자가 회원정보를 입력하고 나서 '추가'버튼을 누르면 서버에 등록을 요청한다.
4. `MemberAddServlet`은 회원 등록 결과를 클라이언트로 보낸다.
5. 웹 브라우저는 회원 등록 결과를 출력한다. 그리고 1초후에 서버에 '회원목록'을 요청한다.
6. `MemberListServlet`은 회원 목록을 클라이언트로 보낸다.

> 위 내용의 골자는 `MemberAddServlet`에서 회원 등록 결과를 웹 브라우져로 보낼 때 리프래시 정보를 함께 보내면 된다.


> 아래코드를 try블록 제일 하단에 입력한다.

```java
response.addHeader("Refresh", "1;url=list");
```

> 또다른 방법은 HTML의 meta태그를 이용한 방법이다.

```java
out.println("<meta http-equiv='Refresh' content='1; url=list'>");
```

> 당연한 얘기지만 `meta태그`이므로 `<head>`안에 선언해야 한다.

---

### 6) 리다이렉트

리프레시와 비슷하지만 결과를 출력하지 않고 곧바로 회원목록 화면으로 이동하게 하는 방법이다.

> 리다이렉트 메소드 `sendRedirect()`

```java
// 리다이렉트를 이용한 리프래시
response.sendRedirect("list");
```


---


### 7) 서블릿 초기화 매개변수

##### 서블릿 초기화 매개변수란?
- 서블릿을 생성하고 초기화할 때, 즉init()를 호출할 때 서블릿 컨테이너가 전달하는 데이터이다.
- 보통 DB 연결 정보와 같은 정적인 데이터를 서블릿에 전달할 때 사용한다.
- 서블릿 초기화 매개변수는 DD파일(web.xml)의 서블릿 배치 정보에 설정할 수 있고, 어노테이션을 사용하여 서블릿 소스 코드에 설정할 수 있다.
- 가능한 소스 코드에서 분리하여 외부 파일에 두는 것을 추천하는 데 이는 외부파일에 두면 변경하기 쉽기 때문이다.
- 실무에서도 데이터베이스 정보와 같은 시스템 환경과 관련된 정보는 외부 파일에 두어 관리한다.


##### 회원 정보 조회와 변경

1. 웹 브라우저가 회원 목록을 요청(/member/list, GET) 한다
2. MemberListServlet 은 회원 목록을 웹브라우저로 보낸다, 웹 브라우저는 회원 목록을 출력한다.
3. 사용자가 회원 목록에서 이름을 클릭하면 회원 상세정보를 요청(/member/update, GET)한다
4. MemberUpdateServlet은 회원 상세 정보를 웹 브라우저에 보낸다.
5. 사용자가 회원 정보를 변경하고 저장을 클릭하면 회원 정보 변경을 요청(/member/update, POST 요청)한다.
6. MemberUpdateServlet은 회원 정보 변경 결과를 웹 브라우저에 보낸다. 웹브라우저는 그 결과를 출력한다.


> 앞서 말했듯이 `web.xml`에 서블랫 배치 정보를 작성할 수 있다.
> `<int-param>` 이 서블릿 초기화 매개변수를 설정하는 태그로, 이 엘리먼트는 `<servlet>`태그의 자식 엘리먼트이다.
> `<int-param>` 에도 두 개의 자식 엘리먼트가 있는데,
> `<param-name>`에는 매개변수의 이름을 지정하고 `<param-value>` 에는 매개변수의 값을 지정하면 된다.


```xml
<init-param>
	<param-name>매개변수 이름</param-name>
    <param-value>매개변수 값</param-value>
</init-param>
```

매개 변수 값을 여러개 설정하고 싶으면 `<init-param>` 엘리먼트를 여러개 작성하면 된다
서블릿 초기화 매개변수들은 오직 그 매개변수를 선언한 서블릿에서만 사용할 수 있으며 다른 서블릿은 사용할 수 없다.

이처럼 서블릿 초기화 매개변수에 `DB` 정보를 두면 나중에 정보가 바뀌더라도 `web.xml` 만 편집하면 되기 때문에 유지보수도 쉬워진다.
실무에서도 이처럼 변경되기 쉬운 값들은 XML파일(.xml)이나 프로퍼티 파일(.properties)에 두어 관리한다.

> 즉 내가 dbcon.jsp 파일로 별도로 만들어 import 했던 내용을 web.xml 로 셋팅해둘수 잇다는 말이다.


##### 초기화 매개변수를 이용하여 JDBC 드라이버 로딩

> 드디어 JSP 게시판 만들기와 다른 점이 나왔다. 참고사항!

> 지금 까지는 아래와 같이 jdbc드라이버를 로드했었다면..

```java
DriverManager.registerDriver(new com.mysql.jdbc.Driver());
```

`Class.forName()`을 사용하여 JDBC 드라이버클래스, 즉 java.sql.Driver를 로딩할 수 있다.
> 클래스 로딩

```java
Class.forName(/* JDBC 드라이버 클래스의 이름 */)
```
> 아래는 JSP 로 처음 기본 게시판 셋팅시 내가 작성했던 코드다..비교해보자

```java
Class.forName("com.mysql.jdbc.Driver");
conn = DriverManaget.getConnection(디비주소,디비유저명,디비비밀번호);
```

- `Class.forName`은 인자값으로 클래스 이름을 넘기면 해당 클래스를 찾아 로딩한다.
- 클래스 이름은 반드시 패키지 이름을 포함해야 한다. 보통 영어로 'fully qualified name' 또는 'QName'이라고 표현한다.
- 자바 API에서 많이 등장하는 용어이니 반드시 기억하고 넘어가자

JDBC 드라이버 클래스의 이름은 서블릿 초기화 매개변수에서 얻어온다.
다음과 같이 `HttpServlet` 클래스에서 상속받은 `getInitParameter()`를 이용하면 서블릿 초기화 매개변수의 값을 꺼낼수 있다.

> 서블릿 초기화 매개변수의 값 꺼내기
```java
this.getInitParameter(/* 매개변수 이름 */);
```

- getInitParameter() 은 해당 서블릿의 배치 정보가 있는 web.xml로부터 `<init-param>`의 매개변수 값을 꺼내준다.
- 이때 반환하는 값은 문자열이다.

> 최종적으로 아래와 같이 작성하면 된다.

```java
Class.forName(this.getInitParameter("driver"));
```

> 데이터베이스 연결도 같은 방법으로 하면 된다.

```java
conn=DriverManager.getConnection(
	this.getInitParameter("url"),
    this.getInitParameter("username"),
    this.getInitParameter("password")
)
```

> 쿼리 결과는 회원정보 상세보기인 만큼 한개의 정보만 가져오기 때문에 아래와 같이 next()를 한번만 호출하면된다.

```java
rs = stmt.excuteQuery(select 쿼리 문);
rs.nest();
```

> 또한 web.xml이 아닌 `@WebServlet`어노테이션으로 설정할수도 있지만 소스코드에 정보가 들어가는만큼 추천하지않는다.
> 언제라도 바뀔 수 있는 정보는 소스 파일이 아닌 외부 파일에 두어야 개발자가 아닌 시스템 운영자도 변경할 수 있기 때문이다.

```java
@WebInitParam(name="driver". value="com.mysql.jdbc.Driver");
@WebInitParam(name="url". value="localhost/jsp");
// 이하 생략
```
@WebInitParams 어노테이션의 initParams는 서블릿 초기화 매개변수를 설정하는 속성으로 이속성의 값은 @WebInitParam의 배열이다.
`initParams={@WebInitParam(), @WebInitParam(), ...}`

@WebInitParam은 두 개의 필수 속성과 한 개의 선택 속성이 올 수 있다.

```java
@WebInitParam(
	name="매개변수 이름",						 // 필수
	value="매개변수 값",							 // 필수
	description="매개변수에 대한 설명"   // 선택
)
```

---

### 8) 컨텍스트 초기화 매개변수

서블릿 초기화 매개변수는 말 그대로 그 매개변수가 선언된 서블릿에서만 사용할 수 있으며, 다른 서블릿은 참조할 수 없다.
따라서 JDBC 다르이버와 데이터베이스 연결정보에 대한 초기화 매개변수를 각 서블릿마다 설정해 줘야 하는 문제가 있다.
이런 경우에 컨텍스트 초기화 매개변수를 사용한다.
`컨텍스트 초기화 매개변수`는  같은 웹 어플리케이션에 소속된 서블릿들이 공유하는 매개변수이다.


> web.xml 에 아래와 같은 파라미터로 작성가능하며 작성양식은 서블릿 초기화 매개변수와 거의 흡사하다.
```xml
<context-param>
		<param-name>driver</param-name>
		<param-value>com.mysql.jdbc.Driver</param-value>
	</context-param>
	<context-param>
		<param-name>url</param-name>
		<param-value>jdbc:mysql://localhost/studydb</param-value>
	</context-param>
	<context-param>
		<param-name>username</param-name>
		<param-value>study</param-value>
	</context-param>
	<context-param>
		<param-name>password</param-name>
		<param-value>study</param-value>
	</context-param>
```

> 컨텍스트 초기화 매개변수의 값을 얻으려면 `ServletContext 객체`  가 필요하다.
> `HttpServlet`으로부터 상속받은 `getServletContext()`를 호출하여 `ServletContext()`객체를 준비한다.

```java
ServletContext sc = this.getServletContext();
```

---


### 9) 필터 사용하기

- 필터는 서블릿 실행 전후에 어떤 작업을 하고자 할 때 사용하는 기술이다.
- 예를 들면 클라이언트가 보낸 데이터의 암호를 해제한다거나, 
- 서블릿이 실행되기전에 필요한 자원을 미리준비한다거나,
- 서블릿이 실행될 때마다 로그를 남긴다거나 하는 작업을 필터를 통해 처리할 수 있다.

> 만약 이런 작업들을 서블릿에 담는다면 서블릿마다 해당 코드를 삽입해야 하고, 
> 필요가 없어지면 그 코드를 삽입한 서블릿을 모두 찾아서 제거해야 하므로 관리하기가 매우 번거로울 것이다.


예를 들면 지난 예제에서 `setCharacterEncoding()`을 호출하여 메시지 바디의 데이터 인코딩을 설정해줬었는데
이를 각 서블릿마다 매번 작성하기는 매우 번거롭다. 
바로 이럴때 서블릿 필터를 이용하여 간단히 처리할 수 있다.

- 필터클래스는 javax.servlet.Filter 인터페이스를 구현해야 한다.

```java
package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(
	urlPatterns="/*",
	initParams={
		@WebInitParam(name="encoding",value="UTF-8")
	})
public class CharacterEncodingFilter implements Filter{
	FilterConfig config;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
	
	@Override
	public void doFilter(
			ServletRequest request, ServletResponse response,
			FilterChain nextFilter) throws IOException, ServletException {
		request.setCharacterEncoding(config.getInitParameter("encoding"));
		nextFilter.doFilter(request, response);
	}

	@Override
	public void destroy() {}
}

```