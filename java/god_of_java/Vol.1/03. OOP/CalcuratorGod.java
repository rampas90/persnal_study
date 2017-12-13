public class CalcuratorGod
{
	public int add(int num1, int num2)
	{
		/*
		int sum;
		sum = num1 + num2;
		return sum;
		*/
		return num1+num2;
	}

	public int addAll(int num1, int num2, int num3)
	{
		/*
		int sum;
		sum = num1+num2+num3;
		return sum;
		*/
		return num1+num2+num3;
	}

	public int subtract(int num1, int num2)
	{
		/*
		int subt;
		subt = num1-num2;
		return subt;
		*/
		return num1-num2;
	}

	public int multiply(int num1, int num2)
	{
		/*
		int mul;
		mul = num1*num2;
		return mul;
		*/
		return num1*num2;
	}

	public int divide(int num1, int num2)
	{
		/*
		int divi;
		divi = num1/num2;
		return divi;
		*/
		return num1/num2;
	}

	public static void main(String[] args)
	{	
		System.out.println("CalcuratorGod class start");

		// 클래스명 객채명 = new Calcurator
		CalcuratorGod calc=new CalcuratorGod();
		int a=10;
		int b=5;
		System.out.println("add : "+calc.add(a,b));
		System.out.println("subtract : "+calc.subtract(a,b));
		System.out.println("multiply : "+calc.multiply(a,b));
		System.out.println("divide : "+calc.divide(a,b));

	}
}