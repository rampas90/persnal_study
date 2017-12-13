public class Hello
{
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
		System.out.print("Hello Java");
		System.out.print("The End \n Bye");
	}
}
/*
1. 소스작성 -> Hello.java : 원시코드 (클래스 명과 파일명을 동일하게 작성 )
2. 컴파일 -> javac 클래스명(Hello)   

*/

/*

자바의 특징
1. 플랫폼 독림성 : JVM(자바가상머신)이 해당 플랫폼마다 제공되어져, 
                   이를 설치하면 어떤 운영체제에서 작성된 자바 파일이든지
				   동일한 실행을 제공한다.
2.객체 지향언어 : 재사용성, 유연성, 프로그램 생산성 향상
3.멀티 스레드 지원: Thread 는 Process보다 작은 단위로 동시 다발적으로 작업 수행이 가능
4.자동 메모리 관리 - Garbage Collector(쓰레기 수집기)
5.동적인 성능 확장 제공 : Applet

자바 프로그램 종류
1. Application : 독립적인 실행 프로그램
                 main() 메소드를 가짐
				 --> Hello.java
				 콘솔 어플리케이션
				 윈도우 어플리케이션 - GUI 환경을 가짐

2. Applet : 비독립적 프로그램
            웹문서(html)에 포함되어 실행되는 프로그램
			main()메소드는 필요 없다
			웹브라우저가 가지고 있는 JVM에 의해 실행된다.
			동적인 성능

*/