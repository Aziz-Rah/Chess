package chess;

public class Board {
	Piece[][] pieces;
	Piece[][] list;
	
	public Board() {
		pieces = new Piece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8 ; j++) {
				pieces[i][j] = null;
			}
		}
	}
	
	public void fillList(){
		list = new Piece[2][16];
		
		for (int i = 0; i < 8; i++){
			list[0][i] = new Pawn(1,i,"wp");
			list[1][i] = new Pawn(6,i,"bp");
		}
		
		list[0][8] = new Rook(0, 0, "wR");
		list[0][9] = new Knight(0, 1, "wN");
		list[0][10] = new Bishop(0, 2, "wB");
		list[0][11] = new Queen(0, 3, "wQ");
		list[0][12] = new King(0, 4, "wK");
		list[0][13] = new Bishop(0, 5, "wB");
		list[0][14] = new Knight(0, 6, "wN");
		list[0][15] = new Rook(0, 7, "wR");
		
		list[1][8] = new Rook(7, 0, "bR");
		list[1][9] = new Knight(7, 1, "bN");
		list[1][10] = new Bishop(7, 2, "bB");
		list[1][11] = new Queen(7, 3, "bQ");
		list[1][12] = new King(7, 4, "bK");
		list[1][13] = new Bishop(7, 5, "bB");
		list[1][14] = new Knight(7, 6, "bN");
		list[1][15] = new Rook(7, 7, "bR");
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
			pieces[6][i] = new Pawn(6, i, "wp");
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
		Piece piece = pieces[row][col];
		
		pieces[row][col] = null;
		
		if(piece.getText().charAt(0) == 'w') {
			for(int i = 0; i < 16; i++) {
				if(list[0][i] != null) {
					if(list[0][i].getText() == piece.getText()
							&& list[0][i].getRow() == row
							&& list[0][i].getCol() == col)
						list[0][i] = null;
				}
				
			}
		} else {
			for(int i = 0; i < 16; i++) {
				if(list[1][i] != null) {
					if(list[1][i].getText() == piece.getText()
							&& list[1][i].getRow() == row
							&& list[1][i].getCol() == col)
						list[1][i] = null;
				}
				
			}
		}
	}
	
	public void movePiece(Piece piece, int row, int col) {
		int startRow = piece.getRow(), startCol = piece.getCol();
		pieces[startRow][startCol] = null;
		piece.setPos(row, col);
		piece.hasMoved = true;
		
		if(piece.getText().charAt(0) == 'w') {
			for(int i = 0; i < 16; i++) {
				if(list[0][i] != null) {
					if(list[0][i].getText() == piece.getText()
							&& list[0][i].getRow() == startRow
							&& list[0][i].getCol() == startCol)
						list[0][i].setPos(row, col);
				}
				
			}
		} else {
			for(int i = 0; i < 16; i++) {
				if(list[1][i] != null) {
					if(list[1][i].getText() == piece.getText()
							&& list[1][i].getRow() == startRow
							&& list[1][i].getCol() == startCol)
						list[1][i].setPos(row, col);
				}
				
			}
		}
		
		pieces[row][col] = piece;		
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