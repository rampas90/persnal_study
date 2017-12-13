## 오브젝트와 의존관계

> 스프링의 핵심철학

객체지향 프로그래밍이 제공하는 폭넓은 혜택을 누릴 수 있도록 기본으로 돌아가자는 것

스프링이 가장 관심을 많이 두는 대상은 오브젝트다. 
그래서 스프링을 이해하려면 먼저 오브젝트에 깊은 관심을 가져야 한다.

애플리케이션에서 오브젝트가 생성되고 다른 오브젝트와 관계를 맺고, 사용되고, 소멸하기까지의 전 과정을 진지하게 생각해볼 필요가 있다.

더 나아가서 오브젝트는 어떻게 설계돼댜 하는지, 어떤 단위로 만들어지며 어떤 과정을 통해 자신의 존재를 드러내고 등장해야 하는지에 대해서도 살펴봐야 한다.

결국 오브젝트에 대한 관심은 오브젝트의 기술적인 특징과 사용 방법을 넘어서 오브젝트의 설계로 발전하게 된다.

객체지향 설계(Object oriented design)의 기초와 원칙을 비롯해서, 다양한 목적을 위해 재활용 가능한 설계 방법인 디자인 패턴, 좀 더 깔끔한 구조가 되도록 지속적으로 개선해나가는 작업인 리팩토링, 오브젝트가 기대한 대로 동작하고 있는지를 효과적으로 검증하는 데 쓰이는 단위 테스트와 같은 오브젝트 설계와 구현에 관한 여러가지 응용기술과 지식이 요구된다.

### 초난감 DAO


> 사용자 정보를 JDBC API를 통해 DB에 저장하고 조회할 수 있는 간단한 DAO를 통해 차근차근 알아보자

DAO(Data Access Object)는 DB를 사용해 데이터를 조회하거나 조작하는 기능을 전담하도록 만든 오브젝트를 말한다.



> User 클래스

```java
package springbook.dev.com;

public class User {
	String id;
	String name;
	String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
```



> UserDao

```java
package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao {
	public void add(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection(
      "jdbc:mysql://localhost/springbook?characterEncoding=UTF-8", "spring",
				"book");

		PreparedStatement ps = c.prepareStatement(
			"insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection(
      "jdbc:mysql://localhost/springbook?characterEncoding=UTF-8", "spring",
				"book");
		PreparedStatement ps = c
				.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}
}
```

가장 기본적인 위 코드들이 만들어졌다.

그런데 만약 이 클래스가 제대로 동작하는지 어떻게 확인할 수 있을까?
서버에 배치하고, 웹브라우져를 통해 테스트해보는 방법이 있지만, 이런 간단한 UserDao 코드를 확인하기 위한 작업치고는
부담이 너무 크다.

> 이럴때 가장 간단한 방법은 main()메소드를 통해 확인해보는 방법이 있다.

```java
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		UserDao dao = new UserDao();
		
		User user = new User();
		user.setId("whiteship");
		user.setName("신명철");
		user.setPassword("1234");
		
		dao.add(user);
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user.getPassword());
		
		System.out.println(user2.getId()+ " 조회성공");
		
	}
```

> 출력결과

```java
신명철
1234
whiteship 조회성공
```

> 위의 DAO는 테스트결과 문제없이 잘 실행된다..
> 하지만 위 코드는 사실 문제점이 많은 코드다..어떤점이 문제가 될지 생각해보자


### DAO의 분리

> 과연 어떻게 변경이 일어날 때 필요한 작업을 최소화하고, 그 변경이 다른곳에 문제를 일으키지 않게 할수 있을까?

그것은 **분리**와 **확장**을 고려한 설계를 필요로 한다.

보통 모든 변경과 발전은 한 번에 한가지 관심사항에 집중해서 일어난다.
여기서 먼저 분리에 대해 생각해보자
문제는, ++**변화는 대체로 집중된 한가지**++관심에 대해 일어나지만 그에따른 작업은 한곳에 집중되지 않는 경우가 많다는 점이다.

- 단지 DB접속용 암호를 변경하려고 DAO클래스 수백개를 모두 수정해야 한다면?

- 혹은 트랙잭션 기술을 다른 것으로 바꿨따고 비즈니스 로직이 담긴 코드의 구조를 모두 변경해야 한다면?

- 또는 다른 개발자가 개발한 코드에 변경이 이러날 때마다 내가 만든 클래스도 함께 수정을 해줘야 한다면?

생각만해도 끔직한 일이다.

만약 변화가 한 번에 한가지 관심에(예를들어 특정하여 DB접속정보를 변경해야하는 경우) 집중돼서 일어난다면,
우리가 준비해야 할 일은 ++**한가지 관심이 한 군데에 집중되게 하는 것**++이다.
**즉 관심이 같은 것끼리는 모으고, 관심이 다른 것을 따로 떨어져 있게 하는것이다.**

프로그래밍의 기초 개념 중에 관심사의 분리(Separation of Concerns)라는 게 있다.
이를 객체지향에 적용해보면, 관심이 같은 것끼리는 하나의 객체 안으로 또는 친한 객체로 모이게 하고, 관심이 다른 것은 가능한 한 따로 떨어져서 서로 영향을 주지 않도록 분리하는 것이라고 생각할 수 있다.

#### 커넥션 만들기의 추출

위의 서술했던 관심사를 클래스를 통해 살펴보자

UserDao의 add()메소드 하나에서만 적어도 세 가지의 관심사항을 발견할 수 있다.

1. DB연결을 위한 커넥션을 어떻게 가져올까?

2. 사용자 정보를 Statement에 담긴 SQL을 DB를 통해 실행시키는 방법

3. 작업이 끝나면 사용한 리소스인 Statement와 Connection오브젝트를 닫아줘서 공유 리소스를 시스템에 돌려주기!

여기서 가장 문제가 되는 것은 첫번째 관심사인 DB연결을 위한 Connection 오브젝트를 가져오는 부분이다.
DB커넥션을 가져오는 코드가 다른 관심사와 섞여서 add(), get()두개의 메소드의 동일하게 중복되어 있다.
여기서는 두개의 메소드지만 만약 수백,수천 개의 DAO 메소드를 만들었다면 정말 답도 없는 상황이 나온다.

**바로 이렇게 하나의 관심사가 방만하게 중복되어 있고, 여기저기 흩어져 있어서 다른 관심의 대상과 얽혀 있으면,
변경이 일어날 때 엄청난 고통을 일으키는 원인이 된다.**

##### 중복 코드의 메소드 추출

가장 먼저 할 일은 커넥션을 가져오는 이 중복된 코드를 분리하는 것이다.

> DB 커넥션 정보를 가져오는 별도의 메소드 getConnection() 메소드를 만든다

```java
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		...
	}


	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		...
	}
	private Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection(
      "jdbc:mysql://localhost/springbook?characterEncoding=UTF-8", "spring",
				"book");
		return c;
	}
```

이렇게 중복되는 코드를 분리하여 별도의 메소드로 만드니 DB커넥션 정보가 바뀌더라도 이 메소드만 수정하면 된다.
관심의 종류에 따라 코드를 구분해놓았기 때문에 한 가지 관심에 대한 변경이 일어날 경우 그 관심이 집중되는 부분의 코드만 수정하면 된다.


##### 변경사항에 대한 검증:리팩토링과 테스트

이렇게 코드를 수정했지만 UserDao의 기능에는 아무런 변화가 없다.
이 작업은 기능에는 영향을 주지 않으면서 코드의 구조만 변경해서, 훨씬 깔금해졌고 미래의 변화에 좀 더 손쉽게 대응할 수 있는 코드가 됬다.

> 바로 이런 작업을 `리팩토링(refactoring)`이라고 한다.
> 또한, 위에서 사용한 `getConnection()`이라고 하는 공통의 기능을 담당하는 메소드로 중복된 코드를 뽑아내는 것을 리팩토링에서는 `메소드 추출(extract method)` 기법이라고 부른다.

그런데 여기서 만약 UserDao의 소스코드를 공개하지 않고 DB커넥션 생성방식을 각각 다른 방법으로 사용하려면 어떻게 해야될까?

N사와 D사의 비유를 생각해보자



##### 상속을 통한 확장

고객에게 전체 소스코드를 노출하지 않으면서도 고객별로 독립된 DB커넥션 생성방식을 제공하려면 기존의 UserDao 코드를 한 단계 더 분리해야 한다.

바로 getConnect()을 추상 메소드로 만드는 것이다.
추상 메소드라서 메소드 코드는 없지만 메소드 자체는 존재한다.
따라서 add(), get()메소드에서 getConnection()을 호출하는 코드는 그대로 유지할 수 있다.

> 기존에는 같은 클래스에 다른 메소드로 분리됐던 DB커넥션 연결이라는 관심을 이번에는 상속을 통해 서브클래스로 분리해버리는 것이다.

아래는 위와 같은 방식으로 리팩토링한 코드다

```java
public abstract class UserDao {
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		...
	}


	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		...
	}

	abstract protected Connection getConnection() throws ClassNotFoundException, SQLException ;

	public class NUserDao extends UserDao {
		protected Connection getConnection() throws ClassNotFoundException,
			SQLException {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost/springbook?characterEncoding=UTF-8",
				"spring", "book");
		return c;
	}
   
   public class DUserDao extends UserDao {
		protected Connection getConnection() throws ClassNotFoundException,
			SQLException {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost/springbook?characterEncoding=UTF-8",
				"spring", "book");
		return c;
	}
}
```

수정한 코들르 살펴보면, 클래스 계층구조를 통해 두 개의 관심이 독립적으로 분리되면서 변경작업은 한층 용이해졌다.
이제는 UserDao의 코드는 한 줄도 수정할 필요 없이 DB연결 기능을 새롭게 정의한 클래스를 만들 수 있다.

이제 UserDao는 단순히 변경이 용이하다라는 수준을 넘어서 손쉽게 확장된다라고 말할 수도 있게 됐다.

이렇게 **슈퍼클래스에 기본적인 로직의 흐름(커넥션 가져오기, SQL생성, 실행, 반환)을 만들고, 그 기능의 일부를 추상메소드나
오버라이딩이 가능한 protected 메소드 등으로 만든 뒤 서브클래스에서 이런 메소드를 필요에 맞게 구현해서 사용하도록 하는 방법**을 디자인 패턴에서 `템플릿 메소드 패턴(template method pattern)`이라고 한다.
이 `템플릿 메소드 패턴`은 스프링에서 애용되는 디자인 패턴이다.

- UserDao의 getConnection()메소드 = Connection 타입 오브젝트를 생성한다는 기능을 정의해놓은 추상메소드
- UserDao의 서브클래스의 getConnection()메소드 = 어떤 Connection 클래스의 오브젝트를 어떻게 생성할 것인지를 결정하는 방법

위처럼 서브클래스에서 구체적인 오브젝트 생성방법을 결정하게 하는 것을 `팩토리 메소드 패턴(factory method pattern)`이라고 부르기도 한다.

이와 같이 변경함으로 인해 관심사항이 다른 코드를 분리해내고, 서로 독립적으로 변경 또는 확장할 수 있도록 만드는 것은 간단하면서도 매우 효과적인 방법이다.

하지만 이 방법은 상속을 사용했다는 단점이 있다.
상속 자체는 간단해 보이고 사용하기도 편리하게 느껴지지만 사실 많은 한계점이 있다.
만약 이미 UserDao가 다른 목적을 위해 상속을 사용하고 있다면 어쩔까?

**자바는 클래스의 다중상속을 허용하지 않는다**

단지, 커넥션 객체를 가져오는 방법을 분리하기 위해 상속구조로 만들어버리면, 후에 다른 목적으로 UserDao에 상속을 적용하기 힘들다.

또 다른 문제는 **상속을 통한 상하위 클래스의 관계는 생각보다 밀접하다**는 점이다.

상속관계는 두 가지 다른 관심사에 대해 긴밀한 결합을 허용한다.
서브클래스는 슈퍼클래스의 기능을 직접 사용할 수 있다.
그래서 슈퍼클래스 내부의 변경이 있을 때 모든 서브클래스를 함께 수정하거나 다시 개발해야 할 수도 있다.
반대로 그런 변화에 따른 불편을 주지 않기 위해 슈퍼클래스가 더 이상 변화하지 않도록 제약을 가해야 할지도 모른다.
또한 확장된 기능인 DB 커넥션을 생성하는 코드를 다른 DAO클래스에 적용할 수 없다는 것도 큰 단점이다.

### DAO의 확장

상속을 통한 구현의 여러가지 문제점을 살펴봤으니 이번에는 또다른 관점에서 접근해보자

##### 클래스의 분리

두 개의 관심사를 본격적을 독립시키면서 동시에 손쉽게 확장할 수 있는 방법

지금까지는 성격이 다른, 그래서 다르게 변할 수 있는 관심사를 분리하는 작업을 점진적으로 진행해왔다.
처음에는 독립된 메소드를만들어 분리했고, 다음에는 상하위클래스로 분리했다.
이번에는 아예 상속관계도 아닌 완전히 독립적인 클래스로 만들어 보자

