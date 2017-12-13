public class Plus
{
	public static void main(String[] args) 
	{
		Plus pl = new Plus();
		pl.printScore("123");

	}
	public void examFor(){

		int amount1=0;	
		for( int i=0; i<11; i++ ){
			amount1 +=i;
		}
		System.out.println("for = "+amount1);		
	}


	public void examDoWhile(){
		
		int amount2=0;
		int j=1;

		do{
			
			amount2 +=j;
			j++;
		}
		while(j<11);			
		System.out.println("do-while = "+amount2);
	}


	public void examWhile(){
		int k=0;
		int amount3=0;
		while(k<11){
			amount3 += k;
			k++;
		}
		System.out.println("while = "+amount3);
	}

	public void printScore(String score){
		System.out.println("Score = "+score);
	}

}
