## 클래스 패스

IDE를 사용하지 않을 경우 (이클립스가 자동으로 클래스 위치및 임시 인스턴스 서버배치를 해주지 못할 상황..)


> `/srcbin` 이라는 디렉토리에 `ClasspathDemo.java` 파일을 아래와 같이 생성한 후 컴파일 해보자

```java
class Item{
}
 
class ClasspathDemo {
}
```

> 아래와 같이 두개의 클래스 파일이 생성되는걸 알수 있다.

- ClasspathDemo.class
- Item.class


> 이번엔 `ClasspathDemo2.java`파일을 작성 후 컴파일 해보자

```java
class Item2{
    public void print(){
        System.out.println("Hello world");  
    }
}
 
class ClasspathDemo2 {
    public static void main(String[] args){
        Item2 i1 = new Item2();
        i1.print();
    }
}
```

> 두개의 클래스가 정상적으로 생성됬다면 현재 디렉토리`(/srcbin)`하위에 `lib`을  만들고
> 여기로 `Item2.class` 파일을 이동한 후  `ClasspathDemo2`를 실행해보자


```
Exception in thread "main" java.lang.NoClassDefFoundError: Item2
        at ClasspathDemo2.main(ClasspathDemo2.java:9)
Caused by: java.lang.ClassNotFoundException: Item2
        at java.net.URLClassLoader$1.run(Unknown Source)
        at java.net.URLClassLoader$1.run(Unknown Source)
        at java.security.AccessController.doPrivileged(Native Method)
        at java.net.URLClassLoader.findClass(Unknown Source)
        at java.lang.ClassLoader.loadClass(Unknown Source)
        at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
        at java.lang.ClassLoader.loadClass(Unknown Source)
        ... 1 more
```

> item.class 파일이 현재 디렉토리에 존재하지 않기 때문에 찾을 수 없다는 에러메시지를 확인할 수 있다.
> 바로 이때 사용하는 것이 클래스 패쓰다..아래와 같이 실행해보자..

```
java -classpath ".;lib" ClasspathDemo2
```
> 리눅스 혹은 유닉스 계열이라면 아래와 같이 콜론을 사용해야 한다

```
java -classpath ".:lib" ClasspathDemo2
```

> 옵션 `-classpath`는 자바를 실행할 때 사용할 클래스들의 위치를 가상머신에게 알려주는 역할을 한다. -`classpath`의 값으로 사용된 ".;lib"를 살펴보자.