package chess;

public abstract class Piece {

	private int row;
	private int col;
	private String text;
	boolean hasMoved;
	
	public Piece(int row, int col, String text){
		this.row = row;
		this.col = col;
		this.text = text;
		hasMoved = false;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public void setPos(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public String getText(){
		return text;
	}
	
	abstract boolean isValidMove(Board board, int row, int col);
	abstract void move(Board board, int row, int col);
}