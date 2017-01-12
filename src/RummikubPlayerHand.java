public class RummikubPlayerHand {
	private int LastCards; //手牌數
	private  RummikubCard[] hand; //手牌
	public RummikubPlayerHand(RummikubPlayerHand anotherplayerhand){
		int size = 36;
		this.LastCards = anotherplayerhand.LastCards;
		RummikubCard tempcards[] = anotherplayerhand.getHand();
		hand = new RummikubCard[size];
		for(int num=0;num<size;num++){
			hand[num] = new RummikubCard(tempcards[num]);
		}
	}		
			
	public RummikubPlayerHand(){
		hand = new RummikubCard[36];
		LastCards = 0;
	}
	
	public RummikubCard getCard(int index){
		return hand[index];
	}
	
	public void remove(int index){
		LastCards--;	
	}
	
	public void checkSortHand(){
		for(int i = 1; i < LastCards; i++){
			for(int k = 0; k < i; k++){
			if(hand[k] == null){
				RummikubCard temp = hand[k];
				hand[k] = hand[i];
				hand[i] = temp;
				}	
			}
		}
		int number= LastCards;
		for(int i = 0; i < number; i++){			
				if(hand[i]==null){
					remove(i);
				}
					
			}
	}
	
	public  RummikubCard[] DeSerializeCards(String StrCards)
	{
		String[] clst = StrCards.split("-");
		
		for(int i = 200 ; i<clst.length;i++)
		{
			int type = Integer.parseInt(clst[i].split(",")[0]);
			int color = Integer.parseInt(clst[i].split(",")[1]);
			int number = Integer.parseInt(clst[i].split(",")[2]);
			hand[i-200]=new RummikubCard(type,color,number);
		}
		return hand;
	}

	public RummikubCard[] getHand() {
		return hand;		
	}
	public void drawCard(RummikubCard card){ //抽一張牌到手牌中
		hand[LastCards] = card;
		LastCards++;
	}
	public void setplayCard(RummikubCard card,int index){ //從手牌中打出一張牌
		hand[index]=card;
	}
	public RummikubCard getplayCard(int index){ //從手牌中打出一張牌
		return hand[index];
	}
	public void removeplayCard(int index){ //從手牌中移除張牌
		hand[index] = null;
	}
	
	public int getLastCards(){
		return LastCards;
	}
	public int toSting(int index){
		return hand[index].getNumber();
	}
	public void showHand(){ //印出手牌
		System.out.printf("----------\n");
		for(int count = 0; count < LastCards+1; count++){
			if(count!=LastCards-1)
				System.out.printf("<%d: %-13s>,",count,hand[count]);
			else
				System.out.printf("<%d: %-13s>",count,hand[count]);
		}
		System.out.printf("\n----------\n");
	}
	
	
	public void sortHand(int k){ //整理手牌
		if(k == 1) //照數字
			for(int i = 0; i < LastCards; i++)
				for(int w = 0; w < LastCards-i; w++)
					if(hand[w]!= null && hand[w+1]!= null && hand[w].getNumber() > hand[w+1].getNumber()){
						RummikubCard temp = hand[w];
						hand[w] = hand[w+1];
						hand[w+1] = temp;
					}
		if(k == 2) //照顏色
			for(int i = 0; i < LastCards; i++)
				for(int w = 0; w < LastCards-i; w++)
					if(hand[w]!= null && hand[w+1]!= null && hand[w].getWeight() > hand[w+1].getWeight()){
						RummikubCard temp = hand[w];
						hand[w] = hand[w+1];
						hand[w+1] = temp;
					}
	}
}
