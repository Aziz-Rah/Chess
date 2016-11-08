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
	
	boolean checkmate(Board board) {
		return false;
	}
}