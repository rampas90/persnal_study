	

	public class VariableTypes
	{
		int instanceVariable;
		static int classVariable;

		public void method(int parameter)
		{
			int localVariable;
		}

	}


	1.자바의변수
		1) 지역변수 ( local )
		   - 중괄호 내에서 선언된 변수
			- 지역 변수를 선언한 중괄호 내에서만 유효하다.



		2) 매개변수 ( parameters )
		   - 메소드나 생성자에 넘겨주는 변수
			- 메소드가 호출될 때 생명이 시작되고, 메소드가 끝나면 소멸된다(정확히 호출될때 시작하지는 않지만 일단은 이정도로기억해두자)


		3) 인스턴스 변수 ( instance )
		   - 메소드 밖에 , 클래스 안에 선언된 변수, 앞에는 static이라는 예약어가 없어야 한다.
			- 객체가 생성될 때 생명이 시작되고, 그 객체를 참조하고 있는 다른 객체가 없으면 소멸된다.

		4) 클래스 변수 ( class )
		   - 인스턴스 변수처럼 메소드 밖에, 클래스 안에 선언된 변수 중에서 타입 선언앞에 static 이라는 예약어가 있는 변수
			- 클래스가 생성될 때 생명이 시작되고 , 자바 프로그램이 끝날 때 소멸된다. 


	
	
/* 생각해보기 

	아래 두개의 지역변수는 서로 같은 변수일까?
*/ 


	public class VariableTypes
	{
		int instanceVariable;
		static int classVariable;

		public void method(int parameter)
		{
			int localVariable;
		}

		public void anothermethod(int parameter)
		{
			int localVariable;
		}
	}

/* 생각해보기 

	아래 1번과 2번의 의 지역변수는 서로 같은 변수일까?

	아래는 컴파일 에러가 난다 그이유는 글 작성시점에선 알고있지만 나중에 봤을때 생각이 안난다면 p.87 참조
*/ 


	public class VariableTypes
	{
		int instanceVariable;
		static int classVariable;

		public void method(int parameter)
		{
			int localVariable;
		}

		public void anothermethod(int parameter)
		{
			if(true){
				int localVariable;   //1
				if(true){
					int localVariable;//2
				}
			}							   //3
			
			if(true){
				int localVariable;
			}
		}
	}



	2. 자바의 자료형

		1) 기본자료형 (Primitive Data Type)
			- 추가로 만들 수 없다. 
			- 이미 정해져 있다
			- int a = 10;
			- new 없이 바로 초기화가 가능한것을 기본자료형

		2) 참조자료형 (Reference Data Type)
			- 마음대로 만들 수 있다. ex) Calculator, Car 같은 클래스들
			- Calculator calc = new Calculator();
			- new를 사용해서 초기화하는 것을 참조자료형
			- String의 경우는 참조자료형

			  일반적으로 아래와 같이 사용하여 기본자료형이라고 생각할 수 있지만.!!
			  String str1 = "book1";
				
			  다음과 같이 정의해도 상관없다.
			  String str2 = new String("book2");



