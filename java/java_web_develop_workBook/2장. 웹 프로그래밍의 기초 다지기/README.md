## 2. 웹 프로그래밍의 기초다지기

#### 1) HTTP 프로토콜이란?

- `HTTP`프로토콜은 웹 브라우저와 웹 서버 사이의 데이터 통신 규칙이다.
- 우리가 링크를 클릭하면 웹브라우저는 `HTTP`요청 형식에 따라 웹 서버에 데이터를 보낸다.
- 이때 보내는 데이터는 `HTTP`응답 형식에 맞추어 보내면 된다.

> `HTTP 프로토콜`은 단순이 `HTML`페이지나 이미지 파일을 전송하는 차원을 넘어서,
> 원격 컴퓨터에 로딩되어 있는 함수나 객체의 메서드를 호출할 때도 사용된다.
> 특히 웹 어플리케이션을 개발하다 보면 `SOAP(Simple Object Access Protocol)`  나  `RESTful(REpresentational State Transfer)`이라는 용어를 만나게 되는데 이것은 클라이언트와 서버 사이에 서비스를 요청하고 응답을 하는 방식을 말한다.

>  이 두가지 모두 `HTTP프로토콜`을 응용하거나 확장한 기술이다.
>  특히 아마존의 클라우드나 KT의 클라우드는 자신들의 서비스와 연동할 도구로 `RESTful` 방식의 API를 제공하고 있다.

> `HTTP` 프로토콜 응용 기술 몇가지를 더 소개하자면 
> `WebDAV(World Wide Distributed Authoring and Versioning)`를 들 수 있다.
> 이는 웹상에서 여러사람이 문서나 파일을 더 쉽게 편집하고 다룰 수 있게 협업을 도와주는 기술이다.
> `WebDAV`를 응용한 `CalDAV`기술도 있다 이것은 캘린더 데이터를 보다 쉽게 편집하고 공유할 수 있도록 `WebDAV`를 확장한 기술이다.


---



#### 2) HTTP 모니터링

웹 브라우저와 웹 서버 사이에 주고받는 데이터를 들여다보려면 HTTP 프록시 프로그램이 필요하다.
> fiddler를 사용하자

웹  브라우저가 웹 서버에게 요청을 하면 HTTP프록시가 그 요청을 대신 받아서 서버에 전달한다.
서버에서 응답이 오면 HTTP프록시가 그 응답을 대신 받아서 웹 브라우저에게 전달한다.
이렇게 웹 브라우저와 웹 서버의 중간에서 요청이나 응답 내용을 중계해 주기 때문에 둘사이에서 주고받는 내용이 무엇인지 엿볼 수 있다.

>  [프록시 서버란? (검색바로가기)](https://www.google.co.kr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=%ED%94%84%EB%A1%9D%EC%8B%9C%EC%84%9C%EB%B2%84)


fidller를 이용해 `daum.net` 요청데이터를 살펴보자

```
GET / HTTP/1.1
Host: www.daum.net
Connection: keep-alive
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36
Accept-Encoding: gzip, deflate, sdch
Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4

```

요청라인(Request-Line)
요청메시지의 첫 라인은 메서드와 요청하는 자원, 프로토콜 버전으로 구성된다.

> 메서드에는 GET,POST, HEAD, PUT, DELETE, TRACE, CONNECT, OPTIONS 등이 있으며
> 요청URI는 요청하는 자원의 식별자이다.
> 즉 HTML이나 이미지, 동영상, 어플리케이션 등이 있는 가상의 경로라고 생각하면 된다.
> 웹 서버는 이 식별자를 이용하여 해당 자원을 찾습니다.
> `HTTP`버전은 요청 정보가 어떤 버전에 맞추어 작성했는지 웹 서버에게 알려주기 위함이다.

요청헤더
HTTP 요청 내용중에 2~7번 라인은 서버가 요청을 처리할 때 참고하라고 클라이언트에서 웹서버에게 알려주는 정보다.

이정보를 `헤더` 라고 말한다.

> 헤더에는 세가지 종류가 있는데 요청이나 응답 모두에 적용할 수 있는 `일반헤더(General-header)`와 요청 또는 응답 둘 중 하나에만 적용할 수 있는 `요청 헤더 또는 응답헤더(Request-header/Response-header)`, 보내거나 받는 본문 데이터를 설명하는 `엔티티헤더(Entity-header)`가 있다.

요청 헤더 중에서 `User-Agent`는 클라이언트의 정보를 서버에게 알려주는 헤더로, 웹 서버는 이 헤더를 분석하여 요청자의 Os와 브라우저를 구분한다.

	PHP의 Referer 함수나 서버변수에 저장되는 정보와 무슨 관계인지 알아보기!
    

---

#### 3)HTTP 클라이언트 만들기

클라이언트와 서버가 주고받는 데이터 형식만 안다면 누구나 클라이언트나 서버를 개발할 수 있다.


```java
package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SimpleHttpClient {

	public static void main(String[] args) throws Exception {
		// 1. 소켓 및 입출력 스트림 준비
		Socket socket=new Socket("www.daum.net",80);
		BufferedReader in=new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		PrintStream out=new PrintStream(socket.getOutputStream());
		
		// 2. 요청라인 출력
		out.println("GET / HTTP/1.1");
		
		// 3. 헤더정보 출력
		out.println("Host: www.daum.net");
		out.println("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0)"
				+ " AppleWebKit/537.36 (KHTML, like Gecko)"
				+ " Chrome/30.0.1599.101 Safari/537.36");
		
        // 4. 공백라인 출력
		out.println();
        
		// 5. 응답 내용 출력
		String line=null;
		while((line = in.readLine()) != null){
			System.out.println(line);
		}
		
		in.close();
		out.close();
		socket.close();
	}
}

```
> 위 코드`(SimpleHttpClient 클래스)`를 살펴보자

1. 접속할 웹서버는 '다음' 사이트. 웹 서버의 기본 포트번호는 80이기 때문에 접속할 서버의 포트번호를 80으로 지정한 후, 소켓으로 입/출력을 하기 위한 객체를 준비한다.

2. 먼저 서버에게 수행할 작업을 알려주는 요청라인을 보낸다. 요청형식은 GET, 원하는 자원은 웹 서버 루트 폴더에 있는 기본문서(/), 사용할 프로토콜은 HTTP, 버전은 1.1 이다

3. 웹서버에 부가 정보를 보낸다. 접속하려는 웹 서버의 주소는 `www.daum.net` 요청자의 정보는 크롬브라우저라고 설정한다. '다음' 웹 서버는 Host, User-Agent 이렇게 두 가지 헤더만 보내도 정상적으로 응답해준다.

4. 요청의 끝을 표시하기 위해 공백라인을 보낸다.

5.  서버로부터 받은 데이터를 라인 단위로 읽어서 출력한다.


> 널리 알려진 프로토콜 몇 가지를 간단히 살펴보자


1) FTP (File Transfer Protocol)
- 클라이언트와 서버 간에 파일을 주고받기 위해 만든 통신 규약

