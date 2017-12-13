/*
	java.lang.Math Ŭ������ Ȱ��

1>	public static int max(int a, int b)
	: �Ű������� �޾Ƶ��� ������ �� �ִ밪�� ���� �����ش�.

2> public static int min(int a, int b)
	: �ּҰ��� ������.

3> public static double ceil(double d)
	: d���� ũ�ų� ���� ���� �� ���� ���� ���� �����ش�. -> õ����

4> public static double floor(double d)
	: d���� �۰ų� ���� ���� �� ���� ���� ���� �����ش�. -> ���簪

5> public static pow(double x, double y)
	: x�� y���� ������

6> public static sqrt(double d)
	: d�� �������� ��ȯ

7> public static random()
	: ������ ������ ��ȯ�Ѵ�. 0.0 <= ������ ���� <1.0

*/

// out.println ���� ��밣���ϰ� ����
import static java.lang.System.out;
public class MathTest
{

	public static void main(String[] args) 
	{		

		/*
		int mx=Math.max(-5,-8);
		out.println("-8�� -5�� �ִ밪 : "+mx);

		int mn=Math.min(-5,-8);
		out.println("-8�� -5�� �ּҰ� : "+mn);

		double mx2=Math.max(1.23,3.45);
		out.println("1.23�� 3.45�� �ִ밪 : "+mx2);

		out.println("5.67�� ceil : "+Math.ceil(5.67));

		out.println("5.67�� floor : "+Math.floor(5.67));

		out.println("3�� 7 : "+Math.pow(3,7));

		out.println("Math.PI : "+Math.PI);
		
			
		*/
		/*
			Math.random()*����(����) + ���۵Ǵ� ����

		*/
		// 3<=r4 <11
		int r4=(int)(Math.random()*8+3);
		out.println("r4 = "+r4);

		// 
		int r5=(int)(Math.random()*13+16);
		out.println("r5 = "+r5);

		// ���ĺ� �빮�ڸ� �������� �����Ͽ� ���
		/*
            | <- �ٱ��� ����
				E E S W E - ���� ����
				E V H F W
				H R F D E

				3 * 5
		*/
		for(int i=0;i<3;i++)
		{
			for (int k=0;k<5 ;k++ )
			{	
				//���ڴ� �ƽ�Ű �ڵ尪 Ȥ�� ���ڿ�
				char ch = (char)(Math.random()*26+'A');
				out.print(ch+" ");
			}
			out.println();
		}


	}
}
