package com.cisco.josouthe;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {
    public static String encode( String original ){
        return original.replace("|","%7C").replace(" ", "%20").replace(":","%3A").replace(".", "%2E").replace("-", "%2D");
    }
    public static long now() { return new Date().getTime(); }

    public static String getDateString(long adjustMinutes) { //ISO8601 Date (Extend) https://dencode.com/en/date/iso8601
        return ZonedDateTime.now().plusMinutes(adjustMinutes).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
    }
    public static String getEncodedDateString( long adjustMinutes ) {
        return encode(getDateString(adjustMinutes));
    }
}
