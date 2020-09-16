package chess;
import java.util.Scanner;

/**
 * Chess class
 * @author Amy Guinto
 * @author Aziz Rahman
 *
 */

public class Chess {
	/**
	 * main method provides functionality for playing chess
	 * @param args
	 */
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
		boolean illegal = false;
		Piece checkPiece = null;

		while(play) {
			// move prompt
			if(player == 0)
				System.out.print("White's move: ");
			else
				System.out.print("Black's move: ");
			String move = in.nextLine();

			// break if player chooses to resign
			if(move.equals("resign")) {
				System.out.println("\nDraw");
				break;
			}

			// break if player agrees to a draw
			if(draw) {
				if(move.equals("draw")) {
					System.out.println("\nDraw");
					break;
				}
				else
					draw = false;
			}

			if(move.length() < 5) {
				System.out.println("\nError: Move is not in correct formatting (FileRank FileRank)\n");
			} else {
				// convert move input to appropriate row and col (e.g. e5 -> row 6, col 4)
				int startRow = getRowNum(move.charAt(1)), startCol = getColNum(move.charAt(0));
				int endRow = getRowNum(move.charAt(4)), endCol = getColNum(move.charAt(3));

				// check to make sure move has legal input
				if(startRow > 7 || endRow > 7 || startCol > 7 || endCol > 7
						|| startRow < 0 || endRow < 0 || startCol < 0 || endCol < 0) {
					System.out.println("\nError: Move is not in correct formatting (FileRank FileRank)\n");
				} else {
					Piece piece = board.getPiece(startRow, startCol);

					// checks for correct color piece
					if(piece != null && ((player == 0 && piece.getText().charAt(0) != 'w')
							|| (player == 1 && piece.getText().charAt(0) != 'b')) ) {
						System.out.println("\nIllegal move, try again\n");
					}
					// checks for basic move
					else if(piece != null && piece.isValidMove(board, endRow, endCol)) {
						enPassant = false;

						// move piece if valid move is entered
						board.movePiece(piece, endRow, endCol);

						illegal = false;
						if(checkPiece != null) {
							if(check(checkPiece, board) == 1) {
								System.out.println("\nIllegal move, try again\n");
								board.movePiece(piece, startRow, startCol);
								illegal = true;
							}
							else
								checkPiece = null;
						}

						if(move.length() == 11) {
							String append = move.substring(6);
							if(append.equals("draw?"))
								draw = true;
						}

						// checks for promotion
						if(piece.getText().charAt(1) == 'p') {
							if(endRow == 0) {
								if(move.length() == 5) {
									board.pieces[endRow][endCol] = new Queen(endRow, endCol, "wQ");
									board.pieces[endRow][endCol].hasMoved = true;
								} else if(move.charAt(6) == 'R' || move.charAt(6) == 'N' || move.charAt(6) == 'B'){
									if(move.charAt(6) == 'R')
										board.pieces[endRow][endCol] = new Rook(endRow, endCol, "wR");
									else if(move.charAt(6) == 'N')
										board.pieces[endRow][endCol] = new Knight(endRow, endCol, "wN");
									else if(move.charAt(6) == 'B')
										board.pieces[endRow][endCol] = new Bishop(endRow, endCol, "wB");

									board.pieces[endRow][endCol].hasMoved = true;
								} else {
									System.out.println("\nError: Incorrect promotion type\n");
								}
							} else if(endRow == 7) {
								if(move.length() == 5) {
									board.pieces[endRow][endCol] = new Queen(endRow, endCol, "bQ");
									board.pieces[endRow][endCol].hasMoved = true;
								} else if(move.charAt(6) == 'R' || move.charAt(6) == 'N' || move.charAt(6) == 'B'){
									if(move.charAt(6) == 'R')
										board.pieces[endRow][endCol] = new Rook(endRow, endCol, "bR");
									else if(move.charAt(6) == 'N')
										board.pieces[endRow][endCol] = new Knight(endRow, endCol, "bN");
									else if(move.charAt(6) == 'B')
										board.pieces[endRow][endCol] = new Bishop(endRow, endCol, "bB");

									board.pieces[endRow][endCol].hasMoved = true;
								} else {
									System.out.println("\nError: Incorrect promotion type\n");
								}
							}
						}

						if(!illegal) {
							System.out.println();
							board.display();
						}

						// check if en passant move is possible on next turn
						if(player == 0 && piece.getText() == "wp" && startRow == 6 && endRow == 4)
							enPassant = true;
						if(player == 1 && piece.getText() == "bp" && startRow == 1 && endRow == 3)
							enPassant = true;

						// checks for check and checkmate
						if(check(piece, board) == 1) {
							System.out.println("\nCheck\n");
							checkPiece = piece;
						}
						else if(check(piece, board) == 2) {
							System.out.println("\nCheckmate\n");
							if(player == 0)
								System.out.println("\nWhite wins");
							else
								System.out.println("\nBlack wins");
							break;
						}

						// alternate turn
						if(!illegal) {
							if(player == 0)
								player++;
							else
								player--;
						}

					}
					// checks for en Passant move
					else if(enPassant == true && board.pieces[startRow][endCol] != null) {
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
						} else if(piece != null && board.pieces[startRow][startCol].getText() == "bp") {
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
					// checks for castling move
					else if(piece != null && piece.getText().charAt(1) == 'K' && piece.hasMoved == false) {
						if(startRow == endRow && startCol+2 == endCol && board.pieces[endRow][7] != null) {
							if(board.pieces[endRow][7].hasMoved == false) {
								board.movePiece(piece, endRow, endCol);
								Piece rook = board.pieces[endRow][7];
								board.movePiece(rook, endRow, 5);

								System.out.println();
								board.display();

								// alternate turn
								if(player == 0)
									player++;
								else
									player--;
							} else {
								System.out.println("\nIllegal move, try again\n");
							}
						} else if(startRow == endRow && startCol-2 == endCol && board.pieces[endRow][0] != null) {
							if(board.pieces[endRow][0].hasMoved == false) {
								board.movePiece(piece, endRow, endCol);
								Piece rook = board.pieces[endRow][0];
								board.movePiece(rook, endRow, 3);

								System.out.println();
								board.display();

								// alternate turn
								if(player == 0)
									player++;
								else
									player--;
							} else {
								System.out.println("\nIllegal move, try again\n");
							}
						}
					}
					else
						System.out.println("\nIllegal move, try again\n");
				}
			}		
		}
		in.close();
	}

