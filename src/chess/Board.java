package chess;

public class Board {
	Piece[][] board;
	
	public Board() {
		board = new Piece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8 ; j++) {
				board[i][j] = null;
			}
		}
	}
	
	public void fill() {
		board[0][0] = new Rook(0, 0, "bR");
		board[0][1] = new Knight(0, 1, "bN");
		board[0][2] = new Bishop(0, 2, "bB");
		board[0][3] = new Queen(0, 3, "bQ");
		board[0][4] = new King(0, 4, "bK");
		board[0][5] = new Bishop(0, 5, "bB");
		board[0][6] = new Knight(0, 6, "bN");
		board[0][7] = new Rook(0, 7, "bR");
		
		for(int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(1, i, "bp");
			board[6][i] = new Pawn(1, i, "wp");
		}
		
		board[7][0] = new Rook(7, 0, "wR");
		board[7][1] = new Knight(7, 1, "wN");
		board[7][2] = new Bishop(7, 2, "wB");
		board[7][3] = new Queen(7, 3, "wQ");
		board[7][4] = new King(7, 4, "wK");
		board[7][5] = new Bishop(7, 5, "wB");
		board[7][6] = new Knight(7, 6, "wN");
		board[7][7] = new Rook(7, 7, "wR");
	}
	
	public Piece getPiece(int row, int col) {
		return board[row][col];
	}
	
	public void addPiece(Piece piece) {
		board[piece.getRow()][piece.getCol()] = piece;
	}
	
	public void removePiece(int row, int col) {
		board[row][col] = null;
	}
	
	public void display() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null)
					System.out.print(board[i][j].getText() + " ");
				else {
					if( (((i & 1) == 0) && ((j & 1) != 0)) ||
							(((i & 1) != 0) && ((j & 1) == 0)))
						System.out.print("## ");
					else
						System.out.print("   ");
				}
			}
			System.out.println(8-i);
		}
		System.out.println(" a  b  c  d  e  f  g  h");
	}
}