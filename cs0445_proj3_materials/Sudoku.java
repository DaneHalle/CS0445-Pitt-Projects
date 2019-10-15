import java.util.List;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Sudoku {
	static final boolean[] check1={false,false,false,false,false,false,false,false,false};
	static final boolean[] check2={false,false,false,false,false,false,false,false,false};
	static final boolean[] check3={false,false,false,false,false,false,false,false,false};
	static int[][] inputBoard;


	static boolean isFullSolution(int[][] board) {
		for(int r=0; r<9; r++){
			for(int c=0; c<9; c++){
				int hold=board[r][c];
				if(hold==0){
					return false;
				}
			}
		}
		return true;
	}

	static boolean reject(int[][] board) {
		if(board.length==9){
			for(int r=0; r<9; r++){
				for(int c=0; c<9; c++){
					int hold1=board[r][c];
					if(hold1==0){}else{
						if(check1[hold1-1]){
							for(int i=0; i<9; i++){
								check1[i]=false;
								check2[i]=false;
							}
							return true;
						}else{
							check1[hold1-1]=true;
						}
					}
	
					int hold2=board[c][r];
					if(hold2==0){}else{
						if(check2[hold2-1]){
							for(int i=0; i<9; i++){
								check1[i]=false;
								check2[i]=false;
							}
							return true;
						}else{
							check2[hold2-1]=true;
						}
					}
				}
				for(int i=0; i<9; i++){
					check1[i]=false;
					check2[i]=false;
				}
			}
		}else{
			for(int m=0; m<3; m++){
				for(int n=0; n<3; n++){
					int hold3=board[m][n];
					if(hold3==0){}else{
						if(check3[hold3-1]){
							for(int i=0; i<9; i++){
								check3[i]=false;
							}	
							return true;
						}else{
							check3[hold3-1]=true;
						}
					}
				}
			}
			for(int i=0; i<9; i++){
				check3[i]=false;
			}	
		}

		if(board.length==9){
			int[][] test=new int[3][3];
			int q=0; int z=0;
			for(int blockRow=0; blockRow<9; blockRow+=3){
				for(int blockCol=0; blockCol<9; blockCol+=3){
					for(int tempRow=blockRow; tempRow<blockRow+3 && z<3; tempRow++){
						for(int tempCol=blockCol; tempCol<blockCol+3 && q<3; tempCol++){
							test[z][q]=board[tempRow][tempCol];
							q++; 
						}
						q=0;
						z++;
					}
					q=0; z=0;
					if(reject(test)){
						return true;
					}
				}
			}
		}

		for(int i=0; i<9; i++){
			check1[i]=false;
			check2[i]=false;
			check3[i]=false;
		}
		return false;
	}

	static int[][] extend(int[][] board) {
		int[][] out=copy(board);
		for(int r=0; r<9; r++){
			for(int c=0; c<9; c++){
				int hold=out[r][c];
				if(hold==0){
					out[r][c]=1;
					return out;
				}
			}
		}
		return null;
	}

	static int[][] next(int[][] board) {
		for(int i=8; i>=0; i--){
			for(int j=8; j>=0; j--){
				if(board[i][j]!=inputBoard[i][j]){
					if(board[i][j]==9){
						return null;
					}else{
						board[i][j]++;
						return board;
					}
				}
			}
		}
		return null;
	}

	static void testIsFullSolution() {
		System.out.println("IsFullSolution testing");
		int[][] trivial=readBoard("1-trivial.su");
		int[][] easy=readBoard("2-easy.su");
		int[][] medium=readBoard("3-medium.su");
		int[][] hard=readBoard("4-hard.su");
		int[][] evil=readBoard("5-evil.su");
		System.out.println(isFullSolution(trivial)+" trivial board");
		System.out.println(isFullSolution(easy)+" easy board");
		System.out.println(isFullSolution(medium)+" medium board");
		System.out.println(isFullSolution(hard)+" hard board");
		System.out.println(isFullSolution(evil)+" evil board");

		int[][] solved_board=readBoard("solved-board.su");
		System.out.println(isFullSolution(solved_board)+" solved_board board");
		int[][] partial_board=readBoard("partial-board.su");
		System.out.println(isFullSolution(partial_board)+" partial_board board");
		System.out.println();
	}

	static void testReject() {
		System.out.println("Reject testing");
		int[][] trivial=readBoard("1-trivial.su");
		int[][] easy=readBoard("2-easy.su");
		int[][] medium=readBoard("3-medium.su");
		int[][] hard=readBoard("4-hard.su");
		int[][] evil=readBoard("5-evil.su");
		System.out.println(reject(trivial)+" trivial board");
		System.out.println(reject(easy)+" easy board");
		System.out.println(reject(medium)+" medium board");
		System.out.println(reject(hard)+" hard board");
		System.out.println(reject(evil)+" evil board");

		int[][] solved_board=readBoard("solved-board.su");
		System.out.println(reject(solved_board)+" solved_board board");
		int[][] reject_row=readBoard("reject-row.su");
		System.out.println(reject(reject_row)+" reject_row board");
		int[][] reject_col=readBoard("reject-col.su");
		System.out.println(reject(reject_col)+" reject_col board");
		int[][] reject_3x3=readBoard("reject-3x3.su");
		System.out.println(reject(reject_3x3)+" reject_3x3 board");
		System.out.println();
	}

	static void testExtend() {
		System.out.println("Extend testing");
		int[][] trivial=readBoard("1-trivial.su");
		int[][] easy=readBoard("2-easy.su");
		int[][] medium=readBoard("3-medium.su");
		int[][] hard=readBoard("4-hard.su");
		int[][] evil=readBoard("5-evil.su");
		printBoard(trivial);
		System.out.println();
		printBoard(extend(trivial));
		System.out.println("  trivial board");

		printBoard(easy);
		System.out.println();
		printBoard(extend(easy));
		System.out.println("  easy board");

		printBoard(medium);
		System.out.println();
		printBoard(extend(medium));
		System.out.println("  medium board");

		printBoard(hard);
		System.out.println();
		printBoard(extend(hard));
		System.out.println("  hard board");

		printBoard(evil);
		System.out.println();
		printBoard(extend(evil));
		System.out.println("  evil board");
		System.out.println();
	}

	static void testNext() {
		System.out.println("Next testing");
		int[][] trivial=readBoard("1-trivial.su");
		int[][] easy=readBoard("2-easy.su");
		int[][] medium=readBoard("3-medium.su");
		int[][] hard=readBoard("4-hard.su");
		int[][] evil=readBoard("5-evil.su");
		trivial=extend(trivial);
		easy=extend(easy);
		medium=extend(medium);
		hard=extend(hard);
		evil=extend(evil);

		printBoard(trivial);
		System.out.println();
		inputBoard=readBoard("1-trivial.su");
		printBoard(next(trivial));
		System.out.println("  trivial board");

		printBoard(easy);
		System.out.println();
		inputBoard=readBoard("2-easy.su");
		printBoard(next(easy));
		System.out.println("  easy board");

		printBoard(medium);
		System.out.println();
		inputBoard=readBoard("3-medium.su");
		printBoard(next(medium));
		System.out.println("  medium board");

		printBoard(hard);
		System.out.println();
		inputBoard=readBoard("4-hard.su");
		printBoard(next(hard));
		System.out.println("  hard board");

		printBoard(evil);
		System.out.println();
		inputBoard=readBoard("5-evil.su");
		printBoard(next(evil));
		System.out.println("  evil board");
		System.out.println();

		int[][] satan=readBoard("6-satan.su");
		printBoard(satan);
		System.out.println();
		inputBoard=readBoard("6-satan.su");
		printBoard(next(satan));
		System.out.println("  satan board");
	}

	static int[][] copy(int[][] board){
		int[][] out=new int[9][9];
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				out[i][j]=board[i][j];
			}
		}
		return out;
	}

	static void print(int[][] board){
		if(board==null){
			System.out.println("No assignment");
			return;
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(board[i][j]);
			}
			System.out.print("\n");
		}
	}

	static void printBoard(int[][] board) {
		if(board == null) {
			System.out.println("No assignment");
			return;
		}
		for(int i = 0; i < 9; i++) {
			if(i == 3 || i == 6) {
				System.out.println("----+-----+----");
			}
			for(int j = 0; j < 9; j++) {
				if(j == 2 || j == 5) {
					System.out.print(board[i][j] + " | ");
				} else {
					System.out.print(board[i][j]);
				}
			}
			System.out.print("\n");
		}
	}

	static int[][] readBoard(String filename) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
		} catch (IOException e) {
			return null;
		}
		int[][] board = new int[9][9];
		int val = 0;
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				try {
					val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
				} catch (Exception e) {
					val = 0;
				}
				board[i][j] = val;
			}
		}
		return board;
	}

	static int[][] solve(int[][] board) {
		if(reject(board)){ return null;}
		if(isFullSolution(board)){ return board;}
		int[][] attempt = extend(board);
		while (attempt != null) {
			int[][] solution = solve(attempt);
			if(solution != null){ return solution;}
			attempt = next(attempt);
		}
		return null;
	}

	public static void main(String[] args) {
		if(args[0].equals("-t")) {
			testIsFullSolution();
			testReject();
			testExtend();
			testNext();
		} else {
			int[][] board = readBoard(args[0]);
			inputBoard=readBoard(args[0]);
			printBoard(board);
			System.out.println("Solution:");
			printBoard(solve(board));
		}
	}
}