public class UseStringMethods
{

	public static void main(String[] args) 
	{
		UseStringMethods usm=new UseStringMethods();
		String text="The String class represents character strings.";
		String fstr="string";
		char c='s';
		String fstr2="ss";

		//usm.printWords(text);
		//usm.findString(text, fstr);
		//usm.findAnyCaseString(text, fstr);
		//usm.countChar(text, c);
		
		usm.printContainWords(text, fstr2);
	}

	public void printWords(String str){
		
		String str2[]=str.split(" ");

		for(String tmp:str2){
			System.out.println(tmp);
		}
	}

	public void findString(String str, String findStr){
		System.out.println("string is appeared at "+str.indexOf(findStr));
	}

	public void findAnyCaseString(String str, String findStr){
		String str2 = str.toLowerCase();
		System.out.println("string is appeared at "+str2.indexOf(findStr));
	}
	
	public void countChar(String str,char c){
		int charCount=0;
		char[] strArray=str.toCharArray();
		
		for(char tmp:strArray){
			if(tmp=='s'){
				charCount++;
			}
		}

		System.out.println("char 's' count is "+charCount);
	}

	public void printContainWords(String str, String findStr){
		String[] strArray=str.split(" ");

		for(String tmp:strArray ){
			if(tmp.contains(findStr)){
				System.out.print(tmp+" contains ss");
			}
		}
	}
}
