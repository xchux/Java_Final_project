import java.util.Arrays;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class RummikubGame implements Cloneable {
	private int Players; // 玩家數
	private final int NUMBER_OF_CARDS = 106;
	private boolean[] ifMeld; // 是否破冰
	private int[] Order; // 順序
	private int[] heal = {3,3,3,3,3}; 
	private RummikubPlayerHand[] playerHand; // 每個玩家的手牌
	private DeckOfRummikubCard myRummikub; // 牌堆
	private RummikubBorder border = new RummikubBorder();
	private RummikubBorder returnborder = new RummikubBorder();
	private RummikubPlayerHand[] returnplayerHand; // 每個玩家的手牌
	Scanner scn = new Scanner(System.in);
	// private RummikubBorder returnboarder; /// 不合法回歸牌桌
	RummikubPlayerHand returnCards;/// 不合法回歸手牌
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
		for (int count = 0; count < Players * 14; count++) // 第一次發牌
			playerHand[(count % Players) + 1].drawCard(myRummikub.dealCard());
		// showDeckCards();
		determineOrder(); // 決定順序
		// showDeckCards();
	}

	/**
	 * public int getPlayerHandWeight(int p, int index) { return }
	 */

	public void determineOrder() { // 決定順序
		int[] orderValue = new int[Players + 1]; // 記錄權重
		RummikubCard[] tempDeck = new RummikubCard[Players + 1];
		Order = new int[Players + 1];
		System.out.printf("開始決定順序...(SMILE最大，黑>紅>藍>橘)\n");
		for (int count = 1; count <= Players; count++)
			tempDeck[count] = myRummikub.dealCard();
		int s = 0;
		while (s == 0) { // 檢查有沒有抽到一樣的
			s = 1;
			for (int x = 1; x < Players; x++)
				for (int y = x + 1; y <= Players; y++)
					if (tempDeck[x].getWeight() == tempDeck[y].getWeight()) {
						s = 0;
						System.out.printf("抽到一樣的牌，洗牌重抽\n");
						for (int count = 1; count <= Players; count++)
							myRummikub.returnCard(tempDeck[count]);
					}
			if (s == 0) {
				myRummikub.shuffle();
				for (int count = 1; count <= Players; count++)
					tempDeck[count] = myRummikub.dealCard();
			}
		}
		for (int count = 1; count <= Players; count++) { // 權重記錄下來，牌放回去
			orderValue[count] = tempDeck[count].getWeight();
			System.out.printf("玩家 %d 抽到的牌: %s\n", count, tempDeck[count]);
			myRummikub.returnCard(tempDeck[count]);
		}
		for (int count = 1; count <= Players; count++) { // 比較權重
			int max = orderValue[count];
			int maxIndex = count;
			for (int i = 1; i <= Players; i++) {
				if (orderValue[i] > max) {
					max = orderValue[i];
					maxIndex = i;
				}
			}
			Order[count] = maxIndex;
			System.out.printf("玩家 %d -> ", maxIndex);
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

	public void start() { // 遊戲開始
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

			returnCards = new RummikubPlayerHand(playerHand[p]); ///////////////// 轉原始4家手牌至returnCards
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
			 * System.out.printf("第 %d 回合，輪到玩家 %d : ", count + 1, p); if
			 * (ifMeld[p]) System.out.printf("(已經破冰)\n"); else
			 * System.out.printf("(還沒破冰)\n");
			 * System.out.printf("玩家 %d 牌架上的牌 : \n", p);
			 */

			System.out.println("玩家:" + p);
			choose = newframe.getChoice();
			/*
			 * System.out.print("出牌前的playerHand為 : "); playerHand[p].showHand();
			 * System.out.print("出牌前的returnCards為 : "); returnCards.showHand();
			 */
			// System.out.print("出牌前的returnBorders為 : \n");
			// returnBorders.paintRummikubBorder();
			// System.out.print("出牌前的border為 : \n");
			// border.paintRummikubBorder();
			while (choose == 1)// 出牌
			{	
				choose = newframe.getChoice();
				// System.out.println("我"+choose);
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
					// System.out.print("不合法結束時的returnCards為");
					// returnCards.showHand();
					/* System.out.print("出牌後的returnBorders為 :\n");
					 returnBorders.paintRummikubBorder();
					 System.out.print("出牌後的border為 :\n");
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
					
					if (border.check() == true) {// 合法
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
						System.out.println("//////////合法結束//////////");
						for (int x = 0; x < 10; x++)
							for (int y = 0; y < 20; y++) {
								border.setRummikubBorder(border.getRummikubBorder(x, y), x, y);
							}
						if(countborder==countreturnborder)
							choose = 2;
						System.out.printf("測試"+choose+"countborder///"+countborder+"countreturnborder///"+countreturnborder);
					} else { // 不合法
						System.out.println("//////////不合法結束//////////");
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
					if (choose == 2)// 抽牌
					{
						playerHand[p].drawCard(myRummikub.dealCard());
					}
				} else if (choose == 2)// 抽牌
				{
					playerHand[p] = returnCards;
					playerHand[p].drawCard(myRummikub.dealCard());
					border = returnBorders;
				} else
					System.out.print("");
			}
			// System.out.print("出牌後的returnBorders為 : \n");
			// returnBorders.paintRummikubBorder();
			// System.out.print("出牌後的border為 : \n");
			// border.paintRummikubBorder();

			/*
			 * System.out.print("出牌後的returnCards為 : "); returnCards.showHand();
			 * System.out.print("出牌後的playerHand為 : "); playerHand[p].showHand();
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