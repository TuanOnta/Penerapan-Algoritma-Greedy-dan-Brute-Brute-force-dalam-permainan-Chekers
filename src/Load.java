import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Load {

    private int turn;
    private int[][] board;

    public Load(int[][] board, int turn) {
        this.board = board;
        this.turn = turn;
    }

    public void load(String filePath) throws FileNotFoundException {
        try{
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
    
            // Read the turn information
            String line = scanner.nextLine();
            turn = Integer.parseInt(line);
    
            // Read the board size
            board = new int[8][8];
    
            // Read the board matrix
            for (int i = 0; i < 8; i++) {
                line = scanner.nextLine();
                String[] values = line.trim().split("\\s+"); // Split based on whitespace
    
                for (int j = 0; j < 8; j++) {
                    board[i][j] = Integer.parseInt(values[j]);
                }
            }
    
            scanner.close(); // Close the scanner after reading the file

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getTurn() {
        return turn;
    }
}