즉, 서브클래스가 아닌 아예 별도의 클래스를 만들고, 이렇게 만든 클래스를 UserDao가 이용하게 하는 것이다.
`SimpleConnectionMaker`라는 새로운 클래스를 만들고 DB생성 기능을 그 안에 넣는다. 그리고 UserDao는 new 키워드를 사용해
`SimpleConnectionMaker` 클래스의 오브젝트를 만들어두고, 이를 add(), get()메소드에서 사용하면 된다.

> 독립된 SimpleConnectionMaker를 사용하게 만든 UserDao

```java
public abstract class UserDao {
	private SimpleConnectionMaker simpleConnectionMaker;

	public UserDao() {
		this.simpleConnectionMaker = new SimpleConnectionMaker();
	}

	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = this.simpleConnectionMaker.getConnection();
		...
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = this.simpleConnectionMaker.getConnection();
		...
	}
}
```


> 독립시킨 DB연결 기능 메소드를 담은 SimpleConnectionMaker

```java
package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost/springbook?characterEncoding=UTF-8", "spring", "book");
		return c;
	}
}
```

이처럼 성격이 다른 코드를 화끈하게 분리하기는 잘한 것 같은데, 이번엔 다른 문제가 발생했다.
N사와 D사에 UserDao클래스만 공급하고 상속을 통해 DB커넥션 기능을 확장해서 사용하게 했던 게 다시 불가능해졌다.
즉 UserDao 소스코드의 다음 줄을 직접 수정해야 한다.
```java
simpleConnectionMaker = new SimpleConnectionMaker();
```
UserDao의 소스코드를 함께 제공하지 않고는 DB연결방법을 바꿀 수 없다는 처음 문제로 다시 되돌아와 버렸다.

이렇게 클래스를 분리한 경우에도 상속을 이용했을 때와 마찬가지로 자유로운 확장이 가능하게 하려면 두 가지 문제를 해결해야 한다.

1. 분리한 simpleConnectionMaker 클래스의 메소드
	예) Connection c = simpleConnectionMaker.openConnection();
2. DB커넥션을 제공하는 클래스가 어떤 것인지를 UserDao가 구체적으로 알고 있어야 한다는 점

이러한 문제들의 근본적인 원인은 UserDao가 바뀔 수 잇는정보, 즉 DB커넥션을 가져오는 클래스에 대해 너무 많이 알고 있기 때문이다.
어떤 클래스가 쓰일지, 그 클래스에서 커넥션을 가져오는 메소드는 이름이 뭔지까지 일일히 알고 있어야 한다.
따라서 **UserDao는 DB커넥션을 가져오는 구체적인 방법에 종속**되어 버린다.

결과적으로 보면 상속을 이용한 방법만도 못한 상황이 되버렸다.

#### 인터페이스의 도입

그렇다면 클래스를 분리하면서도 이런 문제를 해결할 수는 없을까?
가장 좋은 해결책은 두 개의 클래스가 서로 긴밀하게 연결되어 있지 않도록 중간에 추상적인 느슨한 연결고리를 만들어 주는 것이다.

> 추상화란 어떤 것들의 공통적인 성격을 뽑아내어 이를 따로 분리해내는 작업을 말한다.

자바가 추상화를 위해 제공하는 가장 유용한 도구는 바로 인터페이스다.
결국 오브젝트를 만들려면 구체적인 클래스 하나를 선택해야겠지만, 인터페이스로 추상화해놓은 최소한의 통로를 통해 접근하는 쪽에서는 오브젝트를 만들때 사용할 클래스가 무엇인지 몰라도 된다.

즉 이제 UserDao는 자신이 사용할 클래스가 어떤 것인지 몰라도 된다.
UserDao가 인터페이스를 사용하게 한다면 인터페이스의 메소드를 통해 알 수 있는 기능에만 관심을 가지면 되지, 그 기능을 어떻게 구현했는지에는 관심을 둘 필요가 없다.

DB커넥션을 가져오는 메소드 이름을 makeConnection()이라고 정하고, 
이 인터페이스를 사용하는 UserDao 입장에서는 ConnectionMaker 인터페이스 타입의 오브젝트라면 어떤 클래스로 만들어졌든지 상관없이 makeConnection()메소드를 호출하기만 하면 Connection 타입의 오브젝트를 만들어서 돌려줄 것이라고 기대할 수 있다.

> ConnectionMaker 인터페이스

```java
public interface ConnectionMaker {
	public abstract Connection makeConnection() throws ClassNotFoundException,
			SQLException;
}
```

즉 고객에게 납품을 할 때는 UserDao클래스와 함께 ConnectionMaker 인터페이스도 전달한다.
그리고 D사의 개발자라면 아래와 같이 ConnectionMaker 인터페이스를 구현한 클래스를 만들고, 메소드를 작성해주면 된다
> ConnectionMaker 구현 클래스

```java
public class DConnectionMaker implements ConnectionMaker {
	public Connection makeConnection() throws ClassNotFoundException,
			SQLException {
		...
	}
}
```

그러면 이 코드를 적용한 UserDao의 코드를 살펴보자

> ConnectionMaker 인터페이스를 사용하도록 개선한 UserDao

```java
public class UserDao {
	// 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알 필요가 없다.
	private ConnectionMaker connectionMaker;
	
   // 생성자에 클래스 이름이 나온다...(관계에 유의...)
	public UserDao() {
		connectionMaker = new DConnectionMaker();
	}

	// 인터페이스에 정의된 메소드를 사용하므로 클래스가 바뀐다고 해도 메소드 이름이 변경될 걱정은 없다.
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		...
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = this.connectionMaker.makeConnection();
		...
	}
}
```

UserDao의 add(), get()메소드와 필드에는 ConnectionMaker라는 인터페이스와 인터페이스의 메소드인 makeConnection만 사용하도록 했다.
그러니 이제는 아무리 N사와D사가 DB접속용 클래스를 다시 만든다고 해도 UserDao의 코드를 뜯어 고칠 일은 없을 것 같다.
그러나 자세히 살펴보면 DConnection 클래스의 생성자를 호출해서 오브젝트를 생성하는 코드가 아래와 같이 여전히 UserDao에 남아있다.

```java
connectionMaker = new DConnectionMaker();
```

UserDao의 다른 모든 곳에서는 인터페이스를 이용하게 만들어서 DB커넥션을 제공하는 클래스에 대한 구체적인 정보는 모두 제거가 가능했지만,
초기에 한 번 어떤 클래스의 오브젝트를 사용할지를 결정하는 생성자의 코드는 제거되지 않고 남아 있다.

> 초기에 한 번 사용하는 이 코드조차 제거를 하려면 어떻게 해야 할까?

결국, 또 다시 원점이다. 여전히 UserDao소스코드를 함께 제공해서, 필요할 때마다 UserDao의 생성자 메소드를 직접 수정하지 않고서는 고객에게 자유로운 DB커넥션 확장 기능을 가진 UserDao를 제공할 수 가 없다.



#### 관계설정 책임의 분리

말이 어렵지만 문장의 뜻보다는 흐름을 통해 이해하자

UserDao와 ConnectionMaker 라는 두 개의 관심을 인터페이스를 써가면서까지 거의 완벽하게 분리했는데도, 
여전히 UserDao에는 어떤 ConnectionMaker 구현 클래스를 사용할지를 결정하는 코드가 남아있다.

이 때문에 여전히 UserDao변경 없이는 DB커넥션 기능의 확장이 자유롭지 못한데, 그 이유는 UserDao안에 분리되지 않은, 
또 다른 관심사항이 존재하고 있기 때문이다.

UserDao의 관심사항
- JDBC API와 User오브젝트를 이용해 DB에 정보를 어떻게 넣고 뺄 것인가?
- ConnectionMaker 인터페이스로 대표되는 DB커넥션을 어떻게 가져올 것인가?

UserDao 에 있는 new DConnectionMaker()라는 코드는 위의 두가지 관심사항과는 또다른
어떤 ConnectionMaker 구현 클래스의 오브젝트를 사용할지를 결정하는 관심사를 가지고 있다.

간단히 말하자면 UserDao와 UserDao가 사용할 ConnectionMaker의 특정 구현 클래스 사이의 관계를 설정해주는 것에 관한 관심이다.

> 바로 이 관심사를 담은 코드를 UserDao에서 분리하지 않으면 UserDao는 결코 독립적으로 확장 가능한 클래스가 될 수 없다.

여기서 클라이언트의 개념이 필요한데 여기서 말하는 `클라이언트`는 

두개의 오브젝트가 있고 A오브젝트가 B오브젝트의 기능을 사용한다면,
사용되는 쪽인 A오브젝트가 사용하는 쪽인 B오브젝트에게 서비스를 제공하는 셈이다.
이때 사용되는 오브젝트 `A를 서비스`, `B를 클라이언트`라고 부를 수 있다.
UserDao의 클라이언트라고 하면 UserDao를 사용하는 오브젝트를 가리킨다.

갑자기 뜬금없이 클라이언트에 대한 정의까지 해가며 설명을 하는 이유는 바로 이곳이
UserDao와 ConnectionMaker 구현 클래스의 관계를 결정해주는 기능을 분리해서 두기에 적절한 곳이기 때문이다.

> 즉 우리는  UserDao의 모든 코드를 ConnectionMaker 인터페이스 외에는 어떤 클래스와도 관계를 가져서는 안되면서도,
> UserDao 오브젝트가 동작하려면 특정 클래스의 오브젝트와 관계를 맺도록 만들어야 한다.

주의할점은 **클래스 사이의 관계가 아닌, 단지 오브젝트 사이에 다이나믹한 관계**를 만들어야 한다는 것이다.
이 차이를 잘 구분해야 하는데.............
클래스 사이의 관게는 앞서 살펴봤던 
```java
connectionMaker = new DConnectionMaker();
```
위 코드처럼 코드에 다른 클래스 이름이 나타내기 때문에 만들어지는 것이다.
하지만 ++**오브젝트 사이의 관계**++는 그렇지 않다.

코드에서는 특정 클래스를 전혀 알지 못하더라도 해당 클래스가 구현한 인터페이스를 사용했다면, 그 클래스의 오브젝트를 인터페이스 타입으로 받아서 사용할 수 있다.

이거야 말로 객체지향 프로그램의 **다형성** 이라는 특징 덕분에 가능한 일이다.

다시 본론으로 돌아와서
UserDao 오브젝트가 DConnectionManager 오브젝트를 사용하게 하려면 두 클래스의 오브젝트 사이에 
- 런타임 사용관계 or
- 링크 or
- 의존관계

라 불리는 관계를 맺어주면 된다.

> 그렇다면 **오브젝트간의 관계**를 설명하기전에 먼저 언급했던 UserDao의 클라이언트는 무슨 역할을 하는 걸까?

바로 위와 같은 런타임 오브젝트 관계를 갖는 구조로 만들어주는게 클라이언트의 책임이다.
기존의 UserDao에서는 생성자에게 이 책임이 있었다면, 이 역할을 클라이언트가 넘겨 받는 셈이다.

> UserDao의 생성자가 맡고 있떤 역할을 클라이언트에게 넘김에 따라 생성자를 새로 수정하자

```java
public UserDao(ConnectionMaker connectionMaker){
	this.connectionMaker = connectionMaker;
}
```

보다시피 클라이언트가 미리 만들어 둔 ConnectionMaker의 오브젝트를 전달 받을 수 있도록 매개변수를 추가했고, 그에 따라 
구체적인 구현체의 이름인 `DConnectionMaker()`가 드디어 사라졌다.

> 어떻게 이것이 가능한 걸까? 
> UserDao의 클라이언트 UserDaoTest 클래스(새로만들었다)를 통해 살펴보자

```java
public class UserDaoTest{
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
   	// UserDao가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 만든다.
   	ConnectionMaker connectionMaker = new DConnectionMaker();

		// 1. UserDao 생성
		// 2. 사용할 ConnectionMaker 타입의 오브젝트 제공.
		//    결국 두 오브젝트 사이의 의존관계 설정 효과
		UserDao dao = new UserDao(connectionMaker);
		...
   }
}
```

UserDaoTest는 UserDao와 ConnectionMaker 구현 클래스와의 **런타임 오브젝트 의존관계**를 설정하는 책임을 담당해야 한다.
그래서 특정 ConnectionMaker 구현 클래스의 오브젝트를 만들고, UserDao 생성자 파라미터에 넣어 두 개의 오브젝트를 연결해 준다.

> 이렇게 `인터페이스`를 도입하고 `클라이언트`의 도움을 얻는 방법은 상속을 사용해 비슷한 시도를 했을 경우에 비해서 훨씬 유연하다.



#### 원칙과 패턴


##### 개방 폐쇄 원칙
개방 폐쇄 원칙(OCP, Open-Closed Principle)을 이요하면 지금까지 해온 리팩토리 작업의 특징과 최종적으로 개선된
설계와 코드의 장점이 무엇인지 효과적으로 설명할 수 있다.

- 깔끔한 설계를 위해 적용 가능한 객체지향 설계 원칙중의 하나
- '클래스나 모듈은 확장에는 열려 있어야 하고 변경에는 닫혀 있어야 한다.'

