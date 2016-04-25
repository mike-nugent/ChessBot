package parsers;

import util.ChessUtil;

//Forsyth–Edwards Notation
//Six fields separated by spaces
//in order: A8, B8, C8 .... A1, B1, C1...
//upper case white, lower case black. 
//position turn castling en-passant halfmove fullmove
public class FenParser
{
    private static final String COLS = "ABCDEFGH";
    private static final String ROWS = "12345678";
    private static final String NUMS = "12345678";
    private static final String WHITE = "RNBQKP";
    private static final String BLACK = "rnbqkp";
    private static final String PIECES = WHITE + BLACK;
    private static final String VALID_ROW_LETTERS = NUMS + PIECES;

    /**
     * Returns true if the fenString is a valid chessboard position. False if
     * not. Takes into consideration pawns on the 1st and 8th row,
     * extra or missing kings, and bad formats.
     * 
     */
    public static boolean validate(final String fenString)
    {
        if (fenString == null || fenString.length() == 0)
        {
            System.out.println("position was null or empty [" + fenString + "]");
            return false;
        }

        final String[] segments = fenString.split(" ");
        if (segments.length != 6)
        {
            System.out.println("Segments were not broken into 6 pieces: " + toString(segments));
            return false;
        }

        final String[] position = segments[0].split("/");
        final String turn = segments[1];
        final String castling = segments[2];
        final String enPassant = segments[3];
        final String halfmove = segments[4];
        final String fullMove = segments[5];

        final boolean A = validatePosition(position);
        final boolean B = validateTurn(turn);
        final boolean C = validateCastling(castling);
        final boolean D = validateEnPassant(enPassant);
        final boolean E = validateHalfMove(halfmove);
        final boolean F = validateFullMove(fullMove);

        if (A && B && C && D && E && F)
        {
            System.out.println("Correct FEN string [" + fenString + "] ");
            return true;
        }
        else
        {
            System.out.println("[" + fenString + "] was not a correct FEN string");
            return false;
        }
    }

    private static boolean validateFullMove(final String fullMove)
    {
        return validateTime(fullMove, 1000); // TODO - is this true?
    }

    private static boolean validateHalfMove(final String halfmove)
    {
        return validateTime(halfmove, 50);
    }

    private static boolean validateTime(final String time, final int limit)
    {
        try
        {
            final int parsedTime = Integer.parseInt(time);
            if (parsedTime >= limit)
            {
                System.out.println("The time limit [" + time + "] is illegal");
                return false;
            }
            return true;
        }
        catch (final Exception e)
        {
            System.out.println("[" + time + "] is not a parsable integer value");
            return false;
        }
    }

    private static boolean validateEnPassant(final String enPassant)
    {
        if (enPassant.length() == 1)
        {
            if (enPassant.equals("-"))
            {
                return true;
            }
            else
            {
                System.out.println("En passant [" + enPassant + "] is not correct");
                return false;
            }

        }
        else if (enPassant.length() == 2)
        {
            final String[] epChars = toLetters(enPassant);
            if (COLS.contains(epChars[0].toUpperCase()) && "36".contains(epChars[1].toUpperCase()))
            {
                return true;
            }
            else
            {
                System.out.println("En passant [" + enPassant + "] is not a square");
                return false;
            }
        }
        else
        {
            System.out.println("Too many characters for en passant [" + enPassant + "]");
            return false;
        }
    }

    private static boolean validateCastling(final String castling)
    {
        if (castling.length() == 1)
        {
            if (!castling.equals("-") && !"KQkq".contains(castling))
            {
                System.out.println("Unknown castling sequence [" + castling + "]");
                return false;
            }
        }
        else
        {
            final String[] castlingChars = toLetters(castling);
            String prevLetters = "";
            for (final String cas : castlingChars)
            {
                if (!validateChar(cas, "KQkq"))
                {
                    System.out.println("Castle char [" + cas + "] in [" + castling + "] does not make sense");
                    return false;
                }

                if (prevLetters.contains(cas))
                {
                    System.out.println("Duplicate castlings found [" + castling + "]");
                    return false;
                }
                prevLetters += cas;
            }
        }

        return true;
    }

    private static boolean validateTurn(final String turn)
    {
        if (validateChar(turn, "wb"))
        {
            return true;
        }
        else
        {
            System.out.println("Turn [" + turn + "] should be either \"w\" or \"b\"");
            return false;
        }
    }

    private static boolean validatePosition(final String[] position)
    {
        if (position.length != 8)
        {
            System.out.println("There should be 8 segments in the position string: " + toString(position));
            return false;
        }

        final String positionString = toString(position);
        final int numWhiteKings = ChessUtil.getOccurances(positionString, "K");
        final int numBlackKings = ChessUtil.getOccurances(positionString, "k");

        if (numWhiteKings != 1 || numBlackKings != 1)
        {
            System.out.println("Incorrect number of kings on the board. " +
                    "White kings [" + numWhiteKings + "] Black kings [" + numBlackKings + "]");
            return false;
        }

        int counter = 1;
        for (final String row : position)
        {
            if (!validateRow(row, counter++))
            {
                System.out.println("The row [" + row + "] had illegal characters");
                return false;
            }
        }
        return true;
    }

    private static boolean validateRow(final String row, final int counter)
    {
        final String[] chars = toLetters(row);
        int rowLength = 0;

        if (counter == 1 || counter == 8)
        {
            if (row.contains("p") || row.contains("P"))
            {
                System.out.println("row [" + row + "] contained an impossible pawn on the [" + counter + "] row");
                return false;
            }
        }

        for (final String chr : chars)
        {
            if (!validateChar(chr, VALID_ROW_LETTERS))
            {
                System.out.println("Character [" + chr + "] in row [" + row + "] was not valid");
                return false;
            }

            rowLength += getNumSpaces(chr);
        }

        if (rowLength != 8)
        {
            System.out.println("row [" + row + "] had wrong amount of squares.  Should equal 8");
            return false;
        }

        return true;
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

    private static String[] toLetters(final String row)
    {
        final String[] chars = new String[row.length()];
        for (int i = 0; i < row.length(); i++)
        {
            chars[i] = Character.toString(row.charAt(i));
        }
        return chars;
    }

    private static boolean validateChar(final String chr, final String validRowLetters)
    {
        return validRowLetters.contains(chr);
    }

    private static String toString(final String[] segments)
    {
        String result = "[";
        for (int i = 0; i < segments.length; i++)
        {
            result += ((i == 0) ? segments[i] : "," + segments[i]);
        }
        result += "]";

        return result;
    }
}
