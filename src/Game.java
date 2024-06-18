import java.util.Optional;
import java.util.Scanner;

public class Game {
    public enum GameState {WON, LOST, RUNNING};
    private Board board;
    private GameState gs;

    public Game(){
        board = new Board();
        gs = GameState.RUNNING;
    }

    public void playGame(){
        Scanner scanner = new Scanner(System.in);
        while(this.gs == GameState.RUNNING){
            board.printBoard();
            System.out.println("Enter row and column to expose (e.g., '3 4'):");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            if(!board.expose(x, y)){
                gs = GameState.LOST;
                System.out.println("Game Over! You hit a bomb.");
                board.printBoard();
            }
            if(board.isAllCellsExposed()){
                gs = GameState.WON;
                System.out.println("Congratulations! You won.");
                board.printBoard();
            }
        }
        scanner.close();
    }
}
