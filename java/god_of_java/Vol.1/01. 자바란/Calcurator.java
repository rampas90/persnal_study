public class Calcurator
{
	public int add(int num1, int num2)
	{
		int sum;
		sum = num1 + num2;
		return sum;
	}

	public int addAll(int num1, int num2, int num3)
	{
		int sum;
		sum = num1+num2+num3;
		return sum;
	}

	public int subtract(int num1, int num2)
	{
		int subt;
		subt = num1-num2;
		return subt;
	}

	public int multiply(int num1, int num2)
	{
		int mul;
		mul = num1*num2;
		return mul;
	}

	public int divide(int num1, int num2)
	{
		int divi;
		divi = num1/num2;
		return divi;
	}

	public static void main(String[] args)
	{	
		System.out.println("Calcurator class start");

	}
}