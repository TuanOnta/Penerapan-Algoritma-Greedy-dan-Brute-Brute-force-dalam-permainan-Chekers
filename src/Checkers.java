import java.util.List;
import java.util.Scanner;

public class Checkers {
    static final int EMPTY = 0;
    static final int bidak1 = 1;
    static final int bidak2 = 2;

    static int[][] board;
    static final int size = 8;
    static int turn;

    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
    
            board = new int[8][8];
    
            System.out.println("Selamat datang di penyelesaian Checkers!");
            System.out.println("aturan:");
            System.out.println("nomor 1 melambangkan pieces player 1");
            System.out.println("nomor 2 melambangkan pieces player 2");
            System.out.println("nomor 0 melambangkan kotak kosong");
            System.out.println("User akan memasukan pieces ke dalam board dan sistem akan mencari solusi terbaik");
    
            System.out.println("\n\n");
            System.out.println("Apakah anda ingin masukan file?(y/n)");
            boolean file = false;
            String input = scanner.nextLine();
            while (input != "y" || input != "n") {
                if (input.equals("y")) {
                    file = true;
                    break;
                } else if (input.equals("n")) {
                    file = false;
                    break;
                } else {
                    System.out.println("Pilihan tidak valid. Apakah anda ingin masukan file?(y/n)");
                    input = scanner.nextLine();
                }
            }
    
            if (file){
                System.out.println("Pastikan file sudah berada di directory test");
                System.out.println("Masukkan nama file (tanpa .txt): ");
                String fileName = scanner.nextLine();
                Load ld = new Load(board, turn);
                ld.load("test/" + fileName + ".txt");
                board = ld.getBoard();
                turn = ld.getTurn();
                System.out.println("Turn player: " + turn);
                System.out.println("Papan saat ini: ");
                printBoard();
            }else{
                System.out.println();
                System.out.print("Silahkan pilih turn player 1 atau player 2 (1/2): ");
                turn = 0;
                while (turn != 1 || turn != 2) {
                    turn = scanner.nextInt();
                    if (turn == 1 || turn == 2) {
                        break;
                    } else {
                        System.out.print("Pilihan tidak valid. Silahkan pilih turn player 1 atau player 2 (1/2): ");
                    }
                }
        
                System.out.println("Masukkan matriks papan: ");
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        char kolom = (char)('A' + j);
                        int baris = 8 - i;
                        String index =  kolom + String.valueOf(baris);
                        System.out.print("Masukkan nilai untuk " + index + ": ");
                        board[i][j] = scanner.nextInt();
                    }
                }
            }
    
    
            System.out.println("Pilih algoritma: 1 untuk Greedy, 2 untuk Brute Force: ");
            int choice = scanner.nextInt();
    
            if (choice == 1) {
                greedySolution greedy = new greedySolution(board, size, turn);
                List<String> listPiece = greedy.findAllPieceHasMove();
                if (listPiece.size() == 0) {
                    System.out.println("Tidak ada bidak yang bisa bergerak player " + turn + " kalah.");
                    return;
                }
                greedy.solution();
                
            } else if (choice == 2) {
                System.out.println("Solusi menggunakan algoritma Brute Force:");
                bruteForceSolution bruteForce = new bruteForceSolution(board, size, turn);
                List<String> listPiece = bruteForce.findAllPieceHasMove();
                if (listPiece.size() == 0) {
                    System.out.println("Tidak ada bidak yang bisa bergerak player " + turn + " kalah.");
                    return;
                }
                bruteForce.solution();
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    
    }

    private static void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
