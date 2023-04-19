package site.metacoding.blogv3.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtilDate {
    public static String toStringFormat(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