> 참고로 객체지향 설계 원칙(SOLID) 에 대해 참고해보자 [참고사이트 바로가기](http://butunclebob.com/ArticleS.UncleBob.PrinciplesOfOod)
> - SRP(The Single Responsibility Principle) : 단일 책임 원칙
> - OCP(The Open Closed Principle) : 개방 폐쇄 원칙
> - LSP(The Liskov Substitution Principle) : 리스코프 치환 원칙
> - ISP(The Interface Segregation Principle) : 인터페이스 분리 원칙
> - DIP(The Dependency Inversion Principle) : 의존관계 역전 원칙

#### 높은 응집도와 낮은 결합도

개방 폐쇄 원칙은 **높은 응집도와 낮은 결합도**라는 소프트웨어 개발의 고전적인 원리로도 설명이 가능하다

높은 응집도
- 하나의 모듈, 클래스가 하나의 책임 또는 관심사에만 집중되어 있다는 뜻
- 불필요하거나 직접 관련이 없는 외부의 관심과 책임이 얽혀 있지 않으며, 하나의 공통 관심사는 한 클래스에 모여있다.

낮은 결합도 
- 책이과 관심사가 다른 오브젝트 또는 모듈과는 느슨하게 연결된 형태를 유지하는 것이 바람직
- 관계를 유지하는데 꼭 필요한 최소한의 방법만 간접적인 형태고 제공하고, 나머지는 서로 독립적이고 알 필요도 없게 만드는것이다.
- 결합도가 낮아지면 변화에 대응하는 속도가 높아지고, 구성이 깔끔해진다. 또한 확장하기에도 매우 편리하다.


#### 전략패턴

최종적으로 개선한 UserDaoTest - UserDao - ConnectionMaker 구조를 디자인 패턴의 시각으로 보면
`전략 패턴(Strategy Pattern)`에 해당한다고 볼 수 있다.

- 디자인 패턴의 꽃이라고 불릴만큼 다양하고 자주 사용되는 패턴
- 개방 폐쇄 원칙의 실현에도 가장 잘 들어 맞는 패턴

> 자신의 기능 맥락(context)에서, 필요에 따라 변경이 필요한 알고리즘을 인터페이스를 통해 통째로 외부로 분리시키고, 이를 구현한 구체적인 알고리즘 클래스를 필요에 따라 바꿔서 사용할 수 있게 하는 디자인 패턴이다.

여기서 UserDao는 전략패턴의 `컨텍스트에` 해당되고,DB 연결방식인 ConnectionMaker 인터페이스를 구현한 클래스는 `전략`으로 볼 수 있다.


### 제어의 역전 (IoC)

IoC라는 약자로 많이 사용되는 제어의 역전(Inversion of Control)이라는 용어가 있다.
스프링을 통해 알려졌지만 이미 그전부터 있어왔던 개념으로 UserDao코드를 좀더 개선해가며 알아보자

#### 오브젝트 팩토리

지금까지 개선해온 코드는 사실 얼렁뚱땅 넘긴 게 하나 있다 바로 클라이언트인 UserDaoTest다.

지금껏 가장 중점적으로 해왔던 작업이 성격이 다른 책이이나 관심사를 분리하는 작업이였는데,
UserDaoTest는 현재 UserDao의 기능이 잘 동작하는지 테스트하려고 만들었음에도 오브젝트 연결의 역할까지 맡고있는 상황이다.
그러니 이것도 분리하자.

즉 분리될 기능은 UserDao와 ConnectionMaker 구현 클래스의 오브젝트를 만드는 것과, 그렇게 만들어지 두 개의 오브젝트가 연결돼서 사용될 수 있도록 관계를 맺어주는 것이다.

##### 팩토리

분리시킬 기능을 담당할 클래스를 하나 만들어보자.
이 클래스의 역할은 객체의 생성 방법을 결정하고 그렇게 만들어진 오브젝트를 돌려주는 것인데, 이런 일을 하는 오브젝트를 흔히
`팩토리(Factory)`라고 부른다.

팩토리 역할을 맡을 클래스를 지정하고, UserDaoTest에 담겨 있던 UserDao, ConnectionMaker 관련 생성 작업을 DaoFactory로 올기고, UserDaoTest에서는 DaoFactory에 요청해서 미리 만들어진 UserDao 오브젝트를 가져와 사용하게 만든다.

> 팩토리 역할을 맡을 클래스 DaoFactory클래스

```java
package springbook.user.dao;

public class DaoFactory {
	public UserDao userDao() {
   	// 팩토리의 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고, 어떻게 준비시킬지를 결정한다.
   	ConnectionMaker connectionMaker = new DConnectionMaker();
		UserDao userDao = new UserDao(connectionMaker);
		return userDao;
	}
}
```

> 또한 DB커넥션 정보를 DaoFactory 클래스로 넘기면서 변화된 UserDaoTest 클래스도 살펴보자

```java
public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new UserDaoFactory().userDao();
      ...
	}
}
```

##### 설계도로서의 팩토리

이렇게 분리된 오브젝트들의 역할과 관계를 분석해보자.

- UserDao, ConnectionMaker : 애플리케이션의 핵심적인 **데이터 로직과 기술 로직**을 담당
- DaoFactory : 애플리케이션의 오브젝트들을 구성하고 그 **관계를 정의하는 책임**

전자가 실질적인 로직을 담당하는 컴포넌트라면, 후자는 애플리케이션을 구성하는 컴포넌트의 구조와 관계를 정의한 설계도 같은 역할을 한다고 볼 수 있다.

> 여기서 말하는 '설계도'의 개념은 간단히 어떤 오브젝트가 어떤 오브젝트를 사용하는지를 정의해놓은 코드정도라고만 생각하자

이제 N사와 D사에 UserDao를 공급할 때 UserDao, ConnectionMaker와 함께 DaoFactory도 제공하면 된다.
새로운 ConnectionMaker 구현 클래스로 변경이 필요하면 DaoFactory를 수정해서 변경된 클래스를 생성해주도록 변경하면 되며,
핵심 기술(어디까지나 가정이다)이 담긴 UserDao는 변경이 필요없으므로 안전하게 소스코드를 보존할 수 있다.
동시에 DB연결 방식은 자유로운 확장이 가능하다.

이렇게 Factory를 통해 분리했을때 얻을 수 있는 장점은 매우 다양하지만 그중에서도,
애플리케이션의 컴포넌트 역할을 하는 오브젝트와 애플리케이션의 구조를 결정하는 오브젝트를 분리했다는 데 가장 의미가 있다.

#### 오브젝트 팩토리의 활용

만약 DaoFactory에 UserDao가 아닌 다른 DAO의 생성기능을 넣으면 어떻게 될까?
AccountDao, MessageDao 등을 만들었다고 해보자.

```java
public class DaoFactory{
	public UserDao userDao(){
   	return new UserDao(new DConnectionMaker());
   }
   
   public AccountDao accountDao(){
   	return new AccountDao(new DConnectionMaker());
   }
   
   public MessageDao messageDao(){
   	return new MessageDao(new DConnectionMaker());
   }
}
```

딱 봐도 우리가 가장 처음에 메소드로 분리했던 DB커넥션정보가 중복되어 나타나고 있다.
이렇게 코드가 중복되는건 좋지 않은 현상이다.
DAO가 지금보다 더 많아지면 Connection 의 구현 클래스를 바꿀 땜마다 모든 메소드를 일일히 수정해야 하기 때문이다.

중복 문제를 해결하려면 역시 분리해내는 게 가장 좋은 방법이다.
ConnectionMaker의 구현 클래스를 결정하는 오브젝트를 만드는 코드를 별도의 메소드로 뽑아내고,
DAO를 생성하는 각 메도스에서는 새로 만든 메소드를이용하도록 수정해보자

가장 처음에 했던 getConnection 메소드를 따로 만들어 DB연결 기능을 분리해낸 것과 동일한 리팩토링 방법이다.


```java
public class DaoFactory{
	public UserDao userDao(){
   	return new UserDao(ConnectionMaker());
   }
   
   public AccountDao accountDao(){
   	return new AccountDao(ConnectionMaker());
   }
   
   public MessageDao messageDao(){
   	return new MessageDao(ConnectionMaker());
   }
   
   // 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 메소드
   public ConnectionMaker connectionMaker(){
   	return new DConnectionMaker();
   }
}
```

#### 제어권의 이전을 통한 제어관계 역전

이제 제어의 역전이라는 개념에 대해 알아보자.

> 제어의 역전이라는 건, 간단히 프로그램의 **제어 흐름 구조가 뒤바뀌는 것**이라고 설명할 수 있다.

일반적인 프로그램의 흐름을 살펴보면 각 오브젝트는 프로그램 흐름을 결정하거나 사용할 오브젝트를 구성하는 작업에 능동적으로 참여한다.

초기 UserDao를 보면 테스트용 main()메소드는 UserDao 클래스의 오브젝트를 직접 생성하고, 만들어진 오브젝트의 메소드를 사용한다.
UserDao 또한 자신이 사용할 ConnectionMaker의 구현클래스(예를 들면 DConnectionMaker)를 자신이 결정하고, 
그 오브젝트를 필요한 시점에서 생성해두고, 각 메소드에서 이를 사용한다.

즉!!!

모든 오브젝트가 능동적으로 자신이 사용할 클래스를 결정하고, 언제 어떻게 그 오브젝트를 만들지를 스스로 관장한다.
**모든 종류의 작업을 사용하는 쪽에서 제어하는 구조다.**

> `제어의 역전(Ioc)`이란 이런 제어 흐름의 개념을 거꾸로 뒤집는 것이다.

제어의 역전에서는 
- 오브젝트가 자신이 사용할 오브젝트를 스스로 선택하지 않는다.
- 당연히 생성하지도 않는다.
- 자신도 어떻게 만들어지고 어디서 사용되는지를 알 수 없다.

바로 모든 제어권한을 자신이 아닌 다른 대상에게 위이하기 때문이다.
main()과 같은 엔트리 포인트를 제외하면 **모든 오브젝트는 이렇게 위임받은 제어 권한을 갖는 특별한 오브젝트에 의해 결정되고 만들어진다.**

- 서블릿도 이러한 개념이 적용되어 있다고 볼 수있다.
- 템플릿 메소드 패턴도 이러한 개념을 활용해 문제를 해결하는 디자인 패턴이라고 불 수 있다.
- **프레임워크는 제어의 역전 개념이 적용된 대표적인 기술이다**

1. 라이브러리
라이브러리를 사용하는 애플리케이션 코드는 애플리케이션 흐름을 직접 제어한다.
단지 동작하는 중에 필요한 기능이 있을 때 능동적으로 라이브러리를 사용할 뿐이다.

2. 프레임워크
프레임워크는 거꾸로 애플리케이션 코드가 프레임워크에 의해 사용된다.
보통 프레임워크 위에 개발한 클래스를 등록해두고, 프레임워크가 흐름을 주도하는 중에 개발자가 만든 애플리케이션 코드를 사용하도록 만드는 방식이다.
**프레임워크에는 분명한 제어의 역전 개념이 적용되어 있어야 한다**
애플리케이션 코드는 프레임 워크가 짜놓은 틀에서 수동적으로 동작해야 한다.


> 우리가 만든 UserDao와 DaoFactory에도 제어의 역전이 적용되어 있다.

원래 ConnectionMaker의 구현 클래스를 결정하고 오브젝트를 만드는 제어권은 UserDao에게 있었다.
그런데 지금은 DaoFactory에게 있다.
자신이 어떤 ConnectionMaker 구현 클래스를 만들고 사용할지를 결정할 권한을 DaoFactoryy에 넘겼으니 UserDao는 이제 능동적이 아니라 수동적인 존재가 됐다.

UserDao 자신도 팩토리에 의해 수동적으로 만들어지고 자신이 사용할 오브젝트도 DaoFactory가 공급해주는 것을 수동적으로 사용해야 할 입장이 됐다.
UserDaoTest는 DaoFactory가 만들고 초기화해서 자신에게 사용하도록 공급해주는 ConnectionMaker를 사용할 수 밖에 없다.
더욱이 UserDao와 ConnectionMaker의 구현체를 생성하는 책임도 DaoFactory가 맡고 있다.

> 바로 이것이 제어의 역전(IoC)이 일어난 상황이다.
> 자연스럽게 관심을 분리하고 책임을 나누고 유연하게 확장 가능한 구조로 만들기 위해 DaoFactory를 도입했던 과정이 바로 IoC를 적용하는 작업이었다고 볼 수 있다.

제어의 역전에서는 프레임워크 또는 컨테이너와 같이 애플리케이션 컴포넌트의 생성과 관계설정, 사용, 생명주기 관리등을 관장하는 존재가 필요하다.
DaoFactory는 오브젝트 수준의 가장 단순한 IoC컨테이너 내지는 IoC프레임워크라고 불릴 수 있다.



---

### 스프링의 IoC

스프링의 핵심을 담당하는 건, 바로 빈 팩토리 또는 애플리케이션 컨텍스트라고 불리는 것이다.

#### 오브젝트 팩토리를 이용한 스프링 IoC

###### 애플리케이션 컨텍스트와 설정정보

- 스프링에서는 스프링이 제어권을 가지고 직접 만들고 관계를 부여하는 오브젝트를 `빈(bean)`이라고 부른다.
- 스프링 빈은 스프링컨테이너가 **생성과 관계설정, 사용 등을 제어해주는 제어의 역전이 적용된 오브젝트** 가리키는 말이다.
- 빈의 생성과 관계설정 같은 제어를 담당하는 IoC오브젝트를 `빈 팩토리(bean factory)`라고 부른다.

보통 `빈 팩토리`보다는 이를 좀 더 확장한 `애플리케이션 컨텍스트(application context)`를 주로 사용한다.
IoC방식을 따라 만들어진 일종의 빈 팩토리라고 생각하자

> 애플리케이션 컨텍스트 란?

- 별도의 정보를 참고해서 빈(오브젝트)의 생성, 관계설정 등의 제어 작업을 총괄한다.
- 기존 DaoFactory 코드에는 설정정보, 예를 들어 어떤 클래스의 오브젝트를 생성하고 어디에서 사용하도록 연결해줄 것인가 등에 관한 정보가 평버한 자바코드로 만들어져 있다.
- 직접 이런 정보를 담고 있진 않다.
- 별도로 설정정보를 담고 있는 무엇인가를 가져와 이를 활용하는 범용적인 IoC엔진 같은 것이라고 볼 수 있다.


###### DaoFactory를 사용하는 애플리케이션 컨텍스트

앞서 만든 DaoFactory를 스프링의 빈 팩토리가 사용할 수 있는 본격적인 설정정보로 만들어 보자.

- 스프링이 빈팩토리를 위한 오브젝트 설정을 담당하는 클래스라고 인식할 수 있도록 `@Configuration` 이라는 어노테이션을 추가한다.
- 오브젝트를 만들어 주는 메소드에는 `@Bean`이라는 어노테이션을 붙여준다.

> 이와같이 변경한 아래고 소스는 자바코드지만 사실은 XML과 같은 스프링 전용 설정정보라고 보는 것이 좋다.
> DoaFactory 클래스

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
...
@Configuration  -> 애플리케이션 컨텍스트 또는 빈팩토리가 사용할 설정정보라는 표시
public class DaoFactory{
	@Bean
   public UserDao userDao(){
   	return new UserDao(ConnectionMaker());
   }
   @Bean
   public ConnectionMaker connectionMaker(){
   	return new DConnectionMaker();
   }
}
```

> 이제 위의 DaoFactory를 설정정보로 사용하는 애플리케이션 컨텍스트를 만들어보자.

- 애플리케이션 컨텍스트는 `ApplicationContext` 타입의 오브젝트다.
- `ApplicationContext`를 구현한 클래스는 여러 가지가 있는데 DaoFactory 처러 `@Configuration`이 붙은 자바 코드를 설정정보로 사용하려면 `AnnotationConfigpplicaationContext`를 이용하면 된다.
- 애플리케이션 컨텍스트를 만들 때 생성자 파라미터로 DaoFactory 클래스를 넣어준다.
- 준비된 `ApplicationContext`의 getBean()이라는 메소드를 이용해 UserDao의 오브젝트를 가져올 수 있다.

> 위 내용대로 UserDaoTest 를 애플리케이션 컨텍스트로 만들어 보자

```java
public class UserDaoTest{
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
   	ApplicaationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
      UserDao dao = context.getBean("userDao", UserDao.class);
      ...
   }
}
```

`getBean()`메소드는 `ApplicationContext`가 관리하는 오브젝트를 요청하는 메소드다.
`getBean()`의 파라밈터인 "userDao"는 ApplicationContext에 등록된 빈의 이름이다.
DaoFactory()에서 @Bean이라는 어노테이션을 userDao라는 이름의 메소드에 붙였는데, 이 메소드 이름이 바로 빈의 이름이 된다.
즉, userDao라는 이름의 빈을 가져온다는 것은 DaoFactory의 userDao()메소드를 호출해서 그 결과를 가져온다고 생각하면 된다.

> 그런데 UserDao를 가져오는 메소드는 하나뿐인데 왜 굳이 이름을 사용할까?

그것은 UserDao를 생성하는 방식이나 구성을 다르게 가져가는 메소드를 추가할 수 있기 때문이다.

getBean()은 기본적으로 Object타입으로 리턴하게 되어 있어서 매번 리턴되는 오브젝트에 다시 캐스팅을 해줘야 하는데
제네릭을 사용해 getBean()의 두 번째 파라미터에 리턴타입을 주면, 지저분한 캐스팅 코드를 사용하지 않아도 된다.



#### 애플리케이션 컨텍스트의 동작방식

기존에 오브젝트 팩토리를 이용했던 방식과 스프링의 애플리케이션 컨텍스트를 사용한 방식을 비교해보자
오브젝트 팩토리에 대응되는 것이 스프링의 애플리케이션 컨텍스트다.

DaoFactory가 UserDao를 비롯한 DAO 오브젝트를 생성하고 DB 생성 오브젝트와 관계를 맺어주는 제한적인 역할을 하는 데 반해,
애플리케이션 컨텍스트는 애플리케이션에서 IoC를 적용해서 관리할 모든 오브젝트에 대한 생성과 관계설정을 담당한다.
대신 ApplicationContext에는 DaoFactory와 달리 직접 오브젝트를 생성하고 관계를 맺어주는 코드가 없고, 그런 생성정보와 연관관계정보를 별도의 설정정보를 통해 얻는다.

`@Configuration`이 붙은 Daofactory는 이 애플리케이션 컨텍스트가 활용하는 IoC설정정보다.
내부적으로는 애플리케이션 컨텍스트가 DaoFactory의 userDao()메소드를 호출해서 오브젝트를 가져온 것을 클라이언트가 getBean()으로 요청할 때 전달해준다.

이렇게 DaoFactory를 오브젝트 팩토리로 직접 사용했을 때와 비교해서 애플리케이션 컨텍스트를 사용했을 때 얻을 수 있는 장점은 다음과 같다.

- 클라이언트는 구체적인 팩토리 클래스를 알 필요가 없다
- 애플리케이션 컨텍스트는 종합 IoC 서비스를 제공해준다
   - 오브젝트 생성과 관계설정만이 전부가 아니다.
   - 오브젝트가 만들어지는 방식
   - 시점과 전략을 다르게 가져갈 수도 있다.
   - 이에 부가적으로 자동생성, 오브젝트에 대한 후처리, 정보의 조합, 설정 방식의 다변화등 오브젝트를 효과적으로 활용할 수 있는 다양한 기능을 제공한다.
- 애플리케이션 컨텍스트는 빈을 검색하는 다양한 방법을 제공한다.


---

### 싱글톤 레지스트리와 오브젝트 스코프

애플리케이션 컨텍스트와 기존에 만들었던 오브젝트 팩토리와는 중요한 차이점이 있다.

먼저 DaoFactory의 userDao()메소드를 두 번 호출해서 리턴되는 UserDao오브젝트를 비교해보자

> 오브젝트의 동일성과 동등성에 관한 내용 ( p.103 참조)

```java
DaoFactory factory = new DaoFactory();
UserDao dao1 = factory.userDao();
UserDao dao2 = factory.userDao();

