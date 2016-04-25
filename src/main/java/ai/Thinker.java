package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Thinker
{

    /**
     * @param args
     */
    public static void main(final String[] args)
    {
        final Scanner in = new Scanner(System.in);
        int line = 1;
        final List<String> lines = new ArrayList<String>();
        while (true)
        {
            System.out.println("\n\n\n\n\n\n\n");
            for (final String l : lines)
            {
                System.out.println(l);
            }
            System.out.print(line + ". ");
            final String userInput = in.nextLine();
            final String round = line + ". " + userInput + " XX";
            lines.add(round);
            line++;
        }
    }
}
