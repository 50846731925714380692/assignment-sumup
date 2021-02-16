import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Assignment
{
    static final int P1 = 1;
    static final int P2 = 2;

    static int count = 0;
    static Set<String> duplicates = new HashSet<>();

    public static void main(String[] args)
    {
        int[][] board = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        move(board, true, 0);
        move(board, false, 0);

        System.out.println("Total Combinations: " + count * 2);
    }

    public static void move(int[][] board, boolean p1Turn, int depth)
    {
        //also count the full board without a winner
        if (isWon(board) || depth == 9)
        {
            //remove duplicates from the solutions, if this is commented out - duplicated end states will be observed.
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

    private static String stringifyFinal(int[][] board)
    {
        return Arrays.stream(board)
                .flatMapToInt(Arrays::stream)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    static boolean isWon(int[][] board)
    {
        boolean isWon = false;

        //each line and column
        for (int i = 0; i < 3; i++)
        {
            isWon |= wins(board[i][0], board[i][1], board[i][2]);
            isWon |= wins(board[0][i], board[1][i], board[2][i]);
        }

        //diagonals
        isWon |= wins(board[0][0], board[1][1], board[2][2]);
        isWon |= wins(board[0][2], board[1][1], board[2][0]);

        return isWon;
    }

    static boolean wins(int a, int b, int c)
    {
        return a != 0 && a == b && a == c;
    }
}