
import java.util.HashSet;
import java.util.Random;

public class RandomNumberMarker {
	Random random = new Random();

	public static void main(String[] args) {
		RandomNumberMarker sample = new RandomNumberMarker();
		sample.makeRandomNumbers(10);
	}

	public void makeRandomNumbers(int count) {
		for (int loop = 0; loop < count; loop++) {
			HashSet<Integer> set = getSixNumber();
			System.out.println(set);
		}
	}

	public HashSet<Integer> getSixNumber() {
		HashSet<Integer> numberSet = new HashSet<Integer>();
		while (true) {
			int tempNumber = random.nextInt(44) + 1;
			numberSet.add(tempNumber);
			if (numberSet.size() == 6)
				break;
		}
		return numberSet;
	}
}
