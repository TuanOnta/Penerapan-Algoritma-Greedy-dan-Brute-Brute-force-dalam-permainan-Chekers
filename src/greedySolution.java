import java.util.ArrayList;
import java.util.List;

/**
 * greedySolution
 */
public class greedySolution {
    private int[][] board;
    private int size;
    private int turn; // 1 for player 1, 2 for player 2

    public greedySolution(int[][] board, int size, int turn) {
        this.board = board;
        this.size = size;
        this.turn = turn;
    }

    public void solution() {
        System.out.println("Solusi menggunakan algoritma Greedy:");
        printBoard();
        
        List<String> listPiece = findAllPieceHasMove();
        if (listPiece.isEmpty()) {
            System.out.println("Tidak ada bidak yang bisa bergerak player " + turn + " kalah.");
            return;
        }
        
        List<String> bestMove = findBestMove(listPiece);
        if (bestMove.isEmpty()) {
            System.out.println("Tidak ada bidak yang bisa makan");
            String piece = listPiece.get(0);
            int x = Integer.parseInt(piece.split(" ")[0]);
            int y = Integer.parseInt(piece.split(" ")[1]);
            String move = findMove(piece);
            if (move != null) {
                System.out.println("Player " + turn + " bergerak dari " + x + " " + y + " ke " + move);
            } else {
                System.out.println("Tidak ada langkah yang bisa dilakukan player " + turn + " kalah.");
            }
        } else {
            executeBestMove(bestMove);
        }
    }

    private void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String findMove(String piece) {
        int x = Integer.parseInt(piece.split(" ")[0]);
        int y = Integer.parseInt(piece.split(" ")[1]);
        if (turn == 1) {
            if (x - 1 >= 0 && y - 1 >= 0 && board[y - 1][x - 1] == 0) {
                return (x - 1) + " " + (y - 1);
            } else if (x + 1 < size && y - 1 >= 0 && board[y - 1][x + 1] == 0) {
                return (x + 1) + " " + (y - 1);
            }
        } else {
            if (x - 1 >= 0 && y + 1 < size && board[y + 1][x - 1] == 0) {
                return (x - 1) + " " + (y + 1);
            } else if (x + 1 < size && y + 1 < size && board[y + 1][x + 1] == 0) {
                return (x + 1) + " " + (y + 1);
            }
        }
        return null;
    }

    public List<String> findAllPieceHasMove() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((turn == 1 && board[i][j] == 1) || (turn == 2 && board[i][j] == 2)) {
                    if (isPieceHasMove(j, i)) {
                        list.add(j + " " + i);
                    }
                }
            }
        }
        return list;
    }

    public boolean isPieceHasMove(int x, int y) {
        if (turn == 2) {
            return (x - 1 >= 0 && y + 1 < size && board[y + 1][x - 1] == 0) ||
                   (x + 1 < size && y + 1 < size && board[y + 1][x + 1] == 0) ||
                   isPieceCanJumpLeft(x, y) || isPieceCanJumpRight(x, y);
        } else {
            return (x - 1 >= 0 && y - 1 >= 0 && board[y - 1][x - 1] == 0) ||
                   (x + 1 < size && y - 1 >= 0 && board[y - 1][x + 1] == 0) ||
                   isPieceCanJumpLeft(x, y) || isPieceCanJumpRight(x, y);
        }
    }

    public boolean isPieceCanJumpLeft(int x, int y) {
        if (turn == 2) {
            return x - 2 >= 0 && y + 2 < size && board[y + 1][x - 1] == 1 && board[y + 2][x - 2] == 0;
        } else {
            return x - 2 >= 0 && y - 2 >= 0 && board[y - 1][x - 1] == 2 && board[y - 2][x - 2] == 0;
        }
    }

    public boolean isPieceCanJumpRight(int x, int y) {
        if (turn == 2) {
            return x + 2 < size && y + 2 < size && board[y + 1][x + 1] == 1 && board[y + 2][x + 2] == 0;
        } else {
            return x + 2 < size && y - 2 >= 0 && board[y - 1][x + 1] == 2 && board[y - 2][x + 2] == 0;
        }
    }

    public List<String> jumpPieceList(int x, int y, List<String> result) {
        if (x < 0 || x >= size || y < 0 || y >= size || !isPieceHasMove(x, y)) {
            return result;
        } else {
            if (isPieceCanJumpLeft(x, y)) {
                result.add((x - 2) + " " + (turn == 2 ? y + 2 : y - 2));
                jumpPieceList(x - 2, (turn == 2 ? y + 2 : y - 2), result);
            }
            if (isPieceCanJumpRight(x, y)) {
                result.add((x + 2) + " " + (turn == 2 ? y + 2 : y - 2));
                jumpPieceList(x + 2, (turn == 2 ? y + 2 : y - 2), result);
            }
            return result;
        }
    }

    public boolean isPieceCanBeKing(int x, int y) {
        List<String> jumps = new ArrayList<>();
        List<String> list = jumpPieceList(x, y, jumps);
        for (String jump : list) {
            int newY = Integer.parseInt(jump.split(" ")[1]);
            if ((turn == 2 && newY == size - 1) || (turn == 1 && newY == 0)) {
                return true;
            }
        }
        return false;
    }

    public List<String> findBestMove(List<String> listPiece) {
        List<String> bestMoves = new ArrayList<>();
        String bestPiece = listPiece.get(0);
        int maxJumps = 0;

        

        for (String piece : listPiece) {
            int x = Integer.parseInt(piece.split(" ")[0]);
            int y = Integer.parseInt(piece.split(" ")[1]);
            if(isPieceCanBeKing(x, y)) {
                bestPiece = piece;
                List<String> jumps = jumpPieceList(x, y, new ArrayList<>());
                int jumpCount = jumps.size();
                System.out.println("Bidak " + x + " " + y + " bisa melompati " + jumpCount + " bidak");
                if (jumpCount > maxJumps) {
                    maxJumps = jumpCount;
                    bestPiece = piece;
                }
            }
        }
        
        bestMoves.add(bestPiece);
        bestMoves.addAll(jumpPieceList(
            Integer.parseInt(bestPiece.split(" ")[0]),
            Integer.parseInt(bestPiece.split(" ")[1]),
            new ArrayList<>()
        ));
        
        return bestMoves;
    }

    private void executeBestMove(List<String> bestMove) {
        char x = (char) ('A' + Integer.parseInt(bestMove.get(0).split(" ")[0]));
        int y = Integer.parseInt(bestMove.get(0).split(" ")[1]);
        String index = x + "" + (8 - y);
        System.out.println("Player " + turn + " bergerak dari " + index);
        if(bestMove.size() == 1) {
            String move = findMove(bestMove.get(0));
            x =(char)('A' +  Integer.parseInt(move.split(" ")[0]));
            y = Integer.parseInt(move.split(" ")[1]);
            String hasil = x + "" + (8 - y);
            System.out.println("Player " + turn + " bergerak ke " + hasil);

        }
        for (int i = 1; i < bestMove.size(); i++) {
            x = (char) ('A' + Integer.parseInt(bestMove.get(i).split(" ")[0]));
            y = Integer.parseInt(bestMove.get(i).split(" ")[1]);
            index = x + "" + (8 - y);
            System.out.println("Player " + turn + " melompati " + index);
        }
    }
}
