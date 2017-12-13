import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateCalendarSample {

	public static void main(String[] args) {
		DateCalendarSample sample = new DateCalendarSample();
		//sample.checkDate();
		// sample.useCalendar();
		Calendar calendar = new GregorianCalendar(2000, 11, 31);
		sample.addAndRoll(calendar, 10);

	}
	
	public void addAndRoll(Calendar calendar, int amount) {
		calendar.add(Calendar.DATE, amount);
		printCalendar(calendar);
		calendar.add(Calendar.DATE, -amount);
		printCalendar(calendar);
		calendar.roll(Calendar.DATE, amount);
		printCalendar(calendar);
	}

	public void printCalendar(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.KOREA);
		int date = calendar.get(Calendar.DATE);
		System.out.println(year + "/" + month + "/" + date);
	}

	public void checkDate() {
		Date date = new Date();
		System.out.println(date);

		long time = date.getTime();
		System.out.println(time);

		date.setTime(0);
		System.out.println(date);
	}

	

	public void useCalendar() {
		Calendar cal = Calendar.getInstance();
		Calendar greCal = new GregorianCalendar();

		System.out.println(cal);
		System.out.println(greCal);

		int year = greCal.get(Calendar.YEAR);
		int month = greCal.get(Calendar.MONTH);
		int date = greCal.get(Calendar.DAY_OF_MONTH);

		System.out.println(year + "/" + month + "/" + date);

		String monthKorea = greCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.KOREA);
		String monthUS = greCal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);

		System.out.println(monthKorea);
		System.out.println(monthUS);
	}

	

}