System.out.println(dao1);
System.out.println(dao2);
```

> 출력결과

```java
springbook.dao.UserDao@119f375   //주소값은 당연히 매번 달라진다.
springbook.dao.UserDao@117a8bd
```

출력 결과에서 알 수 있듯이, 두 개는 각기 다른 값을 가진 동일하지 않은 오브젝트다.
userDao를 매번 호출하면 계속해서 새로운 오브젝트가 만들어질 것이다.

이번에는 애플리케이션 컨텍스트에 DaoFactory를 설정정보로 등록하고 getBean()메소드를 이용해 userDao라는 이름으로 등록된 오브젝트를 가져와 보자

```java
ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

UserDao dao3 = context.getBean("userDao", UserDao.class);
UserDao dao4 = context.getBean("userDao", UserDao.class);

System.out.println(dao3);
System.out.println(dao4);
```

> 출력결과

```java
springbook.dao.UserDao@ee9f375 
springbook.dao.UserDao@ee9f375
```

출력결과를 보면 두 오브젝트의 값이 같음을 알수 있다.
즉 getBean()을 두 번 호출해서 가져온 오브젝트가 동일하다는 말이다.
이렇게 스프링은 여러 번에 걸쳐 빈을 요청하더라도 매번 동일한 오브젝트를 돌려준다.
단순하게 getBean()을 실행할 때마다 userDao()메소드를 호출하고, 매번 new에 의해 새로운 UserDao가 만들어지지 않는다는 뜻이다.

> 그럼 여기서 의문...대체 왜??????


#### 싱글톤 레지스트리로서의 애플리케이션 컨텍스트

애플리케이션 컨텍스트는 우리가 만들었던 오브젝트 팩토리와 비슷한 방식으로 동작하는 IoC컨테이너다.
그러면서 동시에 이 애플리케이션 컨텍스트는 싱글톤을 저장하고 관리하는 싱글톤 레지스트리 이기도 하다.

스프링은 기본적으로 별다른 설정을 하지 않으면 내부에서 생성하는 빈 오브젝트를 모두 싱글톤으로 만든다.
주의할점은 여기서 싱글톤이라는 것은 디자인 패턴에서 나오는 싱글톤 패턴과 비슷한 개녀이지만 그 구현방법은 확연히 다르다.


##### 서버 애플리케이션과 싱글톤
왜 스프링은 싱글톤으로 빈을 만드는 것일까?

- 스프링이 주로 적용되는 대상이 자바엔터프라이즈 기술을 사용하는 서버환경이기 때문이다.
- 태생적으로 스프링은 엔터프라이즈 시스테을 위해 고안된 기술이기 때문에 서버 환경에서 사용될 때 그 가치가 있고,
실제로 스프링은 대부분 서버환경에서 사용된다.

스프링이 처음 설계됐던 대규모의 엔터프라이즈 서버환경은 **서버 하나당 최대로 초당 수십에서 수백 번씩 브라우저나 여타 시스템으로부터의 요청을 받아 처리할 수 있는 높은 성능이 요구되는 환경**이었고, 다양한 기능을 담당하는 오브젝트들이 차여하는 계층형 구조로 이뤄진 경우가 대부분이었다. 비즈니스 로직도 복잡하고...

그런데 매번 클라이언트에서 요청이 올 때마다 각 로직을 담당하는 오브젝트를 새로 만들어서 사용한다고 생각해보자..
아무리 자바의 오브젝트 생성과 가비지 컬렉션의 성능이 좋아졌다고 한들 이렇게 **부하가 걸리면 서버가 감당하기 힘들다.**

이런 이유로 엔터프라이즈 분야에서는 서비스 오브젝트라는 개념을 일찍부터 사용해 왔다.
**서블릿**은 자바 엔터프라이즈 기술의 가장 기본이 되는 서비스 오브젝트라고 할 수 있다.

스펙에서 강제하진 않지만, 서블릿은 대부분 멀티스레드 환경에서 싱글톤으로 동작한다.
서블릿 클래스당 하나의 오브젝트만 만들어 두고, 사용자의 요청을 담당하는 여러스레드에서 하나의 오브젝트를 공유해 동시에 사용한다.

이렇게 애플리케이션 안에 제한된 수 , 대개 한개의 오브젝트만 만들어서 사용하는 것이 싱글톤 패턴의 원리다.

##### 싱글톤 패턴의 한계

자바에서 싱글톤을 구현하는 방법은 보통 이렇다.

- 클래스 밖에서는 오브젝트를 생성하지 못하도록 생성자를 private 으로 만든다.
- 생성된 싱글톤 오브젝트를 저장할 수 있는 자신과 같은 타입의 스태틱 필드를 정의한다.
- 스태틱 팩토리 메소드인 getInstance()를 만들고 이 메소드가 최초로 호출되는 시점에서 한번만 오브젝트가 만들어지게 한다.
생성된 오브젝트는 스태틱 필드에 저장된다.
또는 스태틱 필드의 초기값으로 오브젝트를 미리 만들어둘 수도 있다.
- 한번 오브젝트(싱글톤)가 만들어지고 난 후에는 getInstance()메소드를 통해 이미 만들어져 스태틱 필드에 저장해둔 오브젝트를 넘겨준다.

> UserDao를 전형적인 싱글톤 패턴을 이용해 만든다면 다음과 같이 될 것이다.

```java
public class UserDao{
	privte static UserDao INSTANCE;
   ...
   private UserDao(ConnectionMaker connectionMaker){
   	this.connectionMaker = connectionMaker;
   }
   public static synchronized UserDao getInstance(){
   	if(INSTANCE == null) INSTANCE = new UserDao(???);
      return INSTANCE;
   }
   ...
}
```

일단 깔끔하게 정리했던 UserDao에 싱글톤을 위한 코드가 추가되고 나니 코드가 상당히 지저분해졌다.
게다가 private으로 바뀐 생성자는 외부에서 호출 할 수가 없기 때문에 DaoFactory에서 UserDao를 생성하며 ConnectionMaker오브젝트를 넣어주는 게 이제는 불가능해졌다.

> 여러모로 생각해봐도 지금까지 개선한 UserDao에 싱글톤 패턴을 도입하는 건 무리로 보인다.

일반적으로 싱글톤 패턴 구현 방식에는 다음과 같은 문제가 있다.

- private 생성자를 갖고 있기 때문에 상속할 수 없다.
- 싱글톤은 테스트하기가 힘들다
- 서버환경에서는 싱글톤이 하나만 만들어지는 것을 보장하지 못한다.
- 싱글톤의 사용은 전역 상태를 만들 수 있기 때문에 바람직하지 못하다.

##### 싱글톤 레지스트리
스프링은 서버환경에서 싱글톤이 만들어져서 서비스 오브젝트 방식으로 사용되는 것은 적극 지지한다.
하지만 자바의 기본적인 싱글톤 패턴의 구현 방식은 여러가지 단점이 있기 때문에, 스프링은 직접 싱글톤 형태의 오브젝트를 만들고
관리하는 기능을 제공한다.
그것이 바로 `싱글톤 레지스트리(singleton registry)`다.
스프링 컨테이너는 싱글톤을 생성하고, 관리하고, 공급하는 싱글톤 관리 컨테이너이기도 하다.

싱글톤 레지스트리의 장점!
- 스태틱 메소드와 `private` 생성자를 사용해야 하는 비정상적인 클래스가 아니라 평범한 자바 클래스를 싱글톤으로 활용하게 해준다.
- 평범한 자바 클래스라고 IoC방식의 컨테이너를 사용해서 생성과 관계설정, 사용등에 대한 제어권을 컨테이너에게 넘기면 손쉽게 싱글톤 방식으로 만들어져 관리되게 할 수 있다.
- 싱글톤 방식으로 사용될 어플리케이션 클래스라도 `public` 생성자를 가질 수 잇다.
- 테스트 환경에서 자유롭게 오브젝트를 만들 수 있고, 테스트를 위한 목 오브젝트로 대체하는 것도 간단하다.
- 생성자 파라미터를 이용해서 사용할 오브젝트를 넣어주게 할 수도 있다.
- **가장 중요한 것은 싱글톤 패턴과 달리 스프링이 지지하는 객체지향적인 설계방식과 원칙, 디자인 패턴(싱글톤 패턴은 제외)등을 
적용하는 데 아무런 제약이 없다는 점이다.**

즉 스프링은 **IoC컨테이너**일뿐만 아니라, 고전적인 싱글톤 패턴을 대신해서 싱글톤을 만들고 관리해주는 **싱글톤 레지스트리**라는 점을 기억하자.

스프링이 빈을 싱글톤으로 만드는 것은 결국 오브젝트의 생성방법을 제어하는 IoC컨테이너로서의 역할이다..


#### 싱글톤과 오브젝트의 상태
싱글톤은 멀테스레드 환경이라면 여러 쓰레드가 동시에 접근해서 사용할 수 있으므로 상태 관리에 주의를 기울여야 한다.

- 기본적으로 싱글톤이 멀티스레드 환경에서 서비스 형태의 오브젝트로 사용되는 경우에는 상태정보를 내부에 갖고 있지 않은 무상태(stateless)방식으로 만들어져야 한다.

> 그렇다면 상태가 없는 방식으로 클래스를 만드는 경우에 각 요청에 대한 정보나, DB나 서버의 리소스로부터 생성한 정보는 어떻게 다뤄야 할까?

> 이때는 파라미터와 로컬변수, 리턴 값 등을 이용하면 된다.

메소드 파라미터나, 메소드 안에서 생성되는 로컬변수는 매번 새로운 값을 저장할 독립적인 공간이 만들어지기 때문에 싱글톤이라고 해도 여러 스레드가 변수의 값을 덮어쓸 일은 없다.

> 인스턴스 변수를 사용하도록 수정한 UserDao

```java
public class UserDao{
	private ConnectionMaker connectionMaker;	// 초기에 설정하면 사용 중에는 바뀌지 않는 읽기전용 인스턴스 변수
   private Connection c;			// 매번 새로운 값으로 바뀌는 정보를 담은 인스턴스 변수... 
   private User user;				// 심각한 문제가발생한다.
   
