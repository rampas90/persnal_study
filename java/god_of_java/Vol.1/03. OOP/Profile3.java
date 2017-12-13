public class Profile3
	{
		
		String name ;
		int age;

		public void setName(String str)
		{
			name = str;
		}	

		public void setAge(int val)
		{
			age = val;
		}

		public void printName()
		{
			System.out.println("name 값은 : " + name);
		}
		
		public void printAge()
		{
			System.out.println("age 값은 : " + age);
		}


		public static void main(String[] args)
		{
			String name = "shin";
			int age = 29;

			System.out.println("My name is " + name);
			System.out.println("My age is "+ age);

			Profile3 p = new Profile3();
			p.setName("Min");
			p.setAge(20);
			p.printName();
			p.printAge();

		}
	}