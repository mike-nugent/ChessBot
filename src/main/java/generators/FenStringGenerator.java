package generators;

public class FenStringGenerator
{
    private static final String DELIM = " ";
    private static final String COLS = "ABCDEFGH";
    private static final String NUMS = "12345678";
    private static final String WHITE = "RNBQKP";
    private static final String BLACK = "rnbqkp";
    private static final String PIECES = WHITE + BLACK;

    public static String generateString()
    {
        return generatePosition() + DELIM +
                generateTurn() + DELIM +
                generateCastling() + DELIM +
                generateEnPassant() + DELIM +
                generateHalfMove() + DELIM +
                generateFullMove();
    }

    private static String generateFullMove()
    {
        return "" + (int) (Math.random() * 100);
    }

    private static String generateHalfMove()
    {

        return "" + (int) (Math.random() * 50);
    }

    private static String generateEnPassant()
    {
        final double rnd = Math.random() * 100;
        if (rnd <= 10)
        {
            return "-";
        }
        else
        {
            final String col = getRandom(COLS);
            final String row = getRandom("36");
            return col + row;
        }
    }

    private static String generateCastling()
    {
        final double rnd = Math.random() * 100;
        if (rnd <= 20)
        {
            return "-";
        }
        else
        {
            final String q = getRandom("q ").replace(" ", "");
            final String k = getRandom("k ").replace(" ", "");
            final String Q = getRandom("Q ").replace(" ", "");
            final String K = getRandom("K ").replace(" ", "");

            final String ret = q + k + Q + K;

            if (ret.equals(""))
            {
                return "-";
            }
            else
            {
                return ret;
            }
        }
    }

    private static String generateTurn()
    {
        return (Math.random() * 100) > 50 ? "w" : "b";
    }

    private static String generatePosition()
    {
        final int wk = Integer.parseInt(getRandom("12345678"));
        final int bk = Integer.parseInt(getRandom("12345678"));

        final String A = makeRow("RNBQrnbq12345678", wk == 1, bk == 1);
        final String B = makeRow("RNBQPrnbqp12345678", wk == 2, bk == 2);
        final String C = makeRow("RNBQPrnbqp12345678", wk == 3, bk == 3);
        final String D = makeRow("RNBQPrnbqp12345678", wk == 4, bk == 4);
        final String E = makeRow("RNBQPrnbqp12345678", wk == 5, bk == 5);
        final String F = makeRow("RNBQPrnbqp12345678", wk == 6, bk == 6);
        final String G = makeRow("RNBQPrnbqp12345678", wk == 7, bk == 7);
        final String H = makeRow("RNBQrnbq12345678", wk == 8, bk == 8);
        return A + "/" + B + "/" + C + "/" + D + "/" + E + "/" + F + "/" + G + "/" + H;

    }

    private static String makeRow(final String chars, final boolean wk, final boolean bk)
    {
        String ret = "";
        int remain = 8;
        if (wk)
        {
            remain--;
            ret = "K";
        }

        if (bk)
        {
            remain--;
        }

        while (remain > 0)
        {
            final String rnd = getRandom(chars);
            if (remain - getNumSpaces(rnd) >= 0)
            {
                String[] retChars = toLetters(ret);

                if (Math.random() * 100 > 50)
                {
                    //right
                    if (NUMS.contains(rnd) &&
                            retChars.length > 1 &&
                            NUMS.contains(retChars[retChars.length - 1]))
                    {
                        //the right hand side is a number, combine em.
                        final String T = retChars[retChars.length - 1];
                        final int newVal = getNumSpaces(T) + getNumSpaces(rnd);
                        retChars[retChars.length - 1] = newVal + "";
                    }
                    else
                    {
                        retChars = combineRight(retChars, rnd);
                    }

                }
                else
                {
                    //left
                    if (NUMS.contains(rnd) &&
                            retChars.length > 1 &&
                            NUMS.contains(retChars[0]))
                    {
                        //the left hand side is a number, combine em.
                        final String T = retChars[0];
                        final int newVal = getNumSpaces(T) + getNumSpaces(rnd);
                        retChars[0] = newVal + "";
                    }
                    else
                    {
                        retChars = combineLeft(retChars, rnd);
                    }
                }

                remain -= getNumSpaces(rnd);
                ret = toRet(retChars);
            }
        }

        if (bk)
        {
            //make this more random later
            if (Math.random() * 100 > 50)
            {
                ret = ret + "k";
            }
            else
            {
                ret = "k" + ret;
            }
        }

        if (ret.length() == 2)
        {
            final String[] tmp = toLetters(ret);
            if (NUMS.contains(tmp[0]) && NUMS.contains(tmp[1]))
            {
                return getNumSpaces(tmp[0]) + getNumSpaces(tmp[1]) + "";
            }
        }

        return ret;
    }

    private static String[] combineLeft(final String[] retChars, final String rnd)
    {
        final String[] newArr = new String[retChars.length + 1];
        newArr[0] = rnd;
        for (int i = 0; i < retChars.length; i++)
        {
            newArr[i + 1] = retChars[i];
        }
        return newArr;
    }

    private static String[] combineRight(final String[] retChars, final String rnd)
    {
        final String[] newArr = new String[retChars.length + 1];
        for (int i = 0; i < retChars.length; i++)
        {
            newArr[i] = retChars[i];
        }
        newArr[retChars.length] = rnd;
        return newArr;
    }

    private static String toRet(final String[] chars)
    {
        String ret = "";
        for (final String s : chars)
        {
            ret += s;
        }
        return ret;
    }

    private static String[] toLetters(final String row)
    {
        final String[] chars = new String[row.length()];
        for (int i = 0; i < row.length(); i++)
        {
            chars[i] = Character.toString(row.charAt(i));
        }
        return chars;
    }

    private static final int getNumSpaces(final String chr)
    {
        if (NUMS.contains(chr))
        {
            return Integer.parseInt(chr);
        }
        else if (PIECES.contains(chr))
        {
            return 1;
        }
        else
        {
            System.out.println("character [" + chr + "] does not make sense");
            return 10000;
        }
    }

    private static String getRandom(final String chars)
    {
        final int rndIndex = (int) (Math.random() * chars.length());
        return Character.toString(chars.charAt(rndIndex));
    }
}