	/**
	 * getRowNum returns the corresponding row index given the rank of a move
	 * @param c	is the character representing the rank
	 * @return
	 */
	static int getRowNum(char c) {
		return '8' - c;
	}

	/**
	 * getColNum returns the corresponding column index given the file of a move
	 * @param c is the character representing the file
	 * @return
	 */
	static int getColNum(char c) {
		return c - 'a';
	}

	/**
	 * check determines if a moved piece puts its opponent's king in check
	 * @param piece	is the piece that was just moved
	 * @param board is the board with all of its pieces
	 * @return
	 */
	static int check(Piece piece, Board board) {
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
					if(board.pieces[i][j].getText().equals(kingText)) {
						king = board.pieces[i][j];
						kingRow = i;
						kingCol = j;
					}
				}
			}
		}


		if(piece.isValidMove(board, kingRow, kingCol)) {
			if(checkmate(board, king, piece)) {
				return 2;
			}
			return 1;
		}
		else {	
			return 0;
		}
	}

	/**
	 * checkmate determines if a moved piece put its opponent's king in checkmate 
	 * @param board	is the board and all of its pieces
	 * @param king	is the opponent's king
	 * @param piece	is the piece that was moved
	 * @return
	 */
	static boolean checkmate(Board board, Piece king, Piece piece) {

		int row = king.getRow();
		int col = king.getCol();
		boolean knight = false;
		boolean pawn = false;
		boolean w = false;
		if (king.getText().charAt(0) == 'w')
			w = true;;

			if (piece instanceof Knight)
				knight = true;
			if (piece instanceof Pawn)
				pawn = true;




			//Whenever a king has a valid move, count is incremented and if a opposing piece can attack that valid move,
			//Check every route for every valid move the king has and determine if a friendly piece can go there
			//count is decremented. count == 0 -> checkmate
			int count = 0;
			//Stores every location that leads to an enemy piece
			boolean[][] storage = new boolean[8][8];

			if (row > 0 && col > 0 && king.isValidMove(board,row-1,col-1)){	//topleft
				count++;
				for (int i = 0; i < 16; i++){
					if (w){
						if (board.list[1][i].isValidMove(board, row-1, col-1)){
							count--;
							break;
						}
					}
					else if (board.list[0][i].isValidMove(board, row-1, col-1)){
						count--;
						break;
					}
				}
			}
			if (row > 0 && king.isValidMove(board,row-1,col)) {		//topmid
				count++;
				for (int i = 0; i < 16; i++){
					if (w){
						if (board.list[1][i].isValidMove(board, row-1, col)){
							count--;
							break;
						}
					}
					else if (board.list[0][i].isValidMove(board, row-1, col)){
						count--;
						break;
					}
				}
			}
			if (row > 0 && col < 7 && king.isValidMove(board,row-1,col+1)) {	//topright
				count++;
				for (int i = 0; i < 16; i++){
					if (w){
						if (board.list[1][i].isValidMove(board, row-1, col+1)){
							count--;
							break;
						}
					}
					else if (board.list[0][i].isValidMove(board, row-1, col+1)){
						count--;
						break;
					}
				}
			}
			if (col > 0 && king.isValidMove(board,row,col-1)) {		//midleft
				count++;
				for (int i = 0; i < 16; i++){
					if (w){
						if (board.list[1][i].isValidMove(board, row, col-1)){
							count--;
							break;
						}
					}
					else if (board.list[0][i].isValidMove(board, row, col-1)){
						count--;
						break;
					}
				}
			}
			if (col < 7 && king.isValidMove(board,row,col+1)) {		//midright
				count++;
				for (int i = 0; i < 16; i++){
					if (w){
						if (board.list[1][i].isValidMove(board, row, col+1)){
							count--;
							break;
						}
					}
					else if (board.list[0][i].isValidMove(board, row, col+1)){
						count--;
						break;
					}
				}
			}
			if (row < 7 && col > 0 && king.isValidMove(board,row+1,col-1)) {	//bottomleft
				count++;
				for (int i = 0; i < 16; i++){
					if (w){
						if (board.list[1][i].isValidMove(board, row+1, col-1)){
							count--;
							break;
						}
					}
					else if (board.list[0][i].isValidMove(board, row+1, col-1)){
						count--;
						break;
					}
				}
			}
			if (row < 7 && king.isValidMove(board,row+1,col)) {		//bottommid
				count++;
				for (int i = 0; i < 16; i++){
					if (w){
						if (board.list[1][i].isValidMove(board, row+1, col)){
							count--;
							break;
						}
					}
					else if (board.list[0][i].isValidMove(board, row+1, col)){
						count--;
						break;
					}
				}
			}
			if (row < 7 && col < 7 && king.isValidMove(board,row+1,col+1)) {	//bottomright
				count++;
				for (int i = 0; i < 16; i++){
					if (w){
						if (board.list[1][i].isValidMove(board, row+1, col+1)){
							count--;
							break;
						}
					}
					else if (board.list[0][i].isValidMove(board, row+1, col+1)){
						count--;
						break;
					}
				}
			}

			if (w)
				w = false;
			else
				w = true;

			//Check knight and Pawn, then 2D, then 1D
			if (count == 0){
				int count2 = 0;
				if (w){
					for (int i = 0; i < 16; i++){
						if (board.list[1][i].isValidMove(board, row, col))
							count2++;
						if (count2 == 2)
							return true;
					}
				}
				else {
					for (int i = 0; i < 16; i++){
						if (board.list[0][i].isValidMove(board, row, col))
							count2++;
						if (count2 == 2)
							return true;
					}
				}

				boolean dontRun = false;

				if (knight || pawn){
					storage[piece.getRow()][piece.getCol()] = true;
					dontRun = true;
				}

				if (!dontRun){
					outerloop:
						for (int j = row+1, k = col+1; j < 8; j++, k++){
							if(k == 8)
								break;
							// Bottom-Right
							if (board.pieces[j][k] != null)
								storage[j][k] = true;
							else if (board.pieces[j][k] instanceof Queen || board.pieces[j][k] instanceof Bishop){
								if (w){
									if (board.pieces[j][k].getText().charAt(0) == 'b'){
										storage [j][k] = true;
										dontRun = true;
										break outerloop;
									}
								} else if (board.pieces[j][k].getText().charAt(0) == 'w'){
									storage [j][k] = true;
									dontRun = true;
									break outerloop;
								}
							}
							else {
								if(j == row && k == col)
									break outerloop;
								dontRun = true;
								break outerloop;
							}

						}
				if(!dontRun)
					clear(storage);
				}
				if (!dontRun){
					outerloop:
						for (int j = row+1, k = col-1; j < 8; j++, k--){	
							if(k == -1)
								break;
							// Bottom-Left
							if (board.pieces[j][k] != null)
								storage[j][k] = true;
							else if (board.pieces[j][k] instanceof Queen || board.pieces[j][k] instanceof Bishop){
								if (w){
									if (board.pieces[j][k].getText().charAt(0) == 'b'){
										storage [j][k] = true;
										dontRun = true;
										break outerloop;
									}
								} else if (board.pieces[j][k].getText().charAt(0) == 'w'){
									storage [j][k] = true;
									dontRun = true;
									break outerloop;
								}
							}
							else {
								if(j == row && k == col)
									break outerloop;
								dontRun = true;
								break outerloop;
							}

						}
				if(!dontRun)
					clear(storage);
				}

				if (!dontRun){
					outerloop:
						for (int j = row-1, k = col+1; j >= 0; k++, j--){			
							// Top-Right
							if(k == 8)
								break;
							if (board.pieces[j][k] != null)
								storage[j][k] = true;
							else if (board.pieces[j][k] instanceof Queen || board.pieces[j][k] instanceof Bishop){
								if (w){
									if (board.pieces[j][k].getText().charAt(0) == 'b'){
										storage [j][k] = true;
										dontRun = true;
										break outerloop;
									}
								} else if (board.pieces[j][k].getText().charAt(0) == 'w'){
									storage [j][k] = true;
									dontRun = true;
									break outerloop;
								}
							}
							else {
								if(j == row && k == col)
									break outerloop;
								dontRun = true;
								break outerloop;
							}

						}
				if(!dontRun)
					clear(storage);
				}
				if (!dontRun){
					outerloop:
						for (int j = row-1, k = col-1; j >= 0; j--, k--){
							// Top-Left
							if(k == -1)
								break;
							if (board.pieces[j][k] != null)
								storage[j][k] = true;
							else if (board.pieces[j][k] instanceof Queen || board.pieces[j][k] instanceof Bishop){
								if (w){
									if (board.pieces[j][k].getText().charAt(0) == 'b'){
										storage [j][k] = true;
										dontRun = true;
										break outerloop;
									}
								} else if (board.pieces[j][k].getText().charAt(0) == 'w'){
									storage [j][k] = true;
									dontRun = true;
									break outerloop;
								}
								else {
									if(j == row && k == col)
										break outerloop;
									dontRun = true;
									break outerloop;
								}
							}

						}
				if(!dontRun)
					clear(storage);
				}

				if (!dontRun){
					for (int j = row; j >= 0; j--){			// Up
						if (board.pieces[j][col] != null)
							storage[j][col] = true;
						else if (board.pieces[j][col] instanceof Queen || board.pieces[j][col] instanceof Rook){
							if (w){
								if (board.pieces[j][col].getText().charAt(0) == 'b'){
									storage [j][col] = true;
									dontRun = true;
									break;
								}
							} else if (board.pieces[j][col].getText().charAt(0) == 'w'){
								storage [j][col] = true;
								dontRun = true;
								break;
							}
							else {
								if(j == row)
									break;
								dontRun = true;
								break;
							}
						}
					}
					if(!dontRun)
						clear(storage);
				}
				if (!dontRun){
					for (int j = col; j <= 7; j++){			// Right
						if (board.pieces[row][j] != null)
							storage[row][j] = true;
						else if (board.pieces[row][j] instanceof Queen || board.pieces[row][j] instanceof Rook){
							if (w){
								if (board.pieces[row][j].getText().charAt(0) == 'b'){
									storage [row][j] = true;
									dontRun = true;
									break;
								}
							} else if (board.pieces[row][j].getText().charAt(0) == 'w'){
								storage [row][j] = true;
								dontRun = true;
								break;
							}
							else {
								if(j == col)
									break;
								dontRun = true;
								break;
							}
						}
					}
					if(!dontRun)
						clear(storage);
				}

				if (!dontRun){
					for (int j = row; j <= 7; j++){			// Down
						if (board.pieces[j][col] != null)
							storage[j][col] = true;
						else if (board.pieces[j][col] instanceof Queen || board.pieces[j][col] instanceof Rook){
							if (w){
								if (board.pieces[j][col].getText().charAt(0) == 'b'){
									storage [j][col] = true;
									dontRun = true;
									break;
								}
							} else if (board.pieces[j][col].getText().charAt(0) == 'w'){
								storage [j][col] = true;
								dontRun = true;
								break;
							}
							else {
								if(j == row)
									break;
								dontRun = true;
								break;
							}
						}
					}
					if(!dontRun)
						clear(storage);
				}

				if (!dontRun){
					for (int j = col; j >= 0; j--){			// Left
						if (board.pieces[row][j] != null)
							storage[row][j] = true;
						else if (board.pieces[row][j] instanceof Queen || board.pieces[row][j] instanceof Rook){
							if (w){
								if (board.pieces[row][j].getText().charAt(0) == 'b'){
									storage [row][j] = true;
									dontRun = true;
									break;
								}
							} else if (board.pieces[row][j].getText().charAt(0) == 'w'){
								storage [row][j] = true;
								dontRun = true;
								break;
							}
							else {
								if(j == col)
									break;
								dontRun = true;
								break;
							}
						}
					}
					if(!dontRun)
						clear(storage);
				}
				if (cmHelper(board, storage, w))
					return false;
				else
					return true;
			}

			return false;
	}

	/**
	 * clear initializes a boolean array to false
	 * @param b is the array to be cleared
	 */
	static void clear(boolean[][] b){
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				b[i][j] = false;
			}
		}
	}

	/**
	 * cmHelper checks if every allied piece can take you out of check
	 * @param board	is the game board with all its pieces
	 * @param b		is a 2D boolean array
	 * @param w		is the opponent's color
	 * @return
	 */
	static boolean cmHelper(Board board, boolean[][] b, boolean w){

		if (w){
			for (int i = 0; i < 8; i++){
				for (int j = 0; j < 8; j++){
					if (b[i][j]){
						for (int k = 0; k < 16; k++){
							if (board.list[0][k].isValidMove(board, i, j))
								return true;
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < 8; i++){
				for (int j = 0; j < 8; j++){
					if (b[i][j]){
						for (int k = 0; k < 16; k++){
							if (board.list[1][k].isValidMove(board, i, j))
								return true;
						}
					}
				}
			}
		}
		return false;
	}
}