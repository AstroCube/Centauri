package net.astrocube.commons.core.utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

public class PrettyTimeUtils {

    public static String getHumanDate(Date date, String locale) {
        PrettyTime p = new PrettyTime(new Locale(locale));
        return p.format(date);
    }

    public static String getHumanDate(LocalDateTime date, String locale) {
        PrettyTime p = new PrettyTime(new Locale(locale));
        return p.format(Date.from(date.toInstant(ZoneOffset.UTC)));
    }

}
