package chess;

public class Bishop extends Piece {

	public Bishop(int row, int col, String text) {
		super(row, col, text);
	}

	boolean isValidMove(Board board, int newRow, int newCol) {
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
		
		if(board.pieces[newRow][newCol] != null) {
			if (board.pieces[newRow][newCol].getText().charAt(0) == getText().charAt(0))
				return false;
		}
		
		//Check if a piece exists before the final position
		//Up-Right
		if (row < newRow && col < newCol){
			for (int i = row+1, j = col+1; i < newRow; i++, j++){
				if (board.pieces[i][j] != null)
					return false;
			}
			return true;
		}
		//Up-Left
		if (row < newRow && col > newCol){
			for (int i = row+1, j = col-1; i < newRow; i++, j--){
				if (board.pieces[i][j] != null)
					return false;
			}
			return true;
		}
		//Down-Right
		if (row > newRow && col < newCol){
			for (int i = row-1, j = col+1; i > newRow; i--, j++){
				if (board.pieces[i][j] != null)
					return false;
			}
			return true;
		}
		//Down-Left
		if (row > newRow && col > newCol){
			for (int i = row-1, j = col-1; i > newRow; i--, j--){
				if (board.pieces[i][j] != null)
					return false;
			}
			return true;
		}
		
		return false;
	}

	
	void move(Board board, int row, int col) {
		board.pieces[row][col] = new Bishop(row,col,getText());
	}

}
