package chess;

public class Knight extends Piece {

	public Knight(int row, int col, String text) {
		super(row, col, text);
	}

	boolean isValidMove(Board board, int newRow, int newCol) {
		int row = getRow();
		int col = getCol();
		if (row == newRow && col == newCol)
			return false;
		if(board.pieces[newRow][newCol] != null) {
			if (board.pieces[newRow][newCol].getText().charAt(0) == getText().charAt(0))
			return false;
		}
		
		//Knight moving vertically
		if ((row + 2 == newRow || row - 2 == newRow) && (col + 1 == newCol || col - 1 == newCol))
			return true;
		//Knight moving horizontally
		else if ((row + 1 == newRow || row - 1 == newRow) && (col + 2 == newCol || col - 2 == newCol))
			return true;
		else
			return false;
	
	}

	void move(Board board, int row, int col) {
		board.pieces[row][col] = new Knight(row,col,getText());
	}

}
