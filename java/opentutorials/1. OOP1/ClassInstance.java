public class CalculatorDemo {
 
	// 클래스와 인스턴스 이전의 프로그래밍
	// 아래 로직이 아주 복잡한 로직일 경우 중복되는 값을 없애기 위해 고안된 방식

   public static void main(String[] args) {
		// 아래의 로직이 1000줄 짜리의 복잡한 로직이라고 가정하자.
      System.out.println(10 + 20);
      System.out.println(20 + 40);
   }

}

// 메소드화된 소스 -> refactoring
public class CalculatorDemo2 {

	public static void sum(int left, int right) {
		System.out.println(left + right);
	}
 
	public static void main(String[] args) {
		sum(10, 20);
		sum(20, 40);
	}
 
}//CalculatorDemo2


	
// 메소드가 많아지면서 생기는 문제???
public class ClaculatorDemo3 {
 
	 public static void avg(int left, int right) {
		  System.out.println((left + right) / 2);
	 }
 
	 public static void sum(int left, int right) {
		  System.out.println(left + right);
	 }
 
	 public static void main(String[] args) {
		int left, right;

		// 변수를 통해 아래 더하기, 평균값들에 들어갈 값들의 중복을 없앰
		left = 10;
		right = 20;
		
		/*
			중간에다른 로직이 들어가고 
			위의 변수에 다른 값이 들어가거나,
			아래 sum, avg 등의 메소드를 동일한 이름으로 사용할 경우
			등등 로직이 커지고 코드의 양이 많아질수록 예측할수 없는 요소들이 발생할 상황이
			점점 많아지게 된다.

			그래서 고안한 내용들은?

			1. 각 변수명들의 역할을 미리 지정한다 
				ex)  left = 왼쪽
			2. 계산기의 left값은 cal_ 접두어를 붙여 cal_left를 사용한다
			3. 연관되어있는 로직을 같은 위치로 모은다

			=> 서로 연관되어있는 변수와 메소드(데이터와 연산을) 를 그룹핑을 해볼까? => 객채지향


		*/
		sum(left, right);
		avg(left, right);



		left = 20;
		right = 40;

		sum(left, right);
		avg(left, right);
		/*
			left,right <=> sum, avg 를 묶는다
			경우에따라서는 sum, avg를 선택하여(골라서) 작업할 수 있도록 
		*/

	 }
 
}//ClaculatorDemo3
 



/*
	계산기라는 객체를 만들고 싶다
	=> left, right 값에 따라서 더하고, 평균값을 내는 객체



*/

class Calculator{
    int left, right;
      
    public void setOprands(int left, int right){
        this.left = left;
        this.right = right;
    }
      
    public void sum(){
        System.out.println(this.left+this.right);
    }
      
    public void avg(){
        System.out.println((this.left+this.right)/2);
    }
}
  
public class CalculatorDemo4 {
      
	public static void main(String[] args) {
		
		// 객체를 호출하여 c1이라는 변수에 담고 그 데이터 형은 객체의 이름이다.
		// 객체가 담겨있는 c1을 인스턴스라고 한다
		Calculator c1 = new Calculator();

		// 객체의 메소드를 호출
		c1.setOprands(10, 20);
		c1.sum();       
		c1.avg();       

		Calculator c2 = new Calculator();
		c2.setOprands(20, 40);
		c2.sum();       
		c2.avg();
	}
  
}