   public User get(String id) throws ClassNotFoundException, SQLException{
   	this.c = connectionMaker.makeConnection();
      ...
      this.user = new User();
      this.user.setId(rs.getString("id"));
      this.user.setName(rs.getString("name"));
      this.user.setPassword(rs.getString("password"));
      ...
      return this.user;
   }
}
```

기존에 만들었던 UserDao와 다른 점은 기존에 로컬변수로 선언하고 사용했던 Connection과 User를 클래스의 인스턴스 필드로 선언했다는 것이다.
따라서 싱글톤으로 만들어져서 멀티쓰레드 환경에서 사용하면 위에서 설명한 대로 심각한 문제가 발생한다.
따라서 스프링의 싱글톤 빈으로 사용되는 클래스를 만들 때는 기존의 UserDao처러 개별적으로 바뀌는 정보는 로컬 변수로 정의하거나, 파라미터로 주고받으면서 사용하게 해야 한다.

단 기존 UserDao의 ConnectionMaker 인터페이스 타입처럼 읽기전용의 정보는 인스턴스 변수로 정의해서 사용해도 된다.

#### 스프링 빈의 스코프
스프링이 관리하는 오브젝트, 즉 빈이 생성되고, 존재하고, 적용되는 범위에 대해 알아보자
스프링에서는 이것을 빈의 **스코프(scope)**라고 한다. 스프링 빈의 기본 스코프는 싱글톤이다.
싱글톤 스코프는 컨테이너 내에 한 개의 오브젝트만 만들어져서, 강제로 제거하지 않는 한 스프링 컨테이너가 존재하는 동안 계속 유지된다.

대부분의 빈은 싱글톤 스코프를 갖지만, 그 외의 스코프를 가질 수 있다.
대표적으로 프로토타입 스코프가 있는데, 싱글톤과 달리 컨테이너에 빈을 요청할 때마다 매번 새로운 오브젝트를만들어 준다
물론 이 외에도 여러가지의 스코프가 있다 (싱글톤 외의 빈의 스코프에 대해서는 10장 참조)


---

### 의존관계 주입(DI)

##### 제어의 역전(IoC)과 의존관계 주입

IoC라는 용어는 매우 느슨하게 정의돼서 폭넓게 사용되는 언어라서, 스프링을 단순히 IoC 컨테이너라고만 해서는 스프링이 제공하는 기능의 특징을 명확하게 설명하지 못한다.

그래서 스프링이 제공하는 IoC 방식의 핵심을 짚어주는 `의존관게 주입(Dependency Injection)`이라는 이름이 등장했다.


#### 런타임 의존관계 설정

##### 의존관계

먼저 의존관계가 뭔지 부터 생각해보자

두 개의 클래스 또는 모듈이 의존관계에 있다고 말할 때는 항상 방향성을 부여해줘야 한다.
즉 누가 누구에게 의존하는 관계에 있다는 식이어야 한다.
UML모델에서는 두 클래스의 `의존관계(dependency relationship)`를 점선으로 된 화살표로 표현한다.

> 그렇다면 의존하고 있다는 건 무슨 의미일까?

A가 B에 의존하고 있다고 생각해보자
만약 B가 변하면 그것이 A에 영향을미친다는 뜻이다.
더 자세히 예를 들어보면 A 에서 B에 정의된 메소드를 호출해서 사용하는 경우다.
의존관계에는 방향성이 있다고 했는데, 이때 B는 A에 의존하지 않으므로 A가 어떻게 변하든 B는 상관이 없다


##### UserDao의 의존관계

지금까지 작업해왔던 UserDao의 예를 보자.
UserDao가 ConnectionMaker에 의존하고 있는 형태다.
따라서 ConnectionMaker 인터페이스가 변한다면 그 영향을 UserDao가 직접적으로 받게된다.
하지만 ConnectionMaker 인터페이스를 구현한 클래스, 즉 DConnectionMaker 등이 다른것으로 바뀌거나 그 내부에서 사용하는 메소드에 변화가 생겨도 UserDao에 영향을 주지 않는다.

이렇게 인터페이스에 대해서만 의존관계를 만들어두면 인터페이스 구현 클래스와의 관계는 느슨해지면서 변화에 영향을 덜 받는 상태가 된다.
즉 결합도가 낮다고 설명할 수 있다.

> 이렇게 인터페이스를 통해 의존관계를 제한해주면 그만큼 변경에서 자유로워지는 셈이다.

현재 UserDao는 DConnectionMaker 라는 클래스의 존재도 알지 못한다.
모델의 관점에서 보자면 UserDao는 DConnectionMaker 클래스에는 의존하지 않기 때문이다.
UML에서 말하는 의존관계란 이러헥 설계 모델의 관점에서 이야기하는 것이다.

그런데!

모델이나 코드에서 클래스와 인터페이스를 통해 드러나는 의존관계 말고, 런타임 시에 오브젝트 사이에서 만들어지는 의존관계도 있다.
`런타임 의존관계` 또는 `오브젝트 의존관계`인데, **설계 시점의 의존관계가 실체화된 것**이라고 볼 수있다.
`런타임 의존관계`는 모델링 시점의 의존관계와는 성격이 분명히 다르다.

이때 프로그램이 시작되고 UserDao 오브젝트가 만들어지고 나서 런타임 시에 의존관계를 맺는 대상, 즉 실제 사용대상인 오브젝트를 
`의존 오브젝트(dependent object)`라고 말한다.

의존관계 주입은 이렇게 구체적인 의존 프로젝트와 그것을 사용할 주체, 보통 클라이언트라고 부르는 오브젝트를 런타임 시에 연결해주는 작업을 말한다.

정리하면 의존관계 주입이란 다음과 같은 세가지 조건을 충족하는 작업을 말한다.
- 클래스 모델이나 코드에는 런타이 시점의 의존관계가 드러나지 않는다.
그러기 위해서는 인터페이스에만 의존하고 있어야 한다.
- 런타임 시점의 의존관계는 컨테이너나 팩토리 같은 제 3의 존재가 결정한다.
- 의존관계는 사용할 오브젝트에 대한 레퍼런스를 외부에서 제공(주입)해줌으로써 만들어진다.

> 여기서 `의존관계 주입`의 핵심은 설계 시점에는 알지 못했던 **두 오브젝트의 관계를 맺도록 도와주는 제3의 존재가 있다는 것**이다


- 전략패턴에 등장하는 클라이언트
- 앞에서 만들었던 DaoFactory
- 스프링의 애플리케이션 컨텍스트,
- 빈 팩토리
- IoC컨테이너

등이 모두 외부에서 오브젝트 사이의 런타이 관계를 맺어주는 책임을 지닌 제3의 존재라고 볼 수 있다.

##### UserDao의 의존관계 주입

UserDao에 적용된 의존관계 주입 기술을 다시 살펴보자

처음에 UserDao는 자신이 사용할 구체적인 클래스를 알고 있어야만 했다.
관계설정의 책임을 분리하기 전에 UserDao 클래스의 생성자를 다시한번 살펴보자

```java
public UserDao(){
	connectionMaker = new DConnectionMaker();
}
```

이 코드에 따르면 UserDao는 이미 설계 시점에서 DConnectionMaker라는 구체적인 클래스의 존재를 알고 있다.
즉 DConnectionMaker 오브젝트를 사용하겠다는 것까지 UserDao가 결정하고 관리하고 있는 셈이다.

이 코드의 문제는 이미 런타임 시의 의존관계가 코드 속에 다 미리 결정되어 있다는 점이었다.

그래서 IoC방식을 써서 UserDao로부터 런타임 의존관계를 드러내는 코드를 제거하고, 제3의 존재에 런타이 의존관계 결정 권한을 위임했고, 그렇게 해서 최종적으로 만들어진 것이 DaoFactory다.

DaoFactory는 위에서 열거한 의존관계 주입의 세가지 조건을 모두 충족한다고 볼 수 있다.
그래서 DaoFactory는 `DI 컨테이너`다.

DI 컨테이너는 UserDao를 만드는 시점에서 생성자의 파라미터로 이미만들어진 DConnectionMaker의 오브젝트를 전달한다.
정확히는 DConnectionMaker 오브젝트의 레퍼런스가 전달되는 것이다.
주입이라는 건 외부에서 내부로 무엇인가를 넘겨줘야 하는것인데, 자바에서 오브젝트에 무엇인가를 넣어준다는 개념은 메소드를 실행하면서 파라미터로 오브젝트의 레퍼런스를 전달해주는 방법뿐이다.
가장 손쉽게 사용할 수 있는 파라미터 전달이 가능한 메소드는 바로 생성자다.

DI컨테이너는 자신이 결정한 의존관계를 맺어줄 클래스의 오브젝트를 만들고 이 생성자의 파라미터로 오브젝트의 레퍼런스를 전달해준다.
바로 다음의 코드가 이 과정의 작업을 위해 필요한 전형적인 코드다

```java
public class UserDao {
	private ConnectionMaker connectionMaker;
	
