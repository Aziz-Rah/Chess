package chess;

public class Knight extends Piece {

	public Knight(int row, int col, String text) {
		super(row, col, text);
	}

	boolean isValidMove(String move) {
		
		String s1 = move.substring(0,2);
		String s2 = move.substring(3,5);
		int newRow = Character.getNumericValue(s2.charAt(0));
		int newCol = Character.getNumericValue(s2.charAt(1));
		int row = getRow();
		int col = getCol();
		if (row == newRow && col == newCol)
			return false;
		
		if (board[newRow][newCol].getText().charAt(0) == getText().charAt(0))
			return false;
		
		//Knight moving vertically
		if ((row + 2 == newRow || row - 2 == newRow) && (col + 1 == newCol || col - 1 == newCol))
			return true;
		//Knight moving horizontally
		else if ((row + 1 == newRow || row - 1 == newRow) && (col + 2 == newCol || col - 2 == newCol))
			return true;
		else
			return false;
	
	}

	boolean move(int row, int col) {
		board[row][col] = new Knight(row,col,getText());
		return true;
	}

}
