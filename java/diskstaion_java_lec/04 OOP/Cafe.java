public class Cafe
{
	public static void main(String[] args) 
	{
		CoffeeMachine cm = new CoffeeMachine();
		int n = cm.getTea(2,2,3);
		System.out.println("n: "+n);

		n=cm.getTea(1,2);
		System.out.println("n: "+n);

		// Yuja 클래스 꺼내오기 유자=3, 설탕2
		Yuja y = new Yuja();
		y.yuja=3;  // 만약 캡슐화 했다면? y.setYuja(3); 물론 setYuja 메소드를 만들었다고 가정하고
		y.sugar=2;
		cm.getTea(y);

	}
}


class CoffeeMachine
{
	int coffee, sugar, cream;
	
	//메소드 오버로딩

	public int getTea(int cf, int s, int cr)
	{
		coffee=cf;
		sugar=s;
		cream=cr;
		System.out.println("밀크커피." + "농도 : " + (coffee+sugar+cream));
		return (coffee+sugar+cream);
	}

	public int getTea(int cf, int cr)
	{
		coffee=cf;
		cream=cr;
		int r = coffee+cream;
		System.out.println("프림커피." + "농도 : " + r);
		return r;
	}

	public String getTea(int cf, String su)
	{
		System.out.println("설탕커피." + "농도 : " + cf+su);
		return cf+su;
	}

	public void getTea(Yuja y)
	{
		System.out.println("유자 차가 나가요.");
		System.out.println("유자 농도 : "+y.yuja);
		System.out.println("설탕 농도 : "+y.sugar);
		System.out.println("=====================");
		System.out.println("유자차농도 : "+ (y.sugar+y.sugar));
	}
	
}

class Yuja
{
	int yuja;
	int sugar;
}
