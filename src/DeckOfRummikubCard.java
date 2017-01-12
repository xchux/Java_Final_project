import java.util.Random;

public class DeckOfRummikubCard{
	private RummikubCard[] deck; //�P��
	private final int NUMBER_OF_CARDS = 106;
	private Random randomNumbers;
	private int topCard; //�̤W�����P�Ҧb��index
	
	public DeckOfRummikubCard(){
		topCard = 0;
		deck = new RummikubCard[NUMBER_OF_CARDS];
		randomNumbers = new Random();
		
		for(int count = 0; count < deck.length; count++){ //�ͦ��@�ƩԱK�P
			if (count == 0) deck[count] = new RummikubCard(1,1,-1);
			else if (count == 1) deck[count] = new RummikubCard(1,3,-1);
			else  deck[count] = new RummikubCard(2,((count-2)/26)+1,((count-2)%13)+1);
		}
	}
	
	public int getTopCard(){
		return topCard;
	}
	
	public void showDeck(){ //��ƵP�L�X��
		System.out.printf("----------\n");
		//System.out.printf("topCard = %d\n",topCard);
		for(int count = topCard; count < deck.length; count++){
			System.out.printf("%13s,",deck[count]);
			if(count%14 == 13) System.out.printf("\n");
		}
		System.out.printf("\n----------\n");
	}
	
	public void shuffle(){ //�~�P
		System.out.printf("�~�P��...\n");
		for(int first = 0; first < deck.length; first++){
			int second = randomNumbers.nextInt(NUMBER_OF_CARDS);
			RummikubCard temp = deck[first];
			deck[first] = deck[second];
			deck[second] = temp; 
		}
	}
	
	public RummikubCard dealCard(){ //�o�@�i�P�X�h
		if(topCard < deck.length){
			return deck[topCard++];
		}
		else return null;
	}
	
	public void returnCard(RummikubCard card){ //���@�i�P�^��
		if(topCard > 0)
			deck[--topCard] = card;
	}
}