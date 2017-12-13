// ��Ű�� ���� �� import �� ���� ����� ������ �⺻������ �ַ� lang��Ű���� ����ϰ�
// lang ��Ű���� ��� �ڵ����� �����Ǳ⶧���� �����ص� �ȴ�.
/*

 ����(variable, field, property, parameter)
	1. Ŭ���� ���� : static ����  ->  �޼ҵ� �ٱ����� ����� ����
	2. ������� : instance ����    ->  �޼ҵ� �ٱ����� ����� ����
	3. �������� : local ����, automatic����  ->   �޼ҵ� �� ��, �Ǵ� �ν��Ͻ� ���ȿ��� ����� ����
	              
*/

public class VarTest
{

          int a = 10; // �������, ��Ʈ�Ͻ� ���� -> static�� �ȵ� ����

	static int b = 20; // Ŭ���� ����, static����

	
	public VarTest()
	{
		int c = 30;	// ��������, ���ú���
		System.out.println("VarTest()������");
		System.out.println("�������� c: "+c);
	}

	public static void main(String[] args)
	{
		//1. Ŭ�������� b�� ���� ����غ���
		// Ŭ������.����
		System.out.println("Ŭ�������� b : "+VarTest.b);

		//2. ������� a�� ���� ����غ���
		//   ���� ��ü�� �����ѵ�, ��ü��.����
		VarTest v = new VarTest();
		System.out.println("������� a : " + v.a);

		//3. �������� c�� ���� ����غ���
		//System.out.println("�������� c: "+ v.c);
		//���������� ����� �� �ȿ����� ��� ����
		// ���������� ����Ϸ��� ������ �ȿ��� ����ؾ� ��
		int c = 300;

		System.out.println("�������� c : "+ c);
	
	}
}
