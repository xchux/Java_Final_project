import java.util.Random;

public class DeckOfRummikubCard{
	private RummikubCard[] deck; //牌堆
	private final int NUMBER_OF_CARDS = 106;
	private Random randomNumbers;
	private int topCard; //最上面的牌所在的index
	
	public DeckOfRummikubCard(){
		topCard = 0;
		deck = new RummikubCard[NUMBER_OF_CARDS];
		randomNumbers = new Random();
		
		for(int count = 0; count < deck.length; count++){ //生成一副拉密牌
			if (count == 0) deck[count] = new RummikubCard(1,1,-1);
			else if (count == 1) deck[count] = new RummikubCard(1,3,-1);
			else  deck[count] = new RummikubCard(2,((count-2)/26)+1,((count-2)%13)+1);
		}
	}
	
	public int getTopCard(){
		return topCard;
	}
	
	public void showDeck(){ //整副牌印出來
		System.out.printf("----------\n");
		//System.out.printf("topCard = %d\n",topCard);
		for(int count = topCard; count < deck.length; count++){
			System.out.printf("%13s,",deck[count]);
			if(count%14 == 13) System.out.printf("\n");
		}
		System.out.printf("\n----------\n");
	}
	
	public void shuffle(){ //洗牌
		System.out.printf("洗牌中...\n");
		for(int first = 0; first < deck.length; first++){
			int second = randomNumbers.nextInt(NUMBER_OF_CARDS);
			RummikubCard temp = deck[first];
			deck[first] = deck[second];
			deck[second] = temp; 
		}
	}
	
	public RummikubCard dealCard(){ //發一張牌出去
		if(topCard < deck.length){
			return deck[topCard++];
		}
		else return null;
	}
	
	public void returnCard(RummikubCard card){ //收一張牌回來
		if(topCard > 0)
			deck[--topCard] = card;
	}
}