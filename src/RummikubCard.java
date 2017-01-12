public class RummikubCard {
	public enum Type {
		SMILE, NUMBER
	}

	public enum Color {
		RED, BLUE, BLACK, ORANGE
	}

	public static final int INVALID = -1;

	private int number;
	private int weight;
	private Type type;
	private Color color;

	public RummikubCard(RummikubCard anotherCard) {
		try{
		this.type = anotherCard.type;
		this.color = anotherCard.color;
		this.number = anotherCard.number;
		this.weight = anotherCard.weight;
		}
		catch(java.lang.NullPointerException e){}
	}

	public RummikubCard() {
	};/////////////////////////////////////////

	public RummikubCard(int t, int c, int number) {
		type = null;
		color = null;
		
		switch (t) {
		case 1:
			type = Type.SMILE;
			break;
		case 2:
			type = Type.NUMBER;
			break;
		}
		switch (c) {
		case 1:
			color = Color.RED;
			break;
		case 2:
			color = Color.BLUE;
			break;
		case 3:
			color = Color.BLACK;
			break;
		case 4:
			color = Color.ORANGE;
			break;
		}
		this.number = number;
		calculateWeight();
	}

	public Type getType() {
		return type;
	}

	public int getTypenumber() {
		int cardtype;
		if (type==Type.SMILE)
			cardtype = 1;
		else if (type==Type.NUMBER)
			cardtype = 2;
		else
			cardtype = 0;

		return cardtype;
	}

	public Color getColor() {
		return color;
	}

	public int getColornumber() {
		int cardnumber;
		if (color==Color.RED)
			cardnumber = 1;
		else if (color==Color.BLUE)
			cardnumber = 2;
		else if (color==Color.BLACK)
			cardnumber = 3;
		else if (color==Color.ORANGE)
			cardnumber = 4;
		else
			cardnumber = 0;

		return cardnumber;
	}

	public int getNumber() {
		return number;
	}

	public int getWeight() {
		return weight;
	}

	public void calculateWeight() {
		int w = 0;
		if (type == type.SMILE) // ¶Â¯ºÁy = 54¡A¬õ¯ºÁy = 53
			switch (color) {
			case BLACK:
				w = 54;
				break;
			case RED:
				w = 53;
				break;
			}
		else {
			switch (color) {
			case BLACK:
				w += 39;
				break;
			case RED:
				w += 26;
				break;
			case BLUE:
				w += 13;
				break;
			case ORANGE:
				w += 0;
				break;
			}
			w += getNumber();
			
		}
		weight = w;
	}

	public String toString() {
		if (number == INVALID)
			return color + " " + type;
		else
			return color + " " + number;
	}
}