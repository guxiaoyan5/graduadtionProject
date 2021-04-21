package edu.gyj.backend;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class DateTest {
    @Test
    public void test(){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.YEAR));
    }
}
