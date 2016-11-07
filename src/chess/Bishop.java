package chess;

public class Bishop extends Piece {

	public Bishop(int row, int col, String text) {
		super(row, col, text);
	}

	boolean isValidMove(String move) {
		
		String s1 = move.substring(0,2);
		String s2 = move.substring(3,5);
		int newRow = Character.getNumericValue(s2.charAt(0));
		int newCol = Character.getNumericValue(s2.charAt(1));
		int row = getRow();
		int col = getCol();
		if (row == newRow || col == newCol)
			return false;
		
		int diffR = newRow-row;
		int diffC = newCol-col;
		diffR = Math.abs(diffR);
		diffC = Math.abs(diffC);
		if (diffR != diffC)
			return false;
		
		if (board[newRow][newCol].getText().charAt(0) == getText().charAt(0))
			return false;
		
		//Check if a piece exists before the final position
		//Up-Right
		if (row < newRow && col < newCol){
			for (int i = row+1, j = col+1; i < newRow; i++, j++){
				if (board[i][j] != null)
					return false;
			}
			return true;
		}
		//Up-Left
		if (row < newRow && col > newCol){
			for (int i = row+1, j = col-1; i < newRow; i++, j--){
				if (board[i][j] != null)
					return false;
			}
			return true;
		}
		//Down-Right
		if (row > newRow && col < newCol){
			for (int i = row-1, j = col+1; i > newRow; i--, j++){
				if (board[i][j] != null)
					return false;
			}
			return true;
		}
		//Down-Left
		if (row > newRow && col > newCol){
			for (int i = row-1, j = col-1; i > newRow; i--, j--){
				if (board[i][j] != null)
					return false;
			}
			return true;
		}
		
		return false;
	}

	
	boolean move(int row, int col) {
		board[row][col] = new Bishop(row,col,getText());
		return true;
	}

}
