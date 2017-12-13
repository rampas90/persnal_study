public class Animal
{

	String name;
	String kind;
	int legCount;
	int iq;
	boolean hasWing;
	
	public void move(){
		System.out.println("움직였다.");
	}

	public void eatFood(){
		System.out.println("음식을 먹었다.");
	}

	public static void main(String[] args) 
	{
		Animal a = new Animal();
		Animal ad = new Dog();
		Animal ac = new Cat();
		
		System.out.println("=======Animal=======");
		a.move();
		a.eatFood();

		System.out.println("=======Dog=======");
		ad.move();
		ad.eatFood();

		System.out.println("=======Cat=======");
		ac.move();
		ac.eatFood();
	}
}

class Dog extends Animal
{
	int age;
	String color;

	public void move(){
		System.out.println("개가 움직였다.");
	}

	public void eatFood(){
		System.out.println("개가 사료를 먹었다.");
	}
}

class Cat extends Animal
{
	int jumpDistance;

	public void move(){
		System.out.println("고양이가 움직였다.");
	}

	public void eatFood(){
		System.out.println("고양이가 우유를 먹었다.");
	}
}

