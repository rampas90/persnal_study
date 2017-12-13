public class ControlOfFlow{
	// 중간 생략

	public void switchStatatement(int numberOfWheel)
	{
		switch(numberOfWheel){
			case 1:
				System.out.println("It is one foot bicycle");
				//break;
			case 2:
				System.out.println("It is a motor cycle or bicycle");
				//break;
			case 3: 
				System.out.println("It is a Three whell car.");
				break;
			case 4: 
				System.out.println("It is a Three whell car.");
				break;
			/*
				이하 케이스 생략
			*/
		}
	}
	public static void main(String[] args) {
		
	}
}