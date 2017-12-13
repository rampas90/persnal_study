## 1장 IoC 컨테이너와 DI

##### 2) 어노테이션 : @Autowired/@Inject

어노테이션을 이용한 의존관계 설정 방법의 두 번째는 @Autowired와 @Inject를 이용하는 것이다.
이 두 가지 어노테이션은 기본적으로 타입에 의한 자동와이어링 방식으로 동작한다. 그 의미나 사용법은 거의 동일하다.
@Autowired는 스프링 2.5부터 적용된 스프링 전용 어노테이션이고, @Inject는 JavaEE 6의 표준 스펙인 `JSR-330(줄려서 DIJ라고 부른다)`에 정의되어 있"는 것으로, 스프링 외에도 JavaEE 6의 스펙을 따르는 여타 프레임워크에서도 동일한 의미로 사용되는 DI를 위한 어노테이션이다.

> 즉 스프링으로 개발한 POJO를 앞으로 다른 환경에서도 사용할 가능성이 있다면 @Inject와 DIJ에서 정의한 어노테이션을 사용하는게 좋다.

@Autowired는 XML의 타입에 의한 자동와이어링 방식을 생성자, 필드, 수정자 메소드, 일반 메소드의 네 가지로 확장한 것이다.

**1) 수정자 메소드와 필드**

필드와 수정자 메소드는 @Resource와 사용 방법이 비슷하다.

@Autowired 어노테이션이 부여된 필드나 수정자를 만들어주면 스프링이 자동으로 DI 해주도록 만드는 것이다.
@Resource와 다른점은 이름 대신 필드나 프로퍼티 타입을 이용해 후보 빈을 찾는다는 것이다.
이 점에서는 XML의 타입 자동와이어링과 비슷하다

다음 소스를 보면 필드의 타입의 Printer이므로 현재 등록된 빈에서 Printer타입에 대입가능한 빈을 찾는다. 
대입 가능한 빈 후보가 하나 발견되면, printer 필드에 자동으로 DI될 것이다.

```java
public class Hello{
	@Autowired
   private Printer printer;
}
```

또한 수정자 메소드에 도 적용할 수 잇다.

```java
public class Hello{
   private Printer printer;
   
   @Autowired
   public void setPrinter(Printer printer){
   	this.printer=printer;
   }
}
```

**2) 생성자**

@Autowired는 @Resource와 다르게 생성자에도 부여할 수 있다.
이때는 생성자의 모든 파라미터에 타입에 의한 자동와이어링이 적용된다.
가력 두개의 프로퍼티를 갖고 있는 메소드가 있다고 가정했을때, 이를 수정자를 이용해 DI하려면 두개 의 수정자 메소드가 필요하고
어노테이션 설정을 사용하면 @Autowired도 두 개가 필요할테지만 생성자를 이용한다면 하나의 @Autowired로 가능하다

```java
public class BasSqlService implements SqlService{
	protected SqlReader sqlReader;
   protected SqlRegistry sqlRegistry;
   
   @Autowired
   public BaSqlService(SqlReader sqlReader, SqlRegistry sqlRegistry){
   	this.sqlReader = sqlReader;
      this.sqlRegistry = sqlRegistry;
   }
}
```

@Autowired는 단 하나의 생성자에만 사용할 수 있다는 제한이 있다.
여러 개의 생성자에 @Autowired가 붙으면 어느 생성자를 이용해서 DI 해야 할 지 스프링이 알 수 없기 때문이다.


**3) 일반 메소드**

@Autowired는 수정자, 생성자 외의 일반 메소드에도 적용할 수 있다. XML을 이용한 의존관계 설정에서는 가능하지 않은 어노테이션 방식의 고유한 기능이다 

생성자 주입과 수정자 주입은 각기 장 단점이 있는데
바로 그래서 등장한 DI 방법이다.

파라미터를 가진 메소드를 만들고 @Autowired를 붙여주면 각 파라미터의 타입을 기준으로 자동와이어링을해서 DI 해줄 수 있다.
생성자 주입과 달리 일반 메소드는 오브젝트 생성 후에 차례로 호출이 가능하므로 여러 개를 만들어도 된다.

한 번에 여러개의 오브젝트를 DI 할수 잇음믐로 코드도 상대적으로 깔끔해진다.

수정자 메소드 주입과 생성자 주입의 장점을 모두 갖춘 방식이다.

++단 이렇게 만들어진 클래스는 XML을 통해서는 의존관계를 설정할 방법이 없다는 점에 주의해야 한다.++

**4)@Qualifier**

Qualifier 는 타입 외의 정보를 추가해서 자동와이어링을 세밀하게 제어할 수 있는 보조적인 방법이다.
타입에 의한 자동와이어링은 안전하고 편리하지만 타입만으로 원하는 빈을 지정하기 어려운 경우가 종종 발생한다.

만약 DataSource 타입의 빈이 하나 이상 등록됐다고 생각해보자.

```xml
<bean id="oracleDataSource" class="...XxxDataSouce">...</bean>
<bean id="mysqlDataSource" class="...YyyDataSouce">...</bean>
```

각기 다른 DB를 사용하지만 모두 Datasource라는 같은 타입의 빈을 사용하고 있다.

여기서는 `@Resource("oracleDatasource")` 같은 형식으로 사용하는데는 문제가 없다 
하지만 @Autowired 를 사용해서 타입에 의한 자동와이어링을 시도하면 에러가 발생한다.

> 그렇다면 이럴때는 어떻게 해야 할까?

바로 이럴때 `@Qualifier`을 이용해서 빈 선정을 도와주는 부가정보를 이용하는 게 좋다.
먼저 자바 소스에 아래와같이 한정자~qualifier~ 값을 설정해주고 ~

```java
@Autowired
@Qualifier("mainDB")
DataSource dataSource;
```
XML의 빈태그에 위에서 등록한 한정자 정보를 등록해주면 된다.

```xml
<bean id="oracleDataSource" class="...XxxDataSource" >
	<qualifier value="mainDB" />
</bean>
```


##### 3) @Autowired 와 getBean(), 스프링 테스트


