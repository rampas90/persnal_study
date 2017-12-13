# 전자정부프레임워크 Guide

## 목차
1. 전자정부 프레임워크 개요
2. 전자정부 프레임워크 기능 소개 
3. 전자정부 프레임워크 구성
4. 결론


## 1. 전자정부프레임워크 개요

### 1-1. 스펙 (3.X)
- egovFrame 버전 : 3.5,1
- JDK : 1.7/1.8(권장)
- Spring Framework : 3.2.9
- Spring Security : 3.2.4
- log4j : 2.0 (SLF4J 도입)
- eclipse : 4.4(Luna)
- 공통컴포넌트 : 250개(모바일포함)

### 1-2. 3.X 버전의 주요 개선기능

#### 코드표준화
로그 표준화 및 성능 개선
- XML 환경설정 단순화
- 파라미터 메시지 방식으로 log 메시지 출력
- 로깅방식 표준화 `SLF4J + Log4j 2`

#### 신규기능
기존 XML 상에 설정되는 정보를 DB 상으로 관리할 수 있는 DB기반 설정관리 기능`(Property'Service)`을 통해 개발/운영 시스템 간의 유연한 관리가 가능하게 됨

> 특장점

- Spring Framework(Container) 설정 정보 DB 분리 관리 가능
- 운영시스템(개발/운영 등)에 따른 설정 분리 가능
- 보안이 필요한 설정정보를 DB로 관리함으로써 보안 강화 (ex:암복호화 키 DB저장 등)

> 표준 프레임워크 3.1

- 공통컴포넌트 및 표준프레임워크 KISA'보안 점검 및 보완'

> 표준 프레임워크 3.2

- 공통컴포넌트 기능 확대 (229종 -> 250종)
- 이중등록(Double Submit)방지
- 약도관리, 도로명 주소 연계
- 기존 에디터를 `ckEditor` 로 교체


## 2. 전자정부프레임워크 기능 소개

- Spring Security 간소화
- MyBatis
- Spring Data JPA
- DB Property
- SLF4J
- Cache Abstraction


### 2-1. Spring Security 간소화
- XML 스키마(egov"security)를 통해 설정 최소화 기능 제공
- 기존 Security 기능과 호환 (설정 기능만 제공)

> XML 네임스페이스 및 스키마 설정

```xml
<beans
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:egov-security="http://www.egovframe.go.kr/schema/egov-security"
	xmlns:security="http://www.springframework.org/schema/security"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
	http://www.egovframe.go.kr/schema/egov-security
	http://www.egovframe.go.kr/schema/egov-security/egovsecurity-3.0.xsd
	http://www.springframework.org/schema/security
	http://www.springframework.org/schema/security/springsecurity-3.2.xsd">
</beans>
```

> egov-security 기본설정 예제코드

```xml
<egov-security:config id="securityConfig"
// 하기 1) egov-security 필수 설정 표 참조
	loginUrl="/cvpl/EgovCvplLogin.do"
	logoutSuccessUrl="/cvpl/EgovCvplLogin.do"
	loginFailureUrl="/cvpl/EgovCvplLogin.do?login_error=1"
	accessDeniedUrl="/system/accessDenied.do"

// 하기 2) egov-security 선택 설정 표 참조
	dataSource="dataSource"
	/**
	 * jdbcUsersByUsernameQuery
	 *   해당 쿼리는 3번째까지는 정해져있다.(컬럼명이 아닌 순서가 필수)
	 *   1. USER_ID
	 *   2. PASSWORD
	 *   3. ENABLED : 임시 Lock 등의 처리를 위해 지원
	 *                예)로그인 5회 실패시..
	 */
	jdbcUsersByUsernameQuery="SELECT
	USER_ID,PASSWORD,ENABLED,USER_NAME,BIRTH_DAY,SSN
	FROM USERS WHERE USER_ID = ?"
	jdbcAuthoritiesByUsernameQuery="SELECT USER_ID,AUTHORITY
	FROM AUTHORITIES WHERE USER_ID = ?"
	// Map 기반으로 jdbcUsersByUsernameQuery 조회결과 처리
	jdbcMapClass=
	"e.r.f.security.userdetails.EgovUserDetailsMapping"
	requestMatcherType="ant"
	// md5 방식은 보안상 확실하게 뚤렸기때문에 sha 방식으로 지원
	hash="sha256"
/>
```

> 1) egov-security 필수 설정

