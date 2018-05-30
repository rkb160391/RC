package dynsmiddleware;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws ParseException {
		String time="02:00:00";
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date date = sdf.parse(time);
		System.out.println(date);
	}
}
