package chess;

public class Rook extends Piece{

	public Rook(int row, int col, String text) {
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
		
		//Determines if there is a piece before the final position
		if (newRow == row){
			if (col < newCol){
				for (int i = col+1; i < newCol; i++){
					if (board[newRow][i] != null)
						return false;
				}
				return true;
			}
			else {
				for (int i = col-1; i < newCol; i--){
					if (board[newRow][i] != null)
						return false;
				}
				return true;
			}
		}
		else if (newCol == col){
			if (row < newRow){
				for (int i = row+1; i < newRow; i++){
					if (board[i][newCol] != null)
						return false;
				}
				return true;
			}
			else {
				for (int i = row-1; i < newRow; i--){
					if (board[i][newCol] != null)
						return false;
				}
				return true;
			}
		}
		
		return false;
		
	}

	boolean move(int row, int col) {
		board[row][col] = new Rook(row,col,getText());
		return true;
	}
}
