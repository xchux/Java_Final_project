import java.util.Arrays;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class RummikubGame implements Cloneable {
	private int Players; // ���a��
	private final int NUMBER_OF_CARDS = 106;
	private boolean[] ifMeld; // �O�_�}�B
	private int[] Order; // ����
	private int[] heal = {3,3,3,3,3}; 
	private RummikubPlayerHand[] playerHand; // �C�Ӫ��a����P
	private DeckOfRummikubCard myRummikub; // �P��
	private RummikubBorder border = new RummikubBorder();
	private RummikubBorder returnborder = new RummikubBorder();
	private RummikubPlayerHand[] returnplayerHand; // �C�Ӫ��a����P
	Scanner scn = new Scanner(System.in);
	// private RummikubBorder returnboarder; /// ���X�k�^�k�P��
	RummikubPlayerHand returnCards;/// ���X�k�^�k��P
	RummikubBorder returnBorders;

	public RummikubGame(int q) {
		Players = q;
		ifMeld = new boolean[q + 1];
		playerHand = new RummikubPlayerHand[q + 1];
		myRummikub = new DeckOfRummikubCard();
		myRummikub.shuffle();
		// myRummikub.showDeck();
		for (int count = 1; count <= Players; count++)
			playerHand[count] = new RummikubPlayerHand();
		for (int count = 0; count < Players * 14; count++) // �Ĥ@���o�P
			playerHand[(count % Players) + 1].drawCard(myRummikub.dealCard());
		// showDeckCards();
		determineOrder(); // �M�w����
		// showDeckCards();
	}

	/**
	 * public int getPlayerHandWeight(int p, int index) { return }
	 */

	public void determineOrder() { // �M�w����
		int[] orderValue = new int[Players + 1]; // �O���v��
		RummikubCard[] tempDeck = new RummikubCard[Players + 1];
		Order = new int[Players + 1];
		System.out.printf("�}�l�M�w����...(SMILE�̤j�A��>��>��>��)\n");
		for (int count = 1; count <= Players; count++)
			tempDeck[count] = myRummikub.dealCard();
		int s = 0;
		while (s == 0) { // �ˬd���S�����@�˪�
			s = 1;
			for (int x = 1; x < Players; x++)
				for (int y = x + 1; y <= Players; y++)
					if (tempDeck[x].getWeight() == tempDeck[y].getWeight()) {
						s = 0;
						System.out.printf("���@�˪��P�A�~�P����\n");
						for (int count = 1; count <= Players; count++)
							myRummikub.returnCard(tempDeck[count]);
					}
			if (s == 0) {
				myRummikub.shuffle();
				for (int count = 1; count <= Players; count++)
					tempDeck[count] = myRummikub.dealCard();
			}
		}
		for (int count = 1; count <= Players; count++) { // �v���O���U�ӡA�P��^�h
			orderValue[count] = tempDeck[count].getWeight();
			System.out.printf("���a %d ��쪺�P: %s\n", count, tempDeck[count]);
			myRummikub.returnCard(tempDeck[count]);
		}
		for (int count = 1; count <= Players; count++) { // ����v��
			int max = orderValue[count];
			int maxIndex = count;
			for (int i = 1; i <= Players; i++) {
				if (orderValue[i] > max) {
					max = orderValue[i];
					maxIndex = i;
				}
			}
			Order[count] = maxIndex;
			System.out.printf("���a %d -> ", maxIndex);
			orderValue[maxIndex] = 0;
		}
		System.out.printf("\n");
		myRummikub.shuffle();
	}

	/*
	 * public RummikubPlayerHand getReturnHandCards(int player){ return
	 * returnCards[player]; }
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void start() { // �C���}�l
		int checkCardNumber = 0;
		int count = 0;
		int p;
		//heal = {3,3,3,3,3};
		// int[] totalCards = {14,14,14,14};
		// RummikubFrame newframe1 = new RummikubFrame();
		RummikubFrame newframe = new RummikubFrame();
		do {

			p = count % Players + 1;
			//heal[p] = 3;
			newframe.setText(p);
			// RummikubBorder test = new RummikubBorder();
			// System.out.println(p);
			playerHand[p].sortHand(2);
			newframe.getplayerHand(playerHand[p]);
			newframe.ClearHandPanel();

			for (int counter = 0; counter <= playerHand[p].getLastCards(); counter++) {
				try {
					if (playerHand[p].getCard(counter).getNumber() != 0)
						newframe.addhandButton(playerHand[p].getCard(counter).getWeight(), counter);
				} catch (Exception ex) {
				}
			}

			returnCards = new RummikubPlayerHand(playerHand[p]); ///////////////// ���l4�a��P��returnCards
			returnBorders = new RummikubBorder(border);
			// newframe.removeAll();
			// newframe.repaint();

			newframe.getboarder(border);
			newframe.addboardButton(border);
			// newframe = new RummikubFrame();
			// newframe.ClearHandPanel();
			newframe.setVisible(true);
			for (int i = 0; i < 10; i++)
				for (int j = 0; j < 20; j++) {
					int n;
					try {
						n = border.getRummikubBorder(i, j).getNumber();
					} catch (Exception e) {
						n = 0;
					}
					if (n != 0)
						newframe.enableboardButton(i, j);
				}
			int choose = 1;
			
			RummikubCard[] temp = new RummikubCard[36];
			// border.paintRummikubBorder();
			/*
			 * System.out.printf("�� %d �^�X�A���쪱�a %d : ", count + 1, p); if
			 * (ifMeld[p]) System.out.printf("(�w�g�}�B)\n"); else
			 * System.out.printf("(�٨S�}�B)\n");
			 * System.out.printf("���a %d �P�[�W���P : \n", p);
			 */

			System.out.println("���a:" + p);
			choose = newframe.getChoice();
			/*
			 * System.out.print("�X�P�e��playerHand�� : "); playerHand[p].showHand();
			 * System.out.print("�X�P�e��returnCards�� : "); returnCards.showHand();
			 */
			// System.out.print("�X�P�e��returnBorders�� : \n");
			// returnBorders.paintRummikubBorder();
			// System.out.print("�X�P�e��border�� : \n");
			// border.paintRummikubBorder();
			while (choose == 1)// �X�P
			{	
				choose = newframe.getChoice();
				// System.out.println("��"+choose);
				if(choose == 5){
					int countborder = 0,countreturnborder=0;
					for (int i = 0; i < 10; i++)
						for (int j = 0; j < 20; j++)
							try {
								if (border.getRummikubBorder(i, j).getNumber() !=0) {
									choose = 3;
									countborder++;
									
								}if (returnBorders.getRummikubBorder(i, j).getNumber() !=0) {
									choose = 3;
									countreturnborder++;
									
								} 

							} catch (java.lang.NullPointerException ex) {
							}
					if(countborder==countreturnborder && heal[p] > 0){
						newframe.excheckok(false,heal[p]);
						System.out.println(heal[p]);
						heal[p]--;
					}
					else if(heal[p] > 0){
						newframe.excheckok(border.check(),heal[p]);
						heal[p]--;
					}
					else
						newframe.unexcheckok();
					choose = newframe.getChoice();
				}
				
				if (choose == 3) {
					// System.out.print("���X�k�����ɪ�returnCards��");
					// returnCards.showHand();
					/* System.out.print("�X�P�᪺returnBorders�� :\n");
					 returnBorders.paintRummikubBorder();
					 System.out.print("�X�P�᪺border�� :\n");
					 border.paintRummikubBorder();*/
					int countborder = 0,countreturnborder=0;
					for (int i = 0; i < 10; i++)
						for (int j = 0; j < 20; j++)
							try {
								if (border.getRummikubBorder(i, j).getNumber() !=0) {
									choose = 3;
									countborder++;
									
								}if (returnBorders.getRummikubBorder(i, j).getNumber() !=0) {
									choose = 3;
									countreturnborder++;
									
								} 

							} catch (java.lang.NullPointerException ex) {
							}
					
					if (border.check() == true) {// �X�k
						for (int i = 0; i < 10; i++)
							for (int j = 0; j < 20; j++) {
								int n;
								try {
									n = border.getRummikubBorder(i, j).getNumber();
								} catch (Exception e) {
									n = 0;
								}
								if (n != 0)
									newframe.enableboardButton(i, j);
							}
						System.out.println("//////////�X�k����//////////");
						for (int x = 0; x < 10; x++)
							for (int y = 0; y < 20; y++) {
								border.setRummikubBorder(border.getRummikubBorder(x, y), x, y);
							}
						if(countborder==countreturnborder)
							choose = 2;
						System.out.printf("����"+choose+"countborder///"+countborder+"countreturnborder///"+countreturnborder);
					} else { // ���X�k
						System.out.println("//////////���X�k����//////////");
						// boarder=returnboarder;
						// playerHand=returnplayerHand;
						// returnplayerHand.showHand();
						// ClearHandPanel();
						// addboardButton(border);
						try {
							playerHand[p] = returnCards;
							playerHand[p].drawCard(myRummikub.dealCard());
							border = returnBorders;

						} catch (Exception ex) {

						}
						for (int counter = 0; counter < playerHand[p].getLastCards(); counter++) {
							try {
								newframe.addhandButton(playerHand[p].getCard(counter).getWeight(), counter);
							} catch (Exception ex) {

							}
						}

					}
					if (choose == 2)// ��P
					{
						playerHand[p].drawCard(myRummikub.dealCard());
					}
				} else if (choose == 2)// ��P
				{
					playerHand[p] = returnCards;
					playerHand[p].drawCard(myRummikub.dealCard());
					border = returnBorders;
				} else
					System.out.print("");
			}
			// System.out.print("�X�P�᪺returnBorders�� : \n");
			// returnBorders.paintRummikubBorder();
			// System.out.print("�X�P�᪺border�� : \n");
			// border.paintRummikubBorder();

			/*
			 * System.out.print("�X�P�᪺returnCards�� : "); returnCards.showHand();
			 * System.out.print("�X�P�᪺playerHand�� : "); playerHand[p].showHand();
			 */

			for (int x = 0; x < 10; x++)
				for (int y = 0; y < 20; y++) {
					returnBorders.setRummikubBorder(border.getRummikubBorder(x, y), x, y);
				}

			playerHand[p].checkSortHand();

			newframe.changeMyChoice();

			count++;
			if(choose == 4 )break;
		} while (playerHand[p].getLastCards() >= 1);
		
		newframe.winner(newframe,p);
	} // for
		// end
}