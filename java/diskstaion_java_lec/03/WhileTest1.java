// system.in.read()
// �� �޼ҵ�� int Ÿ���̴�.
import java.io.*;

public class WhileTest1
{
	public static void main(String[] args) 
		throws IOException
	{
		/*
		while(true){
			
			char ch = (char)System.in.read();
			// ���� ��Ʈ�� Ȯ�ο�
			//int ch = System.in.read();
			//System.out.println(ch);

			//2����Ʈ �ǳʶ�
			// �� ���Ͱ�(\r\n)�� �Է¹��� ����.
			System.in.skip(2);			
			System.out.println("ch = "+ch);

			// ������� , �ݺ��� ��Ż..
			if(ch=='x'||ch=='X')
				break;
			// r = 13 n = 10 < ���Ͱ�

			


		}
		*/

		/* 
			****		3�� 4��
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

				�ݺ� ���� ����

			}while(���ǹ�);

		*/


	}
}
