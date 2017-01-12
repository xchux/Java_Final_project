
public class RummikubBorder {
	RummikubCard[][] RummikubBorder = new RummikubCard[10][21];

	public RummikubBorder(RummikubBorder anotherBorder) {
		RummikubCard tempcards[][] = anotherBorder.getRummikubBorder();
		RummikubBorder = new RummikubCard[10][20];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 20; j++) {
				try {
					RummikubBorder[i][j] = new RummikubCard(tempcards[i][j]);
				} catch (java.lang.NullPointerException e) {
					RummikubBorder[i][j] = null;
					continue;
				}
			}
	}

	public RummikubBorder() {
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 20; j++)
				// RummikubBorder[i][j] = new RummikubCard(0, 0, 0);// 初始
				RummikubBorder[i][j] = new RummikubCard();// 初始
	}

	public RummikubCard[][] getRummikubBorder() {
		return RummikubBorder;
	}

	public RummikubCard getRummikubBorder(int x, int y) {
		return RummikubBorder[x][y];

	}

	public void setRummikubBorder(RummikubCard input, int x, int y) {
		RummikubBorder[x][y] = input;
	}

	public void removeRummikubBorder(int x, int y) {
		RummikubBorder[x][y] = new RummikubCard(0, 0, 0);// 初始
	}

	public void paintRummikubBorder() {

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				System.out.printf("%14s", RummikubBorder[x][y]);
			}
			System.out.printf("\n");
		}

	}

	public String SerializeCards(String StrCards) {
		String clst = "";
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 20; j++)
				RummikubBorder[i][j].getNumber();
		return clst;
	}

	public RummikubCard[][] DeSerializeCards(String StrCards) {
		String[] clst = StrCards.split("-");

		for (int i = 0; i < 200; i++) {
			int type = Integer.parseInt(clst[i].split(",")[0]);
			int color = Integer.parseInt(clst[i].split(",")[1]);
			int number = Integer.parseInt(clst[i].split(",")[2]);
			RummikubBorder[i / 20][i % 20] = new RummikubCard(type, color, number);
		}
		return RummikubBorder;
	}

	public boolean check() {

		boolean inGroup = false;
		int numGroups = 0; // 算個數
		int startx = 0, starty = 0;
		inGroup = true;
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				int out;
				try {
					out = RummikubBorder[x][y].getColornumber();
				} catch (Exception ex) {
					out = 0;
				}

				if (out != 0) {
					if (numGroups == 0) {
						startx = x;
						starty = y;
					}
					numGroups++;
				} else if (numGroups > 0 && out == 0) {
					if (legal(startx, starty, numGroups) == false) {
						return false;
					}
					numGroups = 0;
				} else {
					numGroups = 0;
				}

			} // end for
		}
		// END of setting up groups
		return inGroup;
	}

	public boolean legal(int startx, int starty, int numGroups) {
		boolean validBoard = true;
		int i = 0; // 第幾張牌
		int sameGroup = 0; // 同數字1 同色2
		int gap = 0, maxgap = 0;
		int numbergap = 0;
		if (numGroups < 3 || numGroups > 13)// 牌數不合
			return false;
		// 所有檢查都是與下一張牌檢查

		while (i + 1 + maxgap < numGroups) {// 到倒數第二張
			gap = 0;
			System.out.println(i + "iiiiii" + RummikubBorder[startx][starty + i + gap]);

			if (RummikubBorder[startx][starty + i].getNumber() == -1
					&& RummikubBorder[startx][starty + i + 1].getNumber() == 1 && sameGroup == 2) {// 排頭鬼牌下一張是一

				return false;
			} else if (RummikubBorder[startx][starty + i].getNumber() == 13
					&& RummikubBorder[startx][starty + i + 1].getNumber() == -1 && sameGroup == 2) {// 排尾鬼牌上一張是十三

				return false;
			}
			if (RummikubBorder[startx][starty + i].getNumber() == -1) {
				i++;
				maxgap++;
			}
			System.out.println(i + "iiiiii" + RummikubBorder[startx][starty + i + gap]);
			if (RummikubBorder[startx][starty + i].getNumber() != -1) {

				if (gap + i + 1 < numGroups)
					try{
					while (RummikubBorder[startx][starty + i + gap + 1].getNumber() == -1) { // 下一張牌
						gap++;// 中間空幾張

					}
					}catch(java.lang.NullPointerException ex){break;}
				System.out.println(gap + "gap" + RummikubBorder[startx][starty + i + gap + 1]);
				if (gap > maxgap)// maxgap判斷總排樹用
					maxgap = gap;
				System.out.println(maxgap + "maxgap");
				if (RummikubBorder[startx][starty + i + gap + 1].getNumber() != -1 && sameGroup == 0) {
					numbergap = (RummikubBorder[startx][starty + i].getNumber()
							- RummikubBorder[startx][starty + i + gap + 1].getNumber()) / (gap + 1);
				}
				if (maxgap == 2 && numGroups == 3) {// 總數3張牌 其中2張是鬼牌
					return true;
				} else if (maxgap + i + 1 >= numGroups) {
					System.out.println(maxgap + "///maxgap");
					return validBoard;
				}

				if (RummikubBorder[startx][starty + i].getNumber() == RummikubBorder[startx][starty + i + gap + 1]
						.getNumber()) {// 同號
					System.out.println("同號" + sameGroup);
					if (numGroups > 4 || sameGroup == 2) // 同色最多四張
						return false;
					int nextcolor = (starty + gap + i + 1);
					while (nextcolor < starty + numGroups) {
						System.out.print("nextcolor//" + starty + i);
						if (RummikubBorder[startx][nextcolor].getNumber() == -1) {
							nextcolor++;
							System.out.print("nextcolor//" + nextcolor);
						} else if (RummikubBorder[startx][starty + i].getColor() == RummikubBorder[startx][nextcolor]
								.getColor()) {// 同色回傳錯誤
							System.out.print("同號同色");
							return false;
						} else if (RummikubBorder[startx][starty + i].getNumber() != RummikubBorder[startx][nextcolor]
								.getNumber()) {
							return false;
						}
						nextcolor++;
					} // while end
					sameGroup = 1;
				} // 同號end
				else if (RummikubBorder[startx][starty + i].getColor() == RummikubBorder[startx][starty + i + gap + 1]
						.getColor()) {// 同色
					System.out.println(gap + "同色" + sameGroup);
					if (sameGroup == 1 || Math.abs(numbergap) > 1) {
						return false;
					}
					if (numbergap * 1.0 != (RummikubBorder[startx][starty + i].getNumber()
							- RummikubBorder[startx][starty + i + gap + 1].getNumber()) * 1.0 / (gap + 1)) {
						System.out.println("numbergap:" + numbergap);
						System.out.println("numbergap:" + (RummikubBorder[startx][starty + i].getNumber()
								- RummikubBorder[startx][starty + i + gap + 1].getNumber()) * 1.0 / (gap + 1));
						return false;
					}
					sameGroup = 2;
				} // 同色end
				else
					return false;

			} // getNumber!=-1 end

			i++;
		} // while end
		return validBoard;
	}
}
