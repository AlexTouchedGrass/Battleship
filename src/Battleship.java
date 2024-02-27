import java.util.Scanner;

public class Battleship {

    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final String[][] gameBoard = new String[ROWS][COLS];
    private static int totalShips = 0;
    private static int shipsRemaining = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Main game loop
        while (true) {
            initializeGame();

            // Place ships on the board
            placeShips(scan);

            // Main game loop
            while (shipsRemaining > 0) {
                displayGameBoard();
                makeMove(scan);
                if (isGameFinished()) {
                    displayResult();
                    break;
                }
            }

            if (!playAgain(scan)) {
                break;
            }
        }
    }

    private static void initializeGame() {
        clearGameBoard();
        totalShips = 0;
        shipsRemaining = 0;
    }

    private static void clearGameBoard() {
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                gameBoard[row][col] = "-";
            }
        }
    }

    private static void displayGameBoard() {
        System.out.println("Current Game Board:");
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                System.out.print(gameBoard[row][col] + " ");
            }
            System.out.println();
        }
    }

    private static void placeShips(Scanner scan) {

        placeShip(scan, 2, "Destroyer");
        placeShip(scan, 3, "Cruiser");
        placeShip(scan, 4, "Carrier");
    }

    private static void placeShip(Scanner scan, int size, String shipName) {
        System.out.println("Placing " + shipName + " of size " + size);
        totalShips++;
        shipsRemaining++;

        for (int i = 0; i < size; i++) {
            System.out.print("Enter the row for part " + (i + 1) + ": ");
            int row = scan.nextInt();
            System.out.print("Enter the column for part " + (i + 1) + ": ");
            int col = scan.nextInt();

            // Ensure the input is within bounds
            if (row >= 1 && row <= ROWS && col >= 1 && col <= COLS) {
                gameBoard[row - 1][col - 1] = "b";
            } else {
                System.out.println("Invalid input. Try again.");
                i--;  // Repeat the iteration for the same part if they enter invalid input.
            }
        }
    }

    private static void makeMove(Scanner scan) {
        System.out.print("Enter the row to guess: ");
        int guessRow = scan.nextInt();
        System.out.print("Enter the column to guess: ");
        int guessCol = scan.nextInt();

        // Ensure the input is within bounds
        if (guessRow >= 1 && guessRow <= ROWS && guessCol >= 1 && guessCol <= COLS) {
            if (gameBoard[guessRow - 1][guessCol - 1].equals("b")) {
                System.out.println("Hit!");
                gameBoard[guessRow - 1][guessCol - 1] = "x";  // Mark as hit
                shipsRemaining--;

                if (shipsRemaining == 0) {
                    System.out.println("All ships destroyed! You win!");
                }
            } else if (gameBoard[guessRow - 1][guessCol - 1].equals("x") || gameBoard[guessRow - 1][guessCol - 1].equals("m")) {
                System.out.println("You've already guessed this position. Try again.");
            } else {
                System.out.println("Miss!");
                gameBoard[guessRow - 1][guessCol - 1] = "m";  // Mark as miss
            }
        } else {
            System.out.println("Invalid input. Try again.");
        }
    }

    private static boolean isGameFinished() {
        // Game finishes when all ships are destroyed or when the player who placed the ships wins
        return shipsRemaining == 0 || shipsRemaining == totalShips;
    }

    private static void displayResult() {
        if (shipsRemaining == 0) {
            System.out.println("You found all the ships! Congratulations!");
        } else {
            System.out.println("Player who placed the ships wins! Better luck next time!");
        }
    }

    private static boolean playAgain(Scanner scan) {
        System.out.print("Would you like to play again? (Y/N): ");
        String response = scan.next();
        return response.equalsIgnoreCase("Y");
    }
}
