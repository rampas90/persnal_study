// system.in.read()
// 위 메소드는 int 타입이다.
import java.io.*;

public class WhileTest1
{
	public static void main(String[] args) 
		throws IOException
	{
		/*
		while(true){
			
			char ch = (char)System.in.read();
			// 개행 인트값 확인용
			//int ch = System.in.read();
			//System.out.println(ch);

			//2바이트 건너뜀
			// 즉 엔터값(\r\n)은 입력받지 않음.
			System.in.skip(2);			
			System.out.println("ch = "+ch);

			// 보조제어문 , 반복문 이탈..
			if(ch=='x'||ch=='X')
				break;
			// r = 13 n = 10 < 엔터값

			


		}
		*/

		/* 
			****		3행 4열
			****
			****

		*/

		int i=1;
		while(i<=3){
			int k=1;
			while(k<=4){
				System.out.print("*");
				k++;
			}
			System.out.println();
			i++;
		}


		/*
			
			do{

				반복 실행 문장

			}while(조건문);

		*/


	}
}
