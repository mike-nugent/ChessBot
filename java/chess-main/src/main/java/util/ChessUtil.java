package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChessUtil
{
    public static int getOccurances(final String longString, final String shortString)
    {
        final Pattern p = Pattern.compile(shortString);
        final Matcher m = p.matcher(longString);
        int count = 0;
        while (m.find())
        {
            count += 1;
        }
        return count;
    }
}
