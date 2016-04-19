
// This class represents one playing card.
public class Card
{
	// Card suits (provided for your convenience - use is optional)
	public static final int SPADES   = 0;
	public static final int HEARTS   = 1;
	public static final int CLUBS    = 2;
	public static final int DIAMONDS = 3;

	// Card faces (provided for your convenience - use is optional)
	public static final int ACE      = 1;
	public static final int TWO      = 2;
	public static final int THREE    = 3;
	public static final int FOUR     = 4;
	public static final int FIVE     = 5;
	public static final int SIX      = 6;
	public static final int SEVEN    = 7;
	public static final int EIGHT    = 8;
	public static final int NINE     = 9;
	public static final int TEN      = 10;
	public static final int JACK     = 11;
	public static final int QUEEN    = 12;
	public static final int KING     = 13;


	// define fields here
	int suit;
	int face;
	
	// This constructor builds a card with the given suit and face, turned face down.
	public Card(int cardSuit, int cardFace)
	{
		this.suit=cardSuit;
		this.face=cardFace;
	}

	// This method retrieves the suit (spades, hearts, etc.) of this card.
	public int getSuit()
	{
		return this.suit;
	}
	
	// This method retrieves the face (ace through king) of this card.
	public int getFace()
	{
		return this.face;
	}
	
	// This method retrieves the numerical value of this card
	// (usually same as card face, except 1 for ace and 10 for jack/queen/king)
	public int getValue()
	{	
		if(this.face>=10){
			return 10;
		}
		else{
		return this.face;
		}
	}
	public String printCard(){
		String answer="";
		
		if(this.getFace()>10||this.getFace()==1){
			if(this.getFace()==1){
				answer+="A";
			}
			if(this.getFace()==11){
				answer+="J";
			}
			if(this.getFace()==12){
				answer+="Q";
			}
			if(this.getFace()==13){
				answer+="K";
			}
		}
		else{
			answer+=this.getFace();
		}
		//Prints Suit
		if(this.getSuit()==0){
			//spades
			String s=" of Spades";
			answer+=s;
		}
		if(this.getSuit()==1){
			//hearts
			String s=" of Hearts";
			answer+=s;
		}
		if(this.getSuit()==2){
			//clubs
			String s=" of Clubs";
			answer+=s;
		}
		if(this.getSuit()==3){
			//diamonds
			String s=" of Diamonds";
			answer+=s;
		}
		
		return answer;
	}
}




