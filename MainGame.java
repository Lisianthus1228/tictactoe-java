import java.util.*;
import java.io.IOException;

public class MainGame {
    static Scanner scan = new Scanner(System.in);
    static String[][] tictactoe = {{"[1]","[2]", "[3]"},
                                {"[4]", "[5]", "[6]"},
                                {"[7]", "[8]", "[9]"}};
    
    public static void placeBoard(int boardPos, String userSymbol) {
        int boardPosX = 0;
        int boardPosY = 0;
        // VERIFY POSITION IS VALID FIRST
        while(!(boardPos >= 1 && boardPos <= 9)) {
            System.out.println("INVALID POSITION.");
            System.out.print("ENTER NEW POSITION: ");
            boardPos = scan.nextInt();
        }
        
        if(boardPos <= 3) {
            boardPosX = boardPos-1;
        } else if(boardPos <= 6) {
            boardPosX = boardPos-4;
            boardPosY = 1;
        } else {
            boardPosX = boardPos-7;
            boardPosY = 2;
        }
        
        // VERIFY POSITION IS NOT ALREADY TAKEN
        if(tictactoe[boardPosY][boardPosX].equals("[X]") || tictactoe[boardPosY][boardPosX].equals("[Y]")) {
            System.out.println("TAKEN POSITION.");
            System.out.print("ENTER NEW POSITION: ");
            boardPos = scan.nextInt();
            placeBoard(boardPos, userSymbol);
        }
        
        tictactoe[boardPosY][boardPosX] = "[" + userSymbol + "]";
    }
    
    public static void main(String[] args) {
        boolean playing = true;
        int lastTurn = 1;
        
        System.out.println("[BEGIN THE GAME!]");
        System.out.println("THE BOARD:\n1 2 3\n4 5 6\n7 8 9");
        while(playing) {
            if(lastTurn == 1) {
                lastTurn = 0;
                System.out.print("PLAYER 1 (X) TURN: ");
                int userTurn = scan.nextInt();
                placeBoard(userTurn, "X");
            } else {
                lastTurn = 1;
                System.out.print("PLAYER 2 (O) TURN: ");
                int userTurn = scan.nextInt();
                placeBoard(userTurn, "O");
            }
            clrscr(); // CLEAR BOARD FOR NEW TURN.
            printBoard();
            playing = logicCheck(playing, lastTurn); //IF LAST TURN IS 0, THE POTENTIAL WINNER IS X, OTHERWISE IT IS O.
        }
        
        // GAME OVER
        System.out.println("THE GAME IS OVER.");
    }
    
    public static boolean logicCheck(boolean continueGame, int currPlayer) {
        boolean winner = false;
        // CASE 1: Any of the row's
        for(int i=0; i < tictactoe.length; i++) {
            if(tictactoe[i][0].equals(tictactoe[i][1]) && tictactoe[i][0].equals(tictactoe[i][2])) {
                winner = true;
            } 
        }
        // CASE 2: Any of the column's
        for(int i=0; i < tictactoe.length; i++) {
            if(tictactoe[0][i].equals(tictactoe[1][i]) && tictactoe[0][i].equals(tictactoe[2][i])) {
                winner = true;
            }
        }
        // CASE 3: Both diagonal's
        if(tictactoe[0][0].equals(tictactoe[1][1]) && tictactoe[0][0].equals(tictactoe[2][2])) {
            winner = true;
        } else if(tictactoe[0][2].equals(tictactoe[1][1]) && tictactoe[0][2].equals(tictactoe[2][0])) {
            winner = true;
        }
        
        // CASE 4: STALEMATE
        int playedPlace = 0; // Counts amount of squares that have already been played, max 9
        for(int i=0; i < tictactoe.length; i++) {
            for(int j=0; j < tictactoe.length; j++) {
                if(tictactoe[i][j].equals("[X]") || tictactoe[i][j].equals("[O]")) {
                    playedPlace++;
                }
            }
        }
        if(playedPlace == 9) { // EVERY SQUARE HAS BEEN PLAYED, STALEMATE REACHED.
            System.out.println("NO MORE MOVES, NO WINNER FOUND, STALEMATE REACHED.");
            continueGame = false;
        }
        
        if(winner) {
            if(currPlayer == 0) {
                System.out.println("PLAYER ONE/[X] IS THE WINNER!");
            } else {
                System.out.println("PLAYER TWO/[O] IS THE WINNER!");
            }
            continueGame = false;
        }
        return continueGame;
    }
    
    public static void printBoard() {
        System.out.println("CURRENT BOARD:");
        for(int i=0; i < tictactoe.length; i++) {
            for(int j=0; j < tictactoe[0].length; j++) {
                System.out.print(tictactoe[i][j]);
            }
            System.out.println(); // Gap between rows
        }
    }
    
    public static void clrscr(){
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
}
