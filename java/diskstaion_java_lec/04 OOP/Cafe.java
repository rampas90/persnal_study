public class Cafe
{
	public static void main(String[] args) 
	{
		CoffeeMachine cm = new CoffeeMachine();
		int n = cm.getTea(2,2,3);
		System.out.println("n: "+n);

		n=cm.getTea(1,2);
		System.out.println("n: "+n);

		// Yuja Ŭ���� �������� ����=3, ����2
		Yuja y = new Yuja();
		y.yuja=3;  // ���� ĸ��ȭ �ߴٸ�? y.setYuja(3); ���� setYuja �޼ҵ带 ������ٰ� �����ϰ�
		y.sugar=2;
		cm.getTea(y);

	}
}


class CoffeeMachine
{
	int coffee, sugar, cream;
	
	//�޼ҵ� �����ε�

	public int getTea(int cf, int s, int cr)
	{
		coffee=cf;
		sugar=s;
		cream=cr;
		System.out.println("��ũĿ��." + "�� : " + (coffee+sugar+cream));
		return (coffee+sugar+cream);
	}

	public int getTea(int cf, int cr)
	{
		coffee=cf;
		cream=cr;
		int r = coffee+cream;
		System.out.println("����Ŀ��." + "�� : " + r);
		return r;
	}

	public String getTea(int cf, String su)
	{
		System.out.println("����Ŀ��." + "�� : " + cf+su);
		return cf+su;
	}

	public void getTea(Yuja y)
	{
		System.out.println("���� ���� ������.");
		System.out.println("���� �� : "+y.yuja);
		System.out.println("���� �� : "+y.sugar);
		System.out.println("=====================");
		System.out.println("�������� : "+ (y.sugar+y.sugar));
	}
	
}

class Yuja
{
	int yuja;
	int sugar;
}
