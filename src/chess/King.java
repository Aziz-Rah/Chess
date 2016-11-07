package chess;

public class King extends Piece {

	public King(int row, int col, String text) {
		super(row, col, text);
	}

	//Need to check if the King wants to castle
	boolean isValidMove(String move) {
	
		String s1 = move.substring(0,2);
		String s2 = move.substring(3,5);
		int newRow = Character.getNumericValue(s2.charAt(0));
		int newCol = Character.getNumericValue(s2.charAt(1));
		int row = getRow();
		int col = getCol();
		if (row == newRow && col == newCol)
			return false;
		
		int diffR = Math.abs(newRow - row);
		int diffC = Math.abs(newCol - col);
		if (diffR > 1 || diffC > 1)
			return false;
		
		if (board[newRow][newCol].getText().charAt(0) == getText().charAt(0))
			return false;
		return true;
	}

	boolean move(int row, int col) {
		board[getRow()][getCol()] = null;	
		board[row][col] = new King(row,col,getText());
		return true;
	}

	//Question: How to determine if there is a rook???
	boolean castling(int col){
		
		//determines whether the piece is black or white
		boolean w = false;
		String s = getText();		
		if (s.charAt(0) == 'w')
			w = true;

		if (board[getRow()][getCol()].move)
			return false;
		
		if (col > getCol()){
			
			if (board[getRow()][col+1].move == true)
				return false;
			//Checking that there are no pieces in between the King and Rook
			for (int i = 1; i < 3; i++){
				if (board[getRow()][getCol()+i] != null)
					return false;
			}
			board[getRow()][col] = new King(getRow(),col,getText());
			
			if (w)
				board[getRow()][col-1] = new Rook(getRow(),col-1,"wR");
			else
				board[getRow()][col-1] = new Rook(getRow(),col-1,"bR");
			
			board[getRow()][col+1] = null;
			board[getRow()][getCol()] = null;
			return true;	
		}
		else if (col < getCol()){
			
			if (board[getRow()][col-2].move == true)
				return false;
			//Checking that there are no pieces in between the King and Rook
			for (int i = 1; i < 4; i++){
				if (board[getRow()][getCol()-i] != null)
					return false;
			}
			board[getRow()][col] = new King(getRow(),col,getText());
			
			if (w)
				board[getRow()][col+1] = new Rook(getRow(),col-1,"wR");
			else
				board[getRow()][col+1] = new Rook(getRow(),col-1,"bR");
			
			board[getRow()][col-2] = null;
			board[getRow()][getCol()] = null;
			return true;
			
		}
		return false;
	}
}
