package chess;
import java.util.Scanner;

public class Chess {
	public static void main(String[] args) {
		boolean play = true;
		boolean draw = false;
		int player = 0; // white player = 0, black player = 1
		Scanner in = new Scanner(System.in);
		
		// fill board and display it
		Board board = new Board();
		board.fill();
		board.fillList();
		board.display();
		
		boolean enPassant = false;
		
		while(play) {
			// move prompt
			if(player == 0)
				System.out.print("White's move: ");
			else
				System.out.print("Black's move: ");
			String move = in.nextLine();
			
			// break if player chooses to resign
			if(move.equals("resign"))
				break;
			
			// break if player agrees to a draw
			if(draw) {
				if(move.equals("draw")) {
					System.out.println("Draw");
					break;
				}
				else
					draw = false;
			}
			
			// convert move input to appropriate row and col (e.g. e5 -> row 6, col 4)
			int startRow = getRowNum(move.charAt(1)), startCol = getColNum(move.charAt(0));
			int endRow = getRowNum(move.charAt(4)), endCol = getColNum(move.charAt(3));
			
			// check to make sure move has legal input
			if(startRow > 7 || endRow > 7 || startCol > 7 || endCol > 7) {
				System.err.println("Error: Move is not in correct formatting (FileRank FileRank)");
			} else {
				Piece piece = board.getPiece(startRow, startCol);
				if(piece != null && piece.isValidMove(board, endRow, endCol)) {
					enPassant = false;
					
					// move piece if valid move is entered
					board.movePiece(piece, endRow, endCol);
					
					if(move.length() == 11) {
						String append = move.substring(6);
						if(append.equals("draw?"))
							draw = true;
					}
					
					System.out.println();
					board.display();
					
					// check if en passant move is possible on next turn
					if(player == 0 && piece.getText() == "wp" && startRow == 6 && endRow == 4)
						enPassant = true;
					if(player == 1 && piece.getText() == "bp" && startRow == 1 && endRow == 3)
						enPassant = true;
					
					if(check(piece, board)) {
						System.out.println("Check");
					}
					
					// alternate turn
					if(player == 0)
						player++;
					else
						player--;
				} else if(enPassant == true && board.pieces[startRow][endCol] != null) {
					if(player == 0 && board.pieces[startRow][startCol].getText() == "wp") {
						if(board.pieces[startRow][endCol].getText() == "bp") {
							board.movePiece(piece, endRow, endCol);
							board.removePiece(startRow, endCol);
							
							System.out.println();
							board.display();
							
							// alternate turn
							if(player == 0)
								player++;
							else
								player--;
						}
					} else if(board.pieces[startRow][startCol].getText() == "bp") {
						if(board.pieces[startRow][endCol].getText() == "wp") {
							board.movePiece(piece, endRow, endCol);
							board.removePiece(startRow, endCol);
							
							System.out.println();
							board.display();
							
							// alternate turn
							if(player == 0)
								player++;
							else
								player--;
						}
					}
					enPassant = false;
				}
				else
					System.err.println("\nIllegal move, try again\n");
			}
		}
		in.close();
	}
	
	static int getRowNum(char c) {
		return '8' - c;
	}
	
	static int getColNum(char c) {
		return c - 'a';
	}
	
	static boolean check(Piece piece, Board board) {
		// fields to represent information for opponent's king
		Piece king = null;
		String kingText;
		int kingRow = 0, kingCol = 0;
		
		if(piece.getText().charAt(0) == 'w')
			kingText = "bK";
		else
			kingText = "wK";
		
		// find opponent's king and store it along with its location
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board.pieces[i][j] != null) {
					if(board.pieces[i][j].getText() == kingText) {
						king = board.pieces[i][j];
						kingRow = i;
						kingCol = j;
					}
				}
			}
		}
		
		board.removePiece(kingRow, kingCol); // temporarily remove king (ensures proper check using isValidMove)
		if(piece.isValidMove(board, kingRow, kingCol)) {
			board.addPiece(king);
			return true;
		}
		else {
			board.addPiece(king);
			return false;
		}
	}
	
	boolean checkmate(Board board, Piece king) {
		
		int row = king.getRow();
		int col = king.getCol();
		boolean w = false;
		if (king.getText().charAt(0) == 'w')
			w = true;
		
		//Whenever a king has a valid move, count is incremented and if a opposing piece can attack that valid move,
		//count is decremented. count == 0 -> checkmate
		int count = 0;
		
		if (king.isValidMove(board,row+1,col-1)){	//topleft
			count++;
			for (int i = 0; i < 16; i++){
				if (w){
					if (board.list[0][i].isValidMove(board, row+1, col-1)){
						count--;
						break;
					}
				}
				else if (board.list[1][i].isValidMove(board, row+1, col-1)){
						count--;
						break;
				}
			}
		}
		if (king.isValidMove(board,row+1,col))		//topmid
			count++;
			for (int i = 0; i < 16; i++){
				if (w){
					if (board.list[0][i].isValidMove(board, row+1, col)){
						count--;
						break;
					}
				}
				else if (board.list[1][i].isValidMove(board, row+1, col)){
					count--;
					break;
				}
			}
		if (king.isValidMove(board,row+1,col+1))	//topright
			count++;
			for (int i = 0; i < 16; i++){
				if (w){
					if (board.list[0][i].isValidMove(board, row+1, col+1)){
						count--;
						break;
					}
				}
				else if (board.list[1][i].isValidMove(board, row+1, col+1)){
					count--;
					break;
				}
			}
		if (king.isValidMove(board,row,col-1))		//midleft
			count++;
			for (int i = 0; i < 16; i++){
				if (w){
					if (board.list[0][i].isValidMove(board, row, col-1)){
						count--;
						break;
					}
				}
				else if (board.list[1][i].isValidMove(board, row, col-1)){
					count--;
					break;
				}
			}
		if (king.isValidMove(board,row,col+1))		//midright
			count++;
			for (int i = 0; i < 16; i++){
				if (w){
					if (board.list[0][i].isValidMove(board, row, col+1)){
						count--;
						break;
					}
				}
				else if (board.list[1][i].isValidMove(board, row, col+1)){
					count--;
					break;
				}
			}
		if (king.isValidMove(board,row-1,col-1))	//bottomleft
			count++;
			for (int i = 0; i < 16; i++){
				if (w){
					if (board.list[0][i].isValidMove(board, row-1, col-1)){
						count--;
						break;
					}
				}
				else if (board.list[1][i].isValidMove(board, row-1, col-1)){
					count--;
					break;
				}
			}
		if (king.isValidMove(board,row-1,col))		//bottommid
			count++;
			for (int i = 0; i < 16; i++){
				if (w){
					if (board.list[0][i].isValidMove(board, row-1, col)){
						count--;
						break;
					}
				}
				else if (board.list[1][i].isValidMove(board, row-1, col)){
					count--;
					break;
				}
			}
		if (king.isValidMove(board,row-1,col+1))	//bottomright
			count++;
			for (int i = 0; i < 16; i++){
				if (w){
					if (board.list[0][i].isValidMove(board, row-1, col+1)){
						count--;
						break;
					}
				}
				else if (board.list[1][i].isValidMove(board, row-1, col+1)){
					count--;
					break;
				}
			}
		
		if (count == 0)
			return true;
		return false;
	}
}