import java.math.BigDecimal;

public class BigDecimalSample {
	
	public static void main(String[] args) {
		BigDecimalSample sample = new BigDecimalSample();
		//sample.normalDoubleCalc();
		sample.bigDecimalCalc();
	}
	
	public void normalDoubleCalc() {
		double value = 1.0;
		for (int loop = 0; loop < 10; loop++) {
			value += 0.1;
			System.out.println(value);
		}
	}
	
	public void bigDecimalCalc() {
		BigDecimal value = new BigDecimal("1.0");
		BigDecimal value2 = new BigDecimal(1.0);
		BigDecimal addValue = new BigDecimal("0.1");
		BigDecimal addValue2 = new BigDecimal(0.1);
		for (int loop = 0; loop < 10; loop++) {
			value = value.add(addValue);
			System.out.println(value.toString());
		}
		/*
		for(int loop=0;loop<10;loop++) {
			value2 = value2.add(addValue2);
			System.out.println("value2="+value2);
		}
		*/
	}
}
