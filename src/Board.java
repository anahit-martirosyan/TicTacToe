import java.util.Scanner;

public class Board {
    private char[][] board;
    private int dimension;
    private char turn;
    private int curRow;
    private int curCol;

    public Board(int dim)
    {
        turn = 'X';
        this.dimension = dim;
        board = new char[dimension][dimension];
        for(int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                board[i][j] = ' ';
            }
        }
    }

    public Board() {
        this(3);
    }

    public void gameLoop()
    {
        try(Scanner scanner = new Scanner(System.in)) {
            drawBoard();
            while (true) {
                do {
                    System.out.println("Turn of " + turn + ".");
                    System.out.println("Give row and column values in [1, " + dimension + "] range.");
                    curRow = scanner.nextInt() - 1;
                    curCol = scanner.nextInt() - 1;
                } while (!(curRow >= 0 && curRow < dimension && curCol >= 0 && curCol < dimension));
                if(!addSymbol()) {
                    System.out.println("This cell is already filled. Choose other cell.");
                    continue;
                }
                drawBoard();
                if (checkAndEndIfNecessary()) return;
                changeTurn();
            }
        }
    }

    private void drawBoard()
    {
        final char horiz = '_';
        final char vert = '|';
        final int horizLength = 3;
        final int vertLength = 2;

        System.out.print(' ');
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < horizLength; ++j) {
                System.out.print(horiz);
            }
            System.out.print(' ');
        }
        System.out.println();
        for(int i = 0; i < dimension; ++i) {
            for(int t = 0; t < vertLength; ++t) {
                for(int j = 0; j < dimension; ++j) {
                    System.out.print(vert);
                    for (int k = 0; k < horizLength; ++k) {
                        char s = (t == (vertLength - 1) / 2) && (k == horizLength / 2) ? board[i][j] : ' ';
                        if(t == vertLength - 1) {
                            s = '_';
                        } else if((t == (vertLength - 1) / 2) && (k == horizLength / 2)) {
                            s = board[i][j];
                        } else {
                            s = ' ';
                        }
                        System.out.print(s);
                    }
                }
                System.out.print(vert);
                System.out.println();
            }
        }
    }

    private void changeTurn()
    {
        turn = turn == 'X' ? 'O' : 'X';
    }

    private boolean addSymbol()
    {
        if(board[curRow][curCol] == ' ') {
            board[curRow][curCol] = turn;
            return true;
        }
        return false;
    }

    private boolean checkAndEndIfNecessary()
    {
        boolean status = checkHorizontal() || checkVertical() || checkDiagonal();
        if(status) {
            System.out.println("Game over. " + turn + " won.");
        }
        return status;
    }

    private boolean checkHorizontal()
    {
        return check(true, false);
    }

    private boolean checkVertical()
    {
        return check(false, true);
    }

    private boolean checkDiagonal()
    {
        return check(true, true);
    }

    private boolean check(boolean horizontal, boolean vertical)
    {
        if(horizontal && vertical && curRow != curCol ) return false;
        boolean status = true;
        int i = vertical ? -1 : 0;
        int j = horizontal ? -1 : 0;
        while(curRow + i >= 0 && curCol + j >= 0) {
            if(board[curRow + i][curCol + j] == turn) {
                if(i != 0) --i;
                if(j != 0) --j;
            } else {
                status = false;
                break;
            }
        }
        if(status) {
            i = vertical ? 1 : 0;
            j = horizontal ? 1 : 0;
            while(curRow + i < dimension && curCol + j < dimension) {
                if(board[curRow + i][curCol + j] == turn) {
                    if(i != 0) ++i;
                    if(j != 0) ++j;
                } else {
                    status = false;
                    break;
                }
            }
        }
        return status;
    }
}
