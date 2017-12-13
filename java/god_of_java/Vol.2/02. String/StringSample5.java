public class StringSample5
{
	public static void main(String[] args) 
	{
		StringSample5 sample=new StringSample5();
		sample.formatCheck();
	}

	public void formatCheck() {
		String text = "�� �̸��� %s�Դϴ�. ���ݱ��� %d ���� å�� ���, "
				+ "�Ϸ翡 %f %%�� �ð��� å�� ���µ� �Ҿ��ϰ� �ֽ��ϴ�.";
		String realText = String.format(text, "�̻��", 3, 10.5);
		// String realText=String.format(text, "�̻��",3);
		System.out.println(realText);
	}



	public void replaceCheck() {
		String text = "The String class represents character strings.";
		System.out.println(text.replace('s', 'z'));
		System.out.println(text);
		System.out.println(text.replace("tring", "trike"));
		System.out.println(text.replaceAll(" ", "|"));
		System.out.println(text.replaceFirst(" ", "|"));
	}

	public void trimCheck() {
		String strings[] = new String[] { " a", " b ", "    c", "d    ",
				"e   f", "   " };
		for (String string : strings) {
			System.out.println("[" + string + "] ");
			System.out.println("[" + string.trim() + "] ");
		}
	}

	public void splitCheck(){
		String text="Java technology is both a programming language and ad platform.";

		String[] splitArray=text.split(" ");
		for(String temp:splitArray){
			System.out.println(temp);
		}
	}

	public void substringCheck1(){
		String text="Java technology";
		String technology=text.substring(5);
		System.out.println(technology);
		String tech=text.substring(5,9);
		System.out.println(tech);
	}

	public void indexOfCheck(){
		String text="Java technology is both a programming language and ad platform.";

		System.out.println(text.indexOf('a'));
		System.out.println(text.indexOf("a "));
		System.out.println(text.indexOf('a',20));
		System.out.println(text.indexOf("a ",20));
		System.out.println(text.indexOf('z'));
	}

	public void matchCheck(){
		String text="This is a text";
		String compare1="is";
		String compare2="this";

		// �Ű������� 4���� �޼ҵ�
		System.out.println(text.regionMatches(2,compare1,0,1));

		// �Ű������� 4���� �޼ҵ�
		System.out.println(text.regionMatches(5,compare1,0,2));

		// �Ű������� 5���� �޼ҵ�
		System.out.println(text.regionMatches(true,2,compare2,0,4));
	}

	public void addressCheck(){
		String addresses[]=new String[]{
			"����� ���α� �ŵ�����",
			"��⵵ ������ �д籸 ���ڵ� ���� ����",
			"����� ���α� ������",
		};

		int startCount=0,endCount=0;

		int containCount=0;

		String startText="�����";
		String endText="��";
		String containText="����";

		for(String address:addresses){
			if(address.startsWith(startText)){
				startCount++;
			}
			if(address.endsWith(endText)){
				endCount++;
			}
			if(address.contains(containText)){
				containCount++;
			}
		}	

		System.out.println("Starts with "+startText+" count is "+startCount);
		System.out.println("Ends with "+endText+" count is "+endCount);
		System.out.println("Contains "+containText+" count is "+containCount);
	}

}