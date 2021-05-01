import edu.Infomation.enumObject.ThreeMeals;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {
    @Test
    public void test() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = simpleDateFormat.parse("2021-04-07 11:09:17");
        calendar.setTime(dateTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String meal ;
        if (hour <= 9) {
            meal = ThreeMeals.BREAKFAST;
        } else if (hour <= 15) {
            meal = ThreeMeals.LUNCH;
        } else  {
            meal = ThreeMeals.DINNER;
        }
        System.out.println(meal);
    }
}
