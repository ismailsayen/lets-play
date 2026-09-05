package isayen.lets_play.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDate {
    public static  String CurrentDateToString(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Convert to String
        String formattedString = currentDateTime.format(formatter);
        return formattedString;
    }
}
