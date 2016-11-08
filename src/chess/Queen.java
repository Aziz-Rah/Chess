package chess;

public class Queen extends Piece {

	public Queen(int row, int col, String text) {
		super(row, col, text);
	}
	
	boolean isValidMove(Board board, int newRow, int newCol) {
		int row = getRow();
		int col = getCol();
		if (row == newRow && col == newCol)
			return false;
		
		int diffR = Math.abs(newRow - row);
		int diffC = Math.abs(newCol - col);
		boolean vert = false;
		boolean horiz = false;
		if (diffC == 0)
			vert = true;
		if (diffR == 0)
			horiz = true;
		//Queen moves in a direction that isn't diagonal, vertical, or horizontal
		if ((diffR != diffC && !vert) || (diffR != diffC && !horiz)) 
			return false;
		
		if (board.pieces[newRow][newCol].getText().charAt(0) == getText().charAt(0))
			return false;
		//Check if a piece exists before the final position
		
		//1D Motion
		if (horiz || vert){
			if (newRow == row){
				if (col < newCol){
					for (int i = col+1; i < newCol; i++){
						if (board.pieces[newRow][i] != null)
							return false;
					}
					return true;
				}
				else {
					for (int i = col-1; i < newCol; i--){
						if (board.pieces[newRow][i] != null)
							return false;
					}
					return true;
				}
			}
			else if (newCol == col){
				if (row < newRow){
					for (int i = row+1; i < newRow; i++){
						if (board.pieces[i][newCol] != null)
							return false;
					}
					return true;
				}
				else {
					for (int i = row-1; i < newRow; i--){
						if (board.pieces[i][newCol] != null)
							return false;
					}
					return true;
				}
			}
			return false;
		}
		
		//2D motion
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
		board.pieces[row][col] = new Queen(row,col,getText());
	}
}
