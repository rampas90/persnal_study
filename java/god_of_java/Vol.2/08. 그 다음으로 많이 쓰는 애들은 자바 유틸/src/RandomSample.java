import java.util.Random;

public class RandomSample {

	public static void main(String[] args) {
		RandomSample sample=new RandomSample();
		int randomCount=10;
		sample.generateRandomNumbers(randomCount);
		
	}
	
	public void generateRandomNumbers(int randomCount){
		Random random=new Random();
		for (int loop = 0; loop < randomCount; loop++) {
			System.out.print(random.nextInt(100)+",");
		}
	}
	
	public void parseStringWithSplit(String data){
		String[] splitString=data.split("\\s");
		for (String tempData : splitString) {
			System.out.println("["+tempData+"]");
		}
	}
}
