import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        try(Scanner scanner = new Scanner(System.in)) {
            boolean isPlayed = false;
            while(!isPlayed) {
                try {
                    System.out.print("Specify board size: ");
                        int size = scanner.nextInt();
                        Board board = new Board(size);
                        board.gameLoop();
                        isPlayed = true;
                } catch (InputMismatchException e) {
                    System.out.println("Specify an integer number for board size.");
                    scanner.next();
                }
            }
        }
    }
}
