package test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class CharTest
{
    @Test
    public void testChars()
    {
        final List<String> unique = new LinkedList<String>();
        char cha = 'A';
        int brcnt = 0;
        for (int i = 0; i < 100000; i++)
        {
            final String newc = String.valueOf(cha++);
            if (!unique.contains(newc))
            {
                unique.add(newc);
                System.out.print(newc);
            }
            if (brcnt++ > 100)
            {
                brcnt = 0;
                System.out.println();
            }
        }
    }
}
