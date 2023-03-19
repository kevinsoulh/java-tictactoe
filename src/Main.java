import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        char[][] gameBoard = initialize();
        char currentPlayer = 'O';

        // game loop
        while(status(gameBoard) == -1) {
            // 1. show the game board
            showGameBoard(gameBoard);

            // 2. ask next player for their move
            int[] move = askNextPlayerForMove(currentPlayer);

            // 3. execute that move / call step()
            if(step(gameBoard, move[0], move[1], currentPlayer)) {

                // 4. set the next player
                if(currentPlayer == 'O') {
                    currentPlayer = 'X';
                } else {
                    currentPlayer = 'O';
                }

            } else {
                System.out.println("Space already taken");
            }
        }

        showGameBoard(gameBoard);

        // game is over, announcing draw or winner
        int status = status(gameBoard);
        if(status == 0) {
            System.out.println("Draw :(");
        } else if (status == 1) {
            System.out.println("O is the winner!");
        } else if (status == 2) {
            System.out.println("X is the winner!");
        }

    }

    /**
     * ask the next player for a move
     * @param currentPlayer the next player
     * @return line and column
     */
    private static int[] askNextPlayerForMove(char currentPlayer) {
        Scanner scanner = new Scanner(System.in);

        // 1. say which player is next
        System.out.println("The next player is: " + currentPlayer);

        while(true) {
            // 2. ask for a line number
            System.out.println("Enter the line number (0, 1 or 2): ");
            int line = scanner.nextInt();

            // 3. ask for a column number
            System.out.println("Enter the column number (0, 1 or 2): ");
            int column = scanner.nextInt();

            // 4. check if values are valid
            if(line != 0 && line != 1 && line != 2) {
                System.out.println("Please enter a valid line number.");
                //    => if invalid, go back to 2.
            } else if(column != 0 && column != 1 && column != 2) {
                System.out.println("Please enter a valid column number.");
                //    => if invalid, go back to 2.
            } else {
                // VALID line and column numbers
                return new int[] { line, column };
            }
        }

        //    => if valid, return
        //    => if invalid, go back to 2.
    }

    /**
     * show the game board
     * @param gameBoard the game board
     */
    private static void showGameBoard(char[][] gameBoard) {
        System.out.println("  " + gameBoard[0][0] + " " + gameBoard[0][1] + " " + gameBoard[0][2]);
        System.out.println("  " + gameBoard[1][0] + " " + gameBoard[1][1] + " " + gameBoard[1][2]);
        System.out.println("  " + gameBoard[2][0] + " " + gameBoard[2][1] + " " + gameBoard[2][2]);
    }

    /**
     * initializes the game
     * @return a new game board.
     */
    public static char[][] initialize ( ) {

        return new char[][]{
                { '-', '-', '-' },
                { '-', '-', '-' },
                { '-', '-', '-' }
        };
    }

    /**
     * executes one step in the game
     * @return true if step taken, false if not.
     */
    public static boolean step (char[][] M, int lin, int col, char gamer) {
        // check if you can take this step
        // if you can, set the space to the current character and return true
        // if you cant, return false
        if(M[lin][col] == '-') {
            M[lin][col] = gamer;
            return true;
        }
        return false;
    }

    /**
     * gives you the status of the game
     * @param M the game board
     * @return retorna um número inteiro indicando o estado do jogo: alguém venceu, ocorreu um empate ou o jogo deve continuar. Utilize os códigos:
     * -1 o jogo pode continuar;
     * 0 ocorreu um empate;
     * 1 jogado O venceu;
     * 2 jogador X venceu;
     */
    public static int status (char[][] M) {
        // 1. check if a player has won (and which one) => return 1/2
        if(
                // check first line (horizontally)
                M[0][0] != '-' && M[0][0] == M[0][1] && M[0][0] == M[0][2]
                // check first column (vertically)
                || M[0][0] != '-' && M[0][0] == M[1][0] && M[0][0] == M[2][0]
        ) {
            char winner = M[0][0];
            return winner == 'O' ? 1 : 2;
        } else if(
                // check third line (horizontally)
                M[2][0] != '-' && M[2][0] == M[2][1] && M[2][0] == M[2][2]
                // check third column (vertically)
                || M[0][2] != '-' && M[0][2] == M[1][2] && M[0][2] == M[2][2]
        ) {
            char winner = M[2][2];
            return winner == 'O' ? 1 : 2;
        } else if(
                // check second line (horizontally)
                M[1][0] != '-' && M[1][0] == M[1][1] && M[1][0] == M[1][2]
                // check second column (vertically)
                || M[0][1] != '-' && M[0][1] == M[1][1] && M[0][1] == M[2][1]
                // check top left to bottom right (diagonally)
                || M[0][0] != '-' && M[0][0] == M[1][1] && M[0][0] == M[2][2]
                // check bottom left to top right (diagonally)
                || M[2][0] != '-' && M[2][0] == M[1][1] && M[2][0] == M[0][2]
        ) {
            char winner = M[1][1];
            return winner == 'O' ? 1 : 2;
        }

        // 2. If any space is free => return -1
        for(int line = 0; line < 3; line++) {
            for(int column = 0; column < 3; column++) {
                if(M[line][column] == '-') {
                    return -1;
                }
            }
        }

        return 0;
    }
}