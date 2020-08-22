package net.astrocube.commons.core.utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

public class PrettyTimeUtils {

    public static String getHumanDate(Date date, String locale) {
        PrettyTime p = new PrettyTime(new Locale(locale));
        return p.format(date);
    }

}
