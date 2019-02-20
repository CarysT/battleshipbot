package battleshipbot;

public class Board {
    private String[][] board;
    public Board() {
        board = new String[10][10];
        initialize();
    }
    public int getWidth(int x) {
        return board[0].length;
    }
    public int getHeight() {
        return board.length;
    }
    public void setPosition(int x, int y, String t) {
        board[x][y] = t;
    }
    private void initialize() {
        for (int i = 0; i < board.length;i++) {
            for (int j = 0; j < board[i].length;j++) {
                board[i][j] = "0";
            }
        }
    }
    public String showBoard() {
        String fullBoard = "| ABCDEFGHIJ\n";
        for (int i = 0; i < board.length; i++) {
            fullBoard += (i + " ");
            for (String board11 : board[i]) {
                fullBoard += board11;
            }
            fullBoard += "\n";
        }
        return fullBoard;
    }
    public boolean isAHit(int x, int y) {
        boolean isAHit = false;
        if (Integer.parseInt(board[x][y]) > 1) {
            board[x][y] = "1";
            isAHit = true;
        }
        board[x][y] = "-1";
        return isAHit;
    }
}
