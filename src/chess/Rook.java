package chess;

public class Rook extends Piece{

	public Rook(int row, int col, String text) {
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
		
		//Determines if there is a piece before the final position
		if (newRow == row){
			if (col < newCol){
				for (int i = col+1; i < newCol; i++){
					if (board.pieces[newRow][i] != null)
						return false;
				}
				return true;
			}
			else {
				for (int i = col-1; i > newCol; i--){
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
				for (int i = row-1; i > newRow; i--){
					if (board.pieces[i][newCol] != null)
						return false;
				}
				return true;
			}
		}
		
		return false;
		
	}
	
/*
	void move(Board board, int row, int col) {
		board.pieces[row][col] = new Rook(row,col,getText());
	}
	*/
}
