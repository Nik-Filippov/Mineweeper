import java.util.Random;

public class Board {
    private final int SIZE_N = 10, NUM_BOMBS = 10;
    private Cell[][] board;
    private Cell[] bombs;

    public Board() {
        board = new Cell[SIZE_N][SIZE_N];
        bombs = new Cell[NUM_BOMBS];
        for(int i = 0; i < SIZE_N; i++) {
            for(int j = 0; j < SIZE_N; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
        Random r = new Random();
        int counter = 0;
        for(int i = 0; i < SIZE_N; i++) {
            for(int j = 0; j < SIZE_N; j++) {
                if(counter < NUM_BOMBS) {
                    bombs[counter] = board[i][j].placeBomb();
                    counter++;
                } else { break; }
            }
        }
        for(int i = 0; i < SIZE_N; i++) {
            for (int j = 0; j < SIZE_N; j++) {
                Cell temp = board[i][j];
                int new_row = r.nextInt(SIZE_N);
                int new_col = r.nextInt(SIZE_N);
                board[i][j] = board[new_row][new_col].setRowCol(i, j);
                board[new_row][new_col] = temp.setRowCol(new_row, new_col);
            }
        }
//        for(int i = 0; i < bombs.length; i++) {
//            System.out.println(bombs[i]);
//        }

        for(int i = 0; i < NUM_BOMBS; i++){
            Cell bomb = bombs[i];
            if (bomb.getRow() > 0){
                if(bomb.getCol() > 0){
                    board[bomb.getRow() - 1][bomb.getCol() - 1].increment();
                    board[bomb.getRow()][bomb.getCol() - 1].increment();
                }
                board[bomb.getRow() - 1][bomb.getCol()].increment();
                if(bomb.getCol() + 1 < SIZE_N){
                    board[bomb.getRow() - 1][bomb.getCol() + 1].increment();
                    board[bomb.getRow()][bomb.getCol() + 1].increment();
                }
            }
            if (bomb.getRow() + 1 < SIZE_N){
                if(bomb.getCol() > 0){
                    board[bomb.getRow() + 1][bomb.getCol() - 1].increment();
                }
                board[bomb.getRow() + 1][bomb.getCol()].increment();
                if(bomb.getCol() + 1 < SIZE_N){
                    board[bomb.getRow() + 1][bomb.getCol() + 1].increment();
                }
            }
            if(bomb.getRow() == 0){
                if(bomb.getCol() > 0){
                    board[bomb.getRow()][bomb.getCol() - 1].increment();
                }
                if(bomb.getCol() + 1 < SIZE_N){
                    board[bomb.getRow()][bomb.getCol() + 1].increment();
                }
            }
        }
    }

    public void openBoard(){
        for(int i = 0; i < SIZE_N; i++) {
            for (int j = 0; j < SIZE_N; j++) {
                board[i][j].flip();
            }
        }
    }

    public boolean expose(int x, int y) {
        if(x < 0 || x >= SIZE_N || y < 0 || y >= SIZE_N || board[x][y].isExposed()) {
            return true;
        }
        Cell c = board[x][y];
        if(c.getNumber() == 0){
            exposeZeroes(x, y);
        }
        else{
            board[x][y].flip();
        }
        return !c.isBomb();
    }

    public int exposeZeroes(int x, int y){
        if(x < 0 || y < 0 || x >= SIZE_N || y >= SIZE_N || board[x][y].isExposed()){
            return 0;
        }
        board[x][y].flip();
        if(board[x][y].getNumber() > 0){
            return 1;
        }
        return exposeZeroes(x - 1, y - 1) + exposeZeroes(x, y - 1) + exposeZeroes(x - 1, y) +
                exposeZeroes(x + 1, y + 1) + exposeZeroes(x + 1, y) + exposeZeroes(x, y + 1) +
                exposeZeroes(x - 1, y + 1) + exposeZeroes(x + 1, y - 1);
    }

    public boolean isAllCellsExposed() {
        for(int i = 0; i < SIZE_N; i++){
            for(int j = 0; j < SIZE_N; j++){
                if(!board[i][j].isExposed() && !board[i][j].isBomb()){
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        System.out.print("  ");
        for (int col = 0; col < SIZE_N; col++) {
            System.out.print(col + " ");
        }
        System.out.println();
        for (int row = 0; row < SIZE_N; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < SIZE_N; col++) {
                if (board[row][col].isExposed()) {
                    if (board[row][col].isBomb()) {
                        System.out.print("* ");
                    } else {
                        System.out.print(board[row][col].getNumber() + " ");
                    }
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
}
