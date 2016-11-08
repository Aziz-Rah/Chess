package chess;
import java.util.Scanner;

public class Chess {
	public static void main(String[] args) {
		boolean play = true;
		int player = 0; // white player = 0, black player = 1
		Scanner in = new Scanner(System.in);
		Board board = new Board();
		board.fill();
		board.display();
		
		while(play) {
			if(player == 0)
				System.out.print("White's move: ");
			else
				System.out.print("Black's move: ");
			
			String move = in.nextLine();
			
			if(move.equals("resign"))
				break;
			
			System.out.println("trying to move from " + getRowNum(move.charAt(1)) + "," + getColNum(move.charAt(0)) + " to " + getRowNum(move.charAt(4)) + "," + getColNum(move.charAt(3)));

			Piece piece = board.getPiece(getRowNum(move.charAt(1)), getColNum(move.charAt(0)));

			if(piece.isValidMove(board, getRowNum(move.charAt(4)), getColNum(move.charAt(3)))) {
				piece.move(board, getRowNum(move.charAt(4)), getColNum(move.charAt(3)));
				board.removePiece(piece.getRow(), piece.getCol());
				
				board.display();
				
				if(check(piece, board)) {
					System.out.println("Check");
				}
				
				if(player == 0)
					player++;
				else
					player--;
			}
			else
				System.out.println("Illegal move, try again");
		}
		
		in.close();
	}
	
	static int getRowNum(char c) {
		return '8' - c;
	}
	
	static int getColNum(char c) {
		return c - 'a';
	}
	
	static boolean check(Piece piece, Board board) {
		Piece king = null;
		String kingText;
		
		if(piece.getText().charAt(0) == 'w')
			kingText = "bK";
		else
			kingText = "wK";
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; i < 8; j++) {
				if(board.getPiece(i, j).getText() == kingText)
					king = board.getPiece(i, j);
			}
		}
		
		if(piece.isValidMove(board, king.getRow(), king.getCol()))
			return true;
		else
			return false;
	}
	
	boolean checkmate(Board board, Piece king) {
		
		int row = king.getRow();
		int col = king.getCol();
		
		/*	
		 * Find where the king can move
		 * The boolean arr is a square surrounding the king and finding every angle it can move
		 * From here check if any opposing color's piece has a valid move wherever the boolean array has a true
		 * Might be a better way to do this sorry LOL
		 * King is on arr[1][1]
		*/
		Boolean[][] arr = new Boolean[3][3];
		if (king.isValidMove(board,row,col-1))
			arr[1][0] = true;
		if (king.isValidMove(board,row+1,col-1))
			arr[0][0] = true;
		if (king.isValidMove(board,row+1,col+1))
			arr[0][1] = true;
		if (king.isValidMove(board,row+1,col+1))
			arr[0][2] = true;
		if (king.isValidMove(board,row,col+1))
			arr[1][2] = true;
		if (king.isValidMove(board,row-1,col-1))
			arr[2][0] = true;
		if (king.isValidMove(board,row-1,col))
			arr[2][1] = true;
		if (king.isValidMove(board,row-1,col+1))
			arr[2][2] = true;
		arr[1][1] = false;
		
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				
				//Here is where every opposing piece needs to checked if it has a valid move to where the king has a valid move
				//(the coordinates corresponding to a true in the boolean arr)
			}
		}
		
		return false;
	}
}