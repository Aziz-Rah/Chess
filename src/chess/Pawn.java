package chess;

public class Pawn extends Piece{
	
	private boolean enPassant = false;
	
	public Pawn(int row, int col, String text) {
		super(row, col, text);	
	}

	boolean isValidMove(String move) {
		
		String s1 = move.substring(0,2);
		String s2 = move.substring(3,5);
		int newRow = Character.getNumericValue(s2.charAt(0));
		int newCol = Character.getNumericValue(s2.charAt(1));
		
		int row = getRow();
		int col = getCol();
		boolean w = false;
		String s = getText();		
		if (s.charAt(0) == 'w')
			w = true;
		if((w && row == 2) || (!w && row == 7))
			enPassant = true;
		
		if (enPassant && w){
			if ((board[newRow][newCol] != null) || board[newRow][newCol-1] != null)
				return false;
			if (row == newRow && (col == (newCol+1) || col == (newCol+2)))
				return true;
		}
		else if (w){
			if (board[newRow][newCol] != null)
				return false;
			if (row == newRow && col == newCol+1)
				return true;
		}
		else if (enPassant && !w){
			if ((board[newRow][newCol] != null) || board[newRow][newCol+1] != null)
				return false;
			if (row == newRow && (col == (newCol-1) || col == (newCol-2)))
				return true;
		}
		else {
			if (board[newRow][newCol] != null)
				return false;
			if (row == newRow && col == newCol-1)
				return true;
		}
		
		return false;
	}

	void move(int row, int col) {
		
		Piece piece = new Pawn(row,col,getText());
		board[row][col] = piece;
	}	
}
