package science.mrcuijt.webdoc.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtil {

  public static String dateFormat(Date date, TimeZone timeZone){
    //Locale locale = new Locale(Locale.CHINA);
    DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss", Locale.US);
    df.setTimeZone(timeZone);
    String gmt = "%s GMT";
    return String.format(gmt, df.format(date));
  }

  public static Date bowerCache(){
    Calendar rightNow = Calendar.getInstance();
    rightNow.add(Calendar.DAY_OF_MONTH, 7);
    return rightNow.getTime();
  }

}