| 속성 | 설명 | 부가설명
|--------|--------|-------|
|  loginUrl  |  로그인 페이지 URL  | 특정 보안통제가 되는 Url 에 접근시 로그인 페이지로 전환하고 그때 사용되는 URl 뭔지 지정하는 속성
|  logoutSuccessUrl  |  로그아웃 처리 시 호출되는 페이지 URL  | - |
|  loginFailureUrl  |  로그인 실패 시 호출되는 페이지 URL  | 주로 로그인 페이지로 이동하지만 로그인 실패후 로그인페이지 이동등을 구별할 겨웅 사용
|  accessDeniedUrl  |  권한이 없는 경우 호출되는 페이지  |  로그인 성공했지만 권한이 없을경우 (접근할 수 없는 페이지에 접근했을때 리다이렉트 되는 페이지)|

> 2) egov-security 선택적 설정

| 속성 | 설명 |
|--------|--------|
| dataSource | DBMS 설정 dataSource |
| jdbcUsersByUsernameQuery | 인증에 사용되는 쿼리 |
| jdbcAuthoritiesByUsernameQuery | 인증된 사용자의 권한(authority) 조회 쿼리 |
| jdbcMapClass | 사용자 정보 mapping 처리 class |



> egov-security 초기화 설정 예제코드

```xml
<egov-security:initializer id="initializer" 
	// method 방식 지원 여부
	supportMethod="true"
	// 포인트컷 방식 지원 여부
	supportPointcut="false"
/>
```

> egov-security 추가 설정 예제코드

```xml
<egov-security:secured-object-config id="securedObjectConfig"

	/**
	 * roleHierarchyString
	 * 	계층처리를 위한 설정 문자열 지정
	 * 	미 지정시 DB로부터 지정된 설정정보 사용
	 */
	 
	roleHierarchyString="
		ROLE_ADMIN > ROLE_USER
		ROLE_USER > ROLE_RESTRICTED
		ROLE_RESTRICTED > IS_AUTHENTICATED_FULLY
		IS_AUTHENTICATED_FULLY > IS_AUTHENTICATED_REMEMBERED
		IS_AUTHENTICATED_REMEMBERED > IS_AUTHENTICATED_ANONYMOUSLY"
	sqlRolesAndUrl="
		SELECT auth.URL url, code.CODE_NM authority
		FROM RTETNAUTH auth, RTETCCODE code
		WHERE code.CODE_ID = auth.MNGR_SE"
/>```


### 2-2. MyBatis
- 표준프레임워크에서는 실행환경 2.6 부터 지원
- DAO 처리방식은 3가지를 지원한다.

> DAO 처리 방식

- 1) class 방식
	```java
	public class DeptMapper extends EgovAbstractmapper {
		public void insertDept(DeptVO vo){
			...생략
		}
	}
	```
- 2) interface 방식
	`package` 네임과 `method` 네임의 조합으로 쿼리를 맵핑
- 3) Annotation 방식
	`Annotation`에 직접 쿼리 입력 (다이나믹쿼리 지원 X)


### 2-3. Spring Data 
- 다양한 NoSql 지원을 위한 Spring Data 하위 프로젝트
- Spring Data의 Repository 지원을 통해 DataStorage에 대한 통일된 인터페이스 제공
- 표준프레임워크 2.6 부터 지원

> Spring Data 개요

- RDB -> `JPA`, `JDBC Extentions`
- Big Data -> `Hadoop`
- Data-grid -> `GemFire`
- HTTP -> `REST`
- Key Value Stores -> `Redis`
- Document Stores -> `MongoDB`
- Graph db -> `Neo4j`
- Column Stores -> `HBase`

스프링 기반의 통일되어있는 데이터 엑세스에 대한 프로그램 모델을 지원한다.


> 기존방식과의 차이점

기존에는 컨트롤러가 VO 등을 통해 `Data Access` 를 활용해서, 주로 `MyBatis`, 하이버네이트, JPA 등이 RDB에 접근하는 역할을 해왔다.

하지만 `다양한 Storage` 와 새로운 여러 DB 등이 나오게 되면서 (`GemFire`, `MongoDB`, `Neo4j` 등등...)
기존에 쓰던 데이터엑세스 기술을 활용하지 못해,
새로운 기술들이 필요할경우 전부 개별적으로 구현을 해야 했고 이에 따라 당연히 관리도 어려웠다.

하지만 `Spring Data Repository` 방식으로 구현을 해놓으면
다양한 DB 들과 여러가지 기술들을 통일된 방식으로 쓸 수 있다.

> `spring Data Repository`

마틴파울러가 정의한 내용으로 데이터를 맵핑하는데 콜렉션 기반의 인터페이스다
참고 URL : http://marNnfowler.com/eaaCatalog/repository.html


> 실질적인 활용방법
1. `Repositry` 정의
    데이터 스토리지 기술마다 `Repositry`를 정의하고
