import java.util.HashSet;
import java.util.Set;

public class Assignment
{
    static final int P1 = 1;
    static final int P2 = 2;

    static int count = 0;
    static Set<String> duplicates = new HashSet<>();

    public static void move(int[][] board, boolean p1Turn, int depth)
    {
        if (isFinal(board))
        {
            //remove duplicates from the solutions
            if (duplicates.add(stringifyFinal(board)))
            {
                count++;
            }

            return;
        }

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == 0)
                {
                    //make the move
                    board[i][j] = p1Turn ? P1 : P2;

                    //next move
                    move(board, !p1Turn, depth + 1);

                    //rewind
                    board[i][j] = 0;
                }
            }
        }
    }

    public static void main(String[] args)
    {
        int[][] board = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        move(board, true, 1);

        System.out.println("Total P1 First Combinations: " + count);
    }

    private static String stringifyFinal(int[][] board)
    {
        StringBuilder sb = new StringBuilder(21);

        for (int[] ints : board)
        {
            for (int anInt : ints)
            {
                sb.append(' ').append(anInt == P1 ? 'x' : anInt == P2 ? 'o' : '-');
            }

            sb.append('\n');
        }

        return sb.toString();
    }

    static boolean isFinal(int[][] board)
    {
        boolean isFinal = false;

        //each line and column
        for (int i = 0; i < 3; i++)
        {
            isFinal |= wins(board[i][0], board[i][1], board[i][2]);
            isFinal |= wins(board[0][i], board[1][i], board[2][i]);
        }

        //diagonals
        isFinal |= wins(board[0][0], board[1][1], board[2][2]);
        isFinal |= wins(board[0][2], board[1][1], board[2][0]);

        return isFinal;
    }

    static boolean wins(int a, int b, int c)
    {
        return a != 0 && a == b && a == c;
    }
}