	public UserDao(ConnectionMaker simpleConnectionMaker) {
		this.connectionMaker = simpleConnectionMaker;
	}
}
```

이렇게 해서 두 개의 오브젝트 간에 런타임 의존관계가 만들어졌다.
UserDao 오브젝트는 이제 생성자를 통해 주입받은 DConnectionMaker 오브젝트를 언제든지 사용하면 된다.

이렇게 DI컨테이너에 의해 런타임 시에 의존 오브젝트를 사용할 수 있도록 그 레퍼런스를 전달받는 과정이 마치 메소드(생성자)를 통해 DI 컨테이너가 UserDao에게 주입해 주는 것과 같다고 해서 이를 `의존관계 주입`이라고 부른다.

DI는 자신이 사용할 오브젝트에 대한 선택과 생성 제어권을 외부로 넘기고 자신은 수동적으로 주입받은 오브젝트를 사용한다는 점에서 
IoC의 개념에 잘 들어맞는다.
스프링 컨테이너의 IoC는 주로 의존관계 주입 또는 DI라는 데 초점이 맞춰져 있다..
그래서 스프링을 IoC컨테이너 외에도 DI 컨테이너 또는 DI 프레임워크라고 부르는 것이다.

#### 의존관계 검색과 주입

스프링이 제공하는 IoC방법에는 `의존관계 주입`만 있는 것이 아니다.
코드에서는 구체적인 클래스에 의존하지 않고, 런타임 시에 의존관계를 결정한다는 점에서 의존관계 주입과 비슷하지만,
의존관계를 맺는 방법이 외부로부터의 주입이 아니라 스스로 검색을 이용하기 때문에 `의존관계 검색(dependency lookup)`이라고 불리는 것도 있다.

> 의존관계 검색은 자신이 필요로 하는 의존 오브젝트를 능동적으로 찾는다.

물론 이때 자신이 어떤 클래스의 오브젝트를 이용할지 결정하지는 않는다.(그러면 IoC라고 할 수 없겠찌)

즉, `의존관계 검색`은 
 `의존관계 주입`과 같이 런타임 시 의존관계를 맺을 오브젝트를 결정하는 것과 오브젝트의 생성작업은 외부 컨테이너에게 IoC로 맡기지만,
이를 ++**가져올 때는 메소드나 생성자를 통한 주입 대신 스스로 컨테이너에게 요청하는 방법을 사용한다.**++

> DaoFactory를 이용하는 생성자를 구성해보자

```java
public UserDao(){
	DaoFactory daoFactory = new DaoFactory();
   this.connectionMaker = daoFactory.connectionMaker();
}
```

이렇게 소스를 변경해도 UserDao는 여전히 자신이 어떤 ConnectionMaker 오브젝트를 사용할지 미리 알지 못한다.
여전히 코드의 의존대상은 ConnectionMaker 인터페이스 뿐이다.

런타임 의존관계를 맺으며 IoC의 개념을 잘 따르고 있는 코드다.
하지만 적용방법은 외부로부터의 주입이 아니라 스스로 IoC컨테이너인 DaoFactory에게 요청하는 것이다.

스프링의 IoC컨테이너인 애플리케이션 컨텍스트는 `getBean()`이라는 메소드를 제공하는데, 바로 이 메소드가 의존관계 검색에 사용되는 것이다.

> 애플리케이션 컨텍스트를 사용해서 의존관계 검색 방식으로 ConnectionMaker를 가져오도록 UserDao를 수정해보자

```java
public UserDao(){
	AnnotationConfigApplictionContext context = new AnnotationConfigApplictionContext(DaoFactory.class);
   this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
}
```

`의존관계 검색`은 기존 의존관계 주입의 거의 모든 장저을 갖고 있다. 단 방법만이 조금 다를 뿐이다.
대개는 의존관계 주입 방식을 사용하는 편이 낫지만,
의존관계 검색방식을 사용해야 할 때가 있다. 앞에서 만들었던 테스트 코드인 UserDaoTest를 보자.
테스트 코드에서는 이미 의존관계 검색방식인 getBean()을 사용했다.
스프링의 IoC와 DI 컨테이너를 적용했다고 하더라도 애플리케이션의 기동시점에서 적어도 한 번은 의존관계 검색 방식을 사용해 오브젝트를 가져와야 한다.
스태틱 메소드인 main()에서는 DI를 이용해 오브젝트를 주입받을 방법이 없기 때문이다.
서버에서도 마찬가지다.

`의존관계 검색`과 `의존관계 주입`을 적용할 때 발견할 수 있는 중요한 차이점이 하나 있는데,
의존관계 검색방식에서는 검색하는 오브젝트는 자신이 스프링의 빈일 필요가 없다는 점이다.
즉 ConnectionMaker만 스프링의 빈이기만 하면된다.

반면에 의존관계 주입에서는 UserDao와 ConnectionMaker 사이에 DI가 적용되려면 UserDao도 반드시 컨테이너가 만드는 빈 오브젝트여야 한다.
컨테이너가 UserDao에 ConnectionMaker 오브젝트를 주입해주려면 UserDao에 대한 생성과 초기화 권한을 갖고 있어야 하고, 
그러려면 UserDao는 IoC방식으로 컨테이너에서 생성되는 오브젝트, 즉 빈이어야 하기 때문이다.
이런 점에서 DI와 DL(의존관계 검색)은 적용방법에 차이가 있다.

> DI를 원하는 오브젝트는 먼저 자기 자신이 컨테이너가 관리하는 빈이 돼야 한다는 사실을 잊지 말자.


#### 의존관계 주입의 응용
런타임 시에 사용 의존관계를 맺을 오브젝트를 주입해준다는 DI 기술의 장점은 무엇일까?

앞에서 살펴봤던 오브젝트 팩토리가 바로 DI 방식을 구현한 것이니, 앞에서 설명한 모든 객체지향 설계와 프로그래밍의 원칙을 따랏을 때 얻을 수 있는 장점이 그대로 DI기술에도 적용될 것이다.

스프링이 제공하는 기능의 99%가 DI의 혜택을 이용하고 있다.

##### 기능 구현의 교환

실제 운영에 들어가기전 개발시점에서는 개발자 PC에 설치된 로컬 DB로 사용해야 한다고 해보자.
그리고 개발이 완료되면 운영서버로 배치를 해야 하는데 이때 DI방식을 적용하지 않았다고 가정해보자.

개발중에는 로컬DB를 사용하도록하는 클래스를 만들어서 사용했을 것이고,
이 클래스는 모든 DAO에 들어있을 것이다.
DB연결정보를 운영서버의 데이터베이스로 하기 위해서는 이를 모두 수정해야 하는 문제가 생기는 것이다.

반면 DI방식을 적용해서 만들었다면 모든 DAO는 생성시접에 ConnectionMaker 타입의 오브젝트를 컨테이너로부터 제공받는다.
구체적인 사용 클래스 이름은 컨테이너가 사용할 설정정보에 들어 있다.
@Configuration 이 붙은 DaoFactory를 사용한다고 하면 개발자 PC에서는 DaoFactory의 코드를 다음과 같이 만들어서 사용하면 된다.

> 개발용 ConnectionMaker 생성코드

```java
@Bean
public ConnectionMaker connectionMaker(){
	return new LocalDBConnectionMaker();
}
```

이를 서버에 배포할 때는 어떤 DAO클래스와 코드도 수정할 필요가 없다.
단지 서버에서 사용할 DaoFactory를 다음과 같이 수정해주기만 하면 된다.

> 운영용 ConnectionMaker 생성코드

```java
public ConnectionMaker connectionMaker(){
	return new ProdeuctionDBConectionMaker();
}
```

##### 부가기능 추가

DAO가 DB를 얼마나 많이 연결해서 사용하는지 파악하고 싶다고 해보자.
DB연결횟수를 카운팅하기 위해 무식한 방법으로, 모든 DAO의 makeConnection() 메소드를 호출하는 부분에 새로 추가한 카운터를 증가시키는 코드를 넣어야 할까?

이런 DB연결횟수를 세는 일은 DAO의 관심사항이 아니다. 어떻게든 분리돼야 할 책임이기도 하다

DI 컨테이너에서라면 아주 간단한 방법으로 가능하다. DAO와, DB커넥션을 만드는 오브젝트 사이에 연결횟수를 카운팅하는 오브젝트를 하나 더 추가하는 것이다.
DI를 이용한다고 했으니 당연히 기존 코드는 수정하지 않아도 된다.

> CountingConnectionMaker 라는 클래스 리스트트 다음과 같이 만들어보자
> 이때 중요한 것은 ConnectionMaker 인터페이스를 구현해서 만든다는 점이다.
> DAO가 의존할 대상이 될 것이기 때문이다.

```java
public class CountingConnectionMaker implements ConnectionMaker {
	int counter = 0;
	private ConnectionMaker realConnectionMaker;

	public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}

	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		this.counter++;
		return realConnectionMaker.makeConnection();
	}

	public int getCounter() {
		return this.counter;
	}
}
```

CountingConnectionMaker 클래스는 ConnectionMaker 인터페이스를 구현했지만 내부에서 직접 DB커넥션을 만들지 않는다.
대신 DAO가 DB 커텍션을 가져올 때마다 호출하는 makerConnection()에서 DB 연결횟수 카운터를 장가시킨다.
CountingConnectionMaker는 자신의 관심사인 DB 연결횟수 카운팅 작업을 마치면 실제 DB커넥션을 만들어주는 
`relConnectionMaker`에 저장된 `ConnectionMaker` 타입 오브젝트의 makeConnection()을 호출해서 그 결과를 DAO에게 돌려준다.
그래야만 DAO가 DB 커넥션을 사용해서 정상적으로 동작할 수 있다.

생성자를 보면 CountingConnectionMaker도 DI를 받는 것을 알 수 있다.
CountinConnectionMaker의 오브젝트가 DI 받을 오브젝트도 역시 ConnectionMaker 인터페이스를 구현한 오브젝트다 
아마 실제 DB 커넥션을 돌려주는 DConnectionMaker 클래스의 오브젝트일 것이다.

CountinConnectionMaker가 추가되면서 런타임 의존관계가 어떻게 바뀌는지 살펴보자.

CountinConnectionMaker를 사용하기 전의 런타임 의존관계를 보면
UserDao 오브젝트는 ConnectionMaker 타입의 DConnectionMaker 오브젝트에 의존한다.
UserDao는 ConnectionMaker 의 인터페이스에만 의존하고 있기 때문에, ConnectionMaker 인터페이스를 구현하고 있다면 어떤 것이든 DI가 가능하다.

그래서 UserDao 오브젝트가 DI 받는 대상의 설정을 조정해서 DConnection 오브젝트 대신 CountingConnectionMaker 오브젝트로 바꿔치기 하는 것이다.
이렇게 해두면 UserDao()가 DB커넥션을 가져오려고 할 때마다 CountingConnectionMaker의 makeConnection()메소드가 실행되고 카운터는 하나씩 증가할 것이다.

> 그럼 여기서 의문 기존의 DConnection을 카운팅 메소드로 대체했으니 DB커넥션정보를 다시 제공해줘야 되지 않을까?

그래서 CountingConnectionMaker가 다시 실제 사용할 DB커넥션을 제공해주는 DConnectionMaker를 호출하도록 만들어야 한다.
역시 DI를 사용하면 된다.
이 의존관계를 표현해보면 다음과 같다

`UserDao` -> `CountingConnectionMaker` -> `DConnectionMaker`

> 새로운 의존관계를 담은 설정용 CountingDaoFactory 클래스

```java
packge springbook.user.dao;
...
@Configuration
public class CountingDaoFactory{
	@Bean
   public UserDao userDao(){
   	return new UserDao(connectionMaker());
   }

   @Bean
   public ConnectionMaker connectionMaker(){
   	return new CountingConnectionMaker(realConnectionMaker());
   }

