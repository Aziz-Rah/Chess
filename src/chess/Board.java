package chess;

public class Board {
	Piece[][] pieces;
	
	public Board() {
		pieces = new Piece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8 ; j++) {
				pieces[i][j] = null;
			}
		}
	}
	
	public void fill() {
		pieces[0][0] = new Rook(0, 0, "bR");
		pieces[0][1] = new Knight(0, 1, "bN");
		pieces[0][2] = new Bishop(0, 2, "bB");
		pieces[0][3] = new Queen(0, 3, "bQ");
		pieces[0][4] = new King(0, 4, "bK");
		pieces[0][5] = new Bishop(0, 5, "bB");
		pieces[0][6] = new Knight(0, 6, "bN");
		pieces[0][7] = new Rook(0, 7, "bR");
		
		for(int i = 0; i < 8; i++) {
			pieces[1][i] = new Pawn(1, i, "bp");
			pieces[6][i] = new Pawn(1, i, "wp");
		}
		
		pieces[7][0] = new Rook(7, 0, "wR");
		pieces[7][1] = new Knight(7, 1, "wN");
		pieces[7][2] = new Bishop(7, 2, "wB");
		pieces[7][3] = new Queen(7, 3, "wQ");
		pieces[7][4] = new King(7, 4, "wK");
		pieces[7][5] = new Bishop(7, 5, "wB");
		pieces[7][6] = new Knight(7, 6, "wN");
		pieces[7][7] = new Rook(7, 7, "wR");
	}
	
	public Piece getPiece(int row, int col) {
		return pieces[row][col];
	}
	
	public void addPiece(Piece piece) {
		pieces[piece.getRow()][piece.getCol()] = piece;
	}
	
	public void removePiece(int row, int col) {
		pieces[row][col] = null;
	}
	
	public void display() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(pieces[i][j] != null)
					System.out.print(pieces[i][j].getText() + " ");
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
		System.out.println(" a  b  c  d  e  f  g  h\n");
	}
}