2. `Repositry` 안에 데이터오브젝트 정의

`Repositry` 에서 표준적으로 사용하는 인터페이스
- CrudRepository
	- 기본적인 CRUD, findOne, exists, findAll, cound 등...
	```java
	public interface BoardRepository extends 
		CrudRepository<Board, String> {
	}
	```
- PagingAndSortingRepository
	- CrudRepository를 상속해서 페이징 처리와 정렬을 위한 기능을 확장지원해준다.
	```java
	public interface UserRepository extends
		PagingAndSortingRepository<User, String> {
	}
	```
	- 필요시 페이징에 검색 조건도 추가해서 사용할 수도 있다.
	```java
	public interface ArticleRepository extends
        PagingAndSortingRepository<Article, Integer> {

	  		public List<Article> findByBoardKeyAndDeletedFalse(
				String boardKey);

	  		public Page<Article> findByBoardKeyAndDeletedFalse(
				String boardKey, Pageable pageable);
	}
	```

### 2-4. DB PropertySouce
- DB로부터 설정 정보를 가져와 Spring 설정 property로 사용
- 개발/운영 등 환경에 따른 설정 관리에 대한 유연성 제공
- 보안이 필요한 설정정보를 DB로 관리함 (ex: 암복호화 키 DB 저장등)
- 표준프레임워크 3.0부터 제공


> DB 테이블 설정 예제 SQL

```sql
CREATE TABLE PROPERTY (
  PKEY VARCHAR(20) NOT NULL  PRIMARY KEY ,
  PVALUE!VARCHAR(20) NOT NULL
);

INSERT INTO PROPERTY (PKEY, PVALUE) VALUES ('encryptKey', 'user');
INSERT INTO PROPERTY (PKEY, PVALUE) VALUES ('encryptPassword', 'password');
```

> XML 설정 예제코드

```xml
<jdbc:embeddedMdatabase id="dataSource" type="HSQL">
	<jdbc:script location="classpath:db/ddl.sql" />
	<jdbc:script location="classpath:db/dml.sql" />
</jdbc:embeddedMdatabase>

<bean id="dbPropertySource" class="egovframework.rte.fdl.property.db.DbPropertySource">
	<constructorMarg value="dbPropertySource"/>
	<constructorMarg ref="dataSource"/>
	<constructorMarg value="SELECT PKEY, PVALUE FROM  PROPERTY"/>
</bean>
```


> web.xml 설정 예제코드
> 주의할 점은 DB에서 설정정보를 가져와서 xml를 설정하기 때문에	`ApplicaNonContextIniNalizer`에 의해 Spring context 기동 전에 처리해야 한다.

```xml
<contextMparam> 
	<paramMname>contextInitializerClasses</paramMname> 
	<paramMvalue>egovframework.rte.fdl. 
       property.db.initializer.DBPropertySourceInitializer 
	</paramMvalue> 
</contextMparam> 
<contextMparam> 
	<paramMname>propertySourceConfigLocation</paramMname> 
	<paramMvalue> 
    classpath:/initial/propertysource.xml 
	</paramMvalue> 
</contextMparam>
```


### 2-5. SLF4J
- `SLF4J`는 특정 Logging 서비스 구현체에 비종속적으로 추상화 계층을 제공한다.
	(기본 구현체는 log4j )
- `JCL`, `Log4j`, `LogBack` 등의 다양한 로거와 함께 사용가능하다.
- 표준프레임워크 3.0부터 지원한다.

> SLF4J 사용 ( Log4J 기준) 예제코드

```java
// log4j 에서 import와 logger 선언 이름 정도만 바꿔주면 되며, 사용방법은 log4j 와 동일하다.
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger LOGGER =
	LoggerFactory.getLogger(ClassName.class);
```

### 2-6. Cache Abstraction
- SLF4J 처럼 추상화 계층을 제공한다. (기본 구현체는 ehCache )
- 메소드 단위 cache 지정방식
- 표준프레임워크 3.0 부터 제공

> 추상화 계층을 제공

많이 사용하는 `ehCache`를 기본 구현체로 했으며
필요시 변경하여 사용할 수 있다.

> 메소드 단위 cache 지정방식

말그대로 특정 리스트 메소드를 분리하여 `cache`설정을 할 수 있고, 
(예) 공지사항 리스트에는 적용, 댓글리스트에는 미적용 )
리스트에 Insert, update, delete 등으로 변화가 생겼을시에 `cache`를 무효하는 등의 기본적이 어노테이션등을 지원한다.



## 3. 전자정부 프레임워크 구성