   @Bean
   public ConnectionMaker realConnectionMaker(){
   	return new DConnectionMaker();
   }
}
```

이제 커넥션 카운팅을 위한 실행코드를 만들어보자.
기본적으로 UserDaoTest와 같지만 설정용 클래스를 CountingDaoFactory로 변경해줘야 한다.
DAO를 DL 방식으로 가져와 어떤 작업이든 여러 번 실행시킨다. (테스트를 위해 그냥 0~9까지 10번 for문으로 돌렸음)

설정정보에 지정된 이름과 타입만 알면 특정 빈을 가져올 수 있으니 CountingConnectionMaker 오브젝트를 가져오는 건 간단하다.

지금은 DAO가 하나뿐이지만 DAO가 수십, 수백 개여도 상관없다. DI의 장점은 관심사의 분리(SoC)를 통해 얻어지는 높은 응집도에서 나온다.
모든 DAO가 직접 의존해서 사용할 ConnectionMaker 타입 오브젝트는 connectionMaker()메소드에서 만든다.
따라서 CountingConnectionMaker의 의존관계를 추가하려면 이 메소드만 수정하면 그만ㅊ이다.
또한 CountingConnectionMaker 를 이용한 분석작업이 모두 끝나면, 다시 CountingDaoFactory 설정 클래스를 DaoFactory로 변경하거나 connectionMaker()메소드를 수정하는 것만으로 DAO의 런타이 의존관계는 이전 상태로 복구된다.



#### 메소드를 이용한 의존관계 주입

지금까지는 UserDao의 의존관계 주입을 위해 생성자를 사용했다. 생성자에 파라미터를 만들어두고 이를 통해 DI 컨테이너가 의존할 오브젝트 레퍼런스를 넘겨주도록 만들었다. 그런데 의존관계 주입 시 반드시 생성자를 사용해야 하는 것은 아니다.
생성자가 아닌 일반 메소드를 사용할 수도 있을 뿐만 아니라, 생성자를 사용하는 방법보다 더 자주 사용된다.

> 생성자가 아닌 일반 메소드를 이용해 의존 오브젝트와의 관계를 주입해주는 데는 크게 두 가지 방법이 있다.

- 수정자 메소드를 이용한 주입
- 일반 메소드를 이용한 주입

여기서 생성자가 수정자 메소드보다 나은 점은 한 번에 여러개의 파라미터를 받을 수 있다는 점이다.
하지만 파라미터의 개수가 많아지고 비슷한 타입이 여러개라면 실수하기 쉽다.
임의의 초기화 메소드를 이용하는 DI는 적절한 개수의 파라미터를 가진 여러 개의 초기화 메소드를 만들 수도 있기 때문에
한 번에 모든 필요한 파라미터를 다 받아야 하는 생성자보다 낫다.


> 스프링은 전통적으로 메소드를 이용한 DI 방법 중에서 수정자 메소드를 가장 많이 사용해왔다.
> 뒤에서 보겠지만 DaoFactory 같은 자바 코드 대신 XML을 사용하는 경우에는 자바빈 규약을 따르는 수정자 메소드가 가장 사용하기 편리하다.

수정자 메소드 DI를 사용할 때는 메소드의 이름을 잘 결정하는 게 중요하다.
만약 이름을 짓는 게 귀찮다면 메소드를 통해 DI받을 오브젝트의 타입 이름을 따르는 것이 가장 무난하다.

> 예를 들어 ConnectionMaker 인터페이스 타입의 오브젝트를 DI받는다면 메소드의 이름은 setConnectionMaker()라고 ~

UserDao도 수정자 메소드를 이용해 DI 하도록 만들어보자.
- 기존 생성자는 제거한다.
- 생성자를 대신할 setConnectionMaker()라는 메소드를 하나 추가하고 파라미터로 ConnectionMaker 타입의 오브젝트를 받도록 선언한다.
- 파라미터로 받은 오브젝트는 인스턴스 변수에 저장해두도록 만든다. 대부분의 IDE는 수정자 메소드를 자동생성하는 기능이 있다.
- 인스턴스 변수만 정의해두고 자동생성 기능을 사용하면 편리하다

> 수정자 메소드 DI를 적용한 UserDao

```java
public class UserDao{
	private ConnectionMaker connectionMaker;
   
   public void setConnectionMaker(ConnectionMaker connectionMaker){
   	this.connectionMaker = connectionMaker;
   }
   ...
   // 위 코드는 수정자 메소드 DI의 전형적인 코드다 잘 기억해두자
}
```

UserDao를 수정자 메소드 DI 방식이 가능하도록 변경했으니 DI를 적용하는 DaoFactory의 코드도 함께 수정해줘야 한다.

> 수정자 메소드 DI를 사용하는 팩토리 메소드 DaoFactory 

```java
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}
```

단지 의존관계를 주입하는 시점과 방법이 달라졌을 뿐 결과는 동일하다

스프링은 생성자, 수정자 메소드, 초기화 메소드를 이용한 방법 외에도 다양한 의존관계 주입 방법을 지원한다.
(이는 Vol. 2를 참조)


---

### XML을 이용한 설정

스프링은 DaoFactory와 같은 자바 클래스를 이용하는 것 외에도, 다양한 방법을 통해 DI의존관계 설정정보를 만들 수 있는데,
가장 대표적인 것이 바로 XML이다.

XML은 단순한 텍스트파일이기 때문에 다루기 쉬우며, 자바코드와 달리 컴파일과 같은 별도의 빌드 작업도 필요없다는 장점이 있다.
이제 DaoFactory 자바 코드에 담겨있던 DI를 위한 오브젝트 의존관계 정보를 XML을 이용해서 만들어 보자

#### XML 설정

스프링의 애플리케이션 컨텍스트는 XML에 담긴 DI 정보를 활용할 수 있다.
DI 정보가 담긴 XML 파일은 `<beans>`를 루트 엘리먼트로 사용한다. 이름에서 알 수 있듯이 `<beans>`안에는 여러개의 `<bean>`을 정의할 수 있다.
XML 설정은 `@Configuration`과 `@Bean`이 붙은 자바 클래스로 만든 설정과 내용이 동일하다.

- `@Configuration` = `<beans>`
- `@Bean` = `<bean>`

위와 같이 대응해서 생각하자

하나의 `@Bean`메소드를 통해 얻을 수 있는 빈의 DI 정보는 다음 세가지가
- 빈의 이름 : `@Bean`메소드 이름이 빈의 이름이다. 이 이름은 `getBean()`에서 사용된다.
- 빈의 클래스 : 빈오브젝트를 어떤 클래스를 이용해서 만들지를 정의한다.
- 빈의 의존 오브젝트 : 빈의 생성자나 수정자 메소드를 통해 의존 오브젝트를 넣어준다. 의존 오브젝트도 하나의 빈이므로 이름이 있을 것이고, 그 이름에 해당하는 메소드를 호출해서 의존 오브젝트를 가져온다. 의존 오브젝트는 하나 이상일 수도 있다.

XML 에서 `<bean>`을 사용해도 이 세가지 정보를 정의할 수 있다.
ConnectionMaker 처럼 더 이상 의존하고 있는 오브젝트가 없는 경우에는 세 번째 의존 오브젝트 정보는 생략할 수 있다.


##### connectionMaker() 전환

먼저 DaoFactory의 connectionMaker()메소드에 해당하는 빈을 XML로 정의해보자.
connectionMaker()로 정의되는 빈은 의존하는 다른 오브젝트는 없으니 DI정보 세 가지중 두 가지만 있으면 된다.

|  | 자바 코드 설정정보 | XML 설정정보
|--------|--------|-------
| 빈 설정파일 | @Configuration | `<beans>`
| 빈의 이름 | @Bean methodName() | `<bean id="methodName">`
| 빈의 클래스 | return new BeanClass(); | `<bean class="a.b.c...BeanClass">`

DaoFactory의 @Bean 메소드에 담긴 정보를 1:1로 XML의 태그와 애트리뷰트로 전환해주기만 하면 된다.
단 `<bean>`태그의 class 애트리뷰트에 지정하는 것은 자바 메소드에서 오브젝트를 만들때 사용하는 클래스 이름이라는 점에 주의하자.

- XML에서는 리턴하는 타입을 지정하지 않아도 된다.
- class 애트리뷰터에 넣을 클래스 이름은 패키지까지 모두 포함해야 한다.

이제 connectionMaker()메소드를 `<bean>`태그로 전환해보자.

```xml
<bean id="connectionMaker" class="springbook.user.dao.DConnectionMaker"></bean>	
```

##### userDao() 전환

이번에는 userDao 메소드를 XML로 변환해보자

userDao()에는 DI 정보의 세 가지 요소가 모두 들어 있다.
여기서 관심을 가질 것은 수정자 메소드를 사용해 의존관계를 주입해주는 부분이다.
스프링 개발자가 수정자 메소드를 선호하는 이유 중에는 XML로 의존관계 정보를 만들 떄 편리하다는 점도 있따.

자바반의 관례를 따라서 수정자 메소드는 프로퍼티가 된다.
프로퍼티 이름은 메소드 이름에서 set을 제외한 나머지 부분을 사용한다.
예를 들어 오브젝트에 setConnectionMaker()라는 이름의 메소드가 있다면 connectionMaker라는 프로퍼티를 갖는다고 할 수 있다.

XML에서는 `<property>` 태그를 사용해 의존 오브젝트와의 관계를 정의한다.
`<property>`태그는 name과 ref라는 두 개의 애트리뷰트를 갖는다.

- name : 프로퍼티의 이름으로, name을 통해 수정자 메소드를 알 수 있다.
- ref : 수정자 메소드를 통해 주입해줄 오브젝트의 빈 이름이다.

DI할 오브젝트도 역시 빈 이므로 그 빈의 이름을 지정해주면 된다.
만약 `@Bean`메소드에서라면 다음과 같이 지정을 한다.

```java
userDao.setConnectionMaker(connectionMaker());
```

여기서 userDao.setConnectionMaker()는 userDao 빈의 connectionMaker 프로퍼티를 이용해 의존관계 정보를 주입한다는 뜻이다.
메소드의 파라미터로 넣는 connectionMaker()는 connectionMaker()메소드를 호출해서 리턴하는 오브젝트를 주입하라는 의미다.
바로 이 두가지 정보를 `<property>`의 name 애트리뷰트와 ref 애트리뷰트로 지정해주면 된다.
그 후 이 프로퍼티 태그를 userDao빈을 정의한 `<bean>`태그 안에 넣어주면 된다

```xml
<bean id="userDao" class="springbook.dao.UserDao">
	<property name="connectionMaker" ref="connectionMaker" />
</bean>
```

##### XML의 의존관계 주입 정보

이렇게 해서 두 개의 <bean> 태그를 이용해 @Bean 메소드를 모두 XML로 변환했다.
다음과 같이 `<beans>`로 전환한 두 개의 `<bean>` 태그를 감싸주면 DaoFactory로부터 XML로의 전환 작업이 끝난다.

```xml
<beans>
	<bean id="connectionMaker" class="springbook.user.dao.DConnectionMaker" />
   <bean id="userDao" class="springbook.user.dao.UserDao">
   	<property name="connectionMaker" ref="connectionMaker" />
   </bean>
</beans>
```

여기서 `<property>` 태그의 name과 ref는 그 의미가 다르므로 이름이 같아서 헷갈릴수 있는데, 이 둘이 어떤 차이가 있는지 구별할 수 있어야 한다.

- name : DI에 사용할 수정자 메소드의 프로퍼티 이름
- ref : 주입할 오브젝트를 정의한 빈의 ID다.

보통 프로퍼티 이름과 DI되는 빈의 이름이 같은 경우가 많다.
프로퍼티 이름은 주입할 빈 오브젝트의 인터페이스를 따르는 경우가 많고, 빈 이름도 역시 인터페이스 이름을 사용하는 경우가 많기 때문이다.

때로는 같은 인터페이스를 구현한 의존 오브젝트를 여러 개 정의해두고 그중에서 원하는 걸 골라서 DI하는 경우도 있다.
이때는 각 빈의 이름을 독립적으로 만들어두고 ref애트리뷰트를 이용해 DI 받을 빈을 지정해주면 된다.

```xml
<beans>
   <bean id="localDBConnectionMaker" class="package...LocalDBConnectionMaker" />
   <bean id="testDBConnectionMaker" class="package...TestDBConnectionMaker" />
   <bean id="productionDBConnectionMaker" class="package...ProductionDBConnectionMaker" />

   <bean id="userDao" class="springbook.user.dao.UserDao">
   	<property name="connectionMaker" ref="localDBConnectionMaker" />
   </bean>
</beans>
```


#### XML을 이용하는 애플리케이션 컨텍스트

애플리케이션 컨텍스트가 DaoFactory 대신 XML 설정정보를 활용하도록 만들어보자
XML에서 빈의 의존관계 정보를 이용하는 IoC/DI 작업에는 GenericXmlApplicationContext를 사용한다.
GenericXmlApplicationContext의 생성자 파라미터로 XML 파일의 클래스패스를 지정해주면 된다.
이 설정파일은 클래스패스 최상단에 두면 편하다.

애플리케이션 컨텍스트가 사용하는 XML 설정파일의 이름은 관례를 따라 applicationContext.xml 이라고 만든다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
	<bean id="myConnectionMaker" class="springbook.user.dao.DConnectionMaker">
		<property name="driverClass" value="com.mysql.jdbc.Driver" />
		<property name="url" value="jdbc:mysql://localhost/springbook?characterEncoding=UTF-8" />
		<property name="username" value="spring" />
		<property name="password" value="book" />
	</bean>

	<bean id="userDao" class="springbook.user.dao.UserDao">
		<property name="connectionMaker" ref="myConnectionMaker" />
	</bean>
</beans>
```

xml 설정정보를 담은 applicationContext.xml 파일을 생성했으니 UserDaoTest의 애플리케이션 컨텍스트 생성부분을 수정해줘야 한다.
DaoFactory를 설정정보로 사용했을 때 썻던 AnnotationConfigApplicationContext 대신 GenericXmlApplicationContext를 이용해 다음과 같이 애플리케이션 컨텍스트를 생성하게 만든다.
이때 생성자에는 applicationContext.xml의 클래스패스를 넣는다.

> `/`는 넣을 수도 있고 생략할 수도 있는데 시작하는 `/`가 없는 경우에도 항상 루트에서부터 시작하는 클래스패스라는 점을 기억해두자

