public class ControlOfFlow2
{
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

	public void switchStatement2(int month)
	{
		switch(month)
		{

			case 1:
			case 3: 
			case 5: 
			case 7:
			case 8:
			case 10:
			case 12:
				System.out.println(month + " has 31 days." );
				break;
			case 4:
			case 6:
			/*	
				이하 생략
				각각 30일 , 28 || 29
				그리고 기본값 코드 작성이다.
			*/
		}
	}

	public void whileLoop()
	{
		int loop = 0;

		while(loop<12)
		{
			loop++;
			swtichStatement2(loop);
			if(loop == 6) break;

		}
	}

	// do while 
	/*
		do~ while 문은 적어도 한번은 꼭 실행된다.
		아래 조건문은 whileLoop 와 동일한 로직이다.
	*/
	public void whileLoop2()
	{
		int loop=0;
		do
		{
			loop++;
			switchStatement2(loop);

		}
		while(loop<12);  // 세미콜론을 반드시 찍어줘야한다.
	}
}