### 3-1. 구성 요소의 기능과 역할

전자정부 프레임워크의 전체적인 구성은 아래 그림과 같다.

![](http://uiux.bstones.co.kr/docs_images/egovMD/table_07.jpg)

구성요소중 사실상 주가 되는부분은 개발환경, 실행환경, 공통 컴포넌트로

개발환경은
===

> eclipse 기반에 개발환경과 형상서버를 포함한 CI라고 하는 서버 개발환경을 제공한다.
> 이 부분은 선택적이라 필요에 따라 적용하면 되며,
> Maven 이 필요하다면 Maven만이라도 사용하면되는 구조다.

그외에 운영환경 같은 경우는 모니터링도구와 커뮤니케이션 도구를 제공한다.

실행환경은
===
> Spring 프레임워크 기반의 몇몇 오픈소스SW로  framework의 기능들을 제공한다.
> 그 중 표준화된 아키텍쳐와 몇 가지 준수사항만을 만족하면 표준프레임워크를 사용한 것으로 인정이 된다
> - (Annotation 기반의 Spring MVC 사용
> - layered 아키텍처 준수 -> @Controller, @Service, @Repository를 사용
> - Data Access 부분은 iBatis 또는 MyBatis, 또는 JPA 사용

그 외에 Cache 서비스, 암복호화 서비스 등의 30여종 서비스를 제공하지만 이는 선택사항이다. 

> 모니터링 도구

동작 정보와 수행로그를 수집하고 시스템 상태에 대한 모니터링 기능제공
- 에이전트 관리 : 스케쥴, 로깅등의 설정을 기반으로 모니터링 대상 시스템에서 실행
- 모니터링 정보 수집 : 에이전트가 실행되면서 시스템 정보 및 프로그램 로그 수집 기록
- 운영자 GUI : 운영자에게 수집된 정보를 그래프, 차트등으로 표현

>  커뮤니케이션 도구

프로젝트에서 발생하는 각종 관리항목에 대한 등록 및 관리기능을 제공한다.
- 일정관리
- 개선요청
- 설정관리
- 운영정보
- 산출물관리
- 결재관리
- 게시판자료실


#### 배치운영 도구
- 일괄(배치) 개발/실행환경에서 작성된 배치Job을 등록/실행하고 수행현황을 모니터링하며 처리결과를 확인하기 위한 표준화된 운영환경을 제공


![](http://uiux.bstones.co.kr/docs_images/egovMD/img_batchtool.jpg)


---


### 3-2. 공통컴포넌트 구성

![](http://uiux.bstones.co.kr/docs_images/egovMD/EgovCommonArchitecture.JPG)

> 공통 컴포넌트란?

말뜻 그대로 공통적으로 재사용이 가능한 기능위주로 개발한 컴포넌트의 집합을 뜻하며,
현 시점에서 공통컴포넌트는 모바일을 포함해 250개 정도된다.

이 공통컴포넌트들의 경우 사실상 컴포넌트 보다는 템플릿(재사용 용도)에 가깝고
대다수의 사람들은 공통 컴포넌트를 **쓸 수 없다**로 축약하고 있다.

그 예로는 준수사항중에 DAO 를 egov 에서 제공하는 특정 클래스를 반드시 상속받아야 한다거나 하는 규칙등이 있어서 사내 자체의 프로세스와 맞지 않다거나 확장에 문제가 생기는 등의 문제들을 주로 꼽고있다.

> 참고 이미지 

![](http://uiux.bstones.co.kr/docs_images/egovMD/capture1.jpg)


## 4. 결론

### 특장점
장점
- 이미 많은 기능들을 제공하고 있다 
- 수요가 많다.
- 환경셋팅이 비교적 쉬우며, 예제가 많아 진입장벽이 낮다 (스프링 대비)

단점
- 꽤 많은 리소스를 잡아 먹는다
- 개발의 자율성이 떨어진다.
- 버그가 많다

### 결론
이미 많은 기능들이 구현되어있고, 프로젝트 속성에 따라 컴포넌트들을 사용할지 말지 선택적으로 사용하면 프로젝트 구성에는 문제가 없을것 같으나
기존의 회사 라이브러리와 구현 방식등에 차이점등이 명확한 만큼,
`egovFramework`를 사용한다면 그에따른 선행학습이 **필수** 요소로 작용한다.

물론 `프레임워크`자체가 어느정도의 학습비용을 필요로 하지만,
`egovFramework`는 조금더 많은 학습비용을 필요로 할 것으로 예상된다.



참고 : [전자정부 프레임워크 가이드 wiki](http://www.egovframe.go.kr/wiki/doku.php)


