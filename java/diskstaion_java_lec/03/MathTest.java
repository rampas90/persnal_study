/*
	java.lang.Math 클래스의 활용

1>	public static int max(int a, int b)
	: 매개변수로 받아들인 정수값 중 최대값을 구해 돌려준다.

2> public static int min(int a, int b)
	: 최소값을 돌려줌.

3> public static double ceil(double d)
	: d보다 크거나 같은 정수 중 가장 작은 값을 돌려준다. -> 천정값

4> public static double floor(double d)
	: d보다 작거나 같은 정수 중 가장 작은 값을 돌려준다. -> 마루값

5> public static pow(double x, double y)
	: x의 y승을 돌려줌

6> public static sqrt(double d)
	: d의 제곱근을 반환

7> public static random()
	: 임의의 난수를 반환한다. 0.0 <= 임의의 난수 <1.0

*/

// out.println 으로 사용간으하게 해줌
import static java.lang.System.out;
public class MathTest
{

	public static void main(String[] args) 
	{		

		/*
		int mx=Math.max(-5,-8);
		out.println("-8과 -5의 최대값 : "+mx);

		int mn=Math.min(-5,-8);
		out.println("-8과 -5의 최소값 : "+mn);

		double mx2=Math.max(1.23,3.45);
		out.println("1.23과 3.45의 최대값 : "+mx2);

		out.println("5.67의 ceil : "+Math.ceil(5.67));

		out.println("5.67의 floor : "+Math.floor(5.67));

		out.println("3의 7 : "+Math.pow(3,7));

		out.println("Math.PI : "+Math.PI);
		
			
		*/
		/*
			Math.random()*범위(개수) + 시작되는 숫자

		*/
		// 3<=r4 <11
		int r4=(int)(Math.random()*8+3);
		out.println("r4 = "+r4);

		// 
		int r5=(int)(Math.random()*13+16);
		out.println("r5 = "+r5);

		// 알파벳 대문자를 무작위로 추출하여 출력
		/*
            | <- 바깥쪽 루프
				E E S W E - 안쪽 루프
				E V H F W
				H R F D E

				3 * 5
		*/
		for(int i=0;i<3;i++)
		{
			for (int k=0;k<5 ;k++ )
			{	
				//문자는 아스키 코드값 혹은 문자열
				char ch = (char)(Math.random()*26+'A');
				out.print(ch+" ");
			}
			out.println();
		}


	}
}
