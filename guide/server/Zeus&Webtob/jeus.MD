# JEUS 

----------------

##### 1.개요

버전은 <span style="color : red">`6.0`</span> 윈도우 설치 기준으로 작성하였습니다.

---------------



##### 2.설치

[technet.tmaxsoft.com](http://technet.tmaxsoft.com)

티맥스 사이트에서 다운로드를 진행합니다.
설치받은 파일을 실행하여 인스톨을 진행합니다.
- <span style="color : red">설치경로</span>
- <span style="color : red">jdk 경로</span>
- <span style="color : red"> 관리자 비밀번호</span>

해당 정보를 입력하고 나면
설치가완료됩니다.

----------------

##### 3.관리자
```
계정 : administrator
비밀번호 : 설치시 입력했던 비밀번호
```

URL : [localhost:9744/webadmin](http://localhost:9744/webadmin) 웹에서 접근가능하며
명령 프롬프트 창에서 jeusadmin 을 입력하여도 접근이 가능합니다.

----------------

##### 4.실행

명령 프롬프트를 열고 jeus를 입력하면 서버가 실행됩니다.

서버를 실행하면 기본적으로 관리자페이지에 접근이 가능합니다.
아래 사항은 제우스 컨테이너를 실행시키는 방법입니다. 둘중 한가지 방법을 이용하여
컨테이너를 실행시킵니다.
- 웹 관리자에 접속하여서<span style="color : red"> 엔진 컨테이너</span> 항목으로 접근하여 내 컴퓨터이름으로 생성된 컨테이너를 실행합니다.
- 명령프롬프트 창으로 jeusadmin [내컴퓨터 이름]로 접근하여 <span style="color : red">boot</span>를 입력합니다.

컨테이너가 실행되면  [localhost:8088](localhost:8088)으로 접속하여  jeus를 사용하실수 있습니다.

------------------------------

##### 5.index.jsp
```css
<%=1+1%>
```
index.jsp파일을 아래 경로에 복사한후 jsp작동유무를 확인합니다.
###### C:\TmaxSoft\JEUS6.0\webhome\HKKIM-PC_container1\examples\welcome_war_\_\__


----------------

##### 6.이클립스 연동

참고 URL [http://godwings.blog.me/110183985551](http://godwings.blog.me/110183985551) 







