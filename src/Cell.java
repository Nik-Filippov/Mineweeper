public class Cell {
    private int row, col, number = 0;
    private boolean isBomb,isGuess = false, isExposed = false;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Cell flip(){
        isExposed = true;
        return this;
    }

    public boolean toggleGuess(){
        if(!isExposed){
            isGuess = !isGuess;
        }
        return isGuess;
    }

    public Cell placeBomb(){
        isBomb = true;
        return this;
    }

    public int getNumber(){
        return number;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isGuess() {
        return isGuess;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Cell setRowCol(int row, int col){
        this.row = row;
        this.col = col;
        return this;
    }
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    public void increment(){
        number++;
    }
}
