import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateExample {
    public static void main(String[] args) {
        String dateString = "2024-05-13 14:30:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateString);
            System.out.println("Date: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

public class Task8 {
    public static void main(String[] args) {
        String dateStringWithTz = "2024-05-13 14:30:00+03";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
        try {
            Date date = format.parse(dateStringWithTz);
            System.out.println("Date: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
