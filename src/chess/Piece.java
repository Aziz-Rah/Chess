package chess;

public abstract class Piece {

	private int row;
	private int col;
	private String text;
	
	public Piece(int row, int col, String text){
		this.row = row;
		this.col = col;
		this.text = text;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public String getText(){
		return text;
	}
	
	abstract boolean isValidMove(String move);
	//not boolean anymore
	abstract void move(int row, int col);
}