```java
ApplicationContext context = new GenericXmlApplicationContext("/applicationContext.xml");
```

위처럼 GenericXmlApplicationContext 외에도 ClassPathXmlApplicationContext 를 이용해 XML로부터 설정정보를 가져오는 애플리케이션 컨텍스트를 만들 수 있다.

GenericXmlApplicationContext 는 클래스패스뿐 아니라 다양한 소스로부터 설정파일을 읽어올 수 있다.
위에서 언급한 ClassPathXmlApplicationContext는 XML파일을 클래스패스에서 가져올 때 사용할 수 있는 편리한 기능이 추가된 것이다.

또한 클래스패스의 경로정보를 클래스에서 가져오게 하는 것이 있다.
예를 들어 springbook.user.doa 패키지 안에 daoContext.xml 이라는 설정파일을 만들었다고 해보자.
GenericXmlApplicationContext가 이 XML 설정파일을 사용하게 하려면 클래스패스 루트로부터 파일의 위치를 지정해야 하므로 다음과 같이 작성해야 한다.

```java
new GenericXmlApplicationContext("springbook/user/doa/daoContext.xml");
```

반면에 ClassPathXmlApplicationContext는 XML 파일와 같은 클래스패스에 있는 클래스 오브젝트를 넘겨서 클래스패스에 대한 힌트를 제공할 수 있다.
UserDao는 springbook.user.dao 패키지에 있으므로 daoContext.xml과 같은 클래스패스 위에 있다.
이 UserDao를 함께 넣어주면 XML 파일의 위치를 UserDao의 위치로부터 상대적으로 지정할 수 있다.

```java
new ClassPathXmlApplicationContext("daoContext.xml", UserDao.class);
```

이 방법으로 클래스패스를 지정해야 할 경우가 아니라면 GenericXmlApplicationContext를 사용하는 편이 무난하다.

#### DataSource 인터페이스로 변환

##### DataSource 인터페이스 적용

ConnectionMaker 는 DB 커넥션을 생성해주는 기능 하나만을 정의한 매우 단순한 인터페이스다.
IoC와 DI의 개념을 설명하기 위해 직접 이 인터페이스를 정의하고 사용했지만, 사실 자바에서는 DB커넥션을 가져오는 오브젝트의 기능을 추상화해서 비슷한 용도로 사용할 수 있게 만들어진 DataSource라는 인터페이스가 이미 존재한다.

단, DataSource는 getConnection()이라는 DB커넥션을 가져오는 기능 외에도 여러 개의 메소드를 갖고 있어서 인터페이스를 직접 구현하기는 부담스럽다.

사실 일반적으로 DataSource를 구현해서 DB커넥션을 제공하는 클래스를 만들지는 않고, 이미 다양한 방법으로 DB연결과 풀링 기능을 갖춘 많은 DataSource 구현 클래스가 존재하고, 이를 가져다 사용하면 충분하다.

> DataSource 인터페이스와 다양한 DataSource 구현 클래스를 사용할 수 있도록 UserDao를 리팩토링해보자

- UserDao에 주입될 의존 오브젝트의 타입을 ConnectionMaker에서 DataSource로 변경한다.
- DB커넥션을 가져오는 코드를 makeConnection()에서 getConnection() 메소드로 바꿔준다.

```java
public class UserDao {
	private DataSource dataSource;
   
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();
      ...
   }
```

다음으로 DataSource 구현 클래스가 필요하다

##### 자바 코드 설정 방식

먼저 DaoFactory 설정 방식을 이용해보자. 
- 기존의 connectionMaker() 메소드를 dataSource()로 변경하고, SimpleDriverDataSource 의 오브젝트를 리턴하게 한다.
- 이 오브젝트를 넘기기 전에 DB연결과 관련된 정보를 수정자 메소드를 이용해 지정해줘야 한다.

> DataSource 타입의 dataSource 빈 정의 메소드

```java
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource ();

		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost/springbook?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");

		return dataSource;
	}
```

또한 DaoFactory의 userDao()메소드를 UserDao는 이제 DataSource 타입의 dataSource()를 DI 받도록 수정하자

```java
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}
```

이렇게 해서 UserDao에 DataSource 인터페이스를 적용하고 SimpleDriverDataSource 오브젝트를 DI로 주입해서 사용할 수 있는 준비가 끝났다.

이제 UserDaoTest 를 DaoFactory를 사용하도록 수정하고 테스트를 실행해보자


##### XML 설정방식
이번에는 XML 설정 방식으로 변경해보자.

- id가 connectionMaker인 `<bean>`을 없애고 dataSource라는 이름의 `<bean>`을 등록한다.
- 클래스를 SimpleDriverDataSource로 변경해준다.

```xml
<bean id="dataSource" class="org.springframework.jdbc.datasource.SimpleDriverDataSource" />
```

그런데 문제는 이 `<bean>`설정으로 `SimpleDriverDataSource`의 오브젝트를 만드는 것까지는 가능하지만, 
dataSource()메소드에서 SimpleDriverDataSource 오브젝트의 수정자로 넣어준 DB접속정보는 나타나 있지 않다는 점이다.

UserDao처럼 다른 빈에 의존하는 경우에는 `<property>`태그와 ref 애트리뷰트로 의존할 빈 이름을 넣어주면 된다.
하지만 SimpleDriverDataSource 오브젝트의 경우는 단순 Class 타입의 오브젝트나 텍스트 값이다.

> 그렇다면 XML에서는 어떻게 해서 dataSource()메소드에서처럼 DB연결정보를 넣도록 설정을 만들 수 있을까?

#### 프로퍼티 값의 주입

##### 값 주입
이미 DaoFactory의 dataSource()메소드에서 본 것처럼, 수정자 메소드에는 다른 빈이나 오브젝트뿐 아니라 스트링 같은 단순 값을 넣어줄 수도 있다.
setDriverClass()메소드의 경우에는 Class 타입의 오브젝트이긴 하지만 다른 빈 오브젝트를 DI방식으로 가져와서 넣는 것은 아니다.

> 이렇게 다른 빈 오브젝트의 레퍼런스가 아닌 단순 정보도 오브젝트를 초기화하는 과정에서 수정자 메소드에 넣을 수 있다.

이때는 DI에서처럼 오브젝트의 구현 클래스를 다이나믹하게 바꿀 수 있게 해주는 것이 목적은 아니다.
대신 클래스 외부에서 DB연결정보와 같이 변경가능한 정보를 설정해줄 수 있도록 만들기 위해서다.
예를 들어 DB접속 아이디가 바뀌었더라도 클래스 코드는 수정해줄 필요가 없게 해주는 것이다.

**텍스트나 단순 오브젝트 등을 수정자 메소드에 넣어주는 것을 스프링에서는 '값을 주입한다'**고 말한다.
이것도 성격은 다르지만 일종의 DI라고 볼 수 있다.

스프링의 빈으로 등록될 클래스에 수정자 메소드가 정의되어있다면 `<property>`를 사용해 주입할 정보를 지정할 수 있다는 점에서는
`<property ref="">`와 동일하다.
> 하지만 **다른 빈 오브젝트의 `레퍼런스(ref)`가 아니라 단순 `값(value)`을 주입해주는 것**이기 때문에 ref애트리뷰트 대신 value애트리뷰트를 사용한다.

따라서 dataSource()메소드에서 다음과 같이 수정자 메소드를 호출해서 DB연결정보를 넣었던 아래의 코드는

```java
dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
dataSource.setUrl("jdbc:mysql://localhost/springbook?characterEncoding=UTF-8");
dataSource.setUsername("root");
dataSource.setPassword("1234");
```

아래처럼 XML로 전환할 수 있다.

```xml
<property name="driverClass" ref="com.mysql.jdbc.Driver" />
<property name="url" ref="jdbc:mysql://localhost/springbook" />
<property name="username" ref="root" />
<property name="password" ref="1234" />
```

`ref` 대신에 `value`를 사용했을 뿐이지 기존의 `<property>`태그를 사용했던 것과 내용과 방법은 동일하다.
주의할점은 `value`에 들어가는 것은 다른 빈의 이름이 아니라 실제로 수정자 메소드의 파라미터로 전달되는 스트링 그 자체다


##### value값의 자동 변환

그런데 한 가지 이상한 점이 있다. url, username, password는 모두 스트링 타입이니 원래 텍스트로 정의되는 value애트리뷰트의 값을 사용하는 것은 문제 없다.
그런데 `driverClass` 는 스트링 타입이 아니라 `java.lang.Class`타입이다.
`DaoFactory`에 적용한 예를 생각해보면 Driver.class라는 `Class` 타입 오브젝트를 전달한다.

```java
dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
```
그런데 XML에서는 별다른 타입정보 없이 클래스의 이름이 텍스트 형태로 `value`에 들어가 있다.
하지만 테스트를 해보면 문제 없이 잘 작동한다.

> 이런 설정이 가능한 이유는 스프링이 프로퍼티의 값을, 수정자 메소드의 파라미터 타입을 참고로 해서 적절한 형태로 변환해주기 때문이다.
> `setDriverClass()`메소드의 파라미터 타입이 `Class`임을 확인하고 텍스트값을 `class`오브젝트로 자동 변경해주는 것이다.

즉 내부적으로 다음과 같이 변환 작업이 일어난다고 생각하면 된다.

```java
Class driverClass = Class.forName("com.mysql.jdbc.Driver");
dataSource.setDriverClass(driverClass);
```

> 이처럼 스프링은 `value`에 지정한 텍스트값을 적절한 자바 타입으로 변환해준다.

최종적으로 수정된 `applicationContext.xml`은 다음과 같다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
	
	<bean id="dataSource" class="org.springframework.jdbc.datasource.SimpleDriverDataSource" >
		<property name="driverClass" ref="com.mysql.jdbc.Driver" />
		<property name="url" ref="jdbc:mysql://localhost/springbook" />
		<property name="username" ref="root" />
		<property name="password" ref="1234" />
	</bean>

	<bean id="userDao" class="springbook.user.dao.UserDao">
		<property name="connectionMaker" ref="myConnectionMaker" />
	</bean>
</beans>
```

---

### 정리

지금까지 사용자 정보를 DB에 등록하거나 아이도로 조회하는 기능을 가진 간단한 DAO코드를 만들고, 그 코드의 문제점을 살펴본 뒤,
이를 다양한 방법과 패턴, 원칙, IoC/DI 프레임워크까지 적용해서 개선해왔다.
그 과정을 돌아보자

- 먼저 책임이 다른 코드를 분리해서 두 개의 클래스로 만들었다`(관심사의 분리, 리팩토링)`

- 그중에서 바뀔 수 있는 쪽의 클래스는 인터페이스를 구현하도록 하고, 다른 클래스에서 인터페이스를 통해서만 접근하도록 만들었다.
이렇게 해서 인터페이스를 정의한 쪽의 구현방법이 달라져 클래스가 바뀌더라도, 그 기능을 사용하는 클래스의 코드는 같이 수정할 필요가 없도록 만들었다`(전략 패턴)`

- 이를 통해 자신의 책임 자체가 변경되는 경우 외에는 불필요한 변화가 발생하지 않도록 막아주고, 자신이 사용하는 외부 오브젝트의 기능은 자유롭게 확장하거나 변경할 수 있께 만들었다`(개방폐쇄원칙)`

- 결국 한쪽의 기능변화가 다른 쪽의 변경을 요구하지 않아도 되게 했고`(낮은 결합도)`, 자신의 책임과 관심사에만 순수하게 집중하는`(높은 응집도)` 깔끔한 코드를 만들 수 있었다.

- 오브젝트가 생성되고 여타 오브젝트와 관계를 맺는 작업의 제어권을 별도의 오브젝트 팩토리를 만들어 넘겼다. 또는 오브젝트 팩토리의 기능을 일반화한 IoC컨테이너로 넘겨서 오브젝트가 자신이 사용할 대상의 생성이나 선택에 관한 책임으로부터 자유롭게 만들어 줬다`(제어의 역전/IoC)`

- 전통적인 싱글톤 패턴 구현 방식의 단점을 살펴보고, 서버에서 사용되는 서비스 오브젝트로서의 장점을 살릴 수 있는 싱글톤을 사용하면서도 싱글톤 패턴의 단점을 극복할 수 있도록 설계된 컨테이너를 활용하는 방법에 대해 알아봤다`(싱글톤 레지스트리)`

- 설계 시점과 코드에는 클래스와 인터페이스 사이의 느슨한 의존관계만 만들어넣고, 런타임 시에 실제 사용할 구체적인 의존 오브젝트를 제3자(DI컨테이너)의 도움으로 주입받아서 다이나믹한 의존관계를 갖게 해주는 IoC의 특별한 케이스를 알아봤다`(의존관계 주입/DI)`

- 의존 오브젝트를 주입할 때 생성자를 이용하는 방법과 수정자 메소드를 이용하는 방법을 알아봤다(생성자 주입과 수정자 주입)

- 마지막으로 XML을 이용해 DI설정정보를 만드는 방법과 의존 오브젝트가 아닌 일반 값을 외부에서 설정해서 런타임 시에 주입하는 방법을 알아봤다(XML 설정)