2) Telnet 프로토콜
- 인터넷이나 LAN 상에서 문자 기반으로 원격의 컴퓨터를 제어하기 위해 만든 통신 규약이다.
- 요즘은 보안 때문에 SSH 프로토콜 기반 원격 접속 프로그램을 주로 사용한다.

3) XMPP (Extensible Messaging and Presence Protocol)
- 인스턴스 메시지 및 사용자의 접속 상태 정보를 교환할 목적으로 만든 통신 규약이며 Google Talk가 이 프로토콜을 기반으로 통신합니다.

4) SMTP (Simple Mail Transfer Protocol)
- 인터넷 상에서 메일을 보내기 위한 통신 규약
- POP3(Post  Office Protocol version 3)는 이메일을 가져오는 데 사용하는 통신규약이며 POP3는 이메일으르 가져온 후 서버의 메일을 삭제한다.

5) IMAP(Internet Message Access Protocol)
- POP3와 달리 이메일을 가져온 뒤에 서버의 메일을 지우지 않으며 요즘처럼 여러 대의 장비에서 이메일을 조회하는 경우에 적합하다. 단, POP3에 비해 통신 트래픽이 높은 것이 단점이다.

6) LDAP(Lightweight Directory Access Protocol)
- 디렉터리 서비스에 등록된 자원들을 찾는 통신 규약

7) IRC(Internet Relay Chat)
- 실시간 채팅을 위해 만든 통신 규약

---


#### 4) GET 요청

> GET 요청의 특징

- URL 에 데이터를 포함 -> 데이터 조회에 적합
- 바이너리 및 대용량 데이터 전송불가
- 요청라인과 헤드 필드의 최대크기
	- HTTP 사양에는 제한사항 없음
	- 대용량 URL로 인한 문제 발생 -> 웹 서버에 따라 최대 크기 제한
	- Microsoft IIS 6.0_: 16KB
	- Apache 웹서버 : 8KB

> GET 요청의 문제점과 개선방안

- 보안에 좋지않다.
- 바이너리 데이터를 전송할 수 없다.

	HTTP 사양에서는 요청라인이나 헤더 필드의 최대 크기를 제한하지 않는다. 그러나 대부분의 웹 서버는 대용량 URL을 사용할 때 발생할 수 있는 보안 문제 때문에 요청라인이나 헤더필드의 최대 크기를 제한하고 있다.
    가령 IIS는 버전4.0은 2MB였고, 5.0에서는 128KB로 줄였고, 6.0부터는 16KB로 제한하고 있다.
    아파치 웹서버는 8KB
    
--- 

#### 5) POST 요청

> POST 요청의 특징

- URL에 데이터가 포함되지 않음 -> 외부 노출방지
- 메시지 본문에 데이터 포함 -> 실행 결과 공유 불가
- 바이너리 및 대용량 데이터 전송가능

> POST 요청의 단점 - 요청결과를 공유할 수 없다.

-

> POST 요청의 문제점과 개선방안
- GET 메서드와 마찬가지로 데이터를 전달할 때 "이름=값&이름=값" 형태를 사용한다.
- 문자데이터를 보낼 때는 문제 없지만, 이미지나 동영상과 같은 바이너리 데이터를 보낼 때는 문제가 발생할 수 있다.
- 바이너리 데이터 안에 `=`이나 `&`의 문자코드를 포함할 수 있기 때문이다.
- 이런 문제를 해결하기 위해 바이너리 데이터를 보낼 때는 아주 특별한 형식으로 작성하여 보내야하며, 서버에서도 이 형식에 맞추어 데이터를 분리한다.

