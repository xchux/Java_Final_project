import java.util.Scanner;
import java.util.Arrays;

public class RummikubGameTest{
	public static void main(String args[]){
		int p;
		Scanner scn = new Scanner(System.in);
		System.out.printf("��J���a�H�� : ");
		//p = scn.nextInt();
		p=4;
		RummikubGame newGame = new RummikubGame(p);
		//newGame.showAllPlayerHand();
		
		newGame.start();
